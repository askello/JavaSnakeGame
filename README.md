## Introducing
Java Snake Game is the most popular project for java beginers. My goal was to create java snake game, which can be launched on different platforms, for example swing, javafx, android and so on. Now, I have done the game, abstract class for connecting this game for graphic shells and of course two graphic shells (for Swing and JavaFX), which appear the game.

## Launch the game
You can use native launchers for your OS ("native-apps" folder), or you can launch the game in your IDE by running main methods in files below:
#### JavaFX:
```
src/snake/shell/javafx/Launcher.java
```
#### Swing:
```
src/snake/shell/swing/Launcher.java
```

## How to use
#### Snake Game Settigns:
```java
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
#### Snake Game Control:
```java
// game control
start();
doMove(Direction);
restart();

// game output
getSnake();
getApple();
getScore();
```
#### Sell:
```java
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

## How to create your own game:
To write your own graphic shell, you must implement abstract methods below. Other methods for game control is complete. And of course you can add your own methods or rewrite existing ones. They were developed to make this process easier.
```java
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
