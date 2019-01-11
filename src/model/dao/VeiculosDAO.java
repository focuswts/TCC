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
import model.bean.Modelos;
import model.bean.Veiculos;

/**
 *
 * @author focuswts
 */
public class VeiculosDAO {

    private Connection con = null;

    public VeiculosDAO() {
        con = (Connection) ConnectionFactory.getConnection();
    }

    public boolean save(Veiculos v) {
        String sql = "INSERT INTO Veiculos (idModelo,chassi,placa) VALUES (?,?,?)";
        PreparedStatement st = null;
        try {
            st = (PreparedStatement) con.prepareStatement(sql);
            st.setInt(1, v.getIdModelo().getId());
            st.setString(2, v.getChassi());
            st.setString(3, v.getPlaca());
            st.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println("Erro Ao Adicionar Veículo: " + e);
            return false;
        } finally {
            ConnectionFactory.closeConnection(con, st);
        }

    }

    public ArrayList<Veiculos> findAll() {

        String sql = "SELECT Veiculos.*,Marcas.*,Modelos.* FROM Veiculos "
                + "INNER JOIN Modelos ON Veiculos.idModelo = Modelos.id "
                + "INNER JOIN Marcas ON Modelos.idMarca = Marcas.id";

        PreparedStatement st = null;
        ResultSet rs = null;

        ArrayList<Veiculos> Veiculos = new ArrayList<>();

        try {
            st = (PreparedStatement) con.prepareStatement(sql);
            rs = st.executeQuery();

            while (rs.next()) {
                Veiculos v = new Veiculos();
                v.setId(rs.getInt("Veiculos.id"));

                Modelos m = new Modelos();
                m.setId(rs.getInt("Veiculos.idModelo"));
                m.setModelo(rs.getString("Modelos.modelo"));


                v.setIdModelo(m);
                v.setChassi(rs.getString("chassi"));
                v.setPlaca(rs.getString("placa"));
                Veiculos.add(v);
            }

        } catch (SQLException ex) {
            System.err.println("Erro: " + ex);
        } finally {
            ConnectionFactory.closeConnection(con, st, rs);
        }
        System.out.println("Enviado Arraylist Veículos!");
        return Veiculos;
    }

    public ArrayList<Veiculos> findAll(String modelo) {

        String sql = "SELECT * FROM Veiculos "
                + "INNER JOIN Modelos ON Veiculos.idModelo = Modelos.id "
                + "INNER JOIN Marcas on Modelos.idMarca = Marcas.id "
                + "WHERE Modelos.modelo = ?";

        PreparedStatement st = null;
        ResultSet rs = null;

        ArrayList<Veiculos> Veiculos = new ArrayList<>();

        try {
            st = (PreparedStatement) con.prepareStatement(sql);
            st.setString(1, modelo);
            rs = st.executeQuery();

            while (rs.next()) {
                Veiculos v = new Veiculos();
                v.setId(rs.getInt("id"));

                Modelos m = new Modelos();
                m.setId(rs.getInt("idModelo"));


                v.setIdModelo(m);
                v.setChassi(rs.getString("chassi"));
                v.setPlaca(rs.getString("placa"));
                Veiculos.add(v);
            }

        } catch (SQLException ex) {
            System.err.println("Erro: " + ex);
        } finally {
            ConnectionFactory.closeConnection(con, st, rs);
        }
        System.out.println("Enviado Arraylist Veículos!");
        return Veiculos;
    }

    public ArrayList<Veiculos> findAllFilial(String filial) {

        String sql = "SELECT * FROM Veiculos "
                + "INNER JOIN Modelos ON Veiculos.idModelo = Modelos.id "
                + "WHERE Filiais.filial = ?";

        PreparedStatement st = null;
        ResultSet rs = null;

        ArrayList<Veiculos> Veiculos = new ArrayList<>();

        try {
            st = (PreparedStatement) con.prepareStatement(sql);
            st.setString(1, filial);
            rs = st.executeQuery();

            while (rs.next()) {
                Veiculos v = new Veiculos();
                v.setId(rs.getInt("id"));


                Modelos m = new Modelos();
                m.setId(rs.getInt("idModelo"));
                v.setIdModelo(m);
                v.setChassi(rs.getString("chassi"));
                v.setPlaca(rs.getString("placa"));
                Veiculos.add(v);
            }

        } catch (SQLException ex) {
            System.err.println("Erro: " + ex);
        } finally {
            ConnectionFactory.closeConnection(con, st, rs);
        }
        System.out.println("Enviado Arraylist Veículos!");
        return Veiculos;
    }

    public boolean update(Veiculos v) {
        String sql = "UPDATE Veiculos SET idModelo = ?,chassi = ?,placa = ? WHERE id = ?";
        PreparedStatement st = null;

        try {
            st = (PreparedStatement) con.prepareStatement(sql);
            st.setInt(1, v.getIdModelo().getId());
            st.setString(2, v.getChassi());
            st.setString(3, v.getPlaca());
            st.setInt(4, v.getId());
            st.executeUpdate();
            return true;
        } catch (Exception e) {
            System.out.println("Erro Ao Atualizar Veículos!: " + e);
            return false;
        } finally {
            ConnectionFactory.closeConnection(con, st);
        }

    }

    public boolean delete(Veiculos v) {
        String sql = "DELETE FROM Veiculos WHERE id = ?";
        PreparedStatement st = null;
        try {
            st = (PreparedStatement) con.prepareStatement(sql);
            st.setInt(1, v.getId());
            st.executeUpdate();
            return true;
        } catch (Exception e) {
            System.out.println("Erro Ao Excluir Veículo!: " + e);
            return false;
        } finally {
            ConnectionFactory.closeConnection(con, st);
        }

    }

    public int getIdVehicle(String chassi) {
        String sql = "SELECT * FROM Veiculos WHERE Veiculos.chassi = ?";

        PreparedStatement st = null;
        ResultSet rs = null;
        int id = 0;
        try {
            st = (PreparedStatement) con.prepareStatement(sql);
            st.setString(1, chassi);
            rs = st.executeQuery();
            if (rs.next()) {
                id = rs.getInt("Veiculos.id");
                return id;
            }
        } catch (SQLException e) {
            System.out.println("Erro Ao Adquirir ID! " + e);
        }
        return id;
    }

}
