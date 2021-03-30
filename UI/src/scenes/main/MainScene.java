package scenes.main;

import enumerations.Scenes;
import javafx.fxml.FXML;
import models.GameModel;
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

    // region Overrides

    public void start() {
        super.start();
    }


    // endregion

    // region Actions

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

}
