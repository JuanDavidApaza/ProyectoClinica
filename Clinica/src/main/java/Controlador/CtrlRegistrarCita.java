package Controlador;

import Modelo.Cita;
import Modelo.DAO.CitaDAO;
import Modelo.DAO.HorarioDoctorDAO;
import Modelo.DAO.PacienteDAO;
import Modelo.Doctor;
import Modelo.HorarioDoctor;
import Modelo.Paciente;
import Vista.VentanaRegistroCitaJDialog;
import com.toedter.calendar.JDateChooser;
import java.text.SimpleDateFormat;

import java.util.Date;
import java.util.List;

public class CtrlRegistrarCita {

    private VentanaRegistroCitaJDialog vista;
    private CitaDAO citaDAO;
    private PacienteDAO pacienteDAO;
    private HorarioDoctorDAO horarioDoctorDAO;
    private Paciente paciente;

    public CtrlRegistrarCita(VentanaRegistroCitaJDialog vista, CitaDAO citaDAO, PacienteDAO pacienteDAO, HorarioDoctorDAO horarioDoctorDAO, Paciente paciente) {
        this.vista = vista;
        this.citaDAO = citaDAO;
        this.pacienteDAO = pacienteDAO;
        this.horarioDoctorDAO = horarioDoctorDAO;
        this.paciente = paciente;
        this.vista.setControlador(this);
        inicializar();
    }

    private void inicializar() {
        vista.btnRegistrarCita.addActionListener(e -> registrarCita());
        vista.btnMostrarHistorial.addActionListener(e -> mostrarHistorialMedico());
        vista.btnCancelar.addActionListener(e -> limpiarDatosPaciente());
        cargarDatosPaciente();
        cargarDoctores();

        agregarListenerFechaCita();
    }

    private void cargarDatosPaciente() {
        vista.etiquetaNombrePaciente.setText(paciente.getNombre());
        vista.etiquetaApellidoPaciente.setText(paciente.getApellido());
        vista.etiquetaSexoPersona.setText(paciente.getSexo());
        vista.etiquetaEdadPersona.setText(String.valueOf(paciente.getEdad()));
        vista.etiquetaDNIPersona.setText(String.valueOf(paciente.getDni()));
        // ultima cita
        vista.textAreaUltimaCita.setEnabled(false);
    }

    private void cargarDoctores() {
        List<HorarioDoctor> horarios = horarioDoctorDAO.obtenerHorariosDoctor();
        System.out.println("Número de horarios obtenidos: " + horarios.size());

        for (HorarioDoctor horario : horarios) {
            Doctor doctor = new Doctor();
            doctor.setIdDoctor(horario.getIdDoctor());
            // Puedes establecer otros campos del doctor si es necesario
            vista.comboDoctor.addItem(doctor);
        }
    }

    private void agregarListenerFechaCita() {
        vista.fechaCita.addPropertyChangeListener("date", evt -> actualizarComboBoxDoctores());
    }

    private void actualizarComboBoxDoctores() {
        SimpleDateFormat sdf = new SimpleDateFormat("EEEE"); // Nombre completo del día de la semana
        String diaSemana = sdf.format(vista.fechaCita.getDate());
        List<Doctor> doctores = citaDAO.obtenerDoctoresPorDia(diaSemana);

        vista.comboDoctor.removeAllItems(); // Limpiar combo box antes de agregar nuevos items

        for (Doctor doctor : doctores) {
            vista.comboDoctor.addItem(doctor); // Agregar objeto Doctor al combo box
        }
    }

    public void limpiarDatosPaciente() {
        vista.etiquetaNombrePaciente.setText("");
        // vista.deshabilitarBotonesOperaciones();
    }

    public void mostrarHistorialMedico() {
        // lógica para mostrar historial médico
    }

    public void registrarCita() {
        JDateChooser fecha = vista.fechaCita;
        String turno = vista.getTurno();
        Doctor doctorSeleccionado = (Doctor) vista.comboDoctor.getSelectedItem(); // Obtener el doctor seleccionado del combo box
        int idDoctor = doctorSeleccionado.getIdDoctor();
        int idPaciente = paciente.getIdPaciente();

        // Obtener la lista de citas del doctor para la fecha y turno especificados
        List<Cita> citas = citaDAO.obtenerCitasPorDoctorFechaTurno(idDoctor, new java.sql.Date(fecha.getDate().getTime()), turno);

        // Verificar si ya hay 10 citas para ese doctor en esa fecha y turno
        if (citas.size() >= 10) {
            vista.mostrarMensaje("No hay turnos disponibles para este doctor en este turno");
            return;
        }

        // Calcular el número de turno de la nueva cita
        int numeroTurno = citas.size() + 1;

        // Crear una nueva instancia de Cita
        Cita cita = new Cita();
        cita.setIdPaciente(idPaciente);
        cita.setIdDoctor(idDoctor);
        cita.setFecha(fecha.getDate());
        cita.setTurno(turno);
        cita.setEstado("pendiente");  // O el estado inicial que consideres adecuado
        cita.setNumTurno(numeroTurno);
        cita.setDetalle("");  // O cualquier detalle inicial que consideres adecuado
        cita.setDiagnostico("");  // O cualquier diagnóstico inicial que consideres adecuado

        // Guardar la cita en la base de datos
        boolean exito = citaDAO.insertarCita(cita);

        // Mostrar un mensaje en la vista según el resultado de la operación
        if (exito) {
            vista.mostrarMensaje("Cita registrada exitosamente");
        } else {
            vista.mostrarMensaje("Error al registrar la cita");
        }
    }

}
