package classesImp;

import interfaces.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class ServiceImp implements Service {
    @Override
    public void doHardWork(String string, int n) {
        System.out.printf("Объект %s : выведена строка %s и число %d%n", this.toString(), string, n);
    }

    @Override
    public List<String> run(String item, double value, Date date) {
        return new ArrayList<>(Arrays.asList("one", "two"));
    }

    @Override
    public List<String> work(String item) {
        return new ArrayList<>(Arrays.asList("three", "four"));
    }
}
