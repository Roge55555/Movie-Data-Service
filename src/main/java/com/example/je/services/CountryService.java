package com.example.je.services;

import com.example.je.dao.CountryDAO;
import com.example.je.model.Country;
import com.example.je.model.FilmCountryGenre;

import java.util.List;
import java.util.Objects;

public class CountryService {

    private static CountryService countryService = null;

    private final CountryDAO countryDAO;

    private CountryService(CountryDAO countryDAO) {
        System.out.println("countryservice init");
        if (Objects.isNull(countryDAO))
            this.countryDAO = CountryDAO.getDAO();
        else
            this.countryDAO = countryDAO;
    }

    public static CountryService getService() {
        if (countryService == null) {
            countryService = new CountryService(null);
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
