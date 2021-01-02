package views;

import java.sql.SQLException;
import java.util.ArrayList;
import javax.swing.*;
import models.*;
import controllers.*;
import java.applet.Applet;
import java.applet.AudioClip;
import java.awt.event.ItemEvent;
import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.util.StringTokenizer;
import javax.swing.table.DefaultTableModel;
import views.sounds.AlertSounds;

// ÍNDICE
// Client Functions - linha 30 a 80
public class MainScreen extends javax.swing.JFrame {

    //GLOBAL VARS
    //SET THE CLIENT AND QUEIJO REGISTRATION TO INSERT OR UPDATE
    boolean isClientUpdate = false;
    boolean isQueijoUpdate = false;
    //HELPS THE TABLES ORDENATION
    String clientOrder = "";
    String queijoOrder = "";
    boolean clientOrderDecreasing = false;
    boolean queijoOrderDecreasing = false;

    //HELPS THE COMEBACK BUTTON IN ClientRegistration and QueijoRegistration Pages
    boolean fromPedidoToClientRegistration = false;
    boolean fromPedidoToQueijoRegistration = false;

    //LIST OF PEDIDOS IN THE PEDIDO REGISTRATION PAGE (BEFORE THE DATABASE LIST CREATION)
    ArrayList<QueijoPedido> queijoPedidoList = new ArrayList();

    //SOUNDS
    AlertSounds player;

    //JList
    private DefaultListModel jListQueijoModel;
    
    //DECIMAL FORMATTER
    DecimalFormat df = new DecimalFormat("#.00");

