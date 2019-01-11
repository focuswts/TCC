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
import model.bean.Veiculos;
import model.dao.MarcasDAO;
import model.dao.ModelosDAO;
import model.dao.VeiculosDAO;

/**
 *
 * @author focuswts
 */
public class VehiclesOpsIFGUI extends javax.swing.JInternalFrame {

    /**
     * Creates new form VehiclesOpsIFGUI
     */
    private String operacao;
    private static Veiculos vehicle;
    private Connection con;

    /**
     * Creates new form VehiclesOpsGUI
     */
    public VehiclesOpsIFGUI(java.awt.Frame parent, boolean modal) {
        initComponents();
        con = (Connection) ConnectionFactory.getConnection();
        fill_Brands();
        setCB_Models();
    }

    public VehiclesOpsIFGUI(java.awt.Frame parent, boolean modal, String operacao) {
        initComponents();
        con = (Connection) ConnectionFactory.getConnection();
        this.operacao = operacao;
        fill_Brands();
        setCB_Models();
        adaptOperation();
    }

    public VehiclesOpsIFGUI(java.awt.Frame parent, boolean modal, String operacao, Veiculos vehicle) {
        initComponents();
        con = (Connection) ConnectionFactory.getConnection();
        this.operacao = operacao;
        this.vehicle = vehicle;
        fill_Brands();
        setCB_Models();
        adaptOperation();
    }

    private void setCB_Models() {
        String marca = null;
        if (cb_Brands.getSelectedItem() != null) {
            Marcas brand = (Marcas) cb_Brands.getSelectedItem();
            marca = brand.getMarca();
            fill_Models(marca);
        }

    }

    private Veiculos fieldsToOBJ(String operacao) {
        Marcas b = null;
        Modelos m = null;
        Veiculos v = new Veiculos();
        try {
            if (operacao.equals("Edit")) {
                int id = Integer.valueOf(txt_Id.getText());
                v.setId(id);
                //  v.setIdFilial(branch);
            }
            b = (Marcas) cb_Brands.getSelectedItem();
            m = (Modelos) cb_Models.getSelectedItem();
            m.setIdMarca(b);

            v.setIdModelo(m);
            v.setPlaca(Ftxt_Placa.getText());
            v.setChassi(txt_Chassi.getText());

            System.out.println("Placa: " + v.getPlaca());
      //   System.out.println("Filial: " + v.getIdFilial().getFilial());
            return v;
        } catch (Exception e) {
            System.out.println("Erro Ao Gerar OBJ: " + e);
            return null;
        }
    }

    private void doOperation(String operacao, Veiculos vehicle) {
        VeiculosDAO vDAO = new VeiculosDAO();
        try {
            if (operacao.equals("Add")) {
                vDAO.save(vehicle);
                JOptionPane.showMessageDialog(this, "Veículo Criado Com Sucesso!");
            }
            if (operacao.equals("Edit")) {
                vDAO.update(vehicle);
                JOptionPane.showMessageDialog(this, "Dados Do Veículo Atualizados Com Sucesso!");
            }
            this.dispose();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro Ao Executar Operação!: " + e);
        }

    }

