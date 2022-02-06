package com.example.je;

import com.example.je.model.Film;
import com.example.je.model.FilmCountryGenre;
import com.example.je.model.FullFilm;
import com.example.je.services.CountryService;
import com.example.je.services.FilmService;
import com.example.je.services.GenreService;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import java.util.List;

public class RegularJob implements Job {

    private final FilmService filmService = FilmService.getService();

    private final CountryService countryService = CountryService.getService();

    private final GenreService genreService = GenreService.getService();

    @Override
    public void execute(JobExecutionContext jobExecutionContext) {

        System.out.println("Executing job in background...");

        List<Film> films = filmService.loadFilms();
        List<FilmCountryGenre> filmCountryGenreList = filmService.saveFilms(films);
        countryService.saveCountry(filmCountryGenreList);
        genreService.saveGenre(filmCountryGenreList);

        for (FilmCountryGenre filmCountryGenre : filmCountryGenreList) {
            FullFilm fullFilm = filmService.loadFullFilm(filmCountryGenre.getFilmId());
            filmService.saveFilms(fullFilm);
        }

        System.out.println("Done executing job.");
    }
}
