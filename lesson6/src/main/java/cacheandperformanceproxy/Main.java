package cacheandperformanceproxy;

import java.lang.reflect.Proxy;

public class Main {
    public static void main(String[] args) {
        int countCall = 20;
        Calculator calculator = new CalculatorImp();

        checkCache(calculator, countCall);
        System.out.println("-------------------");
        checkPerformance(calculator, countCall);
    }
    public static void checkCache(Calculator calculator, int count) {
        ClassLoader classLoader = calculator.getClass().getClassLoader();
        Class<?>[] interfaces = calculator.getClass().getInterfaces();

        CacheProxy cacheProxy = new CacheProxy(calculator);
        Calculator cacheProxyCalculator = (Calculator) Proxy.newProxyInstance(classLoader, interfaces, cacheProxy);

        for (int i = 0; i < count; i++) {
            int n = (int)(Math.random() * count);
            System.out.printf("square number %d is equal %d%n", n, cacheProxyCalculator.calc1(n));
        }
    }

    public static void  checkPerformance(Calculator calculator, int count) {
        ClassLoader classLoader = calculator.getClass().getClassLoader();
        Class<?>[] interfaces = calculator.getClass().getInterfaces();

        CacheProxy cacheProxy = new CacheProxy(calculator);
        Calculator cacheProxyCalculator = (Calculator) Proxy.newProxyInstance(classLoader, interfaces, cacheProxy);
        PerformanceProxy performanceProxy = new PerformanceProxy(cacheProxyCalculator);
//
//        PerformanceProxy performanceProxy = new PerformanceProxy(calculator);
        Calculator performanceProxyCalculator = (Calculator) Proxy.newProxyInstance(classLoader, interfaces, performanceProxy);

        for (int i = 0; i < count; i++) {
            int n = (int)(Math.random() * count);
            int metod = (int)(Math.random() * 2);
            if (metod == 0)
                System.out.printf("double value number %d is equal %d%n%n", n, performanceProxyCalculator.calc2(n));
            else
                System.out.printf("square number %d is equal %d%n%n", n, performanceProxyCalculator.calc1(n));
        }
    }
}
