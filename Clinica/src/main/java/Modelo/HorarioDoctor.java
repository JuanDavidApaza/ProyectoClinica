package Modelo;

public class HorarioDoctor {
    private int idHorario;
    private int idDoctor;
    private String dia;
    private String turno;

    public HorarioDoctor() {}

    public HorarioDoctor(int idHorario, int idDoctor, String dia, String turno) {
        this.idHorario = idHorario;
        this.idDoctor = idDoctor;
        this.dia = dia;
        this.turno = turno;
    }

    public int getIdHorario() {
        return idHorario;
    }

    public void setIdHorario(int idHorario) {
        this.idHorario = idHorario;
    }

    public int getIdDoctor() {
        return idDoctor;
    }

    public void setIdDoctor(int idDoctor) {
        this.idDoctor = idDoctor;
    }

    public String getDia() {
        return dia;
    }

    public void setDia(String dia) {
        this.dia = dia;
    }

    public String getTurno() {
        return turno;
    }

    public void setTurno(String turno) {
        this.turno = turno;
    }
}
