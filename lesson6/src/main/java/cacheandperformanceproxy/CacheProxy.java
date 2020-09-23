package cacheandperformanceproxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public class CacheProxy implements InvocationHandler {
    Calculator calculator;
    Map<Object, Object> squareValue = new HashMap<>();
    Map<Object, Object> doubleValue = new HashMap<>();

    public CacheProxy(Calculator calculator) {
        this.calculator = calculator;
    }


    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        Map<Object, Object> cache;
        if (method.getName().equals("calc1"))
             cache = squareValue;
        else
             cache = doubleValue;
        Object key = args[0];
        if (cache.containsKey(key))
            return cache.get(key);
        else {
            Object value = method.invoke(calculator, args);
            cache.put(key, value);
            return value;
        }
    }
}
