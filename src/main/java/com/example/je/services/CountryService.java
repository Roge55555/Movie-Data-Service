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

    static Connection connection;
    static PreparedStatement addFilmCountriesST;
    static PreparedStatement updateFilmCountriesST;

    public static void init() throws SQLException {
        connection = MyConnection.getConnection();
        addFilmCountriesST = connection.prepareStatement(Queries.INSERT_COUNTRY_IN_FILM);
        updateFilmCountriesST = connection.prepareStatement(Queries.UPDATE_COUNTRY_IN_FILM);
    }

    public static void execute() throws SQLException {
        addFilmCountriesST.executeBatch();
        updateFilmCountriesST.executeBatch();
        connection.commit();
        connection.close();
    }

    public static void add(List<Countries> countriesList, int filmId) throws SQLException {
        connection.setAutoCommit(false);

        for (Countries country : countriesList) {
            addFilmCountriesST.setInt(1, filmId);
            addFilmCountriesST.setString(2, country.getCountry());
            addFilmCountriesST.addBatch();
        }
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
        connection.setAutoCommit(false);
        updateFilmCountriesST.addBatch("delete from film_countries where film_id = " + filmId);

        for (Countries country : countriesList) {
            updateFilmCountriesST.setInt(1, filmId);
            updateFilmCountriesST.setString(2, country.getCountry());
            updateFilmCountriesST.addBatch();
        }

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
