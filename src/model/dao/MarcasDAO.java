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
import model.bean.Marcas;

/**
 *
 * @author focuswts
 */
public class MarcasDAO {

    private Connection con = null;

    public MarcasDAO() {
        con = (Connection) ConnectionFactory.getConnection();
    }

    public boolean save(Marcas b) {
        String sql = "INSERT INTO Marcas (marca) VALUES (?)";
        PreparedStatement st = null;
        try {
            st = (PreparedStatement) con.prepareStatement(sql);
            st.setString(1, b.getMarca());

            st.executeUpdate();
            return true;
        } catch (Exception e) {
            System.out.println("Erro Ao Adicionar Fabricante: " + e);
            return false;
        } finally {
            ConnectionFactory.closeConnection(con, st);
        }

    }

    public ArrayList<Marcas> findAll() {

        String sql = "SELECT * FROM Marcas";

        PreparedStatement st = null;
        ResultSet rs = null;

        ArrayList<Marcas> Fabricantes = new ArrayList<>();

        try {
            st = (PreparedStatement) con.prepareStatement(sql);
            rs = st.executeQuery();

            while (rs.next()) {
                Marcas b = new Marcas();
                b.setId(rs.getInt("id"));
                b.setMarca(rs.getString("marca"));
                Fabricantes.add(b);
            }

        } catch (SQLException ex) {
            System.err.println("Erro: " + ex);
        } finally {
            ConnectionFactory.closeConnection(con, st, rs);
        }
        System.out.println("Enviado Arraylist Fabricantes!");
        return Fabricantes;
    }

    public boolean update(Marcas b) {
        String sql = "UPDATE Marcas SET marca = ? WHERE id = ?";
        PreparedStatement st = null;

        try {
            st = (PreparedStatement) con.prepareStatement(sql);
            st.setString(1, b.getMarca());
            st.setInt(2, b.getId());
            st.executeUpdate();
            return true;
        } catch (Exception e) {
            System.out.println("Erro Ao Atualizar Fabricantes!: " + e);
            return false;
        } finally {
            ConnectionFactory.closeConnection(con, st);
        }

    }

    public boolean delete(Marcas b) {
        String sql = "DELETE FROM Marcas WHERE id = ?";
        PreparedStatement st = null;
        try {
            st = (PreparedStatement) con.prepareStatement(sql);
            st.setInt(1, b.getId());
            st.executeUpdate();
            return true;
        } catch (Exception e) {
            System.out.println("Erro Ao Excluir Marca!: " + e);
            return false;
        } finally {
            ConnectionFactory.closeConnection(con, st);
        }

    }

    public int getIdMarca(String marca) {
        String sql = "SELECT * FROM Marcas WHERE marca = ?";

        PreparedStatement st = null;
        ResultSet rs = null;
        int id = 0;
        try {
            st = (PreparedStatement) con.prepareStatement(sql);
            st.setString(1, marca);
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
