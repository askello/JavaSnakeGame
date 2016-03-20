package snake.game.tool;

/**
 * Created by askello on 28.02.2016.
 */
public class Coordinate {
    private int x;
    private int y;

    public Coordinate(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void setPosition(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }


//    @Override
//    public boolean equals(Object o) {
//        if(o==null)
//            return false;
//
//        if(o instanceof Coordinate) {
//            Coordinate other = (Coordinate) o;
//            return (this.x == other.x && this.y == other.y);
//        }
//        else
//            return false;
//    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Coordinate)) return false;

        Coordinate that = (Coordinate) o;

        if (x != that.x) return false;
        if (y != that.y) return false;

        return true;
    }
}
