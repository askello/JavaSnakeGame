package snake.shell.javafx.controller;

import javafx.application.Application;
import javafx.fxml.FXML;

import javafx.scene.control.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import snake.shell.SnakeGameShell;
import snake.shell.javafx.Launcher;

import java.io.*;
import java.util.Properties;


/**
 * Created by askello on 12.03.2016.
 */
public class SettingsController {

    @FXML
    private TextField gridSizeX;
    @FXML
    private TextField gridSizeY;
    @FXML
    private TextField startSnakeLength;
    @FXML
    private TextField squaresSize;

    @FXML
    private ComboBox startSnakePositionX;
    @FXML
    private ComboBox startSnakePositionY;
    @FXML
    private ComboBox startSnakeDirection;
    @FXML
    private ComboBox speed;

    @FXML
    private CheckBox selfEating;
    @FXML
    private CheckBox infinitySpace;

    @FXML
    private ColorPicker backgroundColor;
    @FXML
    private ColorPicker gridLinesColor;
    @FXML
    private ColorPicker snakeColor;
    @FXML
    private ColorPicker appleColor;

    private Stage stage;
    private Launcher game;

    private Properties gameProperties = new Properties();
    private Properties graphicProperties = new Properties();

    @FXML
    private void initialize() {
        loadGameProperties();
        loadGraphicProperties();
    }

    @FXML
    private void saveButtonHandler() {
        saveGameProperties();
        saveGraphicProperties();
        stage.close();
        game.restart();
    }

    @FXML
    private void cancelButtonHandler() {
        stage.close();
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public void setGame(Application game) {
        this.game = (Launcher) game;
    }

    private void loadGameProperties() {
        try {
            InputStream in = new FileInputStream("src/snake/game/settings.properties");
            gameProperties.load(in);

            // Grid Size TextFields
            gridSizeX.setText(gameProperties.getProperty("GridSizeX"));
            gridSizeY.setText(gameProperties.getProperty("GridSizeY"));

            // Start Snake Length TextField
            startSnakeLength.setText(gameProperties.getProperty("StartSnakeLength"));

            // Start Snake Position ComboBoxes
            for(int i=0; i<=Integer.parseInt(gridSizeX.getText()); i++)
                startSnakePositionX.getItems().add(i);
            startSnakePositionX.getSelectionModel().select(gameProperties.getProperty("StartSnakePositionX"));

            for(int i=0; i<=Integer.parseInt(gridSizeY.getText()); i++)
                startSnakePositionY.getItems().add(i);
            startSnakePositionY.getSelectionModel().select(gameProperties.getProperty("StartSnakePositionY"));

            // Direction ComboBox
            startSnakeDirection.getItems().addAll("UP", "RIGHT", "DOWN", "LEFT");
            startSnakeDirection.getSelectionModel().select(gameProperties.getProperty("StartSnakeDirection"));

            // Self Eating Checkbox
            boolean selfEatingProp = Boolean.parseBoolean(gameProperties.getProperty("SelfEating"));
            selfEating.setSelected(selfEatingProp);

            // Infinity Space Checkbox
            boolean infinitySpaceProp = Boolean.parseBoolean(gameProperties.getProperty("InfinitySpace"));
            infinitySpace.setSelected(infinitySpaceProp);

        } catch (IOException e) {
            System.err.println("error loading");
        }
    }

    private void loadGraphicProperties() {
        try {
            InputStream in = new FileInputStream("src/snake/shell/settings.properties");
            graphicProperties.load(in);

            // Speed
            for(int i=1; i<=10; i++)
                speed.getItems().add(i);
            speed.getSelectionModel().select(graphicProperties.getProperty("Speed"));

            // Square Size
            squaresSize.setText(graphicProperties.getProperty("SquareSize"));

            // Colors
            bindColor(backgroundColor, graphicProperties.getProperty("BackgroundColor"));
            bindColor(gridLinesColor, graphicProperties.getProperty("GridLinesColor"));
            bindColor(snakeColor, graphicProperties.getProperty("SnakeColor"));
            bindColor(appleColor, graphicProperties.getProperty("AppleColor"));
        } catch (IOException e) {
            System.err.println("error loading");
        }
    }

    private void saveGameProperties() {
        OutputStream output = null;

        try {
            output = new FileOutputStream("src/snake/game/settings.properties");

            // set the properties value
            gameProperties.setProperty("GridSizeX", gridSizeX.getText());
            gameProperties.setProperty("GridSizeY", gridSizeY.getText());

            gameProperties.setProperty("StartSnakeLength", startSnakeLength.getText());
            gameProperties.setProperty("StartSnakePositionX", String.valueOf(startSnakePositionX.getValue()));
            gameProperties.setProperty("StartSnakePositionY", String.valueOf(startSnakePositionY.getValue()));
            gameProperties.setProperty("StartSnakeDirection", (String)startSnakeDirection.getValue());

            gameProperties.setProperty("SelfEating", String.valueOf(selfEating.isSelected()));
            gameProperties.setProperty("InfinitySpace", String.valueOf(infinitySpace.isSelected()));

            // save properties to project root folder
            gameProperties.store(output, null);

        } catch (IOException io) {
            io.printStackTrace();
        } finally {
            if (output != null) {
                try {
                    output.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void saveGraphicProperties() {
        OutputStream output = null;

        try {
            output = new FileOutputStream("src/snake/shell/settings.properties");

            // set the properties value
            graphicProperties.setProperty("Speed", String.valueOf(speed.getValue()));
            graphicProperties.setProperty("SquareSize", squaresSize.getText());

            graphicProperties.setProperty("BackgroundColor", getRgbString(backgroundColor));
            graphicProperties.setProperty("GridLinesColor", getRgbString(gridLinesColor));
            graphicProperties.setProperty("SnakeColor", getRgbString(snakeColor));
            graphicProperties.setProperty("AppleColor", getRgbString(appleColor));

            // save properties to project root folder
            graphicProperties.store(output, null);

        } catch (IOException io) {
            io.printStackTrace();
        } finally {
            if (output != null) {
                try {
                    output.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void bindColor(ColorPicker colorPicker, String rgbStr) {
        String[] rgbStrArr = null;
        int[] rgbArr = new int[3];

        rgbStrArr = String.valueOf(rgbStr).split(",");
        for(int i=0; i<3; i++)
            rgbArr[i] = Integer.parseInt(rgbStrArr[i]);

        colorPicker.setValue(Color.rgb(rgbArr[0],rgbArr[1],rgbArr[2],1));
    }

    private String getRgbString(ColorPicker colorPicker) {
        String rgbStr = (int)(colorPicker.getValue().getRed() * 255) + "," +
                        (int)(colorPicker.getValue().getGreen() * 255) + "," +
                        (int)(colorPicker.getValue().getBlue() * 255);
        return rgbStr;
    }
}
