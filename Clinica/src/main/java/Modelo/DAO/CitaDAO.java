package Modelo.DAO;

import Modelo.Cita;
import Modelo.Doctor;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

public class CitaDAO {

    private Connection connection;

    public CitaDAO() {}

    public CitaDAO(Connection connection) {
        this.connection = connection;
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }

    public boolean insertarCita(Cita cita) {
        String sql = "INSERT INTO cita (IDPaciente_fk2, Fecha, Turno, NumeroTurno, Estado, Detalle, Diagnostico, IDDoctor_fk) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, cita.getIdPaciente());
            pstmt.setDate(2, new java.sql.Date(cita.getFecha().getTime())); // Convertir java.util.Date a java.sql.Date
            pstmt.setString(3, cita.getTurno());
            pstmt.setInt(4, cita.getNumTurno());
            pstmt.setString(5, cita.getEstado());
            pstmt.setString(6, cita.getDetalle()); // Asegúrate de establecer el valor del detalle
            pstmt.setString(7, cita.getDiagnostico());
            pstmt.setInt(8, cita.getIdDoctor());
            pstmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

   public List<Cita> obtenerCitasPendientes() {
    List<Cita> citas = new ArrayList<>();
    String sql = "SELECT c.*, pd.Nombre AS NombreDoctor, pd.Apellido AS ApellidoDoctor " +
                 "FROM cita c " +
                 "JOIN doctor d ON c.IDDoctor_fk = d.IDDoctor " +
                 "JOIN persona pd ON d.DNI_fk = pd.DNI " +
                 "WHERE c.Estado = 'pendiente'";
    try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
        ResultSet rs = pstmt.executeQuery();
        while (rs.next()) {
            Cita cita = new Cita();
            cita.setIdCita(rs.getInt("IDCita"));
            cita.setIdPaciente(rs.getInt("IDPaciente_fk2"));
            cita.setIdDoctor(rs.getInt("IDDoctor_fk"));
            cita.setFecha(rs.getDate("Fecha"));
            cita.setTurno(rs.getString("Turno"));
            cita.setEstado(rs.getString("Estado"));
            cita.setNumTurno(rs.getInt("NumeroTurno"));
            cita.setDetalle(rs.getString("Detalle"));
            cita.setDiagnostico(rs.getString("Diagnostico"));
            cita.setNombreDoctor(rs.getString("NombreDoctor") + " " + rs.getString("ApellidoDoctor"));
            citas.add(cita);
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return citas;
}


public List<Cita> obtenerCitasPorDNI(String dni) {
    List<Cita> citas = new ArrayList<>();
    String sql = "SELECT c.*, pd.Nombre AS NombreDoctor, pd.Apellido AS ApellidoDoctor " +
                 "FROM cita c " +
                 "JOIN doctor d ON c.IDDoctor_fk = d.IDDoctor " +
                 "JOIN persona pd ON d.DNI_fk = pd.DNI " +
                 "JOIN paciente p ON c.IDPaciente_fk2 = p.IDPaciente " +
                 "WHERE p.DNI_fk = ? AND c.Estado = 'pendiente'";
    try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
        pstmt.setString(1, dni);
        ResultSet rs = pstmt.executeQuery();
        while (rs.next()) {
            Cita cita = new Cita();
            cita.setIdCita(rs.getInt("IDCita"));
            cita.setIdPaciente(rs.getInt("IDPaciente_fk2"));
            cita.setIdDoctor(rs.getInt("IDDoctor_fk"));
            cita.setFecha(rs.getDate("Fecha"));
            cita.setTurno(rs.getString("Turno"));
            cita.setEstado(rs.getString("Estado"));
            cita.setNumTurno(rs.getInt("NumeroTurno"));
            cita.setDetalle(rs.getString("Detalle"));
            cita.setDiagnostico(rs.getString("Diagnostico"));
            cita.setNombreDoctor(rs.getString("NombreDoctor") + " " + rs.getString("ApellidoDoctor"));
            citas.add(cita);
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return citas;
}



    public boolean eliminarCita(int idCita) {
        String sql = "DELETE FROM cita WHERE IDCita = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, idCita);
            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0;
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

    public List<Doctor> obtenerDoctoresPorDiaYTurno(String dia, String turno) {
        List<Doctor> doctores = new ArrayList<>();
        String sql = "SELECT d.IDDoctor, d.DNI_fk, d.Especialidad, d.Usuario, d.Password, p.Nombre, p.Apellido "
                   + "FROM doctor d "
                   + "JOIN horariodoctor hd ON d.IDDoctor = hd.IDDoctor_fk2 "
                   + "JOIN persona p ON d.DNI_fk = p.DNI "
                   + "WHERE hd.DiaSemana = ? AND hd.Turno = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, dia);
            pstmt.setString(2, turno);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                Doctor doctor = new Doctor();
                doctor.setIdDoctor(rs.getInt("IDDoctor"));
                doctor.setDni(rs.getString("DNI_fk"));
                doctor.setEspecialidad(rs.getString("Especialidad"));
                doctor.setUsuario(rs.getString("Usuario"));
                doctor.setPassword(rs.getString("Password"));
                doctor.setNombre(rs.getString("Nombre"));
                doctor.setApellido(rs.getString("Apellido"));
                doctores.add(doctor);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return doctores;
    }
    
     public boolean actualizarEstadoCita(int idCita, String nuevoEstado) throws SQLException {
        String sql = "UPDATE cita SET Estado = ? WHERE IDCita = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, nuevoEstado);
            pstmt.setInt(2, idCita);
            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
        }
    }
     
     public Cita obtenerCitaPorId(int idCita) {
    Cita cita = null;
    String sql = "SELECT c.*, pd.Nombre AS NombreDoctor, pd.Apellido AS ApellidoDoctor " +
                 "FROM cita c " +
                 "JOIN doctor d ON c.IDDoctor_fk = d.IDDoctor " +
                 "JOIN persona pd ON d.DNI_fk = pd.DNI " +
                 "WHERE c.IDCita = ?";
    try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
        pstmt.setInt(1, idCita);
        ResultSet rs = pstmt.executeQuery();
        if (rs.next()) {
            cita = new Cita();
            cita.setIdCita(rs.getInt("IDCita"));
            cita.setIdPaciente(rs.getInt("IDPaciente_fk2"));
            cita.setIdDoctor(rs.getInt("IDDoctor_fk"));
            cita.setFecha(rs.getDate("Fecha"));
            cita.setTurno(rs.getString("Turno"));
            cita.setEstado(rs.getString("Estado"));
            cita.setNumTurno(rs.getInt("NumeroTurno"));
            cita.setDetalle(rs.getString("Detalle"));
            cita.setDiagnostico(rs.getString("Diagnostico"));
            cita.setNombreDoctor(rs.getString("NombreDoctor") + " " + rs.getString("ApellidoDoctor"));
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return cita;
}

}
