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
       
        //bloquear cajas
        modificarPacienteJdialog.cajaNombre.setEnabled(false);
        modificarPacienteJdialog.cajaApellidos.setEnabled(false);
        modificarPacienteJdialog.cajaSexo.setEnabled(false);
        

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
        String nombre = modificarPacienteJdialog.cajaNombre.getText();
        String apellido = modificarPacienteJdialog.cajaApellidos.getText();
        String direccion = modificarPacienteJdialog.cajaDireccion.getText();
        String telefono = modificarPacienteJdialog.cajaTelefono.getText();
        String email = modificarPacienteJdialog.cajaEmail.getText();
        String sexo = modificarPacienteJdialog.cajaSexo.getText();
        String edadStr = modificarPacienteJdialog.cajaEdad.getText();

        // Validar si algún campo obligatorio está vacío
        if (nombre.isEmpty() || apellido.isEmpty() || direccion.isEmpty() ||
                telefono.isEmpty() || email.isEmpty() || sexo.isEmpty() || edadStr.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Todos los campos son obligatorios");
            return;
        }

        // Validar formato de edad
        int edad;
        try {
            edad = Integer.parseInt(edadStr);
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(null, "Ingrese una edad válida");
            return;
        }

        
        Paciente paciente = new Paciente(
                dni,  // Mantener el DNI original
                nombre,
                apellido,
                direccion,
                telefono,
                email,
                sexo,
                edad,
                0,  // No se utiliza
                "Modificado"
        );

        // Actualizar el paciente en la base de datos
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


