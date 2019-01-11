/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.dao;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;
import com.mysql.jdbc.Statement;
import connection.ConnectionFactory;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import model.bean.Clientes;
import model.bean.FormasEntrega;
import model.bean.Pacotes;

/**
 *
 * @author focuswts
 */
public class PacotesDAO {

    private Connection con = null;

    public PacotesDAO() {
        con = (Connection) ConnectionFactory.getConnection();
    }

    public boolean save(Pacotes p) {
        String sql = "INSERT INTO Pacotes (idCliente,idForma,codigoRastreio,estadoEntrega,status) VALUES (?,?,?,?,?)";
        PreparedStatement st = null;
        try {
            st = (PreparedStatement) con.prepareStatement(sql);
            st.setInt(1, p.getIdCliente().getId());
            st.setInt(2, p.getIdForma().getId());
            st.setString(3, p.getCodigoRastreio());
            st.setString(4, "SemEntrega");
            st.setString(5, p.getStatus());
            st.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println("Erro Ao Adicionar Pacote: " + e);
            return false;
        } finally {
            ConnectionFactory.closeConnection(con, st);
        }

    }

    public int saveGetId(Pacotes p) {
        String sql = "INSERT INTO Pacotes (idCliente,idForma,codigoRastreio,estadoEntrega,status) VALUES (?,?,?,?,?)";
        PreparedStatement st = null;
        ResultSet rs;
        int id = 0;
        try {
            st = (PreparedStatement) con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            st.setInt(1, p.getIdCliente().getId());
            st.setInt(2, p.getIdForma().getId());
            st.setString(3, p.getCodigoRastreio());
            st.setString(4, "SemEntrega");
            st.setString(5, p.getStatus());
            st.executeUpdate();
            rs = st.getGeneratedKeys();
            if (rs.next()) {
                id = rs.getInt(1);
            }
            return id;
        } catch (SQLException e) {
            System.out.println("Erro Ao Adicionar Pacote: " + e);
            return id;
        } finally {
            ConnectionFactory.closeConnection(con, st);
        }

    }

    public int findIdPackage(String codigoRastreio) {
        int idPacote = 0;
        String sql = "SELECT Pacotes.*,Clientes.*,FormasEntrega.* FROM Pacotes"
                + " INNER JOIN Clientes  ON Pacotes.idCliente = Clientes.id"
                + " INNER JOIN FormasEntrega ON Pacotes.idForma = FormasEntrega.id"
                + " WHERE Pacotes.codigoRastreio = ?";

        PreparedStatement st = null;
        ResultSet rs = null;

        ArrayList<Pacotes> Pacotes = new ArrayList<>();
        try {
            st = (PreparedStatement) con.prepareStatement(sql);
            st.setString(1, codigoRastreio);
            rs = st.executeQuery();

            while (rs.next()) {
                Pacotes p = new Pacotes();
                idPacote = rs.getInt("Pacotes.id");
                p.setId(rs.getInt("Pacotes.id"));

                Clientes c = new Clientes();
                c.setId(rs.getInt("Pacotes.idCliente"));
                c.setNome(rs.getString("Clientes.nome"));
                c.setCpf(rs.getString("Clientes.cpf"));
                c.setRg(rs.getString("Clientes.rg"));
                c.setEndereco(rs.getString("Clientes.endereco"));
                c.setTelefone(rs.getString("Clientes.telefone"));
                c.setCelular(rs.getString("Clientes.celular"));
                c.setEmail("Clientes.email");

                FormasEntrega dw = new FormasEntrega();
                dw.setId(rs.getInt("Pacotes.idForma"));
                dw.setForma(rs.getString("FormasEntrega.forma"));
                dw.setValor(rs.getString("FormasEntrega.valor"));
                p.setIdCliente(c);
                p.setIdForma(dw);
                p.setCodigoRastreio(rs.getString("codigoRastreio"));
                p.setStatus(rs.getString("status"));
                Pacotes.add(p);
            }
            return idPacote;
        } catch (SQLException ex) {
            System.err.println("Erro Select: " + ex);
        } finally {
            ConnectionFactory.closeConnection(con, st, rs);
        }
        System.out.println("Enviado Arraylist Pacotes!");
        return idPacote;
    }

