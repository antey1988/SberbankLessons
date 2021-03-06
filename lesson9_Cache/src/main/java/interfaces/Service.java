package interfaces;

import annotations.Cache;
import annotations.Metric;
import annotations.TypeCache;

import java.util.Date;
import java.util.List;

public interface Service {
    @Cache(type = TypeCache.IN_FILE)
    String printStringInteger(String string, int n);

    @Metric
    @Cache(identityBy = {String.class, String.class, int.class}, sizeList = 5, type = TypeCache.IN_FILE)
    List<String> run(String str, String item, int value, Date date);

    List<String> work(String item);
}
