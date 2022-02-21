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

public class FilmServlet extends HttpServlet {

    private final FilmService filmService = FilmService.getService();
    private final CountryService countryService = CountryService.getService();
    private final GenreService genreService = GenreService.getService();

    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
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

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        long banId = Long.parseLong(req.getHttpServletMapping().getMatchValue());
        if (banId > 0) {
            filmService.banFilm(banId);
        }
        else {
            resp.getWriter().write("Error, id should be positive!");
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        Long deleteId = Long.valueOf(req.getReader().lines().collect(Collectors.toList()).get(0));
        countryService.delete(deleteId.intValue());
        genreService.delete(deleteId.intValue());
        filmService.deleteFilm(deleteId);
    }

}

