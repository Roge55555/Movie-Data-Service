package com.example.je.model;

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
public class FilmCountryGenre {

    private Long filmId;

    private List<Country> countryList;

    private List<Genre> genreList;

    private Boolean isExist;
}
