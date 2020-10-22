package classesImp;

import interfaces.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class ServiceImp implements Service {
    @Override
    public String printStringInteger(String string, int n, Date date) {
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return  String.format("Объект %s : выведена строка %s и число %d%n", this.toString(), string, n);
    }

    @Override
    public List<String> run(String item, int value, Date date) {
        List<String> ls = IntStream.range(0, value).mapToObj(n-> item + n).collect(Collectors.toList());
        try {
            Thread.sleep(1500);
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
