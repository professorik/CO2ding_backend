create table branches
(
    id       uuid not null,
    name     varchar(255),
    year     integer,
    ejection float8,
    primary key (id)
);

create table admins
(
    id       uuid not null,
    login    varchar(255),
    email    varchar(255),
    password varchar(255),
    primary key (id)
);
