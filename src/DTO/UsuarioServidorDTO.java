package DTO;

public class UsuarioServidorDTO {
    private String nombreUsuario;
    private String nombrePersonaje;
    private String servidor;

    public UsuarioServidorDTO(String nombreUsuario, String nombrePersonaje, String servidor) {
        this.nombreUsuario = nombreUsuario;
        this.nombrePersonaje = nombrePersonaje;
        this.servidor = servidor;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public String getNombrePersonaje() {
        return nombrePersonaje;
    }

    public String getServidor() {
        return servidor;
    }
}

