create type unit as enum ('lbs', 'pcs', 'kWh');

create table dataType
(
    id       serial primary key ,
    name     varchar(255) unique,
    unit     unit
);

create table region
(
    id       serial primary key,
    name     varchar(255) unique
);
create table distribution
(
    id serial primary key,
    dateStart date,
    regionId serial,
    value float8,
    trees integer,
    energy float8
);
