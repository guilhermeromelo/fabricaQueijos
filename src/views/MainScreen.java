package views;

import java.sql.SQLException;
import java.util.ArrayList;
import javax.swing.*;
import models.*;
import controllers.*;
import java.awt.event.ItemEvent;
import javax.swing.table.DefaultTableModel;

// ÍNDICE
// Client Functions - linha 30 a 80
public class MainScreen extends javax.swing.JFrame {

    boolean isClientUpdate = false;
    boolean isQueijoUpdate = false;
    String clientOrder = "";
    String queijoOrder = "";
    boolean clientOrderDecreasing = false;
    boolean queijoOrderDecreasing = false;

    public MainScreen() {
        initComponents();
        //JTabbed Panel
        jlb_totalClientes.setText("30");
        jpn_clientsList.setVisible(true);
        jpn_queijoList.setVisible(true);

        //Second panel
        jpn_clientRegistration.setVisible(false);
        jpn_queijoRegistration.setVisible(false);

        //Tables Initialization
        clientTableBuilder(jTableClient, ClientDAO.read(false, "", false));
        queijoTableBuilder(jTableQueijo, QueijoDAO.read(false, "", false));
        
        //Masks Initialization
        try {
            jtf_client_Phone.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("(##)########*")));
            jtf_client_CPF.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("###.###.###-##")));
        } catch (java.text.ParseException ex) {
            System.out.print("Erro: " + ex.toString());
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
            "Tipo", "Temperatura Ideal"}, 0);
        for (int i = 0; i < queijoList.size(); i++) {
            Queijo q = queijoList.get(i);
            tableRows1.addRow(new Object[]{(i + 1), q.getQueijoID(), q.getWeight(),
                q.getPricePerKg(), q.getQueijoType(), q.getRecommendedTemperature()});
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
            JOptionPane.showMessageDialog(null, "Erro(s) Encontrados: " + erro,
                    "Erro ao Realizar Operação", JOptionPane.ERROR_MESSAGE);
        }
        return valido;
    }
    // Queijo Functions End ----------------------------------------------------------------------------------------------

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jTabbedPane1 = new javax.swing.JTabbedPane();
        jpanel_Dashboard = new javax.swing.JPanel();
        jPanel_OrderPedido = new javax.swing.JPanel();
        jLayeredPane1 = new javax.swing.JLayeredPane();
        jPanel4 = new javax.swing.JPanel();
        jPanel_PedidosList = new javax.swing.JPanel();
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

        javax.swing.GroupLayout jpanel_DashboardLayout = new javax.swing.GroupLayout(jpanel_Dashboard);
        jpanel_Dashboard.setLayout(jpanel_DashboardLayout);
        jpanel_DashboardLayout.setHorizontalGroup(
            jpanel_DashboardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1256, Short.MAX_VALUE)
        );
        jpanel_DashboardLayout.setVerticalGroup(
            jpanel_DashboardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 636, Short.MAX_VALUE)
        );

        jTabbedPane1.addTab("Dashboard", jpanel_Dashboard);

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1256, Short.MAX_VALUE)
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 636, Short.MAX_VALUE)
        );

        jLayeredPane1.setLayer(jPanel4, javax.swing.JLayeredPane.DEFAULT_LAYER);

        javax.swing.GroupLayout jLayeredPane1Layout = new javax.swing.GroupLayout(jLayeredPane1);
        jLayeredPane1.setLayout(jLayeredPane1Layout);
        jLayeredPane1Layout.setHorizontalGroup(
            jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jLayeredPane1Layout.setVerticalGroup(
            jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jPanel_OrderPedidoLayout = new javax.swing.GroupLayout(jPanel_OrderPedido);
        jPanel_OrderPedido.setLayout(jPanel_OrderPedidoLayout);
        jPanel_OrderPedidoLayout.setHorizontalGroup(
            jPanel_OrderPedidoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLayeredPane1)
        );
        jPanel_OrderPedidoLayout.setVerticalGroup(
            jPanel_OrderPedidoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLayeredPane1)
        );

        jTabbedPane1.addTab("Realizar Pedido", jPanel_OrderPedido);

        javax.swing.GroupLayout jPanel_PedidosListLayout = new javax.swing.GroupLayout(jPanel_PedidosList);
        jPanel_PedidosList.setLayout(jPanel_PedidosListLayout);
        jPanel_PedidosListLayout.setHorizontalGroup(
            jPanel_PedidosListLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1256, Short.MAX_VALUE)
        );
        jPanel_PedidosListLayout.setVerticalGroup(
            jPanel_PedidosListLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 636, Short.MAX_VALUE)
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
        jButton_client_order_dec.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_client_order_decActionPerformed(evt);
            }
        });

        jButton_client_order_cres.setIcon(new javax.swing.ImageIcon(getClass().getResource("/views/Icons/baseline_keyboard_arrow_up_black_18dp.png"))); // NOI18N
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
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 173, Short.MAX_VALUE)
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
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jpn_clientsListLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jLabel19)
                    .addComponent(jComboBox_ordenar_clientes, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton_client_order_dec)
                    .addComponent(jButton_client_order_cres))
                .addGap(9, 9, 9)
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
                .addContainerGap(441, Short.MAX_VALUE))
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
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 63, Short.MAX_VALUE)
                .addComponent(jb_backClientPage)
                .addGap(45, 45, 45))
        );

        jLayeredPane2.add(jpn_clientRegistration);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1256, Short.MAX_VALUE)
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 636, Short.MAX_VALUE)
        );

        jLayeredPane2.add(jPanel3);

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1256, Short.MAX_VALUE)
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 636, Short.MAX_VALUE)
        );

        jLayeredPane2.add(jPanel5);

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1256, Short.MAX_VALUE)
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 636, Short.MAX_VALUE)
        );

        jLayeredPane2.add(jPanel6);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1256, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 636, Short.MAX_VALUE)
        );

        jLayeredPane2.add(jPanel1);

        javax.swing.GroupLayout jPanel_ClientListLayout = new javax.swing.GroupLayout(jPanel_ClientList);
        jPanel_ClientList.setLayout(jPanel_ClientListLayout);
        jPanel_ClientListLayout.setHorizontalGroup(
            jPanel_ClientListLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLayeredPane2)
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
        jButton_queijo_order_dec1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_queijo_order_dec1ActionPerformed(evt);
            }
        });

        jButton_queijo_order_cres1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/views/Icons/baseline_keyboard_arrow_up_black_18dp.png"))); // NOI18N
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
                        .addGap(0, 509, Short.MAX_VALUE))
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
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jpn_queijoListLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jpn_queijoListLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel4)
                        .addComponent(jLabel18)
                        .addComponent(jComboBox_ordenar_queijos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jpn_queijoListLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jButton_queijo_order_dec1)
                        .addComponent(jButton_queijo_order_cres1)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 532, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 15, Short.MAX_VALUE)
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
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
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
                .addContainerGap(444, Short.MAX_VALUE))
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
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 162, Short.MAX_VALUE)
                .addComponent(jb_backQueijoPage)
                .addGap(46, 46, 46))
        );

        jLayeredPane3.add(jpn_queijoRegistration);

        javax.swing.GroupLayout jPanel13Layout = new javax.swing.GroupLayout(jPanel13);
        jPanel13.setLayout(jPanel13Layout);
        jPanel13Layout.setHorizontalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1256, Short.MAX_VALUE)
        );
        jPanel13Layout.setVerticalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 636, Short.MAX_VALUE)
        );

        jLayeredPane3.add(jPanel13);

        javax.swing.GroupLayout jPanel12Layout = new javax.swing.GroupLayout(jPanel12);
        jPanel12.setLayout(jPanel12Layout);
        jPanel12Layout.setHorizontalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1256, Short.MAX_VALUE)
        );
        jPanel12Layout.setVerticalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 636, Short.MAX_VALUE)
        );

        jLayeredPane3.add(jPanel12);

        javax.swing.GroupLayout jPanel11Layout = new javax.swing.GroupLayout(jPanel11);
        jPanel11.setLayout(jPanel11Layout);
        jPanel11Layout.setHorizontalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1256, Short.MAX_VALUE)
        );
        jPanel11Layout.setVerticalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 636, Short.MAX_VALUE)
        );

        jLayeredPane3.add(jPanel11);

        javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1256, Short.MAX_VALUE)
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 636, Short.MAX_VALUE)
        );

        jLayeredPane3.add(jPanel10);

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1256, Short.MAX_VALUE)
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 636, Short.MAX_VALUE)
        );

        jLayeredPane3.add(jPanel9);

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1256, Short.MAX_VALUE)
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 636, Short.MAX_VALUE)
        );

        jLayeredPane3.add(jPanel7);

        javax.swing.GroupLayout jPanel_QueijoListLayout = new javax.swing.GroupLayout(jPanel_QueijoList);
        jPanel_QueijoList.setLayout(jPanel_QueijoListLayout);
        jPanel_QueijoListLayout.setHorizontalGroup(
            jPanel_QueijoListLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLayeredPane3)
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
                .addContainerGap()
                .addComponent(jTabbedPane1)
                .addContainerGap())
            .addGroup(layout.createSequentialGroup()
                .addGap(474, 474, 474)
                .addComponent(jLabel1)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addGap(5, 5, 5)
                .addComponent(jTabbedPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 664, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents
// Client 1st part Functions Begin ----------------------------------------------------------------------------------------------
    private void jButton_modificarClienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_modificarClienteActionPerformed
        // TODO add your handling code here:
        //PERGUNTAR QUAL OBJETO A MODIFICAR
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
                JOptionPane.showMessageDialog(null, "CPF não Encontrado", "Erro ao Realizar Operação", JOptionPane.ERROR_MESSAGE);
            }
        }
    }//GEN-LAST:event_jButton_modificarClienteActionPerformed

    private void jButton_removerClienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_removerClienteActionPerformed
        // TODO add your handling code here:
        //PERGUNTAR E ACHAR QUAL O OBJETO A SER EXCLUIDO
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
                int delete = JOptionPane.showConfirmDialog(null, "Tem certeza que deseja excluir "
                        + "o Cliente:\nNome: " + clientDelete.getClientName() + ", CPF: " + clientDelete.getCPF(),
                        "Confirmar Exclusão", JOptionPane.YES_NO_OPTION);

                if (delete == 0) {
                    String erro = ClientDAO.delete(clientDelete);
                    JOptionPane.showMessageDialog(null, (erro == null)
                            ? "Cliente Removido com Sucesso!"
                            : "Erro Encontado: \n" + erro, "Resultado da operação",
                            (erro == null) ? JOptionPane.INFORMATION_MESSAGE : JOptionPane.ERROR_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(null, "Operação Cancelada!");
                }
            } else {
                JOptionPane.showMessageDialog(null, "CPF não Encontrado", "Erro ao Realizar Operação", JOptionPane.ERROR_MESSAGE);
            }
            //ATUALIZAR A TABELA
            clientTableBuilder(jTableClient, ClientDAO.read((clientOrder.isEmpty() ? false : true), clientOrder,(clientOrder.isEmpty() ? false : clientOrderDecreasing)));
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
            JOptionPane.showMessageDialog(null, (erro == null)
                    ? "Dados do Cliente salvos com sucesso!"
                    : "Erro Encontado: \n" + erro, "Resultado da operação",
                    (erro == null) ? JOptionPane.INFORMATION_MESSAGE : JOptionPane.ERROR_MESSAGE);
            if (erro == null) {
                //ATUALIZAR TABELA E TROCAR AS TELAS
                clientTableBuilder(jTableClient, ClientDAO.read((clientOrder.isEmpty() ? false : true), clientOrder,(clientOrder.isEmpty() ? false : clientOrderDecreasing)));
                jpn_clientRegistration.setVisible(false);
                jpn_clientsList.setVisible(true);
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
        jpn_clientsList.setVisible(false);
        jpn_clientRegistration.setVisible(true);
    }//GEN-LAST:event_jButton_inserirClienteActionPerformed

    private void jb_backClientPageActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jb_backClientPageActionPerformed
        // TODO add your handling code here:
        //TROCAR TELAS
        clientTableBuilder(jTableClient, ClientDAO.read((clientOrder.isEmpty() ? false : true), clientOrder,(clientOrder.isEmpty() ? false : clientOrderDecreasing)));
        jpn_clientRegistration.setVisible(false);
        jpn_clientsList.setVisible(true);
    }//GEN-LAST:event_jb_backClientPageActionPerformed
