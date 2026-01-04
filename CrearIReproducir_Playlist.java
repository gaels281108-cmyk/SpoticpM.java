/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Tablas;

import com.mysql.jdbc.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.HashSet;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;
/**
 *
 * @author Usuario
 */
public class CrearIReproducir_Playlist extends javax.swing.JFrame {
int idPlaylistSeleccionada = -1;
String nombrePlaylistSeleccionada = "";
String modoTabla = "PLAYLISTS"; // Puede ser "PLAYLISTS" o "CANCIONES"
    public CrearIReproducir_Playlist() {
        initComponents();
        setSize(1120, 572);
        mostrarCancionesDisponibles();
    }
public void mostrarCancionesDisponibles() {
DefaultTableModel modelo = new DefaultTableModel() {
    @Override
    public boolean isCellEditable(int row, int column) {
        return false; // Esto hace que TODAS las celdas sean no editables
    }
};    modelo.addColumn("ID");
    modelo.addColumn("NOMBRE");
    modelo.addColumn("ARTISTA");
    modelo.addColumn("DURACION");
    modelo.addColumn("GENERO");

    jTable1.setModel(modelo);

    String sql = "SELECT ID_CANCION, NOMBRE, ARTISTA, DURACION, GENERO FROM canciones";
    try {
        Conexion.conexion cc = new Conexion.conexion();
        Connection cn = cc.conectar();
        Statement st = cn.createStatement();
        ResultSet rs = st.executeQuery(sql);

        while (rs.next()) {
            Object[] fila = new Object[5];
            fila[0] = rs.getInt(1);
            fila[1] = rs.getString(2);
            fila[2] = rs.getString(3);
            fila[3] = rs.getString(4);
            fila[4] = rs.getString(5);

            modelo.addRow(fila);
        }
        cn.close();
    } catch (Exception e) {
        System.out.println("Error al cargar canciones: " + e.getMessage());
    }
}

public void mostrarPlaylistsUsuario() {
    // 1. Definir el modelo y LIMPIAR la tabla
DefaultTableModel modelo = new DefaultTableModel() {
    @Override
    public boolean isCellEditable(int row, int column) {
        return false; // Esto hace que TODAS las celdas sean no editables
    }
};    modelo.addColumn("ID_PLAYLIST");
    modelo.addColumn("NOMBRE PLAYLIST");
    modelo.addColumn("CANCIONES");
    jTable1.setModel(modelo); // Al asignar un nuevo modelo vacío, la tabla se limpia automáticamente

    // 2. Consulta SQL filtrando por el ID del usuario logueado
    // Usamos la variable estática que definimos en el Login
    String sql = "SELECT ID_PLAYLIST, NOMBRE, CANCIONES FROM playlist WHERE ID_USUARIO = " + Login.idUsuarioLogueado;

    try {
        Conexion.conexion cc = new Conexion.conexion();
        java.sql.Connection cn = cc.conectar();
        java.sql.Statement st = cn.createStatement();
        java.sql.ResultSet rs = st.executeQuery(sql);

        // 3. Llenar la tabla con los resultados
        String[] datos = new String[3];
        while (rs.next()) {
            datos[0] = rs.getString(1); // ID_PLAYLIST
            datos[1] = rs.getString(2); // NOMBRE
            datos[2] = rs.getString(3); // CANCIONES (La lista de IDs)
            modelo.addRow(datos);
        }
        
        cn.close();
    } catch (Exception e) {
        javax.swing.JOptionPane.showMessageDialog(null, "Error al mostrar playlists: " + e.getMessage());
    }
}
public void actualizarPlaylist(String idCancion, String accion) {
    try {
        Conexion.conexion cc = new Conexion.conexion();
        Connection cn = cc.conectar();
        
        // 1. Obtener canciones actuales
        String sqlSelect = "SELECT CANCIONES FROM playlist WHERE ID_PLAYLIST = " + idPlaylistSeleccionada;
        Statement st = cn.createStatement();
        ResultSet rs = st.executeQuery(sqlSelect);
        
        if (rs.next()) {
            String cancionesActuales = rs.getString("CANCIONES");
            List<String> listaIds = new ArrayList<>(Arrays.asList(cancionesActuales.split(",")));

            if (accion.equals("AGREGAR")) {
                if (!listaIds.contains(idCancion)) listaIds.add(idCancion);
            } else if (accion.equals("ELIMINAR")) {
                listaIds.remove(idCancion);
            }

            String nuevoStringIds = String.join(",", listaIds);

            // 2. Guardar cambios en la BD
            String sqlUpdate = "UPDATE playlist SET CANCIONES = ? WHERE ID_PLAYLIST = ?";
            PreparedStatement pst = cn.prepareStatement(sqlUpdate);
            pst.setString(1, nuevoStringIds);
            pst.setInt(2, idPlaylistSeleccionada);
            
            pst.executeUpdate();
            mostrarNombresCancionesEnArea(nuevoStringIds); // Refrescar jTextArea1
        }
        cn.close();
    } catch (Exception e) {
        JOptionPane.showMessageDialog(null, "Error: " + e.getMessage());
    }
}

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        nombre = new javax.swing.JTextField();
        jSeparator4 = new javax.swing.JSeparator();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();
        jLabel5 = new javax.swing.JLabel();
        lblNombrePlaylist = new javax.swing.JLabel();
        jButton2 = new javax.swing.JButton();
        verplaylist = new javax.swing.JButton();
        vercanciones = new javax.swing.JButton();
        Agregar = new javax.swing.JButton();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jLabel9 = new javax.swing.JLabel();
        jSeparator5 = new javax.swing.JSeparator();
        jSeparator6 = new javax.swing.JSeparator();
        jSeparator7 = new javax.swing.JSeparator();
        jButton3 = new javax.swing.JButton();
        fondo = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setLayout(null);

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        jTable1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTable1MouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(jTable1);

