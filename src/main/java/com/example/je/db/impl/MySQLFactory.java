package com.example.je.db.impl;

import com.example.je.db.ConnectionFactory;
import com.example.je.db.DBConnection;

import java.sql.Connection;
import java.sql.SQLException;

public class MySQLFactory implements ConnectionFactory {

    private Connection connection;

    @Override
    public Connection getConnection(String type) throws SQLException {
        switch (type) {
            case ("mysql"):
                DBConnection dbConnection = new MySQLConnection();
                connection = dbConnection.connect();
                break;
            default:
                System.out.println("error in type");
        }
        return connection;
    }
}
