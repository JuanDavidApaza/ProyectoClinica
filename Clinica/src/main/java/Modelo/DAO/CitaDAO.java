package Modelo.DAO;

import Modelo.Cita;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class CitaDAO {
    private Connection connection;

    public CitaDAO() {
    }

    
    public CitaDAO(Connection connection) {
        this.connection = connection;
    }

    public boolean insertarCita(Cita cita) {
        String sql = "INSERT INTO cita (IDPaciente_fk2, Fecha, Turno, NumeroTurno, Estado, Detalle, Diagnostico, IDDoctor_fk) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, cita.getIdPaciente());
            pstmt.setDate(2, (Date) cita.getFecha());
            pstmt.setString(3, cita.getTurno());
            pstmt.setInt(4, cita.getNumTurno());
            pstmt.setString(5, cita.getEstado());
            pstmt.setString(6, cita.getDetalle());
            pstmt.setString(7, cita.getDiagnostico());
            pstmt.setInt(8, cita.getIdDoctor());
            pstmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<Cita> obtenerCitasPorDoctorFechaTurno(int idDoctor, Date fecha, String turno) {
        List<Cita> citas = new ArrayList<>();
        String sql = "SELECT * FROM cita WHERE IDDoctor_fk = ? AND Fecha = ? AND Turno = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, idDoctor);
            pstmt.setDate(2, fecha);
            pstmt.setString(3, turno);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                Cita cita = new Cita();
                // Set fields of cita from ResultSet
                citas.add(cita);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return citas;
    }

}

