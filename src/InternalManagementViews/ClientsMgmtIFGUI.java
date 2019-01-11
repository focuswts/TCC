/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package InternalManagementViews;

import InternalDialogs.ClientsOpsIFGUI;
import InternalDialogs.contatosModalIF;
import java.util.ArrayList;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import model.bean.Clientes;
import model.bean.Cargos;
import model.dao.ClientesDAO;

/**
 *
 * @author focuswts
 */
public class ClientsMgmtIFGUI extends javax.swing.JInternalFrame {

    /**
     * Creates new form ClientsMgmtIFGUI
     */
    public ClientsMgmtIFGUI() {
        initComponents();
        fill_tbClients();
    }

    private void fill_tbClients() {

        DefaultTableModel aModel = (DefaultTableModel) tb_Clients.getModel();
        try {
            ClientesDAO cDAO = new ClientesDAO();
            ArrayList<Clientes> Clientes = null;
            Clientes = cDAO.findAll();

            int numLinhas = aModel.getRowCount();
            Cargos j = null;
            for (int i = 0; i < numLinhas; i++) {
                aModel.removeRow(0);
            }
            for (int i = 0; i < Clientes.size(); i++) {
                Object[] rowDados = new Object[5];
                rowDados[0] = Clientes.get(i).getNome();
                rowDados[1] = Clientes.get(i).getCpf();
                rowDados[2] = Clientes.get(i).getRg();
                aModel.addRow(rowDados);

            }

            this.tb_Clients.setModel(aModel);
        } catch (Exception e) {
            System.out.println("Erro Ao Preencher JTable: " + e);
        }

    }

    private void fill_tbClientsFilter(ArrayList<Clientes> Clientes) {

        DefaultTableModel aModel = (DefaultTableModel) tb_Clients.getModel();
        try {

            int numLinhas = aModel.getRowCount();
            for (int i = 0; i < numLinhas; i++) {
                aModel.removeRow(0);
            }
            for (int i = 0; i < Clientes.size(); i++) {
                Object[] rowDados = new Object[5];
                rowDados[0] = Clientes.get(i).getNome();
                rowDados[1] = Clientes.get(i).getCpf();
                rowDados[2] = Clientes.get(i).getRg();
                aModel.addRow(rowDados);

            }

            this.tb_Clients.setModel(aModel);
        } catch (Exception e) {
            System.out.println("Erro Ao Preencher JTable: " + e);
        }

    }

    private void viewContatos(String objContato) {
        try {
            String nome = tb_Clients.getValueAt(tb_Clients.getSelectedRow(), 0).toString();
            Clientes client = new Clientes();
            client.setNome(nome);
            //  JOptionPane.showMessageDialog(this, "");
            contatosModalIF viewContatos = new contatosModalIF(null, true, client, objContato);
            this.getParent().add(viewContatos);
            viewContatos.toFront();
            viewContatos.setClient(client);
            viewContatos.setVisible(true);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro Ao Buscar Contatos Do Cliente!");
        }

    }

    private int getClientId() {
        int id = 0;
        ClientesDAO cDAO = new ClientesDAO();
        try {
            String nome = tb_Clients.getValueAt(tb_Clients.getSelectedRow(), 0).toString();
            id = cDAO.getIdByNome(nome);
            return id;
        } catch (Exception e) {
            System.out.println("Erro Ao Pegar Id Cliente: " + e);
            return 0;
        }
    }

    private boolean deleteCliente(int id) {
        Clientes c = new Clientes();
        ClientesDAO cDAO = new ClientesDAO();
        try {
            c.setId(id);
            if (cDAO.delete(c) == false) {
                JOptionPane.showMessageDialog(this, "Cliente Possui Pacote,Exclua O Pacote Primeiro!");
            } else {
                JOptionPane.showMessageDialog(this, "Cliente Excluído Com Sucesso!");
            }
            return true;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro Ao Excluir Cliente: " + e);
            return false;
        }

    }

