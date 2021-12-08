package com.example.je;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MyConnection {

    String userName = "kpuser";
    String password = "kpuser";
    String connectionUrl = "jdbc:mysql://localhost:3306/kinopoiskdb";

    public MyConnection() {
    }

    public Connection getConnection() throws ClassNotFoundException, SQLException {

        Class.forName("com.mysql.jdbc.Driver");
        Connection connection = DriverManager.getConnection(connectionUrl, userName, password);
        System.out.println("connected");

        return connection;
    }
}
