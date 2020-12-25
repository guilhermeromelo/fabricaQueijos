
package main;

import controllers.*;
import controllers.DatabaseConnection;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDateTime;
import models.*;
import views.*;
import java.util.*;
import javax.swing.JOptionPane;

public class Main {

    public static void main(String[] args) {
        try{
//            /* TESTES DO CLIENTE */
//            Client cliente = new Client("125.111.111-12", "Guilherme", "34 988396298", 
//                    "Rua Mariquinha Montandon", "facebook/guirmelo", "insta/guirmelo", "1111 2222 3333 4444");
//            ClientDAO clientDAO = new ClientDAO();
//            clientDAO.create(cliente);
//            
//            ArrayList<Client> clientList = new ArrayList();
//            clientList = ClientDAO.read(false, "creditCard");
//            
//            clientList.forEach(c -> {
//                System.out.println(c.toString());;
//            });
//            
//            
//            /* TESTES DO QUEIJO */
//            Queijo queijo = new Queijo(222, 222, "Queijoooooo", 2222);
//            queijo.setQueijoID(3);
//            QueijoDAO.create(queijo);
//            ArrayList<Queijo> queijoList = new ArrayList();
//            queijoList = QueijoDAO.read(false, "");
//            
//            queijoList.forEach(q ->{
//                System.out.println(q.toString());
//            });
//            
//            Pedido pedido = new Pedido("111.111-12", LocalDateTime.now(), 5);
//            PedidoDAO pedidoDAO = new PedidoDAO();
//            pedidoDAO.create(pedido);
              Connection con = DatabaseConnection.getConexao();
              
            
        }catch(SQLException e){
            System.out.println("\n Erro Encontrado: "+e.toString());
        }
        
        MainScreen tela = new MainScreen();
        tela.setVisible(true);
        tela.setLocationRelativeTo(null);
        
        System.out.println("Hello Wolrd!");
    }
}
