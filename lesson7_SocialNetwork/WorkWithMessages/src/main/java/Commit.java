public class Commit {
    private Account account;
    private String commit;

    public Commit(Account account, String commit) {
        this.account = account;
        this.commit = commit;
    }

    public void setCommit(String commit) {
        this.commit = commit;
    }
}
