package Modelo;

import java.util.Date;

public class Cita {
    private int idCita;
    private int idPaciente;
    private int idDoctor;
    private Date fecha;
    private String turno;
    private String estado;
    private int numTurno;
    private String detalle;
    private String diagnostico;
    private String nombrePaciente;
    private String dniPaciente;
    private String nombreDoctor;
    private String tipoPaciente;

    public Cita() {}

    public Cita(int idCita, int idPaciente, int idDoctor, Date fecha, String turno, String estado, int numTurno, String detalle, String diagnostico) {
        this.idCita = idCita;
        this.idPaciente = idPaciente;
        this.idDoctor = idDoctor;
        this.fecha = fecha;
        this.turno = turno;
        this.estado = estado;
        this.numTurno = numTurno;
        this.detalle = detalle;
        this.diagnostico = diagnostico;
    }

    public Cita(int idCita, int idPaciente, int idDoctor, Date fecha, String turno, String estado, int numTurno, String detalle, String diagnostico, String nombreDoctor) {
        this.idCita = idCita;
        this.idPaciente = idPaciente;
        this.idDoctor = idDoctor;
        this.fecha = fecha;
        this.turno = turno;
        this.estado = estado;
        this.numTurno = numTurno;
        this.detalle = detalle;
        this.diagnostico = diagnostico;
        this.nombreDoctor = nombreDoctor;
    }

    public Cita(int idCita, int idPaciente, int idDoctor, Date fecha, String turno, String estado, int numTurno, String detalle, String diagnostico, String nombrePaciente, String dniPaciente, String nombreDoctor, String tipoPaciente) {
        this.idCita = idCita;
        this.idPaciente = idPaciente;
        this.idDoctor = idDoctor;
        this.fecha = fecha;
        this.turno = turno;
        this.estado = estado;
        this.numTurno = numTurno;
        this.detalle = detalle;
        this.diagnostico = diagnostico;
        this.nombrePaciente = nombrePaciente;
        this.dniPaciente = dniPaciente;
        this.nombreDoctor = nombreDoctor;
        this.tipoPaciente = tipoPaciente;
    }
    
    

    // Getters y setters
    public int getIdCita() {
        return idCita;
    }

    public void setIdCita(int idCita) {
        this.idCita = idCita;
    }

    public int getIdPaciente() {
        return idPaciente;
    }

    public void setIdPaciente(int idPaciente) {
        this.idPaciente = idPaciente;
    }

    public int getIdDoctor() {
        return idDoctor;
    }

    public void setIdDoctor(int idDoctor) {
        this.idDoctor = idDoctor;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public String getTurno() {
        return turno;
    }

    public void setTurno(String turno) {
        this.turno = turno;
    }

    public String getDetalle() {
        return detalle;
    }

    public void setDetalle(String detalle) {
        this.detalle = detalle;
    }

    public String getDiagnostico() {
        return diagnostico;
    }

    public void setDiagnostico(String diagnostico) {
        this.diagnostico = diagnostico;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public int getNumTurno() {
        return numTurno;
    }

    public void setNumTurno(int numTurno) {
        this.numTurno = numTurno;
    }

    public String getNombrePaciente() {
        return nombrePaciente;
    }

    public void setNombrePaciente(String nombrePaciente) {
        this.nombrePaciente = nombrePaciente;
    }

    public String getDniPaciente() {
        return dniPaciente;
    }

    public void setDniPaciente(String dniPaciente) {
        this.dniPaciente = dniPaciente;
    }

    public String getNombreDoctor() {
        return nombreDoctor;
    }

    public void setNombreDoctor(String nombreDoctor) {
        this.nombreDoctor = nombreDoctor;
    }

    public String getTipoPaciente() {
        return tipoPaciente;
    }

    public void setTipoPaciente(String tipoPaciente) {
        this.tipoPaciente = tipoPaciente;
    }
    
    
}
