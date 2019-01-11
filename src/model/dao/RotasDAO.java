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
import model.bean.Rotas;
import model.bean.Veiculos;
import model.bean.Funcionarios;

/**
 *
 * @author focuswts
 */
public class RotasDAO {

    private Connection con = null;

    public RotasDAO() {
        con = (Connection) ConnectionFactory.getConnection();
    }

    public boolean save(Rotas r) {
        String sql = "INSERT INTO Rotas (idFuncionario,idVeiculo,remetente,destinatario) VALUES (?,?,?,?)";
        PreparedStatement st = null;
        try {
            st = (PreparedStatement) con.prepareStatement(sql);
            st.setInt(1, r.getIdFuncionario().getId());
            st.setInt(2, r.getIdVeiculo().getId());
            st.setString(3, r.getRemetente());
            st.setString(4, r.getDestinatario());

            st.executeUpdate();
            return true;
        } catch (Exception e) {
            System.out.println("Erro Ao Adicionar Rota: " + e);
            return false;
        } finally {
            ConnectionFactory.closeConnection(con, st);
        }

    }

    public ArrayList<Rotas> findAll() {

        String sql = "SELECT Rotas.*,Funcionarios.*,Veiculos.*,Modelos.* FROM Rotas "
                + "INNER JOIN Funcionarios ON Rotas.idFuncionario = Funcionarios.id "
                + "INNER JOIN Veiculos ON Rotas.idVeiculo = Veiculos.id "
                + "INNER JOIN Modelos ON Veiculos.idModelo = Modelos.id";

        PreparedStatement st = null;
        ResultSet rs = null;

        ArrayList<Rotas> Rotas = new ArrayList<>();

        try {
            st = (PreparedStatement) con.prepareStatement(sql);
            rs = st.executeQuery();

            while (rs.next()) {
                Rotas r = new Rotas();
                r.setId(rs.getInt("Rotas.id"));

                Funcionarios w = new Funcionarios();
                w.setId(rs.getInt("Rotas.idFuncionario"));
                w.setNome(rs.getString("Funcionarios.nome"));
                w.setCpf(rs.getString("Funcionarios.cpf"));
                w.setRg(rs.getString("Funcionarios.rg"));
                w.setEndereco(rs.getString("Funcionarios.endereco"));
                w.setTelefone(rs.getString("Funcionarios.telefone"));
                w.setCelular(rs.getString("Funcionarios.celular"));
                w.setEmail(rs.getString("Funcionarios.email"));

                r.setIdFuncionario(w);

                Modelos m = new Modelos();
                m.setId(rs.getInt("Veiculos.idModelo"));
                m.setModelo(rs.getString("Modelos.modelo"));

                Veiculos v = new Veiculos();
                v.setId(rs.getInt("Rotas.idVeiculo"));
                v.setIdModelo(m);
                v.setChassi(rs.getString("Veiculos.chassi"));
                v.setPlaca(rs.getString("Veiculos.placa"));

                r.setIdVeiculo(v);
                r.setRemetente(rs.getString("Rotas.remetente"));
                r.setDestinatario(rs.getString("Rotas.destinatario"));
                Rotas.add(r);
            }

        } catch (SQLException ex) {
            System.err.println("Erro: " + ex);
        } finally {
            ConnectionFactory.closeConnection(con, st, rs);
        }
        System.out.println("Enviado Arraylist Rotas!");
        return Rotas;
    }

    public ArrayList<Rotas> findAllWhere(String remetente, String destinatario) {

        String sql = "SELECT Rotas.*,Funcionarios.*,Veiculos.*,Modelos.* FROM Rotas "
                + "INNER JOIN Funcionarios ON Rotas.idFuncionario = Funcionarios.id "
                + "INNER JOIN Veiculos ON Rotas.idVeiculo = Veiculos.id "
                + "INNER JOIN Modelos ON Veiculos.idModelo = Modelos.id "
                + "WHERE Rotas.remetente = ? AND Rotas.destinatario = ? ";

        PreparedStatement st = null;
        ResultSet rs = null;

        ArrayList<Rotas> Rotas = new ArrayList<>();

        try {
            st = (PreparedStatement) con.prepareStatement(sql);
            st.setString(1, remetente);
            st.setString(2, destinatario);
            rs = st.executeQuery();

            while (rs.next()) {
                Rotas r = new Rotas();
                r.setId(rs.getInt("Rotas.id"));

                Funcionarios w = new Funcionarios();
                w.setId(rs.getInt("Rotas.idFuncionario"));
                w.setNome(rs.getString("Funcionarios.nome"));
                w.setCpf(rs.getString("Funcionarios.cpf"));
                w.setRg(rs.getString("Funcionarios.rg"));
                w.setEndereco(rs.getString("Funcionarios.endereco"));
                w.setTelefone(rs.getString("Funcionarios.telefone"));
                w.setCelular(rs.getString("Funcionarios.celular"));
                w.setEmail(rs.getString("Funcionarios.email"));

                r.setIdFuncionario(w);

                Modelos m = new Modelos();
                m.setId(rs.getInt("Veiculos.idModelo"));
                m.setModelo(rs.getString("Modelos.modelo"));

                Veiculos v = new Veiculos();
                v.setId(rs.getInt("Rotas.idVeiculo"));
                v.setIdModelo(m);
                v.setChassi(rs.getString("Veiculos.chassi"));
                v.setPlaca(rs.getString("Veiculos.placa"));

                r.setIdVeiculo(v);
                r.setRemetente(rs.getString("Rotas.remetente"));
                r.setDestinatario(rs.getString("Rotas.destinatario"));
                Rotas.add(r);
            }

        } catch (SQLException ex) {
            System.err.println("Erro: " + ex);
        } finally {
            ConnectionFactory.closeConnection(con, st, rs);
        }
        System.out.println("Enviado Arraylist Rotas Where!");
        return Rotas;
    }

    public boolean update(Rotas r) {
        String sql = "UPDATE Rotas SET idFuncionario = ?, idVeiculo = ?,remetente = ?,destinatario = ? WHERE id = ?";
        PreparedStatement st = null;

        try {
            st = (PreparedStatement) con.prepareStatement(sql);
            st.setInt(1, r.getIdFuncionario().getId());
            st.setInt(2, r.getIdVeiculo().getId());
            st.setString(3, r.getRemetente());
            st.setString(4, r.getDestinatario());
            st.setInt(5, r.getId());
            st.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println("Erro Ao Atualizar Rota!: " + e);
            return false;
        } finally {
            ConnectionFactory.closeConnection(con, st);
        }

    }

    public boolean delete(Rotas r) {
        String sql = "DELETE FROM Rotas WHERE id = ?";
        PreparedStatement st = null;
        try {
            st = (PreparedStatement) con.prepareStatement(sql);
            st.setInt(1, r.getId());
            st.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println("Erro Ao Excluir Rota!: " + e);
            return false;
        } finally {
            ConnectionFactory.closeConnection(con, st);
        }

    }

    public int getIdRota(String remetente, String destinatario) {
        String sql = "SELECT * FROM Rotas "
                + "WHERE remetente = ? AND destinatario = ?";

        PreparedStatement st = null;
        ResultSet rs = null;
        int id = 0;
        try {
            st = (PreparedStatement) con.prepareStatement(sql);
            st.setString(1, remetente);
            st.setString(2, destinatario);
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