    public MainScreen() {
        initComponents();

        //SOUNDS
        player = new AlertSounds();

        //JTabbed Panel
        jlb_totalClientes.setText("30");
        jpn_clientsList.setVisible(true);
        jpn_queijoList.setVisible(true);
        jpn_pedidoList.setVisible(true);

        //Second panel
        jpn_clientRegistration.setVisible(false);
        jpn_queijoRegistration.setVisible(false);

        //Tables Initialization
        clientTableBuilder(jTableClient, ClientDAO.read(false, "", false));
        queijoTableBuilder(jTableQueijo, QueijoDAO.read(false, "", false));
        produtosPedidosTableBuilder(jtb_resumo_produtos_pedido, queijoPedidoList, true);
        pedidosTableBuilder(jtb_PedidoList, PedidoDAO.read());
        produtosPedidosTableBuilder(jtb_PedidoList_queijoPedido, QueijoPedidoDAO.read("" + jtb_PedidoList.getValueAt(0, 1)), false);
        jl_pedidoList_id.setText("" + jtb_PedidoList.getValueAt(0, 1));

        //Lists Initialization
        queijoListBuilder();
        jtf_pedido_nomeProduto.setEditable(false);
        jtf_pedido_id.setEditable(false);
        jtf_pedido_id.setText("Gerado pelo Sistema");

        //Masks Initialization
        try {
            jtf_client_Phone.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("(##)########*")));
            jtf_client_CPF.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("###.###.###-##")));
            jtf_pedido_data.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("##/##/##")));
            jtf_pedido_hora.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("##:##")));
        } catch (java.text.ParseException ex) {
            System.out.print("Erro Encontrado: " + ex.toString());
        }

        //ComboBox Initialization
        //QueijosOrder
        jComboBox_ordenar_queijos.removeAllItems();
        jComboBox_ordenar_queijos.addItem("-------");
        jComboBox_ordenar_queijos.addItem("Id");
        jComboBox_ordenar_queijos.addItem("Peso");
        jComboBox_ordenar_queijos.addItem("Tipo");
        jComboBox_ordenar_queijos.addItem("Valor por Kg");
        jComboBox_ordenar_queijos.addItem("Temeperatura Ideal");

        //ClientesOrder
        jComboBox_ordenar_clientes.removeAllItems();
        jComboBox_ordenar_clientes.addItem("-------");
        jComboBox_ordenar_clientes.addItem("CPF");
        jComboBox_ordenar_clientes.addItem("Nome");
        jComboBox_ordenar_clientes.addItem("Endereço");
        jComboBox_ordenar_clientes.addItem("Cartão Crédito");
        jComboBox_ordenar_clientes.addItem("Facebook");
        jComboBox_ordenar_clientes.addItem("Instagram");

        //ClientComboBox Registration Page
        clientComboBoxBuilder();

    }

    // Client Functions Begin ----------------------------------------------------------------------------------------------
    void clientTableBuilder(JTable jtable, ArrayList<Client> clientList) {
        DefaultTableModel tableRows;
        tableRows = new DefaultTableModel(new String[]{"Nº", "CPF", "Nome", "Telefone",
            "Endereço", "Cartão Crédito", "Facebook", "Instagram"}, 0);
        for (int i = 0; i < clientList.size(); i++) {
            Client c = clientList.get(i);
            tableRows.addRow(new Object[]{(i + 1), c.getCPF(), c.getClientName(), c.getPhone(),
                c.getAddress(), c.getCreditCard(), c.getFacebookURL(), c.getInstagramURL()});
        }
        jtable.setModel(tableRows);
        jlb_totalClientes.setText("" + clientList.size());
    }

    public boolean NewClientVerification() {
        boolean valido = true;
        String erro = "";
        if (jtf_client_CPF.getText().equals("   .   .   -  ") || jtf_client_CPF.getText().contains(" ")) {
            valido = false;
            erro = erro + "\nCampo CPF Vazio ou Incompleto";
        }
        if (jtf_client_Name.getText().isEmpty()) {
            valido = false;
            erro = erro + "\nCampo Nome Vazio";
        }
        if (jtf_client_Phone.getText().equals("(  )         ")) {
            valido = false;
            erro = erro + "\nCampo Telefone Vazio ou Incompleto";
        } else {
            for (int i = 0; i < jtf_client_Phone.getText().length() - 1; i++) {
                if (jtf_client_Phone.getText().charAt(i) == ' ') {
                    valido = false;
                    erro = erro + "\nCampo Telefone Vazio ou Incompleto";
                }
            }
        }
        if (jtf_client_Address.getText().isEmpty()) {
            valido = false;
            erro = erro + "\nCampo Endereço Vazio";
        }
        if (jtf_client_CreditCard.getText().isEmpty()) {
            valido = false;
            erro = erro + "\nCampo Cartão Crédito Vazio";
        }
        if (jtf_client_Insta.getText().isEmpty()) {
            valido = false;
            erro = erro + "\nCampo Instagram Vazio";
        }
        if (jtf_client_Face.getText().isEmpty()) {
            valido = false;
            erro = erro + "\nCampo Facebook Vazio";
        }
        if (valido == false) {
            player.erroSong.play();
            JOptionPane.showMessageDialog(null, "Erro(s) Encontrados: " + erro,
                    "Erro ao Realizar Operação", JOptionPane.ERROR_MESSAGE);
        }
        return valido;
    }
    // Client Functions End ----------------------------------------------------------------------------------------------

    // Queijo Functions Begin ----------------------------------------------------------------------------------------------
    void queijoTableBuilder(JTable jtable, ArrayList<Queijo> queijoList) {
        DefaultTableModel tableRows1;
        tableRows1 = new DefaultTableModel(new String[]{"Nº", "ID", "Peso", "Valor Por Kg",
            "Tipo", "Temperatura Ideal", "Valor Venda"}, 0);
        for (int i = 0; i < queijoList.size(); i++) {
            Queijo q = queijoList.get(i);
            tableRows1.addRow(new Object[]{(i + 1), q.getQueijoID(), df.format(q.getWeight()),
                q.getPricePerKg(), q.getQueijoType(), q.getRecommendedTemperature(), df.format(q.getWeight() * q.getPricePerKg())});
        }
        jtable.setModel(tableRows1);
        jlb_totalQueijos.setText("" + queijoList.size());

    }

    public boolean NewQueijoVerification() {
        boolean valido = true;
        String erro = "";
        if (jtf_queijo_peso.getText().isEmpty()) {
            valido = false;
            erro = erro + "\nCampo Peso Vazio";
        }
        if (jtf_queijo_valorKg.getText().isEmpty()) {
            valido = false;
            erro = erro + "\nCampo Valor por Kg Vazio";
        }
        if (jtf_queijo_tipo.getText().isEmpty()) {
            valido = false;
            erro = erro + "\nCampo Tipo do Queijo Vazio";
        }
        if (jtf_queijo_Temperatura.getText().isEmpty()) {
            valido = false;
            erro = erro + "\nCampo Temperatura Ideal Vazio";
        }
        if (valido == false) {
            player.erroSong.play();
            JOptionPane.showMessageDialog(null, "Erro(s) Encontrados: " + erro,
                    "Erro ao Realizar Operação", JOptionPane.ERROR_MESSAGE);
        }
        return valido;
    }
    // Queijo Functions End ----------------------------------------------------------------------------------------------

    // Pedido Functions Beginning --------------------------------------------------------------------------------------------
    void queijoListBuilder() {
        jListQueijoModel = new DefaultListModel();
        jListQueijoModel.removeAllElements();
        ArrayList<Queijo> queijoList = QueijoDAO.read(true, "queijoid", false);
        queijoList.forEach(q -> {
            jListQueijoModel.addElement("ID: " + q.getQueijoID() + ", Tipo: "
                    + q.getQueijoType() + ", Peso: " + df.format(q.getWeight()) + ", Preço/Kg: " + df.format(q.getPricePerKg()));
        });
        jList_pedido_queijos.setModel(jListQueijoModel);
        jList_pedido_queijos.setSelectedIndex(0);
    }

    void clientComboBoxBuilder() {
        jcb_pedido_client.removeAllItems();
        jcb_pedido_client.addItem("Selecionar...");
        ArrayList<Client> clientList = ClientDAO.read(true, "clientName", false);
        clientList.forEach(c -> {
            jcb_pedido_client.addItem(c.getClientName());
        });
        jtf_pedido_cpfCliente.setEditable(false);
        jtf_pedido_cpfCliente.setText("");
        jcb_pedido_client.setSelectedIndex(0);
        jtf_pedido_cpfCliente.setText("Selecione o cliente acima!");
    }

    void produtosPedidosTableBuilder(JTable jtable, ArrayList<QueijoPedido> produtosPedido, boolean registrationPage) {
        DefaultTableModel tableRows2;
        double total = 0.00;


        tableRows2 = new DefaultTableModel(new String[]{"Nº", "ID", "Tipo", "Peso", "Valor/Kg",
            "Qtd", "Valor Total"}, 0);
        ArrayList<Queijo> queijos = QueijoDAO.read(false, "", false);

        if (!produtosPedido.isEmpty()) {
            for (int j = 0; j < produtosPedido.size(); j++) {
                QueijoPedido p = produtosPedido.get(j);
                boolean achou = false;
                for (int i = 0; i < queijos.size() && achou == false; i++) {
                    Queijo queijoAux = queijos.get(i);
                    if (String.valueOf(p.getFk_id_queijo()).equals("" + queijoAux.getQueijoID())) {
                        achou = true;
                        tableRows2.addRow(new Object[]{(j + 1), queijoAux.getQueijoID(),
                            queijoAux.getQueijoType(), df.format(queijoAux.getWeight()),
                            queijoAux.getPricePerKg(), p.getQuantity(), df.format(p.getQuantity()
                            * queijoAux.getPricePerKg() * queijoAux.getWeight())});
                        total += (p.getQuantity()
                                * queijoAux.getPricePerKg() * queijoAux.getWeight());
                        
                    }

                }
                if (registrationPage == false) {
                    jta_pedidoList_obs.setText(PedidoDAO.search("" + p.getFk_id_pedido()).getNote());
                }
            }
        }
        if (registrationPage) {
            jlb_pedido_valor_total.setText("" + df.format(total));
        } else {
            jlb_pedidoList_valor_total.setText("" + df.format(total));

        }
        jtable.setModel(tableRows2);
    }

    void pedidosTableBuilder(JTable jtable, ArrayList<Pedido> pedidoList) {
        DefaultTableModel tableRows1;
        tableRows1 = new DefaultTableModel(new String[]{"Nº", "ID", "CPF", "Data",
            "Hora", "Prazo (dias)"}, 0);
        for (int i = 0; i < pedidoList.size(); i++) {
            Pedido p = pedidoList.get(i);
            tableRows1.addRow(new Object[]{(i + 1), p.getPedidoID(), p.getFk_client_cpf(),
                dataBuilder(p.getPedidoDate()), horaBuilder(p.getPedidoDate()), p.getDeliveryDeadLine()});
        }
        jtable.setModel(tableRows1);
        jlb_totalPedidos.setText("" + pedidoList.size());
    }

    String dataBuilder(LocalDateTime localDate) {
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

    String horaBuilder(LocalDateTime localDate) {
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

    void clearPedidoRegistrationPage() {
        jtf_pedido_cpfCliente.setText("");
        jtf_pedido_data.setText("");
        jtf_pedido_deliveryDeadLine.setText("");
        jtf_pedido_hora.setText("");
        jtf_pedido_id.setText("Gerado pelo Sistema");
        jtf_pedido_n_cancelar.setText("");
        jtf_pedido_n_cancelar.setText("");
        jta_pedido_note.setText("");
        jsp_quantidade_produto.setValue(0);
        jcb_pedido_client.setSelectedIndex(0);
        jList_pedido_queijos.setSelectedIndex(0);
        jtf_pedido_nomeProduto.setText(jList_pedido_queijos.getSelectedValue());
        produtosPedidosTableBuilder(jtb_resumo_produtos_pedido, new ArrayList(), true);
    }

    boolean newPedidoVerification() {
        boolean valido = true;
        String erro = "";
        if (jcb_pedido_client.getSelectedIndex() == 0) {
            valido = false;
            erro = erro + "\nCliente Não selecionado";
        }
        if (dateVerification(jtf_pedido_data.getText()) == false) {
            valido = false;
            erro = erro + "\nData Inválida";
        }
        if (timeVerification(jtf_pedido_hora.getText()) == false) {
            valido = false;
            erro = erro + "\nHora Inválida";
        }
        if (jtf_pedido_deliveryDeadLine.getText().isEmpty()) {
            valido = false;
            erro = erro + "\nPrazo Entrega Inválido";
        }
        if (valido == false) {
            player.erroSong.play();
            JOptionPane.showMessageDialog(null, "Erro(s) Encontrados: " + erro,
                    "Erro ao Realizar Operação", JOptionPane.ERROR_MESSAGE);
        }

        return valido;
    }

    boolean timeVerification(String time) {
        boolean valido = true;
        if (time.equals("  :  ") || time.contains(" ")) {
            valido = false;
        }
        if (valido == true) {
            StringTokenizer token = new StringTokenizer(time, ":");
            int hora = Integer.parseInt(token.nextToken());
            int min = Integer.parseInt(token.nextToken());

            if (hora < 0 || hora > 23) {
                valido = false;
            }
            if (min < 0 || min > 59) {
                valido = false;
            }
        }
        return valido;
    }

    boolean dateVerification(String date) {
        //CRIAÇÃO DAS VARIAVEIS
        boolean dataValida = true;
        int dia = 29, mes = 12, ano = 2020;
        if (date.equals("  /  /  ") || date.contains(" ")) {
            dataValida = false;
        }
        if (dataValida == true) {
            StringTokenizer dataTokenizer = new StringTokenizer(date, "/");
            dia = Integer.parseInt(dataTokenizer.nextToken());
            mes = Integer.parseInt(dataTokenizer.nextToken());
            if (mes > 12 || mes < 1) {
                dataValida = false;
            }
            //MES COM 31 DIAS
            if (mes == 1 || mes == 3 || mes == 5 || mes == 7 || mes == 8 || mes == 10 || mes == 12) {
                if (dia > 31 || dia < 1) {
                    dataValida = false;
                }
            } else if (mes == 2) {
                //FEVEREIRO COM 29 DIAS
                if (ano % 4 == 0) {
                    if (dia > 29 || dia < 1) {
                        dataValida = false;
                    }
                } else {
                    //FEVEREIRO COM 28 DIAS
                    if (dia > 28 || dia < 1) {
                        dataValida = false;
                    }
                }
            } else {
                //MES COM 30 DIAS
                if (dia > 30 || dia < 1) {
                    dataValida = false;
                }
            }
        }
        return dataValida;
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jTabbedPane1 = new javax.swing.JTabbedPane();
        jpanel_Dashboard = new javax.swing.JPanel();
        jPanel_OrderPedido = new javax.swing.JPanel();
        jLayeredPane1 = new javax.swing.JLayeredPane();
        jPanel4 = new javax.swing.JPanel();
        jLabel20 = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        jLabel22 = new javax.swing.JLabel();
        jLabel23 = new javax.swing.JLabel();
        jLabel24 = new javax.swing.JLabel();
        jtf_pedido_deliveryDeadLine = new javax.swing.JTextField();
        jtf_pedido_id = new javax.swing.JTextField();
        jcb_pedido_client = new javax.swing.JComboBox<>();
        jb_pedido_newClient = new javax.swing.JButton();
        jScrollPane3 = new javax.swing.JScrollPane();
        jta_pedido_note = new javax.swing.JTextArea();
        jL_Cadastrar_cliente1 = new javax.swing.JLabel();
        jL_Cadastrar_cliente2 = new javax.swing.JLabel();
        jL_Cadastrar_cliente3 = new javax.swing.JLabel();
        jScrollPane4 = new javax.swing.JScrollPane();
        jList_pedido_queijos = new javax.swing.JList<>();
        jScrollPane5 = new javax.swing.JScrollPane();
        jtb_resumo_produtos_pedido = new javax.swing.JTable();
        jL_Cadastrar_cliente4 = new javax.swing.JLabel();
        jtf_pedido_nomeProduto = new javax.swing.JTextField();
        jLabel25 = new javax.swing.JLabel();
        jLabel26 = new javax.swing.JLabel();
        jb_pedido_addProduto = new javax.swing.JButton();
        jb_pedido_finalizar = new javax.swing.JButton();
        jb_back_pedidoRegistration = new javax.swing.JButton();
        jb_pedido_newQueijo = new javax.swing.JButton();
        jtf_pedido_data = new javax.swing.JFormattedTextField();
        jsp_quantidade_produto = new javax.swing.JSpinner();
        jLabel31 = new javax.swing.JLabel();
        jtf_pedido_cpfCliente = new javax.swing.JFormattedTextField();
        jLabel33 = new javax.swing.JLabel();
        jb_pedido_hora_now = new javax.swing.JButton();
        jtf_pedido_hora = new javax.swing.JFormattedTextField();
        jLabel32 = new javax.swing.JLabel();
        jlb_pedido_valor_total = new javax.swing.JLabel();
        jLabel35 = new javax.swing.JLabel();
        jLabel36 = new javax.swing.JLabel();
        jtf_pedido_n_cancelar = new javax.swing.JTextField();
        jbt_pedido_cancelar_produto = new javax.swing.JButton();
        jLabel37 = new javax.swing.JLabel();
        jPanel14 = new javax.swing.JPanel();
        jPanel15 = new javax.swing.JPanel();
        jPanel8 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jPanel_PedidosList = new javax.swing.JPanel();
        jLayeredPane4 = new javax.swing.JLayeredPane();
        jpn_pedidoList = new javax.swing.JPanel();
        jl_pedidoList_id = new javax.swing.JLabel();
        jScrollPane6 = new javax.swing.JScrollPane();
        jtb_PedidoList = new javax.swing.JTable();
        jLabel28 = new javax.swing.JLabel();
        jcb_ordenar_pedidos = new javax.swing.JComboBox<>();
        jb_ordenar_pedidos_desc = new javax.swing.JButton();
        jb_ordenar_pedidos_cres = new javax.swing.JButton();
        jLabel29 = new javax.swing.JLabel();
        jlb_totalPedidos = new javax.swing.JLabel();
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();
        jButton6 = new javax.swing.JButton();
        jScrollPane7 = new javax.swing.JScrollPane();
        jtb_PedidoList_queijoPedido = new javax.swing.JTable();
        jLabel34 = new javax.swing.JLabel();
        jLabel38 = new javax.swing.JLabel();
        jLabel39 = new javax.swing.JLabel();
        jLabel40 = new javax.swing.JLabel();
        jtf_pedidoList_selected_id = new javax.swing.JTextField();
        jb_pedidoList_consultar = new javax.swing.JButton();
        jLabel30 = new javax.swing.JLabel();
        jLabel41 = new javax.swing.JLabel();
        jLabel42 = new javax.swing.JLabel();
        jLabel43 = new javax.swing.JLabel();
        jComboBox2 = new javax.swing.JComboBox<>();
        jButton8 = new javax.swing.JButton();
        jButton9 = new javax.swing.JButton();
        jLabel44 = new javax.swing.JLabel();
        jcb_ordenar_QueijosPedido = new javax.swing.JComboBox<>();
        jb_ordenar_QueijosPedido_desc = new javax.swing.JButton();
        jb_ordenar_QueijosPedido_cres = new javax.swing.JButton();
        jScrollPane8 = new javax.swing.JScrollPane();
        jta_pedidoList_obs = new javax.swing.JTextArea();
        jLabel27 = new javax.swing.JLabel();
        jLabel45 = new javax.swing.JLabel();
        jlb_pedidoList_valor_total = new javax.swing.JLabel();
        jLabel46 = new javax.swing.JLabel();
        jPanel19 = new javax.swing.JPanel();
        jPanel18 = new javax.swing.JPanel();
        jPanel17 = new javax.swing.JPanel();
        jPanel16 = new javax.swing.JPanel();
        jPanel_ClientList = new javax.swing.JPanel();
        jLayeredPane2 = new javax.swing.JLayeredPane();
        jpn_clientsList = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTableClient = new javax.swing.JTable();
        jLabel3 = new javax.swing.JLabel();
        jlb_totalClientes = new javax.swing.JLabel();
        jButton_inserirCliente = new javax.swing.JButton();
        jButton_modificarCliente = new javax.swing.JButton();
        jButton_removerCliente = new javax.swing.JButton();
        jLabel19 = new javax.swing.JLabel();
        jComboBox_ordenar_clientes = new javax.swing.JComboBox<>();
        jButton_busca_cliente_cpf = new javax.swing.JButton();
        jButton_busca_cliente_nome = new javax.swing.JButton();
        jButton_client_order_dec = new javax.swing.JButton();
        jButton_client_order_cres = new javax.swing.JButton();
        jpn_clientRegistration = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jL_Cadastrar_cliente = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jtf_client_CreditCard = new javax.swing.JTextField();
        jtf_client_Address = new javax.swing.JTextField();
        jtf_client_Name = new javax.swing.JTextField();
        jtf_client_Insta = new javax.swing.JTextField();
        jtf_client_Face = new javax.swing.JTextField();
        jb_finalizarCadastro = new javax.swing.JButton();
        jb_backClientPage = new javax.swing.JButton();
        jtf_client_CPF = new javax.swing.JFormattedTextField();
        jtf_client_Phone = new javax.swing.JFormattedTextField();
        jPanel3 = new javax.swing.JPanel();
        jPanel5 = new javax.swing.JPanel();
        jPanel6 = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        jPanel_QueijoList = new javax.swing.JPanel();
        jLayeredPane3 = new javax.swing.JLayeredPane();
        jpn_queijoList = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTableQueijo = new javax.swing.JTable();
        jlb_totalQueijos = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jButton_inserirQueijo = new javax.swing.JButton();
        jButton_modificarQueijo = new javax.swing.JButton();
        jButton_removerQueijo = new javax.swing.JButton();
        jLabel18 = new javax.swing.JLabel();
        jComboBox_ordenar_queijos = new javax.swing.JComboBox<>();
        jButton_busca_queijo_ID = new javax.swing.JButton();
        jButton_queijo_menor_temp = new javax.swing.JButton();
        jButton_queijo_mais_caro = new javax.swing.JButton();
        jButton_queijo_order_dec1 = new javax.swing.JButton();
        jButton_queijo_order_cres1 = new javax.swing.JButton();
        jpn_queijoRegistration = new javax.swing.JPanel();
        jL_Cadastrar_queijo = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jtf_queijo_id = new javax.swing.JFormattedTextField();
        jLabel14 = new javax.swing.JLabel();
        jtf_queijo_peso = new javax.swing.JTextField();
        jLabel15 = new javax.swing.JLabel();
        jtf_queijo_valorKg = new javax.swing.JFormattedTextField();
        jLabel16 = new javax.swing.JLabel();
        jtf_queijo_tipo = new javax.swing.JTextField();
        jLabel17 = new javax.swing.JLabel();
        jtf_queijo_Temperatura = new javax.swing.JTextField();
        jb_finalizarCadastroQueijo = new javax.swing.JButton();
        jb_backQueijoPage = new javax.swing.JButton();
        jPanel13 = new javax.swing.JPanel();
        jPanel12 = new javax.swing.JPanel();
        jPanel11 = new javax.swing.JPanel();
        jPanel10 = new javax.swing.JPanel();
        jPanel9 = new javax.swing.JPanel();
        jPanel7 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        jMenuItem_ExportarTXT = new javax.swing.JMenuItem();
        jMenuItem_ExportarPDF = new javax.swing.JMenuItem();
        jMenuItem_ExportarXLS = new javax.swing.JMenuItem();
        jMenu2 = new javax.swing.JMenu();
        jMenuItem_Sobre = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Fábrica de Queijos Uai Sô");
        setResizable(false);

        javax.swing.GroupLayout jpanel_DashboardLayout = new javax.swing.GroupLayout(jpanel_Dashboard);
        jpanel_Dashboard.setLayout(jpanel_DashboardLayout);
        jpanel_DashboardLayout.setHorizontalGroup(
            jpanel_DashboardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1350, Short.MAX_VALUE)
        );
        jpanel_DashboardLayout.setVerticalGroup(
            jpanel_DashboardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 640, Short.MAX_VALUE)
        );

        jTabbedPane1.addTab("Dashboard", jpanel_Dashboard);

        jLayeredPane1.setLayout(new javax.swing.OverlayLayout(jLayeredPane1));

        jLabel20.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel20.setText("ID do pedido:");

        jLabel21.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel21.setText("Cliente:");

        jLabel22.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel22.setText("Data do Pedido:");

        jLabel23.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel23.setText("Prazo de Entrega:");

        jLabel24.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel24.setText("Observações:");

        jcb_pedido_client.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        jcb_pedido_client.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jcb_pedido_clientItemStateChanged(evt);
            }
        });

        jb_pedido_newClient.setIcon(new javax.swing.ImageIcon(getClass().getResource("/views/Icons/baseline_add_black_18dp.png"))); // NOI18N
        jb_pedido_newClient.setIconTextGap(2);
        jb_pedido_newClient.setMargin(new java.awt.Insets(0, 4, 0, 4));
        jb_pedido_newClient.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jb_pedido_newClientActionPerformed(evt);
            }
        });

        jta_pedido_note.setColumns(17);
        jta_pedido_note.setLineWrap(true);
        jta_pedido_note.setRows(5);
        jScrollPane3.setViewportView(jta_pedido_note);

        jL_Cadastrar_cliente1.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jL_Cadastrar_cliente1.setText("Dados Do Pedido:");

        jL_Cadastrar_cliente2.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jL_Cadastrar_cliente2.setText("CADASTRAR NOVO PEDIDO");

        jL_Cadastrar_cliente3.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jL_Cadastrar_cliente3.setText("Adicionar Produto:");

        jList_pedido_queijos.setModel(new javax.swing.AbstractListModel<String>() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public String getElementAt(int i) { return strings[i]; }
        });
        jList_pedido_queijos.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
            public void valueChanged(javax.swing.event.ListSelectionEvent evt) {
                jList_pedido_queijosValueChanged(evt);
            }
        });
        jScrollPane4.setViewportView(jList_pedido_queijos);

        jtb_resumo_produtos_pedido.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane5.setViewportView(jtb_resumo_produtos_pedido);

        jL_Cadastrar_cliente4.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jL_Cadastrar_cliente4.setText("Resumo dos Produtos:");

        jtf_pedido_nomeProduto.setColumns(1);

        jLabel25.setText("Produto:");

        jLabel26.setText("Qtd:");

        jb_pedido_addProduto.setIcon(new javax.swing.ImageIcon(getClass().getResource("/views/Icons/baseline_add_black_18dp.png"))); // NOI18N
        jb_pedido_addProduto.setText("Adicionar Produto ao Pedido");
        jb_pedido_addProduto.setMargin(new java.awt.Insets(0, 14, 0, 14));
        jb_pedido_addProduto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jb_pedido_addProdutoActionPerformed(evt);
            }
        });

        jb_pedido_finalizar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/views/Icons/baseline_add_black_18dp.png"))); // NOI18N
        jb_pedido_finalizar.setText("FINALIZAR E SALVAR O PEDIDO");
        jb_pedido_finalizar.setMargin(new java.awt.Insets(4, 16, 4, 16));
        jb_pedido_finalizar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jb_pedido_finalizarActionPerformed(evt);
            }
        });

        jb_back_pedidoRegistration.setIcon(new javax.swing.ImageIcon(getClass().getResource("/views/Icons/baseline_arrow_back_black_18dp.png"))); // NOI18N
        jb_back_pedidoRegistration.setText("Cancelar e Descartar");
        jb_back_pedidoRegistration.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jb_back_pedidoRegistrationActionPerformed(evt);
            }
        });

        jb_pedido_newQueijo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/views/Icons/baseline_add_black_18dp.png"))); // NOI18N
        jb_pedido_newQueijo.setIconTextGap(2);
        jb_pedido_newQueijo.setMargin(new java.awt.Insets(0, 4, 0, 4));
        jb_pedido_newQueijo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jb_pedido_newQueijoActionPerformed(evt);
            }
        });

        jLabel31.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel31.setText("Cpf Cliente:");

        jLabel33.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel33.setText("Hora:");

        jb_pedido_hora_now.setIcon(new javax.swing.ImageIcon(getClass().getResource("/views/Icons/baseline_schedule_black_18dp.png"))); // NOI18N
        jb_pedido_hora_now.setMargin(new java.awt.Insets(0, 14, 0, 14));
        jb_pedido_hora_now.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jb_pedido_hora_nowActionPerformed(evt);
            }
        });

        jtf_pedido_hora.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jtf_pedido_horaActionPerformed(evt);
            }
        });

        jLabel32.setFont(new java.awt.Font("Tahoma", 0, 48)); // NOI18N
        jLabel32.setText("TOTAL: R$");

        jlb_pedido_valor_total.setFont(new java.awt.Font("Tahoma", 0, 48)); // NOI18N
        jlb_pedido_valor_total.setText("0,00");
        jlb_pedido_valor_total.setToolTipText("");

        jLabel35.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel35.setText("Cancelar Produto");

        jLabel36.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel36.setText("Digite Número do Produto:");

        jbt_pedido_cancelar_produto.setIcon(new javax.swing.ImageIcon(getClass().getResource("/views/Icons/baseline_clear_black_18dp.png"))); // NOI18N
        jbt_pedido_cancelar_produto.setText("Cancelar");
        jbt_pedido_cancelar_produto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbt_pedido_cancelar_produtoActionPerformed(evt);
            }
        });

        jLabel37.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel37.setText("dias");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(502, 502, 502)
                .addComponent(jL_Cadastrar_cliente2)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel24)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel22)
                            .addComponent(jLabel23))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 210, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jtf_pedido_data, javax.swing.GroupLayout.DEFAULT_SIZE, 70, Short.MAX_VALUE)
                                    .addComponent(jtf_pedido_deliveryDeadLine))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel4Layout.createSequentialGroup()
                                        .addComponent(jLabel33)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jtf_pedido_hora, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(jb_pedido_hora_now, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(jLabel37)))))
                    .addComponent(jL_Cadastrar_cliente1)
                    .addComponent(jb_back_pedidoRegistration)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel21)
                            .addComponent(jLabel20)
                            .addComponent(jLabel31))
                        .addGap(36, 36, 36)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                                .addComponent(jcb_pedido_client, javax.swing.GroupLayout.PREFERRED_SIZE, 169, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jb_pedido_newClient))
                            .addComponent(jtf_pedido_id, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jtf_pedido_cpfCliente))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 52, Short.MAX_VALUE)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addGroup(jPanel4Layout.createSequentialGroup()
                            .addComponent(jL_Cadastrar_cliente3)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jb_pedido_newQueijo))
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                            .addComponent(jtf_pedido_nomeProduto)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(jsp_quantidade_produto, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(jPanel4Layout.createSequentialGroup()
                            .addComponent(jLabel25)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel26))
                        .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 405, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jb_pedido_addProduto, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 405, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(50, 50, 50)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(jb_pedido_finalizar, javax.swing.GroupLayout.DEFAULT_SIZE, 470, Short.MAX_VALUE)
                        .addComponent(jL_Cadastrar_cliente4)
                        .addComponent(jScrollPane5, javax.swing.GroupLayout.DEFAULT_SIZE, 470, Short.MAX_VALUE)
                        .addGroup(jPanel4Layout.createSequentialGroup()
                            .addComponent(jLabel35)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addComponent(jLabel36)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addComponent(jtf_pedido_n_cancelar, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addComponent(jbt_pedido_cancelar_produto, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jLabel32)
                        .addGap(18, 18, 18)
                        .addComponent(jlb_pedido_valor_total, javax.swing.GroupLayout.PREFERRED_SIZE, 231, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(40, 40, 40)
                .addComponent(jL_Cadastrar_cliente2)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jL_Cadastrar_cliente1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel20)
                                    .addComponent(jtf_pedido_id, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(10, 10, 10)
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel21)
                                    .addComponent(jcb_pedido_client, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jb_pedido_newClient))
                                .addGap(18, 18, 18)
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel31)
                                    .addComponent(jtf_pedido_cpfCliente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jtf_pedido_data, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel22)
                                    .addComponent(jLabel33)
                                    .addComponent(jtf_pedido_hora, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addComponent(jb_pedido_hora_now))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jtf_pedido_deliveryDeadLine, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel23)
                            .addComponent(jLabel37))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addComponent(jLabel24)
                                .addGap(214, 214, 214))
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addComponent(jScrollPane3)
                                .addGap(134, 134, 134)))
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jb_back_pedidoRegistration)
                            .addComponent(jb_pedido_finalizar))
                        .addGap(33, 33, 33))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGap(58, 58, 58)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addComponent(jL_Cadastrar_cliente4)
                                .addGap(18, 18, 18)
                                .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 249, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jL_Cadastrar_cliente3)
                                    .addComponent(jb_pedido_newQueijo))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 208, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel25)
                                    .addComponent(jLabel26))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jtf_pedido_nomeProduto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jsp_quantidade_produto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel35, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel36)
                                .addComponent(jtf_pedido_n_cancelar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jbt_pedido_cancelar_produto))
                            .addComponent(jb_pedido_addProduto))
                        .addGap(51, 51, 51)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel32, javax.swing.GroupLayout.PREFERRED_SIZE, 68, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jlb_pedido_valor_total, javax.swing.GroupLayout.PREFERRED_SIZE, 68, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(79, 79, 79))))
        );

        jLayeredPane1.add(jPanel4);

        javax.swing.GroupLayout jPanel14Layout = new javax.swing.GroupLayout(jPanel14);
        jPanel14.setLayout(jPanel14Layout);
        jPanel14Layout.setHorizontalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1350, Short.MAX_VALUE)
        );
        jPanel14Layout.setVerticalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 640, Short.MAX_VALUE)
        );

        jLayeredPane1.add(jPanel14);

        javax.swing.GroupLayout jPanel15Layout = new javax.swing.GroupLayout(jPanel15);
        jPanel15.setLayout(jPanel15Layout);
        jPanel15Layout.setHorizontalGroup(
            jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1350, Short.MAX_VALUE)
        );
        jPanel15Layout.setVerticalGroup(
            jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 640, Short.MAX_VALUE)
        );

        jLayeredPane1.add(jPanel15);

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1350, Short.MAX_VALUE)
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 640, Short.MAX_VALUE)
        );

        jLayeredPane1.add(jPanel8);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1350, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 640, Short.MAX_VALUE)
        );

        jLayeredPane1.add(jPanel2);

        javax.swing.GroupLayout jPanel_OrderPedidoLayout = new javax.swing.GroupLayout(jPanel_OrderPedido);
        jPanel_OrderPedido.setLayout(jPanel_OrderPedidoLayout);
        jPanel_OrderPedidoLayout.setHorizontalGroup(
            jPanel_OrderPedidoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLayeredPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 1350, Short.MAX_VALUE)
        );
        jPanel_OrderPedidoLayout.setVerticalGroup(
            jPanel_OrderPedidoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLayeredPane1)
        );

        jTabbedPane1.addTab("Realizar Pedido", jPanel_OrderPedido);

        jLayeredPane4.setLayout(new javax.swing.OverlayLayout(jLayeredPane4));

        jpn_pedidoList.setLayout(null);

        jl_pedidoList_id.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jl_pedidoList_id.setForeground(java.awt.Color.red);
        jl_pedidoList_id.setText("0");
        jpn_pedidoList.add(jl_pedidoList_id);
        jl_pedidoList_id.setBounds(910, 10, 60, 22);

        jtb_PedidoList.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane6.setViewportView(jtb_PedidoList);

        jpn_pedidoList.add(jScrollPane6);
        jScrollPane6.setBounds(10, 45, 580, 540);

        jLabel28.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel28.setText("Ordenar:");
        jpn_pedidoList.add(jLabel28);
        jLabel28.setBounds(10, 14, 62, 17);

        jcb_ordenar_pedidos.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        jpn_pedidoList.add(jcb_ordenar_pedidos);
        jcb_ordenar_pedidos.setBounds(82, 14, 160, 20);

        jb_ordenar_pedidos_desc.setIcon(new javax.swing.ImageIcon(getClass().getResource("/views/Icons/baseline_keyboard_arrow_down_black_18dp.png"))); // NOI18N
        jb_ordenar_pedidos_desc.setMargin(new java.awt.Insets(0, 14, 0, 14));
        jpn_pedidoList.add(jb_ordenar_pedidos_desc);
        jb_ordenar_pedidos_desc.setBounds(252, 11, 51, 23);

        jb_ordenar_pedidos_cres.setIcon(new javax.swing.ImageIcon(getClass().getResource("/views/Icons/baseline_keyboard_arrow_up_black_18dp.png"))); // NOI18N
        jb_ordenar_pedidos_cres.setMargin(new java.awt.Insets(0, 14, 0, 14));
        jpn_pedidoList.add(jb_ordenar_pedidos_cres);
        jb_ordenar_pedidos_cres.setBounds(313, 11, 51, 23);

        jLabel29.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel29.setText("Total de Pedidos Cadastrados: ");
        jpn_pedidoList.add(jLabel29);
        jLabel29.setBounds(10, 612, 189, 17);

        jlb_totalPedidos.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jpn_pedidoList.add(jlb_totalPedidos);
        jlb_totalPedidos.setBounds(209, 612, 34, 17);

        jButton3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/views/Icons/baseline_add_black_18dp.png"))); // NOI18N
        jButton3.setText("Inserir Pedido");
        jpn_pedidoList.add(jButton3);
        jButton3.setBounds(1190, 600, 150, 27);

        jButton4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/views/Icons/baseline_edit_black_18dp.png"))); // NOI18N
        jButton4.setText("Modificar Pedido");
        jpn_pedidoList.add(jButton4);
        jButton4.setBounds(1020, 600, 160, 27);

        jButton5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/views/Icons/baseline_delete_black_18dp.png"))); // NOI18N
        jButton5.setText("Remover Pedido");
        jpn_pedidoList.add(jButton5);
        jButton5.setBounds(850, 600, 160, 27);

        jButton6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/views/Icons/baseline_search_black_18dp.png"))); // NOI18N
        jButton6.setText("PEDIDO MAIS ANTIGO DE UM CLIENTE");
        jpn_pedidoList.add(jButton6);
        jButton6.setBounds(540, 600, 280, 27);

        jtb_PedidoList_queijoPedido.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane7.setViewportView(jtb_PedidoList_queijoPedido);

        jpn_pedidoList.add(jScrollPane7);
        jScrollPane7.setBounds(762, 45, 580, 410);

        jLabel34.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel34.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel34.setText("DO PEDIDO:");
        jpn_pedidoList.add(jLabel34);
        jLabel34.setBounds(590, 170, 170, 30);

        jLabel38.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel38.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel38.setText("DO PEDIDO:");
        jpn_pedidoList.add(jLabel38);
        jLabel38.setBounds(590, 270, 170, 30);

        jLabel39.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel39.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel39.setText("MOSTRAR INFO");
        jpn_pedidoList.add(jLabel39);
        jLabel39.setBounds(590, 140, 170, 30);

        jLabel40.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel40.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel40.setText("DIGITE O ID ");
        jpn_pedidoList.add(jLabel40);
        jLabel40.setBounds(590, 240, 170, 30);
        jpn_pedidoList.add(jtf_pedidoList_selected_id);
        jtf_pedidoList_selected_id.setBounds(640, 310, 70, 20);

        jb_pedidoList_consultar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/views/Icons/baseline_arrow_forward_ios_black_18dp.png"))); // NOI18N
        jb_pedidoList_consultar.setText("CONSULTAR");
        jb_pedidoList_consultar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jb_pedidoList_consultarActionPerformed(evt);
            }
        });
        jpn_pedidoList.add(jb_pedidoList_consultar);
        jb_pedidoList_consultar.setBounds(610, 350, 130, 27);

        jLabel30.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel30.setText("PEDIDOS");
        jpn_pedidoList.add(jLabel30);
        jLabel30.setBounds(635, 10, 85, 22);

        jLabel41.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel41.setText("CADASTRADOS");
        jpn_pedidoList.add(jLabel41);
        jLabel41.setBounds(610, 30, 136, 22);

        jLabel42.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel42.setText("ID PEDIDO:");
        jpn_pedidoList.add(jLabel42);
        jLabel42.setBounds(790, 10, 110, 22);

        jLabel43.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel43.setText("Ordenar:");
        jpn_pedidoList.add(jLabel43);
        jLabel43.setBounds(10, 14, 62, 17);

        jComboBox2.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        jpn_pedidoList.add(jComboBox2);
        jComboBox2.setBounds(82, 14, 160, 20);

        jButton8.setIcon(new javax.swing.ImageIcon(getClass().getResource("/views/Icons/baseline_keyboard_arrow_down_black_18dp.png"))); // NOI18N
        jButton8.setMargin(new java.awt.Insets(0, 14, 0, 14));
        jpn_pedidoList.add(jButton8);
        jButton8.setBounds(252, 11, 51, 23);

        jButton9.setIcon(new javax.swing.ImageIcon(getClass().getResource("/views/Icons/baseline_keyboard_arrow_up_black_18dp.png"))); // NOI18N
        jButton9.setMargin(new java.awt.Insets(0, 14, 0, 14));
        jpn_pedidoList.add(jButton9);
        jButton9.setBounds(313, 11, 51, 23);

        jLabel44.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel44.setText("Ordenar:");
        jpn_pedidoList.add(jLabel44);
        jLabel44.setBounds(980, 10, 62, 17);

        jcb_ordenar_QueijosPedido.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        jpn_pedidoList.add(jcb_ordenar_QueijosPedido);
        jcb_ordenar_QueijosPedido.setBounds(1060, 10, 160, 20);

        jb_ordenar_QueijosPedido_desc.setIcon(new javax.swing.ImageIcon(getClass().getResource("/views/Icons/baseline_keyboard_arrow_down_black_18dp.png"))); // NOI18N
        jb_ordenar_QueijosPedido_desc.setMargin(new java.awt.Insets(0, 14, 0, 14));
        jpn_pedidoList.add(jb_ordenar_QueijosPedido_desc);
        jb_ordenar_QueijosPedido_desc.setBounds(1230, 10, 51, 23);

        jb_ordenar_QueijosPedido_cres.setIcon(new javax.swing.ImageIcon(getClass().getResource("/views/Icons/baseline_keyboard_arrow_up_black_18dp.png"))); // NOI18N
        jb_ordenar_QueijosPedido_cres.setMargin(new java.awt.Insets(0, 14, 0, 14));
        jpn_pedidoList.add(jb_ordenar_QueijosPedido_cres);
        jb_ordenar_QueijosPedido_cres.setBounds(1290, 10, 51, 23);

        jta_pedidoList_obs.setColumns(20);
        jta_pedidoList_obs.setRows(5);
        jScrollPane8.setViewportView(jta_pedidoList_obs);

        jpn_pedidoList.add(jScrollPane8);
        jScrollPane8.setBounds(760, 480, 260, 100);

        jLabel27.setText("OBSERVAÇÕES:");
        jpn_pedidoList.add(jLabel27);
        jLabel27.setBounds(760, 460, 110, 14);

        jLabel45.setFont(new java.awt.Font("Tahoma", 0, 48)); // NOI18N
        jLabel45.setText("TOTAL:");
        jpn_pedidoList.add(jLabel45);
        jLabel45.setBounds(1090, 460, 170, 58);

        jlb_pedidoList_valor_total.setFont(new java.awt.Font("Tahoma", 0, 48)); // NOI18N
        jlb_pedidoList_valor_total.setText("0,00");
        jlb_pedidoList_valor_total.setToolTipText("");
        jpn_pedidoList.add(jlb_pedidoList_valor_total);
        jlb_pedidoList_valor_total.setBounds(1110, 520, 230, 58);

        jLabel46.setFont(new java.awt.Font("Tahoma", 0, 48)); // NOI18N
        jLabel46.setText("R$");
        jpn_pedidoList.add(jLabel46);
        jLabel46.setBounds(1040, 520, 70, 58);

        jLayeredPane4.add(jpn_pedidoList);

        javax.swing.GroupLayout jPanel19Layout = new javax.swing.GroupLayout(jPanel19);
        jPanel19.setLayout(jPanel19Layout);
        jPanel19Layout.setHorizontalGroup(
            jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1350, Short.MAX_VALUE)
        );
        jPanel19Layout.setVerticalGroup(
            jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 640, Short.MAX_VALUE)
        );

        jLayeredPane4.add(jPanel19);

        javax.swing.GroupLayout jPanel18Layout = new javax.swing.GroupLayout(jPanel18);
        jPanel18.setLayout(jPanel18Layout);
        jPanel18Layout.setHorizontalGroup(
            jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1350, Short.MAX_VALUE)
        );
        jPanel18Layout.setVerticalGroup(
            jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 640, Short.MAX_VALUE)
        );

        jLayeredPane4.add(jPanel18);

        javax.swing.GroupLayout jPanel17Layout = new javax.swing.GroupLayout(jPanel17);
        jPanel17.setLayout(jPanel17Layout);
        jPanel17Layout.setHorizontalGroup(
            jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1350, Short.MAX_VALUE)
        );
        jPanel17Layout.setVerticalGroup(
            jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 640, Short.MAX_VALUE)
        );

        jLayeredPane4.add(jPanel17);

        javax.swing.GroupLayout jPanel16Layout = new javax.swing.GroupLayout(jPanel16);
        jPanel16.setLayout(jPanel16Layout);
        jPanel16Layout.setHorizontalGroup(
            jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1350, Short.MAX_VALUE)
        );
        jPanel16Layout.setVerticalGroup(
            jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 640, Short.MAX_VALUE)
        );

        jLayeredPane4.add(jPanel16);

        javax.swing.GroupLayout jPanel_PedidosListLayout = new javax.swing.GroupLayout(jPanel_PedidosList);
        jPanel_PedidosList.setLayout(jPanel_PedidosListLayout);
        jPanel_PedidosListLayout.setHorizontalGroup(
            jPanel_PedidosListLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLayeredPane4)
        );
        jPanel_PedidosListLayout.setVerticalGroup(
            jPanel_PedidosListLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLayeredPane4)
        );

        jTabbedPane1.addTab("Mostrar Pedidos", jPanel_PedidosList);

        jLayeredPane2.setLayout(new javax.swing.OverlayLayout(jLayeredPane2));

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel2.setText("CLIENTES CADASTRADOS");

        jTableClient.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane1.setViewportView(jTableClient);

        jLabel3.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel3.setText("Total de Clientes Cadastrados: ");

        jlb_totalClientes.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        jButton_inserirCliente.setIcon(new javax.swing.ImageIcon(getClass().getResource("/views/Icons/baseline_add_black_18dp.png"))); // NOI18N
        jButton_inserirCliente.setText("Inserir Cliente");
        jButton_inserirCliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_inserirClienteActionPerformed(evt);
            }
        });

        jButton_modificarCliente.setIcon(new javax.swing.ImageIcon(getClass().getResource("/views/Icons/baseline_edit_black_18dp.png"))); // NOI18N
        jButton_modificarCliente.setText("Modificar Cliente");
        jButton_modificarCliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_modificarClienteActionPerformed(evt);
            }
        });

        jButton_removerCliente.setIcon(new javax.swing.ImageIcon(getClass().getResource("/views/Icons/baseline_delete_black_18dp.png"))); // NOI18N
        jButton_removerCliente.setText("Remover Cliente");
        jButton_removerCliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_removerClienteActionPerformed(evt);
            }
        });

        jLabel19.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel19.setText("Ordenar: ");

        jComboBox_ordenar_clientes.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        jComboBox_ordenar_clientes.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jComboBox_ordenar_clientesItemStateChanged(evt);
            }
        });

        jButton_busca_cliente_cpf.setIcon(new javax.swing.ImageIcon(getClass().getResource("/views/Icons/baseline_search_black_18dp.png"))); // NOI18N
        jButton_busca_cliente_cpf.setText("Buscar Por CPF");
        jButton_busca_cliente_cpf.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_busca_cliente_cpfActionPerformed(evt);
            }
        });

        jButton_busca_cliente_nome.setIcon(new javax.swing.ImageIcon(getClass().getResource("/views/Icons/baseline_search_black_18dp.png"))); // NOI18N
        jButton_busca_cliente_nome.setText("Buscar Por Nome");
        jButton_busca_cliente_nome.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_busca_cliente_nomeActionPerformed(evt);
            }
        });

        jButton_client_order_dec.setIcon(new javax.swing.ImageIcon(getClass().getResource("/views/Icons/baseline_keyboard_arrow_down_black_18dp.png"))); // NOI18N
        jButton_client_order_dec.setMargin(new java.awt.Insets(0, 14, 0, 14));
        jButton_client_order_dec.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_client_order_decActionPerformed(evt);
            }
        });

        jButton_client_order_cres.setIcon(new javax.swing.ImageIcon(getClass().getResource("/views/Icons/baseline_keyboard_arrow_up_black_18dp.png"))); // NOI18N
        jButton_client_order_cres.setMargin(new java.awt.Insets(0, 14, 0, 14));
        jButton_client_order_cres.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_client_order_cresActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jpn_clientsListLayout = new javax.swing.GroupLayout(jpn_clientsList);
        jpn_clientsList.setLayout(jpn_clientsListLayout);
        jpn_clientsListLayout.setHorizontalGroup(
            jpn_clientsListLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpn_clientsListLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jpn_clientsListLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1)
                    .addGroup(jpn_clientsListLayout.createSequentialGroup()
                        .addComponent(jLabel19)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jComboBox_ordenar_clientes, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jButton_client_order_dec)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jButton_client_order_cres)
                        .addGap(144, 144, 144)
                        .addComponent(jLabel2)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jpn_clientsListLayout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jlb_totalClientes, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(132, 132, 132)
                        .addComponent(jButton_busca_cliente_cpf)
                        .addGap(18, 18, 18)
                        .addComponent(jButton_busca_cliente_nome)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 267, Short.MAX_VALUE)
                        .addComponent(jButton_removerCliente)
                        .addGap(18, 18, 18)
                        .addComponent(jButton_modificarCliente)
                        .addGap(18, 18, 18)
                        .addComponent(jButton_inserirCliente)))
                .addContainerGap())
        );
        jpn_clientsListLayout.setVerticalGroup(
            jpn_clientsListLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jpn_clientsListLayout.createSequentialGroup()
                .addContainerGap(18, Short.MAX_VALUE)
                .addGroup(jpn_clientsListLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jpn_clientsListLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jButton_client_order_dec)
                        .addComponent(jButton_client_order_cres))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jpn_clientsListLayout.createSequentialGroup()
                        .addGroup(jpn_clientsListLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel19)
                            .addComponent(jComboBox_ordenar_clientes, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 1, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(11, 11, 11)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 537, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jpn_clientsListLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jpn_clientsListLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jButton_inserirCliente)
                        .addComponent(jButton_modificarCliente)
                        .addComponent(jButton_removerCliente))
                    .addGroup(jpn_clientsListLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jpn_clientsListLayout.createSequentialGroup()
                            .addGap(7, 7, 7)
                            .addGroup(jpn_clientsListLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jlb_totalClientes, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 17, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel3, javax.swing.GroupLayout.Alignment.TRAILING)))
                        .addGroup(jpn_clientsListLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jButton_busca_cliente_cpf)
                            .addComponent(jButton_busca_cliente_nome))))
                .addGap(13, 13, 13))
        );

        jLayeredPane2.add(jpn_clientsList);

        jLabel6.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel6.setText("Telefone:");

        jLabel7.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel7.setText("Nome:");

        jL_Cadastrar_cliente.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jL_Cadastrar_cliente.setText("CADASTRAR NOVO CLIENTE");

        jLabel5.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel5.setText("CPF:");

        jLabel8.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel8.setText("Endereço:");

        jLabel9.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel9.setText("Instagram:");

        jLabel10.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel10.setText("Facebook:");

        jLabel11.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel11.setText("Cartão Crédito:");

        jb_finalizarCadastro.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jb_finalizarCadastro.setIcon(new javax.swing.ImageIcon(getClass().getResource("/views/Icons/baseline_save_black_18dp.png"))); // NOI18N
        jb_finalizarCadastro.setText("FINALIZAR O CADASTRO");
        jb_finalizarCadastro.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jb_finalizarCadastroActionPerformed(evt);
            }
        });

        jb_backClientPage.setIcon(new javax.swing.ImageIcon(getClass().getResource("/views/Icons/baseline_arrow_back_black_18dp.png"))); // NOI18N
        jb_backClientPage.setText("Voltar");
        jb_backClientPage.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jb_backClientPageActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jpn_clientRegistrationLayout = new javax.swing.GroupLayout(jpn_clientRegistration);
        jpn_clientRegistration.setLayout(jpn_clientRegistrationLayout);
        jpn_clientRegistrationLayout.setHorizontalGroup(
            jpn_clientRegistrationLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpn_clientRegistrationLayout.createSequentialGroup()
                .addGroup(jpn_clientRegistrationLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jpn_clientRegistrationLayout.createSequentialGroup()
                        .addGap(487, 487, 487)
                        .addComponent(jL_Cadastrar_cliente))
                    .addGroup(jpn_clientRegistrationLayout.createSequentialGroup()
                        .addGap(409, 409, 409)
                        .addGroup(jpn_clientRegistrationLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jpn_clientRegistrationLayout.createSequentialGroup()
                                .addGroup(jpn_clientRegistrationLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel6)
                                    .addComponent(jLabel8)
                                    .addComponent(jLabel7)
                                    .addComponent(jLabel5)
                                    .addComponent(jLabel9)
                                    .addComponent(jLabel11)
                                    .addComponent(jLabel10))
                                .addGap(18, 18, 18)
                                .addGroup(jpn_clientRegistrationLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jtf_client_Address)
                                    .addComponent(jtf_client_CreditCard)
                                    .addComponent(jtf_client_Insta)
                                    .addComponent(jtf_client_Face)
                                    .addComponent(jtf_client_Name)
                                    .addComponent(jtf_client_CPF)
                                    .addComponent(jtf_client_Phone, javax.swing.GroupLayout.DEFAULT_SIZE, 294, Short.MAX_VALUE)))
                            .addComponent(jb_finalizarCadastro, javax.swing.GroupLayout.DEFAULT_SIZE, 406, Short.MAX_VALUE)))
                    .addGroup(jpn_clientRegistrationLayout.createSequentialGroup()
                        .addGap(53, 53, 53)
                        .addComponent(jb_backClientPage, javax.swing.GroupLayout.PREFERRED_SIZE, 129, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(535, Short.MAX_VALUE))
        );
        jpn_clientRegistrationLayout.setVerticalGroup(
            jpn_clientRegistrationLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpn_clientRegistrationLayout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addComponent(jL_Cadastrar_cliente)
                .addGap(127, 127, 127)
                .addGroup(jpn_clientRegistrationLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel5)
                    .addComponent(jtf_client_CPF, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jpn_clientRegistrationLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel7)
                    .addComponent(jtf_client_Name, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jpn_clientRegistrationLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel6)
                    .addComponent(jtf_client_Phone, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jpn_clientRegistrationLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel8)
                    .addComponent(jtf_client_Address, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(15, 15, 15)
                .addGroup(jpn_clientRegistrationLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jpn_clientRegistrationLayout.createSequentialGroup()
                        .addComponent(jLabel11)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel9))
                    .addGroup(jpn_clientRegistrationLayout.createSequentialGroup()
                        .addComponent(jtf_client_CreditCard, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jtf_client_Insta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addGroup(jpn_clientRegistrationLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel10)
                    .addComponent(jtf_client_Face, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(54, 54, 54)
                .addComponent(jb_finalizarCadastro)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 67, Short.MAX_VALUE)
                .addComponent(jb_backClientPage)
                .addGap(45, 45, 45))
        );

        jLayeredPane2.add(jpn_clientRegistration);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1350, Short.MAX_VALUE)
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 640, Short.MAX_VALUE)
        );

        jLayeredPane2.add(jPanel3);

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1350, Short.MAX_VALUE)
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 640, Short.MAX_VALUE)
        );

        jLayeredPane2.add(jPanel5);

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1350, Short.MAX_VALUE)
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 640, Short.MAX_VALUE)
        );

        jLayeredPane2.add(jPanel6);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1350, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 640, Short.MAX_VALUE)
        );

        jLayeredPane2.add(jPanel1);

        javax.swing.GroupLayout jPanel_ClientListLayout = new javax.swing.GroupLayout(jPanel_ClientList);
        jPanel_ClientList.setLayout(jPanel_ClientListLayout);
        jPanel_ClientListLayout.setHorizontalGroup(
            jPanel_ClientListLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLayeredPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 1350, Short.MAX_VALUE)
        );
        jPanel_ClientListLayout.setVerticalGroup(
            jPanel_ClientListLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLayeredPane2)
        );

        jTabbedPane1.addTab("Mostrar Clientes", jPanel_ClientList);

        jLayeredPane3.setLayout(new javax.swing.OverlayLayout(jLayeredPane3));

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel4.setText("QUEIJOS CADASTRADOS");

        jTableQueijo.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane2.setViewportView(jTableQueijo);

        jlb_totalQueijos.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        jLabel12.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel12.setText("Total de Queijos Cadastrados: ");

        jButton_inserirQueijo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/views/Icons/baseline_add_black_18dp.png"))); // NOI18N
        jButton_inserirQueijo.setText("Inserir Queijo");
        jButton_inserirQueijo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_inserirQueijoActionPerformed(evt);
            }
        });

        jButton_modificarQueijo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/views/Icons/baseline_edit_black_18dp.png"))); // NOI18N
        jButton_modificarQueijo.setText("Modificar Queijo");
        jButton_modificarQueijo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_modificarQueijoActionPerformed(evt);
            }
        });

        jButton_removerQueijo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/views/Icons/baseline_delete_black_18dp.png"))); // NOI18N
        jButton_removerQueijo.setText("Remover Queijo");
        jButton_removerQueijo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_removerQueijoActionPerformed(evt);
            }
        });

        jLabel18.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel18.setText("Ordenar:");

        jComboBox_ordenar_queijos.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        jComboBox_ordenar_queijos.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jComboBox_ordenar_queijosItemStateChanged(evt);
            }
        });

        jButton_busca_queijo_ID.setIcon(new javax.swing.ImageIcon(getClass().getResource("/views/Icons/baseline_search_black_18dp.png"))); // NOI18N
        jButton_busca_queijo_ID.setText("Busca por ID");
        jButton_busca_queijo_ID.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_busca_queijo_IDActionPerformed(evt);
            }
        });

        jButton_queijo_menor_temp.setIcon(new javax.swing.ImageIcon(getClass().getResource("/views/Icons/baseline_search_black_18dp.png"))); // NOI18N
        jButton_queijo_menor_temp.setText("Queijo Menor Temperatura");
        jButton_queijo_menor_temp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_queijo_menor_tempActionPerformed(evt);
            }
        });

        jButton_queijo_mais_caro.setIcon(new javax.swing.ImageIcon(getClass().getResource("/views/Icons/baseline_search_black_18dp.png"))); // NOI18N
        jButton_queijo_mais_caro.setText("Queijo Mais Caro");
        jButton_queijo_mais_caro.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_queijo_mais_caroActionPerformed(evt);
            }
        });

        jButton_queijo_order_dec1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/views/Icons/baseline_keyboard_arrow_down_black_18dp.png"))); // NOI18N
        jButton_queijo_order_dec1.setMargin(new java.awt.Insets(0, 14, 0, 14));
        jButton_queijo_order_dec1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_queijo_order_dec1ActionPerformed(evt);
            }
        });

        jButton_queijo_order_cres1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/views/Icons/baseline_keyboard_arrow_up_black_18dp.png"))); // NOI18N
        jButton_queijo_order_cres1.setMargin(new java.awt.Insets(0, 14, 0, 14));
        jButton_queijo_order_cres1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_queijo_order_cres1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jpn_queijoListLayout = new javax.swing.GroupLayout(jpn_queijoList);
        jpn_queijoList.setLayout(jpn_queijoListLayout);
        jpn_queijoListLayout.setHorizontalGroup(
            jpn_queijoListLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpn_queijoListLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jpn_queijoListLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2)
                    .addGroup(jpn_queijoListLayout.createSequentialGroup()
                        .addComponent(jLabel18)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jComboBox_ordenar_queijos, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jButton_queijo_order_dec1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jButton_queijo_order_cres1)
                        .addGap(151, 151, 151)
                        .addComponent(jLabel4)
                        .addGap(0, 603, Short.MAX_VALUE))
                    .addGroup(jpn_queijoListLayout.createSequentialGroup()
                        .addComponent(jLabel12)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jlb_totalQueijos, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(48, 48, 48)
                        .addComponent(jButton_busca_queijo_ID)
                        .addGap(18, 18, 18)
                        .addComponent(jButton_queijo_menor_temp)
                        .addGap(18, 18, 18)
                        .addComponent(jButton_queijo_mais_caro)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton_removerQueijo)
                        .addGap(18, 18, 18)
                        .addComponent(jButton_modificarQueijo)
                        .addGap(18, 18, 18)
                        .addComponent(jButton_inserirQueijo)))
                .addContainerGap())
        );
        jpn_queijoListLayout.setVerticalGroup(
            jpn_queijoListLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpn_queijoListLayout.createSequentialGroup()
                .addGroup(jpn_queijoListLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jpn_queijoListLayout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jpn_queijoListLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jpn_queijoListLayout.createSequentialGroup()
                                .addComponent(jLabel4)
                                .addGap(1, 1, 1))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jpn_queijoListLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jButton_queijo_order_dec1)
                                .addComponent(jButton_queijo_order_cres1)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED))
                    .addGroup(jpn_queijoListLayout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jpn_queijoListLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel18)
                            .addComponent(jComboBox_ordenar_queijos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 532, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 20, Short.MAX_VALUE)
                .addGroup(jpn_queijoListLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jpn_queijoListLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jlb_totalQueijos, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 17, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel12))
                    .addGroup(jpn_queijoListLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jButton_modificarQueijo)
                        .addComponent(jButton_inserirQueijo)
                        .addComponent(jButton_removerQueijo)
                        .addComponent(jButton_busca_queijo_ID)
                        .addComponent(jButton_queijo_menor_temp)
                        .addComponent(jButton_queijo_mais_caro)))
                .addContainerGap(16, Short.MAX_VALUE))
        );

        jLayeredPane3.add(jpn_queijoList);

        jL_Cadastrar_queijo.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jL_Cadastrar_queijo.setText("CADASTRAR NOVO QUEIJO");

        jLabel13.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel13.setText("ID:");

        jLabel14.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel14.setText("Peso (Kg):");

        jLabel15.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel15.setText("Valor Por Kg (R$):");

        jLabel16.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel16.setText("Tipo do Queijo:");

        jLabel17.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel17.setText("Temperatura Ideal (ºC):");

        jb_finalizarCadastroQueijo.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jb_finalizarCadastroQueijo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/views/Icons/baseline_save_black_18dp.png"))); // NOI18N
        jb_finalizarCadastroQueijo.setText("FINALIZAR O CADASTRO");
        jb_finalizarCadastroQueijo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jb_finalizarCadastroQueijoActionPerformed(evt);
            }
        });

        jb_backQueijoPage.setIcon(new javax.swing.ImageIcon(getClass().getResource("/views/Icons/baseline_arrow_back_black_18dp.png"))); // NOI18N
        jb_backQueijoPage.setText("Voltar");
        jb_backQueijoPage.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jb_backQueijoPageActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jpn_queijoRegistrationLayout = new javax.swing.GroupLayout(jpn_queijoRegistration);
        jpn_queijoRegistration.setLayout(jpn_queijoRegistrationLayout);
        jpn_queijoRegistrationLayout.setHorizontalGroup(
            jpn_queijoRegistrationLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpn_queijoRegistrationLayout.createSequentialGroup()
                .addGroup(jpn_queijoRegistrationLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jpn_queijoRegistrationLayout.createSequentialGroup()
                        .addGap(491, 491, 491)
                        .addComponent(jL_Cadastrar_queijo))
                    .addGroup(jpn_queijoRegistrationLayout.createSequentialGroup()
                        .addGap(50, 50, 50)
                        .addGroup(jpn_queijoRegistrationLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jpn_queijoRegistrationLayout.createSequentialGroup()
                                .addComponent(jb_backQueijoPage, javax.swing.GroupLayout.PREFERRED_SIZE, 129, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(633, 633, 633))
                            .addGroup(jpn_queijoRegistrationLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jb_finalizarCadastroQueijo, javax.swing.GroupLayout.PREFERRED_SIZE, 444, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGroup(jpn_queijoRegistrationLayout.createSequentialGroup()
                                    .addGroup(jpn_queijoRegistrationLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jLabel15)
                                        .addComponent(jLabel16)
                                        .addComponent(jLabel14)
                                        .addComponent(jLabel13)
                                        .addComponent(jLabel17))
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addGroup(jpn_queijoRegistrationLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(jtf_queijo_tipo)
                                        .addComponent(jtf_queijo_Temperatura)
                                        .addComponent(jtf_queijo_peso)
                                        .addComponent(jtf_queijo_id)
                                        .addComponent(jtf_queijo_valorKg, javax.swing.GroupLayout.PREFERRED_SIZE, 294, javax.swing.GroupLayout.PREFERRED_SIZE)))))))
                .addContainerGap(538, Short.MAX_VALUE))
        );
        jpn_queijoRegistrationLayout.setVerticalGroup(
            jpn_queijoRegistrationLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpn_queijoRegistrationLayout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addComponent(jL_Cadastrar_queijo)
                .addGap(127, 127, 127)
                .addGroup(jpn_queijoRegistrationLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel13)
                    .addComponent(jtf_queijo_id, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jpn_queijoRegistrationLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel14)
                    .addComponent(jtf_queijo_peso, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jpn_queijoRegistrationLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel15)
                    .addComponent(jtf_queijo_valorKg, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jpn_queijoRegistrationLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel16)
                    .addComponent(jtf_queijo_tipo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jpn_queijoRegistrationLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel17)
                    .addComponent(jtf_queijo_Temperatura, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(28, 28, 28)
                .addComponent(jb_finalizarCadastroQueijo)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 166, Short.MAX_VALUE)
                .addComponent(jb_backQueijoPage)
                .addGap(46, 46, 46))
        );

        jLayeredPane3.add(jpn_queijoRegistration);

        javax.swing.GroupLayout jPanel13Layout = new javax.swing.GroupLayout(jPanel13);
        jPanel13.setLayout(jPanel13Layout);
        jPanel13Layout.setHorizontalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1350, Short.MAX_VALUE)
        );
        jPanel13Layout.setVerticalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 640, Short.MAX_VALUE)
        );

        jLayeredPane3.add(jPanel13);

        javax.swing.GroupLayout jPanel12Layout = new javax.swing.GroupLayout(jPanel12);
        jPanel12.setLayout(jPanel12Layout);
        jPanel12Layout.setHorizontalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1350, Short.MAX_VALUE)
        );
        jPanel12Layout.setVerticalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 640, Short.MAX_VALUE)
        );

        jLayeredPane3.add(jPanel12);

        javax.swing.GroupLayout jPanel11Layout = new javax.swing.GroupLayout(jPanel11);
        jPanel11.setLayout(jPanel11Layout);
        jPanel11Layout.setHorizontalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1350, Short.MAX_VALUE)
        );
        jPanel11Layout.setVerticalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 640, Short.MAX_VALUE)
        );

        jLayeredPane3.add(jPanel11);

        javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1350, Short.MAX_VALUE)
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 640, Short.MAX_VALUE)
        );

        jLayeredPane3.add(jPanel10);

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1350, Short.MAX_VALUE)
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 640, Short.MAX_VALUE)
        );

        jLayeredPane3.add(jPanel9);

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1350, Short.MAX_VALUE)
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 640, Short.MAX_VALUE)
        );

        jLayeredPane3.add(jPanel7);

        javax.swing.GroupLayout jPanel_QueijoListLayout = new javax.swing.GroupLayout(jPanel_QueijoList);
        jPanel_QueijoList.setLayout(jPanel_QueijoListLayout);
        jPanel_QueijoListLayout.setHorizontalGroup(
            jPanel_QueijoListLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLayeredPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 1350, Short.MAX_VALUE)
        );
        jPanel_QueijoListLayout.setVerticalGroup(
            jPanel_QueijoListLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLayeredPane3)
        );

        jTabbedPane1.addTab("Mostrar Queijos", jPanel_QueijoList);

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel1.setText("Fábrica de Queijos Uai Sô");
        jLabel1.setAlignmentY(0.0F);

        jMenu1.setText("Exportar Dados");

        jMenuItem_ExportarTXT.setText("Exportar para Arquivo TXT");
        jMenu1.add(jMenuItem_ExportarTXT);

        jMenuItem_ExportarPDF.setText("Exportar para Arquivo PDF");
        jMenu1.add(jMenuItem_ExportarPDF);

        jMenuItem_ExportarXLS.setText("Exportar para Arquivo XLS");
        jMenu1.add(jMenuItem_ExportarXLS);

        jMenuBar1.add(jMenu1);

        jMenu2.setText("Ajuda");

        jMenuItem_Sobre.setText("Sobre o Programa");
        jMenu2.add(jMenuItem_Sobre);

        jMenuBar1.add(jMenu2);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(474, 474, 474)
                .addComponent(jLabel1)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jTabbedPane1)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addGap(5, 5, 5)
                .addComponent(jTabbedPane1)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents
