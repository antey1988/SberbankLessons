package checkconst;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

public class ConstClass {
    public static void checkConst(Object object) {
        Class<?> clazz = object.getClass();
        Field[] fields = clazz.getDeclaredFields();
        for(Field field : fields) {
            //отбрасыывем не константы
            if (!Modifier.isFinal(field.getModifiers())) continue;
            //отбрасываем не строковые константы
            if (!field.getType().equals(String.class)) continue;
            //отбрасываем строковые константы, у которых значение не совпадает с именем
            field.setAccessible(true);
            try {
                if (!field.getName().equals(field.get(object))) continue;
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
            //печатем нужные констатны
            System.out.println(field.getName());
        }
    }
}
