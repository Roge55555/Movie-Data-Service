package com.example.je.dao;

import com.example.je.Queries;
import com.example.je.db.impl.MySQLFactory;
import com.example.je.model.Country;
import com.example.je.model.FilmCountryGenre;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CountryDAO {

    private static CountryDAO countryDAO = null;

    private final MySQLFactory factory = new MySQLFactory();

    private CountryDAO() {
        System.out.println("countrydao init");
    }

    public static CountryDAO getDAO() {
        if (countryDAO == null) {
            countryDAO = new CountryDAO();
        }
        return countryDAO;
    }

    public void saveAllCountries(List<FilmCountryGenre> filmCountryGenreList) {
        try (Connection connection = factory.getConnection("mysql");
             PreparedStatement addFilmCountriesStatement = connection.prepareStatement(Queries.INSERT_COUNTRY_IN_FILM);
             PreparedStatement updateFilmCountriesStatement = connection.prepareStatement(Queries.UPDATE_COUNTRY_IN_FILM)) {

            connection.setAutoCommit(false);
            for (FilmCountryGenre filmCountryGenre : filmCountryGenreList) {

                if (filmCountryGenre.getIsExist()) {
                    deleteCountry(filmCountryGenre.getFilmId().intValue());

                    for (Country country : filmCountryGenre.getCountryList()) {
                        updateFilmCountriesStatement.setInt(1, filmCountryGenre.getFilmId().intValue());
                        updateFilmCountriesStatement.setString(2, country.getCountry());
                        updateFilmCountriesStatement.addBatch();
                    }
                } else {
                    for (Country country : filmCountryGenre.getCountryList()) {
                        addFilmCountriesStatement.setInt(1, filmCountryGenre.getFilmId().intValue());
                        addFilmCountriesStatement.setString(2, country.getCountry());
                        addFilmCountriesStatement.addBatch();
                    }
                }
            }

            addFilmCountriesStatement.executeBatch();
            updateFilmCountriesStatement.executeBatch();
            connection.commit();

        } catch (SQLException throwables) {
            System.out.println(throwables.getMessage());
        }
    }

    public List<Country> getCountry(int filmId) {
        List<Country> countries = new ArrayList<>();
        try (Connection connection = factory.getConnection("mysql");
             PreparedStatement getFilmCountriesStatement = factory.getConnection("mysql").prepareStatement(Queries.GET_COUNTRY_IN_FILM)) {

            getFilmCountriesStatement.setInt(1, filmId);
            ResultSet resultSetCountry = getFilmCountriesStatement.executeQuery();
            connection.close();

            while (resultSetCountry.next())
                countries.add(new Country(resultSetCountry.getString("name")));

        } catch (SQLException throwables) {
            System.out.println(throwables.getMessage());
        }

        return countries;
    }

    public void deleteCountry(int filmId) {
        try (Connection connection = factory.getConnection("mysql");
             PreparedStatement deleteFilmCountriesStatement = connection.prepareStatement(Queries.DELETE_COUNTRY_IN_FILM)) {

            deleteFilmCountriesStatement.setInt(1, filmId);
            deleteFilmCountriesStatement.execute();

        } catch (SQLException throwables) {
            System.out.println(throwables.getMessage());
        }

    }
}
