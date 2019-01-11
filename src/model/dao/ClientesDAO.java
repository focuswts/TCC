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
import model.bean.Clientes;

/**
 *
 * @author focuswts
 */
public class ClientesDAO {

    private Connection con = null;

    public ClientesDAO() {
        con = (Connection) ConnectionFactory.getConnection();
    }

    public boolean save(Clientes client) {
        String sql = "INSERT INTO Clientes (nome,cpf,rg,endereco,telefone,celular,email) VALUES (?,?,?,?,?,?,?)";

        PreparedStatement st = null;
        try {
            st = (PreparedStatement) con.prepareStatement(sql);
            st.setString(1, client.getNome());
            st.setString(2, client.getCpf());
            st.setString(3, client.getRg());
            st.setString(4, client.getEndereco());
            st.setString(5, client.getTelefone());
            st.setString(6, client.getCelular());
            st.setString(7, client.getEmail());

            st.executeUpdate();
            return true;
        } catch (SQLException ex) {
            System.out.println("Erro Ao Incluir Cliente: " + ex);
            return false;
        } finally {
            ConnectionFactory.closeConnection(con, st);
        }
    }

    public ArrayList<Clientes> findAll() {

        String sql = "SELECT * FROM Clientes";

        PreparedStatement st = null;
        ResultSet rs = null;

        ArrayList<Clientes> Clientes = new ArrayList<>();

        try {
            st = (PreparedStatement) con.prepareStatement(sql);
            rs = st.executeQuery();

            while (rs.next()) {
                Clientes client = new Clientes();
                client.setId(rs.getInt("id"));
                client.setNome(rs.getString("nome"));
                client.setCpf(rs.getString("cpf"));
                client.setRg(rs.getString("rg"));
                client.setEndereco(rs.getString("endereco"));
                client.setTelefone(rs.getString("telefone"));
                client.setCelular(rs.getString("celular"));
                client.setEmail(rs.getString("email"));

                Clientes.add(client);
            }

        } catch (SQLException ex) {
            System.err.println("Erro: " + ex);
        } finally {
            ConnectionFactory.closeConnection(con, st, rs);
        }
        System.out.println("Enviado Arraylist Clientes!");
        return Clientes;
    }

    public ArrayList<Clientes> findAll(String nomeCliente) {

        String sql = "SELECT Clientes.* FROM Clientes  WHERE Clientes.nome LIKE ?";

        PreparedStatement st = null;
        ResultSet rs = null;
        String nomeLike = "%" + nomeCliente + "%";
        ArrayList<Clientes> Clientes = new ArrayList<>();

        try {
            st = (PreparedStatement) con.prepareStatement(sql);
            st.setString(1, nomeLike);
            rs = st.executeQuery();

            while (rs.next()) {
                Clientes client = new Clientes();
                client.setId(rs.getInt("id"));
                client.setNome(rs.getString("nome"));
                client.setCpf(rs.getString("cpf"));
                client.setRg(rs.getString("rg"));
                client.setEndereco(rs.getString("endereco"));
                client.setTelefone(rs.getString("telefone"));
                client.setCelular(rs.getString("celular"));
                client.setEmail(rs.getString("email"));

                Clientes.add(client);
            }

        } catch (SQLException ex) {
            System.err.println("Erro: " + ex);
        } finally {
            ConnectionFactory.closeConnection(con, st, rs);
        }
        System.out.println("Enviado Arraylist Clientes!");
        return Clientes;
    }


    public ArrayList<Clientes> findAllWhere(String nomeCliente) {

        String sql = "SELECT Clientes.* FROM Clientes"
                + " WHERE Clientes.nome LIKE ?";

        PreparedStatement st = null;
        ResultSet rs = null;

        ArrayList<Clientes> Clientes = new ArrayList<>();

        try {
            st = (PreparedStatement) con.prepareStatement(sql);
            st.setString(1, "%" + nomeCliente + "%");
            rs = st.executeQuery();

            while (rs.next()) {
                Clientes client = new Clientes();
                client.setId(rs.getInt("id"));
                client.setNome(rs.getString("nome"));
                client.setCpf(rs.getString("cpf"));
                client.setRg(rs.getString("rg"));
                client.setEndereco(rs.getString("endereco"));
                client.setTelefone(rs.getString("telefone"));
                client.setCelular(rs.getString("celular"));
                client.setEmail(rs.getString("email"));

                Clientes.add(client);
            }

        } catch (SQLException ex) {
            System.err.println("Erro: " + ex);
        } finally {
            ConnectionFactory.closeConnection(con, st, rs);
        }
        System.out.println("Enviado Arraylist Clientes!");
        return Clientes;
    }

