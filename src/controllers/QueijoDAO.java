package controllers;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import models.Queijo;

public class QueijoDAO {
    
    static Connection connection = DatabaseConnection.getConexao();
    
    public static String create(Queijo queijo) {
        String erro = null;
        PreparedStatement state;
        String msgSQL = "insert into queijo(weight, pricePerKg, queijoType, recommendedTemperature)"
                + "values (?, ?, ?, ?)";
        try {
            state = connection.prepareStatement(msgSQL);
            state.setDouble(1, queijo.getWeight());
            state.setDouble(2, queijo.getPricePerKg());
            state.setString(3, queijo.getQueijoType());
            state.setDouble(4, queijo.getRecommendedTemperature());
            state.execute();
            state.close();
        } catch (SQLException e) {
            System.out.println("\n Erro Encontrado: " + e.toString());
            erro = e.toString();
        }
        if (erro == null) {
            System.out.println("\n Queijo adicionado com sucesso!");
        }

        return erro;
    }

    public static ArrayList<Queijo> read(boolean order, String orderParameter, boolean desc) {
        PreparedStatement state;
        ArrayList<Queijo> queijoList = new ArrayList();
        String msgSQL;
        if (order == true) {
            msgSQL = "Select * from queijo order by " + orderParameter+ (desc == true ? " desc" : "" );
        } else {
            msgSQL = "Select * from queijo";
        }

        try {
            state = connection.prepareStatement(msgSQL);
            ResultSet res = state.executeQuery();
            queijoList = resultSetToArrayList(res);
            res.close();
            state.close();
        } catch (SQLException e) {
            System.out.println("\n Erro Encontrado: " + e.toString());
        }
        return queijoList;
    }

    private static ArrayList<Queijo> resultSetToArrayList(ResultSet res) {
        ArrayList<Queijo> queijoList = new ArrayList();
        try {
            while (res.next()) {
                Queijo newQueijo = new Queijo();
                newQueijo.setQueijoID(res.getInt("queijoid"));
                newQueijo.setPricePerKg(res.getDouble("pricePerKg"));
                newQueijo.setQueijoType(res.getString("queijoType"));
                newQueijo.setRecommendedTemperature(res.getDouble("recommendedTemperature"));
                newQueijo.setWeight(res.getDouble("weight"));
                queijoList.add(newQueijo);
            }
        } catch (SQLException e) {
            System.out.println("\n Erro Encontrado: " + e.toString());
        }
        return queijoList;
    }

    public static String update(Queijo queijo) {
        String erro = null;
        PreparedStatement state;
        String msgSQL = "update queijo set weight=?, pricePerKg=?, queijoType=?, "
                + "recommendedTemperature=? where queijoID=?";
        try {
            state = connection.prepareStatement(msgSQL);
            state.setDouble(1, queijo.getWeight());
            state.setDouble(2, queijo.getPricePerKg());
            state.setString(3, queijo.getQueijoType());
            state.setDouble(4, queijo.getRecommendedTemperature());
            state.setInt(5, queijo.getQueijoID());
            state.execute();
            state.close();
        } catch (SQLException e) {
            System.out.println("\n Erro Encontrado: " + e.toString());
            erro = e.toString();
        }
        if (erro == null) {
            System.out.println("\n Queijo Atualizado com sucesso!");
        }
        return erro;
    }

    public static String delete(Queijo queijo) {
        String erro = null;
        PreparedStatement state;
        String msgSQL = "delete from queijo where queijoID=?";
        try {
            state = connection.prepareStatement(msgSQL);
            state.setInt(1, queijo.getQueijoID());
            state.execute();
            state.close();
        } catch (SQLException e) {
            System.out.println("\n Erro Encontrado: " + e.toString());
            erro = e.toString();
        }
        if (erro == null) {
            System.out.println("\n Queijo Removido com sucesso!");
        }
        return erro;
    }
}
