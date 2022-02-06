package com.example.je.servlets;

import com.example.je.model.Film;
import com.example.je.model.FilmCountryGenre;
import com.example.je.model.FullFilm;
import com.example.je.services.CountryService;
import com.example.je.services.FilmService;
import com.example.je.services.GenreService;
import com.google.gson.GsonBuilder;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Objects;

public class LoadServlet extends HttpServlet {

    private final FilmService filmService = FilmService.getService();
    private final CountryService countryService = CountryService.getService();
    private final GenreService genreService = GenreService.getService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        if(req.getHttpServletMapping().getMatchValue() == null) {

            List<Film> films = filmService.loadFilms();
            List<FilmCountryGenre> filmCountryGenreList = filmService.saveFilms(films);
            countryService.saveCountry(filmCountryGenreList);
            genreService.saveGenre(filmCountryGenreList);

        } else {

            FullFilm fullFilm = filmService.loadFullFilm(Long.decode(req.getHttpServletMapping().getMatchValue()));
            List<FilmCountryGenre> filmCountryGenreList = filmService.saveFilms(fullFilm);
            countryService.saveCountry(filmCountryGenreList);
            genreService.saveGenre(filmCountryGenreList);

            if (Objects.nonNull(fullFilm)) {

                String filmJsonString = new GsonBuilder().serializeNulls().create().toJson(fullFilm);
                resp.getWriter().write(filmJsonString);

            }
            else {
                resp.getWriter().write("No film with such Id");
            }
        }
    }
}
