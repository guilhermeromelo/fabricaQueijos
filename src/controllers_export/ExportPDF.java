package controllers_export;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import controllers.ClientDAO;
import controllers.PedidoDAO;
import controllers.QueijoDAO;
import java.awt.Desktop;
import java.awt.List;
import java.awt.Menu;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import models.Client;
import models.Pedido;
import models.Queijo;

public class ExportPDF {
    
    public static void exportClient(){
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Como deseja salvar o arquivo?");   

        int userSelection = fileChooser.showSaveDialog(null);

        File fileToSave = null;
        if (userSelection == JFileChooser.APPROVE_OPTION) {
            fileToSave = fileChooser.getSelectedFile();
        } else {
            return;
        }

        Document documento = new Document();

        PdfPTable table = new PdfPTable(8);
        documento.setPageSize(PageSize.LETTER.rotate());
        
        ArrayList<Client> clientList = ClientDAO.read(false, "", false);
        
        Paragraph header = new Paragraph("Exportação de Clientes");
        
        table.addCell("Nº");
        table.addCell("Nome");
        table.addCell("CPF");
        table.addCell("Telefone");
        table.addCell("Endereço");
        table.addCell("Cartão de Crédito");
        table.addCell("Instagram");
        table.addCell("Facebook");
        
        
        for (int i=0; i<clientList.size(); i++){
            Client client = clientList.get(i);
            
            table.addCell((i+1)+"");
            table.addCell(client.getClientName());
            table.addCell(client.getCPF());
            table.addCell(client.getPhone());
            table.addCell(client.getAddress());
            table.addCell(client.getCreditCard());
            table.addCell(client.getInstagramURL());
            table.addCell(client.getFacebookURL());
            
        }
        
        try {
            PdfWriter.getInstance(documento, new FileOutputStream(fileToSave.getAbsolutePath()+".pdf"));
            documento.open();
            documento.add(header);
            documento.add(new Paragraph(" "));
            documento.add(table);
            documento.close();
            
            Desktop desktop = Desktop.getDesktop();  
            desktop.open(new File(fileToSave.getAbsolutePath()+".pdf"));
            
        } catch (FileNotFoundException | DocumentException  ex) {
            System.out.print("\n Erro: "+ex.toString());
        } catch (IOException ex) {
            Logger.getLogger(Menu.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static void exportQueijo(){
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Como deseja salvar o arquivo?");   

        int userSelection = fileChooser.showSaveDialog(null);

        File fileToSave = null;
        if (userSelection == JFileChooser.APPROVE_OPTION) {
            fileToSave = fileChooser.getSelectedFile();
        } else {
            return;
        }

        Document documento = new Document();

        PdfPTable table = new PdfPTable(6);
        documento.setPageSize(PageSize.LETTER.rotate());
        
        ArrayList<Queijo> queijoList = QueijoDAO.read(false, "", false);
        
        Paragraph header = new Paragraph("Exportação de Queijo");
        
        table.addCell("Nº");
        table.addCell("ID");
        table.addCell("Tipo");
        table.addCell("Peso Kg");
        table.addCell("Preço por Kg");
        table.addCell("Preço Unidade");
        
        for (int i=0; i<queijoList.size(); i++){
            Queijo queijo = queijoList.get(i);
            
            table.addCell((i+1)+"");
            table.addCell(queijo.getQueijoID()+"");
            table.addCell(queijo.getQueijoType());
            table.addCell(queijo.getWeight()+"");
            table.addCell(queijo.getPricePerKg()*queijo.getWeight()+"");
            table.addCell(queijo.getRecommendedTemperature()+"");
            
        }
        
        try {
            PdfWriter.getInstance(documento, new FileOutputStream(fileToSave.getAbsolutePath()+".pdf"));
            documento.open();
            documento.add(header);
            documento.add(new Paragraph(" "));
            documento.add(table);
            documento.close();
            
            Desktop desktop = Desktop.getDesktop();  
            desktop.open(new File(fileToSave.getAbsolutePath()+".pdf"));
            
        } catch (FileNotFoundException | DocumentException  ex) {
            System.out.print("\n Erro: "+ex.toString());
        } catch (IOException ex) {
            Logger.getLogger(Menu.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static void exportPedido(){
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Como deseja salvar o arquivo?");   

        int userSelection = fileChooser.showSaveDialog(null);

        File fileToSave = null;
        if (userSelection == JFileChooser.APPROVE_OPTION) {
            fileToSave = fileChooser.getSelectedFile();
        } else {
            return;
        }

        Document documento = new Document();

        PdfPTable table = new PdfPTable(5);
        documento.setPageSize(PageSize.LETTER.rotate());
        
        ArrayList<Pedido> pedidoList = PedidoDAO.read(false, "", false);
        
        Paragraph header = new Paragraph("Exportação de Pedido");
        
        table.addCell("ID");
        table.addCell("Nome Cliente");
        table.addCell("CPF Cliente");
        table.addCell("Data");
        table.addCell("Prazo Entrega");

        boolean achou=false;
        String clientName="";
        ArrayList<Client> clients = ClientDAO.read(false, "", false);
        for (int i=0; i<pedidoList.size(); i++){
            achou=false;
            Pedido pedido = pedidoList.get(i);
            for(int j=0;j<clients.size() && achou == false;j++){
                if(clients.get(j).getCPF().equals(pedido.getFk_client_cpf())){
                    achou = true;
                    clientName = clients.get(j).getClientName();
                }
            }
            
            table.addCell(pedido.getPedidoID()+"");
            table.addCell(clientName);
            table.addCell(pedido.getFk_client_cpf()+"");
            table.addCell(dataBuilder(pedido.getPedidoDate())+" - "+horaBuilder(pedido.getPedidoDate())+"");
            table.addCell(pedido.getDeliveryDeadLine()+"");          
        }
        
        try {
            PdfWriter.getInstance(documento, new FileOutputStream(fileToSave.getAbsolutePath()+".pdf"));
            documento.open();
            documento.add(header);
            documento.add(new Paragraph(" "));
            documento.add(table);
            
            documento.close();
            
            Desktop desktop = Desktop.getDesktop();  
            desktop.open(new File(fileToSave.getAbsolutePath()+".pdf"));
            
        } catch (FileNotFoundException | DocumentException  ex) {
            System.out.print("\n Erro: "+ex.toString());
        } catch (IOException ex) {
            Logger.getLogger(Menu.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
        public static String dataBuilder(LocalDateTime localDate) {
        String dia = "" + localDate.getDayOfMonth();
        if (dia.length() == 1) {
            dia = "0" + dia;
        }
        String mes = "" + localDate.getMonthValue();
        if (mes.length() == 1) {
            mes = "0" + mes;
        }
        String ano = "" + localDate.getYear();
        ano = ano.substring(2);
        return dia + "/" + mes + "/" + ano;
    }

    public static String horaBuilder(LocalDateTime localDate) {
        String hora = "" + localDate.getHour();
        if (hora.length() == 1) {
            hora = "0" + hora;
        }
        String min = "" + localDate.getMinute();
        if (min.length() == 1) {
            min = "0" + min;
        }
        return (hora + ":" + min);
    }
}
