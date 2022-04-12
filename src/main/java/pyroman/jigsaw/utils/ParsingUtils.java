package pyroman.jigsaw.utils;

import javafx.geometry.Point2D;

import java.util.ArrayList;
import java.util.List;

public class ParsingUtils {

    public static List<Point2D> getCoordinatesFromString(String input) {
        List<Point2D> output = new ArrayList<>();
        String[] pairs = input.split(";");

        for (String pair : pairs) {
            output.add(new Point2D(Integer.parseInt(pair.split(",")[0]),
                    Integer.parseInt(pair.split(",")[1])));
        }

        return output;
    }
}
