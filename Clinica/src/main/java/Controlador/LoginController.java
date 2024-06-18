package Controlador;

import Modelo.DAO.TrabajadorDAO;
import Modelo.DAO.DoctorDAO;
import Modelo.Trabajador;
import Modelo.Doctor;
import Vista.Login;
import javax.swing.JOptionPane;

public class LoginController {
    private Login loginView;
    private TrabajadorDAO trabajadorDAO;
    private DoctorDAO doctorDAO;
    private CtrlPrincipal ctrlPrincipal;

    public LoginController(Login loginView, CtrlPrincipal ctrlPrincipal) {
        this.loginView = loginView;
        this.trabajadorDAO = new TrabajadorDAO();
        this.doctorDAO = new DoctorDAO();
        this.ctrlPrincipal = ctrlPrincipal;

        this.loginView.btnIngresar.addActionListener(e -> autenticarUsuario());
        this.loginView.btnCancelar.addActionListener(e -> borrarDatos());
    }

    public void autenticarUsuario() {
        String usuario = loginView.txtuser.getText();
        String password = new String(loginView.txtpassword.getPassword());

        Trabajador trabajador = trabajadorDAO.obtenerTrabajadorPorUsuarioYContrasena(usuario, password);
        Doctor doctor = doctorDAO.obtenerDoctorPorUsuarioYContrasena(usuario, password);

        if (trabajador != null) {
            JOptionPane.showMessageDialog(loginView, "Bienvenido " + trabajador.getRol());
            ctrlPrincipal.configurarInterfaz(trabajador.getRol());
        } else if (doctor != null) {
            JOptionPane.showMessageDialog(loginView, "Bienvenido Doctor " + doctor.getUsuario());
            ctrlPrincipal.configurarInterfaz("Doctor");
        } else {
            JOptionPane.showMessageDialog(loginView, "Usuario o contraseña incorrectos");
        }
    }
    
    public void borrarDatos(){
        loginView.txtpassword.setText("");
        loginView.txtuser.setText("");
    }
}
