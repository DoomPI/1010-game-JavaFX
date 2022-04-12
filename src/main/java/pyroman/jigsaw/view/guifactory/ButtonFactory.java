package pyroman.jigsaw.view.guifactory;

import javafx.scene.control.Button;
import javafx.scene.layout.Background;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;

import java.util.Objects;

public class ButtonFactory {

    public Button makeButton(String text) {
        Button button = new Button();

        button.setText(text);

        setButtonShape(button);

        button.setFont(Font.loadFont(Objects.requireNonNull(getClass().getResource("/fonts/12484.otf")).toString(), 24));

        button.setBackground(Background.fill(Color.GREY));

        setEventHandlers(button);

        return button;
    }

    private void setButtonShape(Button button) {
        Rectangle rectangle = new Rectangle(48, 24);
        rectangle.setArcHeight(10);
        rectangle.setArcWidth(10);
        button.setShape(rectangle);
    }

    private void setEventHandlers(Button button) {
        button.setOnMouseEntered(event -> button.setBackground(Background.fill(Color.LIGHTGREY)));
        button.setOnMouseExited(event -> button.setBackground(Background.fill(Color.GREY)));
    }
}
