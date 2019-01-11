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
import model.bean.Funcionarios;

/**
 *
 * @author focuswts
 */
public class FuncionariosDAO {

    private Connection con = null;

    public FuncionariosDAO() {
        con = (Connection) ConnectionFactory.getConnection();
    }

    public boolean save(Funcionarios worker) {
        String sql = "INSERT INTO Funcionarios (nome,cpf,rg,endereco,telefone,celular,email,idCargo) VALUES (?,?,?,?,?,?,?,?)";

        PreparedStatement st = null;
        try {
            st = (PreparedStatement) con.prepareStatement(sql);
            st.setString(1, worker.getNome());
            st.setString(2, worker.getCpf());
            st.setString(3, worker.getRg());
            st.setString(4, worker.getEndereco());
            st.setString(5, worker.getTelefone());
            st.setString(6, worker.getCelular());
            st.setString(7, worker.getEmail());
            st.setInt(8, worker.getIdCargo().getId());

            st.executeUpdate();
            return true;
        } catch (SQLException ex) {
            System.out.println("Erro Ao Incluir Funcionário: " + ex);
            return false;
        } finally {
            ConnectionFactory.closeConnection(con, st);
        }
    }

    public ArrayList<Funcionarios> findAll() {

        String sql = "SELECT Funcionarios.*,Cargos.funcao FROM Funcionarios "
                + "INNER JOIN Cargos ON Funcionarios.idCargo = Cargos.id ";

        PreparedStatement st = null;
        ResultSet rs = null;

        ArrayList<Funcionarios> Funcionarios = new ArrayList<>();

        try {
            st = (PreparedStatement) con.prepareStatement(sql);
            rs = st.executeQuery();

            while (rs.next()) {
                Funcionarios worker = new Funcionarios();
                worker.setId(rs.getInt("id"));
                worker.setNome(rs.getString("nome"));
                worker.setCpf(rs.getString("cpf"));
                worker.setRg(rs.getString("rg"));
                worker.setEndereco(rs.getString("endereco"));
                worker.setTelefone(rs.getString("telefone"));
                worker.setCelular(rs.getString("celular"));
                worker.setEmail(rs.getString("email"));

                Cargos job = new Cargos();
                job.setId(rs.getInt("idCargo"));
                job.setFuncao(rs.getString("funcao"));

                worker.setIdCargo(job);
                Funcionarios.add(worker);
            }

        } catch (SQLException ex) {
            System.err.println("Erro: " + ex);
        } finally {
            ConnectionFactory.closeConnection(con, st, rs);
        }
        System.out.println("Enviado Arraylist Funcionários!");
        return Funcionarios;
    }

    public ArrayList<Funcionarios> findAll(Cargos b) {

        String sql = "SELECT Funcionarios.*,Cargos.* FROM Funcionarios "
                + " INNER JOIN Cargos ON Funcionarios.idCargo = Cargos.id "
                + "WHERE Funcionarios.idCargo = ?";

        PreparedStatement st = null;
        ResultSet rs = null;

        ArrayList<Funcionarios> Funcionarios = new ArrayList<>();

        try {
            st = (PreparedStatement) con.prepareStatement(sql);
            st.setInt(1, b.getId());
            rs = st.executeQuery();

            while (rs.next()) {
                Funcionarios worker = new Funcionarios();
                worker.setId(rs.getInt("id"));
                worker.setNome(rs.getString("nome"));
                worker.setCpf(rs.getString("cpf"));
                worker.setRg(rs.getString("rg"));
                worker.setEndereco(rs.getString("endereco"));
                worker.setTelefone(rs.getString("telefone"));
                worker.setCelular(rs.getString("celular"));
                worker.setEmail(rs.getString("email"));

                Cargos job = new Cargos();
                job.setId(rs.getInt("idCargo"));
                job.setFuncao(rs.getString("funcao"));

                worker.setIdCargo(job);
                Funcionarios.add(worker);
            }

        } catch (SQLException ex) {
            System.err.println("Erro: " + ex);
        } finally {
            ConnectionFactory.closeConnection(con, st, rs);
        }
        System.out.println("Enviado Arraylist Funcionários!");
        return Funcionarios;
    }

