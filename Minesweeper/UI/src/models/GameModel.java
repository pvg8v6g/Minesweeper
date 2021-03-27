package models;

/**
 * This file has been created by:
 * pvaughn on
 * 3/18/2021 at
 * 1:17 PM
 */
public class GameModel {

    // region Constructor

    public GameModel(int width, int height, int bombs) {
        this.width = width;
        this.height = height;
        this.bombs = bombs;
    }

    // endregion

    // region Fields

    public int width;
    public int height;
    public int bombs;

    // endregion

}
