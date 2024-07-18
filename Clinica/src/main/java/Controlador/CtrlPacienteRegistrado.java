package Controlador;

import Modelo.DAO.CitaDAO;
import Modelo.DAO.DoctorDAO;
import Modelo.DAO.HistorialMedicoDAO;
import Modelo.DAO.HorarioDoctorDAO;
import Modelo.DAO.PacienteDAO;
import Modelo.Paciente;
import Vista.HistorialMedicoJDialog;
import Vista.ModificarPacienteJDialog;
import Vista.VentanaPacienteRegistrado;
import Vista.VentanaPago;
import Vista.VentanaRegistroCitaJDialog;
import Vista.VentanaTrabajador;

import javax.swing.JOptionPane;

public class CtrlPacienteRegistrado {

    private final VentanaPacienteRegistrado ventanaPacienteRegistrado;
    private final PacienteDAO pacienteDAO;
    private final CitaDAO citaDAO;
    private final HorarioDoctorDAO horarioDoctorDAO;
    private final VentanaTrabajador ventanaTrabajador;
    
    //pago
    private final VentanaPago ventanaPago;

    public CtrlPacienteRegistrado(VentanaPacienteRegistrado ventanaPacienteRegistrado, PacienteDAO pacienteDAO, CitaDAO citaDAO, HorarioDoctorDAO horarioDoctorDAO, VentanaTrabajador ventanaTrabajador, VentanaPago ventanaPago) {
        this.ventanaPacienteRegistrado = ventanaPacienteRegistrado;
        this.pacienteDAO = pacienteDAO;
        this.citaDAO = citaDAO;
        this.horarioDoctorDAO = horarioDoctorDAO;
        this.ventanaTrabajador = ventanaTrabajador;

        this.ventanaPago = ventanaPago;
        
        this.ventanaPacienteRegistrado.setVisible(true);
        this.ventanaPacienteRegistrado.setLocationRelativeTo(null);
        
        

        this.ventanaPacienteRegistrado.btnBuscarPacienteDNI.addActionListener(e -> buscarPacientePorDNI());
        this.ventanaPacienteRegistrado.btnHistorialMedico.addActionListener(e -> mostrarHistorialMedico());
        this.ventanaPacienteRegistrado.btnModificarDatos.addActionListener(e -> modificarDatosPaciente());
        this.ventanaPacienteRegistrado.btnRegistrarCita.addActionListener(e -> registrarCita());
        this.ventanaPacienteRegistrado.btnIraPaciente.addActionListener(e -> regresarVentanaTrabajador());
        this.ventanaPacienteRegistrado.btnIraPago.addActionListener(e -> irAPago());

        // Deshabilitar botones al inicio
        deshabilitarBotonesOperaciones();
    }

    private void buscarPacientePorDNI() {
        String dni = ventanaPacienteRegistrado.cajaDni.getText();
        if (!dni.isEmpty()) {
            Paciente paciente = pacienteDAO.obtenerPacienteRegistrado(dni);
            if (paciente != null) {
                ventanaPacienteRegistrado.etiquetaNombrePaciente.setText("Paciente: "+paciente.getNombre() + " " + paciente.getApellido());
                habilitarBotonesOperaciones();
            } else {
                JOptionPane.showMessageDialog(null, "Paciente no encontrado");
                limpiarDatosPaciente();
            }
        } else {
            JOptionPane.showMessageDialog(null, "Ingrese un DNI válido");
        }
    }

    private void habilitarBotonesOperaciones() {
        ventanaPacienteRegistrado.btnHistorialMedico.setEnabled(true);
        ventanaPacienteRegistrado.btnModificarDatos.setEnabled(true);
        ventanaPacienteRegistrado.btnRegistrarCita.setEnabled(true);
    }

    private void deshabilitarBotonesOperaciones() {
        ventanaPacienteRegistrado.btnHistorialMedico.setEnabled(false);
        ventanaPacienteRegistrado.btnModificarDatos.setEnabled(false);
        ventanaPacienteRegistrado.btnRegistrarCita.setEnabled(false);
    }

    private void limpiarDatosPaciente() {
        ventanaPacienteRegistrado.etiquetaNombrePaciente.setText("");
        deshabilitarBotonesOperaciones();
    }

    private void mostrarHistorialMedico() {
        String dni = ventanaPacienteRegistrado.cajaDni.getText();
        if (!dni.isEmpty()) {
            Paciente paciente = pacienteDAO.obtenerPacienteRegistrado(dni);
            if (paciente != null) {
                HistorialMedicoJDialog historialDialog = new HistorialMedicoJDialog(null, true);
                HistorialMedicoDAO historialDAO = new HistorialMedicoDAO();
                CtrlHistorialMedico ctrlHistorial = new CtrlHistorialMedico(historialDialog, historialDAO, paciente);
                ctrlHistorial.init();
                historialDialog.setVisible(true);
            } else {
                JOptionPane.showMessageDialog(null, "Paciente no encontrado");
            }
        } else {
            JOptionPane.showMessageDialog(null, "Ingrese un DNI válido");
        }
    }

    private void modificarDatosPaciente() {
        String dni = ventanaPacienteRegistrado.cajaDni.getText();
        ModificarPacienteJDialog modificarPacienteJDialog = new ModificarPacienteJDialog(null, true);
        new CtrlModificarPaciente(modificarPacienteJDialog, pacienteDAO, dni);
        modificarPacienteJDialog.setVisible(true);
    }

    private void registrarCita() {
        String dni = ventanaPacienteRegistrado.cajaDni.getText();
        Paciente paciente = pacienteDAO.obtenerPacienteRegistrado(dni);
        if (paciente != null) {
            VentanaRegistroCitaJDialog ventanaRegistroCitaJDialog = new VentanaRegistroCitaJDialog(null, true);
            DoctorDAO doctorDAO = new DoctorDAO();
            new CtrlRegistrarCita(ventanaRegistroCitaJDialog, citaDAO, pacienteDAO, horarioDoctorDAO, paciente,doctorDAO);
            ventanaRegistroCitaJDialog.setVisible(true);
        } else {
            JOptionPane.showMessageDialog(null, "Paciente no encontrado");
        }
    }

    private void regresarVentanaTrabajador() {
        ventanaPacienteRegistrado.dispose();
        ventanaTrabajador.setVisible(true);
    }
    
    private void irAPago(){
        ventanaPacienteRegistrado.dispose();
        new CtrlPago(ventanaPago);
        ventanaPago.setVisible(true);
    }
}
