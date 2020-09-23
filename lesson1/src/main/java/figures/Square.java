package figures;

public class Square extends Figure {
    //    coordinate left bottom pick
    private int x;
    private int y;
    //    side
    private int side;

    public Square(int x, int y, int side) {
        this.x = x;
        this.y = y;
        this.side = side;
    }
}
