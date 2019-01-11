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
public class RotasAux {

    private int id;
    private Rotas idRota;
    private Pacotes idPacotes;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Rotas getIdRota() {
        return idRota;
    }

    public void setIdRota(Rotas idRota) {
        this.idRota = idRota;
    }

    public Pacotes getIdPacotes() {
        return idPacotes;
    }

    public void setIdPacotes(Pacotes idPacotes) {
        this.idPacotes = idPacotes;
    }

}
