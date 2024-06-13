package Modelo;

public class Trabajador extends Persona {
    private int idTrabajador;
    private String usuario;
    private String password;
    private String rol;

    public Trabajador() {}

    public Trabajador(String dni, String nombre, String apellido, String direccion, String telefono, String email, int idTrabajador, String usuario, String password, String rol) {
        super(dni, nombre, apellido, direccion, telefono, email);
        this.idTrabajador = idTrabajador;
        this.usuario = usuario;
        this.password = password;
        this.rol = rol;
    }

    public int getIdTrabajador() {
        return idTrabajador;
    }

    public void setIdTrabajador(int idTrabajador) {
        this.idTrabajador = idTrabajador;
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

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }
}
