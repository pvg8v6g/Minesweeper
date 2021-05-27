package scenes.achievementcompleted;

import achievement.Achievement;
import javafx.animation.FadeTransition;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;
import models.SweepGrid;
import scenes.controller.Controller;

import java.net.URL;

/**
 * This file has been created by:
 * pvaughn on
 * 5/26/2021 at
 * 14:19
 */
public class AchievementCompleted extends Controller<Achievement> {

    // region Constructor

    public AchievementCompleted() {
        this.location = SweepGrid.class.getClassLoader().getResource("images/");
        achievementName = new SimpleStringProperty();
        trophyImage = new SimpleObjectProperty<>();
    }

    // endregion

    // region Overrides

    public void start() {
        super.start();
        setTrophyImage(new Image(location + "trophygold" + ".png"));
        setAchievementName(variable.name);
//        var timeline = new Timeline(new KeyFrame(Duration.millis(3000), actionEvent -> {
//            var transition = new FadeTransition(Duration.millis(500), root);
//            transition.setFromValue(1.0);
//            transition.setToValue(0.0);
//            transition.setCycleCount(1);
//            transition.setOnFinished(ae -> root.getScene().getWindow().hide());
//            transition.play();
//        }));
//        timeline.setCycleCount(1);
//        timeline.play();
    }

    // endregion

    // region Properties

    public String getAchievementName() {
        return achievementName.get();
    }

    public StringProperty achievementNameProperty() {
        return achievementName;
    }

    public void setAchievementName(String achievementName) {
        this.achievementName.set(achievementName);
    }

    public Image getTrophyImage() {
        return trophyImage.get();
    }

    public ObjectProperty<Image> trophyImageProperty() {
        return trophyImage;
    }

    public void setTrophyImage(Image trophyImage) {
        this.trophyImage.set(trophyImage);
    }

    private final StringProperty achievementName;
    private final ObjectProperty<Image> trophyImage;

    // endregion

    // region Fields

    private final URL location;

    // endregion

}
