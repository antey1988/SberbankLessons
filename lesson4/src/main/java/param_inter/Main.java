package param_inter;

import java.util.HashMap;
import java.util.Map;

public class Main {
    public static void main(String[] args) {
        System.out.println("-----------первый обект-------------");
        CountMap<Integer> map1 = new CountMapImpl<>();
        map1.add(10);
        map1.add(10);
        map1.add(5);
        map1.add(6);
        map1.add(5);
        map1.add(10);
        print(map1);

        System.out.println("-----------второй обект-------------");
        CountMap<Integer> map2 = new CountMapImpl<>();
        map2.add(6);
        map2.add(8);
        print(map2);

        System.out.println("-----------заполнение элементами другого объекта-------------");
        map1.addAll(map2);
        print(map1);

        System.out.println("-----------удаление элемента-------------");
        map1.remove(8);
        print(map1);

        System.out.println("-----------размер-------------");
        System.out.println(map1.size());

        System.out.println("-----------преобразование объекта в переданную мапу-------------");
        Map<Integer, Integer> map3 = new HashMap<>();
//        map3 = map1.toMap();
        map1.toMap(map3);
        map3.forEach((k, v)-> System.out.println(k + " :: " + v));

    }

    public static <T> void print(CountMap<T> countMap) {
        countMap.forEach((o)-> System.out.println(o + " : " + countMap.getCount(o)));
    }
}
