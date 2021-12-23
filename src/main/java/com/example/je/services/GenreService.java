package com.example.je.services;

import com.example.je.dao.GenreDAO;
import com.example.je.model.FilmCountryGenre;
import com.example.je.model.Genre;

import java.util.List;

public class GenreService {

    private static GenreService genreService = null;

    private final GenreDAO genreDAO = GenreDAO.getDAO();

    private GenreService() {
        System.out.println("genreservice init");
    }

    public static GenreService getService() {
        if (genreService == null) {
            genreService = new GenreService();
        }
        return genreService;
    }

    public void saveGenre(List<FilmCountryGenre> filmCountryGenreList) {
        genreDAO.saveAllGenres(filmCountryGenreList);
    }

    public List<Genre> get(int getIndex) {
        List<Genre> genres = genreDAO.getGenre(getIndex);

        if (genres.isEmpty()) {
            return null;
        }

        return genres;
    }

    public void delete(int delIndex) {
        genreDAO.deleteGenre(delIndex);
    }
}
