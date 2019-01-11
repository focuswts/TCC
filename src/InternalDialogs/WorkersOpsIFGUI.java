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
import javax.swing.JOptionPane;
import model.bean.Cargos;
import model.bean.Funcionarios;
import model.dao.CargosDAO;
import model.dao.FuncionariosDAO;

/**
 *
 * @author focuswts
 */
public class WorkersOpsIFGUI extends javax.swing.JInternalFrame {

    /**
     * Creates new form WorkersOpsIFGUI
     */
    private Connection con = null;
    private static Funcionarios worker;
    private String operacao;

    public WorkersOpsIFGUI(java.awt.Frame parent, boolean modal) {
        initComponents();
        con = (Connection) ConnectionFactory.getConnection();
        fill_Jobs();
    }

    public WorkersOpsIFGUI(java.awt.Frame parent, boolean modal, String operacao) {
        initComponents();
        con = (Connection) ConnectionFactory.getConnection();
        this.operacao = operacao;
        fill_Jobs();
        adaptOperation();
    }

    public WorkersOpsIFGUI(java.awt.Frame parent, boolean modal, String operacao, Funcionarios worker) {
        initComponents();
        con = (Connection) ConnectionFactory.getConnection();
        this.operacao = operacao;
        this.worker = worker;
        fill_Jobs();
        adaptOperation();
    }

    private void fill_Jobs() {
        CargosDAO jDAO = new CargosDAO();
        ArrayList<Cargos> Cargos = null;

        try {
            cb_Jobs.removeAllItems();
            Cargos = jDAO.findAll();

            DefaultComboBoxModel jobsModel = new DefaultComboBoxModel();

            for (Cargos job : Cargos) {
                jobsModel.addElement(job);

            }
            cb_Jobs.setModel(jobsModel);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro Ao Preencher Cargos!" + e);
        }

    }

    private void getMaxId() {
        try {
            FuncionariosDAO wDAO = new FuncionariosDAO();
            int maxId = wDAO.getMaxId();
            txt_id.setText(String.valueOf(maxId));

        } catch (Exception e) {
        }
    }

