package classesImp;

import interfaces.Service;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class ServiceImp implements Service {
    @Override
    public String printStringInteger(String string, int n) {
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return  String.format("Объект %s : выведена строка %s и число %d%n", this.toString(), string, n);
    }

    @Override
    public List<String> run(String str, String item, int value, Date date) {
        IntStream si = IntStream.range(0, value);
        Stream<String> ss = si.mapToObj(n-> item + n);
        List<String> ls = ss.collect(Collectors.toList());
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return ls;
    }

    @Override
    public List<String> work(String item) {
        return new ArrayList<>(Arrays.asList("three", "four"));
    }
}
