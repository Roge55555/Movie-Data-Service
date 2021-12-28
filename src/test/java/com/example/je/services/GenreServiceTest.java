package com.example.je.services;

import com.example.je.dao.GenreDAO;
import com.example.je.model.FilmCountryGenre;
import com.example.je.model.Genre;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

@ExtendWith(MockitoExtension.class)
class GenreServiceTest {

    @InjectMocks
    GenreService genreService;

    @Mock
    GenreDAO genreDAO;

    private FilmCountryGenre newData(Long filmId, Boolean isExist) {
        List<Genre> genreList = new ArrayList<>();
        genreList.add(new Genre("драма"));
        genreList.add(new Genre("детектив"));
        return FilmCountryGenre.builder()
                .filmId(filmId)
                .genreList(genreList)
                .isExist(isExist)
                .build();

    }

    @Test
    @DisplayName("Save genres of film by film id")
    void saveCountry() {
        List<FilmCountryGenre> filmCountryGenreList = new ArrayList<>();
        filmCountryGenreList.add(newData(373L, false));

        genreService.saveGenre(filmCountryGenreList);

        Mockito.verify(genreDAO, Mockito.times(1)).saveAllGenres(ArgumentMatchers.anyList());
    }

    @Test
    @DisplayName("Get genres of film by film id")
    void get() {
        List<Genre> genreList = new ArrayList<>();
        genreList.add(new Genre("криминал"));
        genreList.add(new Genre("комедия"));

        Mockito.when(genreDAO.getGenre(ArgumentMatchers.anyInt())).thenReturn(genreList);
        List<Genre> getGenreList = genreService.get(54);

        assertEquals(2, getGenreList.size());
        Mockito.verify(genreDAO, Mockito.times(1)).getGenre(ArgumentMatchers.anyInt());
    }

    @Test
    @DisplayName("Get null cause no genres connected to such film id")
    void getEmptyList() {
        List<Genre> genreList = new ArrayList<>();
        Mockito.when(genreDAO.getGenre(ArgumentMatchers.anyInt())).thenReturn(genreList);
        List<Genre> getGenreList = genreService.get(654);

        assertNull(getGenreList);
        Mockito.verify(genreDAO, Mockito.times(1)).getGenre(ArgumentMatchers.anyInt());
    }

    @Test
    @DisplayName("Delete genres of film by film id")
    void delete() {
        genreService.delete(5619);

        Mockito.verify(genreDAO, Mockito.times(1)).deleteGenre(ArgumentMatchers.anyInt());
    }
}