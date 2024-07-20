//package Controlador;
//
//import Modelo.DAO.CitaDAO;
//import Modelo.Cita;
//import Modelo.DAO.EmailSender;
//import Vista.VentanaDoctor;
//import javax.swing.*;
//import javax.swing.table.DefaultTableModel;
//import java.awt.event.ActionEvent;
//import java.awt.event.ActionListener;
//import java.util.List;
//
//public class CtrlVentanaDoctor {
//    private VentanaDoctor ventanaDoctor;
//    private int idDoctor;
//    private CitaDAO citaDAO;
//
//    public CtrlVentanaDoctor(VentanaDoctor ventanaDoctor, int idDoctor) {
//        this.ventanaDoctor = ventanaDoctor;
//        this.idDoctor = idDoctor;
//        this.citaDAO = new CitaDAO();
//
//        cargarCitas();
//        initEventHandlers();
//    }
//
//    private void cargarCitas() {
//        List<Cita> citas = citaDAO.obtenerCitasPorDoctor(idDoctor);
//        DefaultTableModel model = (DefaultTableModel) ventanaDoctor.tablaCitas.getModel();
//        model.setRowCount(0); // Limpiar la tabla
//
//        for (Cita cita : citas) {
//            Object[] row = new Object[]{
//                cita.getIdCita(),
//                cita.getNombrePaciente(),
//                cita.getDniPaciente(),
//                cita.getDetalle(),
//                cita.getTurno(),
//                cita.getNumTurno()
//            };
//            model.addRow(row);
//        }
//    }
//
//    private void cargarCitasPorDni(String dni) {
//        List<Cita> citas = citaDAO.obtenerCitasPorDni(dni, idDoctor);
//        DefaultTableModel model = (DefaultTableModel) ventanaDoctor.tablaCitas.getModel();
//        model.setRowCount(0); // Limpiar la tabla
//
//        for (Cita cita : citas) {
//            Object[] row = new Object[]{
//                cita.getIdCita(),
//                cita.getNombrePaciente(),
//                cita.getDniPaciente(),
//                cita.getDetalle(),
//                cita.getTurno(),
//                cita.getNumTurno()
//            };
//            model.addRow(row);
//        }
//    }
//
//    private void initEventHandlers() {
//        ventanaDoctor.btnBuscarCitaDni.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                String dni = ventanaDoctor.cajaDni.getText();
//                if (!dni.isEmpty()) {
//                    cargarCitasPorDni(dni);
//                } else {
//                    cargarCitas();
//                }
//            }
//        });
//
//        ventanaDoctor.btnVerDetalles.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                int selectedRow = ventanaDoctor.tablaCitas.getSelectedRow();
//                if (selectedRow != -1) {
//                    String detalle = (String) ventanaDoctor.tablaCitas.getValueAt(selectedRow, 3);
//                    String fecha = ventanaDoctor.tablaCitas.getValueAt(selectedRow, 4).toString();
//                    String nombrePaciente = (String) ventanaDoctor.tablaCitas.getValueAt(selectedRow, 1);
//
//                    JOptionPane.showMessageDialog(ventanaDoctor,
//                            "Detalle: " + detalle + "\nTurno: " + fecha + "\nPaciente: " + nombrePaciente,
//                            "Detalles de la Cita",
//                            JOptionPane.INFORMATION_MESSAGE);
//                }
//            }
//        });
//
//        ventanaDoctor.tablaCitas.getSelectionModel().addListSelectionListener(e -> {
//            if (!e.getValueIsAdjusting()) {
//                int selectedRow = ventanaDoctor.tablaCitas.getSelectedRow();
//                if (selectedRow != -1) {
//                    ventanaDoctor.diagnosticoText.setEnabled(true);
//                }
//            }
//        });
//
//        ventanaDoctor.btnEnviarDiagnostico.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                int selectedRow = ventanaDoctor.tablaCitas.getSelectedRow();
//                if (selectedRow != -1) {
//                    int confirm = JOptionPane.showConfirmDialog(ventanaDoctor,
//                            "¿Está seguro de enviar este diagnóstico?",
//                            "Confirmar Envío",
//                            JOptionPane.YES_NO_OPTION);
//                    
//                    if (confirm == JOptionPane.YES_OPTION) {
//                        String diagnostico = ventanaDoctor.diagnosticoText.getText();
//                        int idCita = (int) ventanaDoctor.tablaCitas.getValueAt(selectedRow, 0); // Suponiendo que la ID está en la primera columna
//
//                        // Lógica para actualizar el diagnóstico de la cita
//                        Cita cita = citaDAO.obtenerCitaPorId2(idCita);
//                        cita.setDiagnostico(diagnostico);
//                        citaDAO.actualizarDiagnostico(cita);
//
//                        // Crear historial médico
//                        citaDAO.insertarHistorialMedico(cita.getIdPaciente(), idCita, idDoctor, diagnostico);
//
//                        // Obtener email del paciente y enviar email
//                        String emailPaciente = citaDAO.obtenerEmailPaciente(cita.getIdPaciente());
//                        // Aquí iría la lógica para enviar el email
//                            //1.Test caught email adress
//                            System.out.println("emailPaciente = " + emailPaciente );
//                            // Lógica para enviar el email
//                        String nombreDoctor = citaDAO.obtenerNombreDoctor(idDoctor);
//                        EmailSender.enviarEmailDiagnostico(emailPaciente, diagnostico, cita.getFecha(), nombreDoctor);
//
//
//                        JOptionPane.showMessageDialog(ventanaDoctor,
//                                "Diagnóstico enviado y historial médico actualizado correctamente.",
//                                "Éxito",
//                                JOptionPane.INFORMATION_MESSAGE);
//
//                        // Recargar citas después de actualizar
//                        cargarCitas();
//                    }
//                }
//            }
//        });
//    }
//}
//package Controlador;
//
//import Modelo.DAO.CitaDAO;
//import Modelo.Cita;
//import Modelo.DAO.EmailSender;
//import Vista.VentanaDoctor;
//import javax.swing.*;
//import javax.swing.event.ListSelectionEvent;
//import javax.swing.event.ListSelectionListener;
//import javax.swing.table.DefaultTableModel;
//import java.awt.event.ActionEvent;
//import java.awt.event.ActionListener;
//import java.util.List;
//
//public class CtrlVentanaDoctor {
//    private VentanaDoctor ventanaDoctor;
//    private int idDoctor;
//    private CitaDAO citaDAO;
//
//    public CtrlVentanaDoctor(VentanaDoctor ventanaDoctor, int idDoctor) {
//        this.ventanaDoctor = ventanaDoctor;
//        this.idDoctor = idDoctor;
//        this.citaDAO = new CitaDAO();
//
//        cargarCitas();
//        initEventHandlers();
//    }
//
//    private void cargarCitas() {
//        List<Cita> citas = citaDAO.obtenerCitasPorDoctor(idDoctor);
//        DefaultTableModel model = (DefaultTableModel) ventanaDoctor.tablaCitas.getModel();
//        model.setRowCount(0); // Limpiar la tabla
//
//        for (Cita cita : citas) {
//            Object[] row = new Object[]{
//                cita.getIdCita(),
//                cita.getNombrePaciente(),
//                cita.getDniPaciente(),
//                cita.getDetalle(),
//                cita.getTurno(),
//                cita.getNumTurno(),
//                cita.getDiagnostico()
//            };
//            model.addRow(row);
//        }
//
//        // Inicialmente, deshabilitar los botones
//        ventanaDoctor.btnEnviarDiagnostico.setEnabled(false);
//        ventanaDoctor.btnVerHistorialMedico.setEnabled(false);
//        ventanaDoctor.btnPrintVoucher.setEnabled(false);
//    }
//
//    private void cargarCitasPorDni(String dni) {
//        List<Cita> citas = citaDAO.obtenerCitasPorDni(dni, idDoctor);
//        DefaultTableModel model = (DefaultTableModel) ventanaDoctor.tablaCitas.getModel();
//        model.setRowCount(0); // Limpiar la tabla
//
//        for (Cita cita : citas) {
//            Object[] row = new Object[]{
//                cita.getIdCita(),
//                cita.getNombrePaciente(),
//                cita.getDniPaciente(),
//                cita.getDetalle(),
//                cita.getTurno(),
//                cita.getNumTurno(),
//                cita.getDiagnostico()
//            };
//            model.addRow(row);
//        }
//    }
//
//    private void initEventHandlers() {
//        ventanaDoctor.btnBuscarCitaDni.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                String dni = ventanaDoctor.cajaDni.getText();
//                if (!dni.isEmpty()) {
//                    cargarCitasPorDni(dni);
//                } else {
//                    cargarCitas();
//                }
//            }
//        });
//
//        ventanaDoctor.btnVerDetalles.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                int selectedRow = ventanaDoctor.tablaCitas.getSelectedRow();
//                if (selectedRow != -1) {
//                    String detalle = (String) ventanaDoctor.tablaCitas.getValueAt(selectedRow, 3);
//                    String fecha = ventanaDoctor.tablaCitas.getValueAt(selectedRow, 4).toString();
//                    String nombrePaciente = (String) ventanaDoctor.tablaCitas.getValueAt(selectedRow, 1);
//
//                    JOptionPane.showMessageDialog(ventanaDoctor,
//                            "Detalle: " + detalle + "\nTurno: " + fecha + "\nPaciente: " + nombrePaciente,
//                            "Detalles de la Cita",
//                            JOptionPane.INFORMATION_MESSAGE);
//                }
//            }
//        });
//
//        ventanaDoctor.tablaCitas.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
//            @Override
//            public void valueChanged(ListSelectionEvent e) {
//                if (!e.getValueIsAdjusting()) {
//                    int selectedRow = ventanaDoctor.tablaCitas.getSelectedRow();
//                    if (selectedRow != -1) {
//                        String tipoPaciente = (String) ventanaDoctor.tablaCitas.getValueAt(selectedRow, 7); // Suponiendo que el tipo de paciente está en la columna 7
//
//                        if (tipoPaciente.equals("Registrado")) {
//                            ventanaDoctor.diagnosticoText.setEnabled(true);
//                            ventanaDoctor.btnEnviarDiagnostico.setEnabled(true);
//                            ventanaDoctor.btnVerHistorialMedico.setEnabled(true);
//                            ventanaDoctor.btnPrintVoucher.setEnabled(false);
//                        } else if (tipoPaciente.equals("Invitado")) {
//                            ventanaDoctor.diagnosticoText.setEnabled(false);
//                            ventanaDoctor.btnEnviarDiagnostico.setEnabled(false);
//                            ventanaDoctor.btnVerHistorialMedico.setEnabled(false);
//                            ventanaDoctor.btnPrintVoucher.setEnabled(true);
//                        }
//                    } else {
//                        ventanaDoctor.diagnosticoText.setEnabled(false);
//                        ventanaDoctor.btnEnviarDiagnostico.setEnabled(false);
//                        ventanaDoctor.btnVerHistorialMedico.setEnabled(false);
//                        ventanaDoctor.btnPrintVoucher.setEnabled(false);
//                    }
//                }
//            }
//        });
//
//        ventanaDoctor.btnEnviarDiagnostico.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                int selectedRow = ventanaDoctor.tablaCitas.getSelectedRow();
//                if (selectedRow != -1) {
//                    int confirm = JOptionPane.showConfirmDialog(ventanaDoctor,
//                            "¿Está seguro de enviar este diagnóstico?",
//                            "Confirmar Envío",
//                            JOptionPane.YES_NO_OPTION);
//                    
//                    if (confirm == JOptionPane.YES_OPTION) {
//                        String diagnostico = ventanaDoctor.diagnosticoText.getText();
//                        int idCita = (int) ventanaDoctor.tablaCitas.getValueAt(selectedRow, 0); // Suponiendo que la ID está en la primera columna
//
//                        // Lógica para actualizar el diagnóstico de la cita
//                        Cita cita = citaDAO.obtenerCitaPorId2(idCita);
//                        cita.setDiagnostico(diagnostico);
//                        citaDAO.actualizarDiagnostico(cita);
//
//                        // Crear historial médico
//                        citaDAO.insertarHistorialMedico(cita.getIdPaciente(), idCita, idDoctor, diagnostico);
//
//                        // Obtener email del paciente y enviar email
//                        String emailPaciente = citaDAO.obtenerEmailPaciente(cita.getIdPaciente());
//                        String nombreDoctor = citaDAO.obtenerNombreDoctor(idDoctor);
//
//                        if (emailPaciente != null) {
//                            EmailSender.enviarEmailDiagnostico(emailPaciente, diagnostico, cita.getFecha(), nombreDoctor);
//                        }
//
//                        JOptionPane.showMessageDialog(ventanaDoctor,
//                                "Diagnóstico enviado y historial médico actualizado correctamente.",
//                                "Éxito",
//                                JOptionPane.INFORMATION_MESSAGE);
//
//                        // Recargar citas después de actualizar
//                        cargarCitas();
//                    }
//                }
//            }
//        });
//
//        ventanaDoctor.btnPrintVoucher.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                int selectedRow = ventanaDoctor.tablaCitas.getSelectedRow();
//                if (selectedRow != -1) {
//                    int idCita = (int) ventanaDoctor.tablaCitas.getValueAt(selectedRow, 0);
//                    // Lógica para generar el voucher usando JasperReports o cualquier otro sistema
//                    // Aquí deberías implementar la funcionalidad para generar el voucher
//                    System.out.println("Generar voucher para la cita ID: " + idCita);
//                }
//            }
//        });
//
//        ventanaDoctor.btnVerHistorialMedico.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                int selectedRow = ventanaDoctor.tablaCitas.getSelectedRow();
//                if (selectedRow != -1) {
//                    int idPaciente = (int) ventanaDoctor.tablaCitas.getValueAt(selectedRow, 1);
//                    // Lógica para mostrar el historial médico del paciente
//                    // Aquí deberías implementar la funcionalidad para mostrar el historial médico
//                    System.out.println("Mostrar historial médico para el paciente ID: " + idPaciente);
//                }
//            }
//        });
//    }
//}
package Controlador;

