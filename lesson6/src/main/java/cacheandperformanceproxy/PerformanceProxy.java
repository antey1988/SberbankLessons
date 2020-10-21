package cacheandperformanceproxy;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.time.Duration;
import java.time.LocalTime;
public class PerformanceProxy implements InvocationHandler {
    Calculator calculator;

    public PerformanceProxy(Calculator calculator) {
        this.calculator = calculator;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        Annotation[] annotations = method.getDeclaredAnnotations();
        for (Annotation annotation : annotations) {
            if(annotation.annotationType().equals(Metric.class)) {
                LocalTime start = LocalTime.now();
                Object value = method.invoke(calculator, args);
                LocalTime stop = LocalTime.now();
                Duration duration = Duration.between(start, stop);
                System.out.printf("Время выполнения следующего метода составляет %d нс(%d мс)%n",
                        duration.toNanos(), duration.toMillis());
                return value;
            }
        }
        return method.invoke(calculator, args);
    }


}
