package Modelo.DAO;

import Conexion.Conexion;
import Modelo.Pago;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class PagoDAO {

    private Connection connection;

    public void setConnection(Connection connection) {
        this.connection = connection;
    }

    public boolean insertarPago(Pago pago) throws SQLException {
        String sql = "INSERT INTO pago (IDCita_fk, Monto, FechaPago) VALUES (?, ?, ?)";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, pago.getIdCita());
            pstmt.setDouble(2, pago.getMonto());
            pstmt.setDate(3, new java.sql.Date(pago.getFechaPago().getTime()));

            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
        }
    }
}
