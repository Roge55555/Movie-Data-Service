package com.example.je.services;

import com.example.je.MyConnection;
import com.example.je.dao.Countries;
import com.example.je.dao.Films;
import com.example.je.dao.Genres;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CreateService {
    public static void add(Films film) throws ClassNotFoundException, SQLException {

        Connection connection = new MyConnection().getConnection();
        connection.setAutoCommit(false);
        PreparedStatement checkST = connection.prepareStatement("select name_en from films where name_en = ? and name_en <> 'null' or name_ru = ? and name_ru <> 'null' or id = ?");
        PreparedStatement addFilmST = connection.prepareStatement("insert into films(id, name_ru, name_en, year, length, rating, rating_vote_count, poster_url, poster_url_preview) values (?, ?, ?, ?, ?, ?, ?, ?, ?)");
        PreparedStatement addFilmCountriesST = connection.prepareStatement("insert into film_countries(film_id, country_id) values (?, (select id from countries where name = ?))");
        PreparedStatement addFilmGenresST = connection.prepareStatement("insert into film_genres(film_id, genre_id) values (?, (select id from genres where name = ?))");

        checkST.setString(1, film.getNameEn());
        checkST.setString(2, film.getNameRu());
        checkST.setInt(3, film.getFilmId().intValue());
        ResultSet rsf = checkST.executeQuery();

            if (rsf.next()) {
                System.out.println("Already exist film: " + film.getFilmId() + "/" + film.getNameEn() + "/" + film.getNameRu());
            } else {

                addFilmST.setInt(1, film.getFilmId().intValue());
                addFilmST.setString(2, film.getNameRu());
                addFilmST.setString(3, film.getNameEn());
                addFilmST.setString(4, film.getYear());
                addFilmST.setString(5, film.getFilmLength());
                addFilmST.setString(6, film.getRating());
                addFilmST.setInt(7, film.getRatingVoteCount().intValue());
                addFilmST.setString(8, film.getPosterUrl());
                addFilmST.setString(9, film.getPosterUrlPreview());
                System.out.println("insert");
                addFilmST.addBatch();

                for (int j = 0; j < film.getCountries().size(); j++) { // todo цикл для проверки и если нет, записи страны
                    Countries country = new Countries(film.getCountries().get(j).getCountry());
                    addFilmCountriesST.setInt(1, film.getFilmId().intValue());
                    addFilmCountriesST.setString(2, country.getCountry());
                    addFilmCountriesST.addBatch();
                }

                for (int j = 0; j < film.getGenres().size(); j++) { // todo цикл для проверки и если нет, записи жанра
                    Genres genre = new Genres(film.getGenres().get(j).getGenre());
                    addFilmGenresST.setInt(1, film.getFilmId().intValue());
                    addFilmGenresST.setString(2, genre.getGenre());
                    addFilmGenresST.addBatch();

                }

        }
        addFilmST.executeBatch();
        addFilmCountriesST.executeBatch();
        addFilmGenresST.executeBatch();


        connection.commit();
        connection.close();
    }
}
