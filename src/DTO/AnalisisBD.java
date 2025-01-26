package DTO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import Conexion.Conexion;
import DAO.DAO;

public class AnalisisBD {
    private Connection conexion;

    public AnalisisBD() {
        this.conexion = Conexion.obtenerConexion();
    }

    public void rankServers() throws SQLException {
        DAO dao = new DAO();
        List<ServidorDTO> servidores = dao.obtenerServidoresConMasPersonajes(5);
        for (ServidorDTO servidor : servidores) {
            System.out.println("El servidor " + servidor.getNombreServidor() + " con " + servidor.getNumeroPersonajes() + " personajes.");
        }
    }

    public void listServers() throws SQLException {
        String query = "SELECT region, nombre FROM Servidores ORDER BY region, nombre";
        try (PreparedStatement ps = conexion.prepareStatement(query);
             ResultSet rs = ps.executeQuery()) {

            String regionActual = "";
            while (rs.next()) {
                String region = rs.getString("region");
                String nombre = rs.getString("nombre");

                if (!region.equals(regionActual)) {
                    regionActual = region;
                    System.out.println("Región " + regionActual);
                }
                System.out.println("    " + nombre);
            }
        }
    }

    public void getUserPJ(String nombreUsuario) throws SQLException {
        DAO dao = new DAO();
        UsuarioPersonajesDTO usuario = dao.obtenerNumeroPersonajesDeUsuario(nombreUsuario);
        if (usuario != null) {
            System.out.println(usuario.getNombreUsuario() + " (" + usuario.getNumeroPersonajes() + " personajes)");

            List<UsuarioServidorDTO> personajes = dao.obtenerPersonajesDeUsuario(nombreUsuario);
            for (UsuarioServidorDTO personaje : personajes) {
                System.out.println("    " + personaje.getServidor() + "\n        " + personaje.getNombrePersonaje());
            }
        } else {
            System.out.println("Usuario no encontrado o sin personajes.");
        }
    }

    public void userPJs() throws SQLException {
        DAO dao = new DAO();
        List<UsuarioPersonajesDTO> usuarios = dao.obtenerNumeroPersonajesPorUsuario();
        int contador = 0;

        for (UsuarioPersonajesDTO usuario : usuarios) {
            System.out.print(usuario.getNombreUsuario() + " (" + usuario.getNumeroPersonajes() + ") ");
            contador++;

            if (contador % 5 == 0) {
                System.out.println();
            }
        }
        if (contador % 5 != 0) {
            System.out.println();
        }
    }

    public void areaMap(int idMapa) throws SQLException {
        String query = "SELECT ancho, alto FROM Zonas WHERE mapa_id = ?";

        try (PreparedStatement ps = conexion.prepareStatement(query)) {
            ps.setInt(1, idMapa);
            try (ResultSet rs = ps.executeQuery()) {
                int areaTotal = 0;
                while (rs.next()) {
                    int ancho = rs.getInt("ancho");
                    int alto = rs.getInt("alto");
                    areaTotal += ancho * alto;
                }
                System.out.println("El área total del mapa es: " + areaTotal);
            }
        }
    }
}