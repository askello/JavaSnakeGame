<h1>Introducing</h1>
Java Snake Game is the most popular project for java beginers. My goal was create java snake game, which can be launched on different platforms, for example swing, javafx, android and so on. Now, I have done game, abstract class for connecting this game for graphic shells and of course two graphic shells (for Swing and JavaFX), which appear the game.

<h1>Launch the game</h1>
<h5>JavaFX:</h5>
src/snake/shell/javafx/Launcher.java
<h5>Swing:</h5>
src/snake/shell/swing/Launcher.java

<h1>How to use</h1>
<h5>Snake Game Settigns:</h5>
```sh
// the same as the constructor
snakeGame.setGridSize(int, int);

// default snake settings
snakeGame.setStartSnakeLength(int);
snakeGame.setStartSnakePosition(int, int);
snakeGame.setStartSnakeDirection(Direction);

// game options
snakeGame.setSelfEating(boolean);
snakeGame.setInfinitySpace(boolean);
```
<h5>Snake Game Control:</h5>
```sh
// game control
snakeGame.start();
snakeGame.doMove(Direction);
snakeGame.restart();

// game output
snakeGame.getSnake();
snakeGame.getApple();
snakeGame.getScore();
```

<h1>How to create your own game:</h1>
To write your own graphic shell, you must implement abstract methods below. Other methods for game control is complete. And of course you can add your own methods or rewrite existing ones. They were developed to make this process easier.
```sh
// your main job
protected abstract void drawWindow();
protected abstract void drawBackground();
protected abstract void drawGrid();
protected abstract void drawPoint(int, int);

// color settings (if you don't want 3D graphics)
protected abstract void setDrawingColor(Object);
public abstract void setBackgroundColor(int, int, int);
public abstract void setGridLinesColor(int, int, int);
public abstract void setSnakeColor(int, int, int);
public abstract void setAppleColor(int, int, int);

// other
protected abstract void showScore(int);
protected abstract void showError(String);
```
