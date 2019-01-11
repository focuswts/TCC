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
import model.bean.Modelos;

/**
 *
 * @author focuswts
 */
public class ModelosDAO {

    private Connection con = null;

    public ModelosDAO() {
        con = (Connection) ConnectionFactory.getConnection();
    }

    public boolean save(Modelos m) {
        String sql = "INSERT INTO Modelos (modelo,idMarca) VALUES (?,?)";
        PreparedStatement st = null;
        try {
            st = (PreparedStatement) con.prepareStatement(sql);
            st.setString(1, m.getModelo());
            st.setInt(2, m.getIdMarca().getId());

            st.executeUpdate();
            return true;
        } catch (Exception e) {
            System.out.println("Erro Ao Adicionar Modelo: " + e);
            return false;
        } finally {
            ConnectionFactory.closeConnection(con, st);
        }

    }

    public ArrayList<Modelos> findAll() {

        String sql = "SELECT Modelos.*,Marcas.*,Modelos.* FROM Modelos "
                + "INNER JOIN Marcas ON Modelos.idMarca = Marcas.id";

        PreparedStatement st = null;
        ResultSet rs = null;

        ArrayList<Modelos> Modelos = new ArrayList<>();

        try {
            st = (PreparedStatement) con.prepareStatement(sql);
            rs = st.executeQuery();

            while (rs.next()) {
                Modelos m = new Modelos();
                m.setId(rs.getInt("id"));

                Marcas b = new Marcas();
                m.setIdMarca(b);
                m.setModelo(rs.getString("Modelos.modelo"));

                b.setId(rs.getInt("Marcas.id"));
                b.setMarca(rs.getString("Marcas.marca"));
                Modelos.add(m);
            }

        } catch (SQLException ex) {
            System.err.println("Erro: " + ex);
        } finally {
            ConnectionFactory.closeConnection(con, st, rs);
        }
        System.out.println("Enviado Arraylist Modelos!");
        return Modelos;
    }

    public ArrayList<Modelos> findAll(String marca) {

        String sql = "SELECT * FROM Modelos "
                + "INNER JOIN Marcas ON Modelos.idMarca = Marcas.id "
                + "WHERE Marcas.marca = ?";

        PreparedStatement st = null;
        ResultSet rs = null;

        ArrayList<Modelos> Modelos = new ArrayList<>();

        try {
            st = (PreparedStatement) con.prepareStatement(sql);
            st.setString(1, marca);
            rs = st.executeQuery();

            while (rs.next()) {
                Modelos m = new Modelos();
                m.setId(rs.getInt("id"));

                Marcas b = new Marcas();
                m.setIdMarca(b);
                m.setModelo(rs.getString("modelo"));

                b.setId(rs.getInt("idMarca"));

                Modelos.add(m);
            }

        } catch (SQLException ex) {
            System.err.println("Erro: " + ex);
        } finally {
            ConnectionFactory.closeConnection(con, st, rs);
        }
        System.out.println("Enviado Arraylist Modelos!");
        return Modelos;
    }

    public boolean update(Modelos m) {
        String sql = "UPDATE Modelos SET modelo = ?,idMarca = ? WHERE id = ?";
        PreparedStatement st = null;

        try {
            st = (PreparedStatement) con.prepareStatement(sql);
            st.setString(1, m.getModelo());
            st.setInt(2, m.getIdMarca().getId());
            st.setInt(3, m.getId());
            st.executeUpdate();
            return true;
        } catch (Exception e) {
            System.out.println("Erro Ao Atualizar Modelo!: " + e);
            return false;
        } finally {
            ConnectionFactory.closeConnection(con, st);
        }

    }

    public boolean delete(Modelos m) {
        String sql = "DELETE FROM Modelos WHERE id = ?";
        PreparedStatement st = null;
        try {
            st = (PreparedStatement) con.prepareStatement(sql);
            st.setInt(1, m.getId());
            st.executeUpdate();
            return true;
        } catch (Exception e) {
            System.out.println("Erro Ao Excluir Modelo!: " + e);
            return false;
        } finally {
            ConnectionFactory.closeConnection(con, st);
        }

    }

    public int getIdModelo(String modelo) {
        String sql = "SELECT * FROM Modelos WHERE modelo = ?";

        PreparedStatement st = null;
        ResultSet rs = null;
        int id = 0;
        try {
            st = (PreparedStatement) con.prepareStatement(sql);
            st.setString(1, modelo);
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
