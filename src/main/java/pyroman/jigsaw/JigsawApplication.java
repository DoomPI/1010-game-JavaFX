package pyroman.jigsaw;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class JigsawApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/board-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Плиткострой");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
        stage.getIcons().add(new Image(Objects.requireNonNull(getClass().getResource("/view/textures/icon.jpg")).toString()));
    }

    public static void main(String[] args) {
        launch();
    }
}