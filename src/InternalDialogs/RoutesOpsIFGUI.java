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
import model.bean.Modelos;
import model.bean.Rotas;
import model.bean.Veiculos;
import model.bean.Funcionarios;
import model.dao.RotasDAO;
import model.dao.VeiculosDAO;
import model.dao.FuncionariosDAO;

/**
 *
 * @author focuswts
 */
public class RoutesOpsIFGUI extends javax.swing.JInternalFrame {

    /**
     * Creates new form RoutesOpsIFGUI
     */
    public static Rotas getRoute() {
        return route;
    }

    public static void setRoute(Rotas aRoute) {
        route = aRoute;
    }

    private String operacao;
    private static Rotas route;
    private Connection con;

    /**
     * Creates new form RoutesOpsGUI
     */
    public RoutesOpsIFGUI(java.awt.Frame parent, boolean modal) {
        initComponents();
        fill_Workers();
        fill_Vehicles();
    }

    public RoutesOpsIFGUI(java.awt.Frame parent, boolean modal, String operacao) {
        initComponents();
        con = (Connection) ConnectionFactory.getConnection();
        this.operacao = operacao;
        fill_Workers();
        fill_Vehicles();
        adaptOperation();
        changeIcon(operacao);
    }

    public RoutesOpsIFGUI(java.awt.Frame parent, boolean modal, String operacao, Rotas route) {
        initComponents();
        con = (Connection) ConnectionFactory.getConnection();
        this.operacao = operacao;
        this.route = route;
        fill_Workers();
        fill_Vehicles();
        adaptOperation();
        changeIcon(operacao);
    }

    private void fill_Workers() {
        FuncionariosDAO wDAO = new FuncionariosDAO();
        ArrayList<Funcionarios> Funcionarios = null;

        try {
            cb_Workers.removeAllItems();
            Funcionarios = wDAO.findAll();

            DefaultComboBoxModel workersModel = new DefaultComboBoxModel();

            for (Funcionarios worker : Funcionarios) {
                workersModel.addElement(worker);

            }
            cb_Workers.setModel(workersModel);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro Ao Preencher Filiais!" + e);
        }

    }

    private void fill_Vehicles() {
       VeiculosDAO vDAO = new VeiculosDAO();
        ArrayList<Veiculos> Veiculos = null;

        try {
            cb_Vehicle.removeAllItems();
            Veiculos = vDAO.findAll();

            DefaultComboBoxModel vehiclesModel = new DefaultComboBoxModel();

            for (Veiculos vehicle : Veiculos) {
                vehiclesModel.addElement(vehicle);

            }
            cb_Vehicle.setModel(vehiclesModel);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro Ao Preencher Filiais!" + e);
        }

    }

    private Rotas fieldsToOBJ(String operacao) {
        Veiculos v = new Veiculos();
        Funcionarios w = new Funcionarios();
        Rotas r = new Rotas();
        try {
            if (operacao.equals("Edit")) {
                int id = Integer.valueOf(txt_Id.getText());
                r.setId(id);
            }
            v = (Veiculos) cb_Vehicle.getSelectedItem();

            w = (Funcionarios) cb_Workers.getSelectedItem();

            String remetente = txt_Remetente.getText();
            String destinatario = txt_Destinatario.getText();

            r.setIdFuncionario(w);
            r.setIdVeiculo(v);
            r.setRemetente(remetente);
            r.setDestinatario(destinatario);
            return r;
        } catch (Exception e) {
            System.out.println("Erro Ao Gerar OBJ: " + e);
            return null;
        }
    }

