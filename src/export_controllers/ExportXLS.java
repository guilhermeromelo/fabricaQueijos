package export_controllers;

import controllers.ClientDAO;
import controllers.PedidoDAO;
import controllers.QueijoDAO;
import controllers.QueijoPedidoDAO;
import java.awt.List;
import java.awt.Menu;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import jxl.Workbook;
import jxl.write.*;
import models.Client;
import models.Pedido;
import models.Queijo;
import models.QueijoPedido;

public class ExportXLS {

    public static void exportarCliente() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Como deseja salvar o arquivo?");

        int userSelection = fileChooser.showSaveDialog(null);

        File fileToSave = null;
        if (userSelection == JFileChooser.APPROVE_OPTION) {
            fileToSave = fileChooser.getSelectedFile();
        } else {
            return;
        }

        WritableWorkbook relatorio = null;
        try {
            relatorio = Workbook.createWorkbook(new File(fileToSave.getAbsolutePath() + ".xls"));
            WritableSheet planilha = relatorio.createSheet("Pasta 1", 0);

            ArrayList<Client> clients = ClientDAO.read(false, "", false);

            planilha.addCell(new Label(0, 0, "CPF"));
            planilha.addCell(new Label(1, 0, "Nome"));
            planilha.addCell(new Label(2, 0, "Telefone"));
            planilha.addCell(new Label(3, 0, "Endereço"));
            planilha.addCell(new Label(4, 0, "Cartão de Crédito"));
            planilha.addCell(new Label(5, 0, "Instagram"));
            planilha.addCell(new Label(6, 0, "Facebook"));

            for (int i = 0; i < clients.size(); i++) {
                Client client = clients.get(i);

                planilha.addCell(new Label(0, i + 1, String.valueOf(client.getCPF())));
                planilha.addCell(new Label(1, i + 1, String.valueOf(client.getClientName())));
                planilha.addCell(new Label(2, i + 1, String.valueOf(client.getPhone())));
                planilha.addCell(new Label(3, i + 1, String.valueOf(client.getAddress())));
                planilha.addCell(new Label(4, i + 1, String.valueOf(client.getCreditCard())));
                planilha.addCell(new Label(5, i + 1, String.valueOf(client.getInstagramURL())));
                planilha.addCell(new Label(6, i + 1, String.valueOf(client.getFacebookURL())));
            }

            relatorio.write();
            relatorio.close();

        } catch (IOException ex) {
            Logger.getLogger(Menu.class.getName()).log(Level.SEVERE, null, ex);
        } catch (WriteException ex) {
            Logger.getLogger(Menu.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void exportarQueijo() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Como deseja salvar o arquivo?");

        int userSelection = fileChooser.showSaveDialog(null);

        File fileToSave = null;
        if (userSelection == JFileChooser.APPROVE_OPTION) {
            fileToSave = fileChooser.getSelectedFile();
        } else {
            return;
        }

        WritableWorkbook relatorio = null;
        try {
            relatorio = Workbook.createWorkbook(new File(fileToSave.getAbsolutePath() + ".xls"));
            WritableSheet planilha = relatorio.createSheet("Pasta 1", 0);

            ArrayList<Queijo> queijos = QueijoDAO.read(false, "", false);

            planilha.addCell(new Label(0, 0, "ID"));
            planilha.addCell(new Label(1, 0, "Tipo"));
            planilha.addCell(new Label(2, 0, "Peso Kg"));
            planilha.addCell(new Label(3, 0, "Preço por Kg"));
            planilha.addCell(new Label(4, 0, "Preço Unidade"));

            for (int i = 0; i < queijos.size(); i++) {
                Queijo queijo = queijos.get(i);

                planilha.addCell(new Label(0, i + 1, String.valueOf(queijo.getQueijoID())));
                planilha.addCell(new Label(1, i + 1, String.valueOf(queijo.getQueijoType())));
                planilha.addCell(new Label(2, i + 1, String.valueOf(queijo.getWeight())));
                planilha.addCell(new Label(3, i + 1, String.valueOf(queijo.getPricePerKg())));
                planilha.addCell(new Label(4, i + 1, String.valueOf(queijo.getWeight() * queijo.getPricePerKg())));
            }

            relatorio.write();
            relatorio.close();

        } catch (IOException ex) {
            Logger.getLogger(Menu.class.getName()).log(Level.SEVERE, null, ex);
        } catch (WriteException ex) {
            Logger.getLogger(Menu.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void exportarPedido() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Como deseja salvar o arquivo?");

        int userSelection = fileChooser.showSaveDialog(null);

        File fileToSave = null;
        if (userSelection == JFileChooser.APPROVE_OPTION) {
            fileToSave = fileChooser.getSelectedFile();
        } else {
            return;
        }

        WritableWorkbook relatorio = null;
        try {
            relatorio = Workbook.createWorkbook(new File(fileToSave.getAbsolutePath() + ".xls"));
            WritableSheet planilha = relatorio.createSheet("Pedidos", 0);

            ArrayList<Pedido> pedidos = PedidoDAO.read(false, "", false);

            planilha.addCell(new Label(0, 0, "ID"));
            planilha.addCell(new Label(1, 0, "Nome Cliente"));
            planilha.addCell(new Label(2, 0, "CPF Cliente"));
            planilha.addCell(new Label(3, 0, "Data"));
            planilha.addCell(new Label(4, 0, "Hora"));
            planilha.addCell(new Label(5, 0, "Prazo Entrega"));

            boolean achou = false;
            String clientName = "";
            ArrayList<Client> clients = ClientDAO.read(false, "", false);
            for (int i = 0; i < pedidos.size(); i++) {
                achou = false;
                Pedido pedido = pedidos.get(i);
                for (int j = 0; j < clients.size() && achou == false; j++) {
                    if (clients.get(j).getCPF().equals(pedido.getFk_client_cpf())) {
                        achou = true;
                        clientName = clients.get(j).getClientName();
                    }
                }

                planilha.addCell(new Label(0, i + 1, String.valueOf(pedido.getPedidoID())));
                planilha.addCell(new Label(1, i + 1, String.valueOf(clientName)));
                planilha.addCell(new Label(2, i + 1, String.valueOf(pedido.getFk_client_cpf())));
                planilha.addCell(new Label(3, i + 1, String.valueOf(ExportPDF.dataBuilder(pedido.getPedidoDate()))));
                planilha.addCell(new Label(4, i + 1, String.valueOf(ExportPDF.horaBuilder(pedido.getPedidoDate()))));
                planilha.addCell(new Label(5, i + 1, String.valueOf(pedido.getDeliveryDeadLine())));
            }

            WritableSheet planilha2 = relatorio.createSheet("Queijo Pedidos", 1);

            planilha2.addCell(new Label(0, 0, "ID"));
            planilha2.addCell(new Label(1, 0, "Tipo"));
            planilha2.addCell(new Label(2, 0, "Peso"));
            planilha2.addCell(new Label(3, 0, "Valor/Kg"));
            planilha2.addCell(new Label(4, 0, "Quantidade"));
            planilha2.addCell(new Label(5, 0, "Valor Total"));

            ArrayList<Pedido> pedidoList = PedidoDAO.read(false, "", false);
            ArrayList<Queijo> queijos = QueijoDAO.read(false, "", false);
            Queijo queijo = null;
            int linha = 1;
            for (int k = 0; k < pedidoList.size(); k++) {
                ArrayList<QueijoPedido> queijoPedidoList = QueijoPedidoDAO.read(pedidoList.get(k).getPedidoID() + "");
                planilha2.addCell(new Label(0, linha, String.valueOf("PEDIDO " + pedidoList.get(k).getPedidoID())));
                linha++;
                for (int i = 0; i < queijoPedidoList.size(); i++) {

                    achou = false;
                    QueijoPedido queijoPedido = queijoPedidoList.get(i);
                    for (int j = 0; j < queijos.size() && achou == false; j++) {
                        if (queijos.get(j).getQueijoID() == queijoPedido.getFk_id_queijo()) {
                            achou = true;
                            queijo = queijos.get(j);
                        }
                    }
                    planilha2.addCell(new Label(0, linha, String.valueOf(queijoPedido.getQueijoPedidoID())));
                    planilha2.addCell(new Label(1, linha, String.valueOf(queijo.getQueijoType())));
                    planilha2.addCell(new Label(2, linha, String.valueOf(queijo.getWeight())));
                    planilha2.addCell(new Label(3, linha, String.valueOf(queijo.getPricePerKg())));
                    planilha2.addCell(new Label(4, linha, String.valueOf(queijoPedido.getQuantity())));
                    planilha2.addCell(new Label(5, linha, String.valueOf(queijoPedido.getQuantity() * queijo.getPricePerKg() * queijo.getWeight())));
                    linha++;
                }
            }

            relatorio.write();
            relatorio.close();

        } catch (IOException ex) {
            Logger.getLogger(Menu.class.getName()).log(Level.SEVERE, null, ex);
        } catch (WriteException ex) {
            Logger.getLogger(Menu.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
