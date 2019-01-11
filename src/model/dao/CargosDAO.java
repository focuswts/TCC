/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.dao;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;
import connection.ConnectionFactory;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import model.bean.Cargos;

/**
 *
 * @author focuswts
 */
public class CargosDAO {

    private Connection con = null;

    public CargosDAO() {
        con = (Connection) ConnectionFactory.getConnection();
    }

    public boolean save(Cargos job) {
        String sql = "INSERT INTO Cargos (funcao) VALUES (?)";

        PreparedStatement st = null;
        try {
            st = (PreparedStatement) con.prepareStatement(sql);
            st.setString(1, job.getFuncao());
            st.executeUpdate();
            return true;
        } catch (SQLException ex) {
            System.out.println("Erro Ao Incluir Cargo: " + ex);
            return false;
        } finally {
            ConnectionFactory.closeConnection(con, st);
        }
    }

    public boolean update(Cargos job) {
        String sql = "UPDATE Cargos SET funcao = ? WHERE id = ?";

        PreparedStatement st = null;
        try {
            st = (PreparedStatement) con.prepareStatement(sql);
            st.setString(1, job.getFuncao());
            st.executeUpdate();
            return true;
        } catch (SQLException ex) {
            System.out.println("Erro Ao Atualizar Cargo: " + ex);
            return false;
        } finally {
            ConnectionFactory.closeConnection(con, st);
        }
    }

    public boolean delete(Cargos job) {
        String sql = "DELETE FROM Cargos WHERE id = ?";

        PreparedStatement st = null;
        try {
            st = (PreparedStatement) con.prepareStatement(sql);
            st.setInt(1, job.getId());
            st.executeUpdate();
            return true;
        } catch (SQLException ex) {
            System.out.println("Erro Ao Excluir Cargo: " + ex);
            return false;
        } finally {
            ConnectionFactory.closeConnection(con, st);
        }
    }

    public ArrayList<Cargos> findAll() {

        String sql = "SELECT * FROM Cargos";

        PreparedStatement st = null;
        ResultSet rs = null;

        ArrayList<Cargos> Cargos = new ArrayList<>();

        try {
            st = (PreparedStatement) con.prepareStatement(sql);
            rs = st.executeQuery();

            while (rs.next()) {
                Cargos job = new Cargos();
                job.setId(rs.getInt("id"));
                job.setFuncao(rs.getString("funcao"));
                Cargos.add(job);
            }

        } catch (SQLException ex) {
            System.err.println("Erro: " + ex);
        } finally {
            ConnectionFactory.closeConnection(con, st, rs);
        }
        System.out.println("Enviado Arraylist Cargos!");
        return Cargos;
    }

    public int getIdCargos(String funcao) {
        String sql = "SELECT * FROM Cargos WHERE funcao = ?";

        PreparedStatement st = null;
        ResultSet rs = null;
        int id = 0;
        try {
            st = (PreparedStatement) con.prepareStatement(sql);
            st.setString(1, funcao);
            rs = st.executeQuery();
            if (rs.next()) {
                id = rs.getInt("id");
                return id;
            }
        } catch (SQLException e) {
            System.out.println("Erro Ao Adquirir ID! " + e);
        }
        return id;
    }

}
