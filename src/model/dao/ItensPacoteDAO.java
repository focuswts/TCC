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
import model.bean.ItensPacote;
import model.bean.Pacotes;

/**
 *
 * @author focuswts
 */
public class ItensPacoteDAO {

    private Connection con = null;

    public ItensPacoteDAO() {
        con = (Connection) ConnectionFactory.getConnection();
    }

    public boolean save(ItensPacote pi) {
        String sql = "INSERT INTO ItensPacote (idPacote,item,qtd) VALUES (?,?,?)";
        PreparedStatement st = null;
        try {
            st = (PreparedStatement) con.prepareStatement(sql);
            st.setInt(1, pi.getIdPacote().getId());
            st.setString(2, pi.getItem());
            st.setInt(3, pi.getQtd());
            st.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println("Erro Ao Adicionar  Item Do Pacote: " + e);
            return false;
        } finally {
            ConnectionFactory.closeConnection(con, st);
        }

    }

    public boolean add4All(ArrayList<ItensPacote> listaItens) {
        String sql = "INSERT INTO ItensPacote (idPacote,item,qtd) VALUES (?,?,?)";
        PreparedStatement st = null;
        try {
            st = (PreparedStatement) con.prepareStatement(sql);
            for (int i = 0; i < listaItens.size(); i++) {
                st.setInt(1, listaItens.get(i).getIdPacote().getId());
                st.setString(2, listaItens.get(i).getItem());
                st.setInt(3, listaItens.get(i).getQtd());
                st.executeLargeUpdate();
            }
            return true;
        } catch (SQLException e) {
            System.out.println("Erro Ao Adicionar Itens Pacote!: " + e);
            return false;
        }
    }

    public ArrayList<ItensPacote> findAll(Pacotes pAux) {

        String sql = "SELECT * FROM ItensPacote WHERE idPacote = ?";

        PreparedStatement st = null;
        ResultSet rs = null;

        ArrayList<ItensPacote> ItensPacote = new ArrayList<>();

        try {
            st = (PreparedStatement) con.prepareStatement(sql);
            st.setInt(1, pAux.getId());
            rs = st.executeQuery();

            while (rs.next()) {
                ItensPacote pi = new ItensPacote();
                pi.setId(rs.getInt("id"));

                Pacotes p = new Pacotes();
                p.setId(rs.getInt("idPacote"));

                pi.setItem(rs.getString("item"));

                pi.setQtd(rs.getInt("qtd"));

                ItensPacote.add(pi);
            }

        } catch (SQLException ex) {
            System.err.println("Erro: " + ex);
        } finally {
            ConnectionFactory.closeConnection(con, st, rs);
        }
        System.out.println("Enviado Arraylist Itens Pacote!");
        return ItensPacote;
    }

    public int findAllGetID(Pacotes pAux, String item, int qtd) {
        int id = 0;
        String sql = "SELECT * FROM ItensPacote WHERE idPacote = ? && item = ? && qtd = ?";

        PreparedStatement st = null;
        ResultSet rs = null;

        ArrayList<ItensPacote> ItensPacote = new ArrayList<>();

        try {
            st = (PreparedStatement) con.prepareStatement(sql);
            st.setInt(1, pAux.getId());
            st.setString(2, item);
            st.setInt(3, qtd);
            rs = st.executeQuery();

            while (rs.next()) {
                id = rs.getInt("id");
                ItensPacote pi = new ItensPacote();
                pi.setId(rs.getInt("id"));

                Pacotes p = new Pacotes();
                p.setId(rs.getInt("idPacote"));

                pi.setItem(rs.getString("item"));

                pi.setQtd(rs.getInt("qtd"));

                ItensPacote.add(pi);
            }

        } catch (SQLException ex) {
            System.err.println("Erro: " + ex);
        } finally {
            ConnectionFactory.closeConnection(con, st, rs);
        }
        System.out.println("Enviado Arraylist Itens Pacote!");
        return id;
    }

    public boolean update(ItensPacote pi) {
        String sql = "UPDATE  ItensPacote SET item = ?, qtd = ? WHERE id = ?";
        PreparedStatement st = null;

        try {
            st = (PreparedStatement) con.prepareStatement(sql);
            st.setString(1, pi.getItem());
            st.setInt(2, pi.getQtd());
            st.setInt(3, pi.getId());
            st.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println("Erro Ao Atualizar Itens Pacote!: " + e);
            return false;
        } finally {
            ConnectionFactory.closeConnection(con, st);
        }

    }

    public boolean update4All(ArrayList<ItensPacote> listaItens) {
        String sql = "UPDATE  ItensPacote SET item = ?, qtd = ? WHERE id = ?";
        PreparedStatement st = null;
        try {
            st = (PreparedStatement) con.prepareStatement(sql);
            for (int i = 0; i < listaItens.size(); i++) {
                st.setString(1, listaItens.get(i).getItem());
                st.setInt(2, listaItens.get(i).getQtd());
                st.setInt(3, listaItens.get(i).getId());
                st.executeUpdate();
            }
            return true;
        } catch (SQLException e) {
            System.out.println("Erro Ao Atualizar Itens Pacote!: " + e);
            return false;
        }
    }

    public boolean delete(ItensPacote pi) {
        String sql = "DELETE FROM ItensPacote WHERE id = ?";
        PreparedStatement st = null;
        try {
            st = (PreparedStatement) con.prepareStatement(sql);
            st.setInt(1, pi.getId());
            st.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println("Erro Ao Excluir Itens Pacote!: " + e);
            return false;
        } finally {
            ConnectionFactory.closeConnection(con, st);
        }

    }

    public boolean deleteWithPackage(Pacotes p) {
        String sql = "DELETE FROM ItensPacote WHERE idPacote = ?";
        PreparedStatement st = null;
        try {
            st = (PreparedStatement) con.prepareStatement(sql);
            st.setInt(1, p.getId());
            st.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println("Erro Ao Excluir Itens Pacote!: " + e);
            return false;
        } finally {
            ConnectionFactory.closeConnection(con, st);
        }

    }

}
