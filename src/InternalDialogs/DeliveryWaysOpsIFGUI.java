/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package InternalDialogs;

import connection.ConnectionFactory;
import java.beans.PropertyVetoException;
import java.sql.Connection;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import model.bean.FormasEntrega;
import model.dao.FormasEntregaDAO;

/**
 *
 * @author focuswts
 */
public class DeliveryWaysOpsIFGUI extends javax.swing.JInternalFrame {

    /**
     * Creates new form DeliveryWaysOpsIFGUI
     */
    private String operacao;
    private static FormasEntrega dw;
    private Connection con;

    public DeliveryWaysOpsIFGUI(java.awt.Frame parent, boolean modal) {
        initComponents();
        con = (Connection) ConnectionFactory.getConnection();
    }

    public DeliveryWaysOpsIFGUI(java.awt.Frame parent, boolean modal, String operacao) {
        initComponents();
        con = (Connection) ConnectionFactory.getConnection();
        this.operacao = operacao;
        adaptOperation();
        changeIcon(operacao);
    }

    public DeliveryWaysOpsIFGUI(java.awt.Frame parent, boolean modal, String operacao, FormasEntrega dw) {
        initComponents();
        con = (Connection) ConnectionFactory.getConnection();
        this.operacao = operacao;
        this.dw = dw;
        adaptOperation();
        changeIcon(operacao);
    }

    private void changeIcon(String operacao) {
        ImageIcon icon;
        try {
            if (operacao.equals("Add")) {
                lb_Id.setVisible(false);
                txt_Id.setVisible(false);
                icon = new ImageIcon(getClass().getResource("/icons/icons48x/add.png"));
                btn_Save.setIcon(icon);
                btn_Save.setText("Cadastrar Forma De Entrega");
            }
            if (operacao.equals("Edit")) {
                icon = new ImageIcon(getClass().getResource("/icons/icons48x/accept.png"));
                btn_Save.setIcon(icon);
                btn_Save.setText("Salvar Alterações");
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro Ao Mudar Ícone: " + e);
        }

    }

    private void adaptOperation() {
        try {
            //  JOptionPane.showMessageDialog(this, "Op: " + this.operacao);
            switch (operacao) {
                case "Edit":
                    txt_Id.setEditable(false);
                    btn_Save.setText("Salvar Alterações");
                    fillInfo();
                    break;
                case "Add":
                    txt_Id.setVisible(false);
                    lb_Id.setVisible(false);
                    btn_Save.setText("Cadastrar Forma De Entrega");
                    break;
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro Ao Adaptar Á Operação! " + e);
        }
    }

    private void fillInfo() {
        FormasEntregaDAO dwDAO = new FormasEntregaDAO();
        FormasEntrega deliveryW;
        try {
            deliveryW = dwDAO.getOBJFromForma(this.dw.getForma());
            this.dw.setId(deliveryW.getId());
            txt_Id.setText(String.valueOf(dw.getId()));

            txt_Forma.setText(dw.getForma());

            Ftxt_Valor.setText(dw.getValor());

        } catch (Exception e) {
            System.out.println("Erro Ao Preencher Dados: " + e);
        }

    }

    private FormasEntrega fieldsToOBJ() {
        FormasEntrega deliveryW = new FormasEntrega();
        try {
            if (this.operacao.equals("Edit")) {
                deliveryW.setId(Integer.valueOf(txt_Id.getText()));
            }
            deliveryW.setForma(txt_Forma.getText());

            String valor = "R$" + Ftxt_Valor.getText();
            deliveryW.setValor(valor);
            return deliveryW;
        } catch (Exception e) {
            System.out.println("Erro Ao Transferir Valores Para OBJ: " + e);
            return null;
        }
    }

    private void doOperation(String operacao, FormasEntrega deliveryW) {
        FormasEntregaDAO dwDAO = new FormasEntregaDAO();
        try {
            if (operacao.equals("Add")) {
                dwDAO.save(deliveryW);
                JOptionPane.showMessageDialog(this, "Forma De Entrega Criada Com Sucesso!");
            }
            if (operacao.equals("Edit")) {
                dwDAO.update(deliveryW);
                JOptionPane.showMessageDialog(this, "Dados Da Forma De Entrega Atualizados Com Sucesso!");
            }
            this.dispose();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro Ao Executar Operação!: " + e);
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
        lb_Id = new javax.swing.JLabel();
        txt_Id = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        txt_Forma = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        Ftxt_Valor = new javax.swing.JFormattedTextField();
        jPanel3 = new javax.swing.JPanel();
        btn_Save = new javax.swing.JButton();

        setTitle("Operações Formas De Entrega");
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
            }
            public void internalFrameDeactivated(javax.swing.event.InternalFrameEvent evt) {
                formInternalFrameDeactivated(evt);
            }
        });

        jPanel1.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder("Dados Da Situação"));

        lb_Id.setText("ID");

        jLabel2.setText("Forma");

        jLabel1.setText("Valor");

        Ftxt_Valor.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#0.00"))));

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lb_Id)
                    .addComponent(txt_Id, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2)
                    .addComponent(txt_Forma, javax.swing.GroupLayout.PREFERRED_SIZE, 260, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1)
                    .addComponent(Ftxt_Valor, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(350, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lb_Id)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txt_Id, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txt_Forma, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(Ftxt_Valor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(53, Short.MAX_VALUE))
        );

        btn_Save.setText("Salvar Dados");
        btn_Save.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_SaveActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btn_Save)
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(35, 35, 35)
                .addComponent(btn_Save)
                .addContainerGap(34, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(12, 12, 12))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btn_SaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_SaveActionPerformed
        doOperation(this.operacao, fieldsToOBJ());
    }//GEN-LAST:event_btn_SaveActionPerformed

    private void formInternalFrameDeactivated(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameDeactivated
        try {
            super.setSelected(true);
            super.toFront();
            super.setVisible(true);
        } catch (PropertyVetoException ex) {
            Logger.getLogger(PackageItemChooserIFGUI.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_formInternalFrameDeactivated


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JFormattedTextField Ftxt_Valor;
    private javax.swing.JButton btn_Save;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JLabel lb_Id;
    private javax.swing.JTextField txt_Forma;
    private javax.swing.JTextField txt_Id;
    // End of variables declaration//GEN-END:variables
}
