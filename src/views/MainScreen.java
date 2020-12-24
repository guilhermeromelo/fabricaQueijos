package views;

import java.sql.SQLException;
import java.util.ArrayList;
import javax.swing.*;
import models.*;
import controllers.*;
import javax.swing.table.DefaultTableModel;

public class MainScreen extends javax.swing.JFrame {

    public MainScreen() {
        initComponents();
        jlb_totalClientes.setText("30");
        jpn_clientsList.setVisible(true);
        jpn_clientRegistration.setVisible(false);
        clientTableBuilder(jTable1, ClientDAO.read(false, ""));
        

    }

    void clientTableBuilder(JTable jtable, ArrayList<Client> clientList) {
        DefaultTableModel tableRows;
        tableRows = new DefaultTableModel(new String[]{"Nº", "CPF", "Nome", "Telefone",
            "Endereço", "Cartão Crédito", "Facebook", "Instagram"}, 0);
        for(int i=0;i<clientList.size();i++){
            Client c = clientList.get(i);
            tableRows.addRow(new Object[]{(i+1), c.getCPF(), c.getClientName(), c.getPhone(),
            c.getAddress(), c.getCreditCard(), c.getFacebookURL(), c.getInstagramURL()});
        }
        jtable.setModel(tableRows);
    }

