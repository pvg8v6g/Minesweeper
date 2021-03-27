package main;

import javafx.application.Application;
import javafx.stage.Stage;
import windowmanager.WindowManager;

/**
 * This file has been created by:
 * pvaughn on
 * 3/18/2021 at
 * 8:42 AM
 */
public class Main extends Application {

    public void start(Stage stage) throws Exception {
        WindowManager.stage = stage;
        WindowManager.initialize();
    }

}