    public ArrayList<Pacotes> findAll() {

        String sql = "SELECT Pacotes.*,Clientes.*,FormasEntrega.* FROM Pacotes"
                + " INNER JOIN Clientes  ON Pacotes.idCliente = Clientes.id"
                + " INNER JOIN FormasEntrega ON Pacotes.idForma = FormasEntrega.id "
                + "WHERE Pacotes.estadoEntrega = ?";

        PreparedStatement st = null;
        ResultSet rs = null;

        ArrayList<Pacotes> Pacotes = new ArrayList<>();

        try {
            st = (PreparedStatement) con.prepareStatement(sql);
            st.setString(1, "SemEntrega");
            rs = st.executeQuery();

            while (rs.next()) {
                Pacotes p = new Pacotes();
                p.setId(rs.getInt("Pacotes.id"));

                Clientes c = new Clientes();
                c.setId(rs.getInt("Pacotes.idCliente"));
                c.setNome(rs.getString("Clientes.nome"));
                c.setCpf(rs.getString("Clientes.cpf"));
                c.setRg(rs.getString("Clientes.rg"));
                c.setEndereco(rs.getString("Clientes.endereco"));
                c.setTelefone(rs.getString("Clientes.telefone"));
                c.setCelular(rs.getString("Clientes.celular"));
                c.setEmail("Clientes.email");

                FormasEntrega dw = new FormasEntrega();
                dw.setId(rs.getInt("Pacotes.idForma"));
                dw.setForma(rs.getString("FormasEntrega.forma"));
                dw.setValor(rs.getString("FormasEntrega.valor"));
                p.setIdCliente(c);
                p.setIdForma(dw);
                p.setCodigoRastreio(rs.getString("codigoRastreio"));
                p.setStatus(rs.getString("status"));
                Pacotes.add(p);
            }

        } catch (SQLException ex) {
            System.err.println("Erro Select: " + ex);
        } finally {
            ConnectionFactory.closeConnection(con, st, rs);
        }
        System.out.println("Enviado Arraylist Pacotes!");
        return Pacotes;
    }

    public ArrayList<Pacotes> findAllStatus(String status) {

        String sql = "SELECT Pacotes.*,Clientes.*,FormasEntrega.* FROM Pacotes"
                + " INNER JOIN Clientes  ON Pacotes.idCliente = Clientes.id"
                + " INNER JOIN FormasEntrega ON Pacotes.idForma = FormasEntrega.id "
                + "WHERE Pacotes.estadoEntrega = ? && Pacotes.status = ?";

        PreparedStatement st = null;
        ResultSet rs = null;

        ArrayList<Pacotes> Pacotes = new ArrayList<>();

        try {
            st = (PreparedStatement) con.prepareStatement(sql);
            st.setString(1, "SemEntrega");
            st.setString(2, status);
            rs = st.executeQuery();

            while (rs.next()) {
                Pacotes p = new Pacotes();
                p.setId(rs.getInt("Pacotes.id"));

                Clientes c = new Clientes();
                c.setId(rs.getInt("Pacotes.idCliente"));
                c.setNome(rs.getString("Clientes.nome"));
                c.setCpf(rs.getString("Clientes.cpf"));
                c.setRg(rs.getString("Clientes.rg"));
                c.setEndereco(rs.getString("Clientes.endereco"));
                c.setTelefone(rs.getString("Clientes.telefone"));
                c.setCelular(rs.getString("Clientes.celular"));
                c.setEmail("Clientes.email");

                FormasEntrega dw = new FormasEntrega();
                dw.setId(rs.getInt("Pacotes.idForma"));
                dw.setForma(rs.getString("FormasEntrega.forma"));
                dw.setValor(rs.getString("FormasEntrega.valor"));
                p.setIdCliente(c);
                p.setIdForma(dw);
                p.setCodigoRastreio(rs.getString("codigoRastreio"));
                p.setStatus(rs.getString("status"));
                Pacotes.add(p);
            }

        } catch (SQLException ex) {
            System.err.println("Erro Select: " + ex);
        } finally {
            ConnectionFactory.closeConnection(con, st, rs);
        }
        System.out.println("Enviado Arraylist Pacotes!");
        return Pacotes;
    }

