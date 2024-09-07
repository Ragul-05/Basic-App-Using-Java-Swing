/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package studentdatabase;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.spec.SecretKeySpec;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

/**
 *
 * @author mgrat
 */
public class Decrypt extends javax.swing.JFrame {

   Homepage hp = new Homepage();
   Register rf = new Register();
   
    public Decrypt() {
        initComponents();
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
        jLabel1 = new javax.swing.JLabel();
        decryptbutton = new javax.swing.JButton();
        backtohome = new javax.swing.JButton();
        encryptfield = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(0, 153, 153));

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 36)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("Decryption");

        decryptbutton.setBackground(new java.awt.Color(0, 255, 204));
        decryptbutton.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        decryptbutton.setText("Decrypt");
        decryptbutton.setFocusPainted(false);
        decryptbutton.setPreferredSize(new java.awt.Dimension(95, 32));
        decryptbutton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                decryptbuttonActionPerformed(evt);
            }
        });

        backtohome.setBackground(new java.awt.Color(0, 255, 204));
        backtohome.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        backtohome.setText("Go Back");
        backtohome.setFocusPainted(false);
        backtohome.setPreferredSize(new java.awt.Dimension(95, 32));
        backtohome.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                backtohomeActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(178, 178, 178)
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 204, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(136, 136, 136)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(encryptfield, javax.swing.GroupLayout.PREFERRED_SIZE, 276, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(decryptbutton, javax.swing.GroupLayout.PREFERRED_SIZE, 112, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(52, 52, 52)
                                .addComponent(backtohome, javax.swing.GroupLayout.PREFERRED_SIZE, 112, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addContainerGap(149, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(59, 59, 59)
                .addComponent(encryptfield, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(35, 35, 35)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(decryptbutton, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(backtohome, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(176, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void decryptbuttonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_decryptbuttonActionPerformed
      try {
        // Open file chooser to select the encrypted file
        JFileChooser jfc = new JFileChooser("src/encrypt");
        if (jfc.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
            File encryptedFile = jfc.getSelectedFile();
            String fileName = encryptedFile.getName();
            encryptfield.setText(fileName);

            // Retrieve the secret key from the database
            String sql = "SELECT secret_key FROM key_generator WHERE filename = '" + fileName+ "' AND enc_dec = 'Encrypted'";
            rf.st = rf.conn.createStatement();
            java.sql.ResultSet rs = rf.st.executeQuery(sql);

            if (rs.next()) {
                String encodedKey = rs.getString("secretkey");
                byte[] keyBytes = java.util.Base64.getDecoder().decode(encodedKey);

                // Create the secret key specification
                SecretKeySpec keySpec = new SecretKeySpec(keyBytes, "Blowfish");

                // Create and initialize the cipher for decryption
                Cipher dec = Cipher.getInstance("Blowfish");
                dec.init(Cipher.DECRYPT_MODE, keySpec);

                // Open input stream for the encrypted file
                FileInputStream fileInputStream = new FileInputStream(encryptedFile);

                // Open output stream for the decrypted file
                FileOutputStream outStream = new FileOutputStream("src/encrypt/decryptedFile.txt");

                // Create a CipherInputStream to read the encrypted data
                CipherInputStream cis = new CipherInputStream(fileInputStream, dec);

                // Buffer to read and write data
                byte[] buf = new byte[1024];
                int read;

                // Read from the CipherInputStream and write to the output file
                while ((read = cis.read(buf)) != -1) {
                    outStream.write(buf, 0, read);
                }

                // Close streams
                cis.close();
                fileInputStream.close();
                outStream.flush();
                outStream.close();

                // Show success message
                JOptionPane.showMessageDialog(null, "Decrypted Successfully");

                // Update the database with the decryption status
                String enc_dec = "Decrypted";
                String updateSql = "UPDATE key_generator SET enc_dec = '" + enc_dec + "' WHERE filename = '" + fileName+ "'";
                rf.st.executeUpdate(updateSql);

            } else {
                JOptionPane.showMessageDialog(this, "No encrypted file found with the specified name");
            }
            rs.close();
            rf.st.close();
        } else {
            JOptionPane.showMessageDialog(this, "No file selected");
        }
    } catch (Exception e) {
        e.printStackTrace();
    }
    }//GEN-LAST:event_decryptbuttonActionPerformed

    private void backtohomeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_backtohomeActionPerformed
         dispose();
        Homepage hp = new Homepage();
        hp.show();
    }//GEN-LAST:event_backtohomeActionPerformed

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
            java.util.logging.Logger.getLogger(Decrypt.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Decrypt.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Decrypt.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Decrypt.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Decrypt().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton backtohome;
    private javax.swing.JButton decryptbutton;
    private javax.swing.JTextField encryptfield;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    // End of variables declaration//GEN-END:variables
}