        jPanel1.add(jScrollPane1);
        jScrollPane1.setBounds(30, 50, 1070, 220);

        nombre.setBorder(null);
        nombre.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                nombreMousePressed(evt);
            }
        });
        nombre.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                nombreActionPerformed(evt);
            }
        });
        jPanel1.add(nombre);
        nombre.setBounds(760, 390, 320, 40);

        jSeparator4.setBackground(new java.awt.Color(0, 0, 0));
        jPanel1.add(jSeparator4);
        jSeparator4.setBounds(610, 340, 510, 20);

        jTextArea1.setEditable(false);
        jTextArea1.setColumns(20);
        jTextArea1.setRows(5);
        jScrollPane2.setViewportView(jTextArea1);

        jPanel1.add(jScrollPane2);
        jScrollPane2.setBounds(20, 370, 460, 150);

        jLabel5.setFont(new java.awt.Font("Times New Roman", 0, 13)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel5.setText("Playlist Seleccionada");
        jLabel5.setToolTipText("");
        jPanel1.add(jLabel5);
        jLabel5.setBounds(20, 320, 240, 20);

        lblNombrePlaylist.setFont(new java.awt.Font("Tempus Sans ITC", 0, 17)); // NOI18N
        lblNombrePlaylist.setForeground(new java.awt.Color(255, 255, 255));
        lblNombrePlaylist.setToolTipText("");
        lblNombrePlaylist.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        jPanel1.add(lblNombrePlaylist);
        lblNombrePlaylist.setBounds(20, 340, 240, 30);

        jButton2.setFont(new java.awt.Font("Times New Roman", 0, 13)); // NOI18N
        jButton2.setForeground(new java.awt.Color(255, 255, 255));
        jButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/upload_11012696.png"))); // NOI18N
        jButton2.setText("<html>Guardar <br>PlayList<html>");
        jButton2.setToolTipText("");
        jButton2.setContentAreaFilled(false);
        jButton2.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jButton2.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButton2.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        jPanel1.add(jButton2);
        jButton2.setBounds(780, 470, 70, 70);

        verplaylist.setFont(new java.awt.Font("Times New Roman", 0, 13)); // NOI18N
        verplaylist.setText("Ver Playlist");
        verplaylist.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                verplaylistActionPerformed(evt);
            }
        });
        jPanel1.add(verplaylist);
        verplaylist.setBounds(30, 30, 170, 20);

        vercanciones.setFont(new java.awt.Font("Times New Roman", 0, 13)); // NOI18N
        vercanciones.setText("Ver Cancion");
        vercanciones.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                vercancionesActionPerformed(evt);
            }
        });
        jPanel1.add(vercanciones);
        vercanciones.setBounds(30, 30, 170, 20);

        Agregar.setFont(new java.awt.Font("Times New Roman", 0, 13)); // NOI18N
        Agregar.setText("Agregar Cancion");
        Agregar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AgregarActionPerformed(evt);
            }
        });
        jPanel1.add(Agregar);
        Agregar.setBounds(960, 25, 140, 30);

        jLabel7.setFont(new java.awt.Font("Tempus Sans ITC", 0, 17)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(255, 255, 255));
        jLabel7.setText("<html>Nombre de<br>la Playlist</html>.");
        jLabel7.setToolTipText("");
        jPanel1.add(jLabel7);
        jLabel7.setBounds(640, 390, 110, 40);

        jLabel8.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(255, 255, 255));
        jLabel8.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel8.setText("Crear Playlist");
        jLabel8.setToolTipText("");
        jPanel1.add(jLabel8);
        jLabel8.setBounds(610, 290, 510, 50);

        jButton1.setFont(new java.awt.Font("Times New Roman", 0, 13)); // NOI18N
        jButton1.setForeground(new java.awt.Color(255, 255, 255));
        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/remove_11012647.png"))); // NOI18N
        jButton1.setText("<html>Eliminar <br>Playlist<html>");
        jButton1.setContentAreaFilled(false);
        jButton1.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jButton1.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButton1.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        jPanel1.add(jButton1);
        jButton1.setBounds(920, 470, 70, 70);

        jLabel9.setFont(new java.awt.Font("Times New Roman", 0, 13)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(255, 255, 255));
        jLabel9.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel9.setText("Canciones de la playlist");
        jLabel9.setToolTipText("");
        jPanel1.add(jLabel9);
        jLabel9.setBounds(260, 350, 220, 20);

        jSeparator5.setBackground(new java.awt.Color(0, 0, 0));
        jSeparator5.setOrientation(javax.swing.SwingConstants.VERTICAL);
        jPanel1.add(jSeparator5);
        jSeparator5.setBounds(610, 270, 20, 300);

        jSeparator6.setBackground(new java.awt.Color(0, 0, 0));
        jPanel1.add(jSeparator6);
        jSeparator6.setBounds(610, 290, 520, 20);

        jSeparator7.setBackground(new java.awt.Color(0, 0, 0));
        jPanel1.add(jSeparator7);
        jSeparator7.setBounds(0, 290, 610, 20);

        jButton3.setFont(new java.awt.Font("Times New Roman", 0, 13)); // NOI18N
        jButton3.setForeground(new java.awt.Color(255, 255, 255));
        jButton3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/remove_11012647.png"))); // NOI18N
        jButton3.setText("<html>Eliminar <br>cancion<html>");
        jButton3.setContentAreaFilled(false);
        jButton3.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jButton3.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButton3.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });
        jPanel1.add(jButton3);
        jButton3.setBounds(510, 400, 70, 70);

        fondo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/fondo.reproductor.jpg"))); // NOI18N
        jPanel1.add(fondo);
        fondo.setBounds(0, 0, 1120, 570);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 1126, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 572, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void nombreMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_nombreMousePressed

        // TODO add your handling code here:
    }//GEN-LAST:event_nombreMousePressed

    private void nombreActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_nombreActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_nombreActionPerformed

    private void verplaylistActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_verplaylistActionPerformed
   verplaylist.setVisible(false);
        vercanciones.setVisible(true);
        mostrarPlaylistsUsuario();
        Agregar.setVisible(false);
        modoTabla = "PLAYLISTS";
    }//GEN-LAST:event_verplaylistActionPerformed

    private void vercancionesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_vercancionesActionPerformed
   vercanciones.setVisible(false);
        verplaylist.setVisible(true);
