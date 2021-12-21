package com.example.je.services;

import com.example.je.MyConnection;
import com.example.je.model.Film;
import com.example.je.model.FilmCountryGenre;
import com.example.je.model.Genre;
import org.h2.tools.RunScript;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class GenreServiceTest {

    GenreService genreService = GenreService.getService();
    FilmService filmService = FilmService.getService();

    private static Connection connection;

    static {
        try {
            connection = MyConnection.getConnection();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @BeforeAll
    static void beforeAll() throws SQLException {
        ClassLoader classloader = Thread.currentThread().getContextClassLoader();
        InputStream schemaIS = classloader.getResourceAsStream("schema.sql");
        InputStream dataIS = classloader.getResourceAsStream("data.sql");

        if (schemaIS != null) {
            RunScript.execute(connection, new InputStreamReader(schemaIS));
        }
        if (dataIS != null) {
            RunScript.execute(connection, new InputStreamReader(dataIS));
        }
    }

    @AfterAll
    static void afterAll() throws SQLException {
        ClassLoader classloader = Thread.currentThread().getContextClassLoader();
        InputStream clearIS = classloader.getResourceAsStream("clear.sql");
        if (clearIS != null) {
            RunScript.execute(connection, new InputStreamReader(clearIS));
        }
    }

    private FilmCountryGenre newData(Long filmId, Boolean isExist) {
        List<Genre> genreList = new ArrayList<>();
        genreList.add(new Genre("драма"));
        genreList.add(new Genre("детектив"));
        return FilmCountryGenre.builder()
                .filmId(filmId)
                .genreList(genreList)
                .isExist(isExist)
                .build();

    }

    @Test
    @DisplayName("Add genres of film to db by film id")
    void addCountry() {
        List<FilmCountryGenre> filmCountryGenreList = new ArrayList<>();
        filmCountryGenreList.add(newData(373L, false));
        filmCountryGenreList.add(newData(625L, false));

        List<Film> filmList = new ArrayList<>();
        filmList.add(Film.builder().filmId(373L).nameEn("Test film 1").ratingVoteCount(613L).build());
        filmList.add(Film.builder().filmId(625L).nameEn("Test film 2").ratingVoteCount(613L).build());
        filmService.saveFilms(filmList);

        genreService.saveGenre(filmCountryGenreList);
        assertAll(() -> assertEquals(newData(null, null).getGenreList(), genreService.get(373)),
                () -> assertEquals(newData(null, null).getGenreList(), genreService.get(625)));
    }

    @Test
    @DisplayName("Update genres of film in db by film id")
    void updateCountry() {
        List<FilmCountryGenre> filmCountryGenreList = new ArrayList<>();
        filmCountryGenreList.add(newData(6695L, true));

        genreService.saveGenre(filmCountryGenreList);
        assertEquals(newData(null, null).getGenreList(), genreService.get(6695));
    }

    @Test
    @DisplayName("Get genres of film from db by film id")
    void get() {
        List<Genre> genreList = new ArrayList<>();
        genreList.add(new Genre("криминал"));
        genreList.add(new Genre("комедия"));
        assertEquals(genreList, genreService.get(10179));
    }

    @Test
    @DisplayName("Delete genres of film from db by film id")
    void delete() {
        genreService.delete(5619);
        assertNull(genreService.get(5619));
    }
}