    private void viewOpsClients(String operacao) {
        try {

            if (operacao.equals("Edit")) {
                String nome = tb_Clients.getValueAt(tb_Clients.getSelectedRow(), 0).toString();
                Clientes client = new Clientes();
                client.setNome(nome);
                String cpf = tb_Clients.getValueAt(tb_Clients.getSelectedRow(), 1).toString();
                client.setCpf(cpf);
                String rg = tb_Clients.getValueAt(tb_Clients.getSelectedRow(), 2).toString();
                client.setRg(rg);
                
                ClientsOpsIFGUI viewOpClients = new ClientsOpsIFGUI(null, true, operacao, client);
                this.getParent().add(viewOpClients);
                viewOpClients.toFront();
                viewOpClients.setOperacao(operacao);
                viewOpClients.setClient(client);
                viewOpClients.setVisible(true);
            }
            if (operacao.equals("Add")) {
                System.out.println("ADD");
                ClientsOpsIFGUI viewOpClients = new ClientsOpsIFGUI(null, true, operacao);
                this.getParent().add(viewOpClients);
                viewOpClients.toFront();
                viewOpClients.setOperacao(operacao);
                viewOpClients.setVisible(true);
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro Ao Editar Cliente!: " + e);
        }
    }

   

    private void filterSearch() {
        ClientesDAO cDAO = new ClientesDAO();
        try {

            String search = txt_Search.getText();

            ArrayList<Clientes> clientes = cDAO.findAll(search);

            fill_tbClientsFilter(clientes);

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro Ao Filtrar Os Resultados! " + e);
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel4 = new javax.swing.JPanel();
        jPanel6 = new javax.swing.JPanel();
        btn_viewDados = new javax.swing.JButton();
        btn_editClient = new javax.swing.JButton();
        btn_addClient = new javax.swing.JButton();
        btn_delClient = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tb_Clients = new javax.swing.JTable();
        jPanel1 = new javax.swing.JPanel();
        txt_Search = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();

        setClosable(true);
        setTitle("Gerenciamento De Clientes");
        addInternalFrameListener(new javax.swing.event.InternalFrameListener() {
            public void internalFrameOpened(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameClosing(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameClosed(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameIconified(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameDeiconified(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameActivated(javax.swing.event.InternalFrameEvent evt) {
                formInternalFrameActivated(evt);
            }
            public void internalFrameDeactivated(javax.swing.event.InternalFrameEvent evt) {
            }
        });

        jPanel4.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jPanel6.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Operações Disponíveis", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Dialog", 1, 14))); // NOI18N
        jPanel6.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jPanel6FocusGained(evt);
            }
        });

        btn_viewDados.setText("Ver Dados");
        btn_viewDados.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_viewDadosActionPerformed(evt);
            }
        });

        btn_editClient.setText("Editar Cliente");
        btn_editClient.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_editClientActionPerformed(evt);
            }
        });

        btn_addClient.setText("Adicionar Cliente");
        btn_addClient.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_addClientActionPerformed(evt);
            }
        });

        btn_delClient.setText("Excluir Cliente");
        btn_delClient.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_delClientActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btn_viewDados)
                .addGap(18, 18, 18)
                .addComponent(btn_delClient)
                .addGap(18, 18, 18)
                .addComponent(btn_editClient)
                .addGap(18, 18, 18)
                .addComponent(btn_addClient)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btn_viewDados)
                    .addComponent(btn_editClient)
                    .addComponent(btn_addClient)
                    .addComponent(btn_delClient))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jScrollPane1.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        tb_Clients.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null}
            },
            new String [] {
                "Nome", "CPF", "RG"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tb_Clients.getTableHeader().setReorderingAllowed(false);
        jScrollPane1.setViewportView(tb_Clients);

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("Barra De Busca"));

        txt_Search.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N

        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/icons48x/search.png"))); // NOI18N
        jButton1.setText("Pesquisar");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Noto Sans", 0, 14)); // NOI18N
        jLabel1.setText("Insira O Nome Do Cliente");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(txt_Search, javax.swing.GroupLayout.PREFERRED_SIZE, 455, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(180, 180, 180)
                .addComponent(jLabel1)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap(15, Short.MAX_VALUE)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton1)
                    .addComponent(txt_Search, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane1))
                .addContainerGap())
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 60, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btn_viewDadosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_viewDadosActionPerformed
        if (!tb_Clients.getSelectionModel().isSelectionEmpty()) {
            String objContato = "View";
            viewContatos(objContato);
        } else {
            JOptionPane.showMessageDialog(this, "Selecione Uma Linha!");
        }
    }//GEN-LAST:event_btn_viewDadosActionPerformed

    private void btn_editClientActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_editClientActionPerformed
        if (!tb_Clients.getSelectionModel().isSelectionEmpty()) {
            String operacao = "Edit";
            viewOpsClients(operacao);
        } else {
            JOptionPane.showMessageDialog(this, "Selecione Uma Linha!");
        }
    }//GEN-LAST:event_btn_editClientActionPerformed

    private void btn_addClientActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_addClientActionPerformed
        String operacao = "Add";
        viewOpsClients(operacao);
    }//GEN-LAST:event_btn_addClientActionPerformed

    private void btn_delClientActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_delClientActionPerformed
        if (!tb_Clients.getSelectionModel().isSelectionEmpty()) {
            deleteCliente(getClientId());
            fill_tbClients();
        } else {
            JOptionPane.showMessageDialog(this, "Selecione Uma Linha!");
        }
    }//GEN-LAST:event_btn_delClientActionPerformed

    private void jPanel6FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jPanel6FocusGained

    }//GEN-LAST:event_jPanel6FocusGained

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        if (txt_Search.getText().equals("")) {
            fill_tbClients();
        } else {
            filterSearch();
        }
    }//GEN-LAST:event_jButton1ActionPerformed

    private void formInternalFrameActivated(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameActivated
        fill_tbClients();
    }//GEN-LAST:event_formInternalFrameActivated


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn_addClient;
    private javax.swing.JButton btn_delClient;
    private javax.swing.JButton btn_editClient;
    private javax.swing.JButton btn_viewDados;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tb_Clients;
    private javax.swing.JTextField txt_Search;
    // End of variables declaration//GEN-END:variables
}
