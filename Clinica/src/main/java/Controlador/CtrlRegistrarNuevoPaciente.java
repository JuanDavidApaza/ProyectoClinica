package Controlador;

import Modelo.DAO.PacienteDAO;
import Modelo.Paciente;
import Vista.RegistrarNuevoPacienteJdialog;
import javax.swing.JOptionPane;

public class CtrlRegistrarNuevoPaciente {

    private final RegistrarNuevoPacienteJdialog registrarNuevoPacienteJdialog;
    private final PacienteDAO pacienteDAO;

    public CtrlRegistrarNuevoPaciente(RegistrarNuevoPacienteJdialog registrarNuevoPacienteJdialog, PacienteDAO pacienteDAO) {
        this.registrarNuevoPacienteJdialog = registrarNuevoPacienteJdialog;
        this.pacienteDAO = pacienteDAO;

        this.registrarNuevoPacienteJdialog.btnRegistrar.addActionListener(e -> registrarNuevoPaciente());
        this.registrarNuevoPacienteJdialog.btnCancelar.addActionListener(e -> cancelarRegistro());
    }

    private void registrarNuevoPaciente() {
        String dni = registrarNuevoPacienteJdialog.cajaDNI.getText().trim(); // Trim para eliminar espacios en blanco
        String nombre = registrarNuevoPacienteJdialog.cajaNombre.getText();
        String apellido = registrarNuevoPacienteJdialog.cajaApellidos.getText();
        String direccion = registrarNuevoPacienteJdialog.cajaDireccion.getText();
        String telefono = registrarNuevoPacienteJdialog.cajaTelefono.getText();
        String email = registrarNuevoPacienteJdialog.cajaEmail.getText();
        String sexo = registrarNuevoPacienteJdialog.cajaSexo.getText();
        String edadStr = registrarNuevoPacienteJdialog.cajaEdad.getText();

        // Validaciones
        if (dni.isEmpty() || nombre.isEmpty() || apellido.isEmpty() || direccion.isEmpty() ||
                telefono.isEmpty() || email.isEmpty() || sexo.isEmpty() || edadStr.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Todos los campos son obligatorios");
            return;
        }

        // edad
        int edad;
        try {
            edad = Integer.parseInt(edadStr);
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(null, "Ingrese una edad válida");
            return;
        }

        // Validar si el DNI ya existe
        if (pacienteDAO.existeDNI(dni)) {
            JOptionPane.showMessageDialog(null, "El DNI ingresado ya existe");
            return;
        }

        // Crear el objeto
        Paciente paciente = new Paciente(dni, nombre, apellido, direccion, telefono, email, sexo, edad, 0, "Registrado");

        // Insertar el paciente en la base de datos
        if (pacienteDAO.insertarPaciente(paciente)) {
            JOptionPane.showMessageDialog(null, "Paciente registrado exitosamente");
            registrarNuevoPacienteJdialog.dispose();
        } else {
            JOptionPane.showMessageDialog(null, "Error al registrar el paciente");
        }
    }

    private void cancelarRegistro() {
        registrarNuevoPacienteJdialog.dispose();
    }
}
