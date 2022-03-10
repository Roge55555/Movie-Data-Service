CREATE TABLE IF NOT EXISTS film
(
    id bigint PRIMARY KEY AUTO_INCREMENT,
    name_ru VARCHAR(255) ,
    name_en VARCHAR(255) ,
    `year` VARCHAR(255) ,
    length VARCHAR(255) ,
    rating double ,
    rating_vote_count bigint ,
    poster_url VARCHAR(255) ,
    poster_url_preview VARCHAR(255) ,
    is_blocked boolean
);

CREATE TABLE IF NOT EXISTS full_film
(
    kinopoisk_id bigint PRIMARY KEY AUTO_INCREMENT,
    imdb_id VARCHAR(255) ,
    name_ru VARCHAR(255) ,
    name_en VARCHAR(255) ,
    name_original VARCHAR(255) ,
    poster_url VARCHAR(255) ,
    poster_url_preview VARCHAR(255) ,
    reviews_count bigint ,
    rating_good_review double ,
    rating_good_review_vote_count bigint ,
    rating_kinopoisk double ,
    rating_kinopoisk_vote_count bigint ,
    rating_imdb double ,
    rating_imdb_vote_count bigint ,
    rating_film_critics double ,
    rating_film_critics_vote_count bigint ,
    rating_await double ,
    rating_await_count bigint ,
    rating_rf_critics double ,
    rating_rf_critics_vote_count bigint ,
    web_url VARCHAR(255) ,
    `year` bigint ,
    length bigint ,
    slogan VARCHAR(255) ,
    `description` longtext ,
    short_description VARCHAR(255) ,
    editor_annotation VARCHAR(255) ,
    is_tickets_available BOOLEAN ,
    production_status VARCHAR(255) ,
    `type` VARCHAR(255) ,
    rating_mpaa VARCHAR(255) ,
    rating_age_limits VARCHAR(255) ,
    start_year bigint ,
    end_year bigint ,
    `serial` BOOLEAN ,
    short_film BOOLEAN ,
    completed BOOLEAN ,
    has_imax BOOLEAN ,
    has_3d BOOLEAN ,
    last_sync VARCHAR(255)
);

CREATE TABLE IF NOT EXISTS country
(
    id bigint PRIMARY KEY AUTO_INCREMENT,
    `name` VARCHAR(50) NOT NULL unique
);

CREATE TABLE IF NOT EXISTS film_country
(
    id bigint PRIMARY KEY AUTO_INCREMENT,
    film_id bigint,
    country_id bigint,
    foreign key (film_id) references film (id),
    foreign key (country_id) references country (id)
);

CREATE TABLE IF NOT EXISTS genre
(
    id bigint PRIMARY KEY AUTO_INCREMENT,
    `name` VARCHAR(50) NOT NULL unique
);

CREATE TABLE IF NOT EXISTS film_genre
(
    id bigint PRIMARY KEY AUTO_INCREMENT,
    film_id bigint,
    genre_id bigint,
    foreign key (film_id) references film (id),
    foreign key (genre_id) references genre (id)
);