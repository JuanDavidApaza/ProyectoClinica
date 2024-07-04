package Modelo.DAO;

import Modelo.HorarioDoctor;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class HorarioDoctorDAO {
    private Connection connection;

    public HorarioDoctorDAO(Connection connection) {
        this.connection = connection;
    }
    
   public List<HorarioDoctor> obtenerHorariosDoctor() {
        List<HorarioDoctor> horarios = new ArrayList<>();
        String sql = "SELECT * FROM horariodoctor";
        try (Statement stmt = connection.createStatement()) {
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                HorarioDoctor horario = new HorarioDoctor();
                // Set fields of horario from ResultSet
                horarios.add(horario);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return horarios;
    }

}
