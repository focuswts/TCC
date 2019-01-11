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
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import model.bean.Entregas;
import model.bean.Modelos;
import model.bean.Pacotes;
import model.bean.Rotas;
import model.bean.RotasAux;
import model.bean.Veiculos;
import model.bean.Funcionarios;

/**
 *
 * @author focuswts
 */
public class EntregasDAO {

    private Connection con = null;

    public EntregasDAO() {
        con = (Connection) ConnectionFactory.getConnection();
    }

    public boolean save(Entregas d) {
        String sql = "INSERT INTO Entregas (idRotaAux,situacao,data,local,status) VALUES (?,?,?,?,?)";
        PreparedStatement st = null;
        try {

            st = (PreparedStatement) con.prepareStatement(sql);
            st.setInt(1, d.getIdRotaAux().getId());
            st.setString(2, d.getSituacao());
            st.setTimestamp(3, java.sql.Timestamp.valueOf(d.getData()));
            st.setString(4, d.getLocal());
            st.setString(5, d.getStatus());
            st.executeUpdate();
            return true;
        } catch (Exception e) {
            System.out.println("Erro Ao Adicionar Entregas " + e);
            return false;
        } finally {
            ConnectionFactory.closeConnection(con, st);
        }

    }

    public ArrayList<Entregas> findAll() {

        String sql = "SELECT Entregas.*,RotasAux.*,Pacotes.*,Rotas.* FROM Entregas "
                + "INNER JOIN RotasAux ON Entregas.idRotaAux = RotasAux.id "
                + "INNER JOIN Pacotes ON RotasAux.idPacotes = Pacotes.id "
                + "INNER JOIN Rotas ON RotasAux.idRota = Rotas.id "
                + "WHERE Pacotes.estadoEntrega = ?";

        PreparedStatement st = null;
        ResultSet rs = null;

        ArrayList<Entregas> Entregas = new ArrayList<>();

        try {
            st = (PreparedStatement) con.prepareStatement(sql);
            st.setString(1, "Entrega");
            rs = st.executeQuery();

            while (rs.next()) {
                Entregas d = new Entregas();
                d.setId(rs.getInt("Entregas.id"));

                Rotas r = new Rotas();
                r.setId(rs.getInt("Rotas.id"));
                r.setRemetente(rs.getString("Rotas.remetente"));
                r.setDestinatario(rs.getString("Rotas.destinatario"));

                RotasAux ra = new RotasAux();
                ra.setId(rs.getInt("RotasAux.id"));
                ra.setIdRota(r);

                Pacotes p = new Pacotes();
                p.setId(rs.getInt("Pacotes.id"));
                p.setCodigoRastreio(rs.getString("Pacotes.codigoRastreio"));
                ra.setIdPacotes(p);

                d.setIdRotaAux(ra);
                d.setSituacao(rs.getString("Entregas.situacao"));

                SimpleDateFormat f = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");//Converte TimeStamp Para String
                String formattedDate = f.format(rs.getTimestamp("Entregas.data"));
                d.setData(formattedDate);
                d.setLocal(rs.getString("Entregas.local"));
                d.setStatus(rs.getString("Entregas.status"));
                Entregas.add(d);
            }

        } catch (SQLException ex) {
            System.err.println("Erro: " + ex);
        } finally {
            ConnectionFactory.closeConnection(con, st, rs);
        }
        System.out.println("Enviado Arraylist Entregas!");
        return Entregas;
    }