    /*
    private void atualizarTabela()
    {
        dtm_tabela = new DefaultTableModel(null, new String[]{"CPF","Nome","Endereço","Email"});
        JTB_dados.setModel(dtm_tabela);
        dtm_tabela.setNumRows(0);
        al_listaPessoas = PessoaDAO.buscarTodosElementos();
        for (Pessoa p : al_listaPessoas) 
        {
            dtm_tabela.addRow(new Object[]{p.getCpf(), p.getNome(), p.getEndereco(), p.getEmail()});
	}
    }
    public void montaTabela(JTable j, ArrayList<Carro> listaCarros) {
        DefaultTableModel tabela;
        tabela = new DefaultTableModel(new String[]{"Codigo", "Chassi", "Ano", "Modelo", "Fabricante", "Potência", "Ar Condicionado"}, 0);

        listaCarros.forEach(p -> {
            Vector linha = new Vector();
            linha.add(p.getCodigo());
            linha.add(p.getChassi());
            linha.add(p.getAno());
            linha.add(p.getModelo());
            linha.add(p.getFabricante());
            linha.add(p.getPotencia());
            linha.add(p.isArCondicionado());
            tabela.addRow(linha);
        });
        j.setModel(tabela);
    }*/

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jTabbedPane1 = new javax.swing.JTabbedPane();
        jpanel_Dashboard = new javax.swing.JPanel();
        jPanel_RealizarPedido = new javax.swing.JPanel();
        jLayeredPane1 = new javax.swing.JLayeredPane();
        jPanel4 = new javax.swing.JPanel();
        jPanel_MostrarPedidos = new javax.swing.JPanel();
        jPanel_CadastrarCliente = new javax.swing.JPanel();
        jLayeredPane2 = new javax.swing.JLayeredPane();
        jpn_clientsList = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jLabel3 = new javax.swing.JLabel();
        jlb_totalClientes = new javax.swing.JLabel();
        jButton_inserirCliente = new javax.swing.JButton();
        jButton_modificarCliente = new javax.swing.JButton();
        jButton_removerCliente = new javax.swing.JButton();
        jpn_clientRegistration = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jtf_client_CreditCard = new javax.swing.JTextField();
        jtf_client_Address = new javax.swing.JTextField();
        jtf_client_Phone = new javax.swing.JTextField();
        jtf_client_Name = new javax.swing.JTextField();
        jtf_client_CPF = new javax.swing.JTextField();
        jtf_client_Insta = new javax.swing.JTextField();
        jtf_client_Face = new javax.swing.JTextField();
        jb_finalizarCadastro = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        jPanel5 = new javax.swing.JPanel();
        jPanel6 = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        jPanel_CadastrarQueijo = new javax.swing.JPanel();
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
            .addGap(0, 1255, Short.MAX_VALUE)
        );
        jpanel_DashboardLayout.setVerticalGroup(
            jpanel_DashboardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 620, Short.MAX_VALUE)
        );

        jTabbedPane1.addTab("Dashboard", jpanel_Dashboard);

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1255, Short.MAX_VALUE)
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 620, Short.MAX_VALUE)
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

        javax.swing.GroupLayout jPanel_RealizarPedidoLayout = new javax.swing.GroupLayout(jPanel_RealizarPedido);
        jPanel_RealizarPedido.setLayout(jPanel_RealizarPedidoLayout);
        jPanel_RealizarPedidoLayout.setHorizontalGroup(
            jPanel_RealizarPedidoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLayeredPane1)
        );
        jPanel_RealizarPedidoLayout.setVerticalGroup(
            jPanel_RealizarPedidoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLayeredPane1)
        );

        jTabbedPane1.addTab("Realizar Pedido", jPanel_RealizarPedido);

        javax.swing.GroupLayout jPanel_MostrarPedidosLayout = new javax.swing.GroupLayout(jPanel_MostrarPedidos);
        jPanel_MostrarPedidos.setLayout(jPanel_MostrarPedidosLayout);
        jPanel_MostrarPedidosLayout.setHorizontalGroup(
            jPanel_MostrarPedidosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1255, Short.MAX_VALUE)
        );
        jPanel_MostrarPedidosLayout.setVerticalGroup(
            jPanel_MostrarPedidosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 620, Short.MAX_VALUE)
        );

        jTabbedPane1.addTab("Mostrar Pedidos", jPanel_MostrarPedidos);

        jLayeredPane2.setLayout(new javax.swing.OverlayLayout(jLayeredPane2));

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel2.setText("CLIENTES CADASTRADOS");

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane1.setViewportView(jTable1);

        jLabel3.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel3.setText("Total de Clientes Cadastrados: ");

        jlb_totalClientes.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        jButton_inserirCliente.setText("Inserir Cliente");
        jButton_inserirCliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_inserirClienteActionPerformed(evt);
            }
        });

        jButton_modificarCliente.setText("Modificar Cliente");
        jButton_modificarCliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_modificarClienteActionPerformed(evt);
            }
        });

        jButton_removerCliente.setText("Remover Cliente");
        jButton_removerCliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_removerClienteActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jpn_clientsListLayout = new javax.swing.GroupLayout(jpn_clientsList);
        jpn_clientsList.setLayout(jpn_clientsListLayout);
        jpn_clientsListLayout.setHorizontalGroup(
            jpn_clientsListLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpn_clientsListLayout.createSequentialGroup()
                .addGroup(jpn_clientsListLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jpn_clientsListLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jScrollPane1))
                    .addGroup(jpn_clientsListLayout.createSequentialGroup()
                        .addGap(486, 486, 486)
                        .addComponent(jLabel2)
                        .addGap(0, 529, Short.MAX_VALUE))
                    .addGroup(jpn_clientsListLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jlb_totalClientes, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
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
                .addComponent(jLabel2)
                .addGap(13, 13, 13)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 530, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jpn_clientsListLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jpn_clientsListLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jlb_totalClientes, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 17, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel3, javax.swing.GroupLayout.Alignment.TRAILING))
                    .addGroup(jpn_clientsListLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jButton_inserirCliente)
                        .addComponent(jButton_modificarCliente)
                        .addComponent(jButton_removerCliente)))
                .addGap(14, 14, 14))
        );

        jLayeredPane2.add(jpn_clientsList);

        jLabel6.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel6.setText("Telefone:");

        jLabel7.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel7.setText("Nome:");

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel4.setText("CADASTRAR NOVO CLIENTE");

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
        jb_finalizarCadastro.setText("FINALIZAR O CADASTRO");
        jb_finalizarCadastro.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jb_finalizarCadastroActionPerformed(evt);
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
                        .addComponent(jLabel4))
                    .addGroup(jpn_clientRegistrationLayout.createSequentialGroup()
                        .addGap(409, 409, 409)
                        .addGroup(jpn_clientRegistrationLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jpn_clientRegistrationLayout.createSequentialGroup()
                                .addGroup(jpn_clientRegistrationLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel6)
                                    .addComponent(jLabel8)
                                    .addComponent(jLabel7)
                                    .addComponent(jLabel5)
                                    .addComponent(jLabel9)
                                    .addComponent(jLabel11)
                                    .addComponent(jLabel10))
                                .addGroup(jpn_clientRegistrationLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jpn_clientRegistrationLayout.createSequentialGroup()
                                        .addGap(18, 18, 18)
                                        .addGroup(jpn_clientRegistrationLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addComponent(jtf_client_Address)
                                            .addComponent(jtf_client_CreditCard)
                                            .addComponent(jtf_client_Insta)
                                            .addComponent(jtf_client_Face, javax.swing.GroupLayout.PREFERRED_SIZE, 294, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jpn_clientRegistrationLayout.createSequentialGroup()
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addGroup(jpn_clientRegistrationLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jtf_client_CPF, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 294, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jtf_client_Name, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 294, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jtf_client_Phone, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 294, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                            .addComponent(jb_finalizarCadastro, javax.swing.GroupLayout.PREFERRED_SIZE, 406, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(440, Short.MAX_VALUE))
        );
        jpn_clientRegistrationLayout.setVerticalGroup(
            jpn_clientRegistrationLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpn_clientRegistrationLayout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addComponent(jLabel4)
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
                .addContainerGap(132, Short.MAX_VALUE))
        );

        jLayeredPane2.add(jpn_clientRegistration);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1255, Short.MAX_VALUE)
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 631, Short.MAX_VALUE)
        );

        jLayeredPane2.add(jPanel3);

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1255, Short.MAX_VALUE)
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 631, Short.MAX_VALUE)
        );

        jLayeredPane2.add(jPanel5);

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1255, Short.MAX_VALUE)
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 631, Short.MAX_VALUE)
        );

        jLayeredPane2.add(jPanel6);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1255, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 631, Short.MAX_VALUE)
        );

        jLayeredPane2.add(jPanel1);

        javax.swing.GroupLayout jPanel_CadastrarClienteLayout = new javax.swing.GroupLayout(jPanel_CadastrarCliente);
        jPanel_CadastrarCliente.setLayout(jPanel_CadastrarClienteLayout);
        jPanel_CadastrarClienteLayout.setHorizontalGroup(
            jPanel_CadastrarClienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLayeredPane2)
        );
        jPanel_CadastrarClienteLayout.setVerticalGroup(
            jPanel_CadastrarClienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLayeredPane2)
        );

        jTabbedPane1.addTab("Mostrar Clientes", jPanel_CadastrarCliente);

        javax.swing.GroupLayout jPanel_CadastrarQueijoLayout = new javax.swing.GroupLayout(jPanel_CadastrarQueijo);
        jPanel_CadastrarQueijo.setLayout(jPanel_CadastrarQueijoLayout);
        jPanel_CadastrarQueijoLayout.setHorizontalGroup(
            jPanel_CadastrarQueijoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1255, Short.MAX_VALUE)
        );
        jPanel_CadastrarQueijoLayout.setVerticalGroup(
            jPanel_CadastrarQueijoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 620, Short.MAX_VALUE)
        );

        jTabbedPane1.addTab("Cadastrar Queijo", jPanel_CadastrarQueijo);

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
                .addComponent(jTabbedPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 648, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton_modificarClienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_modificarClienteActionPerformed
        // TODO add your handling code here:
        String idUpdate = JOptionPane.showInputDialog("Por favor digite o CPF do Cliente para modificar: ");
    }//GEN-LAST:event_jButton_modificarClienteActionPerformed

    private void jButton_removerClienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_removerClienteActionPerformed
        // TODO add your handling code here:
        String idDelete = JOptionPane.showInputDialog("Por favor digite o CPF do Cliente para remover: ");
    }//GEN-LAST:event_jButton_removerClienteActionPerformed

    private void jb_finalizarCadastroActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jb_finalizarCadastroActionPerformed
        // TODO add your handling code here:
        Client newClient = new Client();
        newClient.setCPF(jtf_client_CPF.getText());
        newClient.setClientName(jtf_client_Name.getText());
        newClient.setAddress(jtf_client_Address.getText());
        newClient.setCreditCard(jtf_client_CreditCard.getText());
        newClient.setPhone(jtf_client_Phone.getText());
        newClient.setFacebookURL(jtf_client_Face.getText());
        newClient.setInstagramURL(jtf_client_Insta.getText());
        ClientDAO.create(newClient);
        jpn_clientRegistration.setVisible(false);
        jpn_clientsList.setVisible(true);
    }//GEN-LAST:event_jb_finalizarCadastroActionPerformed

    private void jButton_inserirClienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_inserirClienteActionPerformed
        // TODO add your handling code here:
        jpn_clientsList.setVisible(false);
        jpn_clientRegistration.setVisible(true);
    }//GEN-LAST:event_jButton_inserirClienteActionPerformed

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
    private javax.swing.JButton jButton_inserirCliente;
    private javax.swing.JButton jButton_modificarCliente;
    private javax.swing.JButton jButton_removerCliente;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
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
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem_ExportarPDF;
    private javax.swing.JMenuItem jMenuItem_ExportarTXT;
    private javax.swing.JMenuItem jMenuItem_ExportarXLS;
    private javax.swing.JMenuItem jMenuItem_Sobre;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel_CadastrarCliente;
    private javax.swing.JPanel jPanel_CadastrarQueijo;
    private javax.swing.JPanel jPanel_MostrarPedidos;
    private javax.swing.JPanel jPanel_RealizarPedido;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JButton jb_finalizarCadastro;
    private javax.swing.JLabel jlb_totalClientes;
    private javax.swing.JPanel jpanel_Dashboard;
    private javax.swing.JPanel jpn_clientRegistration;
    private javax.swing.JPanel jpn_clientsList;
    private javax.swing.JTextField jtf_client_Address;
    private javax.swing.JTextField jtf_client_CPF;
    private javax.swing.JTextField jtf_client_CreditCard;
    private javax.swing.JTextField jtf_client_Face;
    private javax.swing.JTextField jtf_client_Insta;
    private javax.swing.JTextField jtf_client_Name;
    private javax.swing.JTextField jtf_client_Phone;
    // End of variables declaration//GEN-END:variables
}
