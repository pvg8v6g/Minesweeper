package scenes.controller;

import javafx.geometry.Insets;
import javafx.scene.layout.VBox;
import windowmanager.WindowManager;

/**
 * This file has been created by:
 * pvaughn on
 * 3/18/2021 at
 * 11:21 AM
 */
public class Controller<T> {

    // region Start

    public void start() {
        root.getChildren().forEach(x -> VBox.setMargin(x, new Insets(5)));
        bindRootSize();
    }

    // endregion

    // region Size Binding

    private void bindRootSize() {
        root.prefWidthProperty().bind(WindowManager.scene.widthProperty());
        root.prefHeightProperty().bind(WindowManager.scene.heightProperty());
    }

    // endregion

    // region Fields

    public T variable;
    public VBox root;

    // endregion

}