    public ArrayList<Entregas> findAllStatus(String status) {

        String sql = "SELECT Entregas.*,RotasAux.*,Pacotes.*,Rotas.* FROM Entregas "
                + "INNER JOIN RotasAux ON Entregas.idRotaAux = RotasAux.id "
                + "INNER JOIN Pacotes ON RotasAux.idPacotes = Pacotes.id "
                + "INNER JOIN Rotas ON RotasAux.idRota = Rotas.id "
                + "WHERE Pacotes.estadoEntrega = ? && Entregas.status = ?";

        PreparedStatement st = null;
        ResultSet rs = null;

        ArrayList<Entregas> Entregas = new ArrayList<>();

        try {
            st = (PreparedStatement) con.prepareStatement(sql);
            st.setString(1, "Entrega");
            st.setString(2, status);
            rs = st.executeQuery();

            while (rs.next()) {
                Entregas d = new Entregas();
                d.setId(rs.getInt("Entregas.id"));

                Rotas r = new Rotas();
                r.setId(rs.getInt("Rotas.id"));
                r.setRemetente(rs.getString("Rotas.remetente"));
                r.setDestinatario(rs.getString("Rotas.destinatario"));

                RotasAux ra = new RotasAux();
                ra.setId(rs.getInt("RotasAux.id"));
                ra.setIdRota(r);

                Pacotes p = new Pacotes();
                p.setId(rs.getInt("Pacotes.id"));
                p.setCodigoRastreio(rs.getString("Pacotes.codigoRastreio"));
                ra.setIdPacotes(p);

                d.setIdRotaAux(ra);
                d.setSituacao(rs.getString("Entregas.situacao"));

                SimpleDateFormat f = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");//Converte TimeStamp Para String
                String formattedDate = f.format(rs.getTimestamp("Entregas.data"));
                d.setData(formattedDate);
                d.setLocal(rs.getString("Entregas.local"));
                d.setStatus(rs.getString("Entregas.status"));
                Entregas.add(d);
            }

        } catch (SQLException ex) {
            System.err.println("Erro: " + ex);
        } finally {
            ConnectionFactory.closeConnection(con, st, rs);
        }
        System.out.println("Enviado Arraylist Entregas!");
        return Entregas;
    }

    public ArrayList<Entregas> findAllWhereClient(String cpf) {

        String sql = "SELECT Entregas.*,RotasAux.*,Pacotes.*,Rotas.* FROM Entregas "
                + "INNER JOIN RotasAux ON Entregas.idRotaAux = RotasAux.id "
                + "INNER JOIN Pacotes ON RotasAux.idPacotes = Pacotes.id "
                + "INNER JOIN Rotas ON RotasAux.idRota = Rotas.id "
                + "INNER JOIN Clientes ON Pacotes.idCliente = Clientes.id "
                + "WHERE Pacotes.estadoEntrega = ? AND Clientes.cpf = ?";

        PreparedStatement st = null;
        ResultSet rs = null;

        ArrayList<Entregas> Entregas = new ArrayList<>();

        try {
            st = (PreparedStatement) con.prepareStatement(sql);
            st.setString(1, "Entrega");
            st.setString(2, cpf);
            rs = st.executeQuery();

            while (rs.next()) {
                Entregas d = new Entregas();
                d.setId(rs.getInt("Entregas.id"));

                Rotas r = new Rotas();
                r.setId(rs.getInt("Rotas.id"));
                r.setRemetente(rs.getString("Rotas.remetente"));
                r.setDestinatario(rs.getString("Rotas.destinatario"));

                RotasAux ra = new RotasAux();
                ra.setId(rs.getInt("RotasAux.id"));
                ra.setIdRota(r);

                Pacotes p = new Pacotes();
                p.setId(rs.getInt("Pacotes.id"));
                p.setCodigoRastreio(rs.getString("Pacotes.codigoRastreio"));
                ra.setIdPacotes(p);

                d.setIdRotaAux(ra);
                d.setSituacao(rs.getString("Entregas.situacao"));

                SimpleDateFormat f = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");//Converte TimeStamp Para String
                String formattedDate = f.format(rs.getTimestamp("Entregas.data"));
                d.setData(formattedDate);
                d.setLocal(rs.getString("Entregas.local"));
                d.setStatus(rs.getString("Entregas.status"));
                Entregas.add(d);
            }

        } catch (SQLException ex) {
            System.err.println("Erro: " + ex);
        } finally {
            ConnectionFactory.closeConnection(con, st, rs);
        }
        System.out.println("Enviado Arraylist Entregas!");
        return Entregas;
    }

