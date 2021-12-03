package com.example.JE.services;

import com.example.JE.Utils;
import com.example.JE.dao.Films;
import com.example.JE.dao.Pages;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class LoadService {
    public static void loadData(Pages pages) throws ClassNotFoundException, SQLException {
        String userName = "kpuser";
        String password = "kpuser";
        String connectionUrl = "jdbc:mysql://localhost:3306/kinopoiskdb";
        Class.forName("com.mysql.jdbc.Driver");
        try(Connection connection = DriverManager.getConnection(connectionUrl, userName, password);
            Statement statement = connection.createStatement()) {
            System.out.println("connected");
            for(int i = 0; i < pages.getFilms().size(); i++) { //todo цикл записи по 1 фильму фильмов

                Films film = pages.getFilms().get(i);
                ResultSet rsf = statement.executeQuery("select name_en from films where name_en = '" + Utils.doublingApostrophe(film.getNameEn()) + "' and name_en <> 'null' or name_ru = '" + Utils.doublingApostrophe(film.getNameRu()) + "' and name_ru <> 'null' or id = " + film.getFilmId());

                if (rsf.next()) {
                    System.out.println("Already exist film: " + film.getFilmId() + "/" + film.getNameEn() + "/" + film.getNameRu());
                } else {

                    statement.executeUpdate("insert into films(id, name_ru, name_en, year, length, rating, rating_vote_count, poster_url, poster_url_preview) " +
                            "values (" + film.getFilmId() + ", '" + Utils.doublingApostrophe(film.getNameRu()) + "', '" + Utils.doublingApostrophe(film.getNameEn()) + "', '" + film.getYear() + "', '" + film.getFilmLength() +
                            "', '" + film.getRating() + "', " + film.getRatingVoteCount() + ", '" + film.getPosterUrl() + "', '" + film.getPosterUrlPreview() + "')");

                    for (int j = 0; j < film.getCountries().size(); j++) { // todo цикл для проверки и если нет, записи страны
                        ResultSet rsc = statement.executeQuery("select id from countries where name = '" + film.getCountries().get(j).getCountry() + "'");
                        if (rsc.next()) {
                            statement.executeUpdate("insert into film_countries(film_id, country_id) values (" + film.getFilmId() + ", " + rsc.getInt("id") + ")");
                        } else {
                            statement.executeUpdate("insert into countries(name) values ('" + film.getCountries().get(j).getCountry() + "')");
                            ResultSet rsc1 = statement.executeQuery("select id from countries where name = '" + film.getCountries().get(j).getCountry() + "'");
                            rsc1.next();
                            statement.executeUpdate("insert into film_countries(film_id, country_id) values (" + film.getFilmId() + ", " + rsc1.getInt("id") + ")");
                        }
                    }

                    for (int j = 0; j < film.getGenres().size(); j++) { // todo цикл для проверки и если нет, записи жанра
                        ResultSet rsg = statement.executeQuery("select id from genres where name = '" + film.getGenres().get(j).getGenre() + "'");
                        if (rsg.next()) {
                            statement.executeUpdate("insert into film_genres(film_id, genre_id) values (" + film.getFilmId() + ", " + rsg.getInt("id") + ")");
                        } else {
                            statement.executeUpdate("insert into genres(name) values ('" + film.getGenres().get(j).getGenre() + "')");
                            ResultSet rsg1 = statement.executeQuery("select id from genres where name = '" + film.getGenres().get(j).getGenre() + "'");
                            rsg1.next();
                            statement.executeUpdate("insert into film_genres(film_id, genre_id) values (" + film.getFilmId() + ", " + rsg1.getInt("id") + ")");
                        }
                    }
                }
            }
        }
    }
}
