package com.example.je.dao;

import com.example.je.Queries;
import com.example.je.db.impl.MySQLFactory;
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

    private final MySQLFactory factory = new MySQLFactory();

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
        try (Connection connection = factory.getConnection("mysql");
             PreparedStatement FilmGenresStatement = connection.prepareStatement(Queries.INSERT_GENRE_IN_FILM)) {

            connection.setAutoCommit(false);
            for (FilmCountryGenre filmCountryGenre : filmCountryGenreList) {

                if (filmCountryGenre.getIsExist()) {
                    deleteGenre(filmCountryGenre.getFilmId().intValue());

                    for (Genre genre : filmCountryGenre.getGenreList()) {
                        FilmGenresStatement.setInt(1, filmCountryGenre.getFilmId().intValue());
                        FilmGenresStatement.setString(2, genre.getGenre());
                        FilmGenresStatement.addBatch();
                    }
                } else {
                    for (Genre genre : filmCountryGenre.getGenreList()) {
                        FilmGenresStatement.setInt(1, filmCountryGenre.getFilmId().intValue());
                        FilmGenresStatement.setString(2, genre.getGenre());
                        FilmGenresStatement.addBatch();
                    }
                }
            }

            FilmGenresStatement.executeBatch();
            connection.commit();

        } catch (SQLException throwables) {
            System.out.println(throwables.getMessage());
        }
    }

    public List<Genre> getGenre(int filmId) {
        List<Genre> genres = new ArrayList<>();
        try (Connection connection = factory.getConnection("mysql");
             PreparedStatement getFilmGenresStatement = factory.getConnection("mysql").prepareStatement(Queries.GET_GENRE_IN_FILM)) {

            getFilmGenresStatement.setInt(1, filmId);
            ResultSet resultSetGenre = getFilmGenresStatement.executeQuery();
            connection.close();

            while (resultSetGenre.next())
                genres.add(new Genre(resultSetGenre.getString("name")));

        } catch (SQLException throwables) {
            System.out.println(throwables.getMessage());
        }

        return genres;
    }

    public void deleteGenre(int filmId) {
        try (Connection connection = factory.getConnection("mysql");
             PreparedStatement deleteFilmGenresStatement = connection.prepareStatement(Queries.DELETE_GENRE_IN_FILM)) {

            deleteFilmGenresStatement.setInt(1, filmId);
            deleteFilmGenresStatement.execute();

        } catch (SQLException throwables) {
            System.out.println(throwables.getMessage());
        }

    }
}