// Client 1st part Functions Begin ----------------------------------------------------------------------------------------------
    private void jButton_modificarClienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_modificarClienteActionPerformed
        // TODO add your handling code here:
        //PERGUNTAR QUAL OBJETO A MODIFICAR
        player.alertSong.play();
        String cpfUpdate = JOptionPane.showInputDialog("Por favor digite o CPF do Cliente para modificar: ");
        //PROCURAR QUAL OBJETO A SER MODIFICADO
        if (cpfUpdate != null) {
            ArrayList<Client> clientsList = ClientDAO.read(false, "", false);
            Client clientModify = new Client();
            boolean achou = false;
            for (int i = 0; i < clientsList.size() && achou == false; i++) {
                clientModify = clientsList.get(i);
                if (cpfUpdate.replace(".", "").replace("-", "").equals(clientModify.getCPF().replace(".", "").replace("-", ""))) {
                    achou = true;
                }
            }
            //PASSAR OS DADOS DO OBJETO PARA TELA DE UPDADE OU MOSTRAR ERRO DE NÃO ENCONTRADO
            if (achou == true) {
                jtf_client_CPF.setText(clientModify.getCPF());
                jtf_client_CPF.setEditable(false);
                jtf_client_Address.setText(clientModify.getAddress());
                jtf_client_CreditCard.setText(clientModify.getCreditCard());
                jtf_client_Face.setText(clientModify.getFacebookURL());
                jtf_client_Insta.setText(clientModify.getInstagramURL());
                jtf_client_Name.setText(clientModify.getClientName());
                jtf_client_Phone.setText(clientModify.getPhone());

                //TROCAR AS TELAS
                jL_Cadastrar_cliente.setText("ALTERAR DADOS DO CLIENTE");
                jb_finalizarCadastro.setText("Salvar Alteração de Dados");
                isClientUpdate = true;
                jpn_clientsList.setVisible(false);
                jpn_clientRegistration.setVisible(true);
            } else {
                player.erroSong.play();
                JOptionPane.showMessageDialog(null, "CPF não Encontrado", "Erro ao Realizar Operação", JOptionPane.ERROR_MESSAGE);
            }
        }
    }//GEN-LAST:event_jButton_modificarClienteActionPerformed

    private void jButton_removerClienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_removerClienteActionPerformed
        // TODO add your handling code here:
        //PERGUNTAR E ACHAR QUAL O OBJETO A SER EXCLUIDO
        player.alertSong.play();
        String cpfDelete = JOptionPane.showInputDialog("Por favor digite o CPF do Cliente para remover: ");
        if (cpfDelete != null) {
            ArrayList<Client> clientsList = ClientDAO.read(false, "", false);
            Client clientDelete = new Client();
            boolean achou = false;
            for (int i = 0; i < clientsList.size() && achou == false; i++) {
                clientDelete = clientsList.get(i);
                if (cpfDelete.replace(".", "").replace("-", "").equals(clientDelete.getCPF().replace(".", "").replace("-", ""))) {
                    achou = true;
                }
            }
            //FAZER OPERAÇÃO E PEGAR E MOSTRAR O RESULTADO OU ERROS
            if (achou == true) {
                player.alertSong.play();
                int delete = JOptionPane.showConfirmDialog(null, "Tem certeza que deseja excluir "
                        + "o Cliente:\nNome: " + clientDelete.getClientName() + ", CPF: " + clientDelete.getCPF(),
                        "Confirmar Exclusão", JOptionPane.YES_NO_OPTION);

                if (delete == 0) {
                    String erro = ClientDAO.delete(clientDelete);
                    if (erro == null) {
                        player.sucessSong.play();
                    } else {
                        player.erroSong.play();
                    }
                    JOptionPane.showMessageDialog(null, (erro == null)
                            ? "Cliente Removido com Sucesso!"
                            : "Erro Encontado: \n" + erro, "Resultado da operação",
                            (erro == null) ? JOptionPane.INFORMATION_MESSAGE : JOptionPane.ERROR_MESSAGE);
                } else {
                    player.sucessSong.play();
                    JOptionPane.showMessageDialog(null, "Operação Cancelada!");
                }
            } else {
                player.erroSong.play();
                JOptionPane.showMessageDialog(null, "CPF não Encontrado", "Erro ao Realizar Operação", JOptionPane.ERROR_MESSAGE);
            }
            //ATUALIZAR A TABELA
            clientTableBuilder(jTableClient, ClientDAO.read((clientOrder.isEmpty() ? false : true), clientOrder, (clientOrder.isEmpty() ? false : clientOrderDecreasing)));
        }
    }//GEN-LAST:event_jButton_removerClienteActionPerformed

    private void jb_finalizarCadastroActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jb_finalizarCadastroActionPerformed
        // TODO add your handling code here:
        //VERIFICAR VALIDADE DOS CAMPOS
        if (NewClientVerification()) {
            //RECUPERAR INFORMAÇÕES E ADICIONAR AO OBJETO
            Client newClient = new Client();
            newClient.setCPF(jtf_client_CPF.getText());
            newClient.setClientName(jtf_client_Name.getText());
            newClient.setAddress(jtf_client_Address.getText());
            newClient.setCreditCard(jtf_client_CreditCard.getText());
            newClient.setPhone(jtf_client_Phone.getText());
            newClient.setFacebookURL(jtf_client_Face.getText());
            newClient.setInstagramURL(jtf_client_Insta.getText());
            //ADICIONAR OU DAR UPDATE E PEGAR SE TEVE ERRO OU NÃO
            String erro;
            if (isClientUpdate == true) {
                erro = ClientDAO.update(newClient);
            } else {
                erro = ClientDAO.create(newClient);
            }
            //MOSTRAR RESULTADO DA OPERAÇÃO
            if (erro == null) {
                player.sucessSong.play();
            } else {
                player.erroSong.play();
            }
            JOptionPane.showMessageDialog(null, (erro == null)
                    ? "Dados do Cliente salvos com sucesso!"
                    : "Erro Encontado: \n" + erro, "Resultado da operação",
                    (erro == null) ? JOptionPane.INFORMATION_MESSAGE : JOptionPane.ERROR_MESSAGE);
            if (erro == null) {
                //ATUALIZAR TABELA E TROCAR AS TELAS
                clientTableBuilder(jTableClient, ClientDAO.read((clientOrder.isEmpty() ? false : true), clientOrder, (clientOrder.isEmpty() ? false : clientOrderDecreasing)));
                clientComboBoxBuilder();
                jpn_clientRegistration.setVisible(false);
                jpn_clientsList.setVisible(true);
                if (fromPedidoToClientRegistration == true) {
                    jTabbedPane1.setSelectedIndex(1);
                }
            }
        }
    }//GEN-LAST:event_jb_finalizarCadastroActionPerformed

    private void jButton_inserirClienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_inserirClienteActionPerformed
        // TODO add your handling code here:
        //PREPARAR OS CAMPOS DA NOVA TELA
        jtf_client_CPF.setText("");
        jtf_client_CPF.setEditable(true);
        jtf_client_Address.setText("");
        jtf_client_CreditCard.setText("");
        jtf_client_Face.setText("");
        jtf_client_Insta.setText("");
        jtf_client_Name.setText("");
        jtf_client_Phone.setText("");

        //TROCAR TELAS
        jL_Cadastrar_cliente.setText("CADASTRAR UM NOVO CLIENTE");
        jb_finalizarCadastro.setText("Salvar e Finalizar Cadastro");
        isClientUpdate = false;
        fromPedidoToClientRegistration = false;
        jpn_clientsList.setVisible(false);
        jpn_clientRegistration.setVisible(true);
    }//GEN-LAST:event_jButton_inserirClienteActionPerformed

    private void jb_backClientPageActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jb_backClientPageActionPerformed
        // TODO add your handling code here:
        //TROCAR TELAS
        if (fromPedidoToClientRegistration == false) {
            clientTableBuilder(jTableClient, ClientDAO.read((clientOrder.isEmpty() ? false : true), clientOrder, (clientOrder.isEmpty() ? false : clientOrderDecreasing)));
            jpn_clientRegistration.setVisible(false);
            jpn_clientsList.setVisible(true);
        } else {
            clientTableBuilder(jTableClient, ClientDAO.read((clientOrder.isEmpty() ? false : true), clientOrder, (clientOrder.isEmpty() ? false : clientOrderDecreasing)));
            jpn_clientRegistration.setVisible(false);
            jpn_clientsList.setVisible(true);
            jTabbedPane1.setSelectedIndex(1);
        }

    }//GEN-LAST:event_jb_backClientPageActionPerformed
