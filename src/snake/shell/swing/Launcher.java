package snake.shell.swing;

import snake.game.SnakeGame;
import snake.game.tool.Direction;

/**
 * Created by askello on 05.03.2016.
 */
public class Launcher {

   public static void main(String args[]) {
       // Create and configure instance snake game
       SnakeGame snakeGame = new SnakeGame();

       /* Change game settings if needed

       // the same as the constructor
       snakeGame.setGridSize(20, 15);

       // default snake settings
       snakeGame.setStartSnakeLength(2);
       snakeGame.setStartSnakePosition(3, 3);
       snakeGame.setStartSnakeDirection(Direction.RIGHT);

       // set game options
       snakeGame.setSelfEating(true);
       snakeGame.setInfinitySpace(true);

       */


       // Create instance of shell and give it instance of snake game
       SwingShell shell = new SwingShell(snakeGame);

       /*
       shell.setSpeed(10); // from 1 to 10
       shell.setSquareSize(40);

       // Colors
       shell.setBackgroundColor(5, 50, 10);
       shell.setGridLinesColor(255, 216, 0);
       shell.setSnakeColor(20, 30, 150);
       shell.setAppleColor(210, 105, 30);
       */

       shell.start();
   }

}
