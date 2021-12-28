package com.example.je.services;

import com.example.je.dao.GenreDAO;
import com.example.je.model.FilmCountryGenre;
import com.example.je.model.Genre;

import java.util.List;
import java.util.Objects;

public class GenreService {

    private static GenreService genreService = null;

    private final GenreDAO genreDAO;

    private GenreService(GenreDAO genreDAO) {
        System.out.println("genreservice init");
        if (Objects.isNull(genreDAO))
            this.genreDAO = GenreDAO.getDAO();
        else
            this.genreDAO = genreDAO;
    }

    public static GenreService getService() {
        if (genreService == null) {
            genreService = new GenreService(null);
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
