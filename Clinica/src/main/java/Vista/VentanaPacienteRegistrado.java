package Vista;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

/**
 *
 * @author 51934
 */
public class VentanaPacienteRegistrado extends javax.swing.JFrame {

    /**
     * Creates new form VentanaTrabajador
     */
    public VentanaPacienteRegistrado(){
        initComponents();
        setSize(1200, 700);
        setResizable(false);
        setLocationRelativeTo(null);
        
        
        try {
            UIManager.setLookAndFeel("com.jtattoo.plaf.aero.AeroLookAndFeel");
            SwingUtilities.updateComponentTreeUI(this);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(VentanaPacienteRegistrado.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            Logger.getLogger(VentanaPacienteRegistrado.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(VentanaPacienteRegistrado.class.getName()).log(Level.SEVERE, null, ex);
        } catch (UnsupportedLookAndFeelException ex) {
            Logger.getLogger(VentanaPacienteRegistrado.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        etiquetaNombrePaciente = new javax.swing.JLabel();
        cajaDni = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        btnBuscarPacienteDNI = new javax.swing.JButton();
        jLabel7 = new javax.swing.JLabel();
        btnRegistrarCita = new javax.swing.JButton();
        btnHistorialMedico = new javax.swing.JButton();
        btnModificarDatos = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jToolBar1 = new javax.swing.JToolBar();
        btnIraPaciente = new javax.swing.JButton();
        btnIraPago = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(0, 204, 153));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(0, 0, 0));
        jLabel2.setText("Modificar Datos");
        jPanel1.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(890, 380, -1, -1));

        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(0, 0, 0));
        jLabel3.setText("Registrar cita");
        jPanel1.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 380, -1, 30));

        jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(0, 0, 0));
        jLabel4.setText("Historial Medico");
        jPanel1.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(480, 380, -1, -1));

        etiquetaNombrePaciente.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        etiquetaNombrePaciente.setForeground(new java.awt.Color(0, 0, 255));
        jPanel1.add(etiquetaNombrePaciente, new org.netbeans.lib.awtextra.AbsoluteConstraints(390, 140, -1, 30));

        cajaDni.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cajaDniActionPerformed(evt);
            }
        });
        jPanel1.add(cajaDni, new org.netbeans.lib.awtextra.AbsoluteConstraints(500, 100, 190, -1));

        jLabel6.setFont(new java.awt.Font("Segoe UI", 1, 36)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(0, 0, 0));
        jLabel6.setText("Búsqueda Paciente Registrado");
        jPanel1.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 0, -1, 50));

        btnBuscarPacienteDNI.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        btnBuscarPacienteDNI.setText("Buscar");
        jPanel1.add(btnBuscarPacienteDNI, new org.netbeans.lib.awtextra.AbsoluteConstraints(730, 100, -1, -1));

        jLabel7.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(0, 0, 0));
        jLabel7.setText("Ingrese Dni: ");
        jPanel1.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 90, -1, 30));

        btnRegistrarCita.setBackground(new java.awt.Color(0, 102, 102));
        btnRegistrarCita.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/cita.png"))); // NOI18N
        btnRegistrarCita.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRegistrarCitaActionPerformed(evt);
            }
        });
        jPanel1.add(btnRegistrarCita, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 220, 150, 150));

        btnHistorialMedico.setBackground(new java.awt.Color(0, 102, 102));
        btnHistorialMedico.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/informe-medico.png"))); // NOI18N
        jPanel1.add(btnHistorialMedico, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 220, 150, 140));

        btnModificarDatos.setBackground(new java.awt.Color(0, 102, 102));
        btnModificarDatos.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/documento.png"))); // NOI18N
        jPanel1.add(btnModificarDatos, new org.netbeans.lib.awtextra.AbsoluteConstraints(910, 220, -1, -1));

        jPanel2.setBackground(new java.awt.Color(0, 102, 102));

        jToolBar1.setBackground(new java.awt.Color(0, 102, 102));
        jToolBar1.setRollover(true);

        btnIraPaciente.setFont(new java.awt.Font("Segoe UI Semibold", 1, 18)); // NOI18N
        btnIraPaciente.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/paciente.png"))); // NOI18N
        btnIraPaciente.setText("Paciente");
        btnIraPaciente.setAlignmentX(0.5F);
        btnIraPaciente.setFocusable(false);
        btnIraPaciente.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnIraPaciente.setMargin(new java.awt.Insets(3, 25, 3, 25));
        btnIraPaciente.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnIraPaciente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnIraPacienteActionPerformed(evt);
            }
        });
        jToolBar1.add(btnIraPaciente);

        btnIraPago.setFont(new java.awt.Font("Segoe UI Semibold", 1, 18)); // NOI18N
        btnIraPago.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/pay.png"))); // NOI18N
        btnIraPago.setText("Pago");
        btnIraPago.setAlignmentX(0.5F);
        btnIraPago.setFocusable(false);
        btnIraPago.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnIraPago.setMargin(new java.awt.Insets(2, 20, 3, 14));
        btnIraPago.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jToolBar1.add(btnIraPago);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(60, 60, 60)
                .addComponent(jToolBar1, javax.swing.GroupLayout.PREFERRED_SIZE, 507, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jToolBar1, javax.swing.GroupLayout.DEFAULT_SIZE, 112, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 1286, Short.MAX_VALUE)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 697, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnIraPacienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnIraPacienteActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnIraPacienteActionPerformed

    private void cajaDniActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cajaDniActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cajaDniActionPerformed

    private void btnRegistrarCitaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRegistrarCitaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnRegistrarCitaActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(VentanaPacienteRegistrado.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(VentanaPacienteRegistrado.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(VentanaPacienteRegistrado.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(VentanaPacienteRegistrado.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new VentanaPacienteRegistrado().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    public javax.swing.JButton btnBuscarPacienteDNI;
    public javax.swing.JButton btnHistorialMedico;
    public javax.swing.JButton btnIraPaciente;
    public javax.swing.JButton btnIraPago;
    public javax.swing.JButton btnModificarDatos;
    public javax.swing.JButton btnRegistrarCita;
    public javax.swing.JTextField cajaDni;
    public javax.swing.JLabel etiquetaNombrePaciente;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JToolBar jToolBar1;
    // End of variables declaration//GEN-END:variables
}
