package Modelo.DAO;

import Modelo.Paciente;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import Conexion.Conexion;

public class PacienteDAO {

    private Connection conexion;

    public PacienteDAO() {
        try {
            this.conexion = Conexion.getConnection();
        } catch (SQLException ex) {
            Logger.getLogger(PacienteDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public boolean insertarPaciente(Paciente paciente) {
    if (existeDNI(paciente.getDni())) {
        return false;
    }

    String sqlPersona = "INSERT INTO persona (DNI, Nombre, Apellido, Direccion, Telefono, Email, Sexo, Edad) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
    String sqlPaciente = "INSERT INTO paciente (DNI_fk, Tipo) VALUES (?, ?)";
    String sqlGetIdPaciente = "SELECT LAST_INSERT_ID() AS IDPaciente";

    try {
        conexion.setAutoCommit(false);

        PreparedStatement statementPersona = conexion.prepareStatement(sqlPersona);
        statementPersona.setString(1, paciente.getDni());
        statementPersona.setString(2, paciente.getNombre());
        statementPersona.setString(3, paciente.getApellido());
        statementPersona.setString(4, paciente.getDireccion());
        statementPersona.setString(5, paciente.getTelefono());
        statementPersona.setString(6, paciente.getEmail());
        statementPersona.setString(7, paciente.getSexo());
        statementPersona.setInt(8, paciente.getEdad());
        statementPersona.executeUpdate();

        PreparedStatement statementPaciente = conexion.prepareStatement(sqlPaciente);
        statementPaciente.setString(1, paciente.getDni());
        statementPaciente.setString(2, paciente.getTipo());
        statementPaciente.executeUpdate();

        PreparedStatement statementGetIdPaciente = conexion.prepareStatement(sqlGetIdPaciente);
        ResultSet resultSet = statementGetIdPaciente.executeQuery();

        if (resultSet.next()) {
            int idPaciente = resultSet.getInt("IDPaciente");
            paciente.setIdPaciente(idPaciente);
        }

        conexion.commit();
        return true;

    } catch (SQLException ex) {
        try {
            conexion.rollback();
        } catch (SQLException e) {
            Logger.getLogger(PacienteDAO.class.getName()).log(Level.SEVERE, null, e);
        }
        Logger.getLogger(PacienteDAO.class.getName()).log(Level.SEVERE, null, ex);
        return false;
    } finally {
        try {
            conexion.setAutoCommit(true);
        } catch (SQLException e) {
            Logger.getLogger(PacienteDAO.class.getName()).log(Level.SEVERE, null, e);
        }
    }
}

    public boolean existeDNI(String dni) {
        String sql = "SELECT COUNT(*) FROM persona WHERE DNI = ?";
        try {
            PreparedStatement statement = conexion.prepareStatement(sql);
            statement.setString(1, dni);

            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt(1) > 0;
            }
        } catch (SQLException ex) {
            Logger.getLogger(PacienteDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    public Paciente obtenerPacientePorDNI(String dni) {
        String sql = "SELECT p.DNI, p.Nombre, p.Apellido, p.Direccion, p.Telefono, p.Email, p.Sexo, p.Edad, pa.IDPaciente, pa.Tipo " +
                     "FROM persona p " +
                     "JOIN paciente pa ON p.Dni = pa.DNI_fk " +
                     "WHERE p.DNI = ?";
        try {
            PreparedStatement statement = conexion.prepareStatement(sql);
            statement.setString(1, dni);

            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                String nombre = resultSet.getString("Nombre");
                String apellido = resultSet.getString("Apellido");
                String direccion = resultSet.getString("Direccion");
                String telefono = resultSet.getString("Telefono");
                String email = resultSet.getString("Email");
                String sexo = resultSet.getString("Sexo");
                int edad = resultSet.getInt("Edad");
                int idPaciente = resultSet.getInt("IDPaciente");
                String tipo = resultSet.getString("Tipo");

                return new Paciente(dni, nombre, apellido, direccion, telefono, email, sexo, edad, idPaciente, tipo);
            } else {
                return null;
            }

        } catch (SQLException ex) {
            Logger.getLogger(PacienteDAO.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
    public boolean actualizarPaciente(Paciente paciente) {
    String sqlPersona = "UPDATE persona SET Nombre = ?, Apellido = ?, Direccion = ?, Telefono = ?, Email = ?, Sexo = ?, Edad = ? WHERE DNI = ?";
    try {
        conexion.setAutoCommit(false);  // Start transaction

        PreparedStatement statementPersona = conexion.prepareStatement(sqlPersona);
        statementPersona.setString(1, paciente.getNombre());
        statementPersona.setString(2, paciente.getApellido());
        statementPersona.setString(3, paciente.getDireccion());
        statementPersona.setString(4, paciente.getTelefono());
        statementPersona.setString(5, paciente.getEmail());
        statementPersona.setString(6, paciente.getSexo());
        statementPersona.setInt(7, paciente.getEdad());
        statementPersona.setString(8, paciente.getDni());
        int rowsUpdated = statementPersona.executeUpdate();

        conexion.commit();  // Commit transaction
        return rowsUpdated > 0;

    } catch (SQLException ex) {
        try {
            conexion.rollback();  // Rollback transaction on error
        } catch (SQLException e) {
            Logger.getLogger(PacienteDAO.class.getName()).log(Level.SEVERE, null, e);
        }
        Logger.getLogger(PacienteDAO.class.getName()).log(Level.SEVERE, null, ex);
        return false;
    } finally {
        try {
            conexion.setAutoCommit(true);  // Reset auto-commit
        } catch (SQLException e) {
            Logger.getLogger(PacienteDAO.class.getName()).log(Level.SEVERE, null, e);
        }
    }
}

}
