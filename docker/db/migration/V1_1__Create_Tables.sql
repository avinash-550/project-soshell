-- create user table
CREATE TABLE users (
    id uuid NOT NULL PRIMARY KEY,
    username VARCHAR(512) NOT NULL,
    password VARCHAR(255) NOT NULL
);