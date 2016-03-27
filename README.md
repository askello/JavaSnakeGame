<h1>Introducing</h1>
Java Snake Game is the most popular project for java beginers. My goal was to create java snake game, which can be launched on different platforms, for example swing, javafx, android and so on. Now, I have done the game, abstract class for connecting this game for graphic shells and of course two graphic shells (for Swing and JavaFX), which appear the game.

<h1>Launch the game</h1>
You can use native launchers for your OS ("native-apps" folder), or you can launch the game in your IDE by running main methods in files below:
<h5>JavaFX:</h5>
src/snake/shell/javafx/Launcher.java
<h5>Swing:</h5>
src/snake/shell/swing/Launcher.java

<h1>How to use</h1>
<h5>Snake Game Settigns:</h5>
```
// the same as the constructor
setGridSize(int, int);

// default snake settings
setStartSnakeLength(int);
setStartSnakePosition(int, int);
setStartSnakeDirection(Direction);

// game options
setSelfEating(boolean);
setInfinitySpace(boolean);
```
<h5>Snake Game Control:</h5>
```
// game control
start();
doMove(Direction);
restart();

// game output
getSnake();
getApple();
getScore();
```
<h5>Sell:</h5>
```
// settings
setSpeed(int); // from 1 to 10
setSquareSize(int);

// Colors
setBackgroundColor(int, int, int);
setGridLinesColor(int, int, int);
setSnakeColor(int, int, int);
setAppleColor(int, int, int);

// Control
start();
```

<h1>How to create your own game:</h1>
To write your own graphic shell, you must implement abstract methods below. Other methods for game control is complete. And of course you can add your own methods or rewrite existing ones. They were developed to make this process easier.
```
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
