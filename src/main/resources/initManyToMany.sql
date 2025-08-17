CREATE SCHEMA IF NOT EXISTS vote_app AUTHORIZATION postgres;

CREATE TABLE IF NOT EXISTS vote_app.artist
(
    id bigserial PRIMARY KEY,
    name character varying NOT NULL
);

ALTER TABLE IF EXISTS vote_app.artist
    OWNER to postgres;

CREATE TABLE IF NOT EXISTS vote_app.genre
(
    id bigserial PRIMARY KEY,
    name character varying NOT NULL
);

ALTER TABLE IF EXISTS vote_app.genre
    OWNER to postgres;

CREATE TABLE IF NOT EXISTS vote_app.vote
(
    id bigserial PRIMARY KEY,
    dt_create timestamp(6) without time zone NOT NULL,
    artist_id bigint REFERENCES vote_app.artist(id),
    about text NOT NULL
    );

ALTER TABLE IF EXISTS vote_app.vote
    OWNER to postgres;

CREATE TABLE IF NOT EXISTS vote_app.vote_to_genre
(
    vote_id bigint NOT NULL REFERENCES vote_app.vote(id),
    genre_id bigint NOT NULL REFERENCES vote_app.genre(id)

    );

ALTER TABLE IF EXISTS vote_app.vote_to_genre
    OWNER to postgres;

-- generated tests --
-- Artists
INSERT INTO vote_app.artist (name) VALUES ('Artist A'), ('Artist B');

-- Genres
INSERT INTO vote_app.genre (name) VALUES ('Rock'), ('Jazz'), ('Pop');

-- Votes
INSERT INTO vote_app.vote (dt_create, artist_id, about) VALUES
                                                            (NOW(), 1, 'Vote for Artist A'),
                                                            (NOW(), 1, 'Another vote for Artist A'),
                                                            (NOW(), 2, 'Vote for Artist B');

-- Many-to-Many associations (a vote can belong to multiple genres)
INSERT INTO vote_app.vote_to_genre (vote_id, genre_id) VALUES
                                                           (1, 1), -- Vote 1 is Rock
                                                           (1, 2), -- Vote 1 is also Jazz
                                                           (2, 3), -- Vote 2 is Pop
                                                           (3, 1), -- Vote 3 is Rock
                                                           (3, 3); -- Vote 3 is also Pop

