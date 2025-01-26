package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import Conexion.Conexion;
import DTO.RegionServidoresDTO;
import DTO.ServidorDTO;
import DTO.UsuarioPersonajesDTO;
import DTO.UsuarioServidorDTO;

public class DAO {
    private Connection conexion;

    public DAO() {
        this.conexion = Conexion.obtenerConexion();
    }

    public List<UsuarioPersonajesDTO> obtenerNumeroPersonajesPorUsuario() throws SQLException {
        String query = "SELECT u.nombre AS usuario, COUNT(p.id) AS num_personajes " +
                       "FROM Usuarios u " +
                       "LEFT JOIN Personajes p ON u.id = p.usuario_id " +
                       "GROUP BY u.id HAVING num_personajes >= 1";

        try (PreparedStatement ps = conexion.prepareStatement(query);
             ResultSet rs = ps.executeQuery()) {

            List<UsuarioPersonajesDTO> resultados = new ArrayList<>();
            while (rs.next()) {
                resultados.add(new UsuarioPersonajesDTO(rs.getString("usuario"), rs.getInt("num_personajes")));
            }
            return resultados;
        }
    }

    public UsuarioPersonajesDTO obtenerNumeroPersonajesDeUsuario(String nombreUsuario) throws SQLException {
        String query = "SELECT u.nombre AS usuario, COUNT(p.id) AS num_personajes " +
                       "FROM Usuarios u " +
                       "LEFT JOIN Personajes p ON u.id = p.usuario_id " +
                       "WHERE u.nombre = ? GROUP BY u.id";

        try (PreparedStatement ps = conexion.prepareStatement(query)) {
            ps.setString(1, nombreUsuario);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new UsuarioPersonajesDTO(rs.getString("usuario"), rs.getInt("num_personajes"));
                }
            }
        }
        return null;
    }

    public List<UsuarioServidorDTO> obtenerPersonajesDeUsuario(String nombreUsuario) throws SQLException {
        String query = "SELECT u.nombre AS usuario, p.nombre AS personaje, s.nombre AS servidor " +
                       "FROM Usuarios u " +
                       "JOIN Personajes p ON u.id = p.usuario_id " +
                       "JOIN Servidores s ON p.servidor_id = s.id " +
                       "WHERE u.nombre = ?";

        try (PreparedStatement ps = conexion.prepareStatement(query)) {
            ps.setString(1, nombreUsuario);
            try (ResultSet rs = ps.executeQuery()) {
                List<UsuarioServidorDTO> resultados = new ArrayList<>();
                while (rs.next()) {
                    resultados.add(new UsuarioServidorDTO(rs.getString("usuario"), rs.getString("personaje"), rs.getString("servidor")));
                }
                return resultados;
            }
        }
    }

    public List<UsuarioServidorDTO> obtenerNumeroPersonajesPorServidor() throws SQLException {
        String query = "SELECT u.nombre AS usuario, COUNT(p.id) AS num_personajes, s.nombre AS servidor " +
                       "FROM Usuarios u " +
                       "JOIN Personajes p ON u.id = p.usuario_id " +
                       "JOIN Servidores s ON p.servidor_id = s.id " +
                       "GROUP BY u.id, s.id";

        try (PreparedStatement ps = conexion.prepareStatement(query);
             ResultSet rs = ps.executeQuery()) {

            List<UsuarioServidorDTO> resultados = new ArrayList<>();
            while (rs.next()) {
                resultados.add(new UsuarioServidorDTO(rs.getString("usuario"), rs.getString("num_personajes"), rs.getString("servidor")));
            }
            return resultados;
        }
    }

    public List<ServidorDTO> obtenerServidoresConMasPersonajes(int limite) throws SQLException {
        String query = "SELECT s.nombre AS servidor, COUNT(p.id) AS num_personajes " +
                       "FROM Servidores s " +
                       "JOIN Personajes p ON s.id = p.servidor_id " +
                       "GROUP BY s.id ORDER BY num_personajes DESC LIMIT ?";

        try (PreparedStatement ps = conexion.prepareStatement(query)) {
            ps.setInt(1, limite);
            try (ResultSet rs = ps.executeQuery()) {
                List<ServidorDTO> resultados = new ArrayList<>();
                while (rs.next()) {
                    resultados.add(new ServidorDTO(rs.getString("servidor"), rs.getInt("num_personajes")));
                }
                return resultados;
            }
        }
    }

    public int obtenerNumeroServidoresDeRegion(String region) throws SQLException {
        String query = "SELECT COUNT(*) AS num_servidores " +
                       "FROM Servidores " +
                       "WHERE region = ?";

        try (PreparedStatement ps = conexion.prepareStatement(query)) {
            ps.setString(1, region);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("num_servidores");
                }
            }
        }
        return 0;
    }

    public List<RegionServidoresDTO> obtenerNumeroServidoresPorRegion() throws SQLException {
        String query = "SELECT region, COUNT(*) AS num_servidores " +
                       "FROM Servidores GROUP BY region";

        try (PreparedStatement ps = conexion.prepareStatement(query);
             ResultSet rs = ps.executeQuery()) {

            List<RegionServidoresDTO> resultados = new ArrayList<>();
            while (rs.next()) {
                resultados.add(new RegionServidoresDTO(rs.getString("region"), rs.getInt("num_servidores")));
            }
            return resultados;
        }
    }
}
