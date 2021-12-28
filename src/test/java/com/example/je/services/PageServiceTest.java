package com.example.je.services;

import com.example.je.model.Film;
import com.example.je.model.Page;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PageServiceTest {

    PageService pageService = PageService.getService();

    @Test
    @DisplayName("Add new list of films")
    void addFilms() {
        Page page = new Page();
        List<Film> mainFilms = new ArrayList<>();
        List<Film> newFilms = new ArrayList<>();
        mainFilms.add(new Film(1L, "1", "1", "1", "1", new ArrayList<>(), new ArrayList<>(), "1", 11L, "1", "1", true));
        mainFilms.add(new Film(2L, "2", "2", "2", "2", new ArrayList<>(), new ArrayList<>(), "2", 22L, "2", "2", true));
        newFilms.add(new Film(3L, "3", "3", "3", "3", new ArrayList<>(), new ArrayList<>(), "3", 33L, "3", "3", true));
        newFilms.add(new Film(4L, "4", "4", "4", "4", new ArrayList<>(), new ArrayList<>(), "4", 44L, "4", "4", true));

        page.setFilms(mainFilms);
        pageService.addFilms(page, newFilms);
        mainFilms.addAll(newFilms);
        assertEquals(mainFilms, page.getFilms());
    }
}