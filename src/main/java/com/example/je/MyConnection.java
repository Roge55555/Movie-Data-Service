package com.example.je;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MyConnection {

    private static MyConnection connection = null;

    private final String userName = "kpuser";
    private final String password = "kpuser";
    private final String connectionUrl = "jdbc:mysql://localhost:3306/kinopoiskdb";

    private MyConnection() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }

    public static Connection getConnection() throws SQLException {

        if (connection == null) {
            connection = new MyConnection();
            System.out.println("connected");
        }

            return DriverManager.getConnection(connection.connectionUrl, connection.userName, connection.password);
    }
}
