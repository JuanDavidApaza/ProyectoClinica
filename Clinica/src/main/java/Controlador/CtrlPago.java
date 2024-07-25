package Controlador;

import Conexion.Conexion;
import Modelo.Cita;
import Modelo.DAO.CitaDAO;
import Modelo.DAO.EmailSender;
import Modelo.DAO.PacienteDAO;
import Modelo.DAO.PagoDAO;
import Modelo.Paciente;
import Modelo.Pago;
import Vista.VentanaPago;
import Vista.VentanaTrabajador;

import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Date;

public class CtrlPago {

    private VentanaPago vista;
    private CitaDAO citaDAO;
    private PagoDAO pagoDAO;
    private Connection conn;

    public CtrlPago(VentanaPago vista) {
        this.vista = vista;
        this.citaDAO = new CitaDAO();
        this.pagoDAO = new PagoDAO();

        this.vista.btnBuscarCitaXDNI.addActionListener(e -> buscarCitaPorDNI());
        this.vista.btnRefrescarTablaCitas.addActionListener(e -> refrescarTablaCitas());
        this.vista.btnEliminarCita.addActionListener(e -> eliminarCita());
        this.vista.btnPagarCita.addActionListener(e -> habilitarOpcionesPago(true));
        this.vista.btnCancelarPago.addActionListener(e -> cancelarPago());
        this.vista.btnConfirmarPago.addActionListener(e -> confirmarPago());
        this.vista.radioBtnPagaconTarjeta.addActionListener(e -> habilitarOpcionesPago(true));
        this.vista.radioBtnPagoEfectivo.addActionListener(e -> habilitarOpcionesPago(false));
        this.vista.btnIraPaciente.addActionListener(e-> irAPaciente());
        inicializarVista();
    }
    private void irAPaciente(){
        vista.dispose();
        VentanaTrabajador ventanaTrabajador = new VentanaTrabajador();
        PacienteDAO pacienteDAO = new PacienteDAO();
        new CtrlTrabajador(ventanaTrabajador, pacienteDAO);
        ventanaTrabajador.setVisible(true);
    }
    private void inicializarVista() {
        vista.setVisible(true);
        vista.etiquetaimgQRyape.setVisible(false);
        vista.imgCard.setVisible(false);
        vista.cajaNumeroTarjeta.setVisible(false);
        vista.cajaCvv.setVisible(false);
        vista.cajaFechaVenc.setVisible(false);
        vista.btnConfirmarPago.setEnabled(false);
        refrescarTablaCitas();
        
        vista.radioBtnPagaconTarjeta.setSelected(false);
        vista.radioBtnPagoEfectivo.setSelected(false);
        vista.radioBtnPagaconTarjeta.setEnabled(false);
        vista.radioBtnPagoEfectivo.setEnabled(false);
    }

