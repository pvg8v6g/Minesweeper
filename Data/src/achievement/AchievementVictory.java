package achievement;

import access.Access;

/**
 * This file has been created by:
 * pvaughn on
 * 5/25/2021 at
 * 08:46
 */
public class AchievementVictory extends Achievement {

    // region Initialize

    public void initialize() {
        name = "All Clear";
        description = "Achieve this by winning your first game.";
    }

    // endregion

    // region Completion

    public boolean checkCompletion() {
        if (completed) return false;
        completed = Access.gameData.gamesWon > 0;
        return completed;
    }

    // endregion

}