// Client 1st part Functions End ----------------------------------------------------------------------------------------------

// Queijo Functions Begin ----------------------------------------------------------------------------------------------    
    private void jButton_inserirQueijoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_inserirQueijoActionPerformed
        jtf_queijo_id.setEditable(false);
        //INICIAR TEXTFIELDS
        jtf_queijo_id.setText("Gerado pelo Sistema");
        jtf_queijo_Temperatura.setText("");
        jtf_queijo_peso.setText("");
        jtf_queijo_tipo.setText("");
        jtf_queijo_valorKg.setText("");
        //TROCAR A TELA
        isQueijoUpdate = false;
        fromPedidoToQueijoRegistration = false;
        jL_Cadastrar_queijo.setText("CADASTRAR NOVO QUEIJO");
        jb_finalizarCadastroQueijo.setText("Finalizar e Salvar Dados");
        jpn_queijoRegistration.setVisible(true);
        jpn_queijoList.setVisible(false);
    }//GEN-LAST:event_jButton_inserirQueijoActionPerformed

    private void jb_finalizarCadastroQueijoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jb_finalizarCadastroQueijoActionPerformed
        // TODO add your handling code here:
        //VERIFICAR VALIDADE DOS DADOS DIGITADOS
        if (NewQueijoVerification()) {
            boolean hasError = false;
            //ADICIONAR OS DADOS A UM OBJETO
            Queijo newQueijo = new Queijo();
            try {
                if (isQueijoUpdate == true) {
                    newQueijo.setQueijoID(Integer.parseInt(jtf_queijo_id.getText()));
                }
                newQueijo.setWeight(Double.parseDouble(jtf_queijo_peso.getText().replace(',', '.')));
                newQueijo.setPricePerKg(Double.parseDouble(jtf_queijo_valorKg.getText().replace(',', '.')));
                newQueijo.setRecommendedTemperature(Double.parseDouble(jtf_queijo_Temperatura.getText().replace(',', '.')));
            } catch (NumberFormatException e) {
                System.out.println("\n Erro Encontrado: " + e.toString());
                player.erroSong.play();
                JOptionPane.showMessageDialog(null, "Erro Encontrado: \n" + e.toString(),
                        "Erro ao Realizar Operação", JOptionPane.ERROR_MESSAGE);
                hasError = true;
            }
            newQueijo.setQueijoType(jtf_queijo_tipo.getText());
            //ADICIONAR OU DAR UPDATE E PEGAR SE TEVE ERRO OU NÃO
            if (hasError == false) {
                String erro;
                if (isQueijoUpdate == true) {
                    erro = QueijoDAO.update(newQueijo);
                } else {
                    erro = QueijoDAO.create(newQueijo);
                }
                //MOSTRAR RESULTADO DA OPERAÇÃO
                if (erro == null) {
                    player.sucessSong.play();
                } else {
                    player.erroSong.play();
                }
                JOptionPane.showMessageDialog(null, (erro == null)
                        ? "Dados do Queijo salvos com sucesso!"
                        : "Erro Encontado: \n" + erro, "Resultado da operação",
                        (erro == null) ? JOptionPane.INFORMATION_MESSAGE
                                : JOptionPane.ERROR_MESSAGE);
                if (erro == null) {

                    //ATUALIZAR TABELA E TROCAR AS TELAS
                    queijoListBuilder();
                    queijoTableBuilder(jTableQueijo, QueijoDAO.read((queijoOrder.isEmpty() ? false : true), queijoOrder, (queijoOrder.isEmpty() ? false : queijoOrderDecreasing)));
                    //TROCAR TELAS
                    jpn_queijoList.setVisible(true);
                    jpn_queijoRegistration.setVisible(false);
                    if (fromPedidoToQueijoRegistration == true) {
                        jTabbedPane1.setSelectedIndex(1);
                    }
                }
            }
        }
    }//GEN-LAST:event_jb_finalizarCadastroQueijoActionPerformed

    private void jb_backQueijoPageActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jb_backQueijoPageActionPerformed
        queijoTableBuilder(jTableQueijo, QueijoDAO.read((queijoOrder.isEmpty() ? false : true), queijoOrder, (queijoOrder.isEmpty() ? false : queijoOrderDecreasing)));
        //TROCAR TELAS
        jpn_queijoList.setVisible(true);
        jpn_queijoRegistration.setVisible(false);
        if (fromPedidoToQueijoRegistration == true) {
            jTabbedPane1.setSelectedIndex(1);
        }
    }//GEN-LAST:event_jb_backQueijoPageActionPerformed

    private void jButton_modificarQueijoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_modificarQueijoActionPerformed
        // TODO add your handling code here:
        //PERGUNTAR QUAL OBJETO A MODIFICAR
        player.alertSong.play();
        String idUpdate = JOptionPane.showInputDialog("Por favor digite o ID do Queijo para modificar: ");
        //PROCURAR QUAL OBJETO A SER MODIFICADO
        if (idUpdate != null) {
            ArrayList<Queijo> queijoList = QueijoDAO.read(false, "", false);
            Queijo queijoModify = new Queijo();
            boolean achou = false;
            for (int i = 0; i < queijoList.size() && achou == false; i++) {
                queijoModify = queijoList.get(i);
                if (idUpdate.equals("" + queijoModify.getQueijoID())) {
                    achou = true;
                }
            }
            //PASSAR OS DADOS DO OBJETO PARA TELA DE UPDADE OU MOSTRAR ERRO DE NÃO ENCONTRADO
            if (achou == true) {
                jtf_queijo_id.setText("" + queijoModify.getQueijoID());
                jtf_queijo_id.setEditable(false);
                jtf_queijo_peso.setText("" + queijoModify.getWeight());
                jtf_queijo_tipo.setText(queijoModify.getQueijoType());
                jtf_queijo_valorKg.setText("" + queijoModify.getPricePerKg());
                jtf_queijo_Temperatura.setText("" + queijoModify.getRecommendedTemperature());

                //TROCAR AS TELAS
                jL_Cadastrar_queijo.setText("ALTERAR DADOS DO QUEIJO");
                jb_finalizarCadastroQueijo.setText("Salvar Alteração de Dados");
                isQueijoUpdate = true;
                jpn_queijoList.setVisible(false);
                jpn_queijoRegistration.setVisible(true);
            } else {
                player.erroSong.play();
                JOptionPane.showMessageDialog(null, "ID do Queijo não Encontrado", "Erro ao Realizar Operação", JOptionPane.ERROR_MESSAGE);
            }
        }
    }//GEN-LAST:event_jButton_modificarQueijoActionPerformed

    private void jButton_removerQueijoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_removerQueijoActionPerformed
        // TODO add your handling code here:
        //PERGUNTAR E ACHAR QUAL O OBJETO A SER EXCLUIDO
        player.alertSong.play();
        String idUpdate = JOptionPane.showInputDialog("Por favor digite o ID do Queijo para remover: ");
        if (idUpdate != null) {
            ArrayList<Queijo> queijoList = QueijoDAO.read(false, "", false);
            Queijo queijoModify = new Queijo();
            boolean achou = false;
            for (int i = 0; i < queijoList.size() && achou == false; i++) {
                queijoModify = queijoList.get(i);
                if (idUpdate.equals("" + queijoModify.getQueijoID())) {
                    achou = true;
                }
            }
            //FAZER OPERAÇÃO E PEGAR E MOSTRAR O RESULTADO OU ERROS
            if (achou == true) {
                player.alertSong.play();
                int delete = JOptionPane.showConfirmDialog(null, "Tem certeza que deseja excluir "
                        + "o Queijo:\nID: " + queijoModify.getQueijoID() + ", Tipo: " + queijoModify.getQueijoType(),
                        "Confirmar Exclusão", JOptionPane.YES_NO_OPTION);

                if (delete == 0) {
                    String erro = QueijoDAO.delete(queijoModify);
                    if (erro == null) {
                        player.sucessSong.play();
                    } else {
                        player.erroSong.play();
                    }
                    JOptionPane.showMessageDialog(null, (erro == null)
                            ? "Queijo Removido com Sucesso!"
                            : "Erro Encontado: \n" + erro, "Resultado da operação",
                            (erro == null) ? JOptionPane.INFORMATION_MESSAGE : JOptionPane.ERROR_MESSAGE);
                } else {
                    player.sucessSong.play();
                    JOptionPane.showMessageDialog(null, "Operação Cancelada!");
                }
            } else {
                player.erroSong.play();
                JOptionPane.showMessageDialog(null, "ID do Queijo não Encontrado", "Erro ao Realizar Operação", JOptionPane.ERROR_MESSAGE);
            }
            //ATUALIZAR A TABELA
            queijoTableBuilder(jTableQueijo, QueijoDAO.read((queijoOrder.isEmpty() ? false : true), queijoOrder, (queijoOrder.isEmpty() ? false : queijoOrderDecreasing)));
        }
    }//GEN-LAST:event_jButton_removerQueijoActionPerformed

    private void jComboBox_ordenar_queijosItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jComboBox_ordenar_queijosItemStateChanged
        // TODO add your handling code here:
        if (evt.getStateChange() == ItemEvent.SELECTED) {
            String newChoice = jComboBox_ordenar_queijos.getSelectedItem().toString();
            switch (newChoice) {
                case "-------": {
                    queijoOrder = "";
                    break;
                }
                case "Id": {
                    queijoOrder = "queijoid";
                    break;
                }
                case "Peso": {
                    queijoOrder = "weight";
                    break;
                }
                case "Valor por Kg": {
                    queijoOrder = "pricePerKg";
                    break;
                }
                case "Temeperatura Ideal": {
                    queijoOrder = "recommendedTemperature";
                    break;
                }
                case "Tipo": {
                    queijoOrder = "queijoType";
                    break;
                }
            }
            queijoOrderDecreasing = false;
            queijoTableBuilder(jTableQueijo, QueijoDAO.read((queijoOrder.isEmpty() ? false : true), queijoOrder, (queijoOrder.isEmpty() ? false : queijoOrderDecreasing)));
        }
    }//GEN-LAST:event_jComboBox_ordenar_queijosItemStateChanged

    private void jButton_busca_queijo_IDActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_busca_queijo_IDActionPerformed
        // TODO add your handling code here:
        //PERGUNTAR QUAL ID DO OBJETO A BUSCAR
        player.alertSong.play();
        String idBusca = JOptionPane.showInputDialog("Por favor digite o ID do Queijo que deseja buscar: ");
        boolean achou = false;
        Queijo queijoSeek = new Queijo();
        //PROCURAR QUAL OBJETO A SER MODIFICADO
        if (idBusca != null) {
            ArrayList<Queijo> queijoList = QueijoDAO.read(false, "", false);
            for (int i = 0; i < queijoList.size() && achou == false; i++) {
                queijoSeek = queijoList.get(i);
                if (idBusca.equals("" + queijoSeek.getQueijoID())) {
                    achou = true;
                }
            }
        }

        if (achou == true) {
            player.sucessSong.play();
            JOptionPane.showMessageDialog(null, "Queijo Encontrado:\n ID: " + queijoSeek.getQueijoID()
                    + ", \nTipo: " + queijoSeek.getQueijoType()
                    + ", \nPeso: " + queijoSeek.getWeight()
                    + " Kg, \nPreço Por Kg: R$ " + queijoSeek.getPricePerKg()
                    + ", \nTemperatura Recomendada: " + queijoSeek.getRecommendedTemperature() + " ºC");
        } else {
            player.erroSong.play();
            JOptionPane.showMessageDialog(null, "ID do Queijo não Encontrado", "Erro ao Realizar Operação", JOptionPane.ERROR_MESSAGE);
        }

    }//GEN-LAST:event_jButton_busca_queijo_IDActionPerformed

    private void jButton_queijo_menor_tempActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_queijo_menor_tempActionPerformed
        // TODO add your handling code here:
        ArrayList<Queijo> queijoList = QueijoDAO.read(false, "", false);
        if (!queijoList.isEmpty()) {

            Queijo lowerTemperatureQueijo = null;
            for (int i = 0; i < queijoList.size(); i++) {
                Queijo queijoAux = queijoList.get(i);
                if (i == 0) {
                    lowerTemperatureQueijo = queijoAux;
                } else if (queijoAux.getRecommendedTemperature() < lowerTemperatureQueijo.getRecommendedTemperature()) {
                    lowerTemperatureQueijo = queijoAux;
                }
            }
            player.sucessSong.play();
            JOptionPane.showMessageDialog(null, "Queijo de Menor Temperatura Encontrado:\n ID: " + lowerTemperatureQueijo.getQueijoID()
                    + ", \nTipo: " + lowerTemperatureQueijo.getQueijoType()
                    + ", \nPeso: " + lowerTemperatureQueijo.getWeight()
                    + " Kg, \nPreço Por Kg: R$ " + lowerTemperatureQueijo.getPricePerKg()
                    + ", \nTemperatura Recomendada: " + lowerTemperatureQueijo.getRecommendedTemperature() + " ºC");
        } else {
            player.erroSong.play();
            JOptionPane.showMessageDialog(null, "Nenhum Queijo Encontrado", "Erro ao Realizar Operação", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_jButton_queijo_menor_tempActionPerformed

    private void jButton_queijo_mais_caroActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_queijo_mais_caroActionPerformed
        // TODO add your handling code here:
        ArrayList<Queijo> queijoList = QueijoDAO.read(false, "", false);
        if (!queijoList.isEmpty()) {
            Queijo mostExpensiveQueijo = null;
            for (int i = 0; i < queijoList.size(); i++) {
                Queijo queijoAux = queijoList.get(i);
                if (i == 0) {
                    mostExpensiveQueijo = queijoAux;
                } else if (queijoAux.getPricePerKg() > mostExpensiveQueijo.getPricePerKg()) {
                    mostExpensiveQueijo = queijoAux;
                }
            }
            player.sucessSong.play();
            JOptionPane.showMessageDialog(null, "Queijo Mais Caro Encontrado:\n ID: " + mostExpensiveQueijo.getQueijoID()
                    + ", \nTipo: " + mostExpensiveQueijo.getQueijoType()
                    + ", \nPeso: " + mostExpensiveQueijo.getWeight()
                    + " Kg, \nPreço Por Kg: R$ " + mostExpensiveQueijo.getPricePerKg()
                    + ", \nTemperatura Recomendada: " + mostExpensiveQueijo.getRecommendedTemperature() + " ºC");
        } else {
            player.erroSong.play();
            JOptionPane.showMessageDialog(null, "Nenhum Queijo Encontrado", "Erro ao Realizar Operação", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_jButton_queijo_mais_caroActionPerformed
// Queijo Functions End ----------------------------------------------------------------------------------------------

// Client 2nd part Functions Begin ----------------------------------------------------------------------------------------------    
    private void jComboBox_ordenar_clientesItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jComboBox_ordenar_clientesItemStateChanged
        // TODO add your handling code here:
        if (evt.getStateChange() == ItemEvent.SELECTED) {
            String newChoice = jComboBox_ordenar_clientes.getSelectedItem().toString();
            switch (newChoice) {
                case "-------": {
                    clientOrder = "";
                    break;
                }
                case "CPF": {
                    clientOrder = "cpf";
                    break;
                }
                case "Nome": {
                    clientOrder = "clientName";
                    break;
                }
                case "Endereço": {
                    clientOrder = "address";
                    break;
                }
                case "Cartão Crédito": {
                    clientOrder = "creditCard";
                    break;
                }
                case "Facebook": {
                    clientOrder = "facebookURL";
                    break;
                }
                case "Instagram": {
                    clientOrder = "instagramURL";
                    break;
                }
            }
            clientOrderDecreasing = false;
            clientTableBuilder(jTableClient, ClientDAO.read((clientOrder.isEmpty() ? false : true), clientOrder, (clientOrder.isEmpty() ? false : clientOrderDecreasing)));
        }
    }//GEN-LAST:event_jComboBox_ordenar_clientesItemStateChanged

    private void jButton_busca_cliente_nomeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_busca_cliente_nomeActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton_busca_cliente_nomeActionPerformed

    private void jButton_busca_cliente_cpfActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_busca_cliente_cpfActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton_busca_cliente_cpfActionPerformed

    private void jButton_client_order_decActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_client_order_decActionPerformed
        // TODO add your handling code here:
        player.effectSong.play();
        clientOrderDecreasing = true;
        if (jComboBox_ordenar_clientes.getSelectedItem() == "-------") {
            jComboBox_ordenar_clientes.setSelectedItem("CPF");
            clientOrder = "cpf";
        }
        clientTableBuilder(jTableClient, ClientDAO.read((clientOrder.isEmpty() ? false : true), clientOrder, (clientOrder.isEmpty() ? false : clientOrderDecreasing)));
    }//GEN-LAST:event_jButton_client_order_decActionPerformed

    private void jButton_client_order_cresActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_client_order_cresActionPerformed
        // TODO add your handling code here:
        player.effectSong.play();
        clientOrderDecreasing = false;
        clientTableBuilder(jTableClient, ClientDAO.read((clientOrder.isEmpty() ? false : true), clientOrder, (clientOrder.isEmpty() ? false : clientOrderDecreasing)));
    }//GEN-LAST:event_jButton_client_order_cresActionPerformed
//Client 2nd part Functions End ----------------------------------------------------------------------------------------------    

// Queijo Functions Begin ----------------------------------------------------------------------------------------------    
    private void jButton_queijo_order_dec1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_queijo_order_dec1ActionPerformed
        // TODO add your handling code here:
        player.effectSong.play();
        queijoOrderDecreasing = true;
        if (jComboBox_ordenar_queijos.getSelectedItem() == "-------") {
            jComboBox_ordenar_queijos.setSelectedItem("Id");
            queijoOrder = "queijoid";
        }
        queijoTableBuilder(jTableQueijo, QueijoDAO.read((queijoOrder.isEmpty() ? false : true), queijoOrder, (queijoOrder.isEmpty() ? false : queijoOrderDecreasing)));
    }//GEN-LAST:event_jButton_queijo_order_dec1ActionPerformed

    private void jButton_queijo_order_cres1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_queijo_order_cres1ActionPerformed
        // TODO add your handling code here:
        player.effectSong.play();
        queijoOrderDecreasing = false;
        queijoTableBuilder(jTableQueijo, QueijoDAO.read((queijoOrder.isEmpty() ? false : true), queijoOrder, (queijoOrder.isEmpty() ? false : queijoOrderDecreasing)));
    }//GEN-LAST:event_jButton_queijo_order_cres1ActionPerformed
