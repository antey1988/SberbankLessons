package interfaces;

import java.util.Date;
import java.util.List;

public interface Service {
    void doHardWork(String string, int n);
    List<String> run(String item, double value, Date date);
    List<String> work(String item);
}
