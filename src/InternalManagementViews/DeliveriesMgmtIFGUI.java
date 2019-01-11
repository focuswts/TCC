/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package InternalManagementViews;

import InternalDialogs.DeliveriesOpsIFGUI;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import model.bean.Entregas;
import model.bean.SituacoesEntrega;
import model.bean.Rotas;
import model.bean.RotasAux;
import model.dao.EntregasDAO;
import model.dao.SituacoesEntregaDAO;
import model.dao.RotasAuxDAO;

/**
 *
 * @author focuswts
 */
public class DeliveriesMgmtIFGUI extends javax.swing.JInternalFrame {

    /**
     * Creates new form DeliveriesMgmtIFGUI
     */
    private static String tipoBotao;

    public DeliveriesMgmtIFGUI() {
        initComponents();
        fill_tbDeliveries("Ativo");
        this.tipoBotao = "Inativo";
    }

    private void fill_tbDeliveries(String status) {

        DefaultTableModel aModel = (DefaultTableModel) tb_Deliveries.getModel();
        try {
            EntregasDAO dDAO = new EntregasDAO();
            ArrayList<Entregas> Entregas = null;
            Entregas = dDAO.findAllStatus(status);

            int numLinhas = aModel.getRowCount();

            for (int i = 0; i < numLinhas; i++) {
                aModel.removeRow(0);
            }

            for (int i = 0; i < Entregas.size(); i++) {
                Object[] rowDados = new Object[4];
                rowDados[0] = Entregas.get(i).getIdRotaAux().getIdPacotes().getCodigoRastreio();
                rowDados[1] = Entregas.get(i).getData();
                rowDados[2] = Entregas.get(i).getSituacao();
                rowDados[3] = Entregas.get(i).getLocal();
                aModel.addRow(rowDados);

            }

            this.tb_Deliveries.setModel(aModel);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro Ao Preencher TB Packages! " + e);
        }

    }

    private SituacoesEntrega getDeliverySituationsOBJFromSituacao(String situacao) {
        SituacoesEntrega ds = null;
        SituacoesEntregaDAO dsDAO = new SituacoesEntregaDAO();
        try {
            ds = dsDAO.getOBJFromSituacao(situacao);
        } catch (Exception e) {
            System.out.println("Erro Ao Pegar OBJ DeliverySituation: " + e);
            return null;
        }
        return ds;
    }

    private Entregas getDeliveryOBJFromCodRastreio(String codigoRastreio) {
        EntregasDAO dDAO = new EntregasDAO();
        Entregas delivery = null;
        try {
            delivery = dDAO.findAllWhereGetOBJ(codigoRastreio);

        } catch (Exception e) {
            System.out.println("Erro Ao Pegar Id Delviery: " + e);
        }
        return delivery;
    }

    private void deleteDelivery() {
        EntregasDAO dDAO = new EntregasDAO();
        try {
            String codigoRastreio = (String) tb_Deliveries.getValueAt(tb_Deliveries.getSelectedRow(), 0);
            String op = "";

            Entregas e = getDeliveryOBJFromCodRastreio(codigoRastreio);
            e.setStatus("Inativo");

            if (dDAO.disable(e, codigoRastreio)) {
                JOptionPane.showMessageDialog(this, "Entrega Arquivada Com Sucesso!");
            }
            fill_tbDeliveries("Ativo");

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro Ao Excluir Entrega: " + e);
        }

    }



    private Rotas getRoute(String codigoRastreio) {
        Rotas r = new Rotas();
        RotasAuxDAO rauxDAO = new RotasAuxDAO();
        ArrayList<RotasAux> rotasAux = rauxDAO.findAllWhere(codigoRastreio);
        try {
            System.out.println(rotasAux.size());
            if (rotasAux.size() > 0) {
                for (int i = 0; i < rotasAux.size(); i++) {

                    int idRota = rotasAux.get(i).getIdRota().getId();
                    String remetente = rotasAux.get(i).getIdRota().getRemetente();
                    String destinatario = rotasAux.get(i).getIdRota().getDestinatario();

                    r.setId(idRota);
                    r.setRemetente(remetente);
                    r.setDestinatario(destinatario);
                    return r;
                }

            }

        } catch (Exception e) {
            System.out.println("Erro Ao Pegar Rota: " + e);
        }
        return null;
    }

    private Rotas getRoute(String codigoRastreio, RotasAux ra) {
        Rotas r = new Rotas();
        RotasAuxDAO rauxDAO = new RotasAuxDAO();
        ArrayList<RotasAux> rotasAux = rauxDAO.findAllWhere(codigoRastreio);
        try {
            System.out.println(rotasAux.size());
            if (rotasAux.size() > 0) {
                for (int i = 0; i < rotasAux.size(); i++) {

                    int idRota = rotasAux.get(i).getIdRota().getId();
                    String remetente = rotasAux.get(i).getIdRota().getRemetente();
                    String destinatario = rotasAux.get(i).getIdRota().getDestinatario();

                    ra.setId(rotasAux.get(i).getId());

                    r.setId(idRota);
                    r.setRemetente(remetente);
                    r.setDestinatario(destinatario);
                    return r;
                }

            }

        } catch (Exception e) {
            System.out.println("Erro Ao Pegar Rota: " + e);
        }
        return null;
    }

   

    private void viewPackages() {
        try {
            PackageMgmtIFGUI viewPackages = new PackageMgmtIFGUI();
            this.getParent().add(viewPackages);
            viewPackages.toFront();
            viewPackages.setVisible(true);
            this.dispose();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro Ao Abrir Tela Pacotes: " + e);

        }

    }

