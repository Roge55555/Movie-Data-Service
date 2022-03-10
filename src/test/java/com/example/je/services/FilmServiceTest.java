package com.example.je.services;

import com.example.je.Utils;
import com.example.je.dao.FilmDAO;
import com.example.je.dao.FullFilmDAO;
import com.example.je.model.Film;
import com.example.je.model.FullFilm;
import com.example.je.model.Page;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class FilmServiceTest {

    @Mock
    FilmDAO filmDAO;

    @Mock
    FullFilmDAO fullFilmDAO;

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
                .posterUrl("https://kinopoiskapiunofficial.tech/images/posters/kp/435.jpg")
                .posterUrlPreview("https://kinopoiskapiunofficial.tech/images/posters/kp_small/435.jpg")
                .build();
    }

    @Test
    @DisplayName("Load all 250 films in list")
    void loadFilms() {
        List<Film> top250;
        int numberOfPages = 13;

        doCallRealMethod().when(pageService).addFilms(any(Page.class), anyList());
        top250 = filmService.loadFilms();


        assertEquals(250, top250.size());
        verify(pageService, times(numberOfPages)).addFilms(any(Page.class), anyList());
    }

    @Test
    @DisplayName("Load full film")
    void loadFullFilm() {
        FullFilm fullFilm = null;

        when(filmService.getFilm(anyLong())).thenReturn(newFilm(301L, "Интерстеллар", null));
        fullFilm = filmService.loadFullFilm(301L);

        assertNotNull(fullFilm);
    }

    @Test
    @DisplayName("Save film(s)")
    void addFilms() {
        try (MockedStatic<Utils> utilities = mockStatic(Utils.class)) {
            List<Film> filmsAdd = new ArrayList<>();
            filmsAdd.add(newFilm(8L, "4400", "4400"));

            utilities.when(() -> Utils.saveImage(anyString(), anyString())).thenCallRealMethod();
            filmService.saveFilms(filmsAdd);

            verify(filmDAO, times(1)).saveAllFilms(anyList());
        }

    }

    @Test
    @DisplayName("Save full film")
    void addFullFilm() {
        FullFilm fullFilm = new FullFilm();

        filmService.saveFullFilm(fullFilm);

        verify(fullFilmDAO, times(1)).saveFullFilm(any(FullFilm.class));
    }

    @Test
    @DisplayName("Get film by id")
    void getFilm() {
        when(filmDAO.getFilm(anyLong())).thenReturn(newFilm(6L, null, "12 chairs"));
        Film film = filmService.getFilm(6546L);

        assertNotNull(film);
        verify(filmDAO, times(1)).getFilm(anyLong());
    }

    @Test
    @DisplayName("Delete film by id")
    void banFilm() {
        filmService.banFilm(5485L);

        verify(filmDAO, times(1)).banFilm(anyLong());
    }

    @Test
    @DisplayName("Delete film by id")
    void deleteFilm() {
        filmService.deleteFilm(641564L);

        verify(filmDAO, times(1)).deleteFilm(anyLong());
    }
}