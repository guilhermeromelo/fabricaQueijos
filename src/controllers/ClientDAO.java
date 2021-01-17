package controllers;

import java.sql.*;
import java.util.ArrayList;
import models.Client;

public class ClientDAO {
    
    static Connection connection = DatabaseConnection.getConexao();
    
    public static String create(Client newClient) {
        String erro = null;
        PreparedStatement state;
        String msgSQL = "insert into clients (cpf, clientName, phone, address,"
                + "facebookURL, instagramURL, creditCard) values (?,?,?,?,?,?,?)";
        try {
            state = connection.prepareStatement(msgSQL);
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

    public static ArrayList<Client> read(boolean ordered, String orderParameter, boolean desc) {
        ArrayList<Client> clientList = new ArrayList();
        PreparedStatement state;
        ResultSet res;
        String msgSQL;
        if (ordered == true) {
            msgSQL = "Select * from clients order by " + orderParameter + (desc==true ? " desc": "");
        } else {
            msgSQL = "Select * from clients";
        }
        try {
            state = connection.prepareStatement(msgSQL);
            res = state.executeQuery();
            clientList = resultSetToArrayListClient(res);
            res.close();
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
        } catch (SQLException e) {
            System.out.println("\n Erro Encontrado: " + e.toString());
        }

        return clientList;
    }

    public static String update(Client client) {
        String erro = null;
        PreparedStatement state;
        String msgSQL = "update clients set address=?, clientName=?, creditCard=?, "
                + "facebookURL=?, instagramURL=?, phone=? where cpf=?";
        try {
            state = connection.prepareStatement(msgSQL);
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
        String msgSQL = "delete from clients where cpf=?";
        try {
            state = connection.prepareStatement(msgSQL);
            state.setString(1, client.getCPF());
            state.execute();
            state.close();
        } catch (SQLException e) {
            System.out.println("\n Erro Encontrado: " + e.toString());
            erro = e.toString();
        }
        if (erro == null) {
            System.out.println("\n Cliente Removido com sucesso!");
        }
        return erro;
    }
    
    public static String nameSearch(String cpf) {
        PreparedStatement state;
        String msgSQL = "select clientname from clients where cpf = ?";
        String clientName = null;
        try{
            state=connection.prepareStatement(msgSQL);
            state.setString(1, cpf);
            ResultSet res = state.executeQuery();
            if(res.next()){
                clientName = res.getString("clientname");
            }
        }catch(SQLException e){
            System.out.println("\nErro Encontrado: "+e.toString());
        }
        return clientName;
    }
}
