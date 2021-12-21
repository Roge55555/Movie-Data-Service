package com.example.je.services;

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

public class GenreService {

    private static GenreService genreService = null;

    private GenreService() {
        System.out.println("genreservice init");
    }

    public static GenreService getService() {
        if (genreService == null) {
            genreService = new GenreService();
        }
        return genreService;
    }

    public void saveGenre(List<FilmCountryGenre> filmCountryGenreList) {
        try {
            Connection connection = MyConnection.getConnection();

            PreparedStatement addFilmGenresST = connection.prepareStatement(Queries.INSERT_GENRE_IN_FILM);
            PreparedStatement updateFilmGenresST = connection.prepareStatement(Queries.UPDATE_GENRE_IN_FILM);

            connection.setAutoCommit(false);
            for (FilmCountryGenre filmCountryGenre : filmCountryGenreList) {

                if (filmCountryGenre.getIsExist()) {
                    delete(filmCountryGenre.getFilmId().intValue());

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
            connection.close();

        } catch (SQLException throwables) {
            System.out.println(throwables.getMessage());
        }
    }

    public List<Genre> get(int filmId) {
        List<Genre> genres = new ArrayList<>();
        try {
            Connection connection = MyConnection.getConnection();

            PreparedStatement getFilmGenresST = MyConnection.getConnection().prepareStatement(Queries.GET_GENRE_IN_FILM);
            getFilmGenresST.setInt(1, filmId);
            ResultSet rsg = getFilmGenresST.executeQuery();
            connection.close();

            while (rsg.next())
                genres.add(new Genre(rsg.getString("name")));

        } catch (SQLException throwables) {
            System.out.println(throwables.getMessage());
        }

        if (genres.isEmpty()) {
            return null;
        }

        return genres;
    }

    public void delete(int filmId) {
        try {
            Connection connection = MyConnection.getConnection();

            connection.setAutoCommit(false);
            PreparedStatement delFilmGenresST = connection.prepareStatement(Queries.DELETE_GENRE_IN_FILM);
            delFilmGenresST.setInt(1, filmId);
            delFilmGenresST.addBatch();
            delFilmGenresST.executeBatch();
            connection.commit();
            connection.close();

        } catch (SQLException throwables) {
            System.out.println(throwables.getMessage());
        }

    }
}
