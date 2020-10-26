package refactoring1;

public class Tractor {

    int[] position = new int[] { 0, 0 };
    final int[] field = new int[] { 5, 5 };
    Orientation orientation = Orientation.NORTH;

    public void move(String command) {
        if (command.equals("F")) {
            moveForwards();
        } else if (command.equals("T")) {
            turnClockwise();
        }
    }

    public void moveForwards() {
        if (orientation == Orientation.NORTH) {
            position[1]++;
        } else if (orientation == Orientation.EAST) {
            position[0]++;
        } else if (orientation == Orientation.SOUTH) {
            position[1]--;
        } else {
            position[0]--;
        }

        if (position[0] > field[0] || position[1] > field[1]) {
            throw new TractorInDitchException();
        }
    }

    public void turnClockwise() {
        /*if (orientation == Orientation.NORTH) {
            orientation = Orientation.EAST;
        } else if (orientation == Orientation.EAST) {
            orientation = Orientation.SOUTH;
        } else if (orientation == Orientation.SOUTH) {
            orientation = Orientation.WEST;
        } else if (orientation == Orientation.WEST) {
            orientation = Orientation.NORTH;
        }*/
        int i = orientation.ordinal();
        if (--i < 0)
            i = 3;
        orientation = Orientation.values()[i];
    }

    public int getPositionX() {
        return position[0];
    }

    public int getPositionY() {
        return position[1];
    }

    public Orientation getOrientation() {
        return orientation;
    }

}
