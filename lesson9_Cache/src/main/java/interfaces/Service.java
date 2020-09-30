package interfaces;

import annotations.Cache;
import annotations.SkipType;
import annotations.Timer;

import java.util.Date;
import java.util.List;

public interface Service {
    @Cache()
    @Timer()
    String printStringInteger(String string, int n);

    @Cache(identityBy = {String.class, String.class, int.class}, sizeList = 7, zip = false)
    List<String> run(String str, String item, int value, Date date);

    List<String> work(String item);
}
