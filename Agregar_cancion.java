/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Tablas;

import com.mysql.jdbc.Connection;
import java.awt.Image;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Usuario
 */
public class Agregar_cancion extends javax.swing.JFrame {
// Esta variable guardará el archivo MP3 que selecciones en el botón Examinar
File archivoSeleccionado;
    /**
     * Creates new form Agregar_cancion
     */
    public Agregar_cancion() {
        initComponents();
                    setSize(992, 619);  // ancho, alto
               setLocationRelativeTo(null);
               SetImageLabel(fon, "src/Imagenes/fondo.reproductor.jpg");
cbGenero.removeAllItems(); // Limpia los items por defecto
cbGenero.addItem("Rock");
cbGenero.addItem("Pop");
cbGenero.addItem("Trap");
cbGenero.addItem("Rap");
cbGenero.addItem("Corrido");
cbGenero.addItem("otro");

    }

public void mostrarCanciones() {
    DefaultTableModel modelo = new DefaultTableModel();
    modelo.addColumn("ID");
    modelo.addColumn("Nombre");
    modelo.addColumn("Artista");
    modelo.addColumn("Duración");
    modelo.addColumn("Género");
    
    // AQUÍ: Usa el nombre correcto de tu tabla (ej: jTable1)
    Tabla.setModel(modelo); 

    String sql = "SELECT ID_CANCION, NOMBRE, ARTISTA, DURACION, GENERO FROM canciones";
    String[] datos = new String[5];
    
    try {
        Conexion.conexion cc = new Conexion.conexion();
        Connection cn = cc.conectar();
        
        // VALIDACIÓN: Si la conexión falla, no intentes seguir
        if (cn != null) {
            Statement st = cn.createStatement();
            ResultSet rs = st.executeQuery(sql);
            
            while (rs.next()) {
                datos[0] = rs.getString(1);
                datos[1] = rs.getString(2);
                datos[2] = rs.getString(3);
                datos[3] = rs.getString(4);
                datos[4] = rs.getString(5);
                modelo.addRow(datos);
            }
        }
    } catch (SQLException e) {
        JOptionPane.showMessageDialog(null, "Error al cargar los datos: " + e.getMessage());
    }
}
public void mostrarUltimoAgregado() {
    try {
        // 1. Capturamos el modelo actual de la tabla
        DefaultTableModel modelo = (DefaultTableModel) Tabla.getModel();
        
        // 2. Limpiamos todas las filas existentes (para que solo quede la nueva)
        modelo.setRowCount(0);

        // 3. Consultamos el último registro en la DB
        String sql = "SELECT ID_CANCION, NOMBRE, ARTISTA, DURACION, GENERO FROM canciones ORDER BY ID_CANCION DESC LIMIT 1";
        
        Conexion.conexion cc = new Conexion.conexion();
        Connection cn = cc.conectar();
        Statement st = cn.createStatement();
        ResultSet rs = st.executeQuery(sql);

        if (rs.next()) {
            Object[] fila = new Object[5];
            fila[0] = rs.getString(1);
            fila[1] = rs.getString(2);
            fila[2] = rs.getString(3);
            fila[3] = rs.getString(4);
            fila[4] = rs.getString(5);
            
            // 4. Agregamos la fila al modelo
            modelo.addRow(fila);
            
            // 5. ¡ESTO ES LO MÁS IMPORTANTE! Forzar a la tabla a mostrar los cambios
            Tabla.revalidate();
            Tabla.repaint();
        }
        
        cn.close();
    } catch (Exception e) {
        JOptionPane.showMessageDialog(null, "Error al actualizar tabla: " + e.getMessage());
    }
}
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        Tabla = new javax.swing.JTable();
        jButton1 = new javax.swing.JButton();
        jSeparator1 = new javax.swing.JSeparator();
        jSeparator2 = new javax.swing.JSeparator();
        jSeparator3 = new javax.swing.JSeparator();
        jButton2 = new javax.swing.JButton();
        txtNombre = new javax.swing.JTextField();
        txtArtista = new javax.swing.JTextField();
        txtDuracion = new javax.swing.JTextField();
        cbGenero = new javax.swing.JComboBox<>();
        fon = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setLayout(null);

        jPanel2.setLayout(null);