    public ArrayList<Pacotes> findAllWhere(int id) {

        String sql = "SELECT Pacotes.*,Clientes.*,FormasEntrega.* FROM Pacotes"
                + " INNER JOIN Clientes  ON Pacotes.idCliente = Clientes.id"
                + " INNER JOIN FormasEntrega ON Pacotes.idForma = FormasEntrega.id"
                + " WHERE Pacotes.id = ? AND Pacotes.estadoEntrega = ?";

        PreparedStatement st = null;
        ResultSet rs = null;

        ArrayList<Pacotes> Pacotes = new ArrayList<>();

        try {
            st = (PreparedStatement) con.prepareStatement(sql);
            st.setInt(1, id);
            st.setString(2, "SemEntrega");
            rs = st.executeQuery();

            while (rs.next()) {
                Pacotes p = new Pacotes();
                p.setId(rs.getInt("Pacotes.id"));

                Clientes c = new Clientes();
                c.setId(rs.getInt("Pacotes.idCliente"));
                c.setNome(rs.getString("Clientes.nome"));
                c.setCpf(rs.getString("Clientes.cpf"));
                c.setRg(rs.getString("Clientes.rg"));
                c.setEndereco(rs.getString("Clientes.endereco"));
                c.setTelefone(rs.getString("Clientes.telefone"));
                c.setCelular(rs.getString("Clientes.celular"));
                c.setEmail("Clientes.email");

                FormasEntrega dw = new FormasEntrega();
                dw.setId(rs.getInt("Pacotes.idForma"));
                dw.setForma(rs.getString("FormasEntrega.forma"));
                dw.setValor(rs.getString("FormasEntrega.valor"));
                p.setIdCliente(c);
                p.setIdForma(dw);
                p.setCodigoRastreio(rs.getString("codigoRastreio"));
                p.setStatus(rs.getString("status"));
                Pacotes.add(p);
            }

        } catch (SQLException ex) {
            System.err.println("Erro Select: " + ex);
        } finally {
            ConnectionFactory.closeConnection(con, st, rs);
        }
        System.out.println("Enviado Arraylist Pacotes!");
        return Pacotes;
    }

    public boolean update(Pacotes p) {
        String sql = "UPDATE Pacotes SET idCliente = ?,idForma = ?,codigoRastreio = ?, status = ? WHERE id = ?";
        PreparedStatement st = null;

        try {
            st = (PreparedStatement) con.prepareStatement(sql);
            st.setInt(1, p.getIdCliente().getId());
            st.setInt(2, p.getIdForma().getId());
            st.setString(3, p.getCodigoRastreio());
            st.setInt(4, p.getId());
            st.setString(5, p.getStatus());
            st.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println("Erro Ao Atualizar Pacotes!: " + e);
            return false;
        } finally {
            ConnectionFactory.closeConnection(con, st);
        }

    }

    public boolean updateEntrega(Pacotes p) {
        String sql = "UPDATE Pacotes SET estadoEntrega = ? WHERE id = ?";
        PreparedStatement st = null;

        try {
            st = (PreparedStatement) con.prepareStatement(sql);
            st.setString(1, p.getEstadoEntrega());
            st.setInt(2, p.getId());
            st.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println("Erro Ao Atualizar Pacotes!: " + e);
            return false;
        } finally {
            ConnectionFactory.closeConnection(con, st);
        }

    }

    public boolean updateStatus(Pacotes p) {
        String sql = "UPDATE Pacotes SET status = ? WHERE id = ?";
        PreparedStatement st = null;

        try {
            st = (PreparedStatement) con.prepareStatement(sql);
            st.setString(1, p.getStatus());
            st.setInt(2, p.getId());
            st.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println("Erro Ao Atualizar Pacotes!: " + e);
            return false;
        } finally {
            ConnectionFactory.closeConnection(con, st);
        }

    }

    public boolean delete(Pacotes p) {
        String sql = "DELETE FROM Pacotes WHERE id = ?";
        PreparedStatement st = null;
        ItensPacoteDAO piDAO = new ItensPacoteDAO();
        try {
            if (piDAO.deleteWithPackage(p) == true) {
                st = (PreparedStatement) con.prepareStatement(sql);
                st.setInt(1, p.getId());
                st.executeUpdate();
            }
            return true;

        } catch (SQLException e) {
            System.out.println("Erro Ao Excluir Pacotes!: " + e);
            return false;
        } finally {
            ConnectionFactory.closeConnection(con, st);
        }

    }

    public boolean disable(Pacotes p) {
        String sql = "UPDATE Pacotes SET status = ? WHERE id = ?";
        PreparedStatement st = null;

        try {
            st = (PreparedStatement) con.prepareStatement(sql);
            st.setString(1, p.getStatus());
            st.setInt(2, p.getId());
            st.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println("Erro Ao Atualizar Pacotes!: " + e);
            return false;
        } finally {
            ConnectionFactory.closeConnection(con, st);
        }

    }
    
}
