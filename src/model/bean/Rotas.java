/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.bean;

/**
 *
 * @author focuswts
 */
public class Rotas {

    private int id;
    private Funcionarios idFuncionario;
    private Veiculos idVeiculo;
    private String remetente;
    private String destinatario;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Funcionarios getIdFuncionario() {
        return idFuncionario;
    }

    public void setIdFuncionario(Funcionarios idFuncionario) {
        this.idFuncionario = idFuncionario;
    }

    public Veiculos getIdVeiculo() {
        return idVeiculo;
    }

    public void setIdVeiculo(Veiculos idVeiculo) {
        this.idVeiculo = idVeiculo;
    }

    public String getRemetente() {
        return remetente;
    }

    public void setRemetente(String remetente) {
        this.remetente = remetente;
    }

    public String getDestinatario() {
        return destinatario;
    }

    public void setDestinatario(String destinatario) {
        this.destinatario = destinatario;
    }

}
