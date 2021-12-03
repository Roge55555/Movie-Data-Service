package com.example.JE.dao;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Objects;

public class Genres {

    @JsonProperty("genre")
    private String genre;

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Genres genres = (Genres) o;
        return Objects.equals(genre, genres.genre);
    }

    @Override
    public int hashCode() {
        return Objects.hash(genre);
    }

    public Genres(String genre) {
        this.genre = genre;
    }

    public Genres() {
    }
}