import Conexion.Conexion;
import Modelo.DAO.CitaDAO;
import Modelo.Cita;
import Modelo.DAO.EmailSender;
import Modelo.DAO.HistorialMedicoDAO;
import Modelo.DAO.PacienteDAO;
import Modelo.Paciente;
import Vista.HistorialMedicoJDialog;
import Vista.VentanaDoctor;
import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import static javax.swing.WindowConstants.DISPOSE_ON_CLOSE;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.design.JRDesignQuery;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;

public class CtrlVentanaDoctor {

    private VentanaDoctor ventanaDoctor;
    private int idDoctor;
    private CitaDAO citaDAO;

    public CtrlVentanaDoctor(VentanaDoctor ventanaDoctor, int idDoctor) {
        this.ventanaDoctor = ventanaDoctor;
        this.idDoctor = idDoctor;
        this.citaDAO = new CitaDAO();

        cargarCitas();
        initEventHandlers();
    }

    private void cargarCitas() {
        List<Cita> citas = citaDAO.obtenerCitasPorDoctor(idDoctor);
        DefaultTableModel model = (DefaultTableModel) ventanaDoctor.tablaCitas.getModel();
        model.setRowCount(0); // Limpiar la tabla

        for (Cita cita : citas) {
            Object[] row = new Object[]{
                cita.getIdCita(),
                cita.getNombrePaciente(),
                cita.getDniPaciente(),
                cita.getDetalle(),
                cita.getTurno(),
                cita.getNumTurno(),
                cita.getTipoPaciente()
            };
            model.addRow(row);
        }

        // Inicialmente, deshabilitar los botones
        ventanaDoctor.btnEnviarDiagnostico.setEnabled(false);
        ventanaDoctor.btnVerHistorialMedico.setEnabled(false);
        ventanaDoctor.btnPrintVoucher.setEnabled(false);
    }

