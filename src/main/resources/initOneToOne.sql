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
    ADD CONSTRAINT vote_id_uniq UNIQUE (genre_id);

ALTER TABLE IF EXISTS vote_app.vote_to_genre
    OWNER to postgres;

--- generated tests ---
-- Artists
INSERT INTO vote_app.artist (name) VALUES ('Artist E'), ('Artist F');

-- Genres
INSERT INTO vote_app.genre (name) VALUES ('Metal'), ('Blues');

-- Votes
INSERT INTO vote_app.vote (dt_create, artist_id, about) VALUES
                                                            (NOW(), 1, 'Vote for Artist E'),
                                                            (NOW(), 2, 'Vote for Artist F');

-- One-to-One relationships (each genre can belong to only one vote)
INSERT INTO vote_app.vote_to_genre (vote_id, genre_id) VALUES
                                                           (1, 1), -- Vote 1 ↔ Genre Metal
                                                           (2, 2); -- Vote 2 ↔ Genre Blues

