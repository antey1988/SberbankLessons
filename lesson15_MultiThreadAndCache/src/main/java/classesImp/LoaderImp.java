package classesImp;

import interfaces.Loader;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class LoaderImp implements Loader {
    @Override
    public void printIntegerString(int n, String string) {
        System.out.printf("Объект %s : выведена строка %s и число %d%n", this.toString(), string, n);
    }

    @Override
    public List<String> getOrderFromFilter(String item, double value, Date date) {
        return new ArrayList<>(Arrays.asList("five", "six"));
    }

    @Override
    public List<String> getAllOrder(String item) {
        return new ArrayList<>(Arrays.asList("seven", "nine"));
    }
}
