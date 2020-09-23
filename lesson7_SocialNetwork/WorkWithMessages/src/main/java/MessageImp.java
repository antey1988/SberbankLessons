public class MessageImp implements Message{
    @Override
    public Chat startChat(Account account) {
        System.out.printf("Создан чат с пользователем %s%n", account);
        return new Chat(account);
    }

    @Override
    public void stopChat(Chat chat) {
        System.out.printf("Чат с пользователем %s закрыт%n", chat.getAccount());
    }

    @Override
    public void delete(Chat chat) {
        System.out.printf("Чат с пользователем %s удален%n", chat.getAccount());
    }
}
