CREATE TYPE colors AS ENUM (
    'white',
    'black',
    'blue',
    'green',
    'yellow',
    'orange',
    'pink'
    );

CREATE TYPE breeds AS ENUM (
    'american shorthair',
    'britman',
    'cornish rex',
    'british shorthair',
    'munchkin',
    'oriental',
    'persian',
    'peterbald'
    );

CREATE TABLE IF NOT EXISTS cats
(
    id       varchar not null unique,
    name     varchar not null,
    birthday date    not null,
    breed    breeds  not null,
    color    colors  not null
);

CREATE TABLE IF NOT EXISTS owners
(
    id       varchar not null unique,
    name     varchar not null,
    birthday date    not null
);

CREATE TABLE IF NOT EXISTS owner_cat
(
    owner_id varchar not null references owners (id),
    cat_id   varchar not null references cats (id)
);

CREATE TABLE IF NOT EXISTS cat_friend
(
    cat_id    varchar not null references cats (id),
    friend_id varchar not null references cats (id)
);