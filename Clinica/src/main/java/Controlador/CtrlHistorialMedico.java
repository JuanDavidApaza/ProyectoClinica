package Controlador;

import Modelo.DAO.HistorialMedicoDAO;
import Modelo.HistorialMedico;
import Modelo.Paciente;
import Vista.HistorialMedicoJDialog;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import java.util.List;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class CtrlHistorialMedico {
    private HistorialMedicoJDialog view;
    private HistorialMedicoDAO dao;
    private Paciente paciente;

    public CtrlHistorialMedico(HistorialMedicoJDialog view, HistorialMedicoDAO dao, Paciente paciente) {
        this.view = view;
        this.dao = dao;
        this.paciente = paciente;
        this.view.btnGenerarPdf.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Generate PDF logic
            }
        });
        this.view.btnDetalles.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mostrarDetallesCita();
            }
        });
        this.view.tablaHistorialMedico.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting() && view.tablaHistorialMedico.getSelectedRow() != -1) {
                    view.btnDetalles.setEnabled(true);
                } else {
                    view.btnDetalles.setEnabled(false);
                }
            }
        });
    }

    public void init() {
        List<HistorialMedico> historialList = dao.listarHistorialMedico(paciente.getIdPaciente());
        DefaultTableModel model = (DefaultTableModel) view.tablaHistorialMedico.getModel();
        model.setRowCount(0);
        for (HistorialMedico historial : historialList) {
            model.addRow(new Object[]{
                historial.getNumeroHistorial(),
                historial.getFecha(),
                historial.getDiagnostico(),
                historial.getNombreDoctor()
            });
        }

        view.etiquetaNombrePaciente.setText("Paciente: " + paciente.getNombre() + " " + paciente.getApellido());
        view.etiquetaDniPaciente.setText("DNI: " + paciente.getDni());
    }

    private void mostrarDetallesCita() {
        int selectedRow = view.tablaHistorialMedico.getSelectedRow();
        if (selectedRow != -1) {
            int numeroHistorial = (int) view.tablaHistorialMedico.getValueAt(selectedRow, 0);
            HistorialMedico historial = dao.obtenerHistorialMedicoConDetalles(numeroHistorial);
            if (historial != null) {
                String detalles = "Fecha: " + historial.getFecha() + "\n"
                                + "Turno: " + historial.getTurno() + "\n"
                                + "Detalle de la Cita: " + historial.getDetalleCita() + "\n"
                                + "Diagnostico: " + historial.getDiagnostico() + "\n"
                                + "Doctor: " + historial.getNombreDoctor();
                JOptionPane.showMessageDialog(view, detalles, "Detalles de la Cita", JOptionPane.INFORMATION_MESSAGE);
            }
        }
    }
}
