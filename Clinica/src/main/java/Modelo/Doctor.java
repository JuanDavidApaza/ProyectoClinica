package Modelo;

public class Doctor extends Persona {

    private int idDoctor;
    private String especialidad;
    private String usuario;
    private String password;

    public Doctor() {
    }

    public Doctor(String dni, String nombre, String apellido, String direccion, String telefono, String sexo, int edad, String email, String pais, int idDoctor, String especialidad, String usuario, String password) {
        super(dni, nombre, apellido, direccion, telefono, email, sexo, edad, pais);
        this.idDoctor = idDoctor;
        this.especialidad = especialidad;
        this.usuario = usuario;
        this.password = password;
    }

    public int getIdDoctor() {
        return idDoctor;
    }

    public void setIdDoctor(int idDoctor) {
        this.idDoctor = idDoctor;
    }

    public String getEspecialidad() {
        return especialidad;
    }

    public void setEspecialidad(String especialidad) {
        this.especialidad = especialidad;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return getNombre() + " " + getApellido();
    }

}
