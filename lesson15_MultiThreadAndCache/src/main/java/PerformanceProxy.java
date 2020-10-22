import annotations.Metric;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class PerformanceProxy implements InvocationHandler {
    private Object object;
/*
    public PerformanceProxy(Object proxyObject) {
        this.proxyObject = proxyObject;
    }*/

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        Annotation[] annotations = method.getDeclaredAnnotations();
        for (Annotation annotation : annotations) {
            if(annotation instanceof Metric) {
                LocalTime start = LocalTime.now();
                Object value = method.invoke(object, args);
                LocalTime stop = LocalTime.now();
                Duration duration = Duration.between(start, stop);
                System.out.printf("Время выполнения метода %s c параметрами %s объекта %s в потоке %s: %d мс%n",
                        method.getName(), Arrays.asList(format(args)), object, Thread.currentThread().getName(), duration.toMillis());
                return value;
            }
        }
        return method.invoke(object, args);
    }


    //  создание прокси над переданным объектом
    public <T> T metric(T object) {
        this.object = object;
        ClassLoader classLoader = object.getClass().getClassLoader();
        Class<?>[] interfesec = object.getClass().getInterfaces();
        return  (T) Proxy.newProxyInstance(classLoader, interfesec, this);
    }

    @Override
    public String toString() {
        return "PerformanceProxy{" +
                "object=" + object +
                '}';
    }

    private Object[] format(Object[] args) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yy / hh:mm:ss:SS");
        Object[] object = Arrays.stream(args).map(t->{
            if (t instanceof Date) {
                t = sdf.format((Date)t);
            }
            return t;
        }).toArray();
        return object;
    }
}
