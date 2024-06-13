package Modelo;

public class HistorialMedico {
    private int numeroHistorial;
    private int idPaciente;
    private String antecedentesMedicos;

    public HistorialMedico() {}

    public HistorialMedico(int numeroHistorial, int idPaciente, String antecedentesMedicos) {
        this.numeroHistorial = numeroHistorial;
        this.idPaciente = idPaciente;
        this.antecedentesMedicos = antecedentesMedicos;
    }

    public int getNumeroHistorial() {
        return numeroHistorial;
    }

    public void setNumeroHistorial(int numeroHistorial) {
        this.numeroHistorial = numeroHistorial;
    }

    public int getIdPaciente() {
        return idPaciente;
    }

    public void setIdPaciente(int idPaciente) {
        this.idPaciente = idPaciente;
    }

    public String getAntecedentesMedicos() {
        return antecedentesMedicos;
    }

    public void setAntecedentesMedicos(String antecedentesMedicos) {
        this.antecedentesMedicos = antecedentesMedicos;
    }
}
