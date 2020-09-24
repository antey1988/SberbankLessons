import java.util.ArrayList;
import java.util.List;

public class AccountImp implements Account{
    private int id;
    private String firstName;
    private String secondName;
    private Friends friends;
    private Media media;
    private Message message;
    private Wall wall;

    public AccountImp(int id, String firstName, String secondName) {
        this.id = id;
        this.firstName = firstName;
        this.secondName = secondName;
    }

    @Override
    public void show() {
        System.out.printf("Пользователь %s %s %n", firstName, secondName);
        System.out.println("Друзья:");
        friends.showFriends();
        System.out.println("Музыка, видео, фото:");
        media.showContent();
        System.out.println("Стена:");
        wall.show();
    }
}
