package Controlador;

import Modelo.DAO.PacienteDAO;
import Modelo.Paciente;
import Vista.ModificarPacienteJDialog;
import javax.swing.JOptionPane;

public class CtrlModificarPaciente {

    private final ModificarPacienteJDialog modificarPacienteJdialog;
    private final PacienteDAO pacienteDAO;
    private final String dni;

    public CtrlModificarPaciente(ModificarPacienteJDialog modificarPacienteJdialog, PacienteDAO pacienteDAO, String dni) {
        this.modificarPacienteJdialog = modificarPacienteJdialog;
        this.pacienteDAO = pacienteDAO;
        this.dni = dni;

        cargarDatosPaciente();

        this.modificarPacienteJdialog.btnEditar.addActionListener(e -> editarDatosPaciente());
        this.modificarPacienteJdialog.btnCancelar.addActionListener(e -> cancelarEdicion());
    }

    private void cargarDatosPaciente() {
        Paciente paciente = pacienteDAO.obtenerPacientePorDNI(dni);
        if (paciente != null) {
            modificarPacienteJdialog.cajaNombre.setText(paciente.getNombre());
            modificarPacienteJdialog.cajaApellidos.setText(paciente.getApellido());
            modificarPacienteJdialog.cajaDireccion.setText(paciente.getDireccion());
            modificarPacienteJdialog.cajaTelefono.setText(paciente.getTelefono());
            modificarPacienteJdialog.cajaEmail.setText(paciente.getEmail());
            modificarPacienteJdialog.cajaSexo.setText(paciente.getSexo());
            modificarPacienteJdialog.cajaEdad.setText(String.valueOf(paciente.getEdad()));
        } else {
            JOptionPane.showMessageDialog(null, "Paciente no encontrado");
            modificarPacienteJdialog.dispose();
        }
    }

    private void editarDatosPaciente() {
        Paciente paciente = new Paciente(
            dni,
            modificarPacienteJdialog.cajaNombre.getText(),
            modificarPacienteJdialog.cajaApellidos.getText(),
            modificarPacienteJdialog.cajaDireccion.getText(),
            modificarPacienteJdialog.cajaTelefono.getText(),
            modificarPacienteJdialog.cajaEmail.getText(),
            modificarPacienteJdialog.cajaSexo.getText(),
            Integer.parseInt(modificarPacienteJdialog.cajaEdad.getText()),
            0,  //no se usa
            "Modificado" //placeholder
        );

        if (pacienteDAO.actualizarPaciente(paciente)) {
            JOptionPane.showMessageDialog(null, "Datos del paciente actualizados");
            modificarPacienteJdialog.dispose();
        } else {
            JOptionPane.showMessageDialog(null, "Error al actualizar los datos del paciente");
        }
    }

    private void cancelarEdicion() {
        modificarPacienteJdialog.dispose();
    }
}