    private void adaptOperation() {
        try {
            //  JOptionPane.showMessageDialog(this, "Op: " + this.operacao);
            changeIcon(operacao);
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

    private void fill_Brands() {
        MarcasDAO bDAO = new MarcasDAO();
        ArrayList<Marcas> Marcas = null;

        try {
            this.cb_Brands.removeAllItems();
            Marcas = bDAO.findAll();

            DefaultComboBoxModel branchsModel = new DefaultComboBoxModel();

            for (Marcas brand : Marcas) {
                branchsModel.addElement(brand);

            }
            this.cb_Brands.setModel(branchsModel);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro Ao Preencher Marcas!" + e);
        }

    }

    private void fill_Models(String marca) {
        ModelosDAO mDAO = new ModelosDAO();
        ArrayList<Modelos> Modelos = null;

        try {
            cb_Models.removeAllItems();
            Modelos = mDAO.findAll(marca);

            DefaultComboBoxModel modelsModel = new DefaultComboBoxModel();

            for (Modelos model : Modelos) {
                modelsModel.addElement(model);
                System.out.println(model.getModelo());

            }
            cb_Models.setModel(modelsModel);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro Ao Preencher Modelos!" + e);
        }

    }

    private void getDados() {
        String sql = null;
        PreparedStatement st;
        ResultSet rs;
        try {
            sql = "SELECT Veiculos.*,Modelos.*,Marcas.* FROM Veiculos "
                    + "INNER JOIN Modelos ON Veiculos.idModelo = Modelos.id "
                    + "INNER JOIN Marcas ON Modelos.idMarca = Marcas.id "
                    + "WHERE Veiculos.chassi = ?";

            st = (PreparedStatement) con.prepareStatement(sql);
            st.setString(1, vehicle.getChassi());
            rs = st.executeQuery();

            if (rs.next()) {
                txt_Id.setText(rs.getString("Veiculos.id"));

                Marcas b = new Marcas();
                b.setId(rs.getInt("Marcas.id"));
                b.setMarca(rs.getString("Marcas.marca"));

                cb_Brands.getModel().setSelectedItem(b);

                Modelos m = new Modelos();
                m.setId(rs.getInt("Veiculos.idModelo"));
                m.setModelo(rs.getString("Modelos.modelo"));

                cb_Models.getModel().setSelectedItem(m);

                txt_Chassi.setText(rs.getString("Veiculos.chassi"));
                Ftxt_Placa.setText(rs.getString("Veiculos.placa"));
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
                btn_Save.setText("Cadastrar Veículo");
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
        jPanel2 = new javax.swing.JPanel();
        lb_Id = new javax.swing.JLabel();
        txt_Id = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        cb_Brands = new javax.swing.JComboBox<>();
        jLabel2 = new javax.swing.JLabel();
        cb_Models = new javax.swing.JComboBox<>();
        jLabel3 = new javax.swing.JLabel();
        txt_Chassi = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        Ftxt_Placa = new javax.swing.JFormattedTextField();
        jPanel3 = new javax.swing.JPanel();
        btn_Save = new javax.swing.JButton();

        setClosable(true);
        setTitle("Operações De Veículos");
        setToolTipText("");
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

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder("Dados Do Veículo"));

        lb_Id.setText("ID");

        txt_Id.setEditable(false);

        jLabel1.setText("Marca");

        cb_Brands.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        cb_Brands.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cb_Brands.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cb_BrandsActionPerformed(evt);
            }
        });
        cb_Brands.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                cb_BrandsPropertyChange(evt);
            }
        });

        jLabel2.setText("Modelo");

        cb_Models.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        cb_Models.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Selecione A Marca" }));

        jLabel3.setText("Chassi");

        txt_Chassi.setFont(new java.awt.Font("Dialog", 0, 18)); // NOI18N

        jLabel4.setText("Placa");

        try {
            Ftxt_Placa.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("AAA-####")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }
        Ftxt_Placa.setFont(new java.awt.Font("Dialog", 0, 18)); // NOI18N

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lb_Id)
                    .addComponent(txt_Id, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1)
                            .addComponent(cb_Brands, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(39, 39, 39)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2)
                            .addComponent(cb_Models, javax.swing.GroupLayout.PREFERRED_SIZE, 220, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(jLabel3)
                    .addComponent(txt_Chassi, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4)
                    .addComponent(Ftxt_Placa, javax.swing.GroupLayout.PREFERRED_SIZE, 220, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(102, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addComponent(lb_Id)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txt_Id, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jLabel2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cb_Brands, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cb_Models, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txt_Chassi, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(Ftxt_Placa, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        btn_Save.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/icons48x/add.png"))); // NOI18N
        btn_Save.setText("Cadastrar Veículo");
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
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void cb_BrandsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cb_BrandsActionPerformed
        setCB_Models();
    }//GEN-LAST:event_cb_BrandsActionPerformed

    private void cb_BrandsPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_cb_BrandsPropertyChange

    }//GEN-LAST:event_cb_BrandsPropertyChange

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
    private javax.swing.JFormattedTextField Ftxt_Placa;
    private javax.swing.JButton btn_Save;
    private static javax.swing.JComboBox<String> cb_Brands;
    private static javax.swing.JComboBox<String> cb_Models;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JLabel lb_Id;
    private javax.swing.JTextField txt_Chassi;
    private javax.swing.JTextField txt_Id;
    // End of variables declaration//GEN-END:variables
}
