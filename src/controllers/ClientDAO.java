package controllers;

import java.sql.*;
import java.util.ArrayList;
import models.Client;

public class ClientDAO {
    
    public static String create(Client newClient) {
        String erro = null;
        PreparedStatement state;
        String msgSQL = "insert into client (cpf, clientName, phone, address,"
                + "facebookURL, instagramURL, creditCard) values (?,?,?,?,?,?,?)";
        try {
            state = DatabaseConnection.getConexao().prepareStatement(msgSQL);
            state.setString(1, newClient.getCPF());
            state.setString(2, newClient.getClientName());
            state.setString(3, newClient.getPhone());
            state.setString(4, newClient.getAddress());
            state.setString(5, newClient.getFacebookURL());
            state.setString(6, newClient.getInstagramURL());
            state.setString(7, newClient.getCreditCard());
            state.execute();
            state.close();
        } catch (SQLException e) {
            System.out.println("\n Erro Encontrado: " + e.toString());
            erro = e.toString();
        }
        if (erro == null) {
            System.out.println("\n Cliente adicionado com Sucesso!");
        }

        return erro;
    }

    public static ArrayList<Client> read(boolean ordered, String orderParameter) {
        ArrayList<Client> clientList = new ArrayList();
        PreparedStatement state;
        ResultSet res;
        String msgSQL;
        if (ordered == true) {
            msgSQL = "Select * from client order by (" + orderParameter + ")";
        } else {
            msgSQL = "Select * from client";
        }
        try {
            state = DatabaseConnection.getConexao().prepareStatement(msgSQL);
            res = state.executeQuery();
            clientList = resultSetToArrayListClient(res);
            state.close();
        } catch (SQLException e) {
            System.out.println("\n Erro Encontrado: " + e.toString());
        }

        return clientList;
    }

    private static ArrayList<Client> resultSetToArrayListClient(ResultSet res) {
        ArrayList<Client> clientList = new ArrayList();
        try {
            while (res.next()) {
                Client newClient = new Client();
                newClient.setCPF(res.getString("cpf"));
                newClient.setAddress(res.getString("address"));
                newClient.setClientName(res.getString("clientName"));
                newClient.setCreditCard(res.getString("creditCard"));
                newClient.setFacebookURL(res.getString("facebookURL"));
                newClient.setInstagramURL(res.getString("instagramURL"));
                newClient.setPhone(res.getString("phone"));
                clientList.add(newClient);
            }
            res.close();
        } catch (SQLException e) {
            System.out.println("\n Erro Encontrado: " + e.toString());
        }

        return clientList;
    }

    public static String update(Client client) {
        String erro = null;
        PreparedStatement state;
        String msgSQL = "update client set address=?, clientName=?, creditCard=?, "
                + "facebookURL=?, instagramURL=?, phone=? where cpf=?";
        try {
            state = DatabaseConnection.getConexao().prepareStatement(msgSQL);
            state.setString(1, client.getAddress());
            state.setString(2, client.getClientName());
            state.setString(3, client.getCreditCard());
            state.setString(4, client.getFacebookURL());
            state.setString(5, client.getInstagramURL());
            state.setString(6, client.getPhone());
            state.setString(7, client.getCPF());
            state.execute();
            state.close();
        } catch (SQLException e) {
            System.out.println("\n Erro Encontrado: " + e.toString());
            erro = e.toString();
        }
        if (erro == null) {
            System.out.println("\n Cliente atualizado com sucesso!");
        }
        return erro;
    }

    public static String delete(Client client) {
        String erro = null;
        PreparedStatement state;
        String msgSQL = "delete from client where cpf=?";
        try {
            state = DatabaseConnection.getConexao().prepareStatement(msgSQL);
            state.setString(1, client.getCPF());
            state.execute();
            state.close();
        } catch (SQLException e) {
            System.out.println("\n Erro Encontrado: " + e.toString());
            erro = e.toString();
        }
        if (erro == null) {
            System.out.println("\n Cliente Excluído com sucesso!");
        }
        return erro;
    }
}
