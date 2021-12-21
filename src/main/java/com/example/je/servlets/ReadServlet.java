package com.example.je.servlets;

import com.example.je.model.Film;
import com.example.je.services.CountryService;
import com.example.je.services.FilmService;
import com.example.je.services.GenreService;
import com.google.gson.Gson;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;
import java.util.stream.Collectors;

public class ReadServlet extends HttpServlet {

    private final FilmService filmService = FilmService.getService();
    private final CountryService countryService = CountryService.getService();
    private final GenreService genreService = GenreService.getService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        Long getId = Long.valueOf(req.getReader().lines().collect(Collectors.toList()).get(0));

        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        Film film = filmService.getFilm(getId);
        if (Objects.nonNull(film)) {
            film.setCountries(countryService.get(getId.intValue()));
            film.setGenres(genreService.get(getId.intValue()));

            String filmJsonString = new Gson().toJson(film);
            resp.getWriter().write(filmJsonString);
        }
        else {
            resp.getWriter().write("No film with such Id");
        }
    }
}
