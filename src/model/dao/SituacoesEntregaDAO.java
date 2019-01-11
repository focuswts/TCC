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
import model.bean.SituacoesEntrega;

/**
 *
 * @author focuswts
 */
public class SituacoesEntregaDAO {

    private Connection con = null;

    public SituacoesEntregaDAO() {
        con = (Connection) ConnectionFactory.getConnection();
    }

    public boolean save(SituacoesEntrega ds) {
        String sql = "INSERT INTO SituacoesEntrega (situacao) VALUES (?)";

        PreparedStatement st = null;
        try {
            st = (PreparedStatement) con.prepareStatement(sql);
            st.setString(1, ds.getSituacao());
            st.executeUpdate();
            return true;
        } catch (SQLException ex) {
            System.out.println("Erro Ao Incluir Situação: " + ex);
            return false;
        } finally {
            ConnectionFactory.closeConnection(con, st);
        }
    }

    public boolean update(SituacoesEntrega ds) {
        String sql = "UPDATE SituacoesEntrega SET situacao = ? WHERE id = ?";

        PreparedStatement st = null;
        try {
            st = (PreparedStatement) con.prepareStatement(sql);
            st.setString(1, ds.getSituacao());
            st.setInt(2, ds.getId());
            st.executeUpdate();
            return true;
        } catch (SQLException ex) {
            System.out.println("Erro Ao Atualizar Situação: " + ex);
            return false;
        } finally {
            ConnectionFactory.closeConnection(con, st);
        }
    }

    public boolean delete(SituacoesEntrega ds) {
        String sql = "DELETE FROM SituacoesEntrega WHERE id = ?";

        PreparedStatement st = null;
        try {
            st = (PreparedStatement) con.prepareStatement(sql);
            st.setInt(1, ds.getId());
            st.executeUpdate();
            return true;
        } catch (SQLException ex) {
            System.out.println("Erro Ao Excluir Situação: " + ex);
            return false;
        } finally {
            ConnectionFactory.closeConnection(con, st);
        }
    }

    public ArrayList<SituacoesEntrega> findAll() {

        String sql = "SELECT * FROM SituacoesEntrega";

        PreparedStatement st = null;
        ResultSet rs = null;

        ArrayList<SituacoesEntrega> Situacoes = new ArrayList<>();

        try {
            st = (PreparedStatement) con.prepareStatement(sql);
            rs = st.executeQuery();

            while (rs.next()) {
                SituacoesEntrega ds = new SituacoesEntrega();
                ds.setId(rs.getInt("id"));
                ds.setSituacao(rs.getString("situacao"));
                Situacoes.add(ds);
            }

        } catch (SQLException ex) {
            System.err.println("Erro: " + ex);
        } finally {
            ConnectionFactory.closeConnection(con, st, rs);
        }
        System.out.println("Enviado Arraylist Situações!");
        return Situacoes;
    }

    
    public SituacoesEntrega getOBJFromSituacao(String situacao) {
        String sql = "SELECT * FROM SituacoesEntrega WHERE situacao = ?";
        SituacoesEntrega ds = new SituacoesEntrega();
        PreparedStatement st = null;
        ResultSet rs = null;
        try {
            st = (PreparedStatement) con.prepareStatement(sql);
            st.setString(1, situacao);
            rs = st.executeQuery();
            if (rs.next()) {
                ds.setId(rs.getInt("id"));
                ds.setSituacao(rs.getString("situacao"));
                return ds;
            }
        } catch (SQLException e) {
            System.out.println("Erro Ao Adquirir OBJ! " + e);
        }
        return ds;
    }

}
