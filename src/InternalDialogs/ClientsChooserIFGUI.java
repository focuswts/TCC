/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package InternalDialogs;

import InternalManagementViews.PackageInfoIFGUI;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import model.bean.Clientes;
import model.dao.ClientesDAO;

/**
 *
 * @author focuswts
 */
public class ClientsChooserIFGUI extends javax.swing.JInternalFrame {

    public static Clientes getClient() {
        return client;
    }

    public static void setClient(Clientes aClient) {
        client = aClient;
    }

    /**
     * Creates new form ClientsChooserIFGUI
     */
    private static String operacao;
    private static Clientes client;

    public ClientsChooserIFGUI() {
        initComponents();
        fill_tbClients();
        System.out.println(this.operacao);
    }

    public ClientsChooserIFGUI(String operacao) {
        initComponents();
        this.operacao = operacao;
        System.out.println(this.operacao);
        fill_tbClients();
    }

    private void fill_tbClients() {

        DefaultTableModel aModel = (DefaultTableModel) tb_Clients.getModel();
        try {

            ClientesDAO cDAO = new ClientesDAO();
            ArrayList<Clientes> Clientes = null;
            Clientes = cDAO.findAll();

            int numLinhas = aModel.getRowCount();

            for (int i = 0; i < numLinhas; i++) {
                aModel.removeRow(0);
            }

            for (int i = 0; i < Clientes.size(); i++) {
                Object[] rowDados = new Object[4];
                rowDados[0] = Clientes.get(i).getId();
                rowDados[1] = Clientes.get(i).getNome();
                rowDados[2] = Clientes.get(i).getCpf();
                rowDados[3] = Clientes.get(i).getRg();
                aModel.addRow(rowDados);

            }

            this.tb_Clients.setModel(aModel);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro Ao Preencher Clientes!: " + e);
        }

    }

    private void fill_tbClients(String filtro) {

        DefaultTableModel aModel = (DefaultTableModel) tb_Clients.getModel();
        try {

            ClientesDAO cDAO = new ClientesDAO();
            ArrayList<Clientes> Clientes = null;
            Clientes = cDAO.findAllWhere(filtro);

            int numLinhas = aModel.getRowCount();

            for (int i = 0; i < numLinhas; i++) {
                aModel.removeRow(0);
            }

            for (int i = 0; i < Clientes.size(); i++) {
                Object[] rowDados = new Object[4];
                rowDados[0] = Clientes.get(i).getId();
                rowDados[1] = Clientes.get(i).getNome();
                rowDados[2] = Clientes.get(i).getCpf();
                rowDados[3] = Clientes.get(i).getRg();
                aModel.addRow(rowDados);

            }

            this.tb_Clients.setModel(aModel);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro Ao Preencher Clientes!: " + e);
        }

    }

    private void selectClient() {
        try {
            Clientes c = new Clientes();
            int idClient = Integer.valueOf(tb_Clients.getValueAt(tb_Clients.getSelectedRow(), 0).toString());
            String nome = tb_Clients.getValueAt(tb_Clients.getSelectedRow(), 1).toString();
            String cpf = tb_Clients.getValueAt(tb_Clients.getSelectedRow(), 2).toString();
            String rg = tb_Clients.getValueAt(tb_Clients.getSelectedRow(), 3).toString();

            c.setId(idClient);
            c.setNome(nome);
            c.setCpf(cpf);
            c.setRg(rg);

            this.client = c;

            addClientToPackage();
            System.out.println(this.operacao);

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro Ao Selecionar Cliente! : " + e);
        }

    }

    private void selectClientToUserOps() {
        try {
            Clientes c = new Clientes();
            int idClient = Integer.valueOf(tb_Clients.getValueAt(tb_Clients.getSelectedRow(), 0).toString());
            String nome = tb_Clients.getValueAt(tb_Clients.getSelectedRow(), 1).toString();
            String cpf = tb_Clients.getValueAt(tb_Clients.getSelectedRow(), 2).toString();
            String rg = tb_Clients.getValueAt(tb_Clients.getSelectedRow(), 3).toString();

            c.setId(idClient);
            c.setNome(nome);
            c.setCpf(cpf);
            c.setRg(rg);

            this.client = c;
            addClientToUsersOps();
            System.out.println(this.operacao);

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro Ao Selecionar Cliente! : " + e);
        }

    }

    private void addClientToUsersOps() {
        try {
            UsersOpsIFGUI.client = this.client;
            UsersOpsIFGUI.setClient(this.client);
            UsersOpsIFGUI.unLockClientFields();
            UsersOpsIFGUI.fillClientsFields();
        } catch (Exception e) {
            System.out.println("Erro Ao Enviar Client Para UserOps: " + e);
        }
    }

    private void addClientToPackage() {
        try {
            PackageInfoIFGUI.cliente = this.client;
            PackageInfoIFGUI.setCliente(this.client);
            PackageInfoIFGUI.setClientJF();
        } catch (Exception e) {
            System.out.println("Erro Ao Enviar Client Para UserOps: " + e);
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

        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tb_Clients = new javax.swing.JTable();
        jPanel2 = new javax.swing.JPanel();
        txt_NomeCliente = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        btn_Choose = new javax.swing.JButton();

        setClosable(true);

        jPanel1.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        tb_Clients.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "ID", "Nome", "CPF", "RG"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tb_Clients.getTableHeader().setReorderingAllowed(false);
        jScrollPane1.setViewportView(tb_Clients);

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder("Filtro De Busca"));

        txt_NomeCliente.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N

        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/icons32x/search.png"))); // NOI18N
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jLabel1.setText("Informe O Nome Do Cliente");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txt_NomeCliente)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jButton1)
                .addGap(14, 14, 14))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap(18, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txt_NomeCliente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(17, 17, 17))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addComponent(jButton1)
                        .addContainerGap())))
        );

        jPanel3.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        btn_Choose.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        btn_Choose.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/icons48x/accept.png"))); // NOI18N
        btn_Choose.setText("Selecionar Cliente");
        btn_Choose.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_ChooseActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btn_Choose)
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addComponent(btn_Choose)
                .addGap(0, 6, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 596, Short.MAX_VALUE)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 153, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(80, 80, 80))
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
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 356, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        String filtro = txt_NomeCliente.getText();
        fill_tbClients(filtro);
    }//GEN-LAST:event_jButton1ActionPerformed

    private void btn_ChooseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_ChooseActionPerformed
        if (this.operacao.equals("UserOps")) {
            selectClientToUserOps();
        } else {
            if (this.operacao.equals("Add")) {
                selectClient();
            }
        }
        this.dispose();
    }//GEN-LAST:event_btn_ChooseActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn_Choose;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tb_Clients;
    private javax.swing.JTextField txt_NomeCliente;
    // End of variables declaration//GEN-END:variables

    public String getOperacao() {
        return operacao;
    }

    public void setOperacao(String operacao) {
        this.operacao = operacao;
    }
}
