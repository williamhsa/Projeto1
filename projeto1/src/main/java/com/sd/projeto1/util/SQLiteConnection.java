package com.sd.projeto1.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class SQLiteConnection {

    private PropertyManagement pm;

    public static Connection connect() throws ClassNotFoundException {
        Connection conn = null;
        try {

           // Class.forName("org.sqlite.JDBC");
            
            String url = "jdbc:sqlite:C:/sqlite/projeto1sd.db";

            conn = DriverManager.getConnection(url);

            return conn;

        } catch (SQLException ex) {
            System.out.println("Erro. " + ex.getMessage());
            ex.printStackTrace();
        }

        return null;
    }

    public static void close(Connection con) {
        try {
            con.close();
        } catch (Exception ex) {
        }
    }

}
