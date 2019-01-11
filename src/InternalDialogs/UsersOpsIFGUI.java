/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package InternalDialogs;

import InternalManagementViews.UsersMgmtIFGUI;
import java.beans.PropertyVetoException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import model.bean.Clientes;
import model.bean.Usuarios;
import model.dao.UsuariosDAO;

/**
 *
 * @author focuswts
 */
public class UsersOpsIFGUI extends javax.swing.JInternalFrame {

    /**
     * Creates new form UsersOpsIFGUI
     */
    private static Usuarios user;
    public static Clientes client;
    private static String permission;
    private String operation;

    public UsersOpsIFGUI(java.awt.Frame parent, boolean modal) {
        initComponents();
        fill_cbPermissions();
    }

    public UsersOpsIFGUI(java.awt.Frame parent, boolean modal, String operation) {
        initComponents();
        fill_cbPermissions();
        this.operation = operation;
        adaptView();

    }

    public UsersOpsIFGUI(java.awt.Frame parent, boolean modal, String operation, Usuarios user) {
        initComponents();
        fill_cbPermissions();
        this.operation = operation;
        this.user = user;
        adaptView();
        fillInfo(user);

    }

    private void lockClientFields() {
        try {

            jp_Client.setVisible(false);
            lb_Nome.setVisible(false);
            lb_Cpf.setVisible(false);
            lb_Rg.setVisible(false);

            txt_Nome.setVisible(false);
            txt_Cpf.setVisible(false);
            txt_Rg.setVisible(false);
        } catch (Exception e) {
            System.out.println("Erro AO Adaptar Client Fields: " + e);
        }
    }

