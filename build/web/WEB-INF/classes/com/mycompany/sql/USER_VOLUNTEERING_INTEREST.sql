/**
 * Author:  andres
 * Created: Jan 25, 2018
 */

# -------------------------------------------------------------
# SQL Script to Create The USER_VOLUNTEERING_INTEREST Table
# -------------------------------------------------------------
 
DROP TABLE IF EXISTS USER_VOLUNTEERING_INTEREST;

CREATE TABLE USER_VOLUNTEERING_INTEREST
(
    ID INT PRIMARY KEY AUTO_INCREMENT,
    VOLUNTEERING_AREA_ID INT UNSIGNED NOT NULL,
    USER_ID INT NOT NULL REFERENCES USERS(USER_ID) ON DELETE CASCADE
);
