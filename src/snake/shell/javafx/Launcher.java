package snake.shell.javafx;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import snake.game.SnakeGame;
import snake.game.tool.Direction;
import snake.shell.SnakeGameShell;
import snake.shell.javafx.controller.JavaFXShell;
import snake.shell.javafx.controller.SettingsController;


/**
 * Created by askello on 19.02.2016.
 */

public class Launcher extends Application {

    private Stage mainWindow;
    public static SnakeGame snakeGame;

    public static void main(String args[]) {
        launch(args);
    }

    @Override
    public void init() {

    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        mainWindow = primaryStage;

        snakeGame = new SnakeGame();
        // configureSnakeGame(); // if you want

        FXMLLoader loader = new FXMLLoader(Launcher.class.getResource("view/game.fxml"));
        Parent gameWindow = loader.load();
        JavaFXShell shell = loader.getController();

        //configureShell(shell); // if you want
        shell.setLauncher(this);
        shell.start();

        primaryStage.setTitle("Snake Game");
        primaryStage.setScene(new Scene(gameWindow));
        primaryStage.getIcons().add(new Image("files/images/snake.png"));
        primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent event) {
                System.exit(0);
            }
        });
        primaryStage.show();
    }

    @Override
    public void stop() {

    }

    private void configureSnakeGame() {
        // the same as the constructor
        snakeGame.setGridSize(20, 15);

        // default snake settings
        snakeGame.setStartSnakeLength(2);
        snakeGame.setStartSnakePosition(3, 3);
        snakeGame.setStartSnakeDirection(Direction.RIGHT);

        // set game options
        snakeGame.setSelfEating(true);
        snakeGame.setInfinitySpace(true);
    }

    private void configureShell(SnakeGameShell shell) {
        shell.setSquareSize(40);
        shell.setSpeed(7);

        shell.setBackgroundColor(5, 50, 10);
        shell.setGridLinesColor(255, 216, 0);
        shell.setSnakeColor(20, 30, 150);
        shell.setAppleColor(210, 105, 30);
    }

    public void showSettingsWindow() {
        try {
            // Load the fxml file and create a new stage for the popup dialog.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Launcher.class.getResource("view/settings.fxml"));
            Parent settingsWindow = loader.load();

            // Create the dialog Stage.
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Settings");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(mainWindow);
            Scene scene = new Scene(settingsWindow);
            dialogStage.setScene(scene);

            // Set the person into the controller.
            SettingsController controller = loader.getController();
            controller.setStage(dialogStage);
            controller.setGame(this);

            // Show the dialog and wait until the user closes it
            dialogStage.showAndWait();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void restart() {
        mainWindow.close();
        try {
            start(new Stage());
        } catch(Exception e) {
            System.err.println("fuck!!!");
        }
    }

}