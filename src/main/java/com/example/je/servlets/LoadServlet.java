package com.example.je.servlets;

import com.example.je.model.Film;
import com.example.je.model.FilmCountryGenre;
import com.example.je.services.CountryService;
import com.example.je.services.FilmService;
import com.example.je.services.GenreService;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class LoadServlet extends HttpServlet {

    private final FilmService filmService = FilmService.getService();
    private final CountryService countryService = CountryService.getService();
    private final GenreService genreService = GenreService.getService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
        List<Film> films = filmService.loadFilms();
        List<FilmCountryGenre> filmCountryGenreList = filmService.saveFilms(films);
        countryService.saveCountry(filmCountryGenreList);
        genreService.saveGenre(filmCountryGenreList);
    }
}
