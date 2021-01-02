package controllers;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection{
    public static Connection getConexao(){
        Connection databaseConnection = null;
        try {
            //Class.forName("org.postgresql.Driver");
            databaseConnection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/trab5", "root", "password");
        } catch (SQLException e) {
            System.out.println("\n Erro encontrado: "+ e.toString());
        }
        return databaseConnection;
    }
}