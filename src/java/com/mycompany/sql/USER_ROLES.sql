/**
 * Author:  andres
 * Created: Jan 25, 2018
 */

# -------------------------------------------------------------
# SQL Script to Create The USER_ROLES Table
# -------------------------------------------------------------
 
DROP TABLE IF EXISTS USER_ROLES;

CREATE TABLE USER_ROLES
(
    USER_ROLE_ID INT PRIMARY KEY AUTO_INCREMENT,
    ROLE VARCHAR (255) NOT NULL
);
