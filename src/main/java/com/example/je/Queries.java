package com.example.je;

public class Queries {
    public static final String INSERT_CHECK_EXISTING_FILM = "select name_en from film where name_en = ? and name_en <> 'null' or name_ru = ? and name_ru <> 'null' or id = ?";
    public static final String INSERT_CHECK_EXISTING_FULL_FILM = "select name_en from full_film where name_en = ? and name_en <> 'null' or name_ru = ? and name_ru <> 'null' or kinopoisk_id = ?";
    public static final String INSERT_FILM = "insert into film(id, name_ru, name_en, year, length, rating, rating_vote_count, poster_url, poster_url_preview, is_blocked) values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
    public static final String INSERT_FULL_FILM = "insert into full_film(kinopoisk_id, imdb_id, name_ru, name_en, name_original, poster_url, poster_url_preview, reviews_count, rating_good_review, " +
            "rating_good_review_vote_count, rating_kinopoisk, rating_kinopoisk_vote_count, rating_imdb, rating_imdb_vote_count, rating_film_critics, rating_film_critics_vote_count, rating_await, rating_await_count, " +
            "rating_rf_critics, rating_rf_criticsVoteCount, web_url, year, length, slogan, description, short_description, editor_annotation, is_tickets_available, production_status, type, rating_mpaa, rating_age_limits, " +
            "start_year, end_year, serial, short_film, completed, has_imax, has_3d, last_sync) values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?,?, ?, ?, ?, ?, ?, ?, ?, ?, ?,?, ?, ?, ?, ?, ?, ?, ?, ?, ?,?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
    public static final String INSERT_COUNTRY_IN_FILM = "insert into film_country(film_id, country_id) values (?, (select id from country where name = ?))";
    public static final String INSERT_GENRE_IN_FILM = "insert into film_genre(film_id, genre_id) values (?, (select id from genre where name = ?))";

    public static final String GET_FILM = "select * from  film where id = ?";
    public static final String GET_FULL_FILM = "select * from  full_film where kinopoisk_id = ?";
    public static final String GET_COUNTRY_IN_FILM = "select name from country where id in (select country_id from  film_country where film_id = ?)";
    public static final String GET_GENRE_IN_FILM = "select name from genre where id in (select genre_id from  film_genre where film_id = ?)";

    public static final String UPDATE_FILM = "update film set name_ru = ?, name_en = ?, year = ?, length = ?, rating = ?, rating_vote_count = ?, poster_url = ?, poster_url_preview = ? where id = ?";
    public static final String UPDATE_FULL_FILM = "update full_film set imdb_id = ?, name_ru = ?, name_en = ?, name_original = ?, poster_url = ?, poster_url_preview = ?, reviews_count = ?, rating_good_review = ?, " +
            "rating_good_review_vote_count = ?, rating_kinopoisk = ?, rating_kinopoisk_vote_count = ?, rating_imdb = ?, rating_imdb_vote_count = ?, rating_film_critics = ?, rating_film_critics_vote_count = ?, rating_await = ?, rating_await_count = ?, " +
            "rating_rf_critics = ?, rating_rf_criticsVoteCount = ?, web_url = ?, year = ?, length = ?, slogan = ?, description = ?, short_description = ?, editor_annotation = ?, is_tickets_available = ?, production_status = ?, type = ?, rating_mpaa = ?, rating_age_limits = ?, " +
            "start_year = ?, end_year = ?, serial = ?, short_film = ?, completed = ?, has_imax = ?, has_3d = ?, last_sync = ? where kinopoisk_id = ?";

    public static final String DELETE_FILM = "delete from film where id = ?";
    public static final String DELETE_COUNTRY_IN_FILM = "delete from film_country where film_id = ?";
    public static final String DELETE_GENRE_IN_FILM = "delete from film_genre where film_id = ?";
}
