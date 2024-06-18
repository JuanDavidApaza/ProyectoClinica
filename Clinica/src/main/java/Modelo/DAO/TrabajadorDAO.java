package Modelo.DAO;

import Conexion.Conexion;
import Modelo.Trabajador;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class TrabajadorDAO {

    public Trabajador obtenerTrabajadorPorUsuarioYContrasena(String usuario, String password) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Trabajador trabajador = null;

        try {
            conn = Conexion.getConnection();
            String sql = "SELECT * FROM trabajador WHERE Usuario = ? AND Password = ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, usuario);
            pstmt.setString(2, password);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                trabajador = new Trabajador();
                trabajador.setIdTrabajador(rs.getInt("IDTrabajador"));
                trabajador.setDni(rs.getString("DNI_fk3"));
                trabajador.setRol(rs.getString("Rol"));
                trabajador.setUsuario(rs.getString("Usuario"));
                trabajador.setPassword(rs.getString("Password"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) rs.close();
                if (pstmt != null) pstmt.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return trabajador;
    }
}
