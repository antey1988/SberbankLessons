package groupautos;

import java.util.*;

public class Main {
    public static void main(String[] args) {
        List<Auto> allautos = Auto.init(20);
        Map<String, List<Auto>>  groupautos = new HashMap<>();

        allautos.forEach(System.out::println);

        for(Auto a : allautos) {
            String type = a.getType();
            if (groupautos.containsKey(type)) {
                groupautos.get(type).add(a);
            } else {
                groupautos.put(type, new ArrayList<>(Arrays.asList(a)));
            }
        }

        System.out.println("--------------------------");
        groupautos.forEach((k, v) -> {
            System.out.println("group autos by type " + k);
            v.forEach(System.out::println);
        });
    }

}
