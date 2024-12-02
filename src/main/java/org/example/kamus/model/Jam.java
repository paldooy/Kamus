package org.example.kamus.model;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class Jam implements Gimmick {
    private Label timeLabel;

    @Override
    public void display(VBox container) {

        StackPane clockPane = new StackPane();
        timeLabel = new Label();
        timeLabel.setTextFill(Color.WHITE);
        timeLabel.setFont(Font.font("Digital-7", 60));

        clockPane.setStyle("-fx-background-color: #e89f00; -fx-padding: 20;");
        clockPane.getChildren().add(timeLabel);

        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(1), e -> updateClock()));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();

        container.getChildren().clear();
        container.getChildren().add(clockPane);
    }

    private void updateClock() {
        LocalTime now = LocalTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
        timeLabel.setText(now.format(formatter));
    }
}