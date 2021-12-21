package com.example.je.services;

import com.example.je.MyConnection;
import com.example.je.model.Film;
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

class FilmServiceTest {

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

    private Film newFilm(Long id, String nameRu, String nameEn) {

        return Film.builder()
                .filmId(id)
                .nameRu(nameRu)
                .nameEn(nameEn)
                .year("2013")
                .filmLength("09:26")
                .rating("7.7")
                .ratingVoteCount(358561L)
                .posterUrl(null)
                .posterUrlPreview(null)
                .build();
    }

    @Test
    @DisplayName("Load all 250 films in list")
    void loadFilms() {
        List<Film> top250;

        top250 = filmService.loadFilms();
        assertEquals(250, top250.size());
    }

    @Test
    @DisplayName("Add film in db")
    void addFilms() {
        List<Film> filmsAdd = new ArrayList<>();
        filmsAdd.add(newFilm(8L, null, "4400"));

        filmService.saveFilms(filmsAdd);
        assertEquals(newFilm(8L, null, "4400"), filmService.getFilm(8L));
    }

    @Test
    @DisplayName("Update film in db")
    void updateFilms() {
        Film updateFilm = filmService.getFilm(361L);
        updateFilm.setNameEn("fight club");
        updateFilm.setFilmLength("02:31");
        List<Film> filmsUpdate = new ArrayList<>();
        filmsUpdate.add(updateFilm);

        filmService.saveFilms(filmsUpdate);
        assertEquals(updateFilm, filmService.getFilm(361L));
    }

    @Test
    @DisplayName("Get film from db by id")
    void getFilm() {
        List<Film> filmsGet = new ArrayList<>();
        filmsGet.add(newFilm(6L, null, "12 chairs"));
        filmsGet.add(newFilm(13L, "Король лев", null));
        filmsGet.add(newFilm(92L, "За гранью", null));
        filmService.saveFilms(filmsGet);

        assertAll(() -> assertEquals(filmsGet.get(0), filmService.getFilm(6L)),
                () -> assertEquals(filmsGet.get(1), filmService.getFilm(13L)),
                () -> assertEquals(filmsGet.get(2), filmService.getFilm(92L)));
    }

    @Test
    @DisplayName("Delete film from db by id")
    void deleteFilm() {
        List<Film> filmsGet = new ArrayList<>();
        filmsGet.add(newFilm(64L, "А зори здесь тихие", null));
        filmService.saveFilms(filmsGet);

        filmService.deleteFilm(64L);
        assertNull(filmService.getFilm(64L));
    }
}