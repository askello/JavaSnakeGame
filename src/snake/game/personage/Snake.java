package snake.game.personage;

import snake.game.tool.Coordinate;
import snake.game.tool.Direction;


/**
 * Created by askello on 20.02.2016.
 */
public class Snake extends Personage {

    private boolean eatenApple;

    public Snake(int length, Coordinate position, Direction direction) {
        eatenApple = false;
        build(length, position, direction);
    }

    @Override
    public void setPosition(Coordinate position) {
        // remove tail
        if(!eatenApple)
            body.remove(body.size() - 1);
        else
            eatenApple = false;

        // add head
        body.add(0, position);
    }

    public boolean ate(Apple apple) {
        // if snake's head has same position as apple...
        if(body.get(0).equals(apple.getBody().get(0)))
            return true;

        return false;
    }

    public void gainWeight() {
        eatenApple = true;
    }

    public void removeTail(Coordinate from) {
        // search index of point
        int index = 3;
        for(int i=index; index<body.size(); i++)
            if(body.get(i).equals(from)) {
                index = i;
                break;
            }

        index++;

        // remove elements from index to end
        while(body.size()>=index+1)
            body.remove(index);
    }

    private void build(int length, Coordinate headPosition, Direction direction)
    {
        for(int i=0; i<length; i++)
            if(direction==Direction.UP)         body.add(new Coordinate(headPosition.getX(), headPosition.getY()+i));
            else if(direction==Direction.RIGHT) body.add(new Coordinate(headPosition.getX()-i, headPosition.getY()));
            else if(direction==Direction.DOWN)  body.add(new Coordinate(headPosition.getX(), headPosition.getY()-i));
            else if(direction==Direction.LEFT)  body.add(new Coordinate(headPosition.getX()+i, headPosition.getY()));
    }
}
