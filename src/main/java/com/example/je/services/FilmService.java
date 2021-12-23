package com.example.je.services;

import com.example.je.MyConnection;
import com.example.je.Queries;
import com.example.je.model.Film;
import com.example.je.model.FilmCountryGenre;
import com.example.je.model.Page;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.stream.Collectors;

public class FilmService {

    private static FilmService filmService = null;

    private final PageService pageService = PageService.getService();

    private FilmService() {
        System.out.println("filmservice init");
    }

    public static FilmService getService() {
        if (filmService == null) {
            filmService = new FilmService();
        }
        return filmService;
    }

    public List<Film> loadFilms() {
        int pageNumber = 0;
        int end;
        Page page = new Page();

        Properties props = new Properties();
        ClassLoader classloader = Thread.currentThread().getContextClassLoader();
        InputStream inputStream = classloader.getResourceAsStream("application.properties");
        try {
            props.load(inputStream);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

        do {
            pageNumber++;

            try {
                URL urldemo = new URL(props.getProperty("kp.url") + pageNumber);

                URLConnection yc = urldemo.openConnection();
                yc.setRequestProperty("X-API-KEY", props.getProperty("kp.key"));

                BufferedReader in = new BufferedReader(new InputStreamReader(yc.getInputStream(), StandardCharsets.UTF_8));
                String inputLine = in.readLine();
                ObjectMapper mapper = new ObjectMapper();
                page.setPagesCount(mapper.readValue(inputLine, Page.class).getPagesCount());
                pageService.addFilms(page, mapper.readValue(inputLine, Page.class).getFilms());

            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
            end = page.getPagesCount().intValue();
        } while (pageNumber < end);

        page.setFilms(page.getFilms().stream().distinct().collect(Collectors.toList()));

        return page.getFilms();
    }

    public List<FilmCountryGenre> saveFilms(List<Film> films) {

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

    public Film getFilm(Long getIndex) {
        try (Connection connection = MyConnection.getConnection();
             PreparedStatement getFST = connection.prepareStatement(Queries.GET_FILM)){

            getFST.setInt(1, getIndex.intValue());
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
                System.out.println("No such film - " + getIndex);

                return null;
            }

        } catch (SQLException throwables) {
            System.out.println(throwables.getMessage());
        }
        return null;
    }

    public void deleteFilm(Long delIndex) {
        try (Connection connection = MyConnection.getConnection();
             PreparedStatement delF = connection.prepareStatement(Queries.DELETE_FILM)){

            delF.setInt(1, delIndex.intValue());
            delF.execute();

        } catch (SQLException throwables) {
            System.out.println(throwables.getMessage());
        }
    }
}
