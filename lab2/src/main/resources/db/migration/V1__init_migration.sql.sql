CREATE TYPE colors AS ENUM (
    'WHITE',
    'BLACK',
    'BLUE',
    'GREEN',
    'YELLOW',
    'ORANGE',
    'PINK'
    );

CREATE TYPE breeds AS ENUM (
    'AMERICAN SHORTHAIR',
    'BRITMAN',
    'CORNISH REX',
    'BRITISH SHORTHAIR',
    'MUNCHKIN',
    'ORIENTAL',
    'PERSIAN',
    'PETERBALD'
    );

CREATE TABLE IF NOT EXISTS cats
(
    id       BIGSERIAL not null unique primary key ,
    name     varchar not null,
    birthday date    not null,
    breed    breeds  not null,
    color    colors  not null
);

CREATE TABLE IF NOT EXISTS owners
(
    id       BIGSERIAL not null unique primary key,
    name     varchar not null,
    birthday date    not null
);

CREATE TABLE IF NOT EXISTS owner_cat
(
    owner_id integer not null references owners (id),
    cat_id   integer not null references cats (id)
);

CREATE TABLE IF NOT EXISTS cat_friend
(
    cat_id    integer not null references cats (id),
    friend_id integer not null references cats (id)
);