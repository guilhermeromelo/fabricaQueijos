package controllers;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import models.QueijoPedido;

public class QueijoPedidoDAO {

    /*
    private int queijoPedidoID;
    private int fk_id_pedido;
    private int fk_id_queijo;
    private int quantity;
     */
    public static String create(QueijoPedido newqueijoPedido) {
        String erro = null;
        PreparedStatement state;
        String msgSQL = "insert into queijo_pedido(fk_id_pedido, fk_id_queijo, quantity) "
                + "values (?, ?, ?)";
        try {
            state = DatabaseConnection.getConexao().prepareStatement(msgSQL);
            state.setInt(1, newqueijoPedido.getFk_id_pedido());
            state.setInt(2, newqueijoPedido.getFk_id_queijo());
            state.setInt(3, newqueijoPedido.getQuantity());
            state.execute();
            state.close();
        } catch (SQLException e) {
            System.out.println("\n Erro Encontrado: " + e.toString());
            erro = e.toString();
        }
        if (erro == null) {
            System.out.println("\n QueijoPedido Acionado com sucesso!");
        }

        return erro;
    }

    public static ArrayList<QueijoPedido> read(String id) {
        ArrayList<QueijoPedido> queijoPedidoList = new ArrayList();
        PreparedStatement state;
        String msgSQL = "select * from queijo_pedido where fk_id_pedido =?";
        try {
            state = DatabaseConnection.getConexao().prepareStatement(msgSQL);
            state.setInt(1, Integer.parseInt(id));
            ResultSet res = state.executeQuery();
            queijoPedidoList = resToArrayList(res);
            state.close();
        } catch (SQLException e) {
            System.out.println("\n Erro Encontrado: " + e.toString());
        }
        return queijoPedidoList;
    }

    private static ArrayList<QueijoPedido> resToArrayList(ResultSet res) {
        ArrayList<QueijoPedido> queijoPedidoList = new ArrayList();
        try {
            while (res.next()) {
                QueijoPedido queijoPedido = new QueijoPedido();
                queijoPedido.setFk_id_pedido(res.getInt("fk_id_pedido"));
                queijoPedido.setFk_id_queijo(res.getInt("fk_id_queijo"));
                queijoPedido.setQuantity(res.getInt("quantity"));
                queijoPedido.setQueijoPedidoID(res.getInt("queijoPedidoID"));
                queijoPedidoList.add(queijoPedido);
            }
            res.close();
        } catch (SQLException e) {
            System.out.println("\n Erro Encontrado: " + e.toString());
        }
        return queijoPedidoList;
    }
    
    public static String update(QueijoPedido queijoPedido){
        String erro = null;
        PreparedStatement state;
        String msgSQL = "update queijo_pedido set fk_id_pedido=?, fk_id_queijo=?, "
                + "quantity=? where queijoPedidoID=?";
        try{
            state = DatabaseConnection.getConexao().prepareStatement(msgSQL);
            state.setInt(1, queijoPedido.getFk_id_pedido());
            state.setInt(2, queijoPedido.getFk_id_queijo());
            state.setInt(3, queijoPedido.getQuantity());
            state.setInt(4, queijoPedido.getQueijoPedidoID());
            state.execute();
            state.close();
        }catch(SQLException e){
            System.out.println("\n Erro Encontrado: "+e.toString());
        }
        if(erro == null)
            System.out.println("\n QueijoPedido Atualizado com sucesso!");
        return erro;
    }
    
    public static String delete(QueijoPedido queijoPedido){
        String erro = null;
        PreparedStatement state;
        String msgSQL = "delete from queijo_pedido where queijoPedidoID=?";
        try{
            state = DatabaseConnection.getConexao().prepareStatement(msgSQL);
            state.setInt(1, queijoPedido.getFk_id_pedido());
            state.execute();
            state.close();
        }catch(SQLException e){
            System.out.println("\n Erro Encontrado: "+e.toString());
        }
        if(erro == null)
            System.out.println("\n QueijoPedido Excluído com sucesso!");
        
        return erro;
    }
}
