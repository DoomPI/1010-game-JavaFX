package pyroman.jigsaw.view.figure;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.*;

import java.io.File;
import java.util.Objects;
import java.util.concurrent.ThreadLocalRandom;

public class FigurePiece extends ImageView {

    public FigurePiece(String pieceCoordinatesData) {
        setRandomTexture();
        setFitWidth(64);
        setFitHeight(64);

        setOnDragDetected(event -> {
            Dragboard dragboard = startDragAndDrop(TransferMode.MOVE);
            ClipboardContent content = new ClipboardContent();
            content.putString(pieceCoordinatesData);
            dragboard.setContent(content);
            event.consume();
        });
    }

    private void setRandomTexture() {
        int numberOfFigures = Objects.requireNonNull(
                new File(Objects.requireNonNull(
                        getClass().getResource("/view/textures/figure")).getFile()).listFiles()).length;

        int randomNumber = Math.abs(ThreadLocalRandom.current().nextInt()) % numberOfFigures + 1;
        setImage(new Image(Objects.requireNonNull(getClass().getResource(
                "/view/textures/figure/stone_tile_" + randomNumber + ".jpg")).toString()));
    }


}
