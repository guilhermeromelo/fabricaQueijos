
package export_controllers;

import controllers.ClientDAO;
import controllers.PedidoDAO;
import controllers.QueijoDAO;
import controllers.QueijoPedidoDAO;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import javax.swing.JFileChooser;
import models.Client;
import models.Pedido;
import models.Queijo;
import models.QueijoPedido;

public class ExportTXT {
    
     public static void exportClient() {
        ArrayList<String> stringArray = new ArrayList();
        ArrayList<Client> clientArray = ClientDAO.read(true, "clientName", false);
        stringArray.add("Clientes Fábrica de Queijos Uai Sô: \n");
        for(int i=0; i<clientArray.size();i++){
            stringArray.add(clientArray.get(i).toString());
        }
        gravarStringArquivo(stringArray);
    }
    
    public static void exportQueijos() {
        ArrayList<String> stringArray = new ArrayList();
        ArrayList<Queijo> queijoArray = QueijoDAO.read(false, "", false);
        stringArray.add("Queijos da Fábrica de Queijos Uai Sô: \n");
        for(int i=0; i<queijoArray.size();i++){
            stringArray.add(queijoArray.get(i).toString());
        }
        gravarStringArquivo(stringArray);
    }
    
    public static void exportPedidos() {
        ArrayList<String> stringArray = new ArrayList();
        ArrayList<Pedido> pedidoArray = PedidoDAO.read(true, "clientName", false);
        stringArray.add("Pedidos da Fábrica de Queijos Uai Sô: ");
        for(int i=0; i<pedidoArray.size();i++){
            stringArray.add("\n\nPedido "+i+1+"");
            stringArray.add(pedidoArray.get(i).toString());
            stringArray.add("\nProdutos do Pedido "+i+1);
            ArrayList<QueijoPedido> queijoPedidoList = QueijoPedidoDAO.read(""+pedidoArray.get(i).getPedidoID());
            for(int j=0; j<queijoPedidoList.size();j++){
                QueijoPedido queijoPedido = queijoPedidoList.get(j);
                stringArray.add(queijoPedido.toString());
            }
        }
        gravarStringArquivo(stringArray);
    }

    public static void gravarStringArquivo(ArrayList<String> StringArray) {
        try {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setDialogTitle("Como deseja salvar o arquivo?");   

            int userSelection = fileChooser.showSaveDialog(null);

            File file = null;
            if (userSelection == JFileChooser.APPROVE_OPTION) {
                file = new File(fileChooser.getSelectedFile().getPath()+".txt");
            } else {
                return;
            }
            
            if (!file.exists()) {
                System.out.println("Não há arquivo criado.");
                if (file.createNewFile()) {
                    System.out.println("O arquivo foi criado");
                    FileWriter fw = new FileWriter(file);
                    BufferedWriter bw = new BufferedWriter(fw);
                    for (int i = 0; i < StringArray.size(); i++) {
                        bw.write(StringArray.get(i));
                        bw.newLine();
                    }
                    bw.close();
                    fw.close();
                } else {
                    System.out.println("O arquivo não foi criado.");
                }
            } else {
                System.out.println("Arquivo já existente");
                FileWriter fw = new FileWriter(file, true);
                BufferedWriter bw = new BufferedWriter(fw);
                for (int i = 0; i < StringArray.size(); i++) {
                    bw.write(StringArray.get(i));
                    bw.newLine();
                }
                bw.close();
                fw.close();
            }
        } catch (IOException e) {
            System.out.print("Erro: " + e.toString());
        }
    }
}
