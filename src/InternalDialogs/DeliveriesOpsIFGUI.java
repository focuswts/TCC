/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package InternalDialogs;

import InternalManagementViews.DeliveriesMgmtIFGUI;
import java.beans.PropertyVetoException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;
import model.bean.Entregas;
import model.bean.SituacoesEntrega;
import model.bean.Pacotes;
import model.bean.Rotas;
import model.bean.RotasAux;
import model.dao.EntregasDAO;
import model.dao.SituacoesEntregaDAO;
import model.dao.PacotesDAO;
import model.dao.RotasAuxDAO;

/**
 *
 * @author focuswts
 */
public class DeliveriesOpsIFGUI extends javax.swing.JInternalFrame {

    /**
     * Creates new form DeliveriesOpsIFGUI
     */
    private static Pacotes p;
    private static Rotas r;
    private static String operacao;
    private static Entregas d;
    private static SituacoesEntrega ds;
    private boolean existRoute;
    private static ArrayList<Rotas> rotas;

    public DeliveriesOpsIFGUI() {
        initComponents();
    }

    public DeliveriesOpsIFGUI(java.awt.Frame parent, boolean modal) {
        initComponents();
        fillSituacoes();
        getDateTime();
    }

    public DeliveriesOpsIFGUI(java.awt.Frame parent, boolean modal, String operacao, Entregas d, SituacoesEntrega ds) {
        initComponents();
        this.operacao = operacao;
        this.d = d;
        this.ds = ds;
        fillSituacoes();
        adaptView();
    }

    public DeliveriesOpsIFGUI(java.awt.Frame parent, boolean modal, String operacao, Pacotes p, Rotas r, boolean routeExist) {
        initComponents();
        this.operacao = operacao;
        this.p = p;
        this.r = r;
        fillSituacoes();
        adaptView();
        getDateTime();
    }

    public DeliveriesOpsIFGUI(java.awt.Frame parent, boolean modal, String operacao, Pacotes p, Rotas r, boolean routeExist, ArrayList<Rotas> rotas) {
        initComponents();
        this.operacao = operacao;
        this.p = p;
        this.r = r;
        this.rotas = rotas;
        adaptView();
        fillSituacoes();
        fillInfo(rotas);
        getDateTime();
    }

