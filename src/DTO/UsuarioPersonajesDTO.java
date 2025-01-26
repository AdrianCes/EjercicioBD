package DTO;

public class UsuarioPersonajesDTO {
    private String nombreUsuario;
    private int numeroPersonajes;

    public UsuarioPersonajesDTO(String nombreUsuario, int numeroPersonajes) {
        this.nombreUsuario = nombreUsuario;
        this.numeroPersonajes = numeroPersonajes;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public int getNumeroPersonajes() {
        return numeroPersonajes;
    }
}