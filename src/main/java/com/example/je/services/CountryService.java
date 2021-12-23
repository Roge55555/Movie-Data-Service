package com.example.je.services;

import com.example.je.MyConnection;
import com.example.je.Queries;
import com.example.je.model.Country;
import com.example.je.model.FilmCountryGenre;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CountryService {

    private static CountryService countryService = null;

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
        try (Connection connection = MyConnection.getConnection();
             PreparedStatement addFilmCountriesST = connection.prepareStatement(Queries.INSERT_COUNTRY_IN_FILM);
             PreparedStatement updateFilmCountriesST = connection.prepareStatement(Queries.UPDATE_COUNTRY_IN_FILM)) {

            connection.setAutoCommit(false);
            for (FilmCountryGenre filmCountryGenre : filmCountryGenreList) {

                if (filmCountryGenre.getIsExist()) {
                    delete(filmCountryGenre.getFilmId().intValue());

                    for (Country country : filmCountryGenre.getCountryList()) {
                        updateFilmCountriesST.setInt(1, filmCountryGenre.getFilmId().intValue());
                        updateFilmCountriesST.setString(2, country.getCountry());
                        updateFilmCountriesST.addBatch();
                    }
                } else {
                    for (Country country : filmCountryGenre.getCountryList()) {
                        addFilmCountriesST.setInt(1, filmCountryGenre.getFilmId().intValue());
                        addFilmCountriesST.setString(2, country.getCountry());
                        addFilmCountriesST.addBatch();
                    }
                }
            }

            addFilmCountriesST.executeBatch();
            updateFilmCountriesST.executeBatch();
            connection.commit();

        } catch (SQLException throwables) {
            System.out.println(throwables.getMessage());
        }
    }

    public List<Country> get(int filmId) {
        List<Country> countries = new ArrayList<>();
        try (Connection connection = MyConnection.getConnection();
             PreparedStatement getFilmCountriesST = MyConnection.getConnection().prepareStatement(Queries.GET_COUNTRY_IN_FILM)) {

            getFilmCountriesST.setInt(1, filmId);
            ResultSet rsc = getFilmCountriesST.executeQuery();
            connection.close();

            while (rsc.next())
                countries.add(new Country(rsc.getString("name")));

        } catch (SQLException throwables) {
            System.out.println(throwables.getMessage());
        }

        if (countries.isEmpty()) {
            return null;
        }

        return countries;
    }

    public void delete(int filmId) {
        try (Connection connection = MyConnection.getConnection();
             PreparedStatement delFilmCountriesST = connection.prepareStatement(Queries.DELETE_COUNTRY_IN_FILM)) {

            connection.setAutoCommit(false);

            delFilmCountriesST.setInt(1, filmId);
            delFilmCountriesST.addBatch();
            delFilmCountriesST.executeBatch();
            connection.commit();

        } catch (SQLException throwables) {
            System.out.println(throwables.getMessage());
        }

    }
}
