import java.util.HashMap;
import java.util.Map;

public class WallImp implements Wall{
    Map<Account, String> records = new HashMap<>();

    @Override
    public void open() {
        System.out.println("Стена открыта для записей");
    }

    @Override
    public void close() {
        System.out.println("Стена закрыта для записей");
    }
}
