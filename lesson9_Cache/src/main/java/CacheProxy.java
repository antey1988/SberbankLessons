import annotations.Cache;
import annotations.SkipType;
import annotations.TypeCache;
import exceptions.CountTypeIdentityException;
import exceptions.IdentityException;
import exceptions.NotValidTypeIdentityException;

import java.io.*;
import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.*;

public class CacheProxy implements InvocationHandler {

    private Object proxyObject;
    private final Map<String,Map<Integer,Object>> cacheValuesAllMethods = new HashMap<>();
    private final String rootDirectory;
    private final boolean toDir;

    public CacheProxy(String rootDirictory, boolean toDir) {
        this.rootDirectory = rootDirictory;
        this.toDir = toDir;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws InvocationTargetException, IllegalAccessException {
        System.out.println("Прокси-объект " + proxyObject);
        Cache cacheAnnotation = null;

        Annotation[] annotations = method.getAnnotations();
        for (Annotation annotation : annotations) {
            if (annotation instanceof Cache) {
                cacheAnnotation  = (Cache) annotation;
                break;
            }
        }

        if (cacheAnnotation == null)
             return method.invoke(proxyObject, args);


        Object result;
        if (cacheAnnotation.type() == TypeCache.IN_MEMORY)
            result = cacheFromMemory(method, args, cacheAnnotation);
        else
            result = cacheFromFile(method, args, cacheAnnotation);

        return  result;
    }

//      извлечение кэша из оперативной памяти, если он там присутствует,
//      в случае отсутствия кэша - выполняется стандартный метод
//      полученное значение сохраняется в кэше
    private Object cacheFromMemory(Method method, Object[] args, Cache cacheAnnotation)
            throws InvocationTargetException, IllegalAccessException {
        Object result;
//      генерация уникального ключа поиска кеша
        String key;
//      Формируем имени таблицы, хранящей кэша конкретного метода
        String prefix = cacheAnnotation.namePrefix();
        if (prefix.equals(""))
            prefix = method.getName();
//      Формируем ключ поиска кэша в зависимости от заданной "маски"
        Class<?>[] identityBy = cacheAnnotation.identityBy();
        int postfix;
        try {
            postfix = generateHash(args, identityBy);
        } catch (IdentityException e) {
            System.out.println(e.getMessage());
            throw new RuntimeException();
        }

        if (cacheValuesAllMethods.containsKey(prefix)) {
            Map<Integer, Object> cacheValuesMethod = cacheValuesAllMethods.get(prefix);
            if (cacheValuesMethod.containsKey(postfix)) {
                result = cacheValuesMethod.get(postfix);
            } else {
                result = method.invoke(proxyObject, args);
//              проверка типа возвращаемого значения: коллекция или нет
                Class<?> returnType = method.getReturnType();
                if ((returnType == List.class) && (cacheAnnotation.sizeList() > 0)) {
                    int size = Math.min(((List) result).size(), cacheAnnotation.sizeList());
                    result = ((List) result).subList(0, size);
                }
                cacheValuesMethod.put(postfix, result);
            }
        } else {
            result = method.invoke(proxyObject, args);
            Class<?> returnType = method.getReturnType();
//          проверка типа возвращаемого значения: коллекция или нет
            if ((returnType == List.class) && (cacheAnnotation.sizeList() > 0)) {
                int size = Math.min(((List) result).size(), cacheAnnotation.sizeList());
                result = ((List) result).subList(0, size);
            }
            Map<Integer, Object> cacheValuesMethod = new HashMap<>();
            cacheValuesMethod.put(postfix, result);
            cacheValuesAllMethods.put(prefix, cacheValuesMethod);
        }
        return result;
    }

    private Object cacheFromFile(Method method, Object[] args, Cache cacheAnnotation)
            throws InvocationTargetException, IllegalAccessException {
        Object result;
//      Формируем префикс имени файла либо имени каталога
        String prefix = cacheAnnotation.namePrefix();
        if (prefix.equals(""))
            prefix = method.getName();
//      Формируем постфикс имени файла либо имя файла в зависимости от заданной "маски"
        Class<?>[] identityBy = cacheAnnotation.identityBy();
        int postfix;
        try {
            postfix = generateHash(args, identityBy);
        } catch (IdentityException e) {
            System.out.println(e.getMessage());
            throw new RuntimeException();
        }

        String name;
        File directory;
        if (!toDir) {
            directory = new File(rootDirectory);
            name = prefix + "_" + postfix + ".txt";
        } else {
            directory = new File(rootDirectory + File.separatorChar + prefix);
            name = "" + postfix  + ".txt";
        }
        if (!directory.exists())
            directory.mkdirs();
        File filename = new File(directory, name);

        try {
            InputStream fis = new FileInputStream(filename);
            ObjectInputStream ois = new ObjectInputStream(fis);
            result = ois.readObject();
            Class<?> returnType = method.getReturnType();
            if ((returnType == List.class) && (cacheAnnotation.sizeList() > 0)) {
                int size = Math.min(((List) result).size(), cacheAnnotation.sizeList());
                result = ((List) result).subList(0, size);
            }
            return result;
        } catch (FileNotFoundException e) {

            try (OutputStream fos = new FileOutputStream(filename)) {
                result = method.invoke(proxyObject, args);
                ObjectOutputStream oos = new ObjectOutputStream(fos);
                oos.writeObject(result);
                return  result;
            } catch (IOException fileNotFoundException) {
                fileNotFoundException.printStackTrace();
            }
        } catch (ClassNotFoundException | IOException e) {
            e.printStackTrace();
        }
        return null;
    }
//  генерация хэша из массива агрументов
//  с использованием "маски"
    private int generateHash(Object[] args, Class<?>[] classes)
            throws CountTypeIdentityException, NotValidTypeIdentityException {
//      если "маска не указана", используются все аргументы метода
        if (classes.length == 0) return generateHash(args);
//      таблица соответствия имен примитивных классов и классов-оберток
        Map<String, Class> mapPrimitive = new HashMap<>();
        mapPrimitive.put("byte", Byte.class);
        mapPrimitive.put("short", Short.class);
        mapPrimitive.put("char", Character.class);
        mapPrimitive.put("int", Integer.class);
        mapPrimitive.put("long", Long.class);
        mapPrimitive.put("float", Float.class);
        mapPrimitive.put("double", Double.class);
        mapPrimitive.put("boolean", Boolean.class);

        if (classes.length > args.length)
            throw new CountTypeIdentityException("Количество элементов массива \"маски\", определяющих " +
                    "уникальность кэша не должно превышать количество аргументов метода: " +
                    args.length + " аргумента");
//      формирование списка параметров, в соответствии с "маской"
        List<Object> listArgsForHash = new ArrayList<>();

        int j = 0;
        int i;
        for (i = 0; i < classes.length; i++) {
            Class<?> clazzAnnot = classes[i];
            if (clazzAnnot.isPrimitive())
                clazzAnnot = mapPrimitive.get(clazzAnnot.getSimpleName());
            if (clazzAnnot != SkipType.class) {
                for (; j < args.length; j++) {
                    Class<?> clazzArgs = args[j].getClass();
                    if (clazzAnnot.isAssignableFrom(clazzArgs)) {
                        listArgsForHash.add(args[j]);
                        j++;
                        break;
                    }
                }
            } else j++;
//          в случае если не для всех элементов массива "маски" найдены соответствующие аргументы,
//          а перебраны все аргументы метода, будет выброщенно исключение
//          информирующее о недопустимой маске
            if (j >= args.length ) {
                StringBuilder stringBuilder = new StringBuilder("");
                for (; i < classes.length; i++) {
                    stringBuilder.append("\n").append("\t").append(classes[i]);
                }
                throw new NotValidTypeIdentityException(
                        "Указаны не допустимые типы параметров:" + stringBuilder.toString());
            }
        }
        return generateHash(listArgsForHash.toArray());
    }
//  генерация хэша из массива аргументов
    private int generateHash(Object[] args) {
        int hash = 0;
        for (Object argsForHash : args) {
            hash = 31 * hash + argsForHash.hashCode();
        }
        return hash;
    }
//  создание прокси над переданным объектом
    public <T> T cache(T object) {
        this.proxyObject = object;
        ClassLoader classLoader = proxyObject.getClass().getClassLoader();
        Class<?>[] interfesec = proxyObject.getClass().getInterfaces();
        return  (T) Proxy.newProxyInstance(classLoader, interfesec, this);
    }
}
