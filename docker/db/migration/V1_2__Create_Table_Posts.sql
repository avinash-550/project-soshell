-- create posts table
CREATE TABLE posts (
    id uuid NOT NULL PRIMARY KEY,
    username VARCHAR(512) NOT NULL,
    content VARCHAR(255) NOT NULL,
    createdAt TIMESTAMP NOT NULL
);
