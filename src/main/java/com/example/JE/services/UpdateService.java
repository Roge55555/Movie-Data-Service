package com.example.JE.services;

import com.example.JE.MyConnection;
import com.example.JE.dao.Countries;
import com.example.JE.dao.Films;
import com.example.JE.dao.Genres;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UpdateService {
    public static void updateFilm(Films film) throws ClassNotFoundException, SQLException {
        Connection connection = new MyConnection().getConnection();
        connection.setAutoCommit(false);
        PreparedStatement checkST = connection.prepareStatement("select name_en from films where id = ?");
        PreparedStatement updateFilmST = connection.prepareStatement("update films set name_ru = ?, name_en = ?, year = ?, length = ?, rating = ?, rating_vote_count = ?, poster_url = ?, poster_url_preview = ? where id = ?");
        PreparedStatement updateFilmCountriesST = connection.prepareStatement("insert into film_countries(film_id, country_id) values (?, (select id from countries where name = ?))");
        PreparedStatement updateFilmGenresST = connection.prepareStatement("insert into film_genres(film_id, genre_id) values (?, (select id from genres where name = ?))");

        checkST.setInt(1, film.getFilmId().intValue());
        ResultSet rsf = checkST.executeQuery();

        if (rsf.next()) {

            updateFilmST.setInt(9, film.getFilmId().intValue());
            updateFilmST.setString(1, film.getNameRu());
            updateFilmST.setString(2, film.getNameEn());
            updateFilmST.setString(3, film.getYear());
            updateFilmST.setString(4, film.getFilmLength());
            updateFilmST.setString(5, film.getRating());
            updateFilmST.setInt(6, film.getRatingVoteCount().intValue());
            updateFilmST.setString(7, film.getPosterUrl());
            updateFilmST.setString(8, film.getPosterUrlPreview());
            System.out.println("update");
            updateFilmST.addBatch();



            updateFilmCountriesST.addBatch("delete from film_countries where film_id = " + film.getFilmId().intValue());

            for (int j = 0; j < film.getCountries().size(); j++) { // todo цикл для проверки и если нет, записи страны
                Countries country = new Countries(film.getCountries().get(j).getCountry());
                updateFilmCountriesST.setInt(1, film.getFilmId().intValue());
                updateFilmCountriesST.setString(2, country.getCountry());
                updateFilmCountriesST.addBatch();
            }

            updateFilmCountriesST.addBatch("delete from film_genres where film_id = " + film.getFilmId().intValue());

            for (int j = 0; j < film.getGenres().size(); j++) { // todo цикл для проверки и если нет, записи жанра
                Genres genre = new Genres(film.getGenres().get(j).getGenre());
                updateFilmGenresST.setInt(1, film.getFilmId().intValue());
                updateFilmGenresST.setString(2, genre.getGenre());
                updateFilmGenresST.addBatch();
            }

        } else {
            System.out.println("No film with id: " + film.getFilmId());
        }
        updateFilmST.executeBatch();
        updateFilmCountriesST.executeBatch();
        updateFilmGenresST.executeBatch();


        connection.commit();
        connection.close();
    }
}
