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
    ADD CONSTRAINT vote_id_uniq UNIQUE (vote_id, genre_id);

ALTER TABLE IF EXISTS vote_app.vote_to_genre
    OWNER to postgres;

-- generated tests --
-- Artists
INSERT INTO vote_app.artist (name) VALUES ('Artist C'), ('Artist D');

-- Genres
INSERT INTO vote_app.genre (name) VALUES ('Hip-Hop'), ('Classical');

-- Votes
INSERT INTO vote_app.vote (dt_create, artist_id, about) VALUES
                                                            (NOW(), 1, 'Vote for Artist C'),
                                                            (NOW(), 2, 'Vote for Artist D'),
                                                            (NOW(), 2, 'Another vote for Artist D');

-- One-to-Many relationships (one vote can point to multiple genres)
INSERT INTO vote_app.vote_to_genre (vote_id, genre_id) VALUES
                                                           (1, 1), -- Vote 1 → Hip-Hop
                                                           (1, 2), -- Vote 1 → Classical
                                                           (2, 1), -- Vote 2 → Hip-Hop
                                                           (3, 2); -- Vote 3 → Classical
