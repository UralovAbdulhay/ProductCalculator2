package sample.Controllers;

import eu.hansolo.medusa.Clock;
import eu.hansolo.medusa.ClockBuilder;
import javafx.animation.AnimationTimer;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.RadialGradient;
import javafx.scene.paint.Stop;

import java.net.URL;
import java.util.Locale;
import java.util.ResourceBundle;

public class SoatContr implements Initializable {

    @FXML
    private HBox hBox;

    private Clock clock;
    private long lastTimerCall;
    private AnimationTimer timer;



    @Override
    public void initialize(URL location, ResourceBundle resources) {
        EditTovar.ishla = false;

        RadialGradient gradient1 = new RadialGradient(0,
                .1,
                100,
                100,
                20,
                false,
                CycleMethod.NO_CYCLE,
                new Stop(0, Color.RED),
                new Stop(1, Color.BLACK));

        hBox.setStyle("-fx-background-color: radial-gradient(" +
                "focus-angle 0.0deg, " +
                "focus-distance 10.0% , " +
                "center 50.0% 50.0%," +
                " radius 60%," +
                " #c8ffa5 50.0%," +
                " #ffffff 150.0%)"
        );

        clock = ClockBuilder.create()
                .skinType(Clock.ClockSkinType.SLIM)
                .backgroundPaint(Color.web("#a31d57"))
                .borderPaint(Color.web("#e09a16"))
                .borderWidth(4.7)
                .dateColor(Color.YELLOWGREEN)
                .secondColor(Color.web("#d2fa7d"))
                .dateVisible(true)
                .discreteHours(false)
                .discreteMinutes(false)
                .discreteSeconds(false)
                .hourColor(Color.CORAL)
                .minuteColor(Color.BLUE)
                .hourTickMarkColor(Color.AQUA)
                .hourTickMarksVisible(true)
                .knobColor(Color.MAGENTA)
                .running(true)
                .prefSize(400, 400)
                .locale(resources.getLocale())
                .build();
        clock.setSecondsVisible(true);

        lastTimerCall = System.nanoTime();

        timer = new AnimationTimer() {
            @Override
            public void handle(long now) {

                if (now > lastTimerCall + 5_000_000_000L) {

                    clock.setSecondColor(Color.web("#87d1ff"));

                    if (now > lastTimerCall + 10_000_000_000L) {

                        clock.setSecondColor(Color.web("#d2fa7d"));
                        lastTimerCall = now;
                    }
                }
            }
        };


        hBox.getChildren().clear();
        hBox.getChildren().add(clock);
//        HBox.setHgrow(clock, Priority.ALWAYS);


        timer.start();

    }
}
