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
import model.bean.FormasEntrega;

/**
 *
 * @author focuswts
 */
public class FormasEntregaDAO {

    private Connection con = null;

    public FormasEntregaDAO() {
        con = (Connection) ConnectionFactory.getConnection();
    }

    public boolean save(FormasEntrega dw) {
        String sql = "INSERT INTO FormasEntrega (forma,valor) VALUES (?,?)";
        PreparedStatement st = null;
        try {
            st = (PreparedStatement) con.prepareStatement(sql);
            st.setString(1, dw.getForma());
            st.setString(2, dw.getValor());

            st.executeUpdate();
            return true;
        } catch (Exception e) {
            System.out.println("Erro Ao Adicionar Forma De Entrega: " + e);
            return false;
        } finally {
            ConnectionFactory.closeConnection(con, st);
        }

    }

    public ArrayList<FormasEntrega> findAll() {

        String sql = "SELECT * FROM FormasEntrega";

        PreparedStatement st = null;
        ResultSet rs = null;

        ArrayList<FormasEntrega> FormasEntrega = new ArrayList<>();

        try {
            st = (PreparedStatement) con.prepareStatement(sql);
            rs = st.executeQuery();

            while (rs.next()) {
                FormasEntrega dw = new FormasEntrega();
                dw.setId(rs.getInt("id"));
                dw.setForma(rs.getString("forma"));
                dw.setValor(rs.getString("valor"));

                FormasEntrega.add(dw);
            }

        } catch (SQLException ex) {
            System.err.println("Erro: " + ex);
        } finally {
            ConnectionFactory.closeConnection(con, st, rs);
        }
        System.out.println("Enviado Arraylist FormasEntrega!");
        return FormasEntrega;
    }

    public ArrayList<FormasEntrega> findAllWhere(FormasEntrega dwAux) {

        String sql = "SELECT * FROM FormasEntrega WHERE forma = ?";

        PreparedStatement st = null;
        ResultSet rs = null;

        ArrayList<FormasEntrega> FormasEntrega = new ArrayList<>();

        try {
            st = (PreparedStatement) con.prepareStatement(sql);
            st.setString(1, dwAux.getForma());
            rs = st.executeQuery();

            while (rs.next()) {
                FormasEntrega dw = new FormasEntrega();
                dw.setId(rs.getInt("id"));
                dw.setForma(rs.getString("forma"));
                dw.setValor(rs.getString("valor"));

                FormasEntrega.add(dw);
            }

        } catch (SQLException ex) {
            System.err.println("Erro: " + ex);
        } finally {
            ConnectionFactory.closeConnection(con, st, rs);
        }
        System.out.println("Enviado Arraylist FormasEntrega!");
        return FormasEntrega;
    }

    public boolean update(FormasEntrega dw) {
        String sql = "UPDATE FormasEntrega SET forma = ?,valor = ? WHERE id = ?";
        PreparedStatement st = null;

        try {
            st = (PreparedStatement) con.prepareStatement(sql);
            st.setString(1, dw.getForma());
            st.setString(2, dw.getValor());
            st.setInt(3, dw.getId());
            st.executeUpdate();
            return true;
        } catch (Exception e) {
            System.out.println("Erro Ao Atualizar Formas De Entrega!: " + e);
            return false;
        } finally {
            ConnectionFactory.closeConnection(con, st);
        }

    }

    public boolean delete(FormasEntrega dw) {
        String sql = "DELETE FROM FormasEntrega WHERE id = ?";
        PreparedStatement st = null;
        try {
            st = (PreparedStatement) con.prepareStatement(sql);
            st.setInt(1, dw.getId());
            st.executeUpdate();
            return true;
        } catch (Exception e) {
            System.out.println("Erro Ao Excluir Forma De Entrega!: " + e);
            return false;
        } finally {
            ConnectionFactory.closeConnection(con, st);
        }

    }

    public FormasEntrega getOBJFromForma(String forma) {
        String sql = "SELECT * FROM FormasEntrega WHERE forma = ?";
        FormasEntrega dw = new FormasEntrega();
        PreparedStatement st = null;
        ResultSet rs = null;
        try {
            st = (PreparedStatement) con.prepareStatement(sql);
            st.setString(1, forma);
            rs = st.executeQuery();
            if (rs.next()) {
                dw.setId(rs.getInt("id"));
                dw.setForma(rs.getString("forma"));
                dw.setValor(rs.getString("valor"));
                return dw;
            }
        } catch (SQLException e) {
            System.out.println("Erro Ao Adquirir OBJ! " + e);
        }
        return dw;
    }

}
