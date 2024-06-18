
package Controlador;

import Modelo.DAO.PacienteDAO;
import Vista.*;

public class CtrlTrabajador {

    private final VentanaTrabajador ventanaTrabajador;
    private final PacienteDAO pacienteDAO;

    public CtrlTrabajador(VentanaTrabajador ventanaTrabajador, PacienteDAO pacienteDAO) {
        this.ventanaTrabajador = ventanaTrabajador;
        this.pacienteDAO = pacienteDAO;

        this.ventanaTrabajador.setVisible(true);
        this.ventanaTrabajador.setLocationRelativeTo(null);

        // Asignar listeners a los botones
        this.ventanaTrabajador.btnPacienteReg.addActionListener(e -> mostrarVentanaPacienteRegistrado());
        this.ventanaTrabajador.btnPacienteInvit.addActionListener(e -> mostrarVentanaRegistrarPacienteInvitado());
        this.ventanaTrabajador.btnNuevoPaciente.addActionListener(e -> mostrarVentanaRegistrarNuevoPaciente());
    }

    private void mostrarVentanaPacienteRegistrado() {
        ventanaTrabajador.dispose(); // Cierra la ventana de trabajador
        VentanaPacienteRegistrado ventanaPacienteRegistrado = new VentanaPacienteRegistrado();
        new CtrlPacienteRegistrado(ventanaPacienteRegistrado, pacienteDAO, ventanaTrabajador);
        ventanaPacienteRegistrado.setVisible(true);
    }

    private void mostrarVentanaRegistrarPacienteInvitado() {
        
        RegistrarPacienteInvitadoJdialog registrarPacienteInvitadoJDialog = new RegistrarPacienteInvitadoJdialog(ventanaTrabajador, true);
        new CtrlRegistrarPacienteInvitado(registrarPacienteInvitadoJDialog, pacienteDAO);
        registrarPacienteInvitadoJDialog.setVisible(true);
    }

    private void mostrarVentanaRegistrarNuevoPaciente() {
        
        RegistrarNuevoPacienteJdialog registrarNuevoPacienteJDialog = new RegistrarNuevoPacienteJdialog(ventanaTrabajador, true);
        new CtrlRegistrarNuevoPaciente(registrarNuevoPacienteJDialog, pacienteDAO);
        registrarNuevoPacienteJDialog.setVisible(true);
    }
}

