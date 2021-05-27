package achievement;

import access.Access;

/**
 * This file has been created by:
 * pvaughn on
 * 5/25/2021 at
 * 11:52
 */
public class AchievementRadar extends Achievement {

    // region Initialize

    public void initialize() {
        name = "I Have Radar";
        description = "Achieve this by having zero bombs left when you win.";
    }

    // endregion

    // region Completion

    public boolean checkCompletion() {
        if (completed) return false;
        completed = Access.gameData.bombsLeft == 0 && Access.gameData.gameJustEnded;
        return completed;
    }

    // endregion

}
