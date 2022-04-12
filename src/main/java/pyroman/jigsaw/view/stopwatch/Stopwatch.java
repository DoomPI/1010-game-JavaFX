package pyroman.jigsaw.view.stopwatch;

import javafx.animation.AnimationTimer;
import javafx.scene.control.Label;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class Stopwatch extends AnimationTimer {

    private LocalDateTime startTimestamp;

    private final Label stopWatchLabel;

    private final DateTimeFormatter dateTimeFormatter;

    public Stopwatch(Label stopWatchLabel) {
        this.stopWatchLabel = stopWatchLabel;
        dateTimeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");
    }

    @Override
    public void start() {
        startTimestamp = LocalDateTime.now();
        super.start();
    }

    @Override
    public void handle(long l) {
        LocalDateTime currentTime = LocalDateTime.now();
        long secondsDuration = Duration.between(startTimestamp, currentTime).toSeconds();
        stopWatchLabel.setText(dateTimeFormatter.format(LocalTime.of(0, 0, 0).plusSeconds(secondsDuration)));
    }
}
