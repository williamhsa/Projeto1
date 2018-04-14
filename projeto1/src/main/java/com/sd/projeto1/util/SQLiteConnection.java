package com.sd.projeto1.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class SQLiteConnection {
    
    public static void connect() {
        Connection conn = null;
        try {
           
            String url = "jdbc:sqlite:C:/sqlite/projeto1sd.db";
           
            conn = DriverManager.getConnection(url);
            
            System.out.println("Conexão com SQLITE feita com sucesso");
            
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }
        }
    }
    
    public static void close(Connection con) {
        try {
            con.close();
        } catch (Exception ex) {
        }
    }
   
}