package com.example.je.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@Builder
public class Film {

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
    private List<Country> countries;

    @JsonProperty("genres")
    private List<Genre> genres;

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

    @JsonIgnore
    private Boolean isBlocked;

}
