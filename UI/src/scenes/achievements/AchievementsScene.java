package scenes.achievements;

import access.Access;
import enumerations.Scenes;
import javafx.fxml.FXML;
import javafx.scene.layout.GridPane;
import models.AchievementModel;
import scenes.controller.Controller;
import windowmanager.WindowManager;

import java.io.IOException;

/**
 * This file has been created by:
 * pvaughn on
 * 5/26/2021 at
 * 11:06
 */
public class AchievementsScene extends Controller {

    // region Constructor

    public AchievementsScene() {

    }

    // endregion

    // region Overrides

    public void start() {
        super.start();
        installModels();
    }

    // endregion

    // region Methods

    private void installModels() {
        for (var i = 0; i < Access.gameData.achievements.size(); i++) {
            var x = i % 5;
            var y = i / 5;

            gridPane.add(new AchievementModel(Access.gameData.achievements.get(i)), x, y);
        }
    }

    // endregion

    // region Actions

    @FXML
    private void returnAction() throws IOException {
        WindowManager.changeScene(Scenes.Scene.Main, null);
    }

    // endregion

    // region Properties


    // endregion

    // region FXML

    @FXML
    private GridPane gridPane;

    // endregion

}