    public Clientes findAllWhereRg(String rg) {
        Clientes client = new Clientes();
        String sql = "SELECT Clientes.*,Filiais.* FROM Clientes"
                + " WHERE Clientes.rg = ?";

        PreparedStatement st = null;
        ResultSet rs = null;

        ArrayList<Clientes> Clientes = new ArrayList<>();

        try {
            st = (PreparedStatement) con.prepareStatement(sql);
            st.setString(1, rg);
            rs = st.executeQuery();

            while (rs.next()) {
                client.setId(rs.getInt("id"));
                client.setNome(rs.getString("nome"));
                client.setCpf(rs.getString("cpf"));
                client.setRg(rs.getString("rg"));
                client.setEndereco(rs.getString("endereco"));
                client.setTelefone(rs.getString("telefone"));
                client.setCelular(rs.getString("celular"));
                client.setEmail(rs.getString("email"));


                Clientes.add(client);
            }

        } catch (SQLException ex) {
            System.err.println("Erro: " + ex);
        } finally {
            ConnectionFactory.closeConnection(con, st, rs);
        }
        System.out.println("Enviado Obj Cliente!");
        return client;
    }

    public boolean update(Clientes client) {
        String sql = "UPDATE Clientes SET nome = ?, cpf = ?,rg = ? ,endereco = ?,telefone = ?,celular = ? , email = ? WHERE id = ?";

        PreparedStatement st = null;
        try {

            st = (PreparedStatement) con.prepareStatement(sql);
            st.setString(1, client.getNome());
            st.setString(2, client.getCpf());
            st.setString(3, client.getRg());
            st.setString(4, client.getEndereco());
            st.setString(5, client.getTelefone());
            st.setString(6, client.getCelular());
            st.setString(7, client.getEmail());
            st.setInt(8, client.getId());
            st.executeUpdate();
            return true;
        } catch (SQLException ex) {
            System.out.println("Erro Ao Atualizar Cliente: " + ex);
            return false;
        } finally {
            ConnectionFactory.closeConnection(con, st);
        }
    }

    public boolean delete(Clientes client) {
        String sql = "DELETE FROM Clientes WHERE id = ?";

        PreparedStatement st = null;
        try {
            st = (PreparedStatement) con.prepareStatement(sql);
            st.setInt(1, client.getId());
            st.executeUpdate();
            return true;
        } catch (SQLException ex) {
            System.out.println("Erro Ao Excluir Cliente: " + ex);
            return false;
        } finally {
            ConnectionFactory.closeConnection(con, st);
        }
    }

    public int getIdByNome(String nomeCliente) {
        String sql = "SELECT Clientes.* FROM Clientes "
                + "WHERE Clientes.nome = ?";

        PreparedStatement st = null;
        ResultSet rs = null;
        int id = 0;
        try {
            st = (PreparedStatement) con.prepareStatement(sql);
            st.setString(1, nomeCliente);
            rs = st.executeQuery();

            while (rs.next()) {
                Clientes client = new Clientes();
                client.setId(rs.getInt("Clientes.id"));
                client.setNome(rs.getString("Clientes.nome"));
                client.setCpf(rs.getString("Clientes.cpf"));
                client.setRg(rs.getString("Clientes.rg"));
                client.setEndereco(rs.getString("Clientes.endereco"));
                client.setTelefone(rs.getString("Clientes.telefone"));
                client.setCelular(rs.getString("Clientes.celular"));
                client.setEmail(rs.getString("Clientes.email"));

                id = client.getId();
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

    public int getMaxId() {
        int maxId = 0;
        String sql = "SELECT MAX(id) AS maxId FROM Clientes";
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