// Client 1st part Functions End ----------------------------------------------------------------------------------------------

// Queijo Functions Begin ----------------------------------------------------------------------------------------------    
    private void jButton_inserirQueijoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_inserirQueijoActionPerformed
        // TODO add your handling code here:
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
                JOptionPane.showMessageDialog(null, (erro == null)
                        ? "Dados do Queijo salvos com sucesso!"
                        : "Erro Encontado: \n" + erro, "Resultado da operação",
                        (erro == null) ? JOptionPane.INFORMATION_MESSAGE
                                : JOptionPane.ERROR_MESSAGE);
                if (erro == null) {
                    //ATUALIZAR TABELA E TROCAR AS TELAS
                    queijoTableBuilder(jTableQueijo, QueijoDAO.read((queijoOrder.isEmpty() ? false : true), queijoOrder,(queijoOrder.isEmpty() ? false : queijoOrderDecreasing)));
                    //TROCAR TELAS
                    jpn_queijoList.setVisible(true);
                    jpn_queijoRegistration.setVisible(false);
                }
            }
        }
    }//GEN-LAST:event_jb_finalizarCadastroQueijoActionPerformed

    private void jb_backQueijoPageActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jb_backQueijoPageActionPerformed
        // TODO add your handling code here:
        queijoTableBuilder(jTableQueijo, QueijoDAO.read((queijoOrder.isEmpty() ? false : true), queijoOrder,(queijoOrder.isEmpty() ? false : queijoOrderDecreasing)));
        //TROCAR TELAS
        jpn_queijoList.setVisible(true);
        jpn_queijoRegistration.setVisible(false);
    }//GEN-LAST:event_jb_backQueijoPageActionPerformed

    private void jButton_modificarQueijoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_modificarQueijoActionPerformed
        // TODO add your handling code here:
        //PERGUNTAR QUAL OBJETO A MODIFICAR
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
                JOptionPane.showMessageDialog(null, "ID do Queijo não Encontrado", "Erro ao Realizar Operação", JOptionPane.ERROR_MESSAGE);
            }
        }
    }//GEN-LAST:event_jButton_modificarQueijoActionPerformed

    private void jButton_removerQueijoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_removerQueijoActionPerformed
        // TODO add your handling code here:
        //PERGUNTAR E ACHAR QUAL O OBJETO A SER EXCLUIDO
        String idUpdate = JOptionPane.showInputDialog("Por favor digite o ID do Queijo para modificar: ");
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
                int delete = JOptionPane.showConfirmDialog(null, "Tem certeza que deseja excluir "
                        + "o Queijo:\nID: " + queijoModify.getQueijoID() + ", Tipo: " + queijoModify.getQueijoType(),
                        "Confirmar Exclusão", JOptionPane.YES_NO_OPTION);

                if (delete == 0) {
                    String erro = QueijoDAO.delete(queijoModify);
                    JOptionPane.showMessageDialog(null, (erro == null)
                            ? "Queijo Removido com Sucesso!"
                            : "Erro Encontado: \n" + erro, "Resultado da operação",
                            (erro == null) ? JOptionPane.INFORMATION_MESSAGE : JOptionPane.ERROR_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(null, "Operação Cancelada!");
                }
            } else {
                JOptionPane.showMessageDialog(null, "ID do Queijo não Encontrado", "Erro ao Realizar Operação", JOptionPane.ERROR_MESSAGE);
            }
            //ATUALIZAR A TABELA
            queijoTableBuilder(jTableQueijo, QueijoDAO.read((queijoOrder.isEmpty() ? false : true), queijoOrder,(queijoOrder.isEmpty() ? false : queijoOrderDecreasing)));
        }
    }//GEN-LAST:event_jButton_removerQueijoActionPerformed

    private void jComboBox_ordenar_queijosItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jComboBox_ordenar_queijosItemStateChanged
        // TODO add your handling code here:
        if(evt.getStateChange() == ItemEvent.SELECTED){
            String newChoice = jComboBox_ordenar_queijos.getSelectedItem().toString();
            switch(newChoice){
                case "-------":{
                    queijoOrder = "";
                    break;
                }
                case "Id":{
                    queijoOrder = "queijoid";
                    break;
                }
                case "Peso":{
                    queijoOrder = "weight";
                    break;
                }
                case "Valor por Kg":{
                    queijoOrder = "pricePerKg";
                    break;
                }
                case "Temeperatura Ideal":{
                    queijoOrder = "recommendedTemperature";
                    break;
                }
                case "Tipo":{
                    queijoOrder = "queijoType";
                    break;
                }
            }
            queijoOrderDecreasing = false;
            queijoTableBuilder(jTableQueijo, QueijoDAO.read((queijoOrder.isEmpty() ? false : true), queijoOrder,(queijoOrder.isEmpty() ? false : queijoOrderDecreasing)));
        }
    }//GEN-LAST:event_jComboBox_ordenar_queijosItemStateChanged

    private void jButton_busca_queijo_IDActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_busca_queijo_IDActionPerformed
        // TODO add your handling code here:
        //PERGUNTAR QUAL ID DO OBJETO A BUSCAR
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
       
        if(achou == true){
            JOptionPane.showMessageDialog(null, "Queijo Encontrado:\n ID: "+queijoSeek.getQueijoID()+
                    ", \nTipo: "+queijoSeek.getQueijoType()+
                    ", \nPeso: "+queijoSeek.getWeight()+
                    " Kg, \nPreço Por Kg: R$ "+queijoSeek.getPricePerKg()+
                    ", \nTemperatura Recomendada: "+queijoSeek.getRecommendedTemperature()+" ºC");
        }
        else{
            JOptionPane.showMessageDialog(null, "ID do Queijo não Encontrado", "Erro ao Realizar Operação", JOptionPane.ERROR_MESSAGE);
        }

    }//GEN-LAST:event_jButton_busca_queijo_IDActionPerformed

    private void jButton_queijo_menor_tempActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_queijo_menor_tempActionPerformed
        // TODO add your handling code here:
        ArrayList<Queijo> queijoList = QueijoDAO.read(false, "", false);
        if(!queijoList.isEmpty()){
            
            Queijo lowerTemperatureQueijo = null;
            for(int i=0;i<queijoList.size();i++){
                Queijo queijoAux = queijoList.get(i);
                if(i == 0){
                    lowerTemperatureQueijo = queijoAux;
                }
                else if(queijoAux.getRecommendedTemperature()<lowerTemperatureQueijo.getRecommendedTemperature()){
                    lowerTemperatureQueijo = queijoAux;
                }
            }
            JOptionPane.showMessageDialog(null, "Queijo de Menor Temperatura Encontrado:\n ID: "+lowerTemperatureQueijo.getQueijoID()+
                    ", \nTipo: "+lowerTemperatureQueijo.getQueijoType()+
                    ", \nPeso: "+lowerTemperatureQueijo.getWeight()+
                    " Kg, \nPreço Por Kg: R$ "+lowerTemperatureQueijo.getPricePerKg()+
                    ", \nTemperatura Recomendada: "+lowerTemperatureQueijo.getRecommendedTemperature()+" ºC");
        }
        else{
            JOptionPane.showMessageDialog(null, "Nenhum Queijo Encontrado", "Erro ao Realizar Operação", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_jButton_queijo_menor_tempActionPerformed

    private void jButton_queijo_mais_caroActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_queijo_mais_caroActionPerformed
        // TODO add your handling code here:
        ArrayList<Queijo> queijoList = QueijoDAO.read(false, "", false);
        if(!queijoList.isEmpty()){
            Queijo mostExpensiveQueijo = null;
            for(int i=0;i<queijoList.size();i++){
                Queijo queijoAux = queijoList.get(i);
                if(i == 0){
                    mostExpensiveQueijo = queijoAux;
                }
                else if(queijoAux.getPricePerKg()>mostExpensiveQueijo.getPricePerKg()){
                    mostExpensiveQueijo = queijoAux;
                }
            }
            JOptionPane.showMessageDialog(null, "Queijo Mais Caro Encontrado:\n ID: "+mostExpensiveQueijo.getQueijoID()+
                    ", \nTipo: "+mostExpensiveQueijo.getQueijoType()+
                    ", \nPeso: "+mostExpensiveQueijo.getWeight()+
                    " Kg, \nPreço Por Kg: R$ "+mostExpensiveQueijo.getPricePerKg()+
                    ", \nTemperatura Recomendada: "+mostExpensiveQueijo.getRecommendedTemperature()+" ºC");
        }
        else{
            JOptionPane.showMessageDialog(null, "Nenhum Queijo Encontrado", "Erro ao Realizar Operação", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_jButton_queijo_mais_caroActionPerformed
// Queijo Functions End ----------------------------------------------------------------------------------------------
    
// Client 2nd part Functions Begin ----------------------------------------------------------------------------------------------    
    private void jComboBox_ordenar_clientesItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jComboBox_ordenar_clientesItemStateChanged
        // TODO add your handling code here:
        if(evt.getStateChange() == ItemEvent.SELECTED){
            String newChoice = jComboBox_ordenar_clientes.getSelectedItem().toString();
            switch(newChoice){
                case "-------":{
                    clientOrder = "";
                    break;
                }
                case "CPF":{
                    clientOrder = "cpf";
                    break;
                }
                case "Nome":{
                    clientOrder = "clientName";
                    break;
                }
                case "Endereço":{
                    clientOrder = "address";
                    break;
                }
                case "Cartão Crédito":{
                    clientOrder = "creditCard";
                    break;
                }
                case "Facebook":{
                    clientOrder = "facebookURL";
                    break;
                }
                case "Instagram":{
                    clientOrder = "instagramURL";
                    break;
                }
            }
            clientOrderDecreasing = false;
            clientTableBuilder(jTableClient, ClientDAO.read((clientOrder.isEmpty() ? false : true), clientOrder,(clientOrder.isEmpty() ? false : clientOrderDecreasing)));
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
        clientOrderDecreasing = true;
        if(jComboBox_ordenar_clientes.getSelectedItem() == "-------"){
            jComboBox_ordenar_clientes.setSelectedItem("CPF");
            clientOrder = "cpf";
        }
        clientTableBuilder(jTableClient, ClientDAO.read((clientOrder.isEmpty() ? false : true), clientOrder,(clientOrder.isEmpty() ? false : clientOrderDecreasing)));
    }//GEN-LAST:event_jButton_client_order_decActionPerformed

    private void jButton_client_order_cresActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_client_order_cresActionPerformed
        // TODO add your handling code here:
        clientOrderDecreasing = false;
        clientTableBuilder(jTableClient, ClientDAO.read((clientOrder.isEmpty() ? false : true), clientOrder,(clientOrder.isEmpty() ? false : clientOrderDecreasing)));
    }//GEN-LAST:event_jButton_client_order_cresActionPerformed

    private void jButton_queijo_order_dec1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_queijo_order_dec1ActionPerformed
        // TODO add your handling code here:
        queijoOrderDecreasing = true;
        if(jComboBox_ordenar_queijos.getSelectedItem() == "-------"){
            jComboBox_ordenar_queijos.setSelectedItem("Id");
            queijoOrder = "queijoid";
        }
        queijoTableBuilder(jTableQueijo, QueijoDAO.read((queijoOrder.isEmpty() ? false : true), queijoOrder,(queijoOrder.isEmpty() ? false : queijoOrderDecreasing)));
    }//GEN-LAST:event_jButton_queijo_order_dec1ActionPerformed

    private void jButton_queijo_order_cres1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_queijo_order_cres1ActionPerformed
        // TODO add your handling code here:
        queijoOrderDecreasing = false;
        queijoTableBuilder(jTableQueijo, QueijoDAO.read((queijoOrder.isEmpty() ? false : true), queijoOrder,(queijoOrder.isEmpty() ? false : queijoOrderDecreasing)));
    }//GEN-LAST:event_jButton_queijo_order_cres1ActionPerformed
// Client 2nd part Functions End ----------------------------------------------------------------------------------------------    

    
    
    
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
    private javax.swing.JComboBox<String> jComboBox_ordenar_clientes;
    private javax.swing.JComboBox<String> jComboBox_ordenar_queijos;
    private javax.swing.JLabel jL_Cadastrar_cliente;
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
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JLayeredPane jLayeredPane1;
    private javax.swing.JLayeredPane jLayeredPane2;
    private javax.swing.JLayeredPane jLayeredPane3;
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
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JPanel jPanel_ClientList;
    private javax.swing.JPanel jPanel_OrderPedido;
    private javax.swing.JPanel jPanel_PedidosList;
    private javax.swing.JPanel jPanel_QueijoList;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTable jTableClient;
    private javax.swing.JTable jTableQueijo;
    private javax.swing.JButton jb_backClientPage;
    private javax.swing.JButton jb_backQueijoPage;
    private javax.swing.JButton jb_finalizarCadastro;
    private javax.swing.JButton jb_finalizarCadastroQueijo;
    private javax.swing.JLabel jlb_totalClientes;
    private javax.swing.JLabel jlb_totalQueijos;
    private javax.swing.JPanel jpanel_Dashboard;
    private javax.swing.JPanel jpn_clientRegistration;
    private javax.swing.JPanel jpn_clientsList;
    private javax.swing.JPanel jpn_queijoList;
    private javax.swing.JPanel jpn_queijoRegistration;
    private javax.swing.JTextField jtf_client_Address;
    private javax.swing.JFormattedTextField jtf_client_CPF;
    private javax.swing.JTextField jtf_client_CreditCard;
    private javax.swing.JTextField jtf_client_Face;
    private javax.swing.JTextField jtf_client_Insta;
    private javax.swing.JTextField jtf_client_Name;
    private javax.swing.JFormattedTextField jtf_client_Phone;
    private javax.swing.JTextField jtf_queijo_Temperatura;
    private javax.swing.JFormattedTextField jtf_queijo_id;
    private javax.swing.JTextField jtf_queijo_peso;
    private javax.swing.JTextField jtf_queijo_tipo;
    private javax.swing.JFormattedTextField jtf_queijo_valorKg;
    // End of variables declaration//GEN-END:variables
}
