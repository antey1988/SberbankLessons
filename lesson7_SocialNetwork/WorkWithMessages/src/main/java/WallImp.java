import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WallImp implements Wall{
    private boolean isOpen = false;
    List<Commit> commits = new ArrayList<>();

    @Override
    public void show() {
        if (isOpen)
            commits.forEach(System.out::println);
    }

    @Override
    public void hidden() {

    }

    @Override
    public void open() {
        System.out.println("Стена открыта для записей");
        isOpen = true;
    }

    @Override
    public void close() {
        System.out.println("Стена закрыта для записей");
        isOpen = false;
    }

    @Override
    public void addCommit(Commit commit) {
        if (isOpen)
            commits.add(commit);
    }

    @Override
    public void deleteCommit(Commit commit) {
        commits.remove(commit);
    }

    @Override
    public void clear() {
        commits.clear();
    }
}
