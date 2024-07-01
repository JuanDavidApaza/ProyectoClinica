//package Controlador;
//
//import Modelo.Cita;
//import Modelo.DAO.CitaDAO;
//import Modelo.DAO.HorarioDoctorDAO;
//import Modelo.DAO.PacienteDAO;
//import Modelo.Paciente;
//import Vista.VentanaRegistroCitaJDialog;
//
//import java.util.Date;
//import java.util.List;
//
//public class CtrlRegistrarCita {
//
//    private VentanaRegistroCitaJDialog vista;
//    private CitaDAO citaDAO;
//    private PacienteDAO pacienteDAO;
//    private HorarioDoctorDAO horarioDoctorDAO;
//    private Paciente paciente;
//
//    public CtrlRegistrarCita(VentanaRegistroCitaJDialog vista, CitaDAO citaDAO, PacienteDAO pacienteDAO, HorarioDoctorDAO horarioDoctorDAO, Paciente paciente) {
//        this.vista = vista;
//        this.citaDAO = citaDAO;
//        this.pacienteDAO = pacienteDAO;
//        this.horarioDoctorDAO = horarioDoctorDAO;
//        this.paciente = paciente;
//        this.vista.setControlador(this);
//        inicializar();
//    }
//
//    private void inicializar() {
//        vista.getBtnRegistrarCita().addActionListener(e -> registrarCita());
//        vista.getBtnMostrarHistorial().addActionListener(e -> mostrarHistorialMedico());
//        vista.getBtnLimpiarDatos().addActionListener(e -> limpiarDatosPaciente());
//        cargarDatosPaciente();
//    }
//
//    private void cargarDatosPaciente() {
//        vista.getEtiquetaNombrePaciente().setText(paciente.getNombreCompleto());
//        // cargar cualquier otro dato necesario del paciente
//    }
//
//    private void limpiarDatosPaciente() {
//        vista.getEtiquetaNombrePaciente().setText("");
//        vista.deshabilitarBotonesOperaciones();
//    }
//
//    private void mostrarHistorialMedico() {
//        // lógica para mostrar historial médico
//    }
//
//    public void registrarCita() {
//        Date fecha = vista.getFechaCita();
//        String turno = vista.getTurno();
//        int idDoctor = vista.getIdDoctorSeleccionado();
//
//        List<Cita> citas = citaDAO.obtenerCitasPorDoctorFechaTurno(idDoctor, fecha, turno);
//        if (citas.size() >= 10) {
//            vista.mostrarMensaje("No hay turnos disponibles para este doctor en este turno");
//            return;
//        }
//
//        int numeroTurno = citas.size() + 1;
//
//        Cita cita = new Cita();
//        cita.setIDPaciente_fk2(paciente.getID());
//        cita.setFecha(fecha);
//        cita.setTurno(turno);
//        cita.setNumTurno(numeroTurno);
//        cita.setEstado("Pendiente");
//        cita.setIdDoctor(idDoctor);
//
//        if (citaDAO.insertarCita(cita)) {
//            vista.mostrarMensaje("Cita registrada exitosamente");
//            vista.limpiarFormulario();
//        } else {
//            vista.mostrarMensaje("Error al registrar la cita");
//        }
//    }
//}
//
//

