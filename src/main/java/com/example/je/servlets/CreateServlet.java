package com.example.je.servlets;

import com.example.je.model.Film;
import com.example.je.model.FilmCountryGenre;
import com.example.je.model.Page;
import com.example.je.services.CountryService;
import com.example.je.services.FilmService;
import com.example.je.services.GenreService;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.sql.SQLException;
import java.util.List;
import java.util.Objects;

public class CreateServlet extends HttpServlet {

    private final FilmService filmService = FilmService.getService();
    private final CountryService countryService = CountryService.getService();
    private final GenreService genreService = GenreService.getService();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(req.getInputStream(), StandardCharsets.UTF_8));
        StringBuilder inputLine = new StringBuilder();
        while (in.ready())
            inputLine.append(in.readLine());
        ObjectMapper mapper = new ObjectMapper();
        try {
            List<Film> films = mapper.readValue(inputLine.toString(), Page.class).getFilms();
            if (Objects.nonNull(films)) {
            List<FilmCountryGenre> filmCountryGenreList = filmService.saveFilms(films);
            countryService.saveCountry(filmCountryGenreList);
            genreService.saveGenre(filmCountryGenreList);
            }
            else {
                resp.getWriter().write("No data!");
            }
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}
