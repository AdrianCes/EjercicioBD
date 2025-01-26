import java.sql.SQLException;
import java.util.Scanner;

import Conexion.Conexion;
import Conexion.CreadorBD;
import Conexion.GeneradorBD;
import DTO.AnalisisBD;

public class Main {
    public static void main(String[] args) {
        //GeneradorBD generadorBD = new GeneradorBD();
        //generadorBD.insertarDatos();
        
        AnalisisBD analisisBD = new AnalisisBD();
        try {
            System.out.println("Menú de opciones:");
            System.out.println("1. Rank de servidores");
            System.out.println("2. Listar servidores por región");
            System.out.println("3. Personajes de un usuario");
            System.out.println("4. Usuarios y sus personajes");
            System.out.println("5. Área de un mapa");
            System.out.print("Seleccione una opción: ");

            Scanner scanner = new Scanner(System.in);
            int opcion = scanner.nextInt();

            switch (opcion) {
                case 1:
                    analisisBD.rankServers();
                    break;
                case 2:
                    analisisBD.listServers();
                    break;
                case 3:
                    System.out.print("Ingrese el nombre del usuario: ");
                    String usuario = scanner.next();
                    analisisBD.getUserPJ(usuario);
                    break;
                case 4:
                    analisisBD.userPJs();
                    break;
                case 5:
                    System.out.print("Ingrese el ID del mapa: ");
                    int idMapa = scanner.nextInt();
                    analisisBD.areaMap(idMapa);
                    break;
                default:
                    System.out.println("Opción no válida.");
            }
            scanner.close();
        } catch (SQLException e) {
            System.err.println("Error en la base de datos: " + e.getMessage());
        } catch (Exception ex) {
            System.err.println("Error: " + ex.getMessage());
        } finally {
            Conexion.cerrarConexion();
        }
    }
}
