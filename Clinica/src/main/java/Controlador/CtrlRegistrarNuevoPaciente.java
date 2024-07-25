package Controlador;

import Modelo.DAO.PacienteDAO;
import Modelo.Paciente;
import Vista.RegistrarNuevoPacienteJdialog;
import javax.swing.JOptionPane;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;

public class CtrlRegistrarNuevoPaciente {

    private final RegistrarNuevoPacienteJdialog registrarNuevoPacienteJdialog;
    private final PacienteDAO pacienteDAO;

    public CtrlRegistrarNuevoPaciente(RegistrarNuevoPacienteJdialog registrarNuevoPacienteJdialog, PacienteDAO pacienteDAO) {
        this.registrarNuevoPacienteJdialog = registrarNuevoPacienteJdialog;
        this.pacienteDAO = pacienteDAO;

        desactivarCajas();
        inicializarListeners();
    }

    private void inicializarListeners() {
        this.registrarNuevoPacienteJdialog.ComboBoxDocumento.addActionListener(e -> manejarTipoDocumento());
        this.registrarNuevoPacienteJdialog.btnRegistrar.addActionListener(e -> registrarNuevoPaciente());
        this.registrarNuevoPacienteJdialog.btnCancelar.addActionListener(e -> cancelarRegistro());
        this.registrarNuevoPacienteJdialog.btnconsultar.addActionListener(e -> buscarDatos());
    }

    private void manejarTipoDocumento() {
        String tipoDocumento = (String) registrarNuevoPacienteJdialog.ComboBoxDocumento.getSelectedItem();
        if ("DNI".equals(tipoDocumento)) {
            registrarNuevoPacienteJdialog.btnconsultar.setVisible(true);
            desactivarCajas();
            limpiarCajas();
        } else {
            registrarNuevoPacienteJdialog.btnconsultar.setVisible(false);
            activarCajas();
            limpiarCajas();
        }
    }

    private void desactivarCajas() {
        registrarNuevoPacienteJdialog.cajaNombre.setEnabled(false);
        registrarNuevoPacienteJdialog.cajaApellidos.setEnabled(false);
        registrarNuevoPacienteJdialog.cajaSexo.setEnabled(false);
        registrarNuevoPacienteJdialog.cajaTelefono.setEnabled(false);
        registrarNuevoPacienteJdialog.cajaDireccion.setEnabled(false);
        registrarNuevoPacienteJdialog.cajaEmail.setEnabled(false);
        registrarNuevoPacienteJdialog.cajaEdad.setEnabled(false);
        registrarNuevoPacienteJdialog.cajaPais.setEnabled(false);
    }

    private void activarCajas() {
        registrarNuevoPacienteJdialog.cajaNombre.setEnabled(true);
        registrarNuevoPacienteJdialog.cajaApellidos.setEnabled(true);
        registrarNuevoPacienteJdialog.cajaSexo.setEnabled(true);
        registrarNuevoPacienteJdialog.cajaTelefono.setEnabled(true);
        registrarNuevoPacienteJdialog.cajaDireccion.setEnabled(true);
        registrarNuevoPacienteJdialog.cajaEmail.setEnabled(true);
        registrarNuevoPacienteJdialog.cajaEdad.setEnabled(true);
        registrarNuevoPacienteJdialog.cajaPais.setEnabled(true);
    }

    private void limpiarCajas() {
        registrarNuevoPacienteJdialog.cajaNombre.setText("");
        registrarNuevoPacienteJdialog.cajaApellidos.setText("");
        registrarNuevoPacienteJdialog.cajaSexo.setText("");
        registrarNuevoPacienteJdialog.cajaTelefono.setText("");
        registrarNuevoPacienteJdialog.cajaDireccion.setText("");
        registrarNuevoPacienteJdialog.cajaEmail.setText("");
        registrarNuevoPacienteJdialog.cajaEdad.setText("");
        registrarNuevoPacienteJdialog.cajaPais.setText("");
        registrarNuevoPacienteJdialog.cajaDNI.setText("");

    }

