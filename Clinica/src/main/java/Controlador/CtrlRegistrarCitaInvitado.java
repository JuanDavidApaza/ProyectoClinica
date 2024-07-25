package Controlador;

import Modelo.Cita;
import Modelo.DAO.CitaDAO;
import Modelo.DAO.DoctorDAO;
import Modelo.DAO.HistorialMedicoDAO;
import Modelo.DAO.HorarioDoctorDAO;
import Modelo.DAO.PacienteDAO;
import Modelo.Doctor;
import Modelo.HorarioDoctor;
import Modelo.Paciente;
import Vista.HistorialMedicoJDialog;
import Vista.VentanaRegistroCitaInvitado;
import com.toedter.calendar.JDateChooser;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.beans.PropertyChangeListener;
import java.awt.event.ActionListener;
import java.util.Calendar;
import javax.swing.JOptionPane;

public class CtrlRegistrarCitaInvitado {

    private VentanaRegistroCitaInvitado vista;
    private CitaDAO citaDAO;
    private PacienteDAO pacienteDAO;
    private HorarioDoctorDAO horarioDoctorDAO;
    private Paciente paciente;
    private DoctorDAO doctorDAO;
    private PropertyChangeListener fechaCitaListener;
    private ActionListener turnoListener;

    public CtrlRegistrarCitaInvitado(VentanaRegistroCitaInvitado vista, CitaDAO citaDAO, PacienteDAO pacienteDAO, HorarioDoctorDAO horarioDoctorDAO, Paciente paciente, DoctorDAO doctorDAO) {
        this.vista = vista;
        this.citaDAO = citaDAO;
        this.pacienteDAO = pacienteDAO;
        this.horarioDoctorDAO = horarioDoctorDAO;
        this.paciente = paciente;
        this.doctorDAO = doctorDAO;
        this.vista.setControlador(this);
        inicializar();
    }

    private boolean vistaListenersAdded = false;

    private void inicializar() {
        if (!vistaListenersAdded) {
//            vista.btnRegistrarCita.addActionListener(e -> registrarCita());
//            vista.btnMostrarHistorial.addActionListener(e -> mostrarHistorialMedico());
//            vista.btnCancelar.addActionListener(e -> limpiarDatosPaciente());
            cargarDatosPaciente();
            cargarDoctores();
            agregarListeners();
            vistaListenersAdded = true;
        }
    }

    private void cargarDatosPaciente() {
//        vista.etiquetaNombrePaciente.setText(paciente.getNombre());
//        vista.etiquetaApellidoPaciente.setText(paciente.getApellido());
//        vista.etiquetaSexoPersona.setText(paciente.getSexo());
//        vista.etiquetaEdadPersona.setText(String.valueOf(paciente.getEdad()));
//        vista.etiquetaDNIPersona.setText(String.valueOf(paciente.getDni()));
        // ultima cita
        //vista.textAreaUltimaCita.setEnabled(false);
    }

    private void cargarDoctores() {
        List<HorarioDoctor> horarios = horarioDoctorDAO.obtenerHorariosDoctor();
        System.out.println("Número de horarios obtenidos: " + horarios.size());

        for (HorarioDoctor horario : horarios) {
            Doctor doctor = doctorDAO.obtenerDoctorPorId(horario.getIdDoctor());
            if (doctor != null) {
                vista.comboDoctor.addItem(doctor);
            }
        }
    }

    private void agregarListeners() {
        fechaCitaListener = evt -> {
            if ("date".equals(evt.getPropertyName())) {
                actualizarComboBoxDoctores();
            }
        };
        vista.fechaCita.getDateEditor().addPropertyChangeListener(fechaCitaListener);

        turnoListener = e -> actualizarComboBoxDoctores();
        vista.comboTurno.addActionListener(turnoListener);
    }

    private void actualizarComboBoxDoctores() {
        if (vista.fechaCita.getDate() == null) {
            return;
        }
        SimpleDateFormat sdf = new SimpleDateFormat("EEEE"); // Nombre completo del día de la semana
        String diaSemana = sdf.format(vista.fechaCita.getDate());
        String turno = (String) vista.comboTurno.getSelectedItem();
        List<Doctor> doctores = citaDAO.obtenerDoctoresPorDiaYTurno(diaSemana, turno);

        vista.comboDoctor.removeAllItems(); // Limpiar combo box antes de agregar nuevos items

        Set<Integer> addedDoctorIds = new HashSet<>();

        for (Doctor doctor : doctores) {
            if (!addedDoctorIds.contains(doctor.getIdDoctor())) {
                vista.comboDoctor.addItem(doctor);
                addedDoctorIds.add(doctor.getIdDoctor());
            }
        }
    }

