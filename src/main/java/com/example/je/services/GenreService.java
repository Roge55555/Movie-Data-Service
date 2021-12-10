package com.example.je.services;

import com.example.je.MyConnection;
import com.example.je.Queries;
import com.example.je.dao.Genres;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class GenreService {

    public static void add(List<Genres> genresList, int filmId) throws SQLException {
        Connection connection = MyConnection.getConnection();
        connection.setAutoCommit(false);
        PreparedStatement addFilmGenresST = connection.prepareStatement(Queries.INSERT_GENRE_IN_FILM);

        for (Genres genre : genresList) {
            addFilmGenresST.setInt(1, filmId);
            addFilmGenresST.setString(2, genre.getGenre());
            addFilmGenresST.addBatch();
        }

        addFilmGenresST.executeBatch();

        connection.commit();
        connection.close();
    }

    public static List<Genres> get(int filmId) throws SQLException {
        Connection connection = MyConnection.getConnection();
        PreparedStatement getFilmGenresST = MyConnection.getConnection().prepareStatement(Queries.GET_GENRE_IN_FILM);
        getFilmGenresST.setInt(1, filmId);
        ResultSet rsg = getFilmGenresST.executeQuery();
        connection.close();

        List<Genres> genres = new ArrayList<>();
        while (rsg.next())
            genres.add(new Genres(rsg.getString("name")));

        return genres;

    }

    public static void update(List<Genres> genresList, int filmId) throws SQLException {
        Connection connection = MyConnection.getConnection();
        connection.setAutoCommit(false);
        PreparedStatement updateFilmGenresST = connection.prepareStatement(Queries.UPDATE_GENRE_IN_FILM);

        //updateFilmCountriesST.addBatch("delete from film_genres where film_id = " + film.getFilmId().intValue());
        delete(filmId);

        for (Genres genre : genresList) {
            updateFilmGenresST.setInt(1, filmId);
            updateFilmGenresST.setString(2, genre.getGenre());
            updateFilmGenresST.addBatch();
        }

        updateFilmGenresST.executeBatch();
        connection.commit();
        connection.close();

    }

    public static void delete(int filmId) throws SQLException {
        Connection connection = MyConnection.getConnection();
        connection.setAutoCommit(false);
        PreparedStatement delFilmGenresST = connection.prepareStatement(Queries.DELETE_GENRE_IN_FILM);
        delFilmGenresST.setInt(1, filmId);
        delFilmGenresST.addBatch();
        delFilmGenresST.executeBatch();
        connection.commit();
        connection.close();

    }
}
