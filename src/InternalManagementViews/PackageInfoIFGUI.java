/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package InternalManagementViews;

import InternalDialogs.ClientsChooserIFGUI;
import InternalDialogs.PackageItemChooserIFGUI;
import InternalDialogs.RouteChooserIFGUI;
import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;
import connection.ConnectionFactory;
import java.beans.PropertyVetoException;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import model.bean.Clientes;
import model.bean.FormasEntrega;
import model.bean.ItensPacote;
import model.bean.Pacotes;
import model.dao.ClientesDAO;
import model.dao.FormasEntregaDAO;
import model.dao.ItensPacoteDAO;
import model.dao.PacotesDAO;

/**
 *
 * @author focuswts
 */
public class PackageInfoIFGUI extends javax.swing.JInternalFrame {

    /**
     * Creates new form PackageInfoIFGUI
     */
    private static boolean lock = true;
    
    PackageItemChooserIFGUI viewPic;
    private String operacao;
    private static Pacotes pacote;
    private static String metodo;
    private static int contador;
    public static Clientes cliente;
    
    private Connection con;
    
    public static int getContador() {
        return contador;
    }
    
    public static void setContador(int contador) {
        PackageInfoIFGUI.contador = contador;
    }
    
    public PackageInfoIFGUI() {
        initComponents();
        fill_DeliveryWays();
        changeLock();
    }
    
    public PackageInfoIFGUI(String op) {
        initComponents();
        this.operacao = op;
        this.con = (Connection) ConnectionFactory.getConnection();
        adaptView(op);
        fill_DeliveryWays();
        changeLock();
    }
    
    public PackageInfoIFGUI(Pacotes p, String op) {
        initComponents();
        this.pacote = p;
        this.operacao = op;
        con = (Connection) ConnectionFactory.getConnection();
        adaptView(op);
        fill_DeliveryWays();
        getDados(p.getCodigoRastreio());
        fill_tbPackageItens(p);
        changeLock();
    }
    
    private boolean checkFields() {
        boolean b = false;
        try {
            if (!txt_codigo.getText().trim().equals("") || !txt_cliente.getText().trim().equals("") || !txt_codigo.getText().trim().equals("")) {
                b = true;
            }
            return b;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Preencha Todos Os Dados!: " + e);
            return false;
        }
    }
    
    private void adaptView(String op) {
        try {
            if (op.equals("Add")) {
                this.lock = false;
                lb_Id.setVisible(false);
                txt_id.setVisible(false);
                ImageIcon icon = new ImageIcon(getClass().getResource("/icons/icons48x/add.png"));
                btn_save.setIcon(icon);
                btn_save.setText("Cadastrar Pacote");
            }
            if (op.equals("ViewDelivery")) {
                this.lock = true;
                btn_lock.setVisible(false);
                lb_Information.setVisible(false);
                ImageIcon icon = new ImageIcon(getClass().getResource("/icons/icons48x/accept.png"));
                btn_save.setIcon(icon);
                btn_save.setText("Encaminhar Pacote");
            }
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro Ao Adaptar A View: " + e);
        }
        
    }
    
    private int addPackageToDB() {
        PacotesDAO pDAO = new PacotesDAO();
        int idPacote = 0;
        
        try {
            Pacotes p = fieldsToOBJ(fillClientObjFromDB());
            idPacote = pDAO.saveGetId(p);
            
            txt_id.setText(String.valueOf(idPacote));
            this.contador++;
            return idPacote;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro Ao Salvar Pacote Ao DB : " + e);
            return idPacote;
        }
        
    }
    
    private void removeItem() {
        DefaultTableModel aModel = (DefaultTableModel) tb_PackageItens.getModel();
        ItensPacoteDAO piDAO = new ItensPacoteDAO();
        Pacotes p = new Pacotes();
        p.setId(Integer.valueOf(txt_id.getText()));
        try {
            ItensPacote pi = new ItensPacote();
            
            String itemPacote = tb_PackageItens.getValueAt(tb_PackageItens.getSelectedRow(), 0).toString();
            pi.setIdPacote(p);
            pi.setItem(itemPacote);
            int qtdItemPacote = Integer.valueOf(tb_PackageItens.getValueAt(tb_PackageItens.getSelectedRow(), 1).toString());
            pi.setQtd(qtdItemPacote);
            
            int id = new ItensPacoteDAO().findAllGetID(p, itemPacote, qtdItemPacote);
            pi.setId(id);
            piDAO.delete(pi);
            aModel.removeRow(tb_PackageItens.getSelectedRow());
            JOptionPane.showMessageDialog(this, "Item Excluido Com Sucesso!");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro Ao Excluir Item! : " + e);
        }
        
    }
    
