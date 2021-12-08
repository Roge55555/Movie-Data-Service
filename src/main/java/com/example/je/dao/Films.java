package com.example.je.dao;

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

}
