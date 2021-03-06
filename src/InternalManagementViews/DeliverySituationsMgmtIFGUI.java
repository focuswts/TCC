/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package InternalManagementViews;

import InternalDialogs.DeliverySituationsOpsIFGUI;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import model.bean.SituacoesEntrega;
import model.dao.SituacoesEntregaDAO;

/**
 *
 * @author focuswts
 */
public class DeliverySituationsMgmtIFGUI extends javax.swing.JInternalFrame {

    /**
     * Creates new form BrandsMgmtIFGUI
     */
    public DeliverySituationsMgmtIFGUI() {
        initComponents();
        fill_tbDeliverySituations();
    }

    private void fill_tbDeliverySituations() {

        DefaultTableModel aModel = (DefaultTableModel) tb_DeliverySituation.getModel();
        try {
            SituacoesEntregaDAO seDAO = new SituacoesEntregaDAO();
            ArrayList<SituacoesEntrega> Situacoes = null;
            Situacoes = seDAO.findAll();

            int numLinhas = aModel.getRowCount();
            for (int i = 0; i < numLinhas; i++) {
                aModel.removeRow(0);
            }
            for (int i = 0; i < Situacoes.size(); i++) {
                Object[] rowDados = new Object[1];
                rowDados[0] = Situacoes.get(i).getSituacao();
                aModel.addRow(rowDados);
            }
            this.tb_DeliverySituation.setModel(aModel);
        } catch (Exception e) {
            System.out.println("Erro Ao Preencher JTable: " + e);
        }

    }

    private SituacoesEntrega fieldsToOBJ() {
        SituacoesEntrega se = new SituacoesEntrega();
        SituacoesEntregaDAO seDAO = new SituacoesEntregaDAO();
        try {
            String situacao = tb_DeliverySituation.getValueAt(tb_DeliverySituation.getSelectedRow(), 0).toString();
            se = seDAO.getOBJFromSituacao(situacao);
            return se;
        } catch (Exception e) {
            System.out.println("Erro AO Gerar OBJ: " + e);
            return null;
        }

    }

    private void deleteSituation() {
        SituacoesEntrega se = new SituacoesEntrega();
        SituacoesEntregaDAO seDAO = new SituacoesEntregaDAO();
      se = seDAO.getOBJFromSituacao(tb_DeliverySituation.getValueAt(tb_DeliverySituation.getSelectedRow(), 0).toString());
        try {
            //c.setId(idCargo);
            if (seDAO.delete(se) == true) {
                JOptionPane.showMessageDialog(this, "Situação Excluída Com Sucesso!");
            }
            fill_tbDeliverySituations();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro Ao Deletar Situação: " + e);
        }

    }

    private void viewOpsSituations(String operacao) {
        DeliverySituationsOpsIFGUI viewSituations;
        try {
            if (operacao.equals("Add")) {
                viewSituations = new DeliverySituationsOpsIFGUI(null, true, operacao);
                this.getParent().add(viewSituations);
                viewSituations.toFront();
                viewSituations.setVisible(true);
            }
            if (operacao.equals("Edit")) {
                viewSituations = new DeliverySituationsOpsIFGUI(null, true, operacao, fieldsToOBJ());
                this.getParent().add(viewSituations);
                viewSituations.toFront();
                viewSituations.setVisible(true);
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro Ao Abrir View Ops Situations: " + e);
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
        jLabel1 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tb_DeliverySituation = new javax.swing.JTable();
        jPanel2 = new javax.swing.JPanel();
        btn_Add = new javax.swing.JButton();
        btn_Edit = new javax.swing.JButton();
        btn_Delete = new javax.swing.JButton();

        setClosable(true);
        setTitle("Menu Operações Situações Entrega");
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

        jLabel1.setText("Lista De Situações De Entrega");

        tb_DeliverySituation.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null},
                {null},
                {null},
                {null}
            },
            new String [] {
                "Situação"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tb_DeliverySituation.getTableHeader().setReorderingAllowed(false);
        jScrollPane1.setViewportView(tb_DeliverySituation);

        btn_Add.setText("Adicionar Situação");
        btn_Add.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_AddActionPerformed(evt);
            }
        });

        btn_Edit.setText("Editar Situação");
        btn_Edit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_EditActionPerformed(evt);
            }
        });

        btn_Delete.setText("Excluir Situação");
        btn_Delete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_DeleteActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btn_Add)
                .addGap(18, 18, 18)
                .addComponent(btn_Edit)
                .addGap(18, 18, 18)
                .addComponent(btn_Delete)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btn_Add)
                    .addComponent(btn_Edit)
                    .addComponent(btn_Delete))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                    .addComponent(jPanel2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(87, 87, 87)
                .addComponent(jLabel1)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 198, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
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
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btn_AddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_AddActionPerformed
        String operacao = "Add";
        viewOpsSituations(operacao);
    }//GEN-LAST:event_btn_AddActionPerformed

    private void btn_EditActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_EditActionPerformed
        if (!tb_DeliverySituation.getSelectionModel().isSelectionEmpty()) {
            String operacao = "Edit";
            viewOpsSituations(operacao);
        } else {
            JOptionPane.showMessageDialog(this, "Selecione Uma Linha!");
        }

    }//GEN-LAST:event_btn_EditActionPerformed

    private void btn_DeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_DeleteActionPerformed
        if (!tb_DeliverySituation.getSelectionModel().isSelectionEmpty()) {
            deleteSituation();
        } else {
            JOptionPane.showMessageDialog(this, "Selecione Uma Linha!");
        }

    }//GEN-LAST:event_btn_DeleteActionPerformed

    private void formInternalFrameActivated(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameActivated
        fill_tbDeliverySituations();
    }//GEN-LAST:event_formInternalFrameActivated


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn_Add;
    private javax.swing.JButton btn_Delete;
    private javax.swing.JButton btn_Edit;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tb_DeliverySituation;
    // End of variables declaration//GEN-END:variables
}
