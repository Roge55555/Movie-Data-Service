package com.example.JE.services;

import com.example.JE.Utils;
import com.example.JE.dao.Films;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class UpdateService {
    public static void updateFilm(Films film) throws ClassNotFoundException, SQLException {
        String userName = "kpuser";
        String password = "kpuser";
        String connectionUrl = "jdbc:mysql://localhost:3306/kinopoiskdb";
        Class.forName("com.mysql.jdbc.Driver");
        try(Connection connection = DriverManager.getConnection(connectionUrl, userName, password);
            Statement statement = connection.createStatement()) {
            System.out.println("connected");

            ResultSet rsf = statement.executeQuery("select name_en from films where id = " + film.getFilmId());

            if (!rsf.next()) {
                System.out.println("No film with id: " + film.getFilmId());
            } else {

                statement.executeUpdate("update films set name_ru = '" + Utils.doublingApostrophe(film.getNameRu()) + "', name_en = '" + Utils.doublingApostrophe(film.getNameEn()) + "', " +
                        "year = '" + film.getYear() + "', length = '" + film.getFilmLength() +"', rating = '" + film.getRating() + "', rating_vote_count = " + film.getRatingVoteCount() + ", " +
                                "poster_url = '" + film.getPosterUrl() + "', poster_url_preview = '" + film.getPosterUrlPreview() + "' where id = " + film.getFilmId());

                statement.executeUpdate("delete from film_countries where film_id = " + film.getFilmId());
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

                statement.executeUpdate("delete from film_genres where film_id = " + film.getFilmId());
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
