public interface Message {
    Chat startChat(Account account);
    void stopChat(Chat chat);
    void delete(Chat chat);
}
