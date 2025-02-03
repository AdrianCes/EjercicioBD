package Conexion;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class CreadorBD {
    public static void crearTablas() {
        Connection conexion = Conexion.obtenerConexion();

        if (conexion != null) {
            try (Statement stm = conexion.createStatement()) {
                String tablaServidores= "CREATE TABLE IF NOT EXISTS Servidores ("
                + "id INT AUTO_INCREMENT PRIMARY KEY,"
                + "nombre VARCHAR(50) NOT NULL,"
                + "region VARCHAR(50) NOT NULL"
                + ");";
                stm.execute(tablaServidores);

                String tablaUsuarios = "CREATE TABLE IF NOT EXISTS Usuarios ("
                + "id INT AUTO_INCREMENT PRIMARY KEY,"
                + "nombre VARCHAR(50) NOT NULL,"
                + "codigo_unico VARCHAR(4) NOT NULL UNIQUE"
                + ");";
                stm.execute(tablaUsuarios);

                String tablaPersonajes = "CREATE TABLE IF NOT EXISTS Personajes ("
                + "id INT AUTO_INCREMENT PRIMARY KEY,"
                + "nombre VARCHAR(50) NOT NULL,"
                + "usuario_id INT NOT NULL,"
                + "servidor_id INT NOT NULL,"
                + "FOREIGN KEY (usuario_id) REFERENCES Usuarios(id) ON DELETE CASCADE,"
                + "FOREIGN KEY (servidor_id) REFERENCES Servidores(id) ON DELETE CASCADE"
                + ");";
                stm.execute(tablaPersonajes);

                String tablaMapas = "CREATE TABLE IF NOT EXISTS Mapas ("
                + "id INT AUTO_INCREMENT PRIMARY KEY,"
                + "nombre VARCHAR(50) NOT NULL,"
                + "dificultad INT NOT NULL CHECK (dificultad BETWEEN 0 AND 9)"
                + ");";
                stm.execute(tablaMapas);

                String tablaServidoresMapas = "CREATE TABLE IF NOT EXISTS ServidorMapas ("
                + "servidor_id INT NOT NULL,"
                + "mapa_id INT NOT NULL,"
                + "PRIMARY KEY (servidor_id, mapa_id),"
                + "FOREIGN KEY (servidor_id) REFERENCES Servidores(id) ON DELETE CASCADE,"
                + "FOREIGN KEY (mapa_id) REFERENCES Mapas(id) ON DELETE CASCADE"
                + ");";
                stm.execute(tablaServidoresMapas);

                String tablaZonas = "CREATE TABLE IF NOT EXISTS Zonas ("
                    + "id INT AUTO_INCREMENT PRIMARY KEY,"
                    + "mapa_id INT NOT NULL,"
                    + "ancho INT NOT NULL,"
                    + "alto INT NOT NULL,"
                    + "nombre VARCHAR(50) NOT NULL,"
                    + "FOREIGN KEY (mapa_id) REFERENCES Mapas(id) ON DELETE CASCADE"
                    + ");";
                stm.execute(tablaZonas);

                System.out.println("Tablas creadas con éxito");
            } catch (SQLException e) {
                System.err.println("Error al crear las tablas: " + e.getMessage());
            } finally {
                Conexion.cerrarConexion();
            }
        }
    }
}
