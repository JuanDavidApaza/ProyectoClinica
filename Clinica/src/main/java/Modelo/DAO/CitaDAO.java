package Modelo.DAO;

import Modelo.Cita;
import Modelo.Doctor;
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

    public List<Cita> obtenerCitasPorDoctorFechaTurno(int idDoctor, java.util.Date fecha, String turno) {
        List<Cita> citas = new ArrayList<>();
        String sql = "SELECT * FROM cita WHERE IDDoctor_fk = ? AND Fecha = ? AND Turno = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, idDoctor);
            pstmt.setDate(2, new java.sql.Date(fecha.getTime()));
            pstmt.setString(3, turno);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                Cita cita = new Cita();
                cita.setIdCita(rs.getInt("idCita"));
                cita.setIdPaciente(rs.getInt("IDPaciente_fk2"));
                cita.setIdDoctor(rs.getInt("IDDoctor_fk"));
                cita.setFecha(rs.getDate("Fecha"));
                cita.setTurno(rs.getString("Turno"));
                cita.setEstado(rs.getString("Estado"));
                cita.setNumTurno(rs.getInt("NumeroTurno"));
                cita.setDetalle(rs.getString("Detalle"));
                cita.setDiagnostico(rs.getString("Diagnostico"));
                citas.add(cita);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return citas;
    }

    public List<Doctor> obtenerDoctoresPorDia(String dia) {
        List<Doctor> doctores = new ArrayList<>();
        String sql = "SELECT d.IDDoctor, d.DNI_fk, d.Especialidad, d.Usuario, d.Password "
                + "FROM doctor d "
                + "JOIN horariodoctor hd ON d.IDDoctor = hd.IDDoctor_fk2 "
                + "WHERE hd.DiaSemana = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, dia);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                System.out.println("IDDoctor: " + rs.getInt("IDDoctor"));
                System.out.println("DNI: " + rs.getString("DNI_fk"));
                System.out.println("Especialidad: " + rs.getString("Especialidad"));
                System.out.println("Usuario: " + rs.getString("Usuario"));
                System.out.println("Password: " + rs.getString("Password"));

                Doctor doctor = new Doctor();
                doctor.setIdDoctor(rs.getInt("IDDoctor"));
                doctor.setDni(rs.getString("DNI_fk"));
                doctor.setEspecialidad(rs.getString("Especialidad"));
                doctor.setUsuario(rs.getString("Usuario"));
                doctor.setPassword(rs.getString("Password"));
                doctores.add(doctor);

            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return doctores;
    }

}
