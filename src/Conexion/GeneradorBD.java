package Conexion;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Random;


public class GeneradorBD {


    private static final Random random = new Random();
    Connection conexion = Conexion.obtenerConexion();
    public void insertarServidores(){
        try (PreparedStatement ps = conexion.prepareStatement("INSERT INTO Servidores (nombre, region) VALUES (? , ?)")){
            String[] regiones = {"Ámerica", "Europa", "África"};
            for (int i = 1 ; i <= 10; i++) {
                ps.setString(1, "Servidor_" + i);
                ps.setString(2, regiones[i % regiones.length]);
                ps.addBatch();
            }
            ps.executeBatch();
        } catch (SQLException e) {
            System.err.println("Error al insertar datos de servidores: " + e.getMessage());
        }
    }

    public void insertarUsuarios(){
        try (PreparedStatement ps = conexion.prepareStatement("INSERT INTO Usuarios (nombre, codigo_unico) VALUES (? , ?)")) {
            for (int i = 1; i <= 50; i++) {
                ps.setString(1, "Usuario_" + i);
                ps.setInt(2, generarCodigo());
                ps.addBatch();
            }
            ps.executeBatch();
        } catch (SQLException e) {
            System.err.println("Error al insertar datos de usuarios: " + e.getMessage());
        }
    }

    public void insertarPersonajes(){
        try (PreparedStatement ps = conexion.prepareStatement("INSERT INTO Personajes (nombre, usuario_id, servidor_id) VALUES (? , ? , ?)")) {
            for (int i = 1; i <= 100; i++) {
                ps.setString(1, "Personaje_" + i);
                ps.setInt(2, random.nextInt(50)+1);
                ps.setInt(3, random.nextInt(10)+1);
                ps.addBatch();
            }
            ps.executeBatch();
        } catch (SQLException e) {
            System.err.println("Error al insertar datos de personajes: " + e.getMessage());
        }
    }

    public void insertarMapas(){
        try (PreparedStatement ps = conexion.prepareStatement("INSERT INTO Mapas (nombre, dificultad) VALUES (? , ?)")) {
            for (int i = 1; i <= 20; i++) {
                ps.setString(1, "Nombre_" + i);
                ps.setInt(2, random.nextInt(10));
                ps.addBatch();
            }
            ps.executeBatch();
        } catch (SQLException e) {
            System.err.println("Error al insertar datos de mapas: " + e.getMessage());
        }
    }

    public void insertarZonas(){
        try (PreparedStatement ps = conexion.prepareStatement("INSERT INTO Zonas (mapa_id, ancho, alto, nombre) VALUES (? , ? , ? , ?)")) {
            for (int mapaId = 1; mapaId <= 20; mapaId++) {
                int numZonas = random.nextInt(5) + 1; // 1-5 zonas por mapa
                for (int i = 1; i <= numZonas; i++) {
                    ps.setInt(1, mapaId);
                    ps.setInt(2, random.nextInt(100) + 1); // ancho entre 1 y 100
                    ps.setInt(3, random.nextInt(100) + 1); // alto entre 1 y 100
                    ps.setString(4, "Zona_" + mapaId + "_" + i);
                    ps.addBatch();
                }
                ps.executeBatch();
            }
        } catch (SQLException e) {
            System.err.println("Error al insertar datos de zonas: " + e.getMessage());
        }
    }

    public void borrarTablas() {
        if (conexion != null) {
            try (Statement stm = conexion.createStatement()) {
                stm.execute("DROP TABLE IF EXISTS ServidorMapas;");
                stm.execute("DROP TABLE IF EXISTS Zonas;");
                stm.execute("DROP TABLE IF EXISTS Personajes;");
                stm.execute("DROP TABLE IF EXISTS Mapas;");
                stm.execute("DROP TABLE IF EXISTS Usuarios;");
                stm.execute("DROP TABLE IF EXISTS Servidores;");
                System.out.println("Tablas borradas con éxito.");
            } catch (SQLException e) {
                System.err.println("Error al borrar las tablas: " + e.getMessage());
            } finally {
                Conexion.cerrarConexion();
            }
        } else {
            System.err.println("Conexión a la base de datos no establecida.");
        }
    }
    public int generarCodigo(){
        return 1000 + random.nextInt(9000); //
    }

    public void insertarDatos(){
        insertarServidores();
        insertarUsuarios();
        insertarPersonajes();
        insertarMapas();
        insertarZonas();
    }
    
}
