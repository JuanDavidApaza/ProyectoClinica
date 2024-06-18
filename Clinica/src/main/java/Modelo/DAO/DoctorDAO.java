package Modelo.DAO;

import Conexion.Conexion;
import Modelo.Doctor;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DoctorDAO {

    public Doctor obtenerDoctorPorUsuarioYContrasena(String usuario, String password) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Doctor doctor = null;

        try {
            conn = Conexion.getConnection();
            String sql = "SELECT * FROM doctor WHERE Usuario = ? AND Password = ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, usuario);
            pstmt.setString(2, password);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                doctor = new Doctor();
                doctor.setIdDoctor(rs.getInt("IDDoctor"));
                doctor.setDni(rs.getString("DNI_fk"));
                doctor.setEspecialidad(rs.getString("Especialidad"));
                doctor.setUsuario(rs.getString("Usuario"));
                doctor.setPassword(rs.getString("Password"));
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

        return doctor;
    }
}
