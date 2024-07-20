package Controlador;

import Conexion.Conexion;
import Modelo.DAO.HistorialMedicoDAO;
import Modelo.HistorialMedico;
import Modelo.Paciente;
import Vista.HistorialMedicoJDialog;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import static javax.swing.WindowConstants.DISPOSE_ON_CLOSE;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.design.JRDesignQuery;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;

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
                try {
                    Conexion con = new Conexion();
                    
//           

                    Connection conn = con.getConnection();
                    JasperDesign jdesign = JRXmlLoader.load("C:\\Users\\51934\\Documents\\GitHub\\ProyectoClinica\\Clinica\\src\\main\\java\\Modelo\\MedicoHistorialReport.jrxml");
                    String query = "SELECT \n"
                            + "    ROW_NUMBER() OVER (ORDER BY hm.NumeroHistorial) AS Orden, \n"
                            + "    hm.NumeroHistorial, \n"
                            + "    hm.Fecha, \n"
                            + "    hm.Diagnostico, \n"
                            + "    CONCAT(d.Nombre, ' ', d.Apellido) AS NombreDoctor, \n"
                            + "    CONCAT(p.Nombre, ' ', p.Apellido) AS NombrePaciente\n"
                            + "FROM \n"
                            + "    historialmedico hm \n"
                            + "JOIN \n"
                            + "    doctor doc ON hm.IDDoctor_fk = doc.IDDoctor\n"
                            + "JOIN \n"
                            + "    persona d ON doc.DNI_fk = d.DNI\n"
                            + "JOIN \n"
                            + "    paciente pa ON hm.IDPaciente_fk = pa.IDPaciente\n"
                            + "JOIN \n"
                            + "    persona p ON pa.DNI_fk = p.DNI\n"
                            + "WHERE \n"
                            + "    hm.IDPaciente_fk ="+paciente.getIdPaciente()+"\n"
                            + "ORDER BY \n"
                            + "    hm.NumeroHistorial;\n"
                            + "";
                    JRDesignQuery updateQuery = new JRDesignQuery();
                    updateQuery.setText(query);

                    jdesign.setQuery(updateQuery);

                    JasperReport jreport = JasperCompileManager.compileReport(jdesign);
                    JasperPrint jprint = JasperFillManager.fillReport(jreport, null, conn);

                    // JasperViewer.viewReport(jprint);
                    JasperViewer viewer = new JasperViewer(jprint, false);
                    viewer.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
                    viewer.setVisible(true);
                    viewer.toFront();

                    Conexion.close(conn);

                } catch (SQLException ex) {
                    Logger.getLogger(CtrlVentanaDoctor.class.getName()).log(Level.SEVERE, null, ex);
                } catch (JRException ex) {
                    Logger.getLogger(CtrlVentanaDoctor.class.getName()).log(Level.SEVERE, null, ex);
                }
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
