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

public class CtrlRegistrarNuevoPaciente {

    private final RegistrarNuevoPacienteJdialog registrarNuevoPacienteJdialog;
    private final PacienteDAO pacienteDAO;

    public CtrlRegistrarNuevoPaciente(RegistrarNuevoPacienteJdialog registrarNuevoPacienteJdialog, PacienteDAO pacienteDAO) {
        this.registrarNuevoPacienteJdialog = registrarNuevoPacienteJdialog;
        this.pacienteDAO = pacienteDAO;

        desactivarCajas();
        this.registrarNuevoPacienteJdialog.btnRegistrar.addActionListener(e -> registrarNuevoPaciente());
        this.registrarNuevoPacienteJdialog.btnCancelar.addActionListener(e -> cancelarRegistro());
        this.registrarNuevoPacienteJdialog.btnconsultar.addActionListener(e -> buscarDatos());
    }

    private void desactivarCajas() {
        registrarNuevoPacienteJdialog.cajaNombre.setEnabled(false);
        registrarNuevoPacienteJdialog.cajaApellidos.setEnabled(false);
        registrarNuevoPacienteJdialog.cajaSexo.setEnabled(false);
        registrarNuevoPacienteJdialog.cajaTelefono.setEnabled(false);
        registrarNuevoPacienteJdialog.cajaDireccion.setEnabled(false);
        registrarNuevoPacienteJdialog.cajaEmail.setEnabled(false);
        registrarNuevoPacienteJdialog.cajaEdad.setEnabled(false);
    }
    private void limpiarCajas(){
            registrarNuevoPacienteJdialog.cajaNombre.setText(" ");
            registrarNuevoPacienteJdialog.cajaApellidos.setText(" ");
            registrarNuevoPacienteJdialog.cajaSexo.setText(" ");
            registrarNuevoPacienteJdialog.cajaTelefono.setText(" ");
            registrarNuevoPacienteJdialog.cajaDireccion.setText(" ");
            registrarNuevoPacienteJdialog.cajaEmail.setText(" ");
            registrarNuevoPacienteJdialog.cajaEdad.setText(" ");
    }

    private void buscarDatos() {
        String token = "cGVydWRldnMucHJvZHVjdGlvbi5maXRjb2RlcnMuNjY3NjVjYTZkNDFiOTQxMTE0OGI1ODY2";
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
                
                //Rellenar en cajas
                registrarNuevoPacienteJdialog.cajaNombre.setText(nombres);
                registrarNuevoPacienteJdialog.cajaApellidos.setText(apellido_paterno + " " + apellido_materno);
                registrarNuevoPacienteJdialog.cajaSexo.setText(genero);
                
                //Activar resto de cajas  
                registrarNuevoPacienteJdialog.cajaTelefono.setEnabled(true);
                registrarNuevoPacienteJdialog.cajaDireccion.setEnabled(true);
                registrarNuevoPacienteJdialog.cajaEmail.setEnabled(true);
                registrarNuevoPacienteJdialog.cajaEdad.setEnabled(true);
                
               

                
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
        String nombre = registrarNuevoPacienteJdialog.cajaNombre.getText();
        String apellido = registrarNuevoPacienteJdialog.cajaApellidos.getText();
        String direccion = registrarNuevoPacienteJdialog.cajaDireccion.getText();
        String telefono = registrarNuevoPacienteJdialog.cajaTelefono.getText();
        String email = registrarNuevoPacienteJdialog.cajaEmail.getText();
        String sexo = registrarNuevoPacienteJdialog.cajaSexo.getText();
        String edadStr = registrarNuevoPacienteJdialog.cajaEdad.getText();

        // Validaciones
        if (dni.isEmpty()|| direccion.isEmpty()
                || telefono.isEmpty() || email.isEmpty()|| edadStr.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Todos los campos son obligatorios");
            return;
        }

        // edad
        int edad;
        try {
            edad = Integer.parseInt(edadStr);
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(null, "Ingrese una edad válida");
            return;
        }

        // Validar si el DNI ya existe
        if (pacienteDAO.existeDNI(dni)) {
            JOptionPane.showMessageDialog(null, "El DNI ingresado ya existe");
            return;
        }

        // Crear el objeto
        Paciente paciente = new Paciente(dni, nombre, apellido, direccion, telefono, email, sexo, edad, 0, "Registrado");

        // Insertar el paciente en la base de datos
        if (pacienteDAO.insertarPaciente(paciente)) {
            JOptionPane.showMessageDialog(null, "Paciente registrado exitosamente");
            registrarNuevoPacienteJdialog.dispose();
        } else {
            JOptionPane.showMessageDialog(null, "Error al registrar el paciente");
        }
    }

    private void cancelarRegistro() {
        registrarNuevoPacienteJdialog.dispose();
    }
}