    public void limpiarDatosPaciente() {
       // vista.etiquetaNombrePaciente.setText("");
    }
     public void limpiarDatosPacientei() {
       vista.dispose();
    }

    public void mostrarHistorialMedico() {
        
        String dni = String.valueOf(paciente.getDni());
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

    private boolean isRegistering = false;

    public synchronized void registrarCita() {
    System.out.println("Iniciando registrarCita()");

    if (isRegistering) {
        System.out.println("Intento de registro mientras ya se está registrando una cita.");
        return;
    }
    isRegistering = true;

    // Eliminar listeners antes de intentar registrar
    if (fechaCitaListener != null) {
        vista.fechaCita.getDateEditor().removePropertyChangeListener(fechaCitaListener);
    }
    if (turnoListener != null) {
        vista.comboTurno.removeActionListener(turnoListener);
    }

    try {
        JDateChooser fecha = vista.fechaCita;
        if (fecha.getDate() == null) {
            vista.mostrarMensaje("Por favor, seleccione una fecha.");
            System.out.println("Fecha no seleccionada.");
            return;
        }

        // Validar que la fecha no sea anterior a hoy
        Calendar hoy = Calendar.getInstance();
        hoy.set(Calendar.HOUR_OF_DAY, 0);
        hoy.set(Calendar.MINUTE, 0);
        hoy.set(Calendar.SECOND, 0);
        hoy.set(Calendar.MILLISECOND, 0);

        if (fecha.getDate().before(hoy.getTime())) {
            vista.mostrarMensaje("No se pueden registrar citas para fechas anteriores al día de hoy.");
            System.out.println("Fecha anterior al día de hoy.");
            return;
        }

        String turno = vista.getTurno();
        Doctor doctorSeleccionado = (Doctor) vista.comboDoctor.getSelectedItem();
        if (doctorSeleccionado == null) {
            vista.mostrarMensaje("Por favor, seleccione un doctor.");
            System.out.println("Doctor no seleccionado.");
            return;
        }

        int idDoctor = doctorSeleccionado.getIdDoctor();
        int idPaciente = paciente.getIdPaciente();
        String detalle = vista.getDetalle();

        // Obtener la lista de citas del doctor para la fecha y turno especificados
        List<Cita> citas = citaDAO.obtenerCitasPorDoctorFechaTurno(idDoctor, new java.sql.Date(fecha.getDate().getTime()), turno);

        // Verificar si ya hay 10 citas para ese doctor en esa fecha y turno
        if (citas.size() >= 10) {
            vista.mostrarMensaje("No hay turnos disponibles para este doctor en este turno");
            System.out.println("No hay turnos disponibles.");
            return;
        }

        // Calcular el número de turno de la nueva cita
        int numeroTurno = citas.size() + 1;

        // Crear una nueva instancia de Cita
        Cita cita = new Cita();
        cita.setIdPaciente(idPaciente);
        cita.setIdDoctor(idDoctor);
        cita.setFecha(new java.sql.Date(fecha.getDate().getTime())); // Convertir java.util.Date a java.sql.Date
        cita.setTurno(turno);
        cita.setEstado("pendiente");
        cita.setNumTurno(numeroTurno);
        cita.setDetalle(detalle);
        cita.setDiagnostico("");

        // Guardar la cita en la base de datos
        boolean exito = citaDAO.insertarCita(cita);

        // Mostrar un mensaje en la vista según el resultado de la operación
        if (exito) {
            vista.mostrarMensaje("Cita registrada exitosamente");
            limpiarCampos(); 
            System.out.println("Cita registrada exitosamente.");
            closeDialog(); 
        } else {
            vista.mostrarMensaje("Error al registrar la cita");
            System.out.println("Error al registrar la cita.");
        }
    } finally {
        isRegistering = false;
        System.out.println("isRegistering restablecido a false.");
    }
}


    private void limpiarCampos() {
        vista.comboDoctor.removeAllItems();
        vista.comboTurno.setSelectedIndex(0);
        vista.fechaCita.setDate(null);
        vista.textAreaDetallesCita.setText("");
    }

    private void closeDialog() {
        // Cerrar el JDialog
        vista.dispose();
    }
}
