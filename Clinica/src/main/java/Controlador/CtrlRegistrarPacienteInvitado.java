package Controlador;

import Modelo.DAO.PacienteDAO;
import Vista.RegistrarPacienteInvitadoJdialog;

public class CtrlRegistrarPacienteInvitado {

    private final RegistrarPacienteInvitadoJdialog registrarPacienteInvitadoJdialog;
    private final PacienteDAO pacienteDAO;

    public CtrlRegistrarPacienteInvitado(RegistrarPacienteInvitadoJdialog registrarPacienteInvitadoJdialog, PacienteDAO pacienteDAO) {
        this.registrarPacienteInvitadoJdialog = registrarPacienteInvitadoJdialog;
        this.pacienteDAO = pacienteDAO;

        this.registrarPacienteInvitadoJdialog.btnRegistrar.addActionListener(e -> registrarPacienteInvitado());
        this.registrarPacienteInvitadoJdialog.btnCancelar.addActionListener(e -> cancelarRegistro());
    }

    private void registrarPacienteInvitado() {
        // Lógica
    }

    private void cancelarRegistro() {
        registrarPacienteInvitadoJdialog.dispose();
    }
}
