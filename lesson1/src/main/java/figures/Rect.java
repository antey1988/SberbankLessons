package figures;

public class Rect extends Figure{
//    coordinate left bottom pick
    private int x;
    private int y;
//    width, heigth
    private int width;
    private int heigth;

    public Rect(int x, int y, int width, int heigth) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.heigth = heigth;
    }
}
