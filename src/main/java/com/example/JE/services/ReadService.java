package com.example.JE.services;

import com.example.JE.MyConnection;
import com.example.JE.dao.Countries;
import com.example.JE.dao.Films;
import com.example.JE.dao.Genres;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ReadService {
    public static Films getFilm(Long getIndex) throws ClassNotFoundException, SQLException {

        Connection connection = new MyConnection().getConnection();
        PreparedStatement getFST = connection.prepareStatement("select * from  films where id = ?");
        getFST.setInt(1, getIndex.intValue());
        ResultSet rsf = getFST.executeQuery();

        if(rsf.next()) {
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
            PreparedStatement getFCST = connection.prepareStatement("select name from countries where id in (select country_id from  film_countries where film_id = ?)");
            getFCST.setInt(1, getIndex.intValue());
            ResultSet rsc = getFCST.executeQuery();
            while (rsc.next())
                countries.add(new Countries(rsc.getString("name")));
            film.setCountries(countries);

            List<Genres> genres = new ArrayList<>();
            PreparedStatement getFGST = connection.prepareStatement("select name from genres where id in (select genre_id from  film_genres where film_id = ?)");
            getFGST.setInt(1, getIndex.intValue());
            ResultSet rsg = getFGST.executeQuery();
            while (rsg.next())
                genres.add(new Genres(rsg.getString("name")));
            film.setGenres(genres);

            connection.close();

            return film;
        }
        else {
            System.out.println("No such film - " + getIndex);

            connection.close();

            return null;
        }
    }
}
