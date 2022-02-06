package com.example.je.dao;

import com.example.je.Queries;
import com.example.je.db.ConnectionFactory;
import com.example.je.db.impl.MySQLFactory;
import com.example.je.model.Film;
import com.example.je.model.FilmCountryGenre;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class FilmDAO {

    private static FilmDAO filmDAO = null;

    private final ConnectionFactory factory = new MySQLFactory();

    private FilmDAO() {
        System.out.println("filmdao init");
    }

    public static FilmDAO getDAO() {
        if (filmDAO == null) {
            filmDAO = new FilmDAO();
        }
        return filmDAO;
    }

    public List<FilmCountryGenre> saveAllFilms(List<Film> films) {
        List<FilmCountryGenre> filmCountryGenreList = new ArrayList<>();

        try (Connection connection = factory.getConnection("mysql");
             PreparedStatement checkStatement = connection.prepareStatement(Queries.INSERT_CHECK_EXISTING_FILM);
             PreparedStatement addFilmStatement = connection.prepareStatement(Queries.INSERT_FILM);
             PreparedStatement updateFilmStatement = connection.prepareStatement(Queries.UPDATE_FILM)) {

            connection.setAutoCommit(false);


            for (Film film : films) {

                checkStatement.setString(1, film.getNameEn());
                checkStatement.setString(2, film.getNameRu());
                checkStatement.setInt(3, film.getFilmId().intValue());
                ResultSet resultSetFilm = checkStatement.executeQuery();

                if (resultSetFilm.next()) {
                    updateFilmStatement.setInt(9, film.getFilmId().intValue());
                    updateFilmStatement.setString(1, film.getNameRu());
                    updateFilmStatement.setString(2, film.getNameEn());
                    updateFilmStatement.setString(3, film.getYear());
                    updateFilmStatement.setString(4, film.getFilmLength());
                    updateFilmStatement.setString(5, film.getRating());
                    updateFilmStatement.setInt(6, film.getRatingVoteCount().intValue());
                    updateFilmStatement.setString(7, film.getPosterUrl());
                    updateFilmStatement.setString(8, film.getPosterUrlPreview());
                    updateFilmStatement.addBatch();

                    updateFilmStatement.executeBatch();
                    connection.commit();

                    filmCountryGenreList.add(new FilmCountryGenre(film.getFilmId(), film.getCountries(), film.getGenres(), true));

                } else {

                    addFilmStatement.setInt(1, film.getFilmId().intValue());
                    addFilmStatement.setString(2, film.getNameRu());
                    addFilmStatement.setString(3, film.getNameEn());
                    addFilmStatement.setString(4, film.getYear());
                    addFilmStatement.setString(5, film.getFilmLength());
                    addFilmStatement.setString(6, film.getRating());
                    addFilmStatement.setInt(7, film.getRatingVoteCount().intValue());
                    addFilmStatement.setString(8, film.getPosterUrl());
                    addFilmStatement.setString(9, film.getPosterUrlPreview());
                    addFilmStatement.setBoolean(10, false);
                    addFilmStatement.addBatch();

                    addFilmStatement.executeBatch();
                    connection.commit();

                    filmCountryGenreList.add(new FilmCountryGenre(film.getFilmId(), film.getCountries(), film.getGenres(), false));

                }
            }

        } catch (SQLException throwables) {
            System.out.println(throwables.getMessage());
        }

        return filmCountryGenreList;
    }
    public Film getFilm(Long filmId) {
        try (Connection connection = factory.getConnection("mysql");
             PreparedStatement getFilmStatement = connection.prepareStatement(Queries.GET_FILM)){

            getFilmStatement.setInt(1, filmId.intValue());
            ResultSet resultSetFilm = getFilmStatement.executeQuery();

            if (resultSetFilm.next()) {

                return Film.builder()
                        .filmId((long) resultSetFilm.getInt("id"))
                        .nameRu(resultSetFilm.getString("name_ru"))
                        .nameEn(resultSetFilm.getString("name_en"))
                        .year(resultSetFilm.getString("year"))
                        .filmLength(resultSetFilm.getString("length"))
                        .rating(resultSetFilm.getString("rating"))
                        .ratingVoteCount((long) resultSetFilm.getInt("rating_vote_count"))
                        .posterUrl(resultSetFilm.getString("poster_url"))
                        .posterUrlPreview(resultSetFilm.getString("poster_url_preview")).build();
            }
            else {
                System.out.println("No such film - " + filmId);

                return null;
            }

        } catch (SQLException throwables) {
            System.out.println(throwables.getMessage());
        }

        return null;
    }
    public void deleteFilm(Long filmId) {
        try (Connection connection = factory.getConnection("mysql");
             PreparedStatement deleteFilmStatement = connection.prepareStatement(Queries.DELETE_FILM)){

            deleteFilmStatement.setInt(1, filmId.intValue());
            deleteFilmStatement.execute();

        } catch (SQLException throwables) {
            System.out.println(throwables.getMessage());
        }
    }
}
