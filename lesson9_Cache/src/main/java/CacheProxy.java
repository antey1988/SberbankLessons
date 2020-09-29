import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class CacheProxy implements InvocationHandler {
    private Object proxyObject;

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println("Прокси-объект " + proxyObject);
        return method.invoke(proxyObject, args);
    }

    public <T> T cache(T object) {
        this.proxyObject = object;
        ClassLoader classLoader = proxyObject.getClass().getClassLoader();
        Class<?>[] interfesec = proxyObject.getClass().getInterfaces();
        return  (T) Proxy.newProxyInstance(classLoader, interfesec, this);
    }
}
