create table users (
    id SERIAL primary key,
    username varchar(50) not null,
    password varchar(120) not null,
    enabled boolean not null,
    authority varchar(50) not null
);

create table game_requests (
    id SERIAL primary key,
    api_date date not null,
    request_date_time timestamp not null
);

create table games (
    id SERIAL primary key,
    request_id int not null ,
    date timestamp not null,
    status varchar(50) not null,
    country varchar(50) not null,
    home_team varchar(50) not null,
    away_team varchar(50) not null,
    home_score varchar(50) not null,
    away_score varchar(50) not null,

    foreign key (request_id) references game_requests (id)
);