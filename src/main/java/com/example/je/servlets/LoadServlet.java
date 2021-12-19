package com.example.je.servlets;

import com.example.je.model.Film;
import com.example.je.model.FilmCountryGenre;
import com.example.je.services.CountryService;
import com.example.je.services.FilmService;
import com.example.je.services.GenreService;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class LoadServlet extends HttpServlet {

    private final FilmService filmService = FilmService.getService();
    private final CountryService countryService = CountryService.getService();
    private final GenreService genreService = GenreService.getService();

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {

        try {
            List<Film> films = filmService.loadFilms();
            List<FilmCountryGenre> filmCountryGenreList = filmService.saveFilms(films);
            countryService.saveCountry(filmCountryGenreList);
            genreService.saveGenre(filmCountryGenreList);
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}

