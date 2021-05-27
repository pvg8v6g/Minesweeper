package windowmanager;

import access.Access;
import encryption.Encryption;
import enumerations.Scenes;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.SequentialTransition;
import javafx.animation.Timeline;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import javafx.util.Pair;
import list.Enumerable;
import scenes.controller.Controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * This file has been created by:
 * pvaughn on
 * 3/18/2021 at
 * 8:46 AM
 */
public class WindowManager {

    // region Initialization

    public static void initialize() throws IOException, InterruptedException {
        Access.buildDataAccess();
        installVariables();
        createRoot();
        createScene();
        createWindow();
    }

    // endregion

    // region Create Window Components

    private static void installVariables() {
        var f = WindowManager.class.getClassLoader().getResource("scenes/");
        var c = WindowManager.class.getClassLoader().getResource("css/");
        var i = WindowManager.class.getClassLoader().getResource("images/");
        if (f != null) fxml = f.toString();
        if (c != null) css = c.toString();
        if (i != null) images = i.toString();
    }

    private static void createRoot() {
        root = new VBox();
        root.setAlignment(Pos.CENTER);
    }

    private static void createScene() throws IOException {
        scene = new Scene(root);
        loadTheme(scene, "dark.css");
        changeScene(Scenes.Scene.Main, null);
    }

    private static void createWindow() {
        stage.setScene(scene);
        stage.setMaximized(true);
        stage.titleProperty().bind(titleProperty());
        setTitle("Minesweeper");
        stage.setOnCloseRequest(windowEvent -> {
            try {
                var t = Access.gameData;
                Encryption.encryptData(Access.gameData, new FileOutputStream(new File(Access.gameFolder + "Data\\GameData.data")));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        });
        stage.show();
    }

    // endregion

    // region Stylesheet Loading

    public static void loadTheme(Scene scene, String stylesheet) {
        var defaultStyle = css + "style.css";
        var theme = css + stylesheet;
        scene.getStylesheets().setAll(defaultStyle, theme);
    }

    // endregion

    // region FXML

    private static FXMLLoader fxmlLoader(String filename) throws MalformedURLException {
        var url = new URL(fxml + filename + ".fxml");
        return new FXMLLoader(url);
    }

    private static <T> Pair<Parent, Controller<T>> getControllerParent(Scenes.Scene newScene) throws IOException {
        var loader = fxmlLoader(newScene.getFxml());
        Parent parent = loader.load();
        if (parent == null) return null;
        Controller<T> controller = loader.getController();
        return new Pair<>(parent, controller);
    }

    // endregion

    // region Scenes

    public static <T> void changeScene(Scenes.Scene newScene, T variable) throws IOException {
        var pair = initializeController(newScene, variable);
        if (pair == null) return;
        root.getChildren().setAll(pair.getKey());
    }

    public static <T> Stage showPopup(Stage owner, Scenes.Scene newScene, T variable, StageStyle stageStyle) throws IOException {
        var pair = initializeController(newScene, variable);
        if (pair == null) return null;
        var popup = new Stage();
        popup.initOwner(owner);
        popup.initStyle(stageStyle);
        popup.initModality(Modality.NONE);
        popup.setAlwaysOnTop(true);
        var popupScene = new Scene(pair.getKey());
        if (stageStyle == StageStyle.TRANSPARENT) popupScene.setFill(Color.TRANSPARENT);
        loadTheme(popupScene, "dark.css");
        popup.setTitle(newScene.getTitle());
        popup.setScene(popupScene);
        return popup;
    }

    public static <T> Stage showPopup(Stage owner, Scenes.Scene newScene, T variable, StageStyle stageStyle, double x, double y, double width, double height) throws IOException {
        var pair = initializeController(newScene, variable);
        if (pair == null) return null;
        var popup = new Stage();
        popup.initOwner(owner);
        popup.initStyle(stageStyle);
        popup.initModality(Modality.NONE);
        popup.setAlwaysOnTop(true);
        popup.setX(x);
        popup.setY(y);
        var popupScene = new Scene(pair.getKey(), width, height);
        if (stageStyle == StageStyle.TRANSPARENT) popupScene.setFill(Color.TRANSPARENT);
        loadTheme(popupScene, "dark.css");
        popup.setTitle(newScene.getTitle());
        popup.setScene(popupScene);
        return popup;
    }

    // endregion

    // region Methods

    public static void checkAchievements() throws IOException {
        var achievementsToCheck = Access.gameData.achievements.where(x -> !x.completed);
        var timelines = new Enumerable<SequentialTransition>();
        for (var achievement : achievementsToCheck) {
            if (!achievement.checkCompletion()) continue;
            var popup = WindowManager.showPopup(stage,
                    Scenes.Scene.CompleteAchievement,
                    achievement,
                    StageStyle.TRANSPARENT,
                    stage.getX() + 15.0,
                    stage.getY() + 50.0,
                    275.0,
                    40.0);
            if (popup == null) continue;

            var timeline1 = new Timeline();
            timeline1.setCycleCount(1);
            var kf1 = new KeyFrame(Duration.millis(1), t -> {
                popup.show();
                stage.requestFocus();
            });
            timeline1.getKeyFrames().add(kf1);

            var timeline2 = new Timeline();
            var kf2 = new KeyFrame(Duration.millis(2001));
            timeline2.getKeyFrames().add(kf2);

            var timeline3 = new Timeline();
            var kv1 = new KeyValue(popup.opacityProperty(), 0.0);
            var kf3 = new KeyFrame(Duration.millis(2500), t -> popup.hide(), kv1);
            timeline3.getKeyFrames().add(kf3);

            timelines.add(new SequentialTransition(timeline1, timeline2, timeline3));
        }

        var st = new SequentialTransition();
        st.getChildren().setAll(timelines);
        st.play();
    }

    private static <T> Pair<Parent, Controller<T>> initializeController(Scenes.Scene newScene, T variable) throws IOException {
        Pair<Parent, Controller<T>> pair = getControllerParent(newScene);
        if (pair == null) return null;
        pair.getValue().variable = variable;
        pair.getValue().start();
        setActiveController(pair.getValue());
        setTitle(newScene.getTitle());
        return pair;
    }

    // endregion

    // region Properties

    public static String getTitle() {
        return title.get();
    }

    public static StringProperty titleProperty() {
        return title;
    }

    public static void setTitle(String title) {
        WindowManager.title.set(title);
    }

    public static Controller getActiveController() {
        return activeController.get();
    }

    public static ObjectProperty<Controller> activeControllerProperty() {
        return activeController;
    }

    public static void setActiveController(Controller activeController) {
        WindowManager.activeController.set(activeController);
    }

    private static final StringProperty title = new SimpleStringProperty("Program");
    private static final ObjectProperty<Controller> activeController = new SimpleObjectProperty<>();

    // endregion

    // region Fields

    public static String css;
    public static String fxml;
    public static String images;

    public static Stage stage;
    public static Scene scene;
    public static VBox root;

    // endregion

}
