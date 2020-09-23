import java.util.ArrayList;
import java.util.List;

public class AccountImp implements Account{
    private int id;
    private String firstname;
    private String secondname;
    private List<Account> listFriends = new ArrayList<>();
    private List<MediaContent> listMedia = new ArrayList<>();

    public AccountImp(int id, String firstname, String secondname) {
        this.id = id;
        this.firstname = firstname;
        this.secondname = secondname;
    }

    @Override
    public List<MediaContent> getContent() {
        return listMedia;
    }

    @Override
    public void show() {
        System.out.printf("Пользователь %s %s дружить с:%n", firstname, secondname);
        System.out.println(listFriends.toString());
    }
}
