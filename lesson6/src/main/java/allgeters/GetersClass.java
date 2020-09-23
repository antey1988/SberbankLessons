package allgeters;

import java.lang.reflect.Method;

public class GetersClass {
    public static void printAllGeters(Object object) {
        Class<?> clazz = object.getClass();
        Method[] methods = clazz.getMethods();
        for(Method method : methods) {
            if (!method.getName().startsWith("get")) continue;
            if (method.getParameterCount() != 0) continue;
            if (void.class.equals(method.getReturnType())) continue;
            System.out.println(method.getName());
        }
    }
}
