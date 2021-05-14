/**
 * This file has been created by:
 * pvaughn on
 * 5/12/2021 at
 * 15:23
 */
module Data {
    requires Core;
    exports access;
    exports data;
    exports encryption;
    opens access;
    opens data;
    opens encryption;
}
