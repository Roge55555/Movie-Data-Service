package com.example.JE.services;

import com.example.JE.dao.Countries;
import com.example.JE.dao.Films;
import com.example.JE.dao.Genres;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class ReadService {
    public static Films getFilm(Long getIndex) throws ClassNotFoundException, SQLException {
        String userName = "kpuser";
        String password = "kpuser";
        String connectionUrl = "jdbc:mysql://localhost:3306/kinopoiskdb";
        Class.forName("com.mysql.jdbc.Driver");
        try(Connection connection = DriverManager.getConnection(connectionUrl, userName, password);
            Statement statement = connection.createStatement()) {
            System.out.println("connected");

            ResultSet rsf = statement.executeQuery("select * from  films where id = " + getIndex);
            rsf.next();

            Films film = new Films();
            film.setFilmId((long) rsf.getInt("id"));
            film.setNameRu(rsf.getString("name_ru"));
            film.setNameEn(rsf.getString("name_en"));
            film.setYear(rsf.getString("year"));
            film.setFilmLength(rsf.getString("length"));
            film.setRating(rsf.getString("rating"));
            film.setRatingVoteCount((long) rsf.getInt("rating_vote_count"));
            film.setPosterUrl(rsf.getString("poster_url"));
            film.setPosterUrlPreview(rsf.getString("poster_url_preview"));

            List<Countries> countries = new ArrayList<>();
            ResultSet rsc = statement.executeQuery("select name from countries where id in (select country_id from  film_countries where film_id = " + getIndex + ")");
            while (rsc.next())
                countries.add(new Countries(rsc.getString("name")));
            film.setCountries(countries);

            List<Genres> genres = new ArrayList<>();
            ResultSet rsg = statement.executeQuery("select name from genres where id in (select genre_id from  film_genres where film_id = " + getIndex + ")");
            while (rsg.next())
                genres.add(new Genres(rsg.getString("name")));
            film.setGenres(genres);

            return film;

        }
    }
}
