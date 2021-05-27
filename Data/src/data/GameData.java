package data;

import achievement.Achievement;
import list.Enumerable;

import java.io.Serializable;

/**
 * This file has been created by:
 * pvaughn on
 * 5/13/2021 at
 * 13:45
 */
public class GameData implements Serializable {

    // region Fields To Spot Achievements

    public boolean gameJustEnded;
    public int bombsLeft;

    // endregion

    // region Fields

    public int gamesFinished;
    public int gamesWon;
    public long totalTimePlayed;
    public Enumerable<Achievement> achievements;

    private static final long serialVersionUID = -5818684811448886L;

    // endregion

}