    public ArrayList<Entregas> findAllWhere(String codigoRastreio) {

        String sql = "SELECT Entregas.*,RotasAux.*,Pacotes.*,Rotas.* FROM Entregas "
                + "INNER JOIN RotasAux ON Entregas.idRotaAux = RotasAux.id "
                + "INNER JOIN Pacotes ON RotasAux.idPacotes = Pacotes.id "
                + "INNER JOIN Rotas ON RotasAux.idRota = Rotas.id "
                + "WHERE Pacotes.estadoEntrega = ? AND Pacotes.codigoRastreio = ?";

        PreparedStatement st = null;
        ResultSet rs = null;

        ArrayList<Entregas> Entregas = new ArrayList<>();

        try {
            st = (PreparedStatement) con.prepareStatement(sql);
            st.setString(1, "Entrega");
            st.setString(2, codigoRastreio);
            rs = st.executeQuery();

            while (rs.next()) {
                Entregas d = new Entregas();
                d.setId(rs.getInt("Entregas.id"));

                Rotas r = new Rotas();
                r.setId(rs.getInt("Rotas.id"));
                r.setRemetente(rs.getString("Rotas.remetente"));
                r.setDestinatario(rs.getString("Rotas.destinatario"));

                RotasAux ra = new RotasAux();
                ra.setId(rs.getInt("RotasAux.id"));
                ra.setIdRota(r);

                Pacotes p = new Pacotes();
                p.setId(rs.getInt("Pacotes.id"));
                p.setCodigoRastreio(rs.getString("Pacotes.codigoRastreio"));
                ra.setIdPacotes(p);

                d.setIdRotaAux(ra);
                d.setSituacao(rs.getString("Entregas.situacao"));

                SimpleDateFormat f = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");//Converte TimeStamp Para String
                String formattedDate = f.format(rs.getTimestamp("Entregas.data"));
                d.setData(formattedDate);
                d.setLocal(rs.getString("Entregas.local"));
                d.setStatus(rs.getString("Entregas.status"));
                Entregas.add(d);
            }

        } catch (SQLException ex) {
            System.err.println("Erro: " + ex);
        } finally {
            ConnectionFactory.closeConnection(con, st, rs);
        }
        System.out.println("Enviado Arraylist Entregas!");
        return Entregas;
    }

