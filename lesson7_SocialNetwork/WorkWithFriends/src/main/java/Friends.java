import java.util.List;

public interface Friends {
    List<Account> searchFriends(Account account);
    void addFriends(Account account);
    void removeFriends(Account account);

}