    private void cargarCitasPorDni(String dni) {
        List<Cita> citas = citaDAO.obtenerCitasPorDni(dni, idDoctor);
        DefaultTableModel model = (DefaultTableModel) ventanaDoctor.tablaCitas.getModel();
        model.setRowCount(0); // Limpiar la tabla

        for (Cita cita : citas) {
            Object[] row = new Object[]{
                cita.getIdCita(),
                cita.getNombrePaciente(),
                cita.getDniPaciente(),
                cita.getDetalle(),
                cita.getTurno(),
                cita.getNumTurno(),
                cita.getTipoPaciente()
            };
            model.addRow(row);
        }
    }

    public void generarPdf(int idCita) {
        try {
            Conexion con = new Conexion();
            PreparedStatement pst;
//           

            Connection conn = con.getConnection();
            JasperDesign jdesign = JRXmlLoader.load("C:\\Users\\51934\\Documents\\GitHub\\ProyectoClinica\\Clinica\\src\\main\\java\\Modelo\\DiagnosticoReport.jrxml");
            String query = "SELECT \n"
                    + "    c.IDCita, \n"
                    + "    c.Fecha, \n"
                    + "    c.Turno, \n"
                    + "    c.NumeroTurno, \n"
                    + "    c.Estado, \n"
                    + "    c.Detalle, \n"
                    + "    c.Diagnostico, \n"
                    + "    p.DNI AS dniPaciente, \n"
                    + "    CONCAT(p.Nombre, ' ', p.Apellido) AS nombreCompletoPaciente, \n"
                    + "    d.DNI_fk AS dniDoctor, \n"
                    + "    CONCAT(p2.Nombre, ' ', p2.Apellido) AS nombreDoctor, \n"
                    + "    d.Especialidad\n"
                    + "FROM \n"
                    + "    cita c\n"
                    + "JOIN \n"
                    + "    paciente pa ON c.IDPaciente_fk2 = pa.IDPaciente\n"
                    + "JOIN \n"
                    + "    persona p ON pa.DNI_fk = p.DNI\n"
                    + "JOIN \n"
                    + "    doctor d ON c.IDDoctor_fk = d.IDDoctor\n"
                    + "JOIN \n"
                    + "    persona p2 ON d.DNI_fk = p2.DNI\n"
                    + "WHERE \n"
                    + "    c.IDCita ="+idCita+";";
            JRDesignQuery updateQuery = new JRDesignQuery();
            updateQuery.setText(query);

            jdesign.setQuery(updateQuery);

            JasperReport jreport = JasperCompileManager.compileReport(jdesign);
            JasperPrint jprint = JasperFillManager.fillReport(jreport, null, conn);

            // JasperViewer.viewReport(jprint);
            JasperViewer viewer = new JasperViewer(jprint, false);
            viewer.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
            viewer.setVisible(true);

            Conexion.close(conn);

        } catch (SQLException ex) {
            Logger.getLogger(CtrlVentanaDoctor.class.getName()).log(Level.SEVERE, null, ex);
        } catch (JRException ex) {
            Logger.getLogger(CtrlVentanaDoctor.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void initEventHandlers() {
        ventanaDoctor.btnBuscarCitaDni.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String dni = ventanaDoctor.cajaDni.getText();
                if (!dni.isEmpty()) {
                    cargarCitasPorDni(dni);
                } else {
                    cargarCitas();
                }
            }
        });

        ventanaDoctor.btnVerDetalles.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = ventanaDoctor.tablaCitas.getSelectedRow();
                if (selectedRow != -1) {
                    String detalle = (String) ventanaDoctor.tablaCitas.getValueAt(selectedRow, 3);
                    String fecha = ventanaDoctor.tablaCitas.getValueAt(selectedRow, 4).toString();
                    String nombrePaciente = (String) ventanaDoctor.tablaCitas.getValueAt(selectedRow, 1);

                    JOptionPane.showMessageDialog(ventanaDoctor,
                            "Detalle: " + detalle + "\nTurno: " + fecha + "\nPaciente: " + nombrePaciente,
                            "Detalles de la Cita",
                            JOptionPane.INFORMATION_MESSAGE);
                }
            }
        });

        ventanaDoctor.tablaCitas.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting()) {
                    int selectedRow = ventanaDoctor.tablaCitas.getSelectedRow();
                    if (selectedRow != -1) {
                        String tipoPaciente = (String) ventanaDoctor.tablaCitas.getValueAt(selectedRow, 6); // Suponiendo que el tipo de paciente está en la columna 7

                        if (tipoPaciente.equals("Registrado")) {
                            ventanaDoctor.diagnosticoText.setEnabled(true);
                            ventanaDoctor.btnEnviarDiagnostico.setEnabled(true);
                            ventanaDoctor.btnVerHistorialMedico.setEnabled(true);
                            ventanaDoctor.btnPrintVoucher.setEnabled(false);
                        } else if (tipoPaciente.equals("Invitado")) {
                            ventanaDoctor.diagnosticoText.setEnabled(false);
                            ventanaDoctor.btnEnviarDiagnostico.setEnabled(false);
                            ventanaDoctor.btnVerHistorialMedico.setEnabled(false);
                            ventanaDoctor.btnPrintVoucher.setEnabled(true);
                        }
                    } else {
                        ventanaDoctor.diagnosticoText.setEnabled(false);
                        ventanaDoctor.btnEnviarDiagnostico.setEnabled(false);
                        ventanaDoctor.btnVerHistorialMedico.setEnabled(false);
                        ventanaDoctor.btnPrintVoucher.setEnabled(false);
                    }
                }
            }
        });

        ventanaDoctor.btnEnviarDiagnostico.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = ventanaDoctor.tablaCitas.getSelectedRow();
                if (selectedRow != -1) {
                    int confirm = JOptionPane.showConfirmDialog(ventanaDoctor,
                            "¿Está seguro de enviar este diagnóstico?",
                            "Confirmar Envío",
                            JOptionPane.YES_NO_OPTION);

                    if (confirm == JOptionPane.YES_OPTION) {
                        String diagnostico = ventanaDoctor.diagnosticoText.getText();
                        int idCita = (int) ventanaDoctor.tablaCitas.getValueAt(selectedRow, 0); // Suponiendo que la ID está en la primera columna

                        // Lógica para actualizar el diagnóstico de la cita
                        Cita cita = citaDAO.obtenerCitaPorId2(idCita);
                        cita.setDiagnostico(diagnostico);
                        citaDAO.actualizarDiagnostico(cita);

                        // Crear historial médico
                        citaDAO.insertarHistorialMedico(cita.getIdPaciente(), idCita, idDoctor, diagnostico);

                        // Obtener email del paciente y enviar email
                        String emailPaciente = citaDAO.obtenerEmailPaciente(cita.getIdPaciente());
                        String nombreDoctor = citaDAO.obtenerNombreDoctor(idDoctor);

                        if (emailPaciente != null) {
                            EmailSender.enviarEmailDiagnostico(emailPaciente, diagnostico, cita.getFecha(), nombreDoctor);
                        }

                        JOptionPane.showMessageDialog(ventanaDoctor,
                                "Diagnóstico enviado y historial médico actualizado correctamente.",
                                "Éxito",
                                JOptionPane.INFORMATION_MESSAGE);

                        //PDF
                        generarPdf(idCita);
                        // Recargar citas después de actualizar
                        cargarCitas();
                        ventanaDoctor.diagnosticoText.setText("");
                    }
                }
            }
        });

        ventanaDoctor.btnPrintVoucher.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = ventanaDoctor.tablaCitas.getSelectedRow();
                if (selectedRow != -1) {
                    int idCita = (int) ventanaDoctor.tablaCitas.getValueAt(selectedRow, 0);
                    generarPdf(idCita);
                    System.out.println("Generar voucher para la cita ID: " + idCita);
                }
            }
        });

        ventanaDoctor.btnVerHistorialMedico.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = ventanaDoctor.tablaCitas.getSelectedRow();
                if (selectedRow != -1) {
                    String dni = ventanaDoctor.tablaCitas.getValueAt(selectedRow, 2).toString();

                    PacienteDAO pacienteDAO = new PacienteDAO();
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
            }
        });

    }

}
