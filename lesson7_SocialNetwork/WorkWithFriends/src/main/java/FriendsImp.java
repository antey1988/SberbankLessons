import java.util.HashSet;
import java.util.Set;

public class FriendsImp implements Friends{
    Set<Account> accountsFriends = new HashSet<>();

    @Override
    public void showFriends() {
        accountsFriends.forEach(System.out::println);
    }

    @Override
    public Set<Account> searchFriends() {
        return null;
    }

    @Override
    public void addFriends(Account account) {
        accountsFriends.add(account);
    }

    @Override
    public void removeFriends(Account account) {
        accountsFriends.remove(account);
    }
}
