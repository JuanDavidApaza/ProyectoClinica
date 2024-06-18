package Controlador;

import Modelo.DAO.TrabajadorDAO;
import Modelo.DAO.DoctorDAO;
import Vista.*;

public class CtrlPrincipal {
    private final Login loginView;
    private final LoginController loginController;
    private final VentanaTrabajador ventanaTrabajador;
    private final VentanaAdministrador ventanaAdministrador;
    private final VentanaDoctor ventanaDoctor;

    public CtrlPrincipal() {
        this.loginView = new Login();
        this.ventanaTrabajador = new VentanaTrabajador();
        this.ventanaAdministrador = new VentanaAdministrador();
        this.ventanaDoctor = new VentanaDoctor();

        this.loginController = new LoginController(loginView, this);
        inicio();
    }

    private void inicio() {
        loginView.setVisible(true);
    }

    public void configurarInterfaz(String rol) {
        loginView.dispose();
        switch (rol) {
            case "Administrador":
                ventanaAdministrador.setVisible(true);
                break;
            case "Doctor":
                ventanaDoctor.setVisible(true);
                break;
            default:
                ventanaTrabajador.setVisible(true);
                break;
        }
    }

    public static void main(String[] args) {
        new CtrlPrincipal();
    }
}
