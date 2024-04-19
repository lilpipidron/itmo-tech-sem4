CREATE TABLE owners
(
    id       BIGINT PRIMARY KEY,
    name     VARCHAR(255) NOT NULL,
    birthday DATE         NOT NULL
);

create type breeds as enum (
    'AMERICAN_SHORTHAIR',
    'BRITMAN',
    'CORNISH_REX',
    'BRITISH_SHORTHAIR',
    'MUNCHKIN',
    'ORIENTAL',
    'PERSIAN',
    'PETERBALD'
    );
create type colors as enum (
    'WHITE',
    'BLACK',
    'BLUE',
    'GREEN',
    'YELLOW',
    'ORANGE',
    'PINK'
    );
CREATE TABLE cats
(
    id       BIGINT PRIMARY KEY,
    name     VARCHAR(255) NOT NULL,
    birthday DATE         NOT NULL,
    breed    breeds,
    color    colors,
    owner_id BIGINT,
    FOREIGN KEY (owner_id) REFERENCES owners (id)
);

CREATE TABLE cat_friend (
    cat_id BIGINT REFERENCES cats (id),
    friend_id BIGINT references cats (id)
)