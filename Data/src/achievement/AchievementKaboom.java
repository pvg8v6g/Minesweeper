package achievement;

import access.Access;

/**
 * This file has been created by:
 * pvaughn on
 * 5/25/2021 at
 * 08:44
 */
public class AchievementKaboom extends Achievement {

    // region Initialize

    public void initialize() {
        name = "Kaboom!";
        description = "Achieve this by losing your first game.";
    }

    // endregion

    // region Completion

    public boolean checkCompletion() {
        if (completed) return false;
        completed = Access.gameData.gamesFinished > Access.gameData.gamesWon;
        return completed;
    }

    // endregion

}
