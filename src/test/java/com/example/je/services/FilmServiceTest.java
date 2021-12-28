package com.example.je.services;

import com.example.je.dao.FilmDAO;
import com.example.je.model.Film;
import com.example.je.model.Page;
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
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(MockitoExtension.class)
class FilmServiceTest {

    @Mock
    FilmDAO filmDAO;

    @Mock
    PageService pageService;

    @InjectMocks
    FilmService filmService;

    private Film newFilm(Long id, String nameRu, String nameEn) {

        return Film.builder()
                .filmId(id)
                .nameRu(nameRu)
                .nameEn(nameEn)
                .year("2013")
                .filmLength("09:26")
                .rating("7.7")
                .ratingVoteCount(358561L)
                .posterUrl(null)
                .posterUrlPreview(null)
                .build();
    }

    @Test
    @DisplayName("Load all 250 films in list")
    void loadFilms() {
        List<Film> top250;
        int numberOfPages = 13;

        Mockito.doCallRealMethod().when(pageService).addFilms(ArgumentMatchers.any(Page.class), ArgumentMatchers.anyList());
        top250 = filmService.loadFilms();


        assertEquals(250, top250.size());
        Mockito.verify(pageService, Mockito.times(numberOfPages)).addFilms(ArgumentMatchers.any(Page.class), ArgumentMatchers.anyList());
    }

    @Test
    @DisplayName("Save film(s)")
    void addFilms() {
        List<Film> filmsAdd = new ArrayList<>();
        filmsAdd.add(newFilm(8L, null, "4400"));

        filmService.saveFilms(filmsAdd);

        Mockito.verify(filmDAO, Mockito.times(1)).saveAllFilms(ArgumentMatchers.anyList());
    }

    @Test
    @DisplayName("Get film by id")
    void getFilm() {
        Mockito.when(filmDAO.getFilm(ArgumentMatchers.anyLong())).thenReturn(newFilm(6L, null, "12 chairs"));
        Film film = filmService.getFilm(6546L);

        assertNotNull(film);
        Mockito.verify(filmDAO, Mockito.times(1)).getFilm(ArgumentMatchers.anyLong());
    }

    @Test
    @DisplayName("Delete film by id")
    void deleteFilm() {
        filmService.deleteFilm(641564L);

        Mockito.verify(filmDAO, Mockito.times(1)).deleteFilm(ArgumentMatchers.anyLong());
    }
}