mostrarCancionesDisponibles();
Agregar.setVisible(true);
modoTabla = "CANCIONES";
        // TODO add your handling code here:
    }//GEN-LAST:event_vercancionesActionPerformed

    private void AgregarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AgregarActionPerformed
Agregar_cancion j=new Agregar_cancion();
j.setVisible(true);
this.dispose();
    }//GEN-LAST:event_AgregarActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
String nombrePL = nombre.getText(); // El JTextField de "Nombre de la Playlist"
    
    // Obtenemos las canciones seleccionadas en la tabla (puedes seleccionar varias con Ctrl)
    int[] filasSeleccionadas = jTable1.getSelectedRows();
    String cancionesIds = "";

    if (nombrePL.isEmpty() || filasSeleccionadas.length == 0) {
        JOptionPane.showMessageDialog(null, "Escribe un nombre y selecciona al menos una canción");
        return;
    }

    // Unimos los IDs de las canciones en un String (ej: "1,4,7") para la columna CANCIONES
    for (int i = 0; i < filasSeleccionadas.length; i++) {
        cancionesIds += jTable1.getValueAt(filasSeleccionadas[i], 0).toString();
        if (i < filasSeleccionadas.length - 1) cancionesIds += ",";
    }

    try {
        Conexion.conexion cc = new Conexion.conexion();
        Connection cn = cc.conectar();
        
        // La tabla playlist tiene: ID_PLAYLIST, ID_USUARIO, NOMBRE, CANCIONES
        String sql = "INSERT INTO playlist (ID_USUARIO, NOMBRE, CANCIONES) VALUES (?, ?, ?)";
        
        PreparedStatement pst = cn.prepareStatement(sql);
        pst.setInt(1, Login.idUsuarioLogueado); // Usamos el ID del usuario que entró al sistema
        pst.setString(2, nombrePL);
        pst.setString(3, cancionesIds);

        int n = pst.executeUpdate();
        if (n > 0) {
            JOptionPane.showMessageDialog(null, "¡Playlist '" + nombrePL + "' creada con éxito!");
            nombre.setText("");
        }
        cn.close();
    } catch (Exception e) {
        JOptionPane.showMessageDialog(null, "Error al guardar playlist: " + e.getMessage());
    }
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jTable1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable1MouseClicked
    int fila = jTable1.getSelectedRow();
    if (fila == -1) return;

    // Si modoTabla no es "CANCIONES", por defecto tratará de seleccionar una Playlist
    if (!modoTabla.equals("CANCIONES")) { 
        try {
            // Guardamos ID y Nombre de la playlist seleccionada
            idPlaylistSeleccionada = Integer.parseInt(jTable1.getValueAt(fila, 0).toString());
            nombrePlaylistSeleccionada = jTable1.getValueAt(fila, 1).toString();
            
            // Actualizamos la interfaz
            lblNombrePlaylist.setText("Playlist: " + nombrePlaylistSeleccionada);
            
            // Cargamos las canciones en el área blanca
            String ids = jTable1.getValueAt(fila, 2).toString();
            mostrarNombresCancionesEnArea(ids);
            
        } catch (Exception e) {
            System.out.println("Error al seleccionar playlist: " + e.getMessage());
        }
    } else {
        // Aquí va el código de "Deseas agregar esta canción..." que ya tenías
    }
    }//GEN-LAST:event_jTable1MouseClicked

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
// 1. Verificar que haya una playlist seleccionada
    if (idPlaylistSeleccionada == -1) {
        javax.swing.JOptionPane.showMessageDialog(null, "Por favor, selecciona primero una playlist de la tabla.");
        return;
    }

    // 2. Confirmar la eliminación con el usuario
    int confirmacion = javax.swing.JOptionPane.showConfirmDialog(null, 
        "¿Estás seguro de que deseas eliminar la playlist '" + nombrePlaylistSeleccionada + "'? Esta acción no se puede deshacer.", 
        "Confirmar eliminación", javax.swing.JOptionPane.YES_NO_OPTION);

    if (confirmacion == javax.swing.JOptionPane.YES_OPTION) {
        try {
            Conexion.conexion cc = new Conexion.conexion();
            java.sql.Connection cn = cc.conectar();
            
            // 3. Ejecutar el DELETE en la base de datos
            String sql = "DELETE FROM playlist WHERE ID_PLAYLIST = ?";
            java.sql.PreparedStatement pst = cn.prepareStatement(sql);
            pst.setInt(1, idPlaylistSeleccionada);
            
            int n = pst.executeUpdate();
            
            if (n > 0) {
                javax.swing.JOptionPane.showMessageDialog(null, "Playlist eliminada correctamente.");
                
                // 4. Limpiar la interfaz después de eliminar
                idPlaylistSeleccionada = -1;
                nombrePlaylistSeleccionada = "";
                lblNombrePlaylist.setText("Ninguna playlist seleccionada");
                jTextArea1.setText("");
                
                // 5. Refrescar la tabla para que ya no aparezca la playlist borrada
                mostrarPlaylistsUsuario(); 
            }
            cn.close();
        } catch (Exception e) {
            javax.swing.JOptionPane.showMessageDialog(null, "Error al eliminar: " + e.getMessage());
        }
    }
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
if (idPlaylistSeleccionada == -1) {
        javax.swing.JOptionPane.showMessageDialog(null, "Seleccione primero una playlist en la tabla.");
        return;
    }

    // Pedimos el ID de la canción que aparece en el área de texto
    String idAEliminar = javax.swing.JOptionPane.showInputDialog(null, 
            "Escriba el ID de la canción que desea quitar de: " + nombrePlaylistSeleccionada);

    if (idAEliminar != null && !idAEliminar.isEmpty()) {
        // Llamamos al método que ya tienes para actualizar, pero con la acción ELIMINAR
        actualizarPlaylist(idAEliminar, "ELIMINAR");
        javax.swing.JOptionPane.showMessageDialog(null, "Proceso de eliminación finalizado.");
    }    }//GEN-LAST:event_jButton3ActionPerformed
