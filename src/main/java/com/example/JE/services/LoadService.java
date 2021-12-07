package com.example.JE.services;

import com.example.JE.MyConnection;
import com.example.JE.dao.Countries;
import com.example.JE.dao.Films;
import com.example.JE.dao.Genres;
import com.example.JE.dao.Pages;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LoadService {
    public static void loadData(Pages pages) throws ClassNotFoundException, SQLException {

        Connection connection = new MyConnection().getConnection();
        connection.setAutoCommit(false);
        PreparedStatement checkST = connection.prepareStatement("select name_en from films where name_en = ? and name_en <> 'null' or name_ru = ? and name_ru <> 'null' or id = ?");
        PreparedStatement loadFilmST = connection.prepareStatement("insert into films(id, name_ru, name_en, year, length, rating, rating_vote_count, poster_url, poster_url_preview) values (?, ?, ?, ?, ?, ?, ?, ?, ?)");
        PreparedStatement loadFilmCountriesST = connection.prepareStatement("insert into film_countries(film_id, country_id) values (?, (select id from countries where name = ?))");
        PreparedStatement loadFilmGenresST = connection.prepareStatement("insert into film_genres(film_id, genre_id) values (?, (select id from genres where name = ?))");

        for(int i = 0; i < pages.getFilms().size(); i++) { //todo цикл записи по 1 фильму фильмов

            Films film = pages.getFilms().get(i);
            checkST.setString(1, film.getNameEn());
            checkST.setString(2, film.getNameRu());
            checkST.setInt(3, film.getFilmId().intValue());
            ResultSet rsf = checkST.executeQuery();

            if (rsf.next()) {
                System.out.println("Already exist film: " + film.getFilmId() + "/" + film.getNameEn() + "/" + film.getNameRu());
            } else {


                loadFilmST.setInt(1, film.getFilmId().intValue());
                loadFilmST.setString(2, film.getNameRu());
                loadFilmST.setString(3, film.getNameEn());
                loadFilmST.setString(4, film.getYear());
                loadFilmST.setString(5, film.getFilmLength());
                loadFilmST.setString(6, film.getRating());
                loadFilmST.setInt(7, film.getRatingVoteCount().intValue());
                loadFilmST.setString(8, film.getPosterUrl());
                loadFilmST.setString(9, film.getPosterUrlPreview());
                System.out.println("load");
                loadFilmST.addBatch();

                for (int j = 0; j < film.getCountries().size(); j++) { // todo цикл для проверки и если нет, записи страны
                    Countries country = new Countries(film.getCountries().get(j).getCountry());
                    loadFilmCountriesST.setInt(1, film.getFilmId().intValue());
                    loadFilmCountriesST.setString(2, country.getCountry());
                    loadFilmCountriesST.addBatch();
                }

                for (int j = 0; j < film.getGenres().size(); j++) { // todo цикл для проверки и если нет, записи жанра
                    Genres genre = new Genres(film.getGenres().get(j).getGenre());
                    loadFilmGenresST.setInt(1, film.getFilmId().intValue());
                    loadFilmGenresST.setString(2, genre.getGenre());
                    loadFilmGenresST.addBatch();
                }
            }
        }
        loadFilmST.executeBatch();
        loadFilmCountriesST.executeBatch();
        loadFilmGenresST.executeBatch();


        connection.commit();
        connection.close();
    }
}
