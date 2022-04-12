module pyroman.jigsaw {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.media;


    opens pyroman.jigsaw to javafx.fxml;
    exports pyroman.jigsaw;
    exports pyroman.jigsaw.view.controller;
    opens pyroman.jigsaw.view.controller to javafx.fxml;
}