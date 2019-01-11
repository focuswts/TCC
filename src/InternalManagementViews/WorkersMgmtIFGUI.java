/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package InternalManagementViews;

import InternalDialogs.WorkersOpsIFGUI;
import InternalDialogs.contatosModalIF;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import model.bean.Cargos;
import model.bean.Funcionarios;
import model.dao.FuncionariosDAO;

/**
 *
 * @author focuswts
 */
public class WorkersMgmtIFGUI extends javax.swing.JInternalFrame {

    /**
     * Creates new form WorkersMgmtIFGUI
     */
    private Funcionarios contato = null;

    public WorkersMgmtIFGUI() {
        initComponents();
        fill_tbWorkers();
    }

    private void fill_tbWorkers() {

        DefaultTableModel aModel = (DefaultTableModel) tb_Workers.getModel();
        try {
            FuncionariosDAO wDAO = new FuncionariosDAO();
            ArrayList<Funcionarios> Funcionarios = null;
            Funcionarios = wDAO.findAll();

            int numLinhas = aModel.getRowCount();
            Cargos j = null;
            for (int i = 0; i < numLinhas; i++) {
                aModel.removeRow(0);
            }
            for (int i = 0; i < Funcionarios.size(); i++) {
                Object[] rowDados = new Object[5];
                rowDados[0] = Funcionarios.get(i).getNome();
                rowDados[1] = Funcionarios.get(i).getIdCargo().getFuncao();
                rowDados[2] = Funcionarios.get(i).getCpf();
                rowDados[3] = Funcionarios.get(i).getRg();
                aModel.addRow(rowDados);

            }

            this.tb_Workers.setModel(aModel);
        } catch (Exception e) {
            System.out.println("Erro Ao Preencher TBWorkers: " + e);
        }

    }

    private void viewContatos(String objContato) {
        try {
            String nome = tb_Workers.getValueAt(tb_Workers.getSelectedRow(), 0).toString();
            Funcionarios worker = new Funcionarios();
            worker.setNome(nome);
            contatosModalIF viewContatos = new contatosModalIF(null, true, worker, objContato);
            this.getParent().add(viewContatos);
            viewContatos.toFront();
            viewContatos.setWorker(worker);
            viewContatos.setVisible(true);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro Ao Buscar Contatos Do Funcionário!: " + e);
        }
    }

    private void viewOpsWorkers(String operacao) {
        try {

            if (operacao.equals("Edit")) {
                String nome = tb_Workers.getValueAt(tb_Workers.getSelectedRow(), 0).toString();
                Funcionarios worker = new Funcionarios();
                worker.setNome(nome);
                WorkersOpsIFGUI viewOpWorkers = new WorkersOpsIFGUI(null, true, operacao, worker);
                this.getParent().add(viewOpWorkers);
                viewOpWorkers.toFront();
                viewOpWorkers.setOperacao(operacao);
                viewOpWorkers.setWorker(worker);
                viewOpWorkers.setVisible(true);
            }
            if (operacao.equals("Add")) {
                System.out.println("ADD");
                WorkersOpsIFGUI viewOpWorkers = new WorkersOpsIFGUI(null, true, operacao);
                this.getParent().add(viewOpWorkers);
                viewOpWorkers.toFront();
                viewOpWorkers.setOperacao(operacao);
                viewOpWorkers.setVisible(true);
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro Ao Editar Funcionário!: " + e);
        }
    }

    private int getWorkerId() {
        int id = 0;
        FuncionariosDAO wDAO = new FuncionariosDAO();
        try {
            String nome = tb_Workers.getValueAt(tb_Workers.getSelectedRow(), 0).toString();
            id = wDAO.getIdByNome(nome);
            return id;
        } catch (Exception e) {
            System.out.println("Erro Ao Pegar Id Cliente: " + e);
            return 0;
        }
    }

    private void deleteWorker(int id) {
        Funcionarios w = new Funcionarios();
        FuncionariosDAO wDAO = new FuncionariosDAO();
        try {
            w.setId(id);
            if (wDAO.delete(w) == false) {
                JOptionPane.showMessageDialog(this, "Funcionário Possui Relacionamento,Exclua O Mesmo Primeiro!");
            } else {
                JOptionPane.showMessageDialog(this, "Funcionário Excluído Com Sucesso!");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro Ao Excluir Funcionário: " + e);
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
        tb_Workers = new javax.swing.JTable();
        jPanel3 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        btn_Contatos = new javax.swing.JButton();
        btn_Delete = new javax.swing.JButton();
        btn_Add = new javax.swing.JButton();
        btn_Edit = new javax.swing.JButton();

        setClosable(true);
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

        jPanel1.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        tb_Workers.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        tb_Workers.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Nome", "Cargo", "CPF", "RG"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tb_Workers.getTableHeader().setReorderingAllowed(false);
        jScrollPane1.setViewportView(tb_Workers);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 6, Short.MAX_VALUE)
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 174, Short.MAX_VALUE)
        );

        jPanel2.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        btn_Contatos.setText("Ver Dados");
        btn_Contatos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_ContatosActionPerformed(evt);
            }
        });

        btn_Delete.setText("Excluir Funcionário");
        btn_Delete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_DeleteActionPerformed(evt);
            }
        });

        btn_Add.setText("Adicionar Funcionário");
        btn_Add.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_AddActionPerformed(evt);
            }
        });

        btn_Edit.setText("Editar Funcionário");
        btn_Edit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_EditActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btn_Contatos)
                .addGap(18, 18, 18)
                .addComponent(btn_Edit)
                .addGap(18, 18, 18)
                .addComponent(btn_Delete)
                .addGap(18, 18, 18)
                .addComponent(btn_Add)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btn_Contatos)
                    .addComponent(btn_Delete)
                    .addComponent(btn_Add)
                    .addComponent(btn_Edit))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 220, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 307, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btn_ContatosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_ContatosActionPerformed
        String objContato = "View";
        viewContatos(objContato);
    }//GEN-LAST:event_btn_ContatosActionPerformed

    private void btn_DeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_DeleteActionPerformed
        if (!tb_Workers.getSelectionModel().isSelectionEmpty()) {
            deleteWorker(getWorkerId());
            fill_tbWorkers();
        } else {
            JOptionPane.showMessageDialog(this, "Selecione Uma Linha!");
        }

    }//GEN-LAST:event_btn_DeleteActionPerformed

    private void btn_AddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_AddActionPerformed
        String operacao = "Add";
        viewOpsWorkers(operacao);
    }//GEN-LAST:event_btn_AddActionPerformed

    private void btn_EditActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_EditActionPerformed
        if (!tb_Workers.getSelectionModel().isSelectionEmpty()) {
            String operacao = "Edit";
            viewOpsWorkers(operacao);
        } else {
            JOptionPane.showMessageDialog(this, "Selecione Uma Linha!");
        }

    }//GEN-LAST:event_btn_EditActionPerformed

    private void formInternalFrameActivated(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameActivated
fill_tbWorkers();
    }//GEN-LAST:event_formInternalFrameActivated

    public Funcionarios getContato() {
        return contato;
    }

    public void setContato(Funcionarios contato) {
        this.contato = contato;
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn_Add;
    private javax.swing.JButton btn_Contatos;
    private javax.swing.JButton btn_Delete;
    private javax.swing.JButton btn_Edit;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tb_Workers;
    // End of variables declaration//GEN-END:variables
}
