package achievement;

import java.io.Serializable;
import java.util.UUID;

/**
 * This file has been created by:
 * pvaughn on
 * 5/24/2021 at
 * 15:22
 */
public abstract class Achievement implements Serializable {

    // region Constructor

    public Achievement() {
        uuid = UUID.randomUUID();
        initialize();
    }

    // endregion

    // region Methods

    public abstract void initialize();

    public abstract boolean checkCompletion();

    // endregion

    // region Overrides

    public boolean equals(Object o) {
        return o instanceof Achievement achievement && achievement.uuid.equals(uuid);
    }

    public int hashCode() {
        return 0;
    }

    // endregion

    // region Fields

    public UUID uuid;
    public String name;
    public String description;
    public boolean completed;

    private static final long serialVersionUID = 8911334518795L;

    // endregion

}