    private void lockContent() {
        try {
            txt_id.setEditable(false);
            txt_cliente.setEditable(this.lock);
            txt_codigo.setEditable(this.lock);
            cb_formasRastreio.setEnabled(this.lock);
            btn_SearchClient.setVisible(this.lock);
            btn_add.setVisible(this.lock);
            btn_del.setVisible(this.lock);
            btn_edit.setVisible(this.lock);
            if (this.operacao.equals("ViewDelivery")) {
                btn_save.setVisible(true);
            } else {
                btn_save.setVisible(this.lock);
            }
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro Ao Habilitar Edição Do Pacote! : " + e);
        }
        
    }
    
    private void changeLock() {
        try {
            if (lock == true) {
                lock = false;
            } else {
                lock = true;
            }
            
            ImageIcon icon = new ImageIcon(getClass().getResource("/icons/icons48x/lock.png"));
            ImageIcon icon2 = new ImageIcon(getClass().getResource("/icons/icons48x/lock_off.png"));
            
            if (lock == true) {
                btn_lock.setIcon(icon2);
                lb_Information.setText("Modo De Edição Ativado!");
            } else {
                btn_lock.setIcon(icon);
                lb_Information.setText("Clique No Cadeado Para Habilitar A Edição Do Pacote!");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro Ao Mudar Ícone Botão! " + e);
        }
        lockContent();
        
    }
    
    private void fill_DeliveryWays() {
        FormasEntregaDAO dwDAO = new FormasEntregaDAO();
        ArrayList<FormasEntrega> FormasEntrega = null;
        try {
            cb_formasRastreio.removeAllItems();
            FormasEntrega = dwDAO.findAll();
            
            DefaultComboBoxModel deliveryWaysModel = new DefaultComboBoxModel(); // Cria Modelo Para ComboBox

            for (FormasEntrega deliveryWay : FormasEntrega) {
                deliveryWaysModel.addElement(deliveryWay); //Adiciona Objeto AO ComboBox & É Traduzido No Método toString() da Classe
            }
            cb_formasRastreio.setModel(deliveryWaysModel);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro Ao Carregar Formas Entrega! : " + e);
        }
    }
    
    private void getDados(String codigoRastreio) {
        String sql = null;
        PreparedStatement st;
        ResultSet rs;
        try {
            sql = "SELECT Pacotes.*,Clientes.*,FormasEntrega.* FROM Pacotes "
                    + "INNER JOIN Clientes  ON Pacotes.idCliente = Clientes.id "
                    + "INNER JOIN FormasEntrega ON Pacotes.idForma = FormasEntrega.id "
                    + "WHERE Pacotes.codigoRastreio = ?";
            st = (PreparedStatement) con.prepareStatement(sql);
            st.setString(1, codigoRastreio);
            rs = st.executeQuery();
            if (rs.next()) {
                txt_id.setText(rs.getString("Pacotes.id"));
                txt_codigo.setText(rs.getString("Pacotes.codigoRastreio"));
                txt_cliente.setText(rs.getString("Clientes.nome"));
                
                FormasEntrega dw = new FormasEntrega();
                dw.setId(rs.getInt("FormasEntrega.id"));
                dw.setForma(rs.getString("FormasEntrega.forma"));
                cb_formasRastreio.getModel().setSelectedItem(dw);
            }
            
        } catch (Exception e) {
            System.out.println("Erro Ao Preencher Dados: " + e);
        }
        
    }
    
    private void fill_tbPackageItens(Pacotes pacote) {
        
        DefaultTableModel aModel = (DefaultTableModel) this.tb_PackageItens.getModel();
        try {
            
            ItensPacoteDAO piDAO = new ItensPacoteDAO();
            ArrayList<ItensPacote> ItensPacotes = null;
            ItensPacotes = piDAO.findAll(pacote);
            
            int numLinhas = aModel.getRowCount();
            
            for (int i = 0; i < numLinhas; i++) {
                aModel.removeRow(0);
            }
            
            for (int i = 0; i < ItensPacotes.size(); i++) {
                Object[] rowDados = new Object[2];
                rowDados[0] = ItensPacotes.get(i).getItem();
                rowDados[1] = ItensPacotes.get(i).getQtd();
                aModel.addRow(rowDados);
                
            }
            
            if (aModel.getRowCount() < 1) {
                this.metodo = "Add";
            } else {
                this.metodo = "Update";
            }
            
            this.tb_PackageItens.setModel(aModel);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro Ao Preencher Itens Pacote!: " + e);
        }
        
    }
    
    public static void refresh_tbPackageItens(Pacotes pacote) {
        
        DefaultTableModel aModel = (DefaultTableModel) PackageInfoIFGUI.tb_PackageItens.getModel();
        try {
            
            ItensPacoteDAO piDAO = new ItensPacoteDAO();
            ArrayList<ItensPacote> ItensPacotes = null;
            ItensPacotes = piDAO.findAll(pacote);
            
            int numLinhas = aModel.getRowCount();
            
            for (int i = 0; i < numLinhas; i++) {
                aModel.removeRow(0);
            }
            
            for (int i = 0; i < ItensPacotes.size(); i++) {
                Object[] rowDados = new Object[2];
                rowDados[0] = ItensPacotes.get(i).getItem();
                rowDados[1] = ItensPacotes.get(i).getQtd();
                aModel.addRow(rowDados);
                
            }
            
            if (aModel.getRowCount() < 1) {
                PackageInfoIFGUI.metodo = "Add";
            } else {
                PackageInfoIFGUI.metodo = "Update";
            }
            
            PackageInfoIFGUI.tb_PackageItens.setModel(aModel);
        } catch (Exception e) {
            System.out.println("Erro Ao Preencher Itens Pacote!: " + e);
        }
        
    }
    
    public static void setClientJF() {
        try {
            PackageInfoIFGUI.txt_cliente.setText(PackageInfoIFGUI.cliente.getNome());
            
        } catch (Exception e) {
            System.out.println("Erro Ao Definir Valor Cliente JF: " + e);
        }
        
    }
    
    public static boolean addItemToTbPackageItens(ItensPacote pi) {
        DefaultTableModel aModel = (DefaultTableModel) tb_PackageItens.getModel();
        try {
            Object[] rowPackageItem = new Object[3];
            rowPackageItem[0] = pi.getId();
            rowPackageItem[1] = pi.getItem();
            rowPackageItem[2] = pi.getQtd();
            aModel.addRow(rowPackageItem);
            return true;
        } catch (Exception e) {
            System.out.println("Erro Ao Adicionar Item Pacote Á Lista! : " + e);
            return false;
        }
        
    }
    
    public static boolean editItemFromTbPackageItens(ItensPacote pi) {
        DefaultTableModel aModel = (DefaultTableModel) tb_PackageItens.getModel();
        try {
            aModel.setValueAt(pi.getItem(), tb_PackageItens.getSelectedRow(), 0);
            aModel.setValueAt(pi.getQtd(), tb_PackageItens.getSelectedRow(), 1);
            return true;
        } catch (Exception e) {
            System.out.println("Erro Ao Editar Item Pacote Á Lista! : " + e);
            return false;
        }
        
    }
    
    private ItensPacote addToItemOBJ(String operacao) {
        Pacotes p = new Pacotes();
        p.setId(Integer.valueOf(txt_id.getText()));
        ItensPacote pi = new ItensPacote();
        try {
            
            if (operacao.equals("Edit")) {
                
                String itemPacote = tb_PackageItens.getValueAt(tb_PackageItens.getSelectedRow(), 0).toString();
                pi.setIdPacote(p);
                pi.setItem(itemPacote);
                int qtdItemPacote = Integer.valueOf(tb_PackageItens.getValueAt(tb_PackageItens.getSelectedRow(), 1).toString());
                pi.setQtd(qtdItemPacote);
                
                int id = new ItensPacoteDAO().findAllGetID(p, itemPacote, qtdItemPacote);
                pi.setId(id);
                
            }
            if (operacao.equals("Add")) {
                pi.setIdPacote(pacote);
            }
            return pi;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro Ao Criar OBJ Item Pacote : " + e);
            return null;
        }
    }
    
    private Clientes fillClientObjFromDB() {
        Clientes c = new Clientes();
        ClientesDAO cDAO = new ClientesDAO();
        ArrayList<Clientes> listaClientes = cDAO.findAllWhere(txt_cliente.getText());
        try {
            for (int i = 0; i < listaClientes.size(); i++) {
                c.setId(listaClientes.get(i).getId());
                c.setNome(listaClientes.get(i).getNome());
                c.setCpf(listaClientes.get(i).getCpf());
                c.setRg(listaClientes.get(i).getRg());
                c.setEndereco(listaClientes.get(i).getEndereco());
                c.setEmail(listaClientes.get(i).getEmail());
                c.setTelefone(listaClientes.get(i).getTelefone());
                c.setCelular(listaClientes.get(i).getCelular());
            }
            return c;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro Ao Criar OBJ Cliente: " + e);
            return null;
        }
        
    }
    
    private Pacotes fieldsToOBJ(Clientes c) {
        Pacotes p = new Pacotes();
        try {
            if (!txt_id.getText().equals("")) {
                p.setId(Integer.valueOf(txt_id.getText()));
            }
            p.setIdCliente(c);
            FormasEntrega dw = (FormasEntrega) cb_formasRastreio.getSelectedItem();
            p.setIdForma(dw);
            p.setCodigoRastreio(txt_codigo.getText());
            if (this.operacao.equals("Add")) {
                p.setStatus("Ativo");
            }
            this.pacote = p;
            return p;
        } catch (Exception e) {
            System.out.println("Erro Ao Criar OBJ: " + e);
            return p;
        }
        
    }
    
    private void doOperation(String operacao) {
        PacotesDAO pDAO = new PacotesDAO();
        try {
            if (operacao.equals("Add") && this.contador == 0) {
                addPackageToDB();
                JOptionPane.showMessageDialog(this, "Pacote Criado Com Sucesso!");
            }
            if (operacao.equals("View")) {
                if (!this.pacote.equals(fieldsToOBJ(fillClientObjFromDB()))) {
                    pDAO.update(fieldsToOBJ(fillClientObjFromDB()));
                }
                JOptionPane.showMessageDialog(this, "Dados Do Pacote Alterados Com Sucesso!");
            }
            
            if (operacao.equals("ViewDelivery")) {
                RouteChooserIFGUI viewRouteChooser = new RouteChooserIFGUI(null, true, fieldsToOBJ(fillClientObjFromDB()));
                this.getParent().add(viewRouteChooser);
                viewRouteChooser.toFront();
                viewRouteChooser.setVisible(true);
            }
            
            this.dispose();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro Ao Executar Operação! : " + e);
        }
        
    }
    
    public static boolean isLock() {
        return lock;
    }
    
    public static void setLock(boolean aLock) {
        lock = aLock;
    }
    
    public static javax.swing.JTextField getTxt_cliente() {
        return txt_cliente;
    }
    
    public static void setTxt_cliente(String cliente) {
        PackageInfoIFGUI.txt_cliente.setText(cliente);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        lb_Id = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        txt_id = new javax.swing.JTextField();
        txt_cliente = new javax.swing.JTextField();
        txt_codigo = new javax.swing.JTextField();
        cb_formasRastreio = new javax.swing.JComboBox<>();
        btn_SearchClient = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tb_PackageItens = new javax.swing.JTable();
        btn_add = new javax.swing.JButton();
        btn_edit = new javax.swing.JButton();
        btn_del = new javax.swing.JButton();
        jPanel4 = new javax.swing.JPanel();
        btn_save = new javax.swing.JButton();
        btn_lock = new javax.swing.JButton();
        lb_Information = new javax.swing.JLabel();

        setClosable(true);
        setTitle("Informações Do Pacote");
        addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                formFocusGained(evt);
            }
        });
        addInternalFrameListener(new javax.swing.event.InternalFrameListener() {
            public void internalFrameOpened(javax.swing.event.InternalFrameEvent evt) {
                formInternalFrameOpened(evt);
            }
            public void internalFrameClosing(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameClosed(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameIconified(javax.swing.event.InternalFrameEvent evt) {
                formInternalFrameIconified(evt);
            }
            public void internalFrameDeiconified(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameActivated(javax.swing.event.InternalFrameEvent evt) {
                formInternalFrameActivated(evt);
            }
            public void internalFrameDeactivated(javax.swing.event.InternalFrameEvent evt) {
                formInternalFrameDeactivated(evt);
            }
        });
        addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentShown(java.awt.event.ComponentEvent evt) {
                formComponentShown(evt);
            }
        });

        jPanel1.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder("Dados Do Pacote"));

        lb_Id.setText("ID");

        jLabel2.setText("Cliente");

        jLabel3.setText("Forma Rastreio");

        jLabel4.setText("Código Rastreio");

        txt_id.setEditable(false);

        cb_formasRastreio.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        btn_SearchClient.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/icons32x/search.png"))); // NOI18N
        btn_SearchClient.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_SearchClientActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(txt_id, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(txt_codigo)
                        .addComponent(cb_formasRastreio, 0, 170, Short.MAX_VALUE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lb_Id)
                            .addComponent(jLabel4)
                            .addComponent(jLabel3)
                            .addComponent(jLabel2)
                            .addComponent(txt_cliente, javax.swing.GroupLayout.PREFERRED_SIZE, 260, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btn_SearchClient)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lb_Id)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txt_id, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txt_cliente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel3))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(16, 16, 16)
                        .addComponent(btn_SearchClient)))
                .addGap(15, 15, 15)
                .addComponent(cb_formasRastreio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txt_codigo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder("Conteúdo Do Pacote"));

        tb_PackageItens.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Item", "QTD"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tb_PackageItens.getTableHeader().setReorderingAllowed(false);
        jScrollPane1.setViewportView(tb_PackageItens);

        btn_add.setText("Adicionar Item");
        btn_add.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_addActionPerformed(evt);
            }
        });

        btn_edit.setText("Editar Item");
        btn_edit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_editActionPerformed(evt);
            }
        });

        btn_del.setText("Excluir Item");
        btn_del.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_delActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 570, Short.MAX_VALUE)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(btn_add)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btn_edit)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btn_del)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btn_add)
                    .addComponent(btn_edit)
                    .addComponent(btn_del))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        btn_save.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        btn_save.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/icons48x/accept.png"))); // NOI18N
        btn_save.setText("Salvar Alterações");
        btn_save.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_saveActionPerformed(evt);
            }
        });

        btn_lock.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btn_lockMouseClicked(evt);
            }
        });
        btn_lock.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_lockActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btn_lock, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(58, 58, 58)
                .addComponent(lb_Information)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btn_save)
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(btn_lock, javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(btn_save)))
                .addGap(27, 27, 27))
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addComponent(lb_Information)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btn_SearchClientActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_SearchClientActionPerformed
        ClientsChooserIFGUI viewClients = new ClientsChooserIFGUI(operacao);
        this.getParent().add(viewClients);
        viewClients.toFront();
        
        viewClients.setVisible(true);
    }//GEN-LAST:event_btn_SearchClientActionPerformed

    private void btn_addActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_addActionPerformed
        if (checkFields() == true) {
            if (txt_id.getText().equals("")) {
                if (addPackageToDB() != 0) {
                    lb_Id.setVisible(true);
                    txt_id.setVisible(true);
                }
                JOptionPane.showMessageDialog(this, "Pacote Criado Com Sucesso!");
            }
            Pacotes p = fieldsToOBJ(cliente);
            String operacao = "Add";
            viewPic = new PackageItemChooserIFGUI(null, true, operacao, p);
            this.getParent().add(viewPic);
            viewPic.toFront();
            viewPic.setVisible(true);
        } else {
            JOptionPane.showMessageDialog(this, "Preencha Os Dados Do Pacote Primeiro!: ");
        }

    }//GEN-LAST:event_btn_addActionPerformed

    private void btn_editActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_editActionPerformed
        if (checkFields() == true) {
            String operacao = "Edit";
            viewPic = new PackageItemChooserIFGUI(null, true, operacao, this.pacote, addToItemOBJ(operacao));
            this.getParent().add(viewPic);
            viewPic.toFront();
            viewPic.setVisible(true);
        } else {
            JOptionPane.showMessageDialog(this, "Preencha Os Dados Do Pacote Primeiro!: ");
        }

    }//GEN-LAST:event_btn_editActionPerformed

    private void btn_delActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_delActionPerformed
        if (checkFields() == true) {
            removeItem();
        } else {
            JOptionPane.showMessageDialog(this, "Preencha Os Dados Do Pacote Primeiro!");
        }
    }//GEN-LAST:event_btn_delActionPerformed

    private void btn_saveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_saveActionPerformed
        if (checkFields() == true) {
            doOperation(operacao);
        } else {
            JOptionPane.showMessageDialog(this, "Preencha Os Dados Do Pacote Primeiro!");
        }

    }//GEN-LAST:event_btn_saveActionPerformed

    private void btn_lockMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_lockMouseClicked

    }//GEN-LAST:event_btn_lockMouseClicked

    private void btn_lockActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_lockActionPerformed
        changeLock();
    }//GEN-LAST:event_btn_lockActionPerformed

    private void formFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_formFocusGained
        fill_tbPackageItens(pacote);
    }//GEN-LAST:event_formFocusGained

    private void formComponentShown(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_formComponentShown
    }//GEN-LAST:event_formComponentShown

    private void formInternalFrameActivated(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameActivated
        try {
            if (!txt_id.getText().equals("")) {
                fill_tbPackageItens(pacote);
            }
        } catch (Exception e) {
            System.out.println("erro: " + e);
        }
        

    }//GEN-LAST:event_formInternalFrameActivated

    private void formInternalFrameOpened(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameOpened

    }//GEN-LAST:event_formInternalFrameOpened

    private void formInternalFrameDeactivated(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameDeactivated
        try {
            super.setSelected(true);
            super.toFront();
            super.setVisible(true);
        } catch (PropertyVetoException ex) {
            Logger.getLogger(PackageItemChooserIFGUI.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_formInternalFrameDeactivated

    private void formInternalFrameIconified(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameIconified
        // TODO add your handling code here:
    }//GEN-LAST:event_formInternalFrameIconified
    
    public Pacotes getPacote() {
        return pacote;
    }
    
    public void setPacote(Pacotes pacote) {
        this.pacote = pacote;
    }
    
    public static javax.swing.JTable getTb_PackageItens() {
        return tb_PackageItens;
    }
    
    public static void addItemToTbPackageItens(javax.swing.JTable aTb_PackageItens) {
        tb_PackageItens = aTb_PackageItens;
        
    }
    
    public String getOperacao() {
        return operacao;
    }
    
    public void setOperacao(String operacao) {
        this.operacao = operacao;
    }
    
    public String getMetodo() {
        return metodo;
    }
    
    public void setMetodo(String metodo) {
        this.metodo = metodo;
    }
    
    public Clientes getCliente() {
        return cliente;
    }
    
    public static void setCliente(Clientes acliente) {
        cliente = acliente;
    }


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn_SearchClient;
    private javax.swing.JButton btn_add;
    private javax.swing.JButton btn_del;
    private javax.swing.JButton btn_edit;
    private javax.swing.JButton btn_lock;
    private javax.swing.JButton btn_save;
    private javax.swing.JComboBox<String> cb_formasRastreio;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel1;
    private static javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    public javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lb_Id;
    private javax.swing.JLabel lb_Information;
    public static javax.swing.JTable tb_PackageItens;
    private static javax.swing.JTextField txt_cliente;
    private javax.swing.JTextField txt_codigo;
    private javax.swing.JTextField txt_id;
    // End of variables declaration//GEN-END:variables
}
