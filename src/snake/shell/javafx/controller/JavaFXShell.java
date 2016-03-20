package snake.shell.javafx.controller;

import javafx.application.Platform;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.event.EventHandler;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;

import snake.game.tool.Direction;
import snake.shell.SnakeGameShell;
import snake.shell.javafx.Launcher;


public class JavaFXShell extends SnakeGameShell {
    @FXML
    private Button playPauseButton;
    @FXML
    private Button restartButton;
    @FXML
    private Label scoreLabel;
    @FXML
    private Canvas gameArea;
    private GraphicsContext gc;

    @FXML
    private Menu topMenuGame;
    @FXML
    private Menu topMenuInfo;
    @FXML
    private ImageView playPauseButtonImage;

    private Launcher launcher;

    Alert infoModal = new Alert(Alert.AlertType.INFORMATION);
    Alert errorModal = new Alert(Alert.AlertType.WARNING);


    @FXML
    private void initialize() {
        setSnakeGame(Launcher.snakeGame);

        // Configure canvas (game area)
        gameArea.setWidth(gameAreaWidth());
        gameArea.setHeight(gameAreaHeight());
        gc = gameArea.getGraphicsContext2D();

        // Define key control
        gameArea.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                String key = event.getCode().toString();

                     if(key.equals("UP"))    snakeDirection = Direction.UP;
                else if(key.equals("RIGHT")) snakeDirection = Direction.RIGHT;
                else if(key.equals("DOWN"))  snakeDirection = Direction.DOWN;
                else if(key.equals("LEFT"))  snakeDirection = Direction.LEFT;
            }
        });

        // Allow key control only for canvas
        gameArea.setFocusTraversable(true);
        playPauseButton.setFocusTraversable(false);
        restartButton.setFocusTraversable(false);

        // Pause game if menu active
        topMenuGame.setOnShowing(new EventHandler<Event>() {
            @Override
            public void handle(Event event) {
                pause();
            }
        });
        topMenuInfo.setOnShowing(new EventHandler<Event>() {
            @Override
            public void handle(Event event) {
                pause();
            }
        });

        // Set Info Modal Data
        infoModal.setTitle("About");
        infoModal.setHeaderText("Java Snake Game v0.1");
        infoModal.setContentText("Author: Oleksii Korpan (askello) \nEmail: oleksii.korpan@gmail.com \nRelease: 20 March 2016");

        // Load Colors from "settings.properties"
        loadColors();
    }

    @FXML
    private void playPauseButtonHandler() {
        if(isAnimationPaused())
            play();
        else
            pause();
    }

    private void play() {
        if(isAnimationPaused()) {
            startAnimation();
            playPauseButton.setText("pause");
            playPauseButtonImage.setImage(new Image("files/images/pause.png"));
        }
    }

    private void pause() {
        if(!isAnimationPaused()) {
            stopAnimation();
            playPauseButton.setText("play");
            playPauseButtonImage.setImage(new Image("files/images/play.png"));
        }
    }

    @FXML
    private void restartButtonHandler() {
        restart();
        playPauseButton.setText("pause");
    }

    @FXML
    private void settingsButtonHandler() {
        pause();
        launcher.showSettingsWindow();
    }

    @FXML
    private void exitButtonHandler() {
        System.exit(0);
    }

    @FXML
    private void aboutButtonHandler() {
        infoModal.showAndWait();
    }

    @Override
    protected void drawWindow() {

    }

    @Override
    protected void drawBackground() {
        gc.fillRect(0, 0, gameAreaWidth(), gameAreaHeight());
    }

    @Override
    protected void drawGrid() {
        gc.setLineWidth(1.0);
        // vertical lines
        for(int i=1; i<snakeGame.getGridSizeX(); i++)
            gc.strokeLine(i * squareSize + i - 0.5, 0, i * squareSize + i - 0.5, gameAreaHeight());
        // horizontal lines
        for(int i=1; i<snakeGame.getGridSizeY(); i++)
            gc.strokeLine(0, i * squareSize + i - 0.5, gameAreaWidth(), i * squareSize + i - 0.5);
    }

    @Override
    protected void drawPoint(int x, int y) {
        gc.fillRect(x*squareSize+x, y*squareSize+y, squareSize, squareSize);
    }

    @Override
    public void setBackgroundColor(int red, int green, int blue) {
        backgroundColor = Color.rgb(red, green, blue);
    }
    @Override
    public void setGridLinesColor(int red, int green, int blue) {
        gridLinesColor = Color.rgb(red, green, blue);
    }
    @Override
    public void setSnakeColor(int red, int green, int blue) {
        snakeColor = Color.rgb(red, green, blue);
    }
    @Override
    public void setAppleColor(int red, int green, int blue) {
        appleColor = Color.rgb(red, green, blue);
    }

    @Override
    protected void setDrawingColor(Object color) {
        gc.setFill((Color)color);
        gc.setStroke((Color) color);
    }

    @Override
    protected void showScore(final int score) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                scoreLabel.setText(score + "");
            }
        });
    }

    @Override
    protected void showError(String message) {
        errorModal.setTitle("What a pain...");
        errorModal.setHeaderText("Game Over");
        errorModal.setContentText(message);

        errorModal.showAndWait();
    }

    public void setLauncher(Launcher launcher) {
        this.launcher = launcher;
    }

    private void loadColors() {

    }
}
