package Modelo.DAO;

import Conexion.Conexion;
import Modelo.HistorialMedico;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class HistorialMedicoDAO {

    public List<HistorialMedico> listarHistorialMedico(int idPaciente) {
        List<HistorialMedico> historialList = new ArrayList<>();
        String sql = "SELECT hm.NumeroHistorial, hm.Fecha, hm.Diagnostico, d.Nombre AS NombreDoctor " +
                     "FROM historialmedico hm " +
                     "JOIN doctor doc ON hm.IDDoctor_fk = doc.IDDoctor " +
                     "JOIN persona d ON doc.DNI_fk = d.DNI " +
                     "WHERE hm.IDPaciente_fk = ?";

        try (Connection con = Conexion.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, idPaciente);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    HistorialMedico historial = new HistorialMedico();
                    historial.setNumeroHistorial(rs.getInt("NumeroHistorial"));
                    historial.setFecha(rs.getDate("Fecha"));
                    historial.setDiagnostico(rs.getString("Diagnostico"));
                    historial.setNombreDoctor(rs.getString("NombreDoctor"));
                    historialList.add(historial);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return historialList;
    }

    public HistorialMedico obtenerHistorialMedicoConDetalles(int numeroHistorial) {
        HistorialMedico historial = null;
        String sql = "SELECT hm.Fecha, c.Turno, c.Detalle AS DetalleCita, hm.Diagnostico, d.Nombre AS NombreDoctor " +
                     "FROM historialmedico hm " +
                     "JOIN cita c ON hm.IDCita_fk = c.IDCita " +
                     "JOIN doctor doc ON hm.IDDoctor_fk = doc.IDDoctor " +
                     "JOIN persona d ON doc.DNI_fk = d.DNI " +
                     "WHERE hm.NumeroHistorial = ?";

        try (Connection con = Conexion.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, numeroHistorial);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    historial = new HistorialMedico();
                    historial.setFecha(rs.getDate("Fecha"));
                    historial.setTurno(rs.getString("Turno"));
                    historial.setDetalleCita(rs.getString("DetalleCita"));
                    historial.setDiagnostico(rs.getString("Diagnostico"));
                    historial.setNombreDoctor(rs.getString("NombreDoctor"));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return historial;
    }
}
