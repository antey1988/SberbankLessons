public class MediaImp implements Media{
    private Account account;

    public MediaImp(Account account) {
        this.account = account;
    }

    @Override
    public void addMusic(String name) {
        account.getContent().add(new MediaContent(name, MediaType.MUSIC));
    }

    @Override
    public void removeMusic(MediaContent media) {
        account.getContent().remove(media);
    }

    @Override
    public void listenMusic(MediaContent media) {
        media.play();
    }
}
