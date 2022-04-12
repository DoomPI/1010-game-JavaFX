package pyroman.jigsaw.view.figure;

import javafx.scene.layout.Pane;

import java.io.File;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ThreadLocalRandom;

public class FigureSpawner extends Pane {

    public void spawnPiece() {
        getChildren().clear();
        Figure figure = new Figure(getRandomFigureFromFile());
        getChildren().addAll(figure);
    }

    private String getRandomFigureFromFile() {
        File path = new File(Objects.requireNonNull(getClass().getResource("/figures.txt")).getFile());
        try {
            List<String> allFiguresData = Files.readAllLines(path.toPath());
            return allFiguresData.get(ThreadLocalRandom.current().nextInt(0, allFiguresData.size() - 1));
        } catch (IOException exception) {
            throw new UncheckedIOException(exception);
        }
    }
}
