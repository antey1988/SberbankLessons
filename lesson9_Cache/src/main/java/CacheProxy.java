import annotations.Cache;
import annotations.SkipType;
import annotations.TypeCache;
import exceptions.CountTypeIdentityException;
import exceptions.IdentityException;
import exceptions.NotValidTypeIdentityException;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CacheProxy implements InvocationHandler {

    private Object proxyObject;
    private Map<String,Map<Integer,Object>> cacheValuesAllMethods = new HashMap<>();

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

        Object result = null;

        if (cacheAnnotation.type() == TypeCache.IN_MEMORY)
            try {
                result = cacheFromMemory(method, args, cacheAnnotation);
            } catch (IdentityException e) {
                System.out.println(e.getMessage());
                throw new RuntimeException();
            }
        else
            try {
                result = cacheFromFile(method, args, cacheAnnotation);
            } catch (IdentityException e) {
                System.out.println(e.getMessage());
                throw new RuntimeException();
            }
        return  result;
    }


    private Object cacheFromMemory(Method method, Object[] args, Cache cacheAnnotation)
            throws InvocationTargetException, IllegalAccessException, CountTypeIdentityException, NotValidTypeIdentityException {
        Object result;
//      генерация уникального ключа поиска кеша
        String key;
//      Формируем префикс имени файла в случае записи кэша на диск
//      имя кюча - при хранении в оперативной памяти
        String prefix = cacheAnnotation.namePrefix();
        if (prefix.equals(""))
            prefix = method.getName();
//      формирование постфикса в зависимости от заданной "маски"
        Class<?>[] identityBy = cacheAnnotation.identityBy();
        int postfix = generateHash(args, identityBy);
//      проверка типа возвращаемого значения: коллекция или нет
        if (cacheValuesAllMethods.containsKey(prefix)) {
            Map<Integer, Object> cacheValuesMethod = cacheValuesAllMethods.get(prefix);
            if (cacheValuesMethod.containsKey(postfix)) {
                result = cacheValuesMethod.get(postfix);
            } else {
                result = method.invoke(proxyObject, args);
                Class<?> returnType = method.getReturnType();
                if (returnType == List.class) {
                    result = ((List)result).subList(0, cacheAnnotation.sizeList());
                }
                cacheValuesMethod.put(postfix, result);
            }
        } else {
            result = method.invoke(proxyObject, args);
            Class<?> returnType = method.getReturnType();
            if (returnType == List.class) {
                result = ((List)result).subList(0, cacheAnnotation.sizeList());
            }
            Map<Integer, Object> cacheValuesMethod = new HashMap<>();
            cacheValuesMethod.put(postfix, result);
            cacheValuesAllMethods.put(prefix, cacheValuesMethod);
        }
        return result;
    }

    private Object cacheFromFile(Method method, Object[] args, Cache cacheAnnotation)
            throws InvocationTargetException, IllegalAccessException, CountTypeIdentityException, NotValidTypeIdentityException {
        return null;
    }

    private int generateHash(Object[] args, Class<?>[] classes)
            throws CountTypeIdentityException, NotValidTypeIdentityException {
        Map<String, Class> mapPrimitive = new HashMap<>();
        mapPrimitive.put("byte", Byte.class);
        mapPrimitive.put("short", Short.class);
        mapPrimitive.put("char", Character.class);
        mapPrimitive.put("int", Integer.class);
        mapPrimitive.put("long", Long.class);
        mapPrimitive.put("float", Float.class);
        mapPrimitive.put("double", Double.class);
        mapPrimitive.put("boolean", Boolean.class);

        if (classes.length == 0) return generateHash(args);

        if (classes.length > args.length)
            throw new CountTypeIdentityException("Количество элементов массива классов, определяющих " +
                    "уникальность кэша не должно превышать " + args.length);

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
            }

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

    private int generateHash(Object[] args) {
        int hash = 0;
        for (Object argsForHash : args) {
            hash = 31 * hash + argsForHash.hashCode();
        }
        return hash;
    }

    public <T> T cache(T object) {
        this.proxyObject = object;
        ClassLoader classLoader = proxyObject.getClass().getClassLoader();
        Class<?>[] interfesec = proxyObject.getClass().getInterfaces();
        return  (T) Proxy.newProxyInstance(classLoader, interfesec, this);
    }
}
