package mathematics;

/**
 * This file has been created by:
 * pvaughn on
 * 3/27/2021 at
 * 3:38 PM
 */
public class Mathematics {

    public static int random(int min, int max) {
        return (int) (min + (Math.random() * ((max - min) + 1)));
    }

}
