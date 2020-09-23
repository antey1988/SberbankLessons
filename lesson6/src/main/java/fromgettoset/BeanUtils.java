package fromgettoset;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class BeanUtils {
    public static void assign(Object to, Object from) throws InvocationTargetException, IllegalAccessException {
        //получаем геттеры объекта from
        List<Method> settersTo = getSetters(to);
        //получаем сеттеры объекта to
        List<Method> gettersFrom = getGetters(from);
        //если хотя бы один из списков пуст заверщаем работу метода
        if (settersTo.size() == 0 || gettersFrom.size() == 0) return;

        for (Method setter : settersTo) {                                   //перебираем все сеттеры объекта to
            Class<?> clazzParamSetter = setter.getParameterTypes()[0];      //получаем тип параметра сеттера
            for (Method getter : gettersFrom) {                             //перебираем геттеры объекта from
               Class<?> clazzReturnGetter = getter.getReturnType();         //определяем тип возвращаемого значения
               if(clazzParamSetter.isAssignableFrom(clazzReturnGetter)) {   //сравниваем полученные типы, с учетом наследования
                   setter.invoke(to, getter.invoke(from));                  //вызываем сеттер объекта to, передав параметр, полученный из геттера from
                   gettersFrom.remove(getter);
                   break;
               }
            }
        }
    }
//возвращает все геттеры класса переданного объекта
    private static List<Method> getGetters(Object object) {
        List<Method> list = new ArrayList<>();
        Class<?> clazz = object.getClass();
        Method[] methods = clazz.getMethods();
        for(Method method : methods) {
            if (!method.getName().startsWith("get")) continue;
            if (method.getParameterCount() != 0) continue;
            if (void.class.equals(method.getReturnType())) continue;
            list.add(method);
        }
        return list;
    }
    //возвращает все сеттеры класса переданного объекта
    private static List<Method> getSetters(Object object) {
        List<Method> list = new ArrayList<>();
        Class<?> clazz = object.getClass();
        Method[] methods = clazz.getMethods();
        for(Method method : methods) {
            if (!method.getName().startsWith("set")) continue;
            if (method.getParameterCount() != 1) continue;
            list.add(method);
        }
        return list;
    }
}
