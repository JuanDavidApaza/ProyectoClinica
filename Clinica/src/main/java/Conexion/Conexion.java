package Conexion;

import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Conexion {

    //Manera formal, es definiendo cada atributo de la base de datos
    private static final String JDBC_URL = "jdbc:mysql://b7efgmkpw5fdp5959znu-mysql.services.clever-cloud.com:3306/b7efgmkpw5fdp5959znu";
    private static final String JDBC_USER = "ug2zfbqc8kgapoey";
    private static final String JDBC_PASSWORD = "DMrJ2K4e3Xdy4xZce4vc";

    //Para dar la conexion
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD);
    }

    //Para cerrar los atributos que usaran base de datos
    public static void close(ResultSet rs) throws SQLException {
        rs.close();

    }

    public static void close(PreparedStatement smtm) throws SQLException {
        smtm.close();
    }

    public static void close(Connection conn) throws SQLException {
        conn.close();
    }

    public static void main(String[] args) {
        try {
            getConnection();
            System.out.println("Ya esta creo");
        } catch (SQLException ex) {
            Logger.getLogger(Conexion.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("No esta creo");
        }

    }

}
