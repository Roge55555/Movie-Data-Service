package com.example.je.model;

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
public class Page {

    @JsonProperty("pagesCount")
    private Long pagesCount;

    @JsonProperty("films")
    private List<Film> films;

    public void addFilms(List<Film> newFilms, Long pagesCount) {
        if(films == null)
            this.films = newFilms;
        else
            films.addAll(newFilms);
        this.pagesCount = pagesCount;
    }

}