    public Entregas findAllWhereGetOBJ(String codigoRastreio) {

        String sql = "SELECT Entregas.*,RotasAux.*,Pacotes.*,Rotas.*,Funcionarios.*,Veiculos.*,Modelos.* FROM Entregas "
                + "INNER JOIN RotasAux ON Entregas.idRotaAux = RotasAux.id "
                + "INNER JOIN Pacotes ON RotasAux.idPacotes = Pacotes.id "
                + "INNER JOIN Rotas ON RotasAux.idRota = Rotas.id "
                + "INNER JOIN Funcionarios ON Rotas.idFuncionario = Funcionarios.id "
                + "INNER JOIN Veiculos ON Rotas.idVeiculo = Veiculos.id "
                + "INNER JOIN Modelos ON Veiculos.idModelo = Modelos.id "
                + "WHERE Pacotes.estadoEntrega = ? AND Pacotes.codigoRastreio = ?";

        PreparedStatement st = null;
        ResultSet rs = null;
        Entregas dAux = null;
        try {
            st = (PreparedStatement) con.prepareStatement(sql);
            st.setString(1, "Entrega");
            st.setString(2, codigoRastreio);
            rs = st.executeQuery();

            Entregas d = new Entregas();
            while (rs.next()) {
                d.setId(rs.getInt("Entregas.id"));

                Funcionarios w = new Funcionarios();
                w.setId(rs.getInt("Funcionarios.id"));
                w.setNome(rs.getString("Funcionarios.nome"));
                Modelos m = new Modelos();
                m.setId(rs.getInt("Modelos.id"));
                m.setModelo(rs.getString("Modelos.modelo"));

                Veiculos v = new Veiculos();
                v.setId(rs.getInt("Veiculos.id"));
                v.setIdModelo(m);
                v.setPlaca(rs.getString("Veiculos.placa"));
                v.setChassi(rs.getString("Veiculos.chassi"));

                Rotas r = new Rotas();
                r.setId(rs.getInt("Rotas.id"));
                r.setIdFuncionario(w);
                r.setIdVeiculo(v);
                r.setRemetente(rs.getString("Rotas.remetente"));
                r.setDestinatario(rs.getString("Rotas.destinatario"));

                RotasAux ra = new RotasAux();
                ra.setId(rs.getInt("RotasAux.id"));
                ra.setIdRota(r);

                Pacotes p = new Pacotes();
                p.setId(rs.getInt("Pacotes.id"));
                p.setCodigoRastreio(rs.getString("Pacotes.codigoRastreio"));
                ra.setIdPacotes(p);

                d.setIdRotaAux(ra);
                d.setSituacao(rs.getString("Entregas.situacao"));

                SimpleDateFormat f = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");//Converte TimeStamp Para String
                String formattedDate = f.format(rs.getTimestamp("Entregas.data"));
                d.setData(formattedDate);
                d.setLocal(rs.getString("Entregas.local"));
                d.setStatus(rs.getString("Entregas.status"));
                dAux = d;
            }

        } catch (SQLException ex) {
            System.err.println("Erro: " + ex);
        } finally {
            ConnectionFactory.closeConnection(con, st, rs);
        }
        System.out.println("Enviado Arraylist Entregas!");
        return dAux;
    }

    public int findIdCodRastreio(String codigoRastreio) {

        String sql = "SELECT Entregas.*,RotasAux.*,Pacotes.*,Rotas.* FROM Entregas "
                + "INNER JOIN RotasAux ON Entregas.idRotaAux = RotasAux.id "
                + "INNER JOIN Pacotes ON RotasAux.idPacotes = Pacotes.id "
                + "INNER JOIN Rotas ON RotasAux.idRota = Rotas.id "
                + "WHERE Pacotes.estadoEntrega = ? AND Pacotes.codigoRastreio = ?";

        PreparedStatement st = null;
        ResultSet rs = null;

        try {
            st = (PreparedStatement) con.prepareStatement(sql);
            st.setString(1, "Entrega");
            st.setString(2, codigoRastreio);
            rs = st.executeQuery();
            int idDelivery = 0;
            while (rs.next()) {
                Entregas d = new Entregas();
                d.setId(rs.getInt("Entregas.id"));

                Rotas r = new Rotas();
                r.setId(rs.getInt("Rotas.id"));
                r.setRemetente(rs.getString("Rotas.remetente"));
                r.setDestinatario(rs.getString("Rotas.destinatario"));

                RotasAux ra = new RotasAux();
                ra.setId(rs.getInt("RotasAux.id"));
                ra.setIdRota(r);

                Pacotes p = new Pacotes();
                p.setId(rs.getInt("Pacotes.id"));
                p.setCodigoRastreio(rs.getString("Pacotes.codigoRastreio"));
                ra.setIdPacotes(p);

                d.setIdRotaAux(ra);
                d.setSituacao(rs.getString("Entregas.situacao"));

                SimpleDateFormat f = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");//Converte TimeStamp Para String
                String formattedDate = f.format(rs.getTimestamp("Entregas.data"));
                d.setData(formattedDate);
                d.setLocal(rs.getString("Entregas.local"));
                d.setStatus(rs.getString("Entregas.status"));
                idDelivery = d.getId();
            }

            return idDelivery;
        } catch (SQLException ex) {
            System.err.println("Erro: " + ex);
        } finally {
            ConnectionFactory.closeConnection(con, st, rs);
        }
        System.out.println("Enviado Arraylist Entregas!");
        return 0;
    }

