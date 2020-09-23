package allmetods;

import java.lang.reflect.Method;

public class MethodsClass {
    public static void printAllMetods(Object object, boolean flag) {
        Class<?> clazz = object.getClass();
        //выводит все методы объекта и всех его проедков
        if (flag) {
            do {
                Method[] methods = clazz.getDeclaredMethods();
                for (Method method : methods) {
                    System.out.println(clazz.getSimpleName() + " : " + method.toString());
                }
                clazz = clazz.getSuperclass();
            } while (clazz != null);
        }
        //выводит все публичные методы
        else {
            Method[] methods = clazz.getMethods();
            for (Method method : methods) {
                System.out.println(clazz.getSimpleName() + " : " + method.toString());
            }
        }
    }
}
