/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tccv1;

import ExternalViews.loginGUI;

/**
 *
 * @author focuswts
 */
public class TCCV1 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        viewLogin();
    }

    public static void viewLogin() {
        try {
            loginGUI viewLogin = new loginGUI();
            viewLogin.setVisible(true);
        } catch (Exception e) {
            System.out.println("Erro AO Abrir Tela Login: " + e);
        }
    }
}
