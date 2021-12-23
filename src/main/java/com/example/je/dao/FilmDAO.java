package com.example.je.dao;

import com.example.je.MyConnection;
import com.example.je.Queries;
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

        try (Connection connection = MyConnection.getConnection();
             PreparedStatement checkST = connection.prepareStatement(Queries.INSERT_CHECK_EXISTING_FILM);
             PreparedStatement addFilmST = connection.prepareStatement(Queries.INSERT_FILM);
             PreparedStatement updateFilmST = connection.prepareStatement(Queries.UPDATE_FILM)) {

            connection.setAutoCommit(false);


            for (Film film : films) {

                checkST.setString(1, film.getNameEn());
                checkST.setString(2, film.getNameRu());
                checkST.setInt(3, film.getFilmId().intValue());
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
                    updateFilmST.addBatch();

                    updateFilmST.executeBatch();
                    connection.commit();

                    filmCountryGenreList.add(new FilmCountryGenre(film.getFilmId(), film.getCountries(), film.getGenres(), true));

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
                    addFilmST.addBatch();

                    addFilmST.executeBatch();
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
        try (Connection connection = MyConnection.getConnection();
             PreparedStatement getFST = connection.prepareStatement(Queries.GET_FILM)){

            getFST.setInt(1, filmId.intValue());
            ResultSet rsf = getFST.executeQuery();

            if (rsf.next()) {

                return Film.builder()
                        .filmId((long) rsf.getInt("id"))
                        .nameRu(rsf.getString("name_ru"))
                        .nameEn(rsf.getString("name_en"))
                        .year(rsf.getString("year"))
                        .filmLength(rsf.getString("length"))
                        .rating(rsf.getString("rating"))
                        .ratingVoteCount((long) rsf.getInt("rating_vote_count"))
                        .posterUrl(rsf.getString("poster_url"))
                        .posterUrlPreview(rsf.getString("poster_url_preview")).build();
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
        try (Connection connection = MyConnection.getConnection();
             PreparedStatement delF = connection.prepareStatement(Queries.DELETE_FILM)){

            delF.setInt(1, filmId.intValue());
            delF.execute();

        } catch (SQLException throwables) {
            System.out.println(throwables.getMessage());
        }
    }
}
