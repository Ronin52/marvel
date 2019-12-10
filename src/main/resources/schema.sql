-- CREATE DATABASE marvel;

CREATE TABLE IF NOT EXISTS characters
(
    id          uuid PRIMARY KEY,
    name        VARCHAR,
    description VARCHAR,
    image       VARCHAR
);

CREATE TABLE IF NOT EXISTS comics
(
    id          uuid PRIMARY KEY,
    title       VARCHAR,
    description VARCHAR,
    image       VARCHAR
);

CREATE TABLE IF NOT EXISTS character_comics
(
    character_id uuid REFERENCES characters (id) ON UPDATE CASCADE ON DELETE CASCADE,
    comics_id    uuid REFERENCES comics (id) ON UPDATE CASCADE ON DELETE CASCADE,
    CONSTRAINT character_comics_pk PRIMARY KEY (character_id, comics_id)
);