package DTO;

public class RegionServidoresDTO {
    private String region;
    private int numeroServidores;

    public RegionServidoresDTO(String region, int numeroServidores) {
        this.region = region;
        this.numeroServidores = numeroServidores;
    }

    public String getRegion() {
        return region;
    }

    public int getNumeroServidores() {
        return numeroServidores;
    }
}
