package snake.shell.swing;

import snake.game.SnakeGame;
import snake.game.tool.Coordinate;
import snake.game.tool.Direction;
import snake.shell.SnakeGameShell;

import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.*;

/**
 * Created by askello on 19.02.2016.
 */
public class SwingShell extends SnakeGameShell {

    private JPanel gameArea;
    private Graphics g;

    public SwingShell(SnakeGame snakeGame) {
        super(snakeGame);

        gameArea = new GameArea();
        gameArea.addKeyListener(new KeyControl());
        gameArea.setFocusable(true);
    }

    @Override
    public void setBackgroundColor(int red, int green, int blue) {
        backgroundColor = new Color(red, green, blue);
    }
    @Override
    public void setGridLinesColor(int red, int green, int blue) {
        gridLinesColor = new Color(red, green, blue);
    }
    @Override
    public void setSnakeColor(int red, int green, int blue) {
        snakeColor = new Color(red, green, blue);
    }
    @Override
    public void setAppleColor(int red, int green, int blue) {
        appleColor = new Color(red, green, blue);
    }

    @Override
    protected void setDrawingColor(Object color) {
        g.setColor((Color) color);
    }


    @Override
    protected void drawWindow() {
        JFrame frame = new JFrame();
        frame.setTitle("Snake Game");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.getContentPane().setPreferredSize(new Dimension(gameAreaWidth(), gameAreaHeight()));
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.add(gameArea);
        frame.setVisible(true);
    }

    @Override
    protected void drawBackground() {
        g.fillRect(0, 0, gameAreaWidth(), gameAreaHeight());
    }

    @Override
    protected void drawGrid() {
        // vertical lines
        for(int i=1; i<snakeGame.getGridSizeX(); i++)
            g.drawLine(i * squareSize + i - 1, 0, i * squareSize + i - 1, gameAreaHeight());
        // horizontal lines
        for(int i=1; i<snakeGame.getGridSizeY(); i++)
            g.drawLine(0, i * squareSize + i - 1, gameAreaWidth(), i * squareSize + i - 1);
    }

    @Override
    protected void drawPoint(int x, int y) {
        g.fillRect(x*squareSize+x, y*squareSize+y, squareSize, squareSize);
    }


    /**
     * we must override this method, because we must call
     * method repaint to repaint JPanel
     */
    @Override
    public void drawGameArea() {
        gameArea.repaint();
    }

    @Override
    protected void showScore(int score) {
        // you can implement this method if needed
    }
    @Override
    protected void showError(String message) {
        // you can implement this method if needed
    }



    // game area
    private class GameArea extends JPanel
    {
        @Override
        public void paint(Graphics graphics) {
            g = graphics;

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
    }


    // key control
    public class KeyControl extends KeyAdapter
    {
        public void keyPressed(KeyEvent e)
        {
            int key = e.getKeyCode();

                 if(key == KeyEvent.VK_UP)    snakeDirection = Direction.UP;
            else if(key == KeyEvent.VK_RIGHT) snakeDirection = Direction.RIGHT;
            else if(key == KeyEvent.VK_DOWN)  snakeDirection = Direction.DOWN;
            else if(key == KeyEvent.VK_LEFT)  snakeDirection = Direction.LEFT;
        }
    }

}
