package DTO;

public class ServidorDTO {
    private String nombreServidor;
    private int numeroPersonajes;

    public ServidorDTO(String nombreServidor, int numeroPersonajes) {
        this.nombreServidor = nombreServidor;
        this.numeroPersonajes = numeroPersonajes;
    }

    public String getNombreServidor() {
        return nombreServidor;
    }

    public int getNumeroPersonajes() {
        return numeroPersonajes;
    }
}