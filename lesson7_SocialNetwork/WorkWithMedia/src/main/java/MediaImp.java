import java.util.ArrayList;
import java.util.List;

public class MediaImp implements Media{
    private List<MediaContent> contents = new ArrayList<>();

    public MediaImp() {
        loadFromDB();
    }

    private void loadFromDB() {
        System.out.println("Загрузка контет на из базы данных");
        contents.add(new MediaContent(1, "music1", MediaType.MUSIC));
        contents.add(new MediaContent(2, "music2", MediaType.MUSIC));
        contents.add(new MediaContent(3, "music3", MediaType.MUSIC));
        contents.add(new MediaContent(1, "video1", MediaType.VIDEO));
        contents.add(new MediaContent(2, "video2", MediaType.VIDEO));
        contents.add(new MediaContent(1, "foto1", MediaType.FOTO));
    }

    @Override
    public void showContent() {
        contents.forEach(System.out::println);
    }

    @Override
    public void addContent(MediaContent media) {
        contents.add(media);
    }

    @Override
    public void removeContent(MediaContent media) {
        contents.remove(media);
    }

    @Override
    public void startContent(MediaContent media) {
        media.play();
    }
}
