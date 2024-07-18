package Modelo;

import java.util.Date;

public class HistorialMedico {
    private int numeroHistorial;
    private int idPaciente_fk;
    private int idCita_fk;
    private Date fecha;
    private String diagnostico;
    private int idDoctor_fk;
    
    private Date fechaCita;
    private String turno;
    private String detalleCita;
    private String nombreDoctor;

    // Getters y Setters para todos los campos

    public int getNumeroHistorial() {
        return numeroHistorial;
    }

    public void setNumeroHistorial(int numeroHistorial) {
        this.numeroHistorial = numeroHistorial;
    }

    public int getIdPaciente_fk() {
        return idPaciente_fk;
    }

    public void setIdPaciente_fk(int idPaciente_fk) {
        this.idPaciente_fk = idPaciente_fk;
    }

    public int getIdCita_fk() {
        return idCita_fk;
    }

    public void setIdCita_fk(int idCita_fk) {
        this.idCita_fk = idCita_fk;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public String getDiagnostico() {
        return diagnostico;
    }

    public void setDiagnostico(String diagnostico) {
        this.diagnostico = diagnostico;
    }

    public int getIdDoctor_fk() {
        return idDoctor_fk;
    }

    public void setIdDoctor_fk(int idDoctor_fk) {
        this.idDoctor_fk = idDoctor_fk;
    }

    public Date getFechaCita() {
        return fechaCita;
    }

    public void setFechaCita(Date fechaCita) {
        this.fechaCita = fechaCita;
    }

    public String getTurno() {
        return turno;
    }

    public void setTurno(String turno) {
        this.turno = turno;
    }

    public String getDetalleCita() {
        return detalleCita;
    }

    public void setDetalleCita(String detalleCita) {
        this.detalleCita = detalleCita;
    }

    public String getNombreDoctor() {
        return nombreDoctor;
    }

    public void setNombreDoctor(String nombreDoctor) {
        this.nombreDoctor = nombreDoctor;
    }
}
