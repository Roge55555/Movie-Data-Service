package com.example.je.services;

import com.example.je.MyConnection;
import com.example.je.Queries;
import com.example.je.dao.Films;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class FilmService {

    public static void saveFilms(List<Films> films) throws ClassNotFoundException, SQLException {

        CountryService.init();
        GenreService.init();

        Connection connection = MyConnection.getConnection();
        connection.setAutoCommit(false);
        PreparedStatement checkST = connection.prepareStatement(Queries.INSERT_CHECK_EXISTING_FILM);
        PreparedStatement addFilmST = connection.prepareStatement(Queries.INSERT_FILM);
        PreparedStatement updateFilmST = connection.prepareStatement(Queries.UPDATE_FILM);

        for(Films film : films) { //todo цикл записи по 1 фильму фильмов

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

                CountryService.update(film.getCountries(), film.getFilmId().intValue());

                GenreService.update(film.getGenres(), film.getFilmId().intValue());

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

                // todo записи стран
                CountryService.add(film.getCountries(), film.getFilmId().intValue());

                // todo цикл для жанра
                GenreService.add(film.getGenres(), film.getFilmId().intValue());

            }

        }

        connection.close();

        CountryService.execute();
        GenreService.execute();

    }

    public static Films getFilm(Long getIndex) throws ClassNotFoundException, SQLException {

        Connection connection = MyConnection.getConnection();
        PreparedStatement getFST = connection.prepareStatement(Queries.GET_FILM);
        getFST.setInt(1, getIndex.intValue());
        ResultSet rsf = getFST.executeQuery();

        if(rsf.next()) {

            Films film = Films.builder()
                    .filmId((long) rsf.getInt("id"))
                    .nameRu(rsf.getString("name_ru"))
                    .nameEn(rsf.getString("name_en"))
                    .year(rsf.getString("year"))
                    .filmLength(rsf.getString("length"))
                    .rating(rsf.getString("rating"))
                    .ratingVoteCount((long) rsf.getInt("rating_vote_count"))
                    .posterUrl(rsf.getString("poster_url"))
                    .posterUrlPreview(rsf.getString("poster_url_preview")).build();

            film.setCountries(CountryService.get(getIndex.intValue()));

            film.setGenres(GenreService.get(getIndex.intValue()));

            connection.close();

            return film;
        }
        else {
            System.out.println("No such film - " + getIndex);

            connection.close();

            return null;
        }
    }

    public static void deleteFilm(Long delIndex) throws ClassNotFoundException, SQLException {

        CountryService.delete(delIndex.intValue());

        GenreService.delete(delIndex.intValue());

        Connection connection = MyConnection.getConnection();
        connection.setAutoCommit(false);
        PreparedStatement delF = connection.prepareStatement(Queries.DELETE_FILM);
        delF.setInt(1, delIndex.intValue());
        delF.addBatch();
        delF.executeBatch();
        connection.commit();
        connection.close();

    }
}
