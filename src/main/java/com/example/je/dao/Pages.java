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
public class Pages {

    @JsonProperty("pagesCount")
    private Long pagesCount;

    @JsonProperty("films")
    private List<Films> films;

    public void addFilms(List<Films> newFilms, Long pagesCount) {
        if(films == null)
            this.films = newFilms;
        else
            films.addAll(newFilms);
        this.pagesCount = pagesCount;
    }

}
