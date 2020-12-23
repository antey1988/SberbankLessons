import interfaces.Cachable;
import interfaces.DAO;
import interfaces.ManagerDB;
import interfaces.Source;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

public class CacheProxy implements InvocationHandler {
    private final Object objectProxy;
    private final Map<Source, List<String>> sources = new ConcurrentHashMap<>();
    private Map<String, Map<Integer, List<Integer>>> cacheValuesMethodsFromDB = new ConcurrentHashMap<>();
    private Map<String, Map<Integer, List<Integer>>> cacheValuesMethodsRuntime = new ConcurrentHashMap<>();

    CacheProxy(Object objectProxy) {
        this.objectProxy = objectProxy;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) {
        Cachable annotation = method.getAnnotation(Cachable.class);
        if (annotation == null) {
            invokeMethodObject(method, args);
        }

        Map<Integer, List<Integer>> cacheValuesMethodFromDB = cacheValuesMethodsFromDB.get(method.getName());
        if (cacheValuesMethodFromDB != null) {
            Object cacheValueMethodFromDB = cacheValuesMethodFromDB.get(args[0]);
            if (cacheValueMethodFromDB != null)
                return cacheValueMethodFromDB;
        }

        Map<Integer, List<Integer>> cacheValuesMethod = cacheValuesMethodsRuntime.computeIfAbsent(method.getName(), k->new ConcurrentHashMap<>());
        Object cacheValueMethod = cacheValuesMethod.computeIfAbsent((Integer)args[0], k-> invokeMethodObject(method, args));
        return cacheValueMethod;
    }

    public static <T> T cache(T object){
        CacheProxy cacheProxy = new CacheProxy(object);
        cacheProxy.findAllSources();
        cacheProxy.loadingCacheFromDBToMemory();
        ClassLoader classLoader = object.getClass().getClassLoader();
        Class[] interfaces = object.getClass().getInterfaces();
        return (T)Proxy.newProxyInstance(classLoader, interfaces, cacheProxy);
    }

    private void findAllSources() {
        List<Method> methods = Arrays.stream(objectProxy.getClass().getInterfaces()).map(Class::getMethods)
                .flatMap(Arrays::stream).collect(Collectors.toList());
        Map<Class<? extends Source>, Source> DBs = new HashMap<>();
        for(Method method : methods) {
            Cachable annotation = method.getAnnotation(Cachable.class);
            if (annotation != null) {
                Source source = DBs.computeIfAbsent(annotation.value(), k -> {
                    try {
                        return annotation.value().newInstance();
                    } catch (InstantiationException | IllegalAccessException e) {
                        e.printStackTrace();
                    }
                    return null;
                });
                List<String> namesMethods = sources.computeIfAbsent(source, k -> new ArrayList<>());
                namesMethods.add(method.getName());
            }
        }
    }

    private void loadingCacheFromDBToMemory() {        
        ManagerDB managerDBImp = new ManagerDBImp();
        DAO daoImp = new DAOImp();
        for(Map.Entry<Source, List<String>> entry : sources.entrySet()) {
            try {
                managerDBImp.openConnection(entry.getKey().get_JDBC_Driver(), entry.getKey().get_Database_URL());
                for (String nameMethod : entry.getValue()) {
                    Map<Integer, List<Integer>> valuesMethod = daoImp.getMapObject(managerDBImp.selectAllFromTable(nameMethod));
                    cacheValuesMethodsFromDB.put(nameMethod, valuesMethod);
                }
            } finally {
                managerDBImp.closeConnection();
            }
        }
    }

    private List<Integer> invokeMethodObject(Method method, Object[] args) {
        try {
            return (List<Integer>) method.invoke(objectProxy, args);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        return  null;
    }
}
