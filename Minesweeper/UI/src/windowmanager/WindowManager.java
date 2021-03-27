package windowmanager;

import enumerations.Scenes;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Pair;
import scenes.controller.Controller;

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

    public static void initialize() throws IOException {
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
        loadTheme("dark.css");
        changeScene(Scenes.Scene.Main, null);
    }

    private static void createWindow() {
        stage.setScene(scene);
        stage.setMaximized(true);
        stage.titleProperty().bind(titleProperty());
        setTitle("Minesweeper");
        stage.show();
    }

    // endregion

    // region Stylesheet Loading

    public static void loadTheme(String stylesheet) {
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
        Pair<Parent, Controller<T>> pair = getControllerParent(newScene);
        if (pair == null) return;
        pair.getValue().variable = variable;
        pair.getValue().start();
        setActiveController(pair.getValue());
        setTitle(newScene.getTitle());
        root.getChildren().setAll(pair.getKey());
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
