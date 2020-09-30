package classesImp;

import interfaces.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

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
    public List<String> run(String item, int value, Date date) {
        List<String> list = new ArrayList<>(value);
        for (int i = 0; i < value; i++) {
            list.add(item);
        }
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public List<String> work(String item) {
        return new ArrayList<>(Arrays.asList("three", "four"));
    }
}
