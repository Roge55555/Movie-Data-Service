package com.example.je;

public class Queries {
    public static final String INSERT_CHECK_EXISTING_FILM = "select name_en from films where name_en = ? and name_en <> 'null' or name_ru = ? and name_ru <> 'null' or id = ?";
    public static final String INSERT_FILM = "insert into films(id, name_ru, name_en, year, length, rating, rating_vote_count, poster_url, poster_url_preview) values (?, ?, ?, ?, ?, ?, ?, ?, ?)";
    public static final String INSERT_COUNTRY_IN_FILM = "insert into film_countries(film_id, country_id) values (?, (select id from countries where name = ?))";
    public static final String INSERT_GENRE_IN_FILM = "insert into film_genres(film_id, genre_id) values (?, (select id from genres where name = ?))";

    public static final String GET_FILM = "select * from  films where id = ?";
    public static final String GET_COUNTRY_IN_FILM = "select name from countries where id in (select country_id from  film_countries where film_id = ?)";
    public static final String GET_GENRE_IN_FILM = "select name from genres where id in (select genre_id from  film_genres where film_id = ?)";

    public static final String UPDATE_CHECK_EXISTING_FILM = "select name_en from films where id = ?";
    public static final String UPDATE_FILM = "update films set name_ru = ?, name_en = ?, year = ?, length = ?, rating = ?, rating_vote_count = ?, poster_url = ?, poster_url_preview = ? where id = ?";
    public static final String UPDATE_COUNTRY_IN_FILM = "insert into film_countries(film_id, country_id) values (?, (select id from countries where name = ?))";
    public static final String UPDATE_GENRE_IN_FILM = "insert into film_genres(film_id, genre_id) values (?, (select id from genres where name = ?))";

    public static final String DELETE_FILM = "delete from films where id = ?";
    public static final String DELETE_COUNTRY_IN_FILM = "delete from film_countries where film_id = ?";
    public static final String DELETE_GENRE_IN_FILM = "delete from film_genres where film_id = ?";
}
