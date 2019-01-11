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
public class Veiculos {

    private int id;
    private Modelos idModelo;
    private String chassi;
    private String placa;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getChassi() {
        return chassi;
    }

    public void setChassi(String chassi) {
        this.chassi = chassi;
    }

    public String getPlaca() {
        return placa;
    }

    public void setPlaca(String placa) {
        this.placa = placa;
    }

    public Modelos getIdModelo() {
        return idModelo;
    }

    public void setIdModelo(Modelos idModelo) {
        this.idModelo = idModelo;
    }

    public String toString() {
        return this.getIdModelo().getModelo();
    }



}
