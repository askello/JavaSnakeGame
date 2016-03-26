package snake.shell;

import javafx.application.Platform;
import snake.game.exception.GameNotStartedException;
import snake.game.tool.Coordinate;
import snake.game.tool.Direction;
import snake.game.SnakeGame;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by askello on 19.02.2016.
 */
public abstract class SnakeGameShell {

    protected SnakeGame snakeGame;

    protected Direction snakeDirection;

    protected int speed;
    protected int squareSize;

    private Timer timer;
    private boolean animationPaused;

    protected Object backgroundColor;
    protected Object gridLinesColor;
    protected Object snakeColor;
    protected Object appleColor;

    public SnakeGameShell() {
        configureShell();
        animationPaused = true;
    }

    public SnakeGameShell(SnakeGame snakeGame) {
        this();
        setSnakeGame(snakeGame);
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public void setSquareSize(int squareSize) {
        this.squareSize = squareSize;
    }

    public void setSnakeGame(SnakeGame snakeGame) {
        this.snakeGame = snakeGame;
        snakeDirection = snakeGame.getStartSnakeDirection();
    }

    protected int gameAreaWidth() {
        return snakeGame.getGridSizeX() * squareSize + snakeGame.getGridSizeX() - 1;
    }

    protected int gameAreaHeight() {
        return snakeGame.getGridSizeY() * squareSize + snakeGame.getGridSizeY() - 1;
    }

    public void start() {
        snakeGame.start();
        drawWindow();
        drawGameArea();
        startAnimation();
        showScore(0);
    }

    public void restart() {
        if(!isAnimationPaused())
            stopAnimation();

        snakeDirection = snakeGame.getStartSnakeDirection();
        snakeGame.restart();

        startAnimation();
    }

    protected void startAnimation() {
        if(isAnimationPaused()) {
            TimerTask task = new TimerTask() {
                @Override
                public void run() {
                    // do one move
                    try {
                        snakeGame.doMove(snakeDirection);
                    } catch (GameNotStartedException e) {
                        Platform.runLater(new Runnable() {
                            @Override
                            public void run() {
                                showError("Your score is " + snakeGame.getScore() + ". Try again!");
                            }
                        });
                        stopAnimation();
                    }

                    // repaint game area
                    drawGameArea();
                    showScore(snakeGame.getScore());
                }
            };

            timer = new Timer();
            timer.schedule(task, 0, (600 - speed * 50) / 2);

            animationPaused = false;
        }
    }

    protected void stopAnimation() {
        if(!isAnimationPaused()) {
            timer.cancel();
            animationPaused = true;
        }
    }

    protected boolean isAnimationPaused() {
        return animationPaused;
    }

    private void configureShell() {
        try {
            loadFromProperties();
        }
        catch (Exception e) {
            System.err.print("Properties load error! Shell is loaded using default settings.\n");
            setDefaultSettings();
        }
    }

    private void loadFromProperties() throws Exception {
        Properties prop = new Properties();
        InputStream is = new FileInputStream(System.getProperty("user.dir") + "\\settings\\shell.properties");

        prop.load(is);

        speed = Integer.parseInt(prop.getProperty("Speed"));
        squareSize = Integer.parseInt(prop.getProperty("SquareSize"));

        // Colors
        int[] color = null;
        color = strToRGB(prop.getProperty("BackgroundColor"));
        setBackgroundColor(color[0], color[1], color[2]);
        color = strToRGB(prop.getProperty("GridLinesColor"));
        setGridLinesColor(color[0], color[1], color[2]);
        color = strToRGB(prop.getProperty("SnakeColor"));
        setSnakeColor(color[0], color[1], color[2]);
        color = strToRGB(prop.getProperty("AppleColor"));
        setAppleColor(color[0], color[1], color[2]);
    }

    private int[] strToRGB(String str) {
        int[] rgbArr = new int[3];

        String[] strArr = str.split(",");
        for(int i=0; i<3; i++)
            rgbArr[i] = Integer.parseInt(strArr[i]);

        return rgbArr;
    }

    private void setDefaultSettings() {
        speed = 5;
        squareSize = 40;

        setBackgroundColor(255, 204, 128);
        setGridLinesColor(255, 255, 255);
        setSnakeColor(230, 77, 77);
        setAppleColor(128, 179, 128);
    }

    protected void drawGameArea() {
        setDrawingColor(backgroundColor);
        drawBackground();

        setDrawingColor(gridLinesColor);
        drawGrid();

        setDrawingColor(snakeColor);
        for(Coordinate snakeBodyPart : snakeGame.getSnake())
            drawPoint(snakeBodyPart);

        setDrawingColor(appleColor);
        drawPoint(snakeGame.getApple().get(0));
    }

    protected void drawPoint(Coordinate position) {
        drawPoint(position.getX(), position.getY());
    }

    // you must override only methods bellow
    protected abstract void drawWindow();
    protected abstract void drawBackground();
    protected abstract void drawGrid();
    protected abstract void drawPoint(int x, int y);

    protected abstract void setDrawingColor(Object color);
    public abstract void setBackgroundColor(int red, int green, int blue);
    public abstract void setGridLinesColor(int red, int green, int blue);
    public abstract void setSnakeColor(int red, int green, int blue);
    public abstract void setAppleColor(int red, int green, int blue);

    protected abstract void showScore(int score);
    protected abstract void showError(String message);
}
