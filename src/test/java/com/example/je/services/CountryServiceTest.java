package com.example.je.services;

import com.example.je.dao.CountryDAO;
import com.example.je.model.Country;
import com.example.je.model.FilmCountryGenre;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

@ExtendWith(MockitoExtension.class)
class CountryServiceTest {

    @InjectMocks
    CountryService countryService;

    @Mock
    CountryDAO countryDAO;

    private FilmCountryGenre newData(Long filmId, Boolean isExist) {
        List<Country> countryList = new ArrayList<>();
        countryList.add(new Country("Мальта"));
        countryList.add(new Country("Бразилия"));
        return FilmCountryGenre.builder()
                .filmId(filmId)
                .countryList(countryList)
                .isExist(isExist)
                .build();

    }

    @Test
    @DisplayName("Save countries of film by film id")
    void saveCountry() {
        List<FilmCountryGenre> filmCountryGenreList = new ArrayList<>();
        filmCountryGenreList.add(newData(3442L, true));

        countryService.saveCountry(filmCountryGenreList);

        Mockito.verify(countryDAO, Mockito.times(1)).saveAllCountries(ArgumentMatchers.anyList());
    }

    @Test
    @DisplayName("Get countries of film by film id")
    void get() {
        List<Country> countryList = new ArrayList<>();
        countryList.add(new Country("США"));
        countryList.add(new Country("Новая Зеландия"));

        Mockito.when(countryDAO.getCountry(ArgumentMatchers.anyInt())).thenReturn(countryList);
        List<Country> getCountryList = countryService.get(34444);

        assertEquals(2, getCountryList.size());
        Mockito.verify(countryDAO, Mockito.times(1)).getCountry(ArgumentMatchers.anyInt());
    }

    @Test
    @DisplayName("Get null cause no countries connected to such film id")
    void getEmptyList() {
        List<Country> countryList = new ArrayList<>();
        Mockito.when(countryDAO.getCountry(ArgumentMatchers.anyInt())).thenReturn(countryList);
        List<Country> getCountryList = countryService.get(8676);

        assertNull(getCountryList);
        Mockito.verify(countryDAO, Mockito.times(1)).getCountry(ArgumentMatchers.anyInt());
    }

    @Test
    @DisplayName("Delete countries of film by film id")
    void delete() {
        countryService.delete(688);

        Mockito.verify(countryDAO, Mockito.times(1)).deleteCountry(ArgumentMatchers.anyInt());
    }
}