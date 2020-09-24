public class MediaContent {
    private int id;
    private String name;
    private MediaType type;

    public MediaContent(int id, String name, MediaType type) {
        this.id = id;
        this.name = name;
        this.type = type;
    }

    public void play(){
        System.out.printf("Медиa-файл %s воспроизведен", name);
    }
}
