/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package InternalDialogs;

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
import model.bean.Marcas;
import model.bean.Modelos;
import model.dao.MarcasDAO;
import model.dao.ModelosDAO;

/**
 *
 * @author focuswts
 */
public class ModelsOpsIFGUI extends javax.swing.JInternalFrame {

    /**
     * Creates new form ModelsOpsIFGUI
     */
    private String operacao;
    private static Modelos model;
    private Connection con;

    public ModelsOpsIFGUI(java.awt.Frame parent, boolean modal) {
        initComponents();
        fill_Brands();
    }

    public ModelsOpsIFGUI(java.awt.Frame parent, boolean modal, String operacao) {
        initComponents();
        con = (Connection) ConnectionFactory.getConnection();
        this.operacao = operacao;
        fill_Brands();
        adaptOperation();
        changeIcon(operacao);
    }

    public ModelsOpsIFGUI(java.awt.Frame parent, boolean modal, String operacao, Modelos model) {
        initComponents();
        con = (Connection) ConnectionFactory.getConnection();
        this.operacao = operacao;
        this.model = model;
        fill_Brands();
        adaptOperation();
        changeIcon(operacao);
    }

    private void fill_Brands() {
        MarcasDAO bDAO = new MarcasDAO();
        ArrayList<Marcas> Marcas = null;

        try {
            cb_Brands.removeAllItems();
            Marcas = bDAO.findAll();

            DefaultComboBoxModel branchsModel = new DefaultComboBoxModel();

            for (Marcas brand : Marcas) {
                branchsModel.addElement(brand);

            }
            cb_Brands.setModel(branchsModel);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro Ao Preencher Filiais!" + e);
        }

    }

    private Modelos fieldsToOBJ(String operacao) {
        Marcas b = new Marcas();
        Modelos m = new Modelos();
        try {
            if (operacao.equals("Edit")) {
                int id = Integer.valueOf(txt_Id.getText());
                m.setId(id);
            }
            b = (Marcas) cb_Brands.getSelectedItem();

            m.setIdMarca(b);
            String modelo = txt_Modelo.getText();
            m.setModelo(modelo);
            return m;
        } catch (Exception e) {
            System.out.println("Erro Ao Gerar OBJ: " + e);
            return null;
        }
    }

    private void doOperation(String operacao, Modelos model) {
        ModelosDAO mDAO = new ModelosDAO();
        try {
            if (operacao.equals("Add")) {
                mDAO.save(model);
                JOptionPane.showMessageDialog(this, "Modelo Criada Com Sucesso!");
            }
            if (operacao.equals("Edit")) {
                mDAO.update(model);
                JOptionPane.showMessageDialog(this, "Dados Do Modelo Atualizados Com Sucesso!");
            }
            this.dispose();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro Ao Executar Operação!: " + e);
        }

    }

    private void adaptOperation() {
        try {
            //  JOptionPane.showMessageDialog(this, "Op: " + this.operacao);
            switch (operacao) {
                case "Edit":
                    btn_Save.setText("Salvar Alterações");
                    getDados();
                    break;
                case "Add":
                    txt_Id.setVisible(false);
                    lb_Id.setVisible(false);
                    btn_Save.setText("Cadastrar Modelo");
                    break;
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro Ao Adaptar Á Operação! " + e);
        }
    }

    private void getDados() {
        String sql = null;
        PreparedStatement st;
        ResultSet rs;
        try {
            sql = "SELECT Modelos.*,Marcas.* FROM Modelos "
                    + "INNER JOIN Marcas ON Modelos.idMarca = Marcas.id "
                    + "WHERE Modelos.modelo = ?";
            st = (PreparedStatement) con.prepareStatement(sql);
            st.setString(1, model.getModelo());
            rs = st.executeQuery();

            if (rs.next()) {
                txt_Id.setText(rs.getString("Modelos.id"));
                txt_Modelo.setText(rs.getString("Modelos.modelo"));

                Marcas b = new Marcas();
                b.setId(rs.getInt("Marcas.id"));
                b.setMarca(rs.getString("Marcas.marca"));

                cb_Brands.getModel().setSelectedItem(b);
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro Ao Carregar Dados!: " + e);
        }
    }

    private void changeIcon(String operacao) {
        ImageIcon icon;
        try {
            if (operacao.equals("Add")) {
                lb_Id.setVisible(false);
                txt_Id.setVisible(false);
                icon = new ImageIcon(getClass().getResource("/icons/icons48x/add.png"));
                btn_Save.setIcon(icon);
                btn_Save.setText("Cadastrar Modelo");
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

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        lb_Id = new javax.swing.JLabel();
        txt_Id = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        cb_Brands = new javax.swing.JComboBox<>();
        jLabel2 = new javax.swing.JLabel();
        txt_Modelo = new javax.swing.JTextField();
        jPanel2 = new javax.swing.JPanel();
        btn_Save = new javax.swing.JButton();

        setClosable(true);
        setTitle("Operações De Modelo");
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

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder("Dados Do Modelo"));

        lb_Id.setText("ID");

        txt_Id.setEditable(false);

        jLabel1.setText("Marca");

        cb_Brands.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jLabel2.setText("Modelo");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(lb_Id)
                    .addComponent(txt_Id, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1)
                    .addComponent(cb_Brands, 0, 220, Short.MAX_VALUE)
                    .addComponent(jLabel2)
                    .addComponent(txt_Modelo))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lb_Id)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txt_Id, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cb_Brands, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(txt_Modelo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        btn_Save.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/icons48x/add.png"))); // NOI18N
        btn_Save.setText("Cadastrar Modelo");
        btn_Save.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_SaveActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(278, Short.MAX_VALUE)
                .addComponent(btn_Save)
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btn_Save)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
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
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btn_SaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_SaveActionPerformed
        doOperation(operacao, fieldsToOBJ(operacao));
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
    private javax.swing.JButton btn_Save;
    private javax.swing.JComboBox<String> cb_Brands;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JLabel lb_Id;
    private javax.swing.JTextField txt_Id;
    private javax.swing.JTextField txt_Modelo;
    // End of variables declaration//GEN-END:variables
}
