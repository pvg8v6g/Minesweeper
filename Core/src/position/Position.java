package position;

/**
 * This file has been created by:
 * pvaughn on
 * 3/26/2021 at
 * 8:10 AM
 */
public record Position(int x, int y) {

    public boolean equals(Object o) {
        return o instanceof Position mine && mine.x() == x && mine.y() == y;
    }

    public int hashCode() {
        return 0;
    }

}
