package com.example.je.services;

import com.example.je.model.Film;
import com.example.je.model.Page;

import java.util.List;

public class PageService {

    private static PageService pageService = null;

    private PageService() {
        System.out.println("pageservice init");
    }

    public static PageService getService() {
        if (pageService == null) {
            pageService = new PageService();
        }
        return pageService;
    }

    public void addFilms(Page page, List<Film> newFilms) {
        if (page.getFilms() == null) {
            page.setFilms(newFilms);
        }
        else {
            List<Film> fullListFilms = page.getFilms();
            fullListFilms.addAll(newFilms);
            page.setFilms(fullListFilms);
        }
    }
}