    private void buscarCitaPorDNI() {
        String dni = vista.cajaDNI.getText().trim();
        if (dni.isEmpty()) {
            JOptionPane.showMessageDialog(vista, "Por favor, ingrese un DNI.");
            return;
        }

        try {
            conn = Conexion.getConnection();
            citaDAO.setConnection(conn);
            DefaultTableModel model = (DefaultTableModel) vista.tablaCitasPendientes.getModel();
            model.setRowCount(0);
            for (Cita cita : citaDAO.obtenerCitasPorDNI(dni)) {
                model.addRow(new Object[]{cita.getIdCita(), cita.getFecha(), cita.getTurno(), cita.getNombreDoctor()});
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(vista, "Error al buscar citas por DNI.");
        } finally {
            Conexion.close(conn);
        }
    }

    private void refrescarTablaCitas() {
        try {
            conn = Conexion.getConnection();
            citaDAO.setConnection(conn);
            DefaultTableModel model = (DefaultTableModel) vista.tablaCitasPendientes.getModel();
            model.setRowCount(0);
            for (Cita cita : citaDAO.obtenerCitasPendientes()) {
                model.addRow(new Object[]{cita.getIdCita(), cita.getFecha(), cita.getTurno(), cita.getNombreDoctor()});
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(vista, "Error al refrescar la tabla de citas.");
        } finally {
            Conexion.close(conn);
        }
    }

    private void eliminarCita() {
    int filaSeleccionada = vista.tablaCitasPendientes.getSelectedRow();
    if (filaSeleccionada == -1) {
        JOptionPane.showMessageDialog(vista, "Por favor, seleccione una cita de la tabla.");
        return;
    }

    int respuesta = JOptionPane.showConfirmDialog(vista, "¿Está seguro de que desea eliminar esta cita?", "Confirmar Eliminación", JOptionPane.YES_NO_OPTION);
    if (respuesta == JOptionPane.YES_OPTION) {
        int idCita = (int) vista.tablaCitasPendientes.getValueAt(filaSeleccionada, 0);

        int intentos = 3; // Número máximo de intentos
        SQLException excepcion = null;

        for (int i = 0; i < intentos; i++) {
            try {
                conn = Conexion.getConnection();
                citaDAO.setConnection(conn);
                if (citaDAO.eliminarCita(idCita)) {
                    JOptionPane.showMessageDialog(vista, "Cita eliminada correctamente.");
                    
                    return;
                } else {
                    JOptionPane.showMessageDialog(vista, "Error al eliminar la cita.");
                }
            } catch (SQLException ex) {
                excepcion = ex;
                // Si es el último intento, mostrar el mensaje de error
                if (i == intentos - 1) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(vista, "Error al eliminar la cita.");
                } else {
                    // Esperar un momento antes de intentar nuevamente
                    try {
                        Thread.sleep(1000); // Esperar 1 segundo
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            } finally {
                Conexion.close(conn);
            }
        }
    }
}


    private void habilitarOpcionesPago(boolean habilitarTarjeta) {
        vista.radioBtnPagaconTarjeta.setEnabled(true);
        vista.radioBtnPagoEfectivo.setEnabled(true);
        
        vista.cajaNumeroTarjeta.setVisible(habilitarTarjeta);
        vista.cajaCvv.setVisible(habilitarTarjeta);
        vista.cajaFechaVenc.setVisible(habilitarTarjeta);
        vista.imgCard.setVisible(habilitarTarjeta);
        vista.etiquetaimgQRyape.setVisible(!habilitarTarjeta);
        vista.btnConfirmarPago.setEnabled(true);
        bloquearComponentesParaPago();   
        
    }

    private void cancelarPago() {
        desbloquearComponentes();
        vista.radioBtnPagaconTarjeta.setSelected(false);
        vista.radioBtnPagoEfectivo.setSelected(false);
        vista.radioBtnPagaconTarjeta.setEnabled(false);
        vista.radioBtnPagoEfectivo.setEnabled(false);
        vista.cajaNumeroTarjeta.setText("");
        vista.cajaCvv.setText("");
        vista.cajaFechaVenc.setText("");
        vista.imgCard.setVisible(false);
        vista.etiquetaimgQRyape.setVisible(false);
        vista.cajaNumeroTarjeta.setVisible(false);
        vista.cajaCvv.setVisible(false);
        vista.cajaFechaVenc.setVisible(false);
        vista.btnConfirmarPago.setEnabled(false);
    }

    private void confirmarPago() {
        int filaSeleccionada = vista.tablaCitasPendientes.getSelectedRow();
        if (filaSeleccionada == -1) {
            JOptionPane.showMessageDialog(vista, "Por favor, seleccione una cita de la tabla.");
            return;
        }

        int respuesta = JOptionPane.showConfirmDialog(vista, "¿Está seguro de que desea confirmar el pago?", "Confirmar Pago", JOptionPane.YES_NO_OPTION);
        if (respuesta == JOptionPane.YES_OPTION) {
            int idCita = (int) vista.tablaCitasPendientes.getValueAt(filaSeleccionada, 0);

            try {
                conn = Conexion.getConnection();
                pagoDAO.setConnection(conn);
                citaDAO.setConnection(conn);
                Pago pago = new Pago();
                pago.setIdCita(idCita);
                pago.setMonto(10.00); //monto fijo de 10 soles
                pago.setFechaPago(new Date());
                
                if (vista.radioBtnPagaconTarjeta.isSelected()) {
                if (vista.cajaNumeroTarjeta.getText().trim().isEmpty() ||
                        vista.cajaCvv.getText().trim().isEmpty() ||
                        vista.cajaFechaVenc.getText().trim().isEmpty()) {
                    JOptionPane.showMessageDialog(vista, "Por favor, complete todos los campos de la tarjeta.");
                    return;
                }
            }
                if (pagoDAO.insertarPago(pago)) {
                    citaDAO.actualizarEstadoCita(idCita, "Pagada");

                    // Obtener información de la cita y del paciente
                    PacienteDAO pacienteDAO = new PacienteDAO();
                    Cita cita = citaDAO.obtenerCitaPorId(idCita);
                    if (cita == null) {
                        JOptionPane.showMessageDialog(vista, "Error al obtener la información de la cita.");
                        return;
                    }
                    Paciente paciente = pacienteDAO.obtenerPacientePorId(cita.getIdPaciente());

                    // Enviar correo electrónico de confirmación de pago
                    EmailSender.enviarEmailPago(cita, pago, paciente);

                    JOptionPane.showMessageDialog(vista, "Pago confirmado correctamente y email enviado.");
                    cancelarPago();
                    
                    refrescarTablaCitas();
                } else {
                    JOptionPane.showMessageDialog(vista, "Error al confirmar el pago.");
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(vista, "Error al confirmar el pago.");
            } finally {
                Conexion.close(conn);
            }
        }
    }

    private void bloquearComponentesParaPago() {
        vista.tablaCitasPendientes.setEnabled(false);
        vista.btnBuscarCitaXDNI.setEnabled(false);
        vista.btnRefrescarTablaCitas.setEnabled(false);
        vista.btnEliminarCita.setEnabled(false);
        vista.btnPagarCita.setEnabled(false);
        vista.btnCancelarPago.setEnabled(true);
    }

    private void desbloquearComponentes() {
        vista.tablaCitasPendientes.setEnabled(true);
        vista.btnBuscarCitaXDNI.setEnabled(true);
        vista.btnRefrescarTablaCitas.setEnabled(true);
        vista.btnEliminarCita.setEnabled(true);
        vista.btnPagarCita.setEnabled(true);
        vista.btnCancelarPago.setEnabled(false);
    }
}
