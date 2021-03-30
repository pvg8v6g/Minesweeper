package models;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.LongProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleLongProperty;
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
        timer = new SimpleLongProperty(0);
        minutes = new SimpleIntegerProperty(0);
        seconds = new SimpleIntegerProperty(0);
        installListeners();
        setupTimer();
    }

    // endregion

    // region Listeners

    private void installListeners() {
        timer.addListener((ov, o, n) -> {
            minutes.set((int) (n.longValue() / 60));
            seconds.set((int) (n.longValue() % 60));
        });
    }

    // endregion

    // region Methods

    private void setupTimer() {
        timeline = new Timeline(new KeyFrame(Duration.millis(1000), (ActionEvent action) -> setTimer(getTimer() + 1)));
        timeline.setCycleCount(Timeline.INDEFINITE);
    }

    public void startTimer() {
        timeline.play();
    }

    public void stopTimer() {
        timeline.stop();
    }

    public void resetTimer() {
        setTimer(0);
        timeline.stop();
    }

    // endregion

    // region Properties

    public long getTimer() {
        return timer.get();
    }

    public LongProperty timerProperty() {
        return timer;
    }

    public void setTimer(long timer) {
        this.timer.set(timer);
    }

    public int getMinutes() {
        return minutes.get();
    }

    public int getSeconds() {
        return seconds.get();
    }

    private final LongProperty timer;
    private final IntegerProperty minutes;
    private final IntegerProperty seconds;

    // endregion

    // region Fields

    private Timeline timeline;

    // endregion

}
