package snake.game.personage;

import snake.game.tool.Coordinate;
import java.util.ArrayList;

/**
 * Created by askello on 20.02.2016.
 */
public abstract class Personage {
    protected ArrayList<Coordinate> body;

    public Personage() {
        body = new ArrayList<Coordinate>();
    }

    public Personage(int bodySize) {
        body = new ArrayList<Coordinate>(bodySize);
    }

    public Personage(ArrayList<Coordinate> body) {
        this.body = body;
    }

    public ArrayList<Coordinate> getBody() {
        return (ArrayList<Coordinate>)body.clone();
    }

    public abstract void setPosition(Coordinate position);
}
