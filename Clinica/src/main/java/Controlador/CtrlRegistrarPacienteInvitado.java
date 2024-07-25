package Controlador;

import Conexion.Conexion;
import Modelo.DAO.CitaDAO;
import Modelo.DAO.DoctorDAO;
import Modelo.DAO.HorarioDoctorDAO;
import Modelo.DAO.PacienteDAO;
import Modelo.Paciente;
import Vista.RegistrarPacienteInvitadoJdialog;
import Vista.VentanaRegistroCitaInvitado;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

public class CtrlRegistrarPacienteInvitado {

    private final RegistrarPacienteInvitadoJdialog registrarPacienteInvitadoJdialog;
    private final PacienteDAO pacienteDAO;

    public CtrlRegistrarPacienteInvitado(RegistrarPacienteInvitadoJdialog registrarPacienteInvitadoJdialog, PacienteDAO pacienteDAO) {
        this.registrarPacienteInvitadoJdialog = registrarPacienteInvitadoJdialog;
        this.pacienteDAO = pacienteDAO;
        desactivarCajas();
        inicializarListeners();
    }

    private void inicializarListeners() {
        this.registrarPacienteInvitadoJdialog.btnRegistrar.addActionListener(e -> registrarPacienteInvitado());
        this.registrarPacienteInvitadoJdialog.btnCancelar.addActionListener(e -> cancelarRegistro());
        this.registrarPacienteInvitadoJdialog.btnConsultar.addActionListener(e -> buscarDatos());
        this.registrarPacienteInvitadoJdialog.ComboBoxDocumento.addActionListener(e -> manejarTipoDocumento());
    }

    private void manejarTipoDocumento() {
        String tipoDocumento = (String) registrarPacienteInvitadoJdialog.ComboBoxDocumento.getSelectedItem();
        if ("DNI".equals(tipoDocumento)) {
            registrarPacienteInvitadoJdialog.btnConsultar.setVisible(true);
            desactivarCajas();
            limpiarCajas();
        } else {
            registrarPacienteInvitadoJdialog.btnConsultar.setVisible(false);
            activarCajas();
            limpiarCajas();
        }
    }

    private void activarCajas() {
        this.registrarPacienteInvitadoJdialog.cajaNombre.setEnabled(true);
        this.registrarPacienteInvitadoJdialog.cajaApellidos.setEnabled(true);
        this.registrarPacienteInvitadoJdialog.cajaSexo.setEnabled(true);
        this.registrarPacienteInvitadoJdialog.cajaPais.setEnabled(true);
    }

    private void desactivarCajas() {
        this.registrarPacienteInvitadoJdialog.cajaNombre.setEnabled(false);
        this.registrarPacienteInvitadoJdialog.cajaApellidos.setEnabled(false);
        this.registrarPacienteInvitadoJdialog.cajaSexo.setEnabled(false);
        this.registrarPacienteInvitadoJdialog.cajaPais.setEnabled(false);
    }

    private void limpiarCajas() {
        this.registrarPacienteInvitadoJdialog.cajaNombre.setText(" ");
        this.registrarPacienteInvitadoJdialog.cajaApellidos.setText(" ");
        this.registrarPacienteInvitadoJdialog.cajaSexo.setText(" ");
        this.registrarPacienteInvitadoJdialog.cajaDNI.setText(" ");
        this.registrarPacienteInvitadoJdialog.cajaPais.setText(" ");

    }

