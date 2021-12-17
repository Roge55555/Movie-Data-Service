package com.example.je.services;

import com.example.je.model.Film;
import com.example.je.model.Page;

import java.util.List;

public class PageService {
    public static void addFilms(Page page, List<Film> newFilms) {
        if(page.getFilms() == null) {
            page.setFilms(newFilms);
        }
        else {
            List<Film> fullListFilms = page.getFilms();
            fullListFilms.addAll(newFilms);
            page.setFilms(fullListFilms);
        }
    }
}
