create table owner
(
    id       serial
        primary key,
    show     boolean      not null,
    name     varchar(255) not null,
    password varchar(255) not null,
    username varchar(255) not null
);

create table dog
(
    id       serial
        primary key,
    birthday date,
    breed    varchar(255),
    name     varchar(255) not null,
    sex      boolean      not null,
    owner    integer
        constraint fkohpi80do4388gkgw43770ioe7
            references owner
);

create table event
(
    id          serial
        primary key,
    date        date,
    description varchar(255),
    name        varchar(255) not null,
    time        time,
    owner       integer
        constraint fkp9imfutkbsam1u7gpyuqfahwi
            references owner
);

create table user_role
(
    user_id integer not null
        constraint fko5tqbqamhf3o3lc26ppspamde
            references owner,
    roles   varchar(255)
);

create table vetclinic
(
    id          serial
        primary key,
    city        varchar(255) not null,
    country     varchar(255),
    description varchar(255),
    house       varchar(255) not null,
    name        varchar(255) not null,
    phone       varchar(255),
    region      varchar(255),
    street      varchar(255) not null
);

create table treatment
(
    id        serial
        primary key,
    name      varchar(255)   not null,
    price     numeric(19, 2) not null,
    vetclinic integer
        constraint fksyicp56b4fyw092bhujblluoh
            references vetclinic
);

create table splash_screen
(
    id     serial
        primary key,
    button varchar(255),
    image  bytea,
    text   varchar(255),
    title  varchar(255)
);