    private void adaptView() {
        try {
            fillInfoFromOBJ();
            if (this.operacao.equals("Add")) {
                lb_Data.setVisible(false);
                txt_Data.setVisible(false);
                lb_Hora.setVisible(false);
                txt_Hora.setVisible(false);
            }
            if (this.operacao.equals("Edit")) {
                fillInfoFromOBJ();
                lockFields();
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro Ao Adaptar View: " + e);
        }

    }

    private boolean doOperation() {
        PacotesDAO pdAO = new PacotesDAO();
        RotasAuxDAO rauxDAO = new RotasAuxDAO();
        EntregasDAO dDAO = new EntregasDAO();
        boolean check = false;
        try {
            if (this.operacao.equals("Add")) {
                RotasAux ra = new RotasAux();
                ra.setIdPacotes(p);
                ra.setIdRota(r);
                int idRotaAux = rauxDAO.saveGetId(ra);
                ra.setId(idRotaAux);

                Entregas d = new Entregas();
                d.setIdRotaAux(ra);

                d.setLocal(txt_Local.getText());

                SituacoesEntrega ds = (SituacoesEntrega) cb_Situacao.getModel().getSelectedItem();
                d.setSituacao(ds.getSituacao());
                d.setData(getDateTime());
                d.setStatus("Ativo");
                p.setEstadoEntrega("Entrega");

                if (dDAO.save(d) == true) {
                    if (pdAO.updateEntrega(p) == true) {
                        JOptionPane.showMessageDialog(this, "Pacote Encaminhado Para Entrega!");
                        check = true;
                    }
                } else {
                    JOptionPane.showMessageDialog(this, "Erro Ao Encaminhar Pacote Para Entrega");
                }
            }

            if (this.operacao.equals("Edit")) {
                fieldsToOBJ();
                if (dDAO.update(this.d) == true) {
                    JOptionPane.showMessageDialog(this, "Situação Da Entrega Atualizada Com Sucesso!");
                    this.dispose();
                } else {
                    JOptionPane.showMessageDialog(this, "Erro Ao Atualizar Situação Da Entrega");
                }

            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro Ao Efetuar Operação: " + e);
        }
        return check;
    }

    private Entregas fieldsToOBJ() {
        try {
            Entregas delivery = new Entregas();

            SituacoesEntrega deliverySituation = (SituacoesEntrega) cb_Situacao.getModel().getSelectedItem();
            String situacao = deliverySituation.getSituacao();

            delivery.setSituacao(situacao);
            delivery.setLocal(txt_Local.getText());

            //Atualiza A Hora 
            delivery.setData(getDateTime());

            this.d.setSituacao(situacao);
            this.d.setLocal(txt_Local.getText());
            this.d.setData(getDateTime());
            return delivery;
        } catch (Exception e) {
            System.out.println("Erro Ao Criar OBJ Delivery: " + e);
            return null;
        }

    }

    private void lockFields() {
        txt_Funcionario.setEditable(false);
        txt_Veiculo.setEditable(false);
        txt_Remetente.setEditable(false);
        txt_Destinatario.setEditable(false);
        txt_Hora.setEditable(false);
        txt_Data.setEditable(false);
    }

    private void fillSituacoes() {

        SituacoesEntregaDAO dsDAO = new SituacoesEntregaDAO();
        ArrayList<SituacoesEntrega> situacoes = null;

        try {
            cb_Situacao.removeAllItems();
            situacoes = dsDAO.findAll();
            DefaultComboBoxModel situationsModel = new DefaultComboBoxModel();

            for (SituacoesEntrega deliveryS : situacoes) {
                situationsModel.addElement(deliveryS);
            }
            cb_Situacao.setModel(situationsModel);
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    private String getDateTime() {
        String dateTime = "";
        try {
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/uuuu");
            LocalDate dataHoje = LocalDate.now();
            String dataHojeFormatted = dtf.format(dataHoje);
            txt_Data.setText(dataHojeFormatted);

            DateTimeFormatter dtf2 = DateTimeFormatter.ofPattern("HH:mm:ss");
            LocalTime horaAtual = LocalTime.now();
            String horaAtualFormatted = dtf2.format(horaAtual);
            txt_Hora.setText(horaAtualFormatted);

            dateTime = dataHoje + " " + horaAtual;
            System.out.println(dateTime);
            return dateTime;
        } catch (Exception e) {
            System.out.println(e);
            return null;
        }

    }

    private void viewDeliveries() {
        try {
            DeliveriesMgmtIFGUI viewDeliveries = new DeliveriesMgmtIFGUI();
            this.getParent().add(viewDeliveries);
            viewDeliveries.toFront();
            viewDeliveries.setMaximum(true);
            viewDeliveries.setVisible(true);
        } catch (Exception e) {
        }
    }

    private void fillInfo(ArrayList<Rotas> rotas) {
        try {
            for (int i = 0; i < rotas.size(); i++) {
                String funcionario = rotas.get(i).getIdFuncionario().getNome();
                String veiculo = rotas.get(i).getIdVeiculo().getIdModelo().getModelo();
                String remetente = rotas.get(i).getRemetente();
                String destinatario = rotas.get(i).getDestinatario();

                //TODO 
                txt_Funcionario.setText(funcionario);
                txt_Veiculo.setText(veiculo);
                txt_Remetente.setText(remetente);
                txt_Destinatario.setText(destinatario);
            }

        } catch (Exception e) {
            System.out.println("Erro Ao Preencher Info: " + e);
        }
    }

    private void fillInfoFromOBJ() {
        try {
            System.out.println("Delivery ID: " + d.getId());

            String nomeFuncionario = d.getIdRotaAux().getIdRota().getIdFuncionario().getNome();
            String modeloVeiculo = d.getIdRotaAux().getIdRota().getIdVeiculo().getIdModelo().getModelo();
            String remetente = d.getIdRotaAux().getIdRota().getRemetente();
            String destinatario = d.getIdRotaAux().getIdRota().getDestinatario();

            String dateTime = d.getData();

//PEGA Valores Do Campo DateTime MYSQL Através do Atributo String Classe Delivery
            String dataDB = dateTime.substring(0, 10);
            String horaDB = dateTime.substring(11, 19);

            //Define O Valor Das Variáveis Para Os Fields
            txt_Funcionario.setText(nomeFuncionario);
            txt_Veiculo.setText(modeloVeiculo);
            txt_Remetente.setText(remetente);
            txt_Destinatario.setText(destinatario);
            cb_Situacao.getModel().setSelectedItem(this.ds);
            //Cria OBJ Filiais Para Assimilar Ao ComboBox
            txt_Local.setText(d.getLocal());
///////////////////////////////////////////////////////
            txt_Data.setText(dataDB);
            txt_Hora.setText(horaDB);
        } catch (Exception e) {
            System.out.println("Erro Ao Preencher Informações: " + e);
        }

    }

    public static Entregas getD() {
        return d;
    }

    public static void setD(Entregas aD) {
        d = aD;
    }

    public static SituacoesEntrega getDs() {
        return ds;
    }

    public static void setDs(SituacoesEntrega aDs) {
        ds = aDs;
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
        jLabel1 = new javax.swing.JLabel();
        txt_Funcionario = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        txt_Veiculo = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        txt_Remetente = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        txt_Destinatario = new javax.swing.JTextField();
        lb_Situation = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        cb_Situacao = new javax.swing.JComboBox<>();
        lb_Data = new javax.swing.JLabel();
        txt_Data = new javax.swing.JTextField();
        lb_Hora = new javax.swing.JLabel();
        txt_Hora = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        txt_Local = new javax.swing.JTextField();
        jPanel4 = new javax.swing.JPanel();
        btn_Save = new javax.swing.JButton();

        setClosable(true);
        setTitle("Operações De Entregas");
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

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder("Dados Da Rota Atual Do Pacote"));

        jLabel1.setText("Funcionário");

        jLabel2.setText("Veículo");

        jLabel3.setText("Remetente");

        jLabel4.setText("Destinatario");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txt_Remetente)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1)
                            .addComponent(txt_Funcionario, javax.swing.GroupLayout.PREFERRED_SIZE, 450, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel2)
                            .addComponent(txt_Veiculo, javax.swing.GroupLayout.PREFERRED_SIZE, 340, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel3)
                            .addComponent(jLabel4))
                        .addGap(0, 179, Short.MAX_VALUE))
                    .addComponent(txt_Destinatario))
                .addContainerGap())
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(220, 220, 220)
                .addComponent(lb_Situation)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txt_Funcionario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txt_Veiculo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txt_Remetente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txt_Destinatario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lb_Situation)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder("Situação Da Entrega"));

        jLabel5.setText("Situação ");

        cb_Situacao.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        lb_Data.setText("Data Última Atualização");

        lb_Hora.setText("Hora Última Atualização");

        jLabel8.setText("Local Atual Do Pacote");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(lb_Hora)
                    .addComponent(jLabel5)
                    .addComponent(lb_Data)
                    .addComponent(txt_Hora, javax.swing.GroupLayout.DEFAULT_SIZE, 180, Short.MAX_VALUE)
                    .addComponent(txt_Data)
                    .addComponent(jLabel8)
                    .addComponent(cb_Situacao, 0, 360, Short.MAX_VALUE)
                    .addComponent(txt_Local))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cb_Situacao, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel8)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(txt_Local, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10)
                .addComponent(lb_Data)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txt_Data, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(lb_Hora)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txt_Hora, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        btn_Save.setText("Enviar Para Entrega");
        btn_Save.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_SaveActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btn_Save)
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addGap(0, 33, Short.MAX_VALUE)
                .addComponent(btn_Save))
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
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

    private void btn_SaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_SaveActionPerformed
        if (doOperation() == true) {
            this.dispose();
            viewDeliveries();
        }
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
    private javax.swing.JComboBox<String> cb_Situacao;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JLabel lb_Data;
    private javax.swing.JLabel lb_Hora;
    private javax.swing.JLabel lb_Situation;
    private javax.swing.JTextField txt_Data;
    private javax.swing.JTextField txt_Destinatario;
    private javax.swing.JTextField txt_Funcionario;
    private javax.swing.JTextField txt_Hora;
    private javax.swing.JTextField txt_Local;
    private javax.swing.JTextField txt_Remetente;
    private javax.swing.JTextField txt_Veiculo;
    // End of variables declaration//GEN-END:variables
}
