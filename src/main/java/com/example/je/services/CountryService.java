package com.example.je.services;

import com.example.je.dao.CountryDAO;
import com.example.je.model.Country;
import com.example.je.model.FilmCountryGenre;

import java.util.List;

public class CountryService {

    private static CountryService countryService = null;

    private final CountryDAO countryDAO = CountryDAO.getDAO();

    private CountryService() {
        System.out.println("countryservice init");
    }

    public static CountryService getService() {
        if (countryService == null) {
            countryService = new CountryService();
        }
        return countryService;
    }

    public void saveCountry(List<FilmCountryGenre> filmCountryGenreList) {
        countryDAO.saveAllCountries(filmCountryGenreList);
    }

    public List<Country> get(int getIndex) {
        List<Country> countries = countryDAO.getCountry(getIndex);

        if (countries.isEmpty()) {
            return null;
        }

        return countries;
    }

    public void delete(int delIndex) {
        countryDAO.deleteCountry(delIndex);
    }
}
