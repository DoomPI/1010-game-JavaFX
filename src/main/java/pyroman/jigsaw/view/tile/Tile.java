package pyroman.jigsaw.view.tile;

import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.input.*;
import javafx.scene.layout.GridPane;
import javafx.scene.media.AudioClip;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.StrokeType;
import pyroman.jigsaw.view.controller.BoardViewController;
import pyroman.jigsaw.view.figure.FigurePiece;
import pyroman.jigsaw.view.figure.FigureSpawner;
import pyroman.jigsaw.utils.ParsingUtils;

import java.io.File;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ThreadLocalRandom;

import static pyroman.jigsaw.view.controller.BoardViewController.figuresCount;

public class Tile extends Rectangle {

    public static final int TILE_SIZE = 64;

    private final int row;
    private final int column;

    public Tile(int row, int column) {
        this.row = row;
        this.column = column;
        setScale();
    }

    private void setScale() {
        setWidth(TILE_SIZE);
        setHeight(TILE_SIZE);
        setArcHeight(8);
        setArcWidth(8);
        setFill(Color.TRANSPARENT);

        setStrokeOptions();
    }

    private void setStrokeOptions() {
        setStrokeType(StrokeType.INSIDE);
        setStrokeWidth(3);

        setOnDragOver(this::handleDragOver);
        setOnDragExited(this::handleMouseExit);
        setOnDragDropped(this::handleDragDrop);
    }

    private void handleDragOver(DragEvent event) {
        if (event.getGestureSource().getClass() == FigurePiece.class
                && event.getDragboard().hasString()) {

            List<Point2D> coordinates = ParsingUtils.getCoordinatesFromString(event.getDragboard().getString());

            int originRow = (int) coordinates.get(0).getX();
            int originColumn = (int) coordinates.get(0).getY();

            GridPane parent = (GridPane) getParent();

            for (Point2D coordinate : coordinates) {
                int gridRow = row + ((int) coordinate.getX() - originRow);
                int gridColumn = column + ((int) coordinate.getY() - originColumn);

                if (gridRow < 0 || gridRow >= parent.getRowCount()
                        || gridColumn < 0 || gridColumn >= parent.getColumnCount()) {
                    markTilesRed(coordinates);
                    return;
                }

                for (Node node : parent.getChildren()) {
                    if (node instanceof Tile gridTile) {
                        if (GridPane.getRowIndex(gridTile) == gridRow
                                && GridPane.getColumnIndex(gridTile) == gridColumn) {
                            if (gridTile.getFill() == Color.TRANSPARENT) {
                                gridTile.setStroke(Color.GREEN);
                            } else {
                                markTilesRed(coordinates);
                                return;
                            }
                        }
                    }
                }
            }

            event.acceptTransferModes(TransferMode.MOVE);
        }
    }

    private void handleMouseExit(DragEvent event) {
        GridPane parent = (GridPane) getParent();
        for (Node node : parent.getChildren()) {
            if (node instanceof Tile) {
                ((Tile) node).setStroke(null);
            }
        }
    }

    private void handleDragDrop(DragEvent event) {
        Dragboard dragboard = event.getDragboard();
        if (dragboard.hasString()) {
            List<Point2D> coordinates = ParsingUtils.getCoordinatesFromString(event.getDragboard().getString());

            int originRow = (int) coordinates.get(0).getX();
            int originColumn = (int) coordinates.get(0).getY();

            GridPane parent = (GridPane) getParent();

            for (Point2D coordinate : coordinates) {
                int gridRow = row + ((int) coordinate.getX() - originRow);
                int gridColumn = column + ((int) coordinate.getY() - originColumn);

                for (Node node : parent.getChildren()) {
                    if (node instanceof Tile gridTile) {
                        if (GridPane.getRowIndex(gridTile) == gridRow
                                && GridPane.getColumnIndex(gridTile) == gridColumn) {
                            ((Tile) node).setRandomTexture();
                        }
                    }
                }
            }

            BoardViewController.figureCountLabel.setText("Поставлено фигур: " + ++figuresCount);

            playRandomPlacementSound();

            ((FigureSpawner) (((FigurePiece) event.getGestureSource()).getParent()).getParent()).spawnPiece();
        }
    }


    public void setRandomTexture() {
        int numberOfFigures = Objects.requireNonNull(
                new File(Objects.requireNonNull(
                        getClass().getResource("/view/textures/figure")).getFile()).listFiles()).length;

        int randomNumber = Math.abs(ThreadLocalRandom.current().nextInt()) % numberOfFigures + 1;
        setFill(new ImagePattern(new Image(Objects.requireNonNull(getClass().getResource(
                "/view/textures/figure/stone_tile_" + randomNumber + ".jpg")).toString())));
    }

    private void markTilesRed(List<Point2D> coordinates) {
        GridPane parent = (GridPane) getParent();

        for (Point2D coord : coordinates) {
            int originRow = (int) coordinates.get(0).getX();
            int originColumn = (int) coordinates.get(0).getY();

            int gridRow = this.row + ((int) coord.getX() - originRow);
            int gridColumn = this.column + ((int) coord.getY() - originColumn);

            for (Node node : parent.getChildren()) {
                if (node instanceof Tile gridTile) {
                    if (GridPane.getRowIndex(gridTile) == gridRow
                            && GridPane.getColumnIndex(gridTile) == gridColumn) {
                        gridTile.setStroke(Color.RED);
                    }
                }
            }
        }
    }

    private void playRandomPlacementSound() {
        int randomNumber = Math.abs(ThreadLocalRandom.current().nextInt()) % 3 + 1;
        new AudioClip(Objects.requireNonNull(getClass().getResource("/sounds/tile/tile_sound_" + randomNumber + ".mp3"))
                .toString()).play();
    }
}
