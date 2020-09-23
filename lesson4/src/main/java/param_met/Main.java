package param_met;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        System.out.println("------------ первый список -------------\n" +
                "------------ Проверка методов newArrayList and addAll -------------");
        List<Integer> list1 = CollectionUtils.newArrayList();
        CollectionUtils.addAll(Arrays.asList(8, 1, 3, 5, 6, 4), list1);
        System.out.println(list1);

        System.out.println("------------ второй список, для проверки метода containsAll -------------\n" +
                "------------ Проверка методов add and index -------------");
        List<Integer> list2 = new ArrayList<>();
        CollectionUtils.add(list2, 1);
        CollectionUtils.add(list2, 4);
        System.out.println(list2);
        System.out.println(CollectionUtils.indexOf(list2,4));

        System.out.println("------------ третий список, для проверки метода containsAny -------------");
        List<Integer> list3 = new ArrayList<>(Arrays.asList(9, 0, 11));
//        List<Integer> list3 = new ArrayList<>(Arrays.asList(9, 0, 1));
        System.out.println(list3);

        System.out.println("------------ Проверка методa limit  -------------");
        List<Integer> list4 = CollectionUtils.limit(list1, 3);
        System.out.println(list4);

        System.out.println("------------ Проверка методов containsAll, containsAny, removeAll -------------");
        System.out.println(CollectionUtils.containsAll(list1, list2));
        System.out.println(CollectionUtils.containsAny(list1, list3));
        CollectionUtils.removeAll(list1, list3);
        System.out.println(list1);

        System.out.println("------------ Проверка методов range -------------");
        List<Integer> list5 = CollectionUtils.range(list1,3,6);
        System.out.println(list5);
        List<Integer> list6 = CollectionUtils.range(list1,3,5, (i1, i2)-> Integer.compare(i2, i1));
        System.out.println(list6);

    }
}
