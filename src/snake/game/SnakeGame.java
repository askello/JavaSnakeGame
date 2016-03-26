package snake.game;

import snake.game.exception.GameNotStartedException;
import snake.game.personage.Apple;
import snake.game.personage.Snake;
import snake.game.tool.Coordinate;
import snake.game.tool.Direction;

import java.io.*;
import java.util.ArrayList;
import java.util.Properties;
import java.util.Random;

/**
 * Created by askello on 19.02.2016.
 */
public class SnakeGame {

    private Snake snake;
    private Apple apple;

    private int gridSizeX;
    private int gridSizeY;

    private int startSnakeLength;
    private Coordinate startSnakePosition;
    private Direction startSnakeDirection;
    private Direction lastSnakeDirection;

    private boolean selfEating;
    private boolean infinitySpace;

    private boolean started = false;

    public SnakeGame() {
        configureGame();
    }

    public SnakeGame(int width, int height) {
        this();

        gridSizeX = width;
        gridSizeY = height;
    }

    public void start() {
        snake = new Snake(startSnakeLength, startSnakePosition, startSnakeDirection);
        lastSnakeDirection = startSnakeDirection;

        apple = new Apple();
        apple.setPosition(getFreePosition());

        started = true;
    }

    public void doMove(Direction direction) throws GameNotStartedException {
        if(started) {

                Coordinate newSnakePosition = getNewSnakePosition(direction);
                if (isPositionFree(newSnakePosition)) {
                    // if apple is on next position
                    if(apple.getBody().get(0).equals(newSnakePosition)) {
                        snake.gainWeight();
                        apple.setPosition(getFreePosition());
                    }

                    // self eating
                    if (selfEating && isOnSnake(newSnakePosition)) {
                        snake.removeTail(newSnakePosition);
                    }

                    snake.setPosition(newSnakePosition);
                }
                else
                    started = false;

        }
        else {
            throw new GameNotStartedException();
        }
    }

    public void restart() {
        start();
    }

    public void setGridSize(int x, int y) {
        gridSizeX = x;
        gridSizeY = y;
    }

    public void setStartSnakeLength(int size) {
        startSnakeLength = size;
    }

    public void setStartSnakePosition(int x, int y) {
        startSnakePosition.setPosition(x, y);
    }

    public void setStartSnakeDirection(Direction direction) {
        startSnakeDirection = direction;
    }

    public void setSelfEating(boolean selfEating) {
        this.selfEating = selfEating;
    }

    public void setInfinitySpace(boolean infinitySpace) {
        this.infinitySpace = infinitySpace;
    }

    public Direction getStartSnakeDirection() {
        return startSnakeDirection;
    }

    public int getGridSizeX() {
        return gridSizeX;
    }

    public int getGridSizeY() {
        return gridSizeY;
    }

    public ArrayList<Coordinate> getSnake() {
        return snake.getBody();
    }

    public ArrayList<Coordinate> getApple() {
        return apple.getBody();
    }

    public int getScore() {
        return snake.getBody().size() - startSnakeLength;
    }


    private Coordinate getFreePosition() {
        int randomX;
        int randomY;

        while(true) {
            boolean appleInTheSnake = false;

            randomX = new Random().nextInt(gridSizeX);
            randomY = new Random().nextInt(gridSizeY);

            for (Coordinate position : snake.getBody())
                if (position.getX() == randomX && position.getY() == randomY)
                    appleInTheSnake = true;

            if(appleInTheSnake==false) break;
        }

        return new Coordinate(randomX, randomY);
    }

    private boolean isPositionFree(Coordinate position) {

        // check space bounds
        if(!infinitySpace)
            if(position.getX() >= gridSizeX || position.getX() < 0 || position.getY() >= gridSizeY || position.getY() < 0)
                return false;

        // check selfEating
        if(!selfEating)
            return isOnSnake(position) ? false : true;

        return true;
    }

    private boolean isOnSnake(Coordinate position) {
        for(Coordinate snakeBodyPart : snake.getBody())
            if (position.equals(snakeBodyPart))
                return true;

        return false;
    }

    private Coordinate getNewSnakePosition(Direction direction) {
        int headX = snake.getBody().get(0).getX();
        int headY = snake.getBody().get(0).getY();

        int x=0, y=0;

        // check direction
        if(isDirectionBack(direction))
            direction = lastSnakeDirection;
        else
            lastSnakeDirection = direction;

        if(direction == Direction.UP)
        {
            x = headX;
            y = headY - 1;
            if(infinitySpace && y<0) y = gridSizeY - 1;
        }
        else if(direction == Direction.RIGHT)
        {
            x = headX + 1;
            y = headY;
            if(infinitySpace && x>=gridSizeX) x = 0;
        }
        else if(direction == Direction.DOWN)
        {
            x = headX;
            y = headY + 1;
            if(infinitySpace && y>=gridSizeY) y = 0;
        }
        else if(direction == Direction.LEFT)
        {
            x = headX - 1;
            y = headY;
            if(infinitySpace && x<0) x = gridSizeX - 1;
        }

        return new Coordinate(x, y);
    }

    private boolean isDirectionBack(Direction direction) {
        if(direction == Direction.UP    && lastSnakeDirection == Direction.DOWN)  return true;
        if(direction == Direction.RIGHT && lastSnakeDirection == Direction.LEFT)  return true;
        if(direction == Direction.DOWN  && lastSnakeDirection == Direction.UP)    return true;
        if(direction == Direction.LEFT  && lastSnakeDirection == Direction.RIGHT) return true;

        return false;
    }


    /**
     * Load settings from "settings.properties" file if available,
     * otherwise set default settings
     */
    private void configureGame() {
        try {
            loadFromProperties();
        }
        catch (Exception e) {
            System.err.print("Properties load error! Game is loaded using default settings.\n");
            setDefaultSettings();
        }
    }

    /**
     * Load settings from "settings.properties" file
     */
    private void loadFromProperties() throws Exception {
        Properties prop = new Properties();
        InputStream is = new FileInputStream(System.getProperty("user.dir") + "\\settings\\game.properties");

        prop.load(is);

        gridSizeX = Integer.parseInt(prop.getProperty("GridSizeX"));
        gridSizeY = Integer.parseInt(prop.getProperty("GridSizeY"));

        startSnakeLength = Integer.parseInt(prop.getProperty("StartSnakeLength"));
        startSnakePosition = new Coordinate(
                Integer.parseInt(prop.getProperty("StartSnakePositionX")),
                Integer.parseInt(prop.getProperty("StartSnakePositionY"))
            );

        String loadedDir = prop.getProperty("StartSnakeDirection");
        if(loadedDir.equals("UP"))
            startSnakeDirection = Direction.UP;
        else if(loadedDir.equals("RIGHT"))
            startSnakeDirection = Direction.RIGHT;
        else if(loadedDir.equals("DOWN"))
            startSnakeDirection = Direction.DOWN;
        else if(loadedDir.equals("LEFT"))
            startSnakeDirection = Direction.LEFT;

        selfEating = Boolean.parseBoolean(prop.getProperty("SelfEating"));
        infinitySpace = Boolean.parseBoolean(prop.getProperty("InfinitySpace"));
    }

    private void setDefaultSettings() {
        gridSizeX = 15;
        gridSizeY = 15;

        startSnakeLength = 2;
        startSnakePosition = new Coordinate(2, 2);
        startSnakeDirection = Direction.RIGHT;

        selfEating = false;
        infinitySpace = true;
    }

}
