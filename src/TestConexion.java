import Conexion.CreadorBD;
import Conexion.GeneradorBD;

public class TestConexion {
    public static void main(String[] args) {
        GeneradorBD gen = new GeneradorBD();
        //gen.borrarTablas();
        //CreadorBD.crearTablas();       
        gen.insertarDatos();

    }
}