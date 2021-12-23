package com.example.je.dao;

import com.example.je.MyConnection;
import com.example.je.Queries;
import com.example.je.model.FilmCountryGenre;
import com.example.je.model.Genre;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class GenreDAO {

    private static GenreDAO genreDAO = null;

    private GenreDAO() {
        System.out.println("genredao init");
    }

    public static GenreDAO getDAO() {
        if (genreDAO == null) {
            genreDAO = new GenreDAO();
        }
        return genreDAO;
    }

    public void saveAllGenres(List<FilmCountryGenre> filmCountryGenreList) {
        try (Connection connection = MyConnection.getConnection();
             PreparedStatement addFilmGenresST = connection.prepareStatement(Queries.INSERT_GENRE_IN_FILM);
             PreparedStatement updateFilmGenresST = connection.prepareStatement(Queries.UPDATE_GENRE_IN_FILM)) {

            connection.setAutoCommit(false);
            for (FilmCountryGenre filmCountryGenre : filmCountryGenreList) {

                if (filmCountryGenre.getIsExist()) {
                    deleteGenre(filmCountryGenre.getFilmId().intValue());

                    for (Genre genre : filmCountryGenre.getGenreList()) {
                        updateFilmGenresST.setInt(1, filmCountryGenre.getFilmId().intValue());
                        updateFilmGenresST.setString(2, genre.getGenre());
                        updateFilmGenresST.addBatch();
                    }
                } else {
                    for (Genre genre : filmCountryGenre.getGenreList()) {
                        addFilmGenresST.setInt(1, filmCountryGenre.getFilmId().intValue());
                        addFilmGenresST.setString(2, genre.getGenre());
                        addFilmGenresST.addBatch();
                    }
                }
            }

            addFilmGenresST.executeBatch();
            updateFilmGenresST.executeBatch();
            connection.commit();

        } catch (SQLException throwables) {
            System.out.println(throwables.getMessage());
        }
    }

    public List<Genre> getGenre(int filmId) {
        List<Genre> genres = new ArrayList<>();
        try (Connection connection = MyConnection.getConnection();
             PreparedStatement getFilmGenresST = MyConnection.getConnection().prepareStatement(Queries.GET_GENRE_IN_FILM)) {

            getFilmGenresST.setInt(1, filmId);
            ResultSet rsg = getFilmGenresST.executeQuery();
            connection.close();

            while (rsg.next())
                genres.add(new Genre(rsg.getString("name")));

        } catch (SQLException throwables) {
            System.out.println(throwables.getMessage());
        }

        return genres;
    }

    public void deleteGenre(int filmId) {
        try (Connection connection = MyConnection.getConnection();
             PreparedStatement delFilmGenresST = connection.prepareStatement(Queries.DELETE_GENRE_IN_FILM)) {

            connection.setAutoCommit(false);

            delFilmGenresST.setInt(1, filmId);
            delFilmGenresST.addBatch();
            delFilmGenresST.executeBatch();
            connection.commit();

        } catch (SQLException throwables) {
            System.out.println(throwables.getMessage());
        }

    }
}
