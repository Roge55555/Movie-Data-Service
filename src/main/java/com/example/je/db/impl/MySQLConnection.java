package com.example.je.db.impl;

import com.example.je.db.DBConnection;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class MySQLConnection implements DBConnection {

    String userName;

    String password;

    String connectionUrl;

    String jdbcDriver;

    private static MySQLConnection connection = null;

    @Override
    public Connection connect() throws SQLException {
        if (connection == null) {
            connection = new MySQLConnection();
            System.out.println("connected");
        }
        try {
            Properties props = new Properties();
            ClassLoader classloader = Thread.currentThread().getContextClassLoader();
            InputStream inputStream = classloader.getResourceAsStream("application.properties");
            props.load(inputStream);
            connection.connectionUrl = props.get("db.url").toString();
            connection.userName = props.get("db.user").toString();
            connection.password = props.get("db.password").toString();
            connection.jdbcDriver = props.get("db.driver").toString();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

        try {
            Class.forName(connection.jdbcDriver);
        } catch (ClassNotFoundException e) {
            System.out.println(e.getMessage());
        }
        return DriverManager.getConnection(connection.connectionUrl, connection.userName, connection.password);
    }

}
