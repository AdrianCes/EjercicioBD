import Conexion.CreadorBD;
import Conexion.GeneradorBD;

public class TestConexion {
    public static void main(String[] args) {
        CreadorBD.crearTablas();
        GeneradorBD generar = new GeneradorBD();
        generar.insertarDatos();
    }
}