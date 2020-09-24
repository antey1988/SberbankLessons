public interface Wall {
    void show();
    void hidden();
    void open();
    void close();
    void addCommit(Commit commit);
    void deleteCommit(Commit commit);
    void clear();
}