    private void viewDeliveriesOps() {
        try {
            String operacao = "Edit";
            String codigoRastreio = (String) tb_Deliveries.getValueAt(tb_Deliveries.getSelectedRow(), 0);
            String situacao = (String) tb_Deliveries.getValueAt(tb_Deliveries.getSelectedRow(), 2);

            Entregas d = getDeliveryOBJFromCodRastreio(codigoRastreio);
            SituacoesEntrega ds = getDeliverySituationsOBJFromSituacao(situacao);

            DeliveriesOpsIFGUI viewDeliveries = new DeliveriesOpsIFGUI(null, true, operacao, d, ds);
            this.getParent().add(viewDeliveries);
            viewDeliveries.toFront();
            viewDeliveries.setVisible(true);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro Ao Editar Entrega: " + e);
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
        jPanel2 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tb_Deliveries = new javax.swing.JTable();
        jPanel3 = new javax.swing.JPanel();
        btn_Add = new javax.swing.JButton();
        btn_Update = new javax.swing.JButton();
        btn_Delete = new javax.swing.JButton();
        btn_OldDeliveries = new javax.swing.JButton();

        setClosable(true);
        setResizable(true);
        setTitle("Gerenciamento De Entregas");
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

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder("Lista De Pacotes Em Rota"));

        tb_Deliveries.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "COD Rastreio", "Data", "Situação", "Local"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, true, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tb_Deliveries.getTableHeader().setReorderingAllowed(false);
        jScrollPane1.setViewportView(tb_Deliveries);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 607, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 187, Short.MAX_VALUE)
                .addContainerGap())
        );

        btn_Add.setText("Adicionar Entrega");
        btn_Add.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_AddActionPerformed(evt);
            }
        });

        btn_Update.setText("Atualizar Situação");
        btn_Update.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_UpdateActionPerformed(evt);
            }
        });

        btn_Delete.setText("Excluir Entrega");
        btn_Delete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_DeleteActionPerformed(evt);
            }
        });

        btn_OldDeliveries.setText("Ver Entregas Antigas");
        btn_OldDeliveries.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_OldDeliveriesActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btn_Add)
                .addGap(18, 18, 18)
                .addComponent(btn_Update)
                .addGap(18, 18, 18)
                .addComponent(btn_Delete)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btn_OldDeliveries)
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btn_Add)
                    .addComponent(btn_Update)
                    .addComponent(btn_Delete)
                    .addComponent(btn_OldDeliveries))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
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
                .addContainerGap(40, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btn_AddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_AddActionPerformed
        int dialog = JOptionPane.showConfirmDialog(this, "Deseja Adicionar Nova Entrega?", "AVISO", JOptionPane.YES_NO_OPTION);
        if (dialog == 0) {
            viewPackages();
        }
    }//GEN-LAST:event_btn_AddActionPerformed

    private void btn_UpdateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_UpdateActionPerformed
        if (!tb_Deliveries.getSelectionModel().isSelectionEmpty()) {
            viewDeliveriesOps();
        } else {
            JOptionPane.showMessageDialog(this, "Selecione Uma Linha!");
        }
    }//GEN-LAST:event_btn_UpdateActionPerformed

    private void btn_DeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_DeleteActionPerformed
        if (!tb_Deliveries.getSelectionModel().isSelectionEmpty()) {
            deleteDelivery();
        } else {
            JOptionPane.showMessageDialog(this, "Selecione Uma Linha!");
        }
    }//GEN-LAST:event_btn_DeleteActionPerformed

    private void formInternalFrameActivated(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameActivated
        fill_tbDeliveries("Ativo");
    }//GEN-LAST:event_formInternalFrameActivated

    private void btn_OldDeliveriesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_OldDeliveriesActionPerformed
        try {
            if (tipoBotao.equals("Ativo")) {
                btn_Add.setVisible(true);
                btn_Update.setVisible(true);
                btn_Delete.setVisible(true);
                changeDeliveriesMode("Ativo");
                this.tipoBotao = "Inativo";
            } else {
                btn_Add.setVisible(false);
                btn_Update.setVisible(false);
                btn_Delete.setVisible(false);
                changeDeliveriesMode("Inativo");
                this.tipoBotao = "Ativo";
            }

        } catch (Exception e) {
            System.out.println("Erro Ao Adaptar Pacotes Antigos");
        }

    }//GEN-LAST:event_btn_OldDeliveriesActionPerformed

    private void changeDeliveriesMode(String tipoBotao) {
        try {
            if (tipoBotao.equals("Ativo")) {
                btn_OldDeliveries.setText("Ver Entregas Inativas");
                fill_tbDeliveries(tipoBotao);
            } else {
                btn_OldDeliveries.setText("Ver Entregas Ativas");
                fill_tbDeliveries(tipoBotao);
            }
            tipoBotao = "Inativo";
        } catch (Exception e) {
            System.out.println("Erro Ao Preencher Lista Com Pacotes Antigos: " + e);
        }

    }

    public static String getTipoBotao() {
        return tipoBotao;
    }

    public static void setTipoBotao(String aTipoBotao) {
        tipoBotao = aTipoBotao;
    }


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn_Add;
    private javax.swing.JButton btn_Delete;
    private javax.swing.JButton btn_OldDeliveries;
    private javax.swing.JButton btn_Update;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tb_Deliveries;
    // End of variables declaration//GEN-END:variables
}
