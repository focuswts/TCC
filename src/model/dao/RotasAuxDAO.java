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
import model.bean.Pacotes;
import model.bean.Rotas;
import model.bean.RotasAux;

/**
 *
 * @author focuswts
 */
public class RotasAuxDAO {

    private Connection con = null;

    public RotasAuxDAO() {
        con = (Connection) ConnectionFactory.getConnection();
    }

    public boolean save(RotasAux ra) {
        String sql = "INSERT INTO RotasAux (idRota,idPacotes) VALUES (?,?)";
        PreparedStatement st = null;
        try {
            st = (PreparedStatement) con.prepareStatement(sql);
            st.setInt(1, ra.getIdRota().getId());
            st.setInt(2, ra.getIdPacotes().getId());

            st.executeUpdate();
            return true;
        } catch (Exception e) {
            System.out.println("Erro Ao Adicionar Auxiliar De Rota: " + e);
            return false;
        } finally {
            ConnectionFactory.closeConnection(con, st);
        }

    }

    public int saveGetId(RotasAux ra) {
        String sql = "INSERT INTO RotasAux (idRota,idPacotes) VALUES (?,?)";
        PreparedStatement st = null;
        int id = 0;
        try {
            st = (PreparedStatement) con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            st.setInt(1, ra.getIdRota().getId());
            st.setInt(2, ra.getIdPacotes().getId());

            st.executeUpdate();
            ResultSet rs = st.getGeneratedKeys();
            if (rs.next()) {
                id = rs.getInt(1);
            }

        } catch (Exception e) {
            System.out.println("Erro Ao Adicionar Auxiliar De Rota: " + e);
        } finally {
            ConnectionFactory.closeConnection(con, st);
        }
        return id;
    }

    public ArrayList<RotasAux> findAll() {

        String sql = "SELECT * FROM RotasAux "
                + "INNER JOIN Rotas ON RotasAux.idRota = Rotas.id "
                + "INNER JOIN Pacotes on RotasAux.idPacotes = Pacotes.id";

        PreparedStatement st = null;
        ResultSet rs = null;

        ArrayList<RotasAux> AuxiliarRota = new ArrayList<>();

        try {
            st = (PreparedStatement) con.prepareStatement(sql);
            rs = st.executeQuery();

            while (rs.next()) {
                RotasAux ra = new RotasAux();
                ra.setId(rs.getInt("id"));

                Rotas r = new Rotas();
                r.setId(rs.getInt("idRota"));
                r.setRemetente(rs.getString("Rotas.remetente"));
                r.setDestinatario(rs.getString("Rotas.destinatario"));

                Pacotes p = new Pacotes();
                p.setId(rs.getInt("idPacotes"));

                ra.setIdRota(r);
                ra.setIdPacotes(p);

                AuxiliarRota.add(ra);
            }

        } catch (SQLException ex) {
            System.err.println("Erro: " + ex);
        } finally {
            ConnectionFactory.closeConnection(con, st, rs);
        }
        System.out.println("Enviado Arraylist Auxiliar De Rota!");
        return AuxiliarRota;
    }

    public ArrayList<RotasAux> findAllWhere(String codigoRastreio) {

        String sql = "SELECT * FROM RotasAux "
                + "INNER JOIN Rotas ON RotasAux.idRota = Rotas.id "
                + "INNER JOIN Pacotes on RotasAux.idPacotes = Pacotes.id "
                + "WHERE Pacotes.codigoRastreio = ?";

        PreparedStatement st = null;
        ResultSet rs = null;

        ArrayList<RotasAux> AuxiliarRota = new ArrayList<>();

        try {
            st = (PreparedStatement) con.prepareStatement(sql);
            st.setString(1, codigoRastreio);
            rs = st.executeQuery();

            while (rs.next()) {
                RotasAux ra = new RotasAux();
                ra.setId(rs.getInt("id"));

                Rotas r = new Rotas();
                r.setId(rs.getInt("idRota"));
                r.setRemetente(rs.getString("Rotas.remetente"));
                r.setDestinatario(rs.getString("Rotas.destinatario"));

                Pacotes p = new Pacotes();
                p.setId(rs.getInt("idPacotes"));

                ra.setIdRota(r);
                ra.setIdPacotes(p);

                AuxiliarRota.add(ra);
            }

        } catch (SQLException ex) {
            System.err.println("Erro: " + ex);
        } finally {
            ConnectionFactory.closeConnection(con, st, rs);
        }
        System.out.println("Enviado Arraylist Auxiliar De Rota!");
        return AuxiliarRota;
    }

    public boolean findAllSameRoute(int idRota) {
        boolean check = false;
        int lastRow = 0;
        String sql = "SELECT * FROM RotasAux "
                + "INNER JOIN Rotas ON RotasAux.idRota = Rotas.id "
                + "INNER JOIN Pacotes on RotasAux.idPacotes = Pacotes.id "
                + "WHERE RotasAux.idRota = ?";

        PreparedStatement st = null;
        ResultSet rs = null;

        ArrayList<RotasAux> AuxiliarRota = new ArrayList<>();

        try {
            st = (PreparedStatement) con.prepareStatement(sql);
            st.setInt(1, idRota);
            rs = st.executeQuery();
            while (rs.next()) {
                RotasAux ra = new RotasAux();
                ra.setId(rs.getInt("id"));

                Rotas r = new Rotas();
                r.setId(rs.getInt("idRota"));
                r.setRemetente(rs.getString("Rotas.remetente"));
                r.setDestinatario(rs.getString("Rotas.destinatario"));

                Pacotes p = new Pacotes();
                p.setId(rs.getInt("idPacotes"));

                ra.setIdRota(r);
                ra.setIdPacotes(p);

                AuxiliarRota.add(ra);
                lastRow = rs.getRow();
            }
            if (lastRow > 1) {
                check = true;
            }

        } catch (SQLException ex) {
            System.err.println("Erro: " + ex);
        } finally {
            ConnectionFactory.closeConnection(con, st, rs);
        }
        System.out.println("Enviado Arraylist Auxiliar De Rota!");
        return check;
    }

    public boolean update(RotasAux ra) {
        String sql = "UPDATE RotasAux SET idRota = ?,idPacotes = ? WHERE id = ?";
        PreparedStatement st = null;

        try {
            st = (PreparedStatement) con.prepareStatement(sql);
            st.setInt(1, ra.getId());
            st.setInt(2, ra.getIdRota().getId());
            st.setInt(3, ra.getId());
            st.executeUpdate();
            return true;
        } catch (Exception e) {
            System.out.println("Erro Ao Atualizar Rota Auxiliar!: " + e);
            return false;
        } finally {
            ConnectionFactory.closeConnection(con, st);
        }

    }

    public boolean delete(RotasAux ra) {
        String sql = "DELETE FROM RotasAux WHERE id = ?";
        PreparedStatement st = null;
        try {
            st = (PreparedStatement) con.prepareStatement(sql);
            st.setInt(1, ra.getId());
            st.executeUpdate();
            return true;
        } catch (Exception e) {
            System.out.println("Erro Ao Excluir Rota Auxiliar!: " + e);
            return false;
        } finally {
            ConnectionFactory.closeConnection(con, st);
        }

    }

}
