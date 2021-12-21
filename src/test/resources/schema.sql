CREATE TABLE IF NOT EXISTS films
(
    id bigint PRIMARY KEY AUTO_INCREMENT,
    name_ru VARCHAR(255) ,
    name_en VARCHAR(255) ,
    `year` VARCHAR(255) ,
    length VARCHAR(255) ,
    rating VARCHAR(255) ,
    rating_vote_count bigint ,
    poster_url VARCHAR(255) ,
    poster_url_preview VARCHAR(255)
);

CREATE TABLE IF NOT EXISTS countries
(
    id bigint PRIMARY KEY AUTO_INCREMENT,
    `name` VARCHAR(50) NOT NULL unique
);

CREATE TABLE IF NOT EXISTS film_countries
(
    film_id bigint,
    country_id bigint,
    foreign key (film_id) references films (id),
    foreign key (country_id) references countries (id)
);

CREATE TABLE IF NOT EXISTS genres
(
    id bigint PRIMARY KEY AUTO_INCREMENT,
    `name` VARCHAR(50) NOT NULL unique
);

CREATE TABLE IF NOT EXISTS film_genres
(
    film_id bigint,
    genre_id bigint,
    foreign key (film_id) references films (id),
    foreign key (genre_id) references genres (id)
);