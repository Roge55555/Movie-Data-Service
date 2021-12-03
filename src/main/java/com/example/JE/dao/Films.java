package com.example.JE.dao;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;
import java.util.Objects;

public class Films {

    @JsonProperty("filmId")
    private Long filmId;

    @JsonProperty("nameRu")
    private String nameRu;

    @JsonProperty("nameEn")
    private String nameEn;

    @JsonProperty("year")
    private String year;

    @JsonProperty("filmLength")
    private String filmLength;

    @JsonProperty("countries")
    private List<Countries> countries;

    @JsonProperty("genres")
    private List<Genres> genres;

    @JsonProperty("rating")
    private String rating;

    @JsonProperty("ratingVoteCount")
    private Long ratingVoteCount;

    @JsonProperty("posterUrl")
    private String posterUrl;

    @JsonProperty("posterUrlPreview")
    private String posterUrlPreview;

    @JsonProperty("ratingChange")
    private Boolean ratingChange;

    public Long getFilmId() {
        return filmId;
    }

    public void setFilmId(Long filmId) {
        this.filmId = filmId;
    }

    public String getNameRu() {
        return nameRu;
    }

    public void setNameRu(String nameRu) {
        this.nameRu = nameRu;
    }

    public String getNameEn() {
        return nameEn;
    }

    public void setNameEn(String nameEn) {
        this.nameEn = nameEn;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getFilmLength() {
        return filmLength;
    }

    public void setFilmLength(String filmLength) {
        this.filmLength = filmLength;
    }

    public List<Countries> getCountries() {
        return countries;
    }

    public void setCountries(List<Countries> countries) {
        this.countries = countries;
    }

    public List<Genres> getGenres() {
        return genres;
    }

    public void setGenres(List<Genres> genres) {
        this.genres = genres;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public Long getRatingVoteCount() {
        return ratingVoteCount;
    }

    public void setRatingVoteCount(Long ratingVoteCount) {
        this.ratingVoteCount = ratingVoteCount;
    }

    public String getPosterUrl() {
        return posterUrl;
    }

    public void setPosterUrl(String posterUrl) {
        this.posterUrl = posterUrl;
    }

    public String getPosterUrlPreview() {
        return posterUrlPreview;
    }

    public void setPosterUrlPreview(String posterUrlPreview) {
        this.posterUrlPreview = posterUrlPreview;
    }

    public Boolean getRatingChange() {
        return ratingChange;
    }

    public void setRatingChange(Boolean ratingChange) {
        this.ratingChange = ratingChange;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Films films = (Films) o;
        return Objects.equals(filmId, films.filmId) && Objects.equals(nameRu, films.nameRu) && Objects.equals(nameEn, films.nameEn) && Objects.equals(year, films.year) && Objects.equals(filmLength, films.filmLength) && Objects.equals(countries, films.countries) && Objects.equals(genres, films.genres) && Objects.equals(rating, films.rating) && Objects.equals(ratingVoteCount, films.ratingVoteCount) && Objects.equals(posterUrl, films.posterUrl) && Objects.equals(posterUrlPreview, films.posterUrlPreview) && Objects.equals(ratingChange, films.ratingChange);
    }

    @Override
    public int hashCode() {
        return Objects.hash(filmId, nameRu, nameEn, year, filmLength, countries, genres, rating, ratingVoteCount, posterUrl, posterUrlPreview, ratingChange);
    }
}