    private void doOperation(String operacao, Rotas route) {
        RotasDAO rDAO = new RotasDAO();
        try {
            if (operacao.equals("Add")) {
                rDAO.save(route);
                JOptionPane.showMessageDialog(this, "Rota Criada Com Sucesso!");
            }
            if (operacao.equals("Edit")) {
                rDAO.update(route);
                JOptionPane.showMessageDialog(this, "Dados Da Rota Atualizados Com Sucesso!");
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
                    btn_Save.setText("Cadastrar Rota");
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
            sql = "SELECT Rotas.*,Funcionarios.*,Veiculos.*,Modelos.* FROM Rotas "
                    + "INNER JOIN Funcionarios ON Rotas.idFuncionario = Funcionarios.id "
                    + "INNER JOIN Veiculos ON Rotas.idVeiculo = Veiculos.id "
                    + "INNER JOIN Modelos ON Veiculos.idModelo = Modelos.id "
                    + "WHERE Rotas.remetente = ? AND Rotas.destinatario = ?";
            st = (PreparedStatement) con.prepareStatement(sql);
            st.setString(1, route.getRemetente());
            st.setString(2, route.getDestinatario());
            rs = st.executeQuery();

            if (rs.next()) {
                txt_Id.setText(rs.getString("Rotas.id"));
                Funcionarios w = new Funcionarios();
                w.setId(rs.getInt("Rotas.idFuncionario"));
                w.setNome(rs.getString("Funcionarios.nome"));

                Modelos m = new Modelos();
                m.setId(rs.getInt("Veiculos.idModelo"));
                m.setModelo(rs.getString("Modelos.modelo"));

                Veiculos v = new Veiculos();
                v.setId(rs.getInt("Rotas.idVeiculo"));
                v.setIdModelo(m);

                Rotas r = new Rotas();
                r.setId(rs.getInt("Rotas.id"));
                r.setIdFuncionario(w);
                r.setIdVeiculo(v);
                r.setRemetente(rs.getString("Rotas.remetente"));
                r.setDestinatario(rs.getString("Rotas.destinatario"));

                cb_Workers.getModel().setSelectedItem(w);
                cb_Vehicle.getModel().setSelectedItem(v);

                txt_Remetente.setText(r.getRemetente());
                txt_Destinatario.setText(r.getDestinatario());
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
                btn_Save.setText("Cadastrar Rota");
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

    public String getOperacao() {
        return operacao;
    }

    public void setOperacao(String operacao) {
        this.operacao = operacao;
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
        cb_Workers = new javax.swing.JComboBox<>();
        jLabel2 = new javax.swing.JLabel();
        cb_Vehicle = new javax.swing.JComboBox<>();
        jLabel3 = new javax.swing.JLabel();
        txt_Remetente = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        txt_Destinatario = new javax.swing.JTextField();
        jPanel3 = new javax.swing.JPanel();
        btn_Save = new javax.swing.JButton();

        setClosable(true);
        setTitle("Operações De Rota");
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

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder("Dados Da Rota"));

        lb_Id.setText("ID");

        txt_Id.setEditable(false);

        jLabel1.setText("Funcionário");

        cb_Workers.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jLabel2.setText("Veículo");

        cb_Vehicle.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jLabel3.setText("Remetente");

        jLabel4.setText("Destinatário");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(lb_Id)
                    .addComponent(txt_Id, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1)
                    .addComponent(cb_Workers, 0, 500, Short.MAX_VALUE)
                    .addComponent(jLabel2)
                    .addComponent(cb_Vehicle, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel3)
                    .addComponent(txt_Remetente)
                    .addComponent(jLabel4)
                    .addComponent(txt_Destinatario, javax.swing.GroupLayout.DEFAULT_SIZE, 500, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lb_Id)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txt_Id, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cb_Workers, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cb_Vehicle, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txt_Remetente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txt_Destinatario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        btn_Save.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/icons48x/add.png"))); // NOI18N
        btn_Save.setText("Cadastrar Rota");
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
                .addContainerGap(326, Short.MAX_VALUE)
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
    private javax.swing.JComboBox<String> cb_Vehicle;
    private javax.swing.JComboBox<String> cb_Workers;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JLabel lb_Id;
    private javax.swing.JTextField txt_Destinatario;
    private javax.swing.JTextField txt_Id;
    private javax.swing.JTextField txt_Remetente;
    // End of variables declaration//GEN-END:variables
}
