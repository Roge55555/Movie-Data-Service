package com.example.je.dao;

import com.example.je.Queries;
import com.example.je.db.ConnectionFactory;
import com.example.je.db.impl.MySQLFactory;
import com.example.je.model.FilmCountryGenre;
import com.example.je.model.FullFilm;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class FullFilmDAO {

    private static FullFilmDAO filmDAO = null;

    private final ConnectionFactory factory = new MySQLFactory();

    private FullFilmDAO() {
        System.out.println("fullfilmdao init");
    }

    public static FullFilmDAO getDAO() {
        if (filmDAO == null) {
            filmDAO = new FullFilmDAO();
        }
        return filmDAO;
    }

    public List<FilmCountryGenre> saveFullFilm(FullFilm fullFilm) {
        List<FilmCountryGenre> filmCountryGenreList = new ArrayList<>();

        try (Connection connection = factory.getConnection("mysql");
             PreparedStatement checkStatement = connection.prepareStatement(Queries.INSERT_CHECK_EXISTING_FULL_FILM);
             PreparedStatement addFilmStatement = connection.prepareStatement(Queries.INSERT_FULL_FILM);
             PreparedStatement updateFilmStatement = connection.prepareStatement(Queries.UPDATE_FULL_FILM)) {

            connection.setAutoCommit(false);


            checkStatement.setString(1, fullFilm.getNameEn());
            checkStatement.setString(2, fullFilm.getNameRu());
            checkStatement.setInt(3, fullFilm.getKinopoiskId().intValue());
            ResultSet resultSetFilm = checkStatement.executeQuery();

            if (resultSetFilm.next()) {
                updateFilmStatement.setInt(40, fullFilm.getKinopoiskId().intValue());
                updateFilmStatement.setString(1, fullFilm.getImdbId());
                updateFilmStatement.setString(2, fullFilm.getNameRu());
                updateFilmStatement.setString(3, fullFilm.getNameEn());
                updateFilmStatement.setString(4, fullFilm.getNameOriginal());
                updateFilmStatement.setString(5, fullFilm.getPosterUrl());
                updateFilmStatement.setString(6, fullFilm.getPosterUrlPreview());
                updateFilmStatement.setInt(7, fullFilm.getReviewsCount().intValue());
                updateFilmStatement.setObject(8, fullFilm.getRatingGoodReview(), Types.DOUBLE);
                updateFilmStatement.setInt(9, fullFilm.getRatingGoodReviewVoteCount().intValue());
                updateFilmStatement.setObject(10, fullFilm.getRatingKinopoisk(), Types.DOUBLE);
                updateFilmStatement.setInt(11, fullFilm.getRatingKinopoiskVoteCount().intValue());
                updateFilmStatement.setObject(12, fullFilm.getRatingImdb(), Types.DOUBLE);
                updateFilmStatement.setInt(13, fullFilm.getRatingImdbVoteCount().intValue());
                updateFilmStatement.setObject(14, fullFilm.getRatingFilmCritics(), Types.DOUBLE);
                updateFilmStatement.setInt(15, fullFilm.getRatingFilmCriticsVoteCount().intValue());
                updateFilmStatement.setObject(16, fullFilm.getRatingAwait(), Types.DOUBLE);
                updateFilmStatement.setInt(17, fullFilm.getRatingAwaitCount().intValue());
                updateFilmStatement.setObject(18, fullFilm.getRatingRfCritics(), Types.DOUBLE);
                updateFilmStatement.setInt(19, fullFilm.getRatingRfCriticsVoteCount().intValue());
                updateFilmStatement.setString(20, fullFilm.getWebUrl());
                updateFilmStatement.setInt(21, fullFilm.getYear().intValue());
                updateFilmStatement.setInt(22, fullFilm.getFilmLength().intValue());
                updateFilmStatement.setString(23, fullFilm.getSlogan());
                updateFilmStatement.setString(24, fullFilm.getDescription());
                updateFilmStatement.setString(25, fullFilm.getShortDescription());
                updateFilmStatement.setString(26, fullFilm.getEditorAnnotation());
                updateFilmStatement.setBoolean(27, fullFilm.getIsTicketsAvailable());
                updateFilmStatement.setString(28, fullFilm.getProductionStatus());
                updateFilmStatement.setString(29, fullFilm.getType());
                updateFilmStatement.setString(30, fullFilm.getRatingMpaa());
                updateFilmStatement.setString(31, fullFilm.getRatingAgeLimits());
                updateFilmStatement.setObject(32, fullFilm.getStartYear(), Types.INTEGER);
                updateFilmStatement.setObject(33, fullFilm.getEndYear(), Types.INTEGER);
                updateFilmStatement.setBoolean(34, fullFilm.getSerial());
                updateFilmStatement.setBoolean(35, fullFilm.getShortFilm());
                updateFilmStatement.setBoolean(36, fullFilm.getCompleted());
                updateFilmStatement.setBoolean(37, fullFilm.getHasImax());
                updateFilmStatement.setBoolean(38, fullFilm.getHas3D());
                updateFilmStatement.setString(39, fullFilm.getLastSync());
                updateFilmStatement.addBatch();

                updateFilmStatement.executeBatch();
                connection.commit();

                filmCountryGenreList.add(new FilmCountryGenre(fullFilm.getKinopoiskId(), fullFilm.getCountries(), fullFilm.getGenres(), true));

            } else {

                addFilmStatement.setInt(1, fullFilm.getKinopoiskId().intValue());
                addFilmStatement.setString(2, fullFilm.getImdbId());
                addFilmStatement.setString(3, fullFilm.getNameRu());
                addFilmStatement.setString(4, fullFilm.getNameEn());
                addFilmStatement.setString(5, fullFilm.getNameOriginal());
                addFilmStatement.setString(6, fullFilm.getPosterUrl());
                addFilmStatement.setString(7, fullFilm.getPosterUrlPreview());
                addFilmStatement.setInt(8, fullFilm.getReviewsCount().intValue());
                addFilmStatement.setObject(9, fullFilm.getRatingGoodReview(), Types.DOUBLE);
                addFilmStatement.setInt(10, fullFilm.getRatingGoodReviewVoteCount().intValue());
                addFilmStatement.setObject(11, fullFilm.getRatingKinopoisk(), Types.DOUBLE);
                addFilmStatement.setInt(12, fullFilm.getRatingKinopoiskVoteCount().intValue());
                addFilmStatement.setObject(13, fullFilm.getRatingImdb(), Types.DOUBLE);
                addFilmStatement.setInt(14, fullFilm.getRatingImdbVoteCount().intValue());
                addFilmStatement.setObject(15, fullFilm.getRatingFilmCritics(), Types.DOUBLE);
                addFilmStatement.setInt(16, fullFilm.getRatingFilmCriticsVoteCount().intValue());
                addFilmStatement.setObject(17, fullFilm.getRatingAwait(), Types.DOUBLE);//null
                addFilmStatement.setInt(18, fullFilm.getRatingAwaitCount().intValue());
                addFilmStatement.setObject(19, fullFilm.getRatingRfCritics(), Types.DOUBLE);
                addFilmStatement.setInt(20, fullFilm.getRatingRfCriticsVoteCount().intValue());
                addFilmStatement.setString(21, fullFilm.getWebUrl());
                addFilmStatement.setInt(22, fullFilm.getYear().intValue());
                addFilmStatement.setInt(23, fullFilm.getFilmLength().intValue());
                addFilmStatement.setString(24, fullFilm.getSlogan());
                addFilmStatement.setString(25, fullFilm.getDescription());//too long
                addFilmStatement.setString(26, fullFilm.getShortDescription());
                addFilmStatement.setString(27, fullFilm.getEditorAnnotation());
                addFilmStatement.setBoolean(28, fullFilm.getIsTicketsAvailable());
                addFilmStatement.setString(29, fullFilm.getProductionStatus());
                addFilmStatement.setString(30, fullFilm.getType());
                addFilmStatement.setString(31, fullFilm.getRatingMpaa());
                addFilmStatement.setString(32, fullFilm.getRatingAgeLimits());
                addFilmStatement.setObject(33, fullFilm.getStartYear(), Types.INTEGER);//null
                addFilmStatement.setObject(34, fullFilm.getEndYear(), Types.INTEGER);//null
                addFilmStatement.setBoolean(35, fullFilm.getSerial());
                addFilmStatement.setBoolean(36, fullFilm.getShortFilm());
                addFilmStatement.setBoolean(37, fullFilm.getCompleted());
                addFilmStatement.setBoolean(38, fullFilm.getHasImax());
                addFilmStatement.setBoolean(39, fullFilm.getHas3D());
                addFilmStatement.setString(40, fullFilm.getLastSync());
                addFilmStatement.addBatch();

                addFilmStatement.executeBatch();
                connection.commit();

                filmCountryGenreList.add(new FilmCountryGenre(fullFilm.getKinopoiskId(), fullFilm.getCountries(), fullFilm.getGenres(), false));

            }


        } catch (SQLException throwables) {
            System.out.println(throwables.getMessage());
        }

        return filmCountryGenreList;
    }

    public FullFilm getFullFilm(Long filmId) {
        try (Connection connection = factory.getConnection("mysql");
             PreparedStatement getFilmStatement = connection.prepareStatement(Queries.GET_FULL_FILM)){

            getFilmStatement.setInt(1, filmId.intValue());
            ResultSet resultSetFilm = getFilmStatement.executeQuery();

            if (resultSetFilm.next()) {

                return FullFilm.builder()
                        .kinopoiskId((long) resultSetFilm.getInt("kinopoisk_id"))
                        .imdbId(resultSetFilm.getString("imdb_id"))
                        .nameRu(resultSetFilm.getString("name_ru"))
                        .nameEn(resultSetFilm.getString("name_en"))
                        .nameOriginal(resultSetFilm.getString("name_original"))
                        .posterUrl(resultSetFilm.getString("poster_url"))
                        .posterUrlPreview(resultSetFilm.getString("poster_url_preview"))
                        .reviewsCount((long) resultSetFilm.getInt("reviews_count"))
                        .ratingGoodReview(resultSetFilm.getDouble("rating_good_review"))
                        .ratingGoodReviewVoteCount((long) resultSetFilm.getInt("rating_good_review_vote_count"))
                        .ratingKinopoisk(resultSetFilm.getDouble("rating_kinopoisk"))
                        .ratingKinopoiskVoteCount((long) resultSetFilm.getInt("rating_kinopoisk_vote_count"))
                        .ratingImdb(resultSetFilm.getDouble("rating_imdb"))
                        .ratingImdbVoteCount((long) resultSetFilm.getInt("rating_imdb_vote_count"))
                        .ratingFilmCritics(resultSetFilm.getDouble("rating_film_critics"))
                        .ratingFilmCriticsVoteCount((long) resultSetFilm.getInt("rating_film_critics_vote_count"))
                        .ratingAwait(resultSetFilm.getDouble("rating_await"))
                        .ratingAwaitCount((long) resultSetFilm.getInt("rating_await_count"))
                        .ratingRfCritics(resultSetFilm.getDouble("rating_rf_critics"))
                        .ratingRfCriticsVoteCount((long) resultSetFilm.getInt("rating_rf_criticsVoteCount"))
                        .webUrl(resultSetFilm.getString("web_url"))
                        .year((long) resultSetFilm.getInt("year"))
                        .filmLength((long) resultSetFilm.getInt("length"))
                        .slogan(resultSetFilm.getString("slogan"))
                        .description(resultSetFilm.getString("description"))
                        .shortDescription(resultSetFilm.getString("short_description"))
                        .editorAnnotation(resultSetFilm.getString("editor_annotation"))
                        .isTicketsAvailable(resultSetFilm.getBoolean("is_tickets_available"))
                        .productionStatus(resultSetFilm.getString("production_status"))
                        .type(resultSetFilm.getString("type"))
                        .ratingMpaa(resultSetFilm.getString("rating_mpaa"))
                        .ratingAgeLimits(resultSetFilm.getString("rating_age_limits"))
                        .startYear((long) resultSetFilm.getInt("start_year"))
                        .endYear((long) resultSetFilm.getInt("end_year"))
                        .serial(resultSetFilm.getBoolean("serial"))
                        .shortFilm(resultSetFilm.getBoolean("short_film"))
                        .completed(resultSetFilm.getBoolean("completed"))
                        .hasImax(resultSetFilm.getBoolean("has_imax"))
                        .has3D(resultSetFilm.getBoolean("has_3d"))
                        .lastSync(resultSetFilm.getString("last_sync")).build();
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
}