public void mostrarNombresCancionesEnArea(String ids) {
    jTextArea1.setText(""); // Limpiar area
    if (ids.isEmpty()) return;
    
    try {
        Conexion.conexion cc = new Conexion.conexion();
        Connection cn = cc.conectar();
        // Consulta usando IN para traer todos los nombres de los IDs en la lista
        String sql = "SELECT NOMBRE FROM canciones WHERE ID_CANCION IN (" + ids + ")";
        Statement st = cn.createStatement();
        ResultSet rs = st.executeQuery(sql);
        
        while (rs.next()) {
jTextArea1.append("- " + rs.getString("NOMBRE") + "\n");
        }
        cn.close();
    } catch (Exception e) { }
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
            java.util.logging.Logger.getLogger(CrearIReproducir_Playlist.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(CrearIReproducir_Playlist.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(CrearIReproducir_Playlist.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(CrearIReproducir_Playlist.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new CrearIReproducir_Playlist().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton Agregar;
    private javax.swing.JLabel fondo;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JSeparator jSeparator4;
    private javax.swing.JSeparator jSeparator5;
    private javax.swing.JSeparator jSeparator6;
    private javax.swing.JSeparator jSeparator7;
    private javax.swing.JTable jTable1;
    private javax.swing.JTextArea jTextArea1;
    private javax.swing.JLabel lblNombrePlaylist;
    private javax.swing.JTextField nombre;
    private javax.swing.JButton vercanciones;
    private javax.swing.JButton verplaylist;
    // End of variables declaration//GEN-END:variables
}
