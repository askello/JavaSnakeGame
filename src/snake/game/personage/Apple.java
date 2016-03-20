package snake.game.personage;


import snake.game.tool.Coordinate;

/**
 * Created by askello on 20.02.2016.
 */
public class Apple extends Personage {

    public Apple() {
        super(1);
        body.add(new Coordinate(0, 0));
    }

    public void setPosition(Coordinate position) {
        body.remove(0);
        body.add(position);
    }

}
