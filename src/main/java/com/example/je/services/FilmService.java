package com.example.je.services;

import com.example.je.dao.FilmDAO;
import com.example.je.model.Film;
import com.example.je.model.FilmCountryGenre;
import com.example.je.model.Page;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Objects;
import java.util.Properties;
import java.util.stream.Collectors;

public class FilmService {

    private static FilmService filmService = null;

    private final PageService pageService;

    private final FilmDAO filmDAO;

    private FilmService(FilmDAO filmDAO, PageService pageService) {
        System.out.println("filmservice init");
        if (Objects.isNull(filmDAO) && Objects.isNull(pageService)) {
            this.filmDAO = FilmDAO.getDAO();
            this.pageService = PageService.getService();
        }
        else {
            this.filmDAO = filmDAO;
            this.pageService = pageService;
        }
    }

    public static FilmService getService() {
        if (filmService == null) {
            filmService = new FilmService(null, null);
        }
        return filmService;
    }

    public List<Film> loadFilms() {
        int pageNumber = 0;
        int end;
        Page page = new Page();

        Properties props = new Properties();
        ClassLoader classloader = Thread.currentThread().getContextClassLoader();
        InputStream inputStream = classloader.getResourceAsStream("application.properties");
        try {
            props.load(inputStream);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

        do {
            pageNumber++;

            try {
                URL urldemo = new URL(props.getProperty("kp.url") + pageNumber);

                URLConnection yc = urldemo.openConnection();
                yc.setRequestProperty("X-API-KEY", props.getProperty("kp.key"));

                BufferedReader in = new BufferedReader(new InputStreamReader(yc.getInputStream(), StandardCharsets.UTF_8));
                String inputLine = in.readLine();
                ObjectMapper mapper = new ObjectMapper();
                page.setPagesCount(mapper.readValue(inputLine, Page.class).getPagesCount());
                pageService.addFilms(page, mapper.readValue(inputLine, Page.class).getFilms());

            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
            end = page.getPagesCount().intValue();
        } while (pageNumber < end);

        page.setFilms(page.getFilms().stream().distinct().collect(Collectors.toList()));

        return page.getFilms();
    }

    public List<FilmCountryGenre> saveFilms(List<Film> films) {
        return filmDAO.saveAllFilms(films);
    }

    public Film getFilm(Long getIndex) {
        return filmDAO.getFilm(getIndex);
    }

    public void deleteFilm(Long delIndex) {
        filmDAO.deleteFilm(delIndex);
    }
}
