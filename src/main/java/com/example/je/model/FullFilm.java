package com.example.je.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@Builder
public class FullFilm {

    @JsonProperty("kinopoiskId")
    private Long kinopoiskId;

    @JsonProperty("imdbId")
    private String imdbId;

    @JsonProperty("nameRu")
    private String nameRu;

    @JsonProperty("nameEn")
    private String nameEn;

    @JsonProperty("nameOriginal")
    private String nameOriginal;

    @JsonProperty("posterUrl")
    private String posterUrl;

    @JsonProperty("posterUrlPreview")
    private String posterUrlPreview;

    @JsonProperty("reviewsCount")
    private Long reviewsCount;

    @JsonProperty("ratingGoodReview")
    private Double ratingGoodReview;

    @JsonProperty("ratingGoodReviewVoteCount")
    private Long ratingGoodReviewVoteCount;

    @JsonProperty("ratingKinopoisk")
    private Double ratingKinopoisk;

    @JsonProperty("ratingKinopoiskVoteCount")
    private Long ratingKinopoiskVoteCount;

    @JsonProperty("ratingImdb")
    private Double ratingImdb;

    @JsonProperty("ratingImdbVoteCount")
    private Long ratingImdbVoteCount;

    @JsonProperty("ratingFilmCritics")
    private Double ratingFilmCritics;

    @JsonProperty("ratingFilmCriticsVoteCount")
    private Long ratingFilmCriticsVoteCount;

    @JsonProperty("ratingAwait")
    private Double ratingAwait;

    @JsonProperty("ratingAwaitCount")
    private Long ratingAwaitCount;

    @JsonProperty("ratingRfCritics")
    private Double ratingRfCritics;

    @JsonProperty("ratingRfCriticsVoteCount")
    private Long ratingRfCriticsVoteCount;

    @JsonProperty("webUrl")
    private String webUrl;

    @JsonProperty("year")
    private Long year;

    @JsonProperty("filmLength")
    private Long filmLength;

    @JsonProperty("slogan")
    private String slogan;

    @JsonProperty("description")
    private String description;

    @JsonProperty("shortDescription")
    private String shortDescription;

    @JsonProperty("editorAnnotation")
    private String editorAnnotation;

    @JsonProperty("isTicketsAvailable")
    private Boolean isTicketsAvailable;

    @JsonProperty("productionStatus")
    private String productionStatus;

    @JsonProperty("type")
    private String type;

    @JsonProperty("ratingMpaa")
    private String ratingMpaa;

    @JsonProperty("ratingAgeLimits")
    private String ratingAgeLimits;

    @JsonProperty("countries")
    private List<Country> countries;

    @JsonProperty("genres")
    private List<Genre> genres;

    @JsonProperty("startYear")
    private Long startYear;

    @JsonProperty("endYear")
    private Long endYear;

    @JsonProperty("serial")
    private Boolean serial;

    @JsonProperty("shortFilm")
    private Boolean shortFilm;

    @JsonProperty("completed")
    private Boolean completed;

    @JsonProperty("hasImax")
    private Boolean hasImax;

    @JsonProperty("has3D")
    private Boolean has3D;

    @JsonProperty("lastSync")
    private String lastSync;
}
