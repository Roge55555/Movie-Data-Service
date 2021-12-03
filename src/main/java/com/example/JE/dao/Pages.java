package com.example.JE.dao;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;
import java.util.Objects;

public class Pages {

    @JsonProperty("pagesCount")
    private Long pagesCount;

    @JsonProperty("films")
    private List<Films> films;

    public Long getPagesCount() {
        return pagesCount;
    }

    public List<Films> getFilms() {
        return films;
    }

    public void setFilms(List<Films> films) {
        this.films = films;
    }

    public void addFilms(List<Films> newFilms, Long pagesCount) {
        if(films == null)
            this.films = newFilms;
        else
            films.addAll(newFilms);
        this.pagesCount = pagesCount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Pages pages = (Pages) o;
        return Objects.equals(pagesCount, pages.pagesCount) && Objects.equals(films, pages.films);
    }

    @Override
    public int hashCode() {
        return Objects.hash(pagesCount, films);
    }
}