    public boolean update(Entregas d) {
        String sql = "UPDATE Entregas SET situacao = ?,data = ?,local = ? "
                + "WHERE id = ?";
        PreparedStatement st = null;

        try {
            st = (PreparedStatement) con.prepareStatement(sql);
            st.setString(1, d.getSituacao());
            st.setTimestamp(2, java.sql.Timestamp.valueOf(d.getData()));
            st.setString(3, d.getLocal());
            st.setInt(4, d.getId());
            st.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println("Erro Ao Atualizar Entregas!: " + e);
            return false;
        } finally {
            ConnectionFactory.closeConnection(con, st);
        }

    }

    public boolean updateWithCodRastreio(Entregas d, String codigoRastreio) {
        String sql = "UPDATE Entregas d "
                + "INNER JOIN RotasAux ra ON d.idRotaAux = ra.id "
                + "INNER JOIN Pacotes p ON ra.idPacotes = p.id "
                + "SET situacao = ?,data = ?,local = ? "
                + "WHERE id = ? AND p.codigoRastreio = ?";
        PreparedStatement st = null;

        try {
            st = (PreparedStatement) con.prepareStatement(sql);
            st.setString(1, d.getSituacao());
            st.setTimestamp(2, java.sql.Timestamp.valueOf(d.getData()));
            st.setString(3, d.getLocal());
            st.setInt(4, d.getId());
            st.setString(5, codigoRastreio);
            st.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println("Erro Ao Atualizar Entregas!: " + e);
            return false;
        } finally {
            ConnectionFactory.closeConnection(con, st);
        }

    }

    public boolean delete(Entregas d) {
        String sql = "DELETE FROM Entregas WHERE id = ?";
        PreparedStatement st = null;
        try {
            st = (PreparedStatement) con.prepareStatement(sql);
            st.setInt(1, d.getId());
            st.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println("Erro Ao Excluir Entregas!: " + e);
            return false;
        } finally {
            ConnectionFactory.closeConnection(con, st);
        }

    }

    public boolean disable(Entregas d, String codigoRastreio) {
        String sql = "UPDATE Entregas d "
                + "INNER JOIN RotasAux ra ON d.idRotaAux = ra.id "
                + "INNER JOIN Pacotes p ON ra.idPacotes = p.id "
                + "SET d.status = ? "
                + "WHERE d.id = ? AND p.codigoRastreio = ?";
        PreparedStatement st = null;

        try {
            st = (PreparedStatement) con.prepareStatement(sql);
            st.setString(1, d.getStatus());
            st.setInt(2, d.getId());
            st.setString(3, codigoRastreio);
            st.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println("Erro Ao Desabilitar Entrega!: " + e);
            return false;
        } finally {
            ConnectionFactory.closeConnection(con, st);
        }

    }

    public boolean deleteWithCodigoRastreio(String codigoRastreio) {
        String sql = "DELETE FROM Entregas "
                + "USING Entregas "
                + "INNER JOIN RotasAux ON Entregas.idRotaAux = RotasAux.id "
                + "INNER JOIN Pacotes ON RotasAux.idPacotes = Pacotes.id "
                + "INNER JOIN Rotas ON RotasAux.idRota = Rotas.id "
                + "WHERE Pacotes.codigoRastreio = ?";
        PreparedStatement st = null;
        try {
            st = (PreparedStatement) con.prepareStatement(sql);
            st.setString(1, codigoRastreio);
            st.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println("Erro Ao Excluir Entregas!: " + e);
            return false;
        } finally {
            ConnectionFactory.closeConnection(con, st);
        }

    }

}