    private void adaptOperation() {
        try {
            //  JOptionPane.showMessageDialog(this, "Op: " + this.operacao);
            switch (operacao) {
                case "Edit":
                    btn_doOperation.setText("Salvar Alterações");
                    getDados();
                    break;
                case "Add":
                    txt_id.setVisible(false);
                    lb_Id.setVisible(false);
                    btn_doOperation.setText("Cadastrar Funcionário");
                    getMaxId();
                    break;
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro Ao Adaptar Á Operação! " + e);
        }
    }

    private void addWorker() {
        try {
            Cargos j = (Cargos) cb_Jobs.getSelectedItem();
            Funcionarios w = new Funcionarios();

            w.setId(Integer.valueOf(txt_id.getText()));
            w.setNome(txt_nome.getText());
            w.setCpf(Ftxt_cpf.getText());
            w.setRg(Ftxt_rg.getText());

            CargosDAO jDAO = new CargosDAO();
            int idCargo = jDAO.getIdCargos(j.getFuncao());
            j.setId(idCargo);
            w.setIdCargo(j);
            w.setCelular(Ftxt_Celular.getText());
            w.setEmail(txt_Email.getText());
            w.setEndereco(txt_Endereco.getText());
            w.setTelefone(Ftxt_Telefone.getText());
            this.worker = w;

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro Ao Criar Objeto Funcionário!: " + e);
        }

    }

    private void getDados() {
        String sql = null;
        ResultSet rs;
        try {
            sql = "SELECT Funcionarios.*,Cargos.* FROM Funcionarios "
                    + "INNER JOIN Cargos ON Funcionarios.idCargo = Cargos.id "
                    + "WHERE nome = ?";
            PreparedStatement st = (PreparedStatement) con.prepareStatement(sql);
            st.setString(1, worker.getNome());
            rs = st.executeQuery();

            if (rs.next()) {
                txt_id.setText(String.valueOf(rs.getInt("Funcionarios.id")));
                txt_nome.setText(rs.getString("Funcionarios.nome"));
                Ftxt_cpf.setText(rs.getString("Funcionarios.cpf"));
                Ftxt_rg.setText(rs.getString("Funcionarios.rg"));
                Ftxt_Celular.setText(rs.getString("Funcionarios.celular"));
                txt_Email.setText(rs.getString("Funcionarios.email"));
                Ftxt_Telefone.setText(rs.getString("Funcionarios.telefone"));
                txt_Endereco.setText(rs.getString("Funcionarios.endereco"));

                Cargos j = new Cargos();
                j.setFuncao(rs.getString("Cargos.funcao"));
                cb_Jobs.getModel().setSelectedItem(j);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro Ao Carregar Dados Do Funcionário!: " + e);
        }
    }

    private void doOperation(String operacao, Funcionarios w) {

        FuncionariosDAO wDAO = new FuncionariosDAO();
        try {
            if (operacao.equals("Add")) {
                wDAO.save(w);
                JOptionPane.showMessageDialog(this, "Funcionário Cadastrado Com Sucesso!");
                this.dispose();
            }
            if (operacao.equals("Edit")) {
                wDAO.update(w);
                JOptionPane.showMessageDialog(this, "Dados Do Funcionário Alterados Com Sucesso!");
                this.dispose();
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro Ao Efetuar Operação! " + e);
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
        txt_id = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        txt_nome = new javax.swing.JTextField();
        Ftxt_cpf = new javax.swing.JFormattedTextField();
        Ftxt_rg = new javax.swing.JFormattedTextField();
        jLabel10 = new javax.swing.JLabel();
        cb_Jobs = new javax.swing.JComboBox<>();
        btn_doOperation = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        txt_Endereco = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        txt_Email = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        Ftxt_Telefone = new javax.swing.JFormattedTextField();
        Ftxt_Celular = new javax.swing.JFormattedTextField();

        setClosable(true);
        setTitle("Operações De Funcionário");
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

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder("Dados Pessoais"));

        lb_Id.setText("ID");

        txt_id.setEditable(false);

        jLabel2.setText("Nome");

        jLabel3.setText("CPF");

        jLabel4.setText("RG");

        try {
            Ftxt_cpf.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("###.###.###-##")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }

        try {
            Ftxt_rg.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("#.###.###-#")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }

        jLabel10.setText("Cargo");

        cb_Jobs.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel10)
                    .addComponent(lb_Id)
                    .addComponent(jLabel2)
                    .addComponent(jLabel3)
                    .addComponent(jLabel4))
                .addGap(40, 40, 40)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txt_nome)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(txt_id, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(Ftxt_cpf)
                                    .addComponent(Ftxt_rg, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(0, 0, Short.MAX_VALUE)))
                        .addGap(13, 13, 13))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(cb_Jobs, javax.swing.GroupLayout.PREFERRED_SIZE, 187, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lb_Id)
                    .addComponent(txt_id, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(txt_nome, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(Ftxt_cpf, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(Ftxt_rg, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10)
                    .addComponent(cb_Jobs, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );

        btn_doOperation.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/icons32x/next.png"))); // NOI18N
        btn_doOperation.setText("Cadastrar Contatos");
        btn_doOperation.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_doOperationActionPerformed(evt);
            }
        });

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder("Dados De Contato"));

        jLabel6.setText("Endereço");

        txt_Endereco.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N

        jLabel7.setText("Telefone");

        jLabel8.setText("Celular");

        txt_Email.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N

        jLabel9.setText("Email");

        try {
            Ftxt_Telefone.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("(##)####-####")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }

        try {
            Ftxt_Celular.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("(##)#####-####")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel6)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txt_Endereco))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel7)
                            .addComponent(jLabel8)
                            .addComponent(jLabel9))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txt_Email, javax.swing.GroupLayout.PREFERRED_SIZE, 298, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(Ftxt_Celular, javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(Ftxt_Telefone, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 135, Short.MAX_VALUE)))))
                .addGap(119, 119, 119))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txt_Endereco, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(Ftxt_Telefone, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(Ftxt_Celular, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9)
                    .addComponent(txt_Email, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(18, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(btn_doOperation))
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 238, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btn_doOperation)
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

    private void btn_doOperationActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_doOperationActionPerformed
        addWorker();
        doOperation(operacao, this.worker);
    }//GEN-LAST:event_btn_doOperationActionPerformed

    private void formInternalFrameDeactivated(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameDeactivated
        try {
            super.setSelected(true);
            super.toFront();
            super.setVisible(true);
        } catch (PropertyVetoException ex) {
            Logger.getLogger(PackageItemChooserIFGUI.class.getName()).log(Level.SEVERE, null, ex);
        }

    }//GEN-LAST:event_formInternalFrameDeactivated

    public static Funcionarios getWorker() {
        return worker;
    }

    public static void setWorker(Funcionarios aWorker) {
        worker = aWorker;
    }


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JFormattedTextField Ftxt_Celular;
    private javax.swing.JFormattedTextField Ftxt_Telefone;
    private javax.swing.JFormattedTextField Ftxt_cpf;
    private javax.swing.JFormattedTextField Ftxt_rg;
    private javax.swing.JButton btn_doOperation;
    private javax.swing.JComboBox<String> cb_Jobs;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JLabel lb_Id;
    private javax.swing.JTextField txt_Email;
    private javax.swing.JTextField txt_Endereco;
    private javax.swing.JTextField txt_id;
    private javax.swing.JTextField txt_nome;
    // End of variables declaration//GEN-END:variables

    public String getOperacao() {
        return operacao;
    }

    public void setOperacao(String operacao) {
        this.operacao = operacao;
    }
}
