package scenes.main;

import access.Access;
import enumerations.Scenes;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.fxml.FXML;
import models.GameModel;
import models.TimerModel;
import scenes.controller.Controller;
import windowmanager.WindowManager;

import java.io.IOException;

/**
 * This file has been created by:
 * pvaughn on
 * 3/18/2021 at
 * 11:19 AM
 */
public class MainScene extends Controller {

    // region Constructor

    public MainScene() {
        averageTime = new SimpleStringProperty("None");
    }

    // endregion

    // region Overrides

    public void start() {
        super.start();
        var games = Access.gameData.gamesFinished;
        var totalTime = Access.gameData.totalTimePlayed;
        long h;
        long m;
        long s;
        if (games == 0) {
            h = 0;
            m = 0;
            s = 0;
        } else {
            var avg = totalTime / games;
            h = avg / 3600;
            m = (avg / 60) % 60;
            s = avg % 60;
        }

        var display = TimerModel.buildTimeDisplay((int) h, (int) m, (int) s);
        setAverageTime("Avg Time:     " + display);
    }


    // endregion

    // region Actions

    @FXML
    private void achievementsAction() throws IOException {
        WindowManager.changeScene(Scenes.Scene.Achievements, null);
    }

    @FXML
    private void easyAction() throws IOException {
        WindowManager.changeScene(Scenes.Scene.Game, new GameModel(8, 8, 10));
    }

    @FXML
    private void mediumAction() throws IOException {
        WindowManager.changeScene(Scenes.Scene.Game, new GameModel(20, 20, 70));
    }

    @FXML
    private void hardAction() throws IOException {
        WindowManager.changeScene(Scenes.Scene.Game, new GameModel(20, 20, 90));
    }

    // endregion

    // region Properties

    public String getAverageTime() {
        return averageTime.get();
    }

    public StringProperty averageTimeProperty() {
        return averageTime;
    }

    public void setAverageTime(String averageTime) {
        this.averageTime.set(averageTime);
    }

    private final StringProperty averageTime;

    // endregion

}
