package com.example.JE.services;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DeleteService {
    public static void deleting(Long delIndex) throws ClassNotFoundException, SQLException {
        String userName = "kpuser";
        String password = "kpuser";
        String connectionUrl = "jdbc:mysql://localhost:3306/kinopoiskdb";
        Class.forName("com.mysql.jdbc.Driver");
        try(Connection connection = DriverManager.getConnection(connectionUrl, userName, password);
            Statement statement = connection.createStatement()) {
            System.out.println("connected");
            statement.executeUpdate("delete from film_countries where film_id = " + delIndex);
            statement.executeUpdate("delete from film_genres where film_id = " + delIndex);
            statement.executeUpdate("delete from films where id = " + delIndex);
        }
    }
}
