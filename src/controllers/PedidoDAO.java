package controllers;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import models.Pedido;

public class PedidoDAO {

    public static String create(Pedido newpedido) {
        String erro = null;
        PreparedStatement state;
        String msgSQL = "insert into pedido (fk_client_cpf, pedidoDate, deliveryDeadLine, note)"
                + "values (?,?,?,?)";
        try {
            state = DatabaseConnection.getConexao().prepareStatement(msgSQL);
            state.setString(1, newpedido.getFk_client_cpf());
            Timestamp timestamp = Timestamp.valueOf(newpedido.getPedidoDate());
            state.setTimestamp(2, timestamp);
            state.setDouble(3, newpedido.getDeliveryDeadLine());
            state.setString(4, newpedido.getNote());
            state.execute();
            state.close();
        } catch (SQLException e) {
            System.out.println("\n Erro Encontrado: " + e.toString());
            erro = e.toString();
        }
        if (erro == null) {
            System.out.println("\n Pedido Adicionado com Sucesso!");
        }
        return erro;
    }

    public static ArrayList<Pedido> read() {
        PreparedStatement state;
        ArrayList<Pedido> pedidoList = new ArrayList();
        String msgSQL = "select * from pedido";
        try {
            state = DatabaseConnection.getConexao().prepareStatement(msgSQL);
            ResultSet res;
            res = state.executeQuery();
            pedidoList = resToArrayList(res);
            res.close();
            state.close();
        } catch (SQLException e) {
            System.out.println("\n Erro Encontrado: " + e.toString());
        }
        return pedidoList;
    }

    private static ArrayList<Pedido> resToArrayList(ResultSet res) {
        ArrayList<Pedido> pedidoList = new ArrayList();
        try {
            while (res.next()) {
                Pedido newpedido = new Pedido();
                newpedido.setPedidoID(res.getInt("pedidoID"));
                newpedido.setFk_client_cpf(res.getString("fk_client_cpf"));
                Timestamp date = res.getTimestamp("pedidoDate");
                newpedido.setPedidoDate(date.toLocalDateTime());
                newpedido.setDeliveryDeadLine(res.getDouble("deliveryDeadLine"));
                newpedido.setNote(res.getString("note"));
                pedidoList.add(newpedido);
            }
        } catch (SQLException e) {
            System.out.println("\n Erro Encontrado: " + e.toString());
        }
        return pedidoList;
    }//LocalDateTime localDateTime = timestamp.toLocalDateTime();

    public static String update(Pedido pedido) {
        String erro = null;
        PreparedStatement state;
        String msgSQL = "update pedido set fk_client_cpf=?, pedidoDate=?,"
                + "deliveryDeadLine=?, note=? where pedidoID=?";
        try {
            state = DatabaseConnection.getConexao().prepareStatement(msgSQL);
            state.setString(1, pedido.getFk_client_cpf());
            Timestamp timestamp = Timestamp.valueOf(pedido.getPedidoDate());
            state.setTimestamp(2, timestamp);
            state.setDouble(3, pedido.getDeliveryDeadLine());
            state.setString(4, pedido.getNote());
            state.setInt(5, pedido.getPedidoID());
            state.execute();
            state.close();
        } catch (SQLException e) {
            System.out.println("\n Erro Encontrado: " + e.toString());
            erro = e.toString();
        }
        if (erro == null) {
            System.out.println("\n Pedido Alterado com sucesso!");
        }
        return erro;
    }

    public static String delete(Pedido pedido) {
        String erro = null;
        PreparedStatement state;
        String msgSQL = "delete from pedido where pedidoID=?";
        try {
            state = DatabaseConnection.getConexao().prepareStatement(msgSQL);
            state.setInt(1, pedido.getPedidoID());
            state.execute();
            state.close();
        } catch (SQLException e) {
            System.out.println("\n Erro Encontrado: " + e.toString());
            erro = e.toString();
        }
        if (erro == null) {
            System.out.println("\n Pedido Alterado com Sucesso!");
        }

        return erro;
    }
    
    public static int whoIsLastPedidoID(){
        int nextPedidoID = -1;
        PreparedStatement state;
        String msgSQL = "select max(pedidoid) from pedido";
        try{
            state = DatabaseConnection.getConexao().prepareStatement(msgSQL);
            ResultSet res = state.executeQuery();
            if(res.next()){
                nextPedidoID = res.getInt("max");
            }
            res.close();
            state.close();
        }catch(SQLException e){
            System.out.println("\n Erro Econtrado: " + e.toString());
        }
        return nextPedidoID;
    }


    public static Pedido search(String id) {
        PreparedStatement state;
        Pedido pedidoList = new Pedido();
        String msgSQL = "select * from pedido where pedidoid=?";
        try {
            state = DatabaseConnection.getConexao().prepareStatement(msgSQL);
            state.setInt(1, Integer.parseInt(id));
            ResultSet res;
            res = state.executeQuery();
            pedidoList = resToArrayList(res).get(0);
            res.close();
            state.close();
        } catch (SQLException e) {
            System.out.println("\n Erro Encontrado: " + e.toString());
        }
        return pedidoList;
    }
}