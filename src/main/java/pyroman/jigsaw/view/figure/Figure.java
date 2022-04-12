package pyroman.jigsaw.view.figure;

import javafx.geometry.Point2D;
import javafx.scene.layout.GridPane;
import pyroman.jigsaw.utils.ParsingUtils;

import java.util.List;

public class Figure extends GridPane {

    public Figure(String piecesCoordinatesData) {
        initPieces(piecesCoordinatesData);
    }

    private void initPieces(String piecesCoordinatesData) {

        List<Point2D> piecesCoordinates = ParsingUtils.getCoordinatesFromString(piecesCoordinatesData);

        for (Point2D coordinate : piecesCoordinates) {
            add(new FigurePiece(piecesCoordinatesData), (int) coordinate.getY(), (int) coordinate.getX());
        }
    }
}
