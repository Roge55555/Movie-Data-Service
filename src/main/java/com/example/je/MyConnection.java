package com.example.je;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class MyConnection {

    private static MyConnection connection = null;

    private String userName;
    private String password;
    private String connectionUrl;
    private String jdbcDriver;

    private MyConnection() {
        try {
            Properties props = new Properties();
            ClassLoader classloader = Thread.currentThread().getContextClassLoader();
            InputStream inputStream = classloader.getResourceAsStream("application.properties");
            props.load(inputStream);
            connectionUrl = props.get("db.url").toString();
            userName = props.get("db.user").toString();
            password = props.get("db.password").toString();
            jdbcDriver = props.get("db.driver").toString();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

        try {
            Class.forName(jdbcDriver);
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