// Queijo Functions End ----------------------------------------------------------------------------------------------

// Pedido Functions Begin ----------------------------------------------------------------------------------------------    
    private void jb_pedido_newClientActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jb_pedido_newClientActionPerformed
        //PREPARAR OS CAMPOS DA NOVA TELA
        jtf_client_CPF.setText("");
        jtf_client_CPF.setEditable(true);
        jtf_client_Address.setText("");
        jtf_client_CreditCard.setText("");
        jtf_client_Face.setText("");
        jtf_client_Insta.setText("");
        jtf_client_Name.setText("");
        jtf_client_Phone.setText("");
        fromPedidoToClientRegistration = true;
        jTabbedPane1.setSelectedIndex(3);
        jpn_clientRegistration.setVisible(true);
        jpn_clientsList.setVisible(false);
    }//GEN-LAST:event_jb_pedido_newClientActionPerformed

    private void jb_pedido_newQueijoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jb_pedido_newQueijoActionPerformed
        jtf_queijo_id.setEditable(false);
        //INICIAR TEXTFIELDS
        jtf_queijo_id.setText("Gerado pelo Sistema");
        jtf_queijo_Temperatura.setText("");
        jtf_queijo_peso.setText("");
        jtf_queijo_tipo.setText("");
        jtf_queijo_valorKg.setText("");
        //TROCAR A TELA
        isQueijoUpdate = false;
        jL_Cadastrar_queijo.setText("CADASTRAR NOVO QUEIJO");
        jb_finalizarCadastroQueijo.setText("Finalizar e Salvar Dados");
        jpn_queijoRegistration.setVisible(true);
        jpn_queijoList.setVisible(false);
        fromPedidoToQueijoRegistration = true;
        jTabbedPane1.setSelectedIndex(4);
    }//GEN-LAST:event_jb_pedido_newQueijoActionPerformed

    private void jList_pedido_queijosValueChanged(javax.swing.event.ListSelectionEvent evt) {//GEN-FIRST:event_jList_pedido_queijosValueChanged
        jtf_pedido_nomeProduto.setText(jList_pedido_queijos.getSelectedValue());
    }//GEN-LAST:event_jList_pedido_queijosValueChanged

    private void jb_pedido_finalizarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jb_pedido_finalizarActionPerformed
        if (newPedidoVerification()) {
            int sucessCount = 0;
            boolean erroProdutos = false;
            PedidoDAO.whoIsLastPedidoID();
            Pedido newPedido = new Pedido();
            newPedido.setFk_client_cpf(jtf_pedido_cpfCliente.getText());
            newPedido.setDeliveryDeadLine(Integer.parseInt(jtf_pedido_deliveryDeadLine.getText()));
            newPedido.setNote(jta_pedido_note.getText());

            //RECUPERAR DATA E HORA E GRAVAR
            StringTokenizer st = new StringTokenizer(jtf_pedido_data.getText(), "/");
            int dia = Integer.parseInt(st.nextToken());
            int mes = Integer.parseInt(st.nextToken());
            int ano = Integer.parseInt(st.nextToken()) + 2000;
            StringTokenizer st2 = new StringTokenizer(jtf_pedido_hora.getText(), ":");
            int hora = Integer.parseInt(st2.nextToken());
            int min = Integer.parseInt(st2.nextToken());
            newPedido.setPedidoDate(LocalDateTime.of(ano, mes, dia, hora, min));

            //CRIAR PEDIDO
            String erro = PedidoDAO.create(newPedido);
            if (erro != null) {
                player.erroSong.play();
                JOptionPane.showMessageDialog(null, "Erro Encontado: \n" + erro,
                        "Resultado da operação", JOptionPane.ERROR_MESSAGE);
            } else {
                int idpedido = PedidoDAO.whoIsLastPedidoID();

                for (int i = 0; i < queijoPedidoList.size(); i++) {
                    QueijoPedido pedido = queijoPedidoList.get(i);
                    pedido.setFk_id_pedido(idpedido);
                    String erro1 = QueijoPedidoDAO.create(pedido);
                    if (erro1 != null) {
                        erroProdutos = true;
                        player.erroSong.play();
                        JOptionPane.showMessageDialog(null, "Erro Encontado: \n" + erro1,
                                "Resultado da operação", JOptionPane.ERROR_MESSAGE);
                    } else {
                        sucessCount++;
                    }
                }
                //MOSTRAR RESULTADO DA OPERAÇÃO
                if (erro == null) {
                    player.sucessSong.play();
                } else {
                    player.erroSong.play();
                }
                JOptionPane.showMessageDialog(null, (erro == null)
                        ? (erroProdutos == false) ? "Pedido salvo com sucesso!"
                                : "Pedido salvo com observações: \n "
                                + sucessCount + "de " + queijoPedidoList.size() + "cadastrados"
                        : "Erro Encontado: \n" + erro, "Resultado da operação",
                        (erro == null) ? JOptionPane.INFORMATION_MESSAGE : JOptionPane.ERROR_MESSAGE);

                if (erro == null) {
                    pedidosTableBuilder(jtb_PedidoList, PedidoDAO.read());
                    queijoPedidoList.clear();
                    clearPedidoRegistrationPage();
                    jTabbedPane1.setSelectedIndex(2);
                }
            }
        }
    }//GEN-LAST:event_jb_pedido_finalizarActionPerformed

    private void jcb_pedido_clientItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jcb_pedido_clientItemStateChanged
        Client clientRegistrationPage = new Client();
        Client aux = new Client();
        if (evt.getStateChange() == ItemEvent.SELECTED) {
            String newChoice = jcb_pedido_client.getSelectedItem().toString();
            if (newChoice.equals("Selecionar...")) {
                jtf_pedido_cpfCliente.setText("Selecione o cliente acima!");
            } else {
                ArrayList<Client> clientList = new ArrayList();
                clientList = ClientDAO.read(true, "clientName", false);
                for (int i = 0; i < clientList.size(); i++) {
                    Client c = clientList.get(i);
                    if (c.getClientName().equals(newChoice)) {
                        clientRegistrationPage = c;
                    }
                }
                jtf_pedido_cpfCliente.setText(clientRegistrationPage.getCPF());
            }
        }

    }//GEN-LAST:event_jcb_pedido_clientItemStateChanged

    private void jb_pedido_addProdutoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jb_pedido_addProdutoActionPerformed
        //jtf_pedido_nomeProduto
        int quant = Integer.parseInt(jsp_quantidade_produto.getValue().toString());

        if (quant > 0) {
            //BUSCAR O ID DO PRODUTO A ADICIONAR
            int idNovoProduto;
            StringTokenizer st2 = new StringTokenizer(jList_pedido_queijos.getSelectedValue(), ",");
            String id = st2.nextToken();
            id = id.replace("ID: ", "");
            idNovoProduto = Integer.parseInt(id);

            QueijoPedido newPedido = new QueijoPedido(0, 0, idNovoProduto, quant);
            queijoPedidoList.add(newPedido);
            produtosPedidosTableBuilder(jtb_resumo_produtos_pedido, queijoPedidoList, true);
            
            player.sucessSong.play();
            JOptionPane.showMessageDialog(null, "Produto Adicionado ao Pedido!");
        } else {
            player.erroSong.play();
            JOptionPane.showMessageDialog(null, "Quantidade do Produto Inválida", "Erro ao Realizar Operação", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_jb_pedido_addProdutoActionPerformed

    private void jtf_pedido_horaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jtf_pedido_horaActionPerformed
    }//GEN-LAST:event_jtf_pedido_horaActionPerformed

    private void jbt_pedido_cancelar_produtoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbt_pedido_cancelar_produtoActionPerformed
        
        if(jtf_pedido_n_cancelar.getText().isEmpty()){
            player.erroSong.play();
            JOptionPane.showMessageDialog(null, "Número do Produto Inválido (vazio)", "Erro ao Realizar Operação", JOptionPane.ERROR_MESSAGE);
        }
        else if(!(jtf_pedido_n_cancelar.getText().matches("[0-9]+"))){
            player.erroSong.play();
            JOptionPane.showMessageDialog(null, "Número do Produto Inválido (contém letras)", "Erro ao Realizar Operação", JOptionPane.ERROR_MESSAGE);
        } 
        else if (Integer.parseInt(jtf_pedido_n_cancelar.getText()) <= queijoPedidoList.size() && Integer.parseInt(jtf_pedido_n_cancelar.getText()) > 0) {
            queijoPedidoList.remove(Integer.parseInt(jtf_pedido_n_cancelar.getText()) - 1);
            produtosPedidosTableBuilder(jtb_resumo_produtos_pedido, queijoPedidoList, true);
            jtf_pedido_n_cancelar.setText("");
            player.sucessSong.play();
            JOptionPane.showMessageDialog(null, "Produto Cancelado!");
            
        } else {
            player.erroSong.play();
            JOptionPane.showMessageDialog(null, "Número do Produto Inválido", "Erro ao Realizar Operação", JOptionPane.ERROR_MESSAGE);
        }

    }//GEN-LAST:event_jbt_pedido_cancelar_produtoActionPerformed

    private void jb_pedido_hora_nowActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jb_pedido_hora_nowActionPerformed

        jtf_pedido_data.setText(dataBuilder(LocalDateTime.now()));
        jtf_pedido_hora.setText(horaBuilder(LocalDateTime.now()));
    }//GEN-LAST:event_jb_pedido_hora_nowActionPerformed

    private void jb_back_pedidoRegistrationActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jb_back_pedidoRegistrationActionPerformed
        clearPedidoRegistrationPage();
    }//GEN-LAST:event_jb_back_pedidoRegistrationActionPerformed

    private void jb_pedidoList_consultarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jb_pedidoList_consultarActionPerformed
        jtf_pedidoList_selected_id.getText();
        ArrayList<Pedido> pedidoList = PedidoDAO.read();
        boolean achou = false;
        for (int i = 0; i < pedidoList.size(); i++) {
            Pedido p = pedidoList.get(i);
            if (jtf_pedidoList_selected_id.getText().equals("" + p.getPedidoID())) {
                achou = true;
            }
        }
        if (achou == true) {
            produtosPedidosTableBuilder(jtb_PedidoList_queijoPedido, QueijoPedidoDAO.read(jtf_pedidoList_selected_id.getText()), false);
            jl_pedidoList_id.setText(jtf_pedidoList_selected_id.getText());
        } else {
            player.erroSong.play();
            JOptionPane.showMessageDialog(null, "ID do Pedido não Encontrado", "Erro ao Realizar Operação", JOptionPane.ERROR_MESSAGE);
        }

    }//GEN-LAST:event_jb_pedidoList_consultarActionPerformed
