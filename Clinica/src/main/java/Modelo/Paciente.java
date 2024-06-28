package Modelo;

public class Paciente extends Persona {
    private int idPaciente;
    private String tipo;

    public Paciente() {}

    public Paciente(String dni, String nombre, String apellido, String direccion, String telefono, String email,String sexo, int edad,String pais, int idPaciente, String tipo) {
        super(dni, nombre, apellido, direccion, telefono, email,sexo,edad,pais);
        this.idPaciente = idPaciente;
        this.tipo = tipo;
    }

    public int getIdPaciente() {
        return idPaciente;
    }

    public void setIdPaciente(int idPaciente) {
        this.idPaciente = idPaciente;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
}
