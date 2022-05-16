CREATE TABLE IF NOT EXISTS books
(
    id              BIGSERIAL PRIMARY KEY NOT NULL,
    name            VARCHAR(200) NOT NULL,
    author_name      VARCHAR(50) NOT NULL,
    publication_year INT NOT NULL,
    author_id       BIGSERIAL NOT NULL
);

CREATE TABLE IF NOT EXISTS authors
(
    id              BIGSERIAL PRIMARY KEY NOT NULL,
    first_name          VARCHAR(200) NOT NULL,
    last_name           VARCHAR(50) NOT NULL
);