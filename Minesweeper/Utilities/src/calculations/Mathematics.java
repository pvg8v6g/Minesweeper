package calculations;

/**
 * This file has been created by:
 * pvaughn on
 * 3/25/2021 at
 * 4:01 PM
 */
public class Mathematics {

    public static int random(int min, int max) {
        return (int) (min + (Math.random() * ((max - min) + 1)));
    }

}
