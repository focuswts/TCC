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
import model.bean.Usuarios;

/**
 *
 * @author focuswts
 */
public class UsuariosDAO {

    private Connection con = null;

    public UsuariosDAO() {
        con = (Connection) ConnectionFactory.getConnection();
    }

    public boolean save(Usuarios user) {
        String sql = "INSERT INTO Usuarios (login,senha,nivel) VALUES (?,?,?)";

        PreparedStatement st = null;
        try {
            st = (PreparedStatement) con.prepareStatement(sql);
            st.setString(1, user.getLogin());
            st.setString(2, user.getSenha());
            st.setString(3, user.getNivel());
            st.executeUpdate();
            return true;
        } catch (SQLException ex) {
            System.out.println("Erro Ao Incluir Usuário: " + ex);
            return false;
        } finally {
            ConnectionFactory.closeConnection(con, st);
        }
    }

    public boolean saveWithClient(Usuarios user, Clientes client) {
        String sql = "INSERT INTO Usuarios (login,senha,nivel,idCliente) VALUES (?,?,?,?)";

        PreparedStatement st = null;
        try {
            st = (PreparedStatement) con.prepareStatement(sql);
            st.setString(1, user.getLogin());
            st.setString(2, user.getSenha());
            st.setString(3, user.getNivel());
            st.setInt(4, client.getId());
            st.executeUpdate();
            return true;
        } catch (SQLException ex) {
            System.out.println("Erro Ao Incluir Usuário: " + ex);
            return false;
        } finally {
            ConnectionFactory.closeConnection(con, st);
        }
    }

    public boolean update(Usuarios user) {
        String sql = "UPDATE Usuarios SET login = ? , senha = ? , nivel = ? WHERE id = ?";

        PreparedStatement st = null;
        try {
            st = (PreparedStatement) con.prepareStatement(sql);
            st.setString(1, user.getLogin());
            st.setString(2, user.getSenha());
            st.setString(3, user.getNivel());
            st.setInt(4, user.getId());
            st.executeUpdate();
            return true;
        } catch (SQLException ex) {
            System.out.println("Erro Ao Atualizar Usuário: " + ex);
            return false;
        } finally {
            ConnectionFactory.closeConnection(con, st);
        }
    }

    public boolean delete(Usuarios user) {
        String sql = "DELETE FROM Usuarios WHERE id = ?";

        PreparedStatement st = null;
        try {
            st = (PreparedStatement) con.prepareStatement(sql);
            st.setInt(1, user.getId());
            st.executeUpdate();
            return true;
        } catch (SQLException ex) {
            System.out.println("Erro Ao Excluir Usuário: " + ex);
            return false;
        } finally {
            ConnectionFactory.closeConnection(con, st);
        }
    }

    public ArrayList<Usuarios> findAll() {

        String sql = "SELECT * FROM Usuarios";

        PreparedStatement st = null;
        ResultSet rs = null;

        ArrayList<Usuarios> Usuarios = new ArrayList<>();

        try {
            st = (PreparedStatement) con.prepareStatement(sql);
            rs = st.executeQuery();

            while (rs.next()) {
                Usuarios user = new Usuarios();
                user.setId(rs.getInt("id"));
                user.setLogin(rs.getString("login"));
                user.setSenha(rs.getString("senha"));
                user.setNivel(rs.getString("nivel"));

                Usuarios.add(user);
            }

        } catch (SQLException ex) {
            System.err.println("Erro: " + ex);
        } finally {
            ConnectionFactory.closeConnection(con, st, rs);
        }
        System.out.println("Enviado Arraylist Usuários!");
        return Usuarios;
    }