// Pedido Functions End ----------------------------------------------------------------------------------------------

    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(MainScreen.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(MainScreen.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(MainScreen.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MainScreen.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new MainScreen().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JButton jButton8;
    private javax.swing.JButton jButton9;
    private javax.swing.JButton jButton_busca_cliente_cpf;
    private javax.swing.JButton jButton_busca_cliente_nome;
    private javax.swing.JButton jButton_busca_queijo_ID;
    private javax.swing.JButton jButton_client_order_cres;
    private javax.swing.JButton jButton_client_order_dec;
    private javax.swing.JButton jButton_inserirCliente;
    private javax.swing.JButton jButton_inserirQueijo;
    private javax.swing.JButton jButton_modificarCliente;
    private javax.swing.JButton jButton_modificarQueijo;
    private javax.swing.JButton jButton_queijo_mais_caro;
    private javax.swing.JButton jButton_queijo_menor_temp;
    private javax.swing.JButton jButton_queijo_order_cres1;
    private javax.swing.JButton jButton_queijo_order_dec1;
    private javax.swing.JButton jButton_removerCliente;
    private javax.swing.JButton jButton_removerQueijo;
    private javax.swing.JComboBox<String> jComboBox2;
    private javax.swing.JComboBox<String> jComboBox_ordenar_clientes;
    private javax.swing.JComboBox<String> jComboBox_ordenar_queijos;
    private javax.swing.JLabel jL_Cadastrar_cliente;
    private javax.swing.JLabel jL_Cadastrar_cliente1;
    private javax.swing.JLabel jL_Cadastrar_cliente2;
    private javax.swing.JLabel jL_Cadastrar_cliente3;
    private javax.swing.JLabel jL_Cadastrar_cliente4;
    private javax.swing.JLabel jL_Cadastrar_queijo;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel32;
    private javax.swing.JLabel jLabel33;
    private javax.swing.JLabel jLabel34;
    private javax.swing.JLabel jLabel35;
    private javax.swing.JLabel jLabel36;
    private javax.swing.JLabel jLabel37;
    private javax.swing.JLabel jLabel38;
    private javax.swing.JLabel jLabel39;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel40;
    private javax.swing.JLabel jLabel41;
    private javax.swing.JLabel jLabel42;
    private javax.swing.JLabel jLabel43;
    private javax.swing.JLabel jLabel44;
    private javax.swing.JLabel jLabel45;
    private javax.swing.JLabel jLabel46;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JLayeredPane jLayeredPane1;
    private javax.swing.JLayeredPane jLayeredPane2;
    private javax.swing.JLayeredPane jLayeredPane3;
    private javax.swing.JLayeredPane jLayeredPane4;
    private javax.swing.JList<String> jList_pedido_queijos;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem_ExportarPDF;
    private javax.swing.JMenuItem jMenuItem_ExportarTXT;
    private javax.swing.JMenuItem jMenuItem_ExportarXLS;
    private javax.swing.JMenuItem jMenuItem_Sobre;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel13;
    private javax.swing.JPanel jPanel14;
    private javax.swing.JPanel jPanel15;
    private javax.swing.JPanel jPanel16;
    private javax.swing.JPanel jPanel17;
    private javax.swing.JPanel jPanel18;
    private javax.swing.JPanel jPanel19;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JPanel jPanel_ClientList;
    private javax.swing.JPanel jPanel_OrderPedido;
    private javax.swing.JPanel jPanel_PedidosList;
    private javax.swing.JPanel jPanel_QueijoList;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JScrollPane jScrollPane7;
    private javax.swing.JScrollPane jScrollPane8;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTable jTableClient;
    private javax.swing.JTable jTableQueijo;
    private javax.swing.JButton jb_backClientPage;
    private javax.swing.JButton jb_backQueijoPage;
    private javax.swing.JButton jb_back_pedidoRegistration;
    private javax.swing.JButton jb_finalizarCadastro;
    private javax.swing.JButton jb_finalizarCadastroQueijo;
    private javax.swing.JButton jb_ordenar_QueijosPedido_cres;
    private javax.swing.JButton jb_ordenar_QueijosPedido_desc;
    private javax.swing.JButton jb_ordenar_pedidos_cres;
    private javax.swing.JButton jb_ordenar_pedidos_desc;
    private javax.swing.JButton jb_pedidoList_consultar;
    private javax.swing.JButton jb_pedido_addProduto;
    private javax.swing.JButton jb_pedido_finalizar;
    private javax.swing.JButton jb_pedido_hora_now;
    private javax.swing.JButton jb_pedido_newClient;
    private javax.swing.JButton jb_pedido_newQueijo;
    private javax.swing.JButton jbt_pedido_cancelar_produto;
    private javax.swing.JComboBox<String> jcb_ordenar_QueijosPedido;
    private javax.swing.JComboBox<String> jcb_ordenar_pedidos;
    private javax.swing.JComboBox<String> jcb_pedido_client;
    private javax.swing.JLabel jl_pedidoList_id;
    private javax.swing.JLabel jlb_pedidoList_valor_total;
    private javax.swing.JLabel jlb_pedido_valor_total;
    private javax.swing.JLabel jlb_totalClientes;
    private javax.swing.JLabel jlb_totalPedidos;
    private javax.swing.JLabel jlb_totalQueijos;
    private javax.swing.JPanel jpanel_Dashboard;
    private javax.swing.JPanel jpn_clientRegistration;
    private javax.swing.JPanel jpn_clientsList;
    private javax.swing.JPanel jpn_pedidoList;
    private javax.swing.JPanel jpn_queijoList;
    private javax.swing.JPanel jpn_queijoRegistration;
    private javax.swing.JSpinner jsp_quantidade_produto;
    private javax.swing.JTextArea jta_pedidoList_obs;
    private javax.swing.JTextArea jta_pedido_note;
    private javax.swing.JTable jtb_PedidoList;
    private javax.swing.JTable jtb_PedidoList_queijoPedido;
    private javax.swing.JTable jtb_resumo_produtos_pedido;
    private javax.swing.JTextField jtf_client_Address;
    private javax.swing.JFormattedTextField jtf_client_CPF;
    private javax.swing.JTextField jtf_client_CreditCard;
    private javax.swing.JTextField jtf_client_Face;
    private javax.swing.JTextField jtf_client_Insta;
    private javax.swing.JTextField jtf_client_Name;
    private javax.swing.JFormattedTextField jtf_client_Phone;
    private javax.swing.JTextField jtf_pedidoList_selected_id;
    private javax.swing.JFormattedTextField jtf_pedido_cpfCliente;
    private javax.swing.JFormattedTextField jtf_pedido_data;
    private javax.swing.JTextField jtf_pedido_deliveryDeadLine;
    private javax.swing.JFormattedTextField jtf_pedido_hora;
    private javax.swing.JTextField jtf_pedido_id;
    private javax.swing.JTextField jtf_pedido_n_cancelar;
    private javax.swing.JTextField jtf_pedido_nomeProduto;
    private javax.swing.JTextField jtf_queijo_Temperatura;
    private javax.swing.JFormattedTextField jtf_queijo_id;
    private javax.swing.JTextField jtf_queijo_peso;
    private javax.swing.JTextField jtf_queijo_tipo;
    private javax.swing.JFormattedTextField jtf_queijo_valorKg;
    // End of variables declaration//GEN-END:variables
}
