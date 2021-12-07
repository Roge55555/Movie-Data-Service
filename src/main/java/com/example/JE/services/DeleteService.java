package com.example.JE.services;

import com.example.JE.MyConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DeleteService {
    public static void deleting(Long delIndex) throws ClassNotFoundException, SQLException {

        Connection connection = new MyConnection().getConnection();
        connection.setAutoCommit(false);
        PreparedStatement delFC = connection.prepareStatement("delete from film_countries where film_id = ?");
        PreparedStatement delFG = connection.prepareStatement("delete from film_genres where film_id = ?");
        PreparedStatement delF = connection.prepareStatement("delete from films where id = ?");

        delFC.setInt(1, delIndex.intValue());
        delFG.setInt(1, delIndex.intValue());
        delF.setInt(1, delIndex.intValue());

        delFC.addBatch();
        delFG.addBatch();
        delF.addBatch();

        delFC.executeBatch();
        delFG.executeBatch();
        delF.executeBatch();

        connection.commit();
        connection.close();



    }
}
