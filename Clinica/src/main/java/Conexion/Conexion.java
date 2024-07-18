package Conexion;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.sql.Connection;
import java.sql.SQLException;

public class Conexion {

    private static final String JDBC_URL = "jdbc:mysql://b7efgmkpw5fdp5959znu-mysql.services.clever-cloud.com:3306/b7efgmkpw5fdp5959znu";
    private static final String JDBC_USER = "ug2zfbqc8kgapoey";
    private static final String JDBC_PASSWORD = "DMrJ2K4e3Xdy4xZce4vc";

    private static HikariDataSource dataSource;

    static {
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl(JDBC_URL);
        config.setUsername(JDBC_USER);
        config.setPassword(JDBC_PASSWORD);
        config.setMaximumPoolSize(5); 
        config.setConnectionTimeout(40000); 
        config.setIdleTimeout(600000); 
        config.setMaxLifetime(1800000);

        dataSource = new HikariDataSource(config);
    }

    public static Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }

    public static void close(Connection conn) {
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static void close(AutoCloseable closeable) {
        if (closeable != null) {
            try {
                closeable.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    // Optional: If you want to test the connection
    public static void main(String[] args) {
        try (Connection conn = getConnection()) {
            if (conn != null) {
                System.out.println("¡Conexión establecida!");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            System.out.println("Error al conectar con la base de datos.");
        }
    }
}