    private void buscarDatos() {
        String token = "cGVydWRldnMucHJvZHVjdGlvbi5maXRjb2RlcnMuNjZhMTljOGZkNDFiOTQxMTE0OGI1OTMz";
        String leerdni = registrarNuevoPacienteJdialog.cajaDNI.getText();
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

                // Calcular edad
                int edad = calcularEdad(fecha_nacimiento, "dd/MM/yyyy");

                // Rellenar en cajas
                registrarNuevoPacienteJdialog.cajaNombre.setText(nombres);
                registrarNuevoPacienteJdialog.cajaApellidos.setText(apellido_paterno + " " + apellido_materno);
                registrarNuevoPacienteJdialog.cajaSexo.setText(genero);
                registrarNuevoPacienteJdialog.cajaEdad.setText(String.valueOf(edad));

                // Activar resto de cajas  
                registrarNuevoPacienteJdialog.cajaTelefono.setEnabled(true);
                registrarNuevoPacienteJdialog.cajaDireccion.setEnabled(true);
                registrarNuevoPacienteJdialog.cajaEmail.setEnabled(true);

                //Establecer pais
                registrarNuevoPacienteJdialog.cajaPais.setText("Perú");
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
            JOptionPane.showMessageDialog(null, "DNI NO REGISTRADO EN LA RENIEC");
            desactivarCajas();
            limpiarCajas();
        }
    }

    private void registrarNuevoPaciente() {
        String dni = registrarNuevoPacienteJdialog.cajaDNI.getText().trim(); // Trim para eliminar espacios en blanco
        String nombre = registrarNuevoPacienteJdialog.cajaNombre.getText().trim();
        String apellido = registrarNuevoPacienteJdialog.cajaApellidos.getText().trim();
        String direccion = registrarNuevoPacienteJdialog.cajaDireccion.getText().trim();
        String telefono = registrarNuevoPacienteJdialog.cajaTelefono.getText().trim();
        String email = registrarNuevoPacienteJdialog.cajaEmail.getText().trim();
        String sexo = registrarNuevoPacienteJdialog.cajaSexo.getText().trim();
        String edadStr = registrarNuevoPacienteJdialog.cajaEdad.getText().trim();
        String pais = registrarNuevoPacienteJdialog.cajaPais.getText().trim();

        // Validaciones
        if (dni.isEmpty() || direccion.isEmpty() || telefono.isEmpty() || email.isEmpty() || edadStr.isEmpty() || pais.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Todos los campos son obligatorios");
            return;
        }

        // Validar sexo
        if (!sexo.equals("F") && !sexo.equals("M")) {
            JOptionPane.showMessageDialog(null, "El sexo debe ser F o M");
            return;
        }

        // Validar edad
        int edad;
        try {
            edad = Integer.parseInt(edadStr);
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(null, "Ingrese una edad válida");
            return;
        }

        // Validar si el DNI ya existe
        Paciente pacienteExistente = pacienteDAO.obtenerPacientePorDNI(dni);
        if (pacienteExistente != null) {
            if ("Invitado".equals(pacienteExistente.getTipo())) {
                int respuesta = JOptionPane.showConfirmDialog(null, "El DNI ingresado pertenece a un paciente invitado. ¿Desea registrarlo?", "Confirmar registro", JOptionPane.YES_NO_OPTION);
                if (respuesta == JOptionPane.YES_OPTION) {
                    actualizarTipoPaciente(pacienteExistente, nombre, apellido, direccion, telefono, email, sexo, edad, pais);
                }
            } else {
                JOptionPane.showMessageDialog(null, "El DNI ingresado ya existe");
            }
            return;
        }

        // Crear el objeto Paciente
        Paciente paciente = new Paciente(dni, nombre, apellido, direccion, telefono, email, sexo, edad, pais, 0, "Registrado");

        // Insertar el paciente en la base de datos
        if (pacienteDAO.insertarPaciente(paciente)) {
            JOptionPane.showMessageDialog(null, "Paciente registrado exitosamente");
            registrarNuevoPacienteJdialog.dispose();
        } else {
            JOptionPane.showMessageDialog(null, "Error al registrar el paciente, verificar datos");
        }
    }

    private void actualizarTipoPaciente(Paciente pacienteExistente, String nombre, String apellido, String direccion, String telefono, String email, String sexo, int edad, String pais) {
        pacienteExistente.setNombre(nombre);
        pacienteExistente.setApellido(apellido);
        pacienteExistente.setDireccion(direccion);
        pacienteExistente.setTelefono(telefono);
        pacienteExistente.setEmail(email);
        pacienteExistente.setSexo(sexo);
        pacienteExistente.setEdad(edad);
        pacienteExistente.setPais(pais);
        pacienteExistente.setTipo("Registrado");

        if (pacienteDAO.actualizarPaciente(pacienteExistente)) {
            if (pacienteDAO.actualizarTipoPaciente(pacienteExistente.getDni(), "Registrado")) {
                JOptionPane.showMessageDialog(null, "Paciente actualizado a Registrado exitosamente");
                registrarNuevoPacienteJdialog.dispose();
            } else {
                JOptionPane.showMessageDialog(null, "Error al actualizar el tipo de paciente");
            }
        } else {
            JOptionPane.showMessageDialog(null, "Error al actualizar el paciente");
        }
    }

    private int calcularEdad(String fechaNacimiento, String formato) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(formato);
        LocalDate fechaNac = LocalDate.parse(fechaNacimiento, formatter);
        LocalDate ahora = LocalDate.now();
        return Period.between(fechaNac, ahora).getYears();
    }

    private void cancelarRegistro() {
        registrarNuevoPacienteJdialog.dispose();
    }
}
