import java.util.Set;

public interface Friends {
    void showFriends();
    Set<Account> searchFriends();
    void addFriends(Account account);
    void removeFriends(Account account);

}
