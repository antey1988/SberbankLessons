import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.sql.*;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class CacheProxy implements InvocationHandler {
    private final Object objectProxy;
    private final Map<Source, List<String>> sources = new ConcurrentHashMap<>();
    private Map<String, Map<Integer, Object>> cacheValuesMethods = new ConcurrentHashMap<>();

    private CacheProxy(Object objectProxy) {
        this.objectProxy = objectProxy;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        Cachable annotation = method.getAnnotation(Cachable.class);
        if (annotation == null)
            return method.invoke(objectProxy, args);
        return  null;
    }

    public static <T> T cache(T object){
        CacheProxy cacheProxy = new CacheProxy(object);
        ClassLoader classLoader = object.getClass().getClassLoader();
        Class[] interfaces = object.getClass().getInterfaces();
        return (T)Proxy.newProxyInstance(classLoader, interfaces, cacheProxy);
    }

    private void getAllDatabaseForAllMethods() {
        Method[] methods = objectProxy.getClass().getDeclaredMethods();
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


    private Map<String, ResultSet> getCacheValuesAllMethodsFromAllDatabase() {
        Map<String, ResultSet> map = new HashMap<>();
        for(Map.Entry<Source, List<String>> entry : sources.entrySet()) {
            try {
                Class.forName(entry.getKey().get_JDBC_Driver());
                Connection connection = DriverManager.getConnection(entry.getKey().get_Database_URL());
                Statement statement = connection.createStatement();
                for (String nameMethod : entry.getValue()) {
                    String SQL = "SELECT * FROM " + nameMethod;
                    ResultSet resultSet = statement.executeQuery(SQL);
                    map.put(nameMethod, resultSet);
                }
                statement.close();
                connection.close();
            } catch (ClassNotFoundException | SQLException e) {
                e.printStackTrace();
            }
        }
        return map;
    }
}
