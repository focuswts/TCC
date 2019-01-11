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
public class ItensPacote {

    private int id;
    private Pacotes idPacote;
    private String item;
    private int qtd;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Pacotes getIdPacote() {
        return idPacote;
    }

    public void setIdPacote(Pacotes idPacote) {
        this.idPacote = idPacote;
    }

    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
    }

    public int getQtd() {
        return qtd;
    }

    public void setQtd(int qtd) {
        this.qtd = qtd;
    }

}
