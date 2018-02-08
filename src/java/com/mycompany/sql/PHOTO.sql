/**
 * Author:  andres
 * Created: Jan 25, 2018
 */
# -------------------------------------------------------------
# SQL Script to Create The Photo Table
# -------------------------------------------------------------

DROP TABLE IF EXISTS PHOTO;

CREATE TABLE PHOTO
(
       PHOTO_ID INT UNSIGNED PRIMARY KEY AUTO_INCREMENT NOT NULL,
       EXTENSION ENUM('png', 'jpg', 'jpeg') NOT NULL,
       USER_ID INT REFERENCES USERS(USER_ID) ON DELETE CASCADE,
       OPPORTUNITY_ID INT REFERENCES VOLUNTEERING_OPPORTUNITIES(ID) ON DELETE CASCADE
);
