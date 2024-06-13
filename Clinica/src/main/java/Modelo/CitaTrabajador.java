package Modelo;

import java.util.Date;

public class CitaTrabajador {
    private int idCita;
    private int idTrabajador;
    private String accion;
    private Date fechaAccion;

    public CitaTrabajador() {}

    public CitaTrabajador(int idCita, int idTrabajador, String accion, Date fechaAccion) {
        this.idCita = idCita;
        this.idTrabajador = idTrabajador;
        this.accion = accion;
        this.fechaAccion = fechaAccion;
    }

    public int getIdCita() {
        return idCita;
    }

    public void setIdCita(int idCita) {
        this.idCita = idCita;
    }

    public int getIdTrabajador() {
        return idTrabajador;
    }

    public void setIdTrabajador(int idTrabajador) {
        this.idTrabajador = idTrabajador;
    }

    public String getAccion() {
        return accion;
    }

    public void setAccion(String accion) {
        this.accion = accion;
    }

    public Date getFechaAccion() {
        return fechaAccion;
    }

    public void setFechaAccion(Date fechaAccion) {
        this.fechaAccion = fechaAccion;
    }
}
