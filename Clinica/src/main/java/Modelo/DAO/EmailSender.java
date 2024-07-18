package Modelo.DAO;
import Modelo.Paciente;
import Modelo.Cita;
import Modelo.Pago;
import Modelo.DAO.PacienteDAO;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Date;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class EmailSender {

    public static void enviarEmailPago(Cita cita, Pago pago, Paciente paciente) {
        String to = "deyvid072001pc@gmail.com"; 
        String from = "sielgringoquichh@gmail.com"; 
        String password = "qvfpfalklbtvfsgm"; // Contraseña de la aplicación (no la contraseña normal)
        
        String horaPago = new java.text.SimpleDateFormat("HH:mm:ss").format(new Date());
        String nombre = ""+ paciente.getNombre()+ " " + paciente.getApellido();

        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com"); // Servidor SMTP de Gmail
        props.put("mail.smtp.port", "587"); // Puerto para TLS

        Session session = Session.getInstance(props,
          new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(from, password);
            }
          });

        try {
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(from));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));

            message.setSubject("Confirmación de Pago de Cita #"+cita.getIdCita());

            String body = String.format(
                "Se ha realizado el pago de la cita N°%d\n" +
                "Nombre del Paciente: %s\n" +
                "DNI del Paciente: %s\n" +
                "Fecha de la Cita: %s\n" +
                "Turno: %s\n" +
                "Número de Turno: %d\n" +
                "Monto Pagado: %.2f\n"+
                "Hora Pago: %s\n",        
                cita.getIdCita(),
                nombre,
                paciente.getDni(),
                cita.getFecha().toString(),
                cita.getTurno(),
                cita.getNumTurno(),
                pago.getMonto(),
                horaPago
            );

            message.setText(body);

            Transport.send(message);
            System.out.println("Correo enviado correctamente.");
        } catch (MessagingException mex) {
            mex.printStackTrace();
        }
    }
  public static void enviarEmailDiagnostico(String to, String diagnostico, Date fecha, String nombreDoctor) {
        String from = "sielgringoquichh@gmail.com";
        final String username = "sielgringoquichh@gmail.com";
        final String password = "qvfpfalklbtvfsgm";

        String host = "smtp.gmail.com";

        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", host);
        props.put("mail.smtp.port", "587");

        Session session = Session.getInstance(props, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(from));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
            message.setSubject("Diagnóstico Médico");
            message.setText("Estimado paciente,\n\n"
                    + "Le informamos su diagnóstico médico:\n"
                    + "Diagnóstico: " + diagnostico + "\n"
                    + "Fecha de la consulta: " + fecha + "\n"
                    + "Doctor: " + nombreDoctor + "\n\n"
                    + "Saludos,\n"
                    + "Clinica ");

            Transport.send(message);

            System.out.println("Email enviado exitosamente...");

        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    
  }
}
