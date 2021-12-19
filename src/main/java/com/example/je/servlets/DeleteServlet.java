package com.example.je.servlets;

import com.example.je.services.CountryService;
import com.example.je.services.FilmService;
import com.example.je.services.GenreService;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.stream.Collectors;

public class DeleteServlet extends HttpServlet {

    private final FilmService filmService = FilmService.getService();
    private final CountryService countryService = CountryService.getService();
    private final GenreService genreService = GenreService.getService();

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws IOException {

            Long delId = Long.valueOf(req.getReader().lines().collect(Collectors.toList()).get(0));
        try {
            countryService.delete(delId.intValue());
            genreService.delete(delId.intValue());
            filmService.deleteFilm(delId);
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println(e.getMessage());
        }

    }
}
