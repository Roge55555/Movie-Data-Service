package com.example.je.db;

import java.sql.Connection;
import java.sql.SQLException;

public interface DBConnection {

    Connection connect() throws SQLException;

}