    public int getIdByNome(String nomeFuncionario) {
        String sql = "SELECT Funcionarios.*,Cargos.* FROM Funcionarios "
                + "INNER JOIN Cargos ON Funcionarios.idCargo = Cargos.id "
                + "WHERE Funcionarios.nome = ?";

        PreparedStatement st = null;
        ResultSet rs = null;
        int id = 0;
        try {
            st = (PreparedStatement) con.prepareStatement(sql);
            st.setString(1, nomeFuncionario);
            rs = st.executeQuery();

            while (rs.next()) {
                Funcionarios worker = new Funcionarios();
                worker.setId(rs.getInt("Funcionarios.id"));
                worker.setNome(rs.getString("Funcionarios.nome"));
                worker.setCpf(rs.getString("Funcionarios.cpf"));
                worker.setRg(rs.getString("Funcionarios.rg"));
                worker.setEndereco(rs.getString("Funcionarios.endereco"));
                worker.setTelefone(rs.getString("Funcionarios.telefone"));
                worker.setCelular(rs.getString("Funcionarios.celular"));
                worker.setEmail(rs.getString("Funcionarios.email"));

                Cargos j = new Cargos();
                j.setId(rs.getInt("Cargos.id"));
                j.setFuncao(rs.getString("Cargos.funcao"));

                worker.setIdCargo(j);
                id = worker.getId();
                return id;
            }

        } catch (SQLException ex) {
            System.out.println("Erro Ao Pegar ID: " + ex);
            return id;
        } finally {
            ConnectionFactory.closeConnection(con, st, rs);
        }
        return 0;
    }

    public boolean update(Funcionarios worker) {
        String sql = "UPDATE Funcionarios SET nome = ?, cpf = ?,rg = ? ,endereco = ?,telefone = ?,celular = ? , email = ?,idCargo = ? WHERE id = ?";

        PreparedStatement st = null;
        try {
            Cargos j = new Cargos();
            j.setId(worker.getIdCargo().getId());

            st = (PreparedStatement) con.prepareStatement(sql);
            st.setString(1, worker.getNome());
            st.setString(2, worker.getCpf());
            st.setString(3, worker.getRg());
            st.setString(4, worker.getEndereco());
            st.setString(5, worker.getTelefone());
            st.setString(6, worker.getCelular());
            st.setString(7, worker.getEmail());
            st.setInt(8, j.getId());
            st.setInt(9, worker.getId());
            st.executeUpdate();
            return true;
        } catch (SQLException ex) {
            System.out.println("Erro Ao Atualizar Funcionário: " + ex);
            return false;
        } finally {
            ConnectionFactory.closeConnection(con, st);
        }
    }

    public boolean delete(Funcionarios worker) {
        String sql = "DELETE FROM Funcionarios WHERE id = ?";

        PreparedStatement st = null;
        try {
            st = (PreparedStatement) con.prepareStatement(sql);
            st.setInt(1, worker.getId());
            st.executeUpdate();
            return true;
        } catch (SQLException ex) {
            System.out.println("Erro Ao Excluir Funcionário: " + ex);
            return false;
        } finally {
            ConnectionFactory.closeConnection(con, st);
        }
    }

    public int getMaxId() {
        int maxId = 0;
        String sql = "SELECT MAX(id) AS maxId FROM Funcionarios";
        ResultSet rs = null;
        PreparedStatement st = null;
        try {
            st = (PreparedStatement) con.prepareStatement(sql);
            rs = st.executeQuery();
            if (rs.next()) {
                maxId = rs.getInt("maxId") + 1;
                System.out.println("MaxID: " + maxId);
                return maxId;
            }

        } catch (SQLException e) {
            System.out.println("Erro Ao Pegar MaxID: " + e);
        }
        return maxId;
    }

}
