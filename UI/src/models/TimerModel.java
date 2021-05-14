package models;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.event.ActionEvent;
import javafx.util.Duration;

/**
 * This file has been created by:
 * pvaughn on
 * 3/29/2021 at
 * 12:59 PM
 */
public class TimerModel {

    // region Constructor

    public TimerModel() {
        hours = new SimpleIntegerProperty(0);
        minutes = new SimpleIntegerProperty(0);
        seconds = new SimpleIntegerProperty(0);
        timeDisplay = new SimpleStringProperty("00 : 00 : 00");
        installListeners();
        setupTimer();
    }

    // endregion

    // region Listeners

    private void installListeners() {
        secondsProperty().addListener((ov, o, n) -> {
            var i = n.intValue();
            setTimeDisplay(buildTimeDisplay(getHours(), getMinutes(), getSeconds()));
            if (i < 60) return;
            setMinutes(getMinutes() + 1);
            setSeconds(0);
        });

        minutesProperty().addListener((ov, o, n) -> {
            var i = n.intValue();
            if (i < 60) return;
            setHours(getHours() + 1);
            setMinutes(0);
        });
    }

    // endregion

    // region Private Methods

    private void setupTimer() {
        timeline = new Timeline(new KeyFrame(Duration.millis(1000), (ActionEvent action) -> setSeconds(getSeconds() + 1)));
        timeline.setCycleCount(Timeline.INDEFINITE);
    }

    // endregion

    // region Public Methods

    public void startTimer() {
        timeline.play();
    }

    public void stopTimer() {
        timeline.stop();
    }

    public void resetTimer() {
        setHours(0);
        setMinutes(0);
        setSeconds(0);
        timeline.stop();
    }

    public long getTotalTime() {
        var h = getHours();
        var m = getMinutes();
        var s = getSeconds();
        return s + (m * 60L) + (h * 60L * 60L);
    }

    public static String buildTimeDisplay(int hours, int minutes, int seconds) {
        var builder = new StringBuilder();
        if (hours < 10) builder.append("0");
        builder.append(hours);
        builder.append(" : ");
        if (minutes < 10) builder.append("0");
        builder.append(minutes);
        builder.append(" : ");
        if (seconds < 10) builder.append("0");
        builder.append(seconds);

        return builder.toString();
    }

    // endregion

    // region Properties

    public int getHours() {
        return hours.get();
    }

    public IntegerProperty hoursProperty() {
        return hours;
    }

    public void setHours(int hours) {
        this.hours.set(hours);
    }

    public int getMinutes() {
        return minutes.get();
    }

    public IntegerProperty minutesProperty() {
        return minutes;
    }

    public void setMinutes(int minutes) {
        this.minutes.set(minutes);
    }

    public int getSeconds() {
        return seconds.get();
    }

    public IntegerProperty secondsProperty() {
        return seconds;
    }

    public void setSeconds(int seconds) {
        this.seconds.set(seconds);
    }

    public String getTimeDisplay() {
        return timeDisplay.get();
    }

    public StringProperty timeDisplayProperty() {
        return timeDisplay;
    }

    public void setTimeDisplay(String timeDisplay) {
        this.timeDisplay.set(timeDisplay);
    }

    private final IntegerProperty hours;
    private final IntegerProperty minutes;
    private final IntegerProperty seconds;
    private final StringProperty timeDisplay;

    // endregion

    // region Fields

    private Timeline timeline;

    // endregion

}
