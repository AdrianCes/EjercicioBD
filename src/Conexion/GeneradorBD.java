package Conexion;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Random;

public class GeneradorBD {

    String[] nombres = {
            "Carlos", "María", "José", "Ana", "Luis", "Elena", "Pedro", "Lucía", "Fernando", "Clara",
            "Alberto", "Sara", "Raúl", "Laura", "Javier", "Marta", "Ricardo", "Patricia", "Daniel", "Beatriz",
            "Manuel", "Carmen", "Adrián", "Isabel", "Santiago", "Victoria", "Diego", "Rosa", "Francisco", "Gloria",
            "Jorge", "Sofía", "Rubén", "Paula", "Antonio", "Julia", "Andrés", "Verónica", "Héctor", "Eva",
            "Gabriel", "Silvia", "Rafael", "Alejandra", "Miguel", "Teresa", "Iván", "Natalia", "Óscar", "Pilar"
    };

    String[] nombresPersonajes = {
            "Mario", "Luigi", "Peach", "Bowser", "Link", "Zelda", "Ganondorf", "Samus", "Pikachu", "Ash",
            "Misty", "Brock", "Charizard", "Squirtle", "Bulbasaur", "Donkey Kong", "Diddy Kong", "Kirby", "Meta Knight",
            "King Dedede",
            "Fox McCloud", "Falco Lombardi", "Krystal", "Wolf O'Donnell", "Ness", "Lucas", "Captain Falcon",
            "Jigglypuff", "Marth", "Ike",
            "Chrom", "Robin", "Lucina", "Roy", "Corrin", "Byleth", "Shulk", "Pit", "Palutena", "Dark Pit",
            "Simon Belmont", "Richter Belmont", "Alucard", "Sonic", "Tails", "Knuckles", "Shadow", "Amy", "Dr. Eggman",
            "Crash Bandicoot",
            "Spyro", "Ryu", "Ken", "Chun-Li", "Guile", "Blanka", "E. Honda", "Dhalsim", "Zangief", "Cammy",
            "Vega", "M. Bison", "Jill Valentine", "Chris Redfield", "Leon Kennedy", "Ada Wong", "Claire Redfield",
            "Albert Wesker", "Dante", "Vergil",
            "Trish", "Bayonetta", "Jeanne", "Raiden", "Solid Snake", "Big Boss", "Otacon", "Revolver Ocelot",
            "Liquid Snake", "The Boss",
            "Kratos", "Atreus", "Lara Croft", "Nathan Drake", "Ellie", "Joel", "Tommy", "Abby", "Geralt de Rivia",
            "Yennefer",
            "Ciri", "Dandelion", "Triss Merigold", "V", "Johnny Silverhand", "Panam Palmer", "Judy Alvarez",
            "Jackie Welles", "Goro Takemura", "Rogue"
    };

    String[] servidores = {
            "ServidorCentral01", "DataHub02", "CloudNode03", "BackupServer04",
            "ProxyNode05", "Database06", "WebServer07",
            "APIServer08", "AuthServer09", "FileServer10"
    };

    String[] mapas = {
            "IslaTropical01", "DesiertoArido02", "CavernaOscura03", "CiudadFuturista04",
            "BosqueEncantado05", "MontañaNevada06", "PantanoSombrío07", "RuinasAntiguas08",
            "LagoCristalino09", "VolcánActivo10", "CañónRojo11", "JunglaExótica12",
            "CastilloMisterioso13", "PlayaParadisíaca14", "CamposDorados15", "PáramoHelado16",
            "FortalezaAbandonada17", "CráterGigante18", "CiudadSubterránea19", "ZonaIndustrial20"
    };

    private static final Random random = new Random();
    Connection conexion = Conexion.obtenerConexion();

    public void insertarServidores() {
        try (PreparedStatement ps = conexion.prepareStatement("INSERT INTO Servidores (nombre, region) VALUES (? , ?)")) {
            String[] regiones = { "Ámerica", "Europa", "África" };
            for (int i = 1; i < servidores.length; i++) {
                ps.setString(1, servidores[i]);
                ps.setString(2, regiones[i % regiones.length]);
                ps.addBatch();
            }
            ps.executeBatch();
        } catch (SQLException e) {
            System.err.println("Error al insertar datos de servidores: " + e.getMessage());
        }
    }

    public void insertarUsuarios() {
        try (PreparedStatement ps = conexion.prepareStatement("INSERT INTO Usuarios (nombre, codigo_unico) VALUES (? , ?)")) {
            for (int i = 1; i < nombres.length; i++) {
                ps.setString(1, nombres[i]);
                ps.setInt(2, generarCodigo());
                ps.addBatch();
            }
            ps.executeBatch();
        } catch (SQLException e) {
            System.err.println("Error al insertar datos de usuarios: " + e.getMessage());
        }
    }

    public void insertarMapas() {
        try (PreparedStatement ps = conexion.prepareStatement("INSERT INTO Mapas (nombre, dificultad) VALUES (? , ?)")) {
            for (int i = 1; i < mapas.length; i++) {
                ps.setString(1, mapas[i]);
                ps.setInt(2, random.nextInt(10));
                ps.addBatch();
            }
            ps.executeBatch();
        } catch (SQLException e) {
            System.err.println("Error al insertar datos de mapas: " + e.getMessage());
        }
    }

    public void insertarPersonajes() {
        try (PreparedStatement ps = conexion.prepareStatement("INSERT INTO Personajes (nombre, usuario_id, servidor_id) VALUES (? , ? , ?)")) {
            ResultSet rsUsuarios = conexion.createStatement().executeQuery("SELECT COUNT(*) FROM Usuarios");
            rsUsuarios.next();
            int numUsuarios = rsUsuarios.getInt(1); 
            ResultSet rsServidores = conexion.createStatement().executeQuery("SELECT COUNT(*) FROM Servidores");
            rsServidores.next();
            int numServidores = rsServidores.getInt(1);
            for (int i = 1; i < nombresPersonajes.length; i++) {
                ps.setString(1, nombresPersonajes[i]);
                ps.setInt(2, random.nextInt(numUsuarios) + 1);
                ps.setInt(3, random.nextInt(numServidores) + 1);
                ps.addBatch();
            }
            ps.executeBatch();
        } catch (SQLException e) {
            System.err.println("Error al insertar datos de personajes: " + e.getMessage());
        }
    }

    public void insertarZonas() {
        try (PreparedStatement ps = conexion.prepareStatement("INSERT INTO Zonas (mapa_id, ancho, alto, nombre) VALUES (? , ? , ? , ?)")) {
            ResultSet rsMapas = conexion.createStatement().executeQuery("SELECT COUNT(*) FROM Mapas");
            rsMapas.next();
            int numMapas = rsMapas.getInt(1);
            
            for (int mapaId = 1; mapaId <= numMapas; mapaId++) {
                int numZonas = random.nextInt(5) + 1; 
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
            }
        } else {
            System.err.println("Conexión a la base de datos no establecida.");
        }
    }

    public int generarCodigo() {
        return 1000 + random.nextInt(9000); //
    }

    public void insertarDatos() {
        insertarServidores();
        insertarUsuarios();
        insertarMapas();
        insertarPersonajes();
        insertarZonas();
    }

}
