package enumerations;

/**
 * This file has been created by:
 * pvaughn on
 * 3/18/2021 at
 * 11:16 AM
 */
public class Scenes {

    public enum Scene {

        Main,
        Game,
        Achievements,
        CompleteAchievement;

        // region Properites

        public String getFxml() {
            return fxml;
        }

        public void setFxml(String fxml) {
            this.fxml = fxml;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        private String fxml;
        private String title;

        // endregion

        // region Static

        static {
            Main.setFxml("main\\mainscene");
            Main.setTitle("Minesweeper");
            Game.setFxml("game\\gamescene");
            Game.setTitle("Game");
            Achievements.setFxml("achievements\\achievementsscene");
            Achievements.setTitle("Achievements");
            CompleteAchievement.setFxml("achievementcompleted\\achievementcompleted");
            CompleteAchievement.setTitle("Completed Achievement");
        }

        // endregion

    }

}
