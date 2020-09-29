package interfaces;

import java.util.Date;
import java.util.List;

public interface Loader {
    void doHardWork(String string, int n);
    List<String> getOrderFromFilter(String item, double value, Date date);
    List<String> getAllOrder(String item);
}
