public interface Message {
    Chat startChat(Account account);
    void closeChat(Account account);
    void deleteChat(Account account);
}
