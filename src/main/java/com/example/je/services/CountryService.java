package com.example.je.services;

import com.example.je.MyConnection;
import com.example.je.Queries;
import com.example.je.dao.Countries;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CountryService {

    public static void add(List<Countries> countriesList, int filmId) throws SQLException {
        Connection connection = MyConnection.getConnection();
        connection.setAutoCommit(false);
        PreparedStatement addFilmCountriesST = connection.prepareStatement(Queries.INSERT_COUNTRY_IN_FILM);

        for (Countries country : countriesList) {
            addFilmCountriesST.setInt(1, filmId);
            addFilmCountriesST.setString(2, country.getCountry());
            addFilmCountriesST.addBatch();
        }

        addFilmCountriesST.executeBatch();

        connection.commit();
        connection.close();
    }

    public static List<Countries> get(int filmId) throws SQLException {
        Connection connection = MyConnection.getConnection();
        PreparedStatement getFilmCountriesST = MyConnection.getConnection().prepareStatement(Queries.GET_COUNTRY_IN_FILM);
        getFilmCountriesST.setInt(1, filmId);
        ResultSet rsc = getFilmCountriesST.executeQuery();
        connection.close();

        List<Countries> countries = new ArrayList<>();
        while (rsc.next())
            countries.add(new Countries(rsc.getString("name")));

        return countries;

    }

    public static void update(List<Countries> countriesList, int filmId) throws SQLException {
        Connection connection = MyConnection.getConnection();
        connection.setAutoCommit(false);
        PreparedStatement updateFilmCountriesST = connection.prepareStatement(Queries.UPDATE_COUNTRY_IN_FILM);

        //updateFilmCountriesST.addBatch("delete from film_countries where film_id = " + film.getFilmId().intValue());
        delete(filmId);

        for (Countries country : countriesList) {
            updateFilmCountriesST.setInt(1, filmId);
            updateFilmCountriesST.setString(2, country.getCountry());
            updateFilmCountriesST.addBatch();
        }

        updateFilmCountriesST.executeBatch();
        connection.commit();
        connection.close();

    }

    public static void delete(int filmId) throws SQLException {
        Connection connection = MyConnection.getConnection();
        connection.setAutoCommit(false);
        PreparedStatement delFilmCountriesST = connection.prepareStatement(Queries.DELETE_COUNTRY_IN_FILM);
        delFilmCountriesST.setInt(1, filmId);
        delFilmCountriesST.addBatch();
        delFilmCountriesST.executeBatch();
        connection.commit();
        connection.close();

    }
}
