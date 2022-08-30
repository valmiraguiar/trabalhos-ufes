package controller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
public class DBConnection {
    
    private static final String url = "jdbc:postgresql://localhost:5432/trabalhodb";
    private static final String user = "postgres";
    private static final String password = "1234";
    private static Connection con;
    
    public static Connection connection(){
        try {            
            Class.forName("org.postgresql.Driver");
            con = DriverManager.getConnection(url, user, password);
            System.out.println("Connection Succesfully");
            return con;
        } catch(ClassNotFoundException | SQLException e) {
            System.out.println("Connection Failed");
        }
        return null;
    }

}
