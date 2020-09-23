public class MediaContent {
    private String name;
    private MediaType type;

    public MediaContent(String name, MediaType type) {
        this.name = name;
        this.type = type;
    }

    public void play(){
        System.out.println("Медиa файл воспроизведен");
    }
}
