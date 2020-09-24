import java.util.HashMap;
import java.util.Map;

public class MessageImp implements Message {
    private Map<Account, Chat> chats = new HashMap<>();

    @Override
    public Chat startChat(Account account) {
        if (chats.containsKey(account))
            return chats.get(account);
        else {
            Chat chat = new Chat();
            System.out.printf("Создан чат с пользователем %s%n", account);
            chats.put(account, chat);
            return chat;
        }
    }

    @Override
    public void closeChat(Account account) {
        System.out.printf("Чат с пользователем %s закрыт%n", account);
    }

    @Override
    public void deleteChat(Account account) {
        chats.remove(account);
        System.out.printf("Чат с пользователем %s удален%n", account);
    }
}
