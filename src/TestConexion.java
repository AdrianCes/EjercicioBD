import Conexion.CreadorBD;
import Conexion.GeneradorBD;

public class TestConexion {
    public static void main(String[] args) {
        CreadorBD.crearTablas();
        GeneradorBD gen = new GeneradorBD();
        //gen.borrarTablas();
        gen.insertarDatos();

    }
}