package com.example.je.services;

import com.example.je.Utils;
import com.example.je.dao.FilmDAO;
import com.example.je.dao.FullFilmDAO;
import com.example.je.model.Film;
import com.example.je.model.FilmCountryGenre;
import com.example.je.model.FullFilm;
import com.example.je.model.Page;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Objects;
import java.util.Properties;
import java.util.stream.Collectors;

public class FilmService {

    private static FilmService filmService = null;

    private final CountryService countryService = CountryService.getService();

    private final GenreService genreService = GenreService.getService();

    private final PageService pageService;

    private final FilmDAO filmDAO;

    private final FullFilmDAO fullFilmDAO;

    private FilmService(FilmDAO filmDAO, PageService pageService, FullFilmDAO fullFilmDAO) {
        System.out.println("filmservice init");
        if (Objects.isNull(filmDAO) && Objects.isNull(fullFilmDAO) && Objects.isNull(pageService)) {
            this.filmDAO = FilmDAO.getDAO();
            this.fullFilmDAO = FullFilmDAO.getDAO();
            this.pageService = PageService.getService();
        }
        else {
            this.filmDAO = filmDAO;
            this.fullFilmDAO = fullFilmDAO;
            this.pageService = pageService;
        }
    }

    public static FilmService getService() {
        if (filmService == null) {
            filmService = new FilmService(null, null, null);
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
                URL urlDemo = new URL(props.getProperty("kp.top250.url") + pageNumber);

                URLConnection urlConnection = urlDemo.openConnection();
                urlConnection.setRequestProperty("X-API-KEY", props.getProperty("kp.key"));

                BufferedReader in = new BufferedReader(new InputStreamReader(urlConnection.getInputStream(), StandardCharsets.UTF_8));
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

    public FullFilm loadFullFilm(Long id) {

        if(Objects.isNull(getFilm(id))) {
            List<Film> films = loadFilms();
            List<FilmCountryGenre> filmCountryGenreList = saveFilms(films);
            countryService.saveCountry(filmCountryGenreList);
            genreService.saveGenre(filmCountryGenreList);
        }

        Properties props = new Properties();
        ClassLoader classloader = Thread.currentThread().getContextClassLoader();
        InputStream inputStream = classloader.getResourceAsStream("application.properties");
        FullFilm fullFilm = new FullFilm();

        try {
            props.load(inputStream);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

        try {
            URL urlDemo = new URL(props.getProperty("kp.film.url") + id);

            URLConnection urlConnection = urlDemo.openConnection();
            urlConnection.setRequestProperty("X-API-KEY", props.getProperty("kp.key"));

            BufferedReader in = new BufferedReader(new InputStreamReader(urlConnection.getInputStream(), StandardCharsets.UTF_8));
            String inputLine = in.readLine();
            ObjectMapper mapper = new ObjectMapper();
            fullFilm = mapper.readValue(inputLine, FullFilm.class);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return fullFilm;
    }

    public List<FilmCountryGenre> saveFilms(List<Film> films) {

        System.out.println("start download posters");

        try {
            Files.createDirectories(Paths.get("/JE/img"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        for (Film film : films) {
            Utils.saveImage(film.getPosterUrl(), film.getNameRu().replaceAll("[ /:*?|<>\"]", ""));
            Utils.saveImage(film.getPosterUrlPreview(), film.getNameRu().replaceAll("[ /:*?|<>\"]", "") + "_preview");

        }

        System.out.println("end download posters");

        return filmDAO.saveAllFilms(films);
    }

    public List<FilmCountryGenre> saveFullFilm(FullFilm fullFilm) {
        return fullFilmDAO.saveFullFilm(fullFilm);
    }

    public Film getFilm(Long getIndex) {
        return filmDAO.getFilm(getIndex);
    }

    public void banFilm(Long banIndex) {
        filmDAO.banFilm(banIndex);
    }

    public void deleteFilm(Long delIndex) {
        filmDAO.deleteFilm(delIndex);
    }
}
