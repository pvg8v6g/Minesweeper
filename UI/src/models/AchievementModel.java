package models;

import achievement.Achievement;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

import java.net.URL;

/**
 * This file has been created by:
 * pvaughn on
 * 5/26/2021 at
 * 11:20
 */
public class AchievementModel extends StackPane {

    // region Constructor

    public AchievementModel(Achievement achievement) {
        this.achievement = achievement;
        this.location = SweepGrid.class.getClassLoader().getResource("images/");
        setAlignment(Pos.CENTER);
        initialize();
    }

    // endregion

    // region Initialize

    private void initialize() {
        createChildren();
        checkCompletion();
        setMargins();
        installTooltip();
    }

    private void createChildren() {
        imageView = new ImageView();
        name = new Label(achievement.name);
        borderStack = new StackPane();
        borderStack.setMinSize(110.0, 60.0);
        vBox = new VBox();
        vBox.setAlignment(Pos.CENTER);
        vBox.getChildren().addAll(imageView, name);
        borderStack.getChildren().add(vBox);
        getChildren().addAll(borderStack);
    }

    private void checkCompletion() {
        if (achievement.completed) {
            image("trophygold");
            borderStack.getStyleClass().add("achievement-model-gold");
        } else {
            image("trophy");
            borderStack.getStyleClass().add("achievement-model");
        }
    }

    private void setMargins() {
        VBox.setMargin(name, new Insets(15.0, 0.0, 0.0, 0.0));
        StackPane.setMargin(vBox, new Insets(10.0));
        StackPane.setMargin(borderStack, new Insets(10.0));
    }

    private void installTooltip() {
        var tip = new Tooltip();
        tip.setText(achievement.description);
        tip.setShowDelay(Duration.millis(300));
        tip.setHideDelay(Duration.millis(1000));
        tip.getStyleClass().add("tooltip-pop");
        Tooltip.install(this, tip);
    }

    // endregion

    // region Image Loading

    private void image(String file) {
        imageView.setImage(new Image(location + file + ".png"));
        imageView.setFitWidth(48.0);
        imageView.setFitHeight(48.0);
    }

    // endregion

    // region Fields

    public final Achievement achievement;
    private final URL location;
    private ImageView imageView;
    private StackPane borderStack;
    private VBox vBox;
    private Label name;

    // endregion

}
