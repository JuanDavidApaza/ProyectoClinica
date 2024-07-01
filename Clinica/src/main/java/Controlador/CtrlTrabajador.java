//
//package Controlador;
//
//import Modelo.DAO.CitaDAO;
//import Modelo.DAO.HorarioDoctorDAO;
//import Modelo.DAO.PacienteDAO;
//import Vista.*;
//import java.sql.Connection;
//import java.sql.SQLException;
//import java.util.logging.Level;
//import java.util.logging.Logger;
//
//public class CtrlTrabajador {
//
//    private final VentanaTrabajador ventanaTrabajador;
//    private final PacienteDAO pacienteDAO;
//    
//   
//
//  
//   
//
//    public CtrlTrabajador(VentanaTrabajador ventanaTrabajador, PacienteDAO pacienteDAO) {
//        this.ventanaTrabajador = ventanaTrabajador;
//        this.pacienteDAO = pacienteDAO;
//        
//        //
//
//        this.ventanaTrabajador.setVisible(true);
//        this.ventanaTrabajador.setLocationRelativeTo(null);
//
//        // Asignar listeners a los botones
//        this.ventanaTrabajador.btnPacienteReg.addActionListener(e -> mostrarVentanaPacienteRegistrado());
//        this.ventanaTrabajador.btnPacienteInvit.addActionListener(e -> mostrarVentanaRegistrarPacienteInvitado());
//        this.ventanaTrabajador.btnNuevoPaciente.addActionListener(e -> mostrarVentanaRegistrarNuevoPaciente());
//    }
//
//    private void mostrarVentanaPacienteRegistrado() {
//        ventanaTrabajador.dispose(); // Cierra la ventana de trabajador
//        VentanaPacienteRegistrado ventanaPacienteRegistrado = new VentanaPacienteRegistrado();
//         
//        Connection connection;
//        try {
//            connection = Conexion.Conexion.getConnection();
//            HorarioDoctorDAO horarioDoctorDAO = new HorarioDoctorDAO(connection);
//            CitaDAO citaDAO = new CitaDAO(connection) ;
//        } catch (SQLException ex) {
//            Logger.getLogger(CtrlTrabajador.class.getName()).log(Level.SEVERE, null, ex);
//        }
//        
//        new CtrlPacienteRegistrado(ventanaPacienteRegistrado,pacienteDAO, citaDAO,horarioDoctorDAO,ventanaTrabajador);
//        ventanaPacienteRegistrado.setVisible(true);
//    }
//
//    private void mostrarVentanaRegistrarPacienteInvitado() {
//        
//        RegistrarPacienteInvitadoJdialog registrarPacienteInvitadoJDialog = new RegistrarPacienteInvitadoJdialog(ventanaTrabajador, true);
//        new CtrlRegistrarPacienteInvitado(registrarPacienteInvitadoJDialog, pacienteDAO);
//        registrarPacienteInvitadoJDialog.setVisible(true);
//    }
//
//    private void mostrarVentanaRegistrarNuevoPaciente() {
//        
//        RegistrarNuevoPacienteJdialog registrarNuevoPacienteJDialog = new RegistrarNuevoPacienteJdialog(ventanaTrabajador, true);
//        new CtrlRegistrarNuevoPaciente(registrarNuevoPacienteJDialog, pacienteDAO);
//        registrarNuevoPacienteJDialog.setVisible(true);
//    }
//}

package Controlador;

import Modelo.DAO.CitaDAO;
import Modelo.DAO.HorarioDoctorDAO;
import Modelo.DAO.PacienteDAO;
import Vista.*;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CtrlTrabajador {

    private final VentanaTrabajador ventanaTrabajador;
    private final PacienteDAO pacienteDAO;

    public CtrlTrabajador(VentanaTrabajador ventanaTrabajador, PacienteDAO pacienteDAO) {
        this.ventanaTrabajador = ventanaTrabajador;
        this.pacienteDAO = pacienteDAO;

        this.ventanaTrabajador.setVisible(true);
        this.ventanaTrabajador.setLocationRelativeTo(null);

        // Asignar listeners a los botones
        this.ventanaTrabajador.btnPacienteReg.addActionListener(e -> mostrarVentanaPacienteRegistrado());
        this.ventanaTrabajador.btnPacienteInvit.addActionListener(e -> mostrarVentanaRegistrarPacienteInvitado());
        this.ventanaTrabajador.btnNuevoPaciente.addActionListener(e -> mostrarVentanaRegistrarNuevoPaciente());
    }

    private void mostrarVentanaPacienteRegistrado() {
        ventanaTrabajador.dispose(); // Cierra la ventana de trabajador
        VentanaPacienteRegistrado ventanaPacienteRegistrado = new VentanaPacienteRegistrado();
        
        Connection connection = null;
        try {
            connection = Conexion.Conexion.getConnection();
            HorarioDoctorDAO horarioDoctorDAO = new HorarioDoctorDAO(connection);
            CitaDAO citaDAO = new CitaDAO(connection);
            new CtrlPacienteRegistrado(ventanaPacienteRegistrado, pacienteDAO, citaDAO, horarioDoctorDAO, ventanaTrabajador);
            ventanaPacienteRegistrado.setVisible(true);
        } catch (SQLException ex) {
            Logger.getLogger(CtrlTrabajador.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void mostrarVentanaRegistrarPacienteInvitado() {
        RegistrarPacienteInvitadoJdialog registrarPacienteInvitadoJDialog = new RegistrarPacienteInvitadoJdialog(ventanaTrabajador, true);
        new CtrlRegistrarPacienteInvitado(registrarPacienteInvitadoJDialog, pacienteDAO);
        registrarPacienteInvitadoJDialog.setVisible(true);
    }

    private void mostrarVentanaRegistrarNuevoPaciente() {
        RegistrarNuevoPacienteJdialog registrarNuevoPacienteJDialog = new RegistrarNuevoPacienteJdialog(ventanaTrabajador, true);
        new CtrlRegistrarNuevoPaciente(registrarNuevoPacienteJDialog, pacienteDAO);
        registrarNuevoPacienteJDialog.setVisible(true);
    }
}


