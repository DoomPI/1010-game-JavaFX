package pyroman.jigsaw.view.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import javafx.util.Duration;
import pyroman.jigsaw.view.figure.FigureSpawner;
import pyroman.jigsaw.view.guifactory.ButtonFactory;
import pyroman.jigsaw.view.stopwatch.Stopwatch;
import pyroman.jigsaw.view.tile.Tile;

import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class BoardViewController implements Initializable {

    private Button buttonNewGame;
    private Button buttonEndGame;

    private MediaPlayer mediaPlayer;

    private FigureSpawner figureSpawningField;

    private Stopwatch stopwatch;

    private Label stopwatchLabel;

    public static Label figureCountLabel;
    public static int figuresCount = 0;

    @FXML
    private AnchorPane rootPane;

    @FXML
    private GridPane gridPane;

    private void onButtonExitClick() {
        Stage stage = (Stage) rootPane.getScene().getWindow();
        stage.close();
    }

    private void onButtonNewGameClick() {
        buttonNewGame.setDisable(true);
        buttonEndGame.setDisable(false);

        figureSpawningField.spawnPiece();

        stopwatch = new Stopwatch(stopwatchLabel);
        stopwatch.start();

        figuresCount = 0;

        figureCountLabel.setText("Поставлено фигур: " + figuresCount);

        playMainTheme();
    }

    private void onButtonEndGameClick() {
        buttonEndGame.setDisable(true);
        buttonNewGame.setDisable(false);

        refreshGridPane();
        figureSpawningField.getChildren().clear();

        mediaPlayer.stop();

        stopwatch.stop();
    }

    private void refreshGridPane() {
        gridPane.getChildren().clear();

        for (int row = 0; row < 9; ++row) {
            for (int column = 0; column < 9; ++column) {
                gridPane.add(new Tile(row, column), column, row);
            }
        }
    }

    private void playMainTheme() {
        Media music = new Media((Objects.requireNonNull(getClass().getResource("/sounds/sobyanin.mp3")).toString()));
        mediaPlayer = new MediaPlayer(music);
        mediaPlayer.setVolume(0.25);
        mediaPlayer.play();
        mediaPlayer.setOnEndOfMedia(() -> mediaPlayer.seek(Duration.ZERO));
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initAnchorPane();
        initFigureSpawningField();
        initGridPane();
        initButtons();
        initStopWatchLabel();
        initFigureCountLabel();
    }

    private void initFigureCountLabel() {
        figureCountLabel = new Label();

        figureCountLabel.setPrefWidth(250);
        figureCountLabel.setPrefHeight(32);

        Rectangle rectangle = new Rectangle(48, 48);
        rectangle.setArcWidth(4);
        rectangle.setArcHeight(4);
        figureCountLabel.setShape(rectangle);

        figureCountLabel.setFont(Font.font("Times New Roman", 24));

        figureCountLabel.setBackground(Background.fill(Color.GREY));
        figureCountLabel.setTextAlignment(TextAlignment.CENTER);

        figureCountLabel.setLayoutX(316);
        figureCountLabel.setLayoutY(750);

        figureCountLabel.setText("Поставлено фигур: 0");

        rootPane.getChildren().addAll(figureCountLabel);
    }

    private void initButtons() {
        ButtonFactory buttonFactory = new ButtonFactory();
        buttonNewGame = buttonFactory.makeButton("Новая игра");
        buttonNewGame.setLayoutX(752);
        buttonNewGame.setLayoutY(388.8);
        buttonNewGame.setOnAction(actionEvent -> onButtonNewGameClick());


        buttonEndGame = buttonFactory.makeButton("Завершить игру");
        buttonEndGame.setLayoutX(752);
        buttonEndGame.setLayoutY(432);
        buttonEndGame.setOnAction(actionEvent -> onButtonEndGameClick());
        buttonEndGame.setDisable(true);

        Button buttonExit = buttonFactory.makeButton("Выход");
        buttonExit.setLayoutX(752);
        buttonExit.setLayoutY(475.2);
        buttonExit.setOnAction(actionEvent -> onButtonExitClick());

        rootPane.getChildren().addAll(buttonNewGame, buttonEndGame, buttonExit);

    }

    private void initGridPane() {
        refreshGridPane();

        gridPane.setBackground(new Background(new BackgroundImage(
                new Image(Objects.requireNonNull(getClass().getResource("/view/textures/concrete_background.jpg")).toString()),
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.CENTER,
                BackgroundSize.DEFAULT)));


        ((Pane) gridPane.getParent()).setBackground(new Background(new BackgroundImage(
                new Image(Objects.requireNonNull(getClass().getResource("/view/textures/stone_frame.png")).toString()),
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.CENTER,
                BackgroundSize.DEFAULT)));
    }

    private void initAnchorPane() {
        rootPane.setBackground(new Background(new BackgroundImage(
                new Image(Objects.requireNonNull(getClass().getResource("/view/textures/background.jpg")).toString()),
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.DEFAULT,
                BackgroundSize.DEFAULT)));
    }

    private void initFigureSpawningField() {
        figureSpawningField = new FigureSpawner();
        rootPane.getChildren().addAll(figureSpawningField);

        figureSpawningField.setLayoutX(768);
        figureSpawningField.setLayoutY(120);
        figureSpawningField.setPrefWidth(192);
        figureSpawningField.setPrefHeight(192);
    }

    private void initStopWatchLabel() {
        stopwatchLabel = new Label();

        stopwatchLabel.setPrefWidth(136);
        stopwatchLabel.setPrefHeight(32);

        Rectangle rectangle = new Rectangle(48, 48);
        rectangle.setArcWidth(4);
        rectangle.setArcHeight(4);
        stopwatchLabel.setShape(rectangle);

        stopwatchLabel.setFont(Font.font("Times New Roman", 24));

        stopwatchLabel.setBackground(Background.fill(Color.GREY));
        stopwatchLabel.setTextAlignment(TextAlignment.CENTER);

        stopwatchLabel.setLayoutX(316);
        stopwatchLabel.setLayoutY(688);

        rootPane.getChildren().addAll(stopwatchLabel);
    }
}
