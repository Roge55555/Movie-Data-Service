package com.example.je.services;

import com.example.je.MyConnection;
import com.example.je.model.Country;
import com.example.je.model.Film;
import com.example.je.model.FilmCountryGenre;
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

class CountryServiceTest {

    CountryService countryService = CountryService.getService();
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
        List<Country> countryList = new ArrayList<>();
        countryList.add(new Country("Мальта"));
        countryList.add(new Country("Бразилия"));
        return FilmCountryGenre.builder()
                .filmId(filmId)
                .countryList(countryList)
                .isExist(isExist)
                .build();

    }

    @Test
    @DisplayName("Add countries of film to db by film id")
    void addCountry() {
        List<FilmCountryGenre> filmCountryGenreList = new ArrayList<>();
        filmCountryGenreList.add(newData(72L, false));
        filmCountryGenreList.add(newData(31L, false));

        List<Film> filmList = new ArrayList<>();
        filmList.add(Film.builder().filmId(72L).nameEn("Test film 1").ratingVoteCount(3000L).build());
        filmList.add(Film.builder().filmId(31L).nameEn("Test film 2").ratingVoteCount(3000L).build());
        filmService.saveFilms(filmList);

        countryService.saveCountry(filmCountryGenreList);
        assertAll(() -> assertEquals(newData(null, null).getCountryList(), countryService.get(31)),
                () -> assertEquals(newData(null, null).getCountryList(), countryService.get(72)));
    }

    @Test
    @DisplayName("Update countries of film in db by film id")
    void updateCountry() {
        List<FilmCountryGenre> filmCountryGenreList = new ArrayList<>();
        filmCountryGenreList.add(newData(3442L, true));

        countryService.saveCountry(filmCountryGenreList);
        assertEquals(newData(null, null).getCountryList(), countryService.get(3442));
    }

    @Test
    @DisplayName("Get countries of film from db by film id")
    void get() {
        List<Country> countryList = new ArrayList<>();
        countryList.add(new Country("США"));
        countryList.add(new Country("Новая Зеландия"));
        assertEquals(countryList, countryService.get(328));
    }

    @Test
    @DisplayName("Delete countries of film from db by film id")
    void delete() {
        countryService.delete(688);
        assertNull(countryService.get(688));
    }
}