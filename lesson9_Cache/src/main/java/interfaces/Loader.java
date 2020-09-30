package interfaces;

import annotations.Cache;
import annotations.Timer;

import java.util.Date;
import java.util.List;

public interface Loader {
    @Cache
    @Timer
    void printIntegerString(int n, String string);

    List<String> getOrderFromFilter(String item, double value, Date date);
    List<String> getAllOrder(String item);
}
