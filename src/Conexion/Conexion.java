package Conexion;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class Conexion {
    private static Properties connectionProps;
    private static Connection conn;        
    private static final String USER = "aces";
    private static final String PASSWORD = "191102";
    private static final String SERVER = "aces.iescotarelo.es";
    private static final String PORTNUMBER = "3306";
    private static final String DATABASE = "bd_ejercicio1_ad";

    private Conexion() { }

    public static Connection obtenerConexion() {
        if (conn == null) {
            try {
                connectionProps = new Properties();
                connectionProps.put("user", USER);
                connectionProps.put("password", PASSWORD);
                connectionProps.put("rewriteBatchedStatements", "true");

                String url = "jdbc:mysql://" + 
                SERVER + ":" 
                + PORTNUMBER + "/" 
                + DATABASE;
                conn = DriverManager.getConnection(url, connectionProps);
                System.out.println("Conexión establecida correctamente.");
            } catch (SQLException e) {
                System.err.println("Error al conectar a la base de datos: " + e.getMessage());
            }
        }
        return conn;
    }

    public static void cerrarConexion() {
        if (conn != null) {
            try {
                conn.close();
                System.out.println("Conexión cerrada correctamente.");
            } catch (SQLException e) {
                System.err.println("Error al cerrar la conexión: " + e.getMessage());
            } finally {
                conn = null;    
            }
        }
    }
}


// mysql -h 127.0.0.1 -P 3306 -u aces -p
