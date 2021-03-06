/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package InternalManagementViews;

import InternalDialogs.BrandsOpsIFGUI;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import model.bean.Marcas;
import model.dao.MarcasDAO;

/**
 *
 * @author focuswts
 */
public class BrandsMgmtIFGUI extends javax.swing.JInternalFrame {

    /**
     * Creates new form BrandsMgmtIFGUI
     */
    public BrandsMgmtIFGUI() {
        initComponents();
        fill_tbBrands();
    }

    private void fill_tbBrands() {

        DefaultTableModel aModel = (DefaultTableModel) tb_Brands.getModel();
        try {
            MarcasDAO bDAO = new MarcasDAO();
            ArrayList<Marcas> Filiais = null;
            Filiais = bDAO.findAll();

            int numLinhas = aModel.getRowCount();
            for (int i = 0; i < numLinhas; i++) {
                aModel.removeRow(0);
            }
            for (int i = 0; i < Filiais.size(); i++) {
                Object[] rowDados = new Object[1];
                rowDados[0] = Filiais.get(i).getMarca();
                aModel.addRow(rowDados);
            }
            this.tb_Brands.setModel(aModel);
        } catch (Exception e) {
            System.out.println("Erro Ao Preencher JTable: " + e);
        }

    }

    private Marcas fieldsToOBJ() {
        Marcas b = new Marcas();
        MarcasDAO bDAO = new MarcasDAO();
        try {
            String marca = tb_Brands.getValueAt(tb_Brands.getSelectedRow(), 0).toString();
            int idMarca = bDAO.getIdMarca(marca);
            b.setId(idMarca);
            b.setMarca(marca);
            return b;
        } catch (Exception e) {
            System.out.println("Erro AO Gerar OBJ: " + e);
            return null;
        }

    }

    private void deleteBrand() {
        Marcas b = new Marcas();
        MarcasDAO bDAO;
        bDAO = new MarcasDAO();
        int idBranch = bDAO.getIdMarca(tb_Brands.getValueAt(tb_Brands.getSelectedRow(), 0).toString());
        try {
            b.setId(idBranch);
            if (bDAO.delete(b) == true) {
                JOptionPane.showMessageDialog(this, "Marca Excluída Com Sucesso!");
            }
            fill_tbBrands();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro Ao Deletar Marca: " + e);
        }

    }

    private void viewOpsBrands(String operacao) {
        BrandsOpsIFGUI viewBrands;
        try {
            if (operacao.equals("Add")) {
                viewBrands = new BrandsOpsIFGUI(null, true, operacao);
                this.getParent().add(viewBrands);
                viewBrands.toFront();
                viewBrands.setVisible(true);
            }
            if (operacao.equals("Edit")) {
                viewBrands = new BrandsOpsIFGUI(null, true, operacao, fieldsToOBJ());
                this.getParent().add(viewBrands);
                viewBrands.toFront();
                viewBrands.setVisible(true);
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro Ao Abrir View Ops Branchs: " + e);
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
        tb_Brands = new javax.swing.JTable();
        jPanel2 = new javax.swing.JPanel();
        btn_Add = new javax.swing.JButton();
        btn_Edit = new javax.swing.JButton();
        btn_Delete = new javax.swing.JButton();

        setClosable(true);
        setTitle("Menu Operações Marcas");
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

        jLabel1.setText("Lista De Marcas");

        tb_Brands.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null},
                {null},
                {null},
                {null}
            },
            new String [] {
                "Marca"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tb_Brands.getTableHeader().setReorderingAllowed(false);
        jScrollPane1.setViewportView(tb_Brands);

        btn_Add.setText("Adicionar Marca");
        btn_Add.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_AddActionPerformed(evt);
            }
        });

        btn_Edit.setText("Editar Marca");
        btn_Edit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_EditActionPerformed(evt);
            }
        });

        btn_Delete.setText("Excluir Marca");
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
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(163, 163, 163)
                        .addComponent(jLabel1)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jPanel2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
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
        viewOpsBrands(operacao);
    }//GEN-LAST:event_btn_AddActionPerformed

    private void btn_EditActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_EditActionPerformed
        if (!tb_Brands.getSelectionModel().isSelectionEmpty()) {
            String operacao = "Edit";
            viewOpsBrands(operacao);
        } else {
            JOptionPane.showMessageDialog(this, "Selecione Uma Linha!");
        }

    }//GEN-LAST:event_btn_EditActionPerformed

    private void btn_DeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_DeleteActionPerformed
        if (!tb_Brands.getSelectionModel().isSelectionEmpty()) {
            deleteBrand();
        } else {
            JOptionPane.showMessageDialog(this, "Selecione Uma Linha!");
        }

    }//GEN-LAST:event_btn_DeleteActionPerformed

    private void formInternalFrameActivated(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameActivated
        fill_tbBrands();
    }//GEN-LAST:event_formInternalFrameActivated


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn_Add;
    private javax.swing.JButton btn_Delete;
    private javax.swing.JButton btn_Edit;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tb_Brands;
    // End of variables declaration//GEN-END:variables
}