    private void adaptView() {
        try {
            lockClientFields();
            //  configComboBox();
            if (this.operation.equals("Add")) {
                btn_save.setText("Cadastrar Usuário");
                txt_id.setVisible(false);
                lb_id.setVisible(false);

            }
            if (this.operation.equals("Edit")) {

                btn_save.setText("Salvar Alterações");

            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro Ao Adaptar View: " + e);
        }
    }

    public static void unLockClientFields() {
        try {
            jp_Client.setVisible(true);
            lb_Nome.setVisible(true);
            lb_Cpf.setVisible(true);
            lb_Rg.setVisible(true);

            txt_Nome.setVisible(true);
            txt_Cpf.setVisible(true);
            txt_Rg.setVisible(true);

        } catch (Exception e) {
        }
    }

    private void fillObjInfoFromDB(Usuarios u) {
        try {
            UsuariosDAO uDAO = new UsuariosDAO();
            Usuarios user = uDAO.getObjFromUser(u.getId());
            this.user = user;
        } catch (Exception e) {
            System.out.println("Erro Ao Pegar Dados BD: " + e);
        }
    }

    private void fillInfo(Usuarios user) {
        try {
            txt_id.setText(String.valueOf(user.getId()));
            txt_login.setText(user.getLogin());
            txt_password.setText(user.getSenha());
            cb_permissions.setSelectedItem(user.getNivel());

            if (user.getNivel().equals("Cliente")) {
                fillObjInfoFromDB(user);
                fillInfo2();
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro Ao Preencher Dados: " + e);
        }
    }

    private void fillInfo2() {
        try {
            txt_Nome.setText(String.valueOf(user.getIdCliente().getNome()));
            txt_Cpf.setText(String.valueOf(user.getIdCliente().getCpf()));
            txt_Rg.setText(String.valueOf(user.getIdCliente().getRg()));
        } catch (Exception e) {
            System.out.println("Erro AO Trazer Dados Cliente!: " + e);
        }
    }

    private Usuarios fieldToObj() {
        try {
            Usuarios u = new Usuarios();
            int id = Integer.valueOf(txt_id.getText().toString());
            String login = txt_login.getText();
            String password = new String(txt_password.getPassword());
            String permission = (String) cb_permissions.getSelectedItem();

            u.setId(id);
            u.setLogin(login);
            u.setSenha(password);
            u.setNivel(permission);
            return u;
        } catch (Exception e) {
            System.out.println("Erro Ao Criar OBJ User1: " + e);
            return null;
        }
    }

    private Usuarios fieldToObj2() {
        try {
            Usuarios u = new Usuarios();
            String login = txt_login.getText();
            String password = new String(txt_password.getPassword());
            String permission = (String) cb_permissions.getSelectedItem();

            u.setLogin(login);
            u.setSenha(password);
            u.setNivel(permission);
            this.user = u;
            return u;
        } catch (Exception e) {
            System.out.println("Erro Ao Criar OBJ User2: " + e);
            return null;
        }
    }

    public static boolean saveUserClient(Clientes c) {
        boolean b = false;
        UsuariosDAO uDAO = new UsuariosDAO();
        try {
            if (uDAO.saveWithClient(UsersOpsIFGUI.user, c)) {
                b = true;
            }
        } catch (Exception e) {
            System.out.println("Erro Ao Salvar Usuário Cliente: " + e);
        }
        return b;
    }

    private void checkPermission() {
        String permission = "";
        try {
            permission = (String) cb_permissions.getModel().getSelectedItem();
            if (permission.equals("Cliente")) {
                this.permission = permission;
            }

        } catch (Exception e) {
            System.out.println("Erro Ao Checkar Permissão Cliente: " + e);
        }
    }

    public static void fillClientsFields() {
        try {
            txt_Nome.setText(UsersOpsIFGUI.client.getNome());
            txt_Cpf.setText(UsersOpsIFGUI.client.getCpf());
            txt_Rg.setText(UsersOpsIFGUI.client.getRg());
        } catch (Exception e) {
        }
    }

    private void viewClientsChooser() {
        try {
            ClientsChooserIFGUI viewClientsChooser = new ClientsChooserIFGUI("UserOps");
            this.getParent().add(viewClientsChooser);
            viewClientsChooser.toFront();
            viewClientsChooser.setVisible(true);
        } catch (Exception e) {
            System.out.println("Erro Ao Abrir Clients Chooser: " + e);
        }
    }

    private void doOperation() {
        UsuariosDAO uDAO = new UsuariosDAO();
        try {
            checkPermission();

            switch (this.operation) {
                case "Add":
                    if (cb_permissions.getModel().getSelectedItem().toString().equals("Cliente") && !txt_Nome.getText().equals("")) {
                        uDAO.saveWithClient(fieldToObj2(), client);
                    } else {
                        if (uDAO.save(fieldToObj2())) {
                            JOptionPane.showMessageDialog(this, "Usuário Criado Com Sucesso!");
                        }
                    }
                    this.dispose();
                    break;
                case "Edit":
                    uDAO.update(fieldToObj());
                    JOptionPane.showMessageDialog(this, "Dados Do Usuário Atualizados Com Sucesso!");
                    this.dispose();
                    break;
            }
            UsersMgmtIFGUI.refresh_tbUsers();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro Ao Executar Operação: " + e);
        }

    }

    private void fill_cbPermissions() {
        ArrayList<String> Permissoes = null;
        try {
            cb_permissions.removeAllItems();
            Permissoes = new ArrayList<>();
            Permissoes.add("Admin");
            Permissoes.add("Cliente");

            for (String s : Permissoes) {
                cb_permissions.addItem(s);
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro Ao Carregar Permissões! : " + e);
        }
    }

    public Usuarios getUser() {
        return user;
    }

    public void setUser(Usuarios user) {
        this.user = user;
    }

    public String getOperation() {
        return operation;
    }

    public void setOperation(String operation) {
        this.operation = operation;
    }

    public static Clientes getClient() {
        return client;
    }

    public static void setClient(Clientes aClient) {
        client = aClient;
    }

    public static String getPermission() {
        return permission;
    }

    public static void setPermission(String aPermission) {
        permission = aPermission;
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
        lb_id = new javax.swing.JLabel();
        txt_id = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        txt_login = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        txt_password = new javax.swing.JPasswordField();
        jLabel4 = new javax.swing.JLabel();
        cb_permissions = new javax.swing.JComboBox<>();
        jPanel3 = new javax.swing.JPanel();
        btn_save = new javax.swing.JButton();
        jp_Client = new javax.swing.JPanel();
        lb_Nome = new javax.swing.JLabel();
        txt_Nome = new javax.swing.JTextField();
        lb_Cpf = new javax.swing.JLabel();
        txt_Cpf = new javax.swing.JTextField();
        lb_Rg = new javax.swing.JLabel();
        txt_Rg = new javax.swing.JTextField();

        setClosable(true);
        setTitle("Operações De Usuário");
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

        lb_id.setText("ID");

        txt_id.setEditable(false);

        jLabel2.setText("Login");

        jLabel3.setText("Password");

        jLabel4.setText("Permission");

        cb_permissions.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cb_permissions.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cb_permissionsItemStateChanged(evt);
            }
        });
        cb_permissions.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cb_permissionsActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(lb_id)
                    .addComponent(txt_id, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2)
                    .addComponent(jLabel3)
                    .addComponent(jLabel4)
                    .addComponent(txt_login, javax.swing.GroupLayout.DEFAULT_SIZE, 270, Short.MAX_VALUE)
                    .addComponent(txt_password)
                    .addComponent(cb_permissions, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lb_id)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txt_id, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txt_login, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txt_password, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cb_permissions, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        btn_save.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/icons48x/user_add.png"))); // NOI18N
        btn_save.setText("Cadastrar Usuário");
        btn_save.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_saveActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap(254, Short.MAX_VALUE)
                .addComponent(btn_save)
                .addGap(229, 229, 229))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btn_save)
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
                    .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
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

        lb_Nome.setText("Nome");

        txt_Nome.setEditable(false);

        lb_Cpf.setText("CPF");

        txt_Cpf.setEditable(false);

        lb_Rg.setText("RG");

        txt_Rg.setEditable(false);

        javax.swing.GroupLayout jp_ClientLayout = new javax.swing.GroupLayout(jp_Client);
        jp_Client.setLayout(jp_ClientLayout);
        jp_ClientLayout.setHorizontalGroup(
            jp_ClientLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jp_ClientLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jp_ClientLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lb_Nome)
                    .addComponent(txt_Nome, javax.swing.GroupLayout.PREFERRED_SIZE, 422, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lb_Cpf)
                    .addComponent(lb_Rg)
                    .addGroup(jp_ClientLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(txt_Rg, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 240, Short.MAX_VALUE)
                        .addComponent(txt_Cpf, javax.swing.GroupLayout.Alignment.LEADING)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jp_ClientLayout.setVerticalGroup(
            jp_ClientLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jp_ClientLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lb_Nome)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txt_Nome, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(lb_Cpf)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(txt_Cpf, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(lb_Rg)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(txt_Rg, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(25, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jp_Client, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jp_Client, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btn_saveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_saveActionPerformed
        doOperation();
    }//GEN-LAST:event_btn_saveActionPerformed

    private void formInternalFrameDeactivated(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameDeactivated
        try {
            super.setSelected(true);
            super.toFront();
            super.setVisible(true);
        } catch (PropertyVetoException ex) {
            Logger.getLogger(PackageItemChooserIFGUI.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_formInternalFrameDeactivated

    private void cb_permissionsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cb_permissionsActionPerformed


    }//GEN-LAST:event_cb_permissionsActionPerformed

    private void cb_permissionsItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cb_permissionsItemStateChanged
        if (evt.getItem().toString().equals("Cliente") && this.operation.equals("Add")) {
            viewClientsChooser();
        }
        if (evt.getItem().toString().equals("Cliente") && this.operation.equals("Edit")) {
            jp_Client.setVisible(true);
            unLockClientFields();
        } else {
            lockClientFields();
        }
    }//GEN-LAST:event_cb_permissionsItemStateChanged


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn_save;
    private javax.swing.JComboBox<String> cb_permissions;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private static javax.swing.JPanel jp_Client;
    private static javax.swing.JLabel lb_Cpf;
    private static javax.swing.JLabel lb_Nome;
    private static javax.swing.JLabel lb_Rg;
    private javax.swing.JLabel lb_id;
    private static javax.swing.JTextField txt_Cpf;
    private static javax.swing.JTextField txt_Nome;
    private static javax.swing.JTextField txt_Rg;
    private javax.swing.JTextField txt_id;
    private javax.swing.JTextField txt_login;
    private javax.swing.JPasswordField txt_password;
    // End of variables declaration//GEN-END:variables
}