        jLabel1.setFont(new java.awt.Font("Times New Roman", 2, 24)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("GUARDAR CANCION");
        jPanel2.add(jLabel1);
        jLabel1.setBounds(250, 10, 394, 62);

        Tabla.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        jScrollPane1.setViewportView(Tabla);

        jPanel2.add(jScrollPane1);
        jScrollPane1.setBounds(0, 80, 890, 150);

        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/folder.png"))); // NOI18N
        jButton1.setText("Guardar");
        jButton1.setContentAreaFilled(false);
        jButton1.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButton1.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        jPanel2.add(jButton1);
        jButton1.setBounds(160, 320, 90, 110);

        jSeparator1.setOrientation(javax.swing.SwingConstants.VERTICAL);
        jPanel2.add(jSeparator1);
        jSeparator1.setBounds(280, 240, 10, 270);
        jPanel2.add(jSeparator2);
        jSeparator2.setBounds(0, 240, 890, 10);
        jPanel2.add(jSeparator3);
        jSeparator3.setBounds(0, 70, 890, 10);

        jButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/search-file_3547774.png"))); // NOI18N
        jButton2.setText("Examinar");
        jButton2.setContentAreaFilled(false);
        jButton2.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButton2.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        jPanel2.add(jButton2);
        jButton2.setBounds(30, 320, 90, 110);

        txtNombre.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Nombre", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Times New Roman", 2, 13))); // NOI18N
        jPanel2.add(txtNombre);
        txtNombre.setBounds(410, 240, 350, 60);

        txtArtista.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Artista", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Times New Roman", 2, 13))); // NOI18N
        jPanel2.add(txtArtista);
        txtArtista.setBounds(410, 310, 350, 60);

        txtDuracion.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Duracion", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Times New Roman", 2, 13))); // NOI18N
        txtDuracion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtDuracionActionPerformed(evt);
            }
        });
        jPanel2.add(txtDuracion);
        txtDuracion.setBounds(410, 380, 350, 60);

        cbGenero.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cbGenero.setToolTipText("Genero");
        cbGenero.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Genero", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Times New Roman", 2, 13))); // NOI18N
        jPanel2.add(cbGenero);
        cbGenero.setBounds(410, 450, 350, 60);

        jPanel1.add(jPanel2);
        jPanel2.setBounds(50, 50, 890, 510);

        fon.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/fondo.reproductor.jpg"))); // NOI18N
        jPanel1.add(fon);
        fon.setBounds(0, 0, 990, 620);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 992, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 619, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtDuracionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtDuracionActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtDuracionActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
JFileChooser selector = new JFileChooser();
    FileNameExtensionFilter filtro = new FileNameExtensionFilter("Archivos MP3", "mp3");
    selector.setFileFilter(filtro);

    int resultado = selector.showOpenDialog(this);

    if (resultado == JFileChooser.APPROVE_OPTION) {
        archivoSeleccionado = selector.getSelectedFile();
        // Opcional: imprimir en consola para verificar
        System.out.println("Archivo cargado: " + archivoSeleccionado.getName());
    }
  
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
    try {
        Conexion.conexion cc = new Conexion.conexion();
        Connection cn = cc.conectar();
        
        // Sentencia SQL corregida según tu tabla
        String sql = "INSERT INTO canciones (NOMBRE, ARTISTA, DURACION, GENERO, CANCION) VALUES (?, ?, ?, ?, ?)";
        PreparedStatement pst = cn.prepareStatement(sql);
        
        pst.setString(1, txtNombre.getText());
        pst.setString(2, txtArtista.getText());
        pst.setString(3, txtDuracion.getText());
        pst.setString(4, cbGenero.getSelectedItem().toString());
        
        FileInputStream fis = new FileInputStream(archivoSeleccionado);
        pst.setBinaryStream(5, fis, (int) archivoSeleccionado.length());
        
        int n = pst.executeUpdate(); // Ejecutamos la inserción
        
        if (n > 0) {
            // LLAMADA CLAVE: Limpia la tabla y muestra solo el último de la DB
            mostrarUltimoAgregado(); 
            
            JOptionPane.showMessageDialog(null, "¡Canción guardada con éxito!");
            
            // Ahora limpiamos los campos para el siguiente registro
            txtNombre.setText("");
            txtArtista.setText("");
            txtDuracion.setText("");
            archivoSeleccionado = null;
        }
    } catch (Exception e) {
        JOptionPane.showMessageDialog(null, "Error al guardar: " + e.getMessage());
    }

    }//GEN-LAST:event_jButton1ActionPerformed
private void SetImageLabel(JLabel labelName, String root) {
    ImageIcon image = new ImageIcon(root);
    Icon icon = new ImageIcon(image.getImage().getScaledInstance(labelName.getWidth(), labelName.getHeight(), Image.SCALE_DEFAULT));
    labelName.setIcon(icon);
    this.repaint();
}
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
            java.util.logging.Logger.getLogger(Agregar_cancion.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Agregar_cancion.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Agregar_cancion.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Agregar_cancion.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Agregar_cancion().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTable Tabla;
    private javax.swing.JComboBox<String> cbGenero;
    private javax.swing.JLabel fon;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JTextField txtArtista;
    private javax.swing.JTextField txtDuracion;
    private javax.swing.JTextField txtNombre;
    // End of variables declaration//GEN-END:variables
}