    public ArrayList<Usuarios> findAllInnerJoin() {

        String sql = "SELECT * FROM Usuarios "
                + "INNER JOIN Clientes ON Usuarios.idCliente = Clientes.id";

        PreparedStatement st = null;
        ResultSet rs = null;

        ArrayList<Usuarios> Usuarios = new ArrayList<>();

        try {
            st = (PreparedStatement) con.prepareStatement(sql);
            rs = st.executeQuery();

            while (rs.next()) {
                Usuarios user = new Usuarios();
                user.setId(rs.getInt("Usuarios.id"));
                user.setLogin(rs.getString("Usuarios.login"));
                user.setSenha(rs.getString("Usuarios.senha"));
                user.setNivel(rs.getString("Usuarios.nivel"));

                Clientes c = new Clientes();
                c.setId(rs.getInt("Clientes.id"));
                c.setNome(rs.getString("Clientes.nome"));
                c.setCpf(rs.getString("Clientes.cpf"));
                c.setRg(rs.getString("Clientes.rg"));
                user.setIdCliente(c);

                Usuarios.add(user);
            }

        } catch (SQLException ex) {
            System.err.println("Erro: " + ex);
        } finally {
            ConnectionFactory.closeConnection(con, st, rs);
        }
        System.out.println("Enviado Arraylist Usuários!");
        return Usuarios;
    }

    public boolean login(Usuarios user) {
        String sql = "SELECT * FROM Usuarios WHERE login = ? AND senha = ?";
        PreparedStatement st = null;
        try {
            st = (PreparedStatement) con.prepareStatement(sql);
            st.setString(1, user.getLogin());
            st.setString(2, user.getSenha());

            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                System.out.println("Login Efetuado Com Sucesso!");
                return true;
            }

        } catch (SQLException e) {
            System.out.println("Erro Ao Efetuar Login: " + e);
        }
        return false;
    }

    public Usuarios getObjFromUser(int id) {
        String sql = "SELECT * FROM Usuarios "
                + "INNER JOIN Clientes ON Usuarios.idCliente = Clientes.id "
                + "WHERE Usuarios.id = ?";
        PreparedStatement st = null;
        Usuarios u = new Usuarios();
        try {
            st = (PreparedStatement) con.prepareStatement(sql);
            st.setInt(1, id);

            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                u.setId(rs.getInt("Usuarios.id"));
                u.setLogin(rs.getString("Usuarios.login"));
                u.setNivel(rs.getString("Usuarios.nivel"));

                Clientes c = new Clientes();
                c.setId(rs.getInt("Clientes.id"));
                c.setNome(rs.getString("Clientes.nome"));
                c.setRg(rs.getString("Clientes.rg"));
                c.setCpf(rs.getString("Clientes.cpf"));
                c.setEndereco(rs.getString("Clientes.endereco"));

                u.setIdCliente(c);

                return u;
            }
        } catch (SQLException e) {
            System.out.println("Erro Ao Pegar OBJ Login: " + e);
        }
        return u;
    }

    public Usuarios getObjFromLogin(String login) {
        String sql = "SELECT * FROM Usuarios "
                + "WHERE Usuarios.login = ?";
        PreparedStatement st = null;
        Usuarios u = new Usuarios();
        try {
            st = (PreparedStatement) con.prepareStatement(sql);
            st.setString(1, login);

            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                u.setId(rs.getInt("id"));
                u.setLogin(rs.getString("login"));
                u.setNivel(rs.getString("nivel"));


                return u;
            }
        } catch (SQLException e) {
            System.out.println("Erro Ao Pegar OBJ Login: " + e);
        }
        return u;
    }
    
    public Usuarios getObjFromLoginWithClient(String login) {
        String sql = "SELECT * FROM Usuarios "
                + "INNER JOIN Clientes ON Usuarios.idCliente = Clientes.id "
                + "WHERE Usuarios.login = ?";
        PreparedStatement st = null;
        Usuarios u = new Usuarios();
        try {
            st = (PreparedStatement) con.prepareStatement(sql);
            st.setString(1, login);

            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                u.setId(rs.getInt("id"));
                u.setLogin(rs.getString("login"));
                u.setNivel(rs.getString("nivel"));

                if (rs.getInt("idCliente") != 0) {
                    Clientes c = new Clientes();
                    c.setId(rs.getInt("Clientes.id"));
                    c.setNome(rs.getString("Clientes.nome"));
                    c.setRg(rs.getString("Clientes.rg"));
                    c.setCpf(rs.getString("Clientes.cpf"));
                    c.setEndereco(rs.getString("Clientes.endereco"));

                    u.setIdCliente(c);
                }

                return u;
            }
        } catch (SQLException e) {
            System.out.println("Erro Ao Pegar OBJ Login: " + e);
        }
        return u;
    }

}
