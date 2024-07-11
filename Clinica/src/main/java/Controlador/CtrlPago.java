package Controlador;

import Modelo.Cita;
import Modelo.Pago;
import Vista.VentanaPago;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class CtrlPago {

    private VentanaPago vista;


    public CtrlPago(VentanaPago vista) {
        this.vista = vista;
        this.vista.btnBuscarCitaXDNI.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                buscarCitaPorDNI();
            }
        });
        this.vista.btnRefrescarTablaCitas.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                refrescarTablaCitas();
            }
        });
        this.vista.btnEliminarCita.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                eliminarCita();
            }
        });
        this.vista.btnPagarCita.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                habilitarOpcionesPago();
            }
        });
        this.vista.btnCancelarPago.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cancelarPago();
            }
        });
        this.vista.btnConfirmarPago.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                confirmarPago();
            }
        });
        inicializarVista();
    }

    private void inicializarVista() {
        vista.setVisible(true);
        vista.etiquetaimgQRyape.setVisible(false);
        vista.imgCard.setVisible(false);
        vista.cajaNumeroTarjeta.setVisible(false);
        vista.cajaCvv.setVisible(false);
        vista.cajaFechaVenc.setVisible(false);
        vista.btnConfirmarPago.setEnabled(false);
    }

    private void buscarCitaPorDNI() {
        String dni = vista.cajaDNI.getText().trim();
        if (dni.isEmpty()) {
            JOptionPane.showMessageDialog(vista, "Por favor, ingrese un DNI.");
            return;
        }
        // Lógica para buscar citas por DNI y llenar la tabla
        
    }

    private void refrescarTablaCitas() {
        // Lógica para refrescar la tabla de citas
     
    }

    private void eliminarCita() {
        int filaSeleccionada = vista.tablaCitasPendientes.getSelectedRow();
        if (filaSeleccionada == -1) {
            JOptionPane.showMessageDialog(vista, "Por favor, seleccione una cita de la tabla.");
            return;
        }
        int respuesta = JOptionPane.showConfirmDialog(vista, "¿Está seguro de que desea eliminar esta cita?", "Confirmar Eliminación", JOptionPane.YES_NO_OPTION);
        if (respuesta == JOptionPane.YES_OPTION) {
            // Lógica para eliminar la cita seleccionada
           
        }
    }

    private void habilitarOpcionesPago() {
        int filaSeleccionada = vista.tablaCitasPendientes.getSelectedRow();
        if (filaSeleccionada == -1) {
            JOptionPane.showMessageDialog(vista, "Por favor, seleccione una cita de la tabla.");
            return;
        }
        // Habilitar las opciones de pago
        vista.radioBtnPagaconTarjeta.setEnabled(true);
        vista.radioBtnPagoEfectivo.setEnabled(true);
        vista.btnConfirmarPago.setEnabled(true);
    }

    private void cancelarPago() {
        // Deshabilitar las opciones de pago y limpiar los campos
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
        if (vista.radioBtnPagaconTarjeta.isSelected()) {
            // Lógica para pago con tarjeta
            String numeroTarjeta = vista.cajaNumeroTarjeta.getText().trim();
            String cvv = vista.cajaCvv.getText().trim();
            String fechaVenc = vista.cajaFechaVenc.getText().trim();
            if (numeroTarjeta.isEmpty() || cvv.isEmpty() || fechaVenc.isEmpty()) {
                JOptionPane.showMessageDialog(vista, "Por favor, complete todos los campos de la tarjeta.");
                return;
            }
      
        } else if (vista.radioBtnPagoEfectivo.isSelected()) {
           
            vista.etiquetaimgQRyape.setVisible(true);
            int respuesta = JOptionPane.showConfirmDialog(vista, "¿El cliente ha realizado el pago?", "Confirmar Pago", JOptionPane.YES_NO_OPTION);
            if (respuesta == JOptionPane.YES_OPTION) {
                // Implementa la lógica de confirmación de pago aquí
            }
        } else {
            JOptionPane.showMessageDialog(vista, "Por favor, seleccione un método de pago.");
        }
    }
}
