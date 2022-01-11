package com.example.je.db;

import java.sql.Connection;
import java.sql.SQLException;

public interface ConnectionFactory {

    Connection getConnection(String type) throws SQLException;
}