    private void buscarDatos() {
        String token = "cGVydWRldnMucHJvZHVjdGlvbi5maXRjb2RlcnMuNjZhMTljOGZkNDFiOTQxMTE0OGI1OTMz";
        String leerdni = registrarPacienteInvitadoJdialog.cajaDNI.getText();
        String enlace = "https://api.perudevs.com/api/v1/dni/complete?document=" + leerdni + "&key=" + token;

        try {
            URL url = new URL(enlace);
            URLConnection request = url.openConnection();
            request.connect();

            JsonParser jp = new JsonParser();
            JsonElement root = jp.parse(new InputStreamReader((InputStream) request.getContent()));

            if (root.isJsonObject()) {
                JsonObject rootobj = root.getAsJsonObject();

                String apellido_paterno = rootobj.has("resultado") ? rootobj.getAsJsonObject("resultado").has("apellido_paterno") ? rootobj.getAsJsonObject("resultado").get("apellido_paterno").getAsString() : "" : "";
                String apellido_materno = rootobj.has("resultado") ? rootobj.getAsJsonObject("resultado").has("apellido_materno") ? rootobj.getAsJsonObject("resultado").get("apellido_materno").getAsString() : "" : "";
                String nombres = rootobj.has("resultado") ? rootobj.getAsJsonObject("resultado").has("nombres") ? rootobj.getAsJsonObject("resultado").get("nombres").getAsString() : "" : "";
                String genero = rootobj.has("resultado") ? rootobj.getAsJsonObject("resultado").has("genero") ? rootobj.getAsJsonObject("resultado").get("genero").getAsString() : "" : "";
                String fecha_nacimiento = rootobj.has("resultado") ? rootobj.getAsJsonObject("resultado").has("fecha_nacimiento") ? rootobj.getAsJsonObject("resultado").get("fecha_nacimiento").getAsString() : "" : "";
                String nombre_completo = rootobj.has("resultado") ? rootobj.getAsJsonObject("resultado").has("nombre_completo") ? rootobj.getAsJsonObject("resultado").get("nombre_completo").getAsString() : "" : "";
                String codigo_verificacion = rootobj.has("resultado") ? rootobj.getAsJsonObject("resultado").has("codigo_verificacion") ? rootobj.getAsJsonObject("resultado").get("codigo_verificacion").getAsString() : "" : "";

                //Rellenar en cajas
                registrarPacienteInvitadoJdialog.cajaNombre.setText(nombres);
                registrarPacienteInvitadoJdialog.cajaApellidos.setText(apellido_paterno + " " + apellido_materno);
                registrarPacienteInvitadoJdialog.cajaSexo.setText(genero);

                //Establecer pais
                registrarPacienteInvitadoJdialog.cajaPais.setText("Perú");
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
            JOptionPane.showMessageDialog(null, "DNI NO REGISTRADO EN LA RENIEC");
            desactivarCajas();
            limpiarCajas();
        }
    }

    private void registrarPacienteInvitado() {
        String dni = registrarPacienteInvitadoJdialog.cajaDNI.getText().trim(); // Trim para eliminar espacios en blanco
        String nombre = registrarPacienteInvitadoJdialog.cajaNombre.getText().trim();
        String apellido = registrarPacienteInvitadoJdialog.cajaApellidos.getText().trim();
        String direccion = null;
        String telefono = null;
        String email = null;
        String sexo = registrarPacienteInvitadoJdialog.cajaSexo.getText().trim();
        String pais = registrarPacienteInvitadoJdialog.cajaPais.getText().trim();
        int edad = 0;

        // Validaciones
        // Validar si el DNI ya existe
        if (pacienteDAO.existeDNI(dni)) {
            JOptionPane.showMessageDialog(null, "El DNI ingresado ya existe");
            return;
        }
        // Validar sexo
        if (!sexo.equals("F") && !sexo.equals("M")) {
            JOptionPane.showMessageDialog(null, "El sexo debe ser F o M");
            return;
        }
        if (dni.isEmpty() || nombre.isEmpty() || apellido.isEmpty() || sexo.isEmpty() || pais.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Todos los campos son obligatorios");
            return;
        }

        // Crear el objeto
        Paciente paciente = new Paciente(dni, nombre, apellido, direccion, telefono, email, sexo, edad, pais, 0, "Invitado");

        // Insertar el paciente en la base de datos
        if (pacienteDAO.insertarPaciente(paciente)) {
            JOptionPane.showMessageDialog(null, "Paciente invitado, ir a registrar cita");
            registrarPacienteInvitadoJdialog.dispose();
        } else {
            JOptionPane.showMessageDialog(null, "Error, verificar datos ");
        }

        //Abrir
        VentanaRegistroCitaInvitado ventanaRegistroCitaInvitado = new VentanaRegistroCitaInvitado(null, true);
        DoctorDAO doctorDAO = new DoctorDAO();
        ventanaRegistroCitaInvitado.setVisible(true);
        CitaDAO citaDAO = new CitaDAO();
        Connection connection;
        try {
            connection = Conexion.getConnection();
            HorarioDoctorDAO horarioDoctorDAO = new HorarioDoctorDAO(connection);
            new CtrlRegistrarCitaInvitado(ventanaRegistroCitaInvitado, citaDAO, pacienteDAO, horarioDoctorDAO, paciente, doctorDAO);
            ventanaRegistroCitaInvitado.setVisible(true);
            registrarPacienteInvitadoJdialog.dispose();
        } catch (SQLException ex) {
            Logger.getLogger(CtrlRegistrarPacienteInvitado.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    private void cancelarRegistro() {
        registrarPacienteInvitadoJdialog.dispose();
    }
}
