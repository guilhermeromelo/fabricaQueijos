package controllers;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection{
    public static Connection getConexao(){
        Connection databaseConnection = null;
        try {
            //Class.forName("org.postgresql.Driver");
            databaseConnection = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/trab5_poov", "poov", "pass123");
        } catch (SQLException e) {
            System.out.println("\n Erro encontrado: "+ e.toString());
        }
        return databaseConnection;
    }
}