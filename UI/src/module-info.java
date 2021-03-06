/**
 * This file has been created by:
 * pvaughn on
 * 3/18/2021 at
 * 9:40 AM
 */
module UI {
    requires javafx.graphics;
    requires javafx.controls;
    requires javafx.fxml;
    requires Core;
    requires Data;
    exports main;
    exports windowmanager;
    exports scenes.main;
    exports scenes.game;
    exports scenes.achievements;
    exports scenes.achievementcompleted;
    exports scenes.controller;
    exports models;
    opens main;
    opens windowmanager;
    opens scenes.main;
    opens scenes.game;
    opens scenes.achievements;
    opens scenes.achievementcompleted;
    opens scenes.controller;
    opens models;
}
