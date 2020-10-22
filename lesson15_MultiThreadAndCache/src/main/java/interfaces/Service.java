package interfaces;

import annotations.Cache;
import annotations.Metric;
import annotations.TypeCache;

import java.util.Date;
import java.util.List;

public interface Service {
    @Metric
    @Cache(identityBy = {String.class, int.class}, type = TypeCache.IN_FILE)
    String printStringInteger(String string, int n, Date date);

    @Metric
    @Cache(identityBy = {String.class}, sizeList = 10, type = TypeCache.IN_MEMORY)
    List<String> run(String item, int value, Date date);

    List<String> work(String item);
}
