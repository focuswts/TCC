/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package connection;

import com.mysql.jdbc.PreparedStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author focuswts
 */
public class ConnectionFactory {

    private static final String DRIVER = "com.mysql.jdbc.Driver";
    private static final String URL = "jdbc:mysql://localhost:3306/tcc";
    private static final String USER = "netbeans";
    private static final String PASS = "vu26uj77o";

    public static Connection getConnection() {
        try {
            Class.forName(DRIVER);

            return DriverManager.getConnection(URL, USER, PASS);

        } catch (Exception e) {
            throw new RuntimeException("Erro Na Conexao : ", e);
        }

    }

    public static void closeConnection(Connection con) {
        if (con != null) {
            try {
                con.close();
            } catch (SQLException ex) {
                Logger.getLogger(ConnectionFactory.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
    }

    public static void closeConnection(Connection con, PreparedStatement stmt) {
        closeConnection(con);
        try {
            if (stmt != null) {
                stmt.close();
            }
        } catch (Exception e) {
        }
    }

    public static void closeConnection(Connection con, PreparedStatement stmt, ResultSet rs) {
        closeConnection(con, stmt);
        try {
            if (rs != null) {
                rs.close();
            }

        } catch (Exception e) {
        }
    }

}
