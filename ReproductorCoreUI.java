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
import Tablas.GestionAudio;
import javax.swing.*;
import java.awt.*;
import java.util.Random;
/**
 *
 * @author Usuario
 */
public class ReproductorCoreUI extends javax.swing.JFrame {
GestionAudio gestorAudio = new GestionAudio();
boolean reproduciendo = false;
// Lista de IDs de la playlist y el índice para saber en cuál vamos

    public ReproductorCoreUI() {
    
        initComponents();

// 1. Crea el visualizador una sola vez
    VisualizadorGrafico miVisualizador = new VisualizadorGrafico();
    miVisualizador.setSize(jpVisualizador.getSize()); 

    // 2. Agrégalo a tu panel gris
    jpVisualizador.setLayout(new java.awt.BorderLayout());
    jpVisualizador.add(miVisualizador, java.awt.BorderLayout.CENTER);
    jpVisualizador.revalidate();

    // 3. Conéctalo al gestor de audio (Asegúrate de que 'gestorAudio' esté declarado arriba)
    gestorAudio.setVisualizador(miVisualizador); 
    

        // Quita el borde de enfoque (el cuadrito de puntos al hacer clic)
jsVolumen.setFocusable(false);
// Cambia el color de la "pista" o el fondo si lo deseas
jsVolumen.setOpaque(false);
        mostrarCanciones();
                          setSize(1283, 671);  // ancho, alto
               setLocationRelativeTo(null);
               SetImageLabel(fon, "src/Imagenes/fondo.reproductor.jpg");

    }
public void mostrarCanciones() {
DefaultTableModel modelo = new DefaultTableModel();
modelo.addColumn("ID");
    modelo.addColumn("Nombre");
    modelo.addColumn("Artista");
    modelo.addColumn("Duración");
    modelo.addColumn("Género");
    
    // Cambia "List_music" por el nombre real de tu tabla si es necesario
    List_music.setModel(modelo); 

    String sql = "SELECT ID_CANCION, NOMBRE, ARTISTA, DURACION, GENERO FROM canciones";
    String[] datos = new String[5];
    
    try {
        // Usamos tu clase conexion
        Conexion.conexion cc = new Conexion.conexion(); 
        Connection cn = cc.conectar();
        
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
        cn.close();
    } catch (SQLException e) {
        System.out.println("Error al cargar tabla: " + e.getMessage());
    }
}


// Variable para controlar el hilo de la barra

public void iniciarBarraProgreso() {
    reproduciendo = true;
    
    new Thread(() -> {
        while (reproduciendo) {
            try {
                // 1. Dormimos el hilo medio segundo para no saturar
                Thread.sleep(500); 
                
                // 2. Le preguntamos al gestor en qué porcentaje va
                int porcentaje = gestorAudio.obtenerPorcentajeProgreso();
                
                // 3. Movemos la barra visualmente
                jsProgreso.setValue(porcentaje);
                
                // Si llega al 100%, detenemos el hilo
                if (porcentaje >= 100) {
                    reproduciendo = false;
                    jsProgreso.setValue(0);
                    // Aquí podrías llamar al botón "Siguiente" automáticamente ;)
                }
                
            } catch (Exception e) { }
        }
    }).start();
}


public void mostrarPlaylistsUsuario() {
    // 1. Definir el modelo y LIMPIAR la tabla
DefaultTableModel modelo = new DefaultTableModel(); 
        modelo.addColumn("ID_PLAYLIST");
    modelo.addColumn("NOMBRE PLAYLIST");
    modelo.addColumn("CANCIONES");
    List_music.setModel(modelo); // Al asignar un nuevo modelo vacío, la tabla se limpia automáticamente

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

    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        List_music = new javax.swing.JTable();
        jPanel5 = new javax.swing.JPanel();
        jsAgudos = new javax.swing.JSlider();
        jsGraves = new javax.swing.JSlider();
        jsMedios = new javax.swing.JSlider();
        jpVisualizador = new javax.swing.JPanel();
        btnReproducir = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();
        jsVolumen = new javax.swing.JSlider();
        jsProgreso = new javax.swing.JSlider();
        jScrollPane2 = new javax.swing.JScrollPane();
        txtLyrics = new javax.swing.JTextArea();
        jLabel1 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        verplaylist = new javax.swing.JButton();
        vercanciones = new javax.swing.JButton();
        btnReproducirplaylist = new javax.swing.JButton();
        fon = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setLayout(null);

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));
        jPanel3.setLayout(null);

        jLabel2.setFont(new java.awt.Font("Times New Roman", 2, 13)); // NOI18N
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("Playlist");
        jLabel2.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jLabel2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel2MouseClicked(evt);
            }
        });
        jPanel3.add(jLabel2);
        jLabel2.setBounds(0, 0, 170, 20);

        jPanel1.add(jPanel3);
        jPanel3.setBounds(1000, 190, 170, 20);

        List_music.setBackground(null);
        List_music.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {}
            },
            new String [] {

            }
        ));
        jScrollPane1.setViewportView(List_music);

        jPanel1.add(jScrollPane1);
        jScrollPane1.setBounds(130, 80, 452, 220);

        jPanel5.setBackground(null);
        jPanel5.setLayout(null);

        jsAgudos.setMinimum(-10);
        jsAgudos.setOrientation(javax.swing.JSlider.VERTICAL);
        jsAgudos.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                jsAgudosStateChanged(evt);
            }
        });
        jPanel5.add(jsAgudos);
        jsAgudos.setBounds(300, 120, 30, 150);

        jsGraves.setMinimum(-10);
        jsGraves.setOrientation(javax.swing.JSlider.VERTICAL);
        jsGraves.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                jsGravesStateChanged(evt);
            }
        });
        jPanel5.add(jsGraves);
        jsGraves.setBounds(180, 120, 30, 150);

        jsMedios.setMinimum(-10);
        jsMedios.setOrientation(javax.swing.JSlider.VERTICAL);
        jsMedios.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                jsMediosStateChanged(evt);
            }
        });
        jPanel5.add(jsMedios);
        jsMedios.setBounds(240, 120, 30, 150);

        javax.swing.GroupLayout jpVisualizadorLayout = new javax.swing.GroupLayout(jpVisualizador);
        jpVisualizador.setLayout(jpVisualizadorLayout);
        jpVisualizadorLayout.setHorizontalGroup(
            jpVisualizadorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 460, Short.MAX_VALUE)
        );
        jpVisualizadorLayout.setVerticalGroup(
            jpVisualizadorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 90, Short.MAX_VALUE)
        );

        jPanel5.add(jpVisualizador);
        jpVisualizador.setBounds(20, 20, 460, 90);

        jPanel1.add(jPanel5);
        jPanel5.setBounds(670, 210, 500, 270);

        btnReproducir.setBackground(new java.awt.Color(255, 255, 255));
        btnReproducir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/play_1279827.png"))); // NOI18N
        btnReproducir.setToolTipText("");
        btnReproducir.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnReproducir.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);
        btnReproducir.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnReproducir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnReproducirActionPerformed(evt);
            }
        });
        jPanel1.add(btnReproducir);
        btnReproducir.setBounds(330, 370, 60, 41);

        jButton4.setBackground(new java.awt.Color(255, 255, 255));
        jButton4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/backward_1279813.png"))); // NOI18N
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });
        jPanel1.add(jButton4);
        jButton4.setBounds(200, 370, 79, 40);

        jButton5.setBackground(new java.awt.Color(255, 255, 255));
        jButton5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/forward_1279817.png"))); // NOI18N
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });
        jPanel1.add(jButton5);
        jButton5.setBounds(440, 370, 79, 40);

        jsVolumen.setBackground(new java.awt.Color(255, 255, 255));
        jsVolumen.setOrientation(javax.swing.JSlider.VERTICAL);
        jsVolumen.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                jsVolumenStateChanged(evt);
            }
        });
        jPanel1.add(jsVolumen);
        jsVolumen.setBounds(50, 130, 30, 400);

        jsProgreso.setBackground(null);
        jsProgreso.setValue(0);
        jsProgreso.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jsProgresoMousePressed(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                jsProgresoMouseReleased(evt);
            }
        });
        jPanel1.add(jsProgreso);
        jsProgreso.setBounds(130, 320, 450, 26);

        jScrollPane2.setToolTipText("");

        txtLyrics.setEditable(false);
        txtLyrics.setColumns(20);
        txtLyrics.setLineWrap(true);
        txtLyrics.setRows(5);
        txtLyrics.setWrapStyleWord(true);
        jScrollPane2.setViewportView(txtLyrics);

        jPanel1.add(jScrollPane2);
        jScrollPane2.setBounds(130, 470, 460, 138);

        jLabel1.setFont(new java.awt.Font("Times New Roman", 2, 13)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Play/Pausa");
        jLabel1.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jPanel1.add(jLabel1);
        jLabel1.setBounds(320, 410, 80, 20);

        jLabel3.setFont(new java.awt.Font("Times New Roman", 0, 16)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel3.setText("Ecualizador");
        jLabel3.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jPanel1.add(jLabel3);
        jLabel3.setBounds(680, 170, 120, 50);

        jLabel4.setFont(new java.awt.Font("Times New Roman", 2, 13)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel4.setText("Anterior");
        jLabel4.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jPanel1.add(jLabel4);
        jLabel4.setBounds(200, 410, 80, 20);

        jLabel5.setFont(new java.awt.Font("Times New Roman", 2, 13)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel5.setText("Siguiente");
        jLabel5.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jPanel1.add(jLabel5);
        jLabel5.setBounds(440, 410, 80, 20);

        jLabel6.setFont(new java.awt.Font("Times New Roman", 2, 13)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(255, 255, 255));
        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel6.setText("Lista de canciones");
        jLabel6.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jPanel1.add(jLabel6);
        jLabel6.setBounds(300, 60, 280, 20);

        jLabel7.setFont(new java.awt.Font("Times New Roman", 2, 13)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(255, 255, 255));
        jLabel7.setText("Letra de la cancion ");
        jLabel7.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jPanel1.add(jLabel7);
        jLabel7.setBounds(130, 440, 110, 20);

        jLabel8.setFont(new java.awt.Font("Times New Roman", 2, 13)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(255, 255, 255));
        jLabel8.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel8.setText("Volumen");
        jLabel8.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jPanel1.add(jLabel8);
        jLabel8.setBounds(0, 100, 130, 20);

        verplaylist.setFont(new java.awt.Font("Times New Roman", 0, 13)); // NOI18N
        verplaylist.setText("Ver Playlist");
        verplaylist.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                verplaylistActionPerformed(evt);
            }
        });
        jPanel1.add(verplaylist);
        verplaylist.setBounds(130, 60, 170, 20);

        vercanciones.setFont(new java.awt.Font("Times New Roman", 0, 13)); // NOI18N
        vercanciones.setText("Ver Cancion");
        vercanciones.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                vercancionesActionPerformed(evt);
            }
        });
        jPanel1.add(vercanciones);
        vercanciones.setBounds(130, 60, 170, 20);

        btnReproducirplaylist.setBackground(new java.awt.Color(255, 255, 255));
        btnReproducirplaylist.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/play_1279827.png"))); // NOI18N
        btnReproducirplaylist.setToolTipText("");
        btnReproducirplaylist.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnReproducirplaylist.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);
        btnReproducirplaylist.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnReproducirplaylist.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnReproducirplaylistActionPerformed(evt);
            }
        });
        jPanel1.add(btnReproducirplaylist);
        btnReproducirplaylist.setBounds(800, 30, 60, 41);

        fon.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/fondo.reproductor.jpg"))); // NOI18N
        jPanel1.add(fon);
        fon.setBounds(0, 0, 1280, 670);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 1283, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 671, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnReproducirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnReproducirActionPerformed
int fila = List_music.getSelectedRow();

    if (fila != -1) {
        int id = Integer.parseInt(List_music.getValueAt(fila, 0).toString());

        if (GestionAudio.idCancionGlobal != id) {
            gestorAudio.detener();
            GestionAudio.idCancionGlobal = id;
            gestorAudio.prepararReproduccion(); 
            
            // Iniciamos la letra
            gestorAudio.iniciarEfectosVisuales(txtLyrics);
            
            iniciarBarraProgreso();
            
            // Usamos el nombre exacto que aparece en tu carpeta: pause_1279821.png
            try {
                btnReproducir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/pause_1279821.png")));
            } catch (Exception e) { System.out.println("Error en imagen pause"); }
        
        } else {
            if (gestorAudio.isPaused()) {
                gestorAudio.reanudar(); 
                gestorAudio.iniciarEfectosVisuales(txtLyrics);
                
                try {
                    btnReproducir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/pause_1279821.png")));
                } catch (Exception e) { System.out.println("Error en imagen pause"); }
            } else {
                gestorAudio.pausar();
                
                // SEGÚN TU IMAGEN EL ARCHIVO ES: play_1279827.png (Termina en 27)
                // Verifícalo en tu carpeta 'Imagenes' a la izquierda del NetBeans
                try {
                    btnReproducir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/play_1279827.png")));
                } catch (Exception e) { 
                    System.out.println("Error en imagen play: " + e.getMessage()); 
                }}}}
            
        
    }//GEN-LAST:event_btnReproducirActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed

        int fila = List_music.getSelectedRow();

    // 2. Verificamos que no sea la primera canción (fila 0) para poder retroceder
    if (fila > 0) {
        // Restamos uno a la fila para seleccionar la de arriba
        int nuevaFila = fila - 1;
        List_music.setRowSelectionInterval(nuevaFila, nuevaFila);
        
        // Obtenemos el nuevo ID de esa fila
        int id = Integer.parseInt(List_music.getValueAt(nuevaFila, 0).toString());

        // 3. Ejecutamos la lógica de reproducción
        gestorAudio.detener();
        GestionAudio.idCancionGlobal = id;
        
        // Preparamos el audio y lanzamos los efectos de la letra
        gestorAudio.prepararReproduccion(); 
        gestorAudio.iniciarEfectosVisuales(txtLyrics);
        
        // Reiniciamos tu barra de progreso
        iniciarBarraProgreso();

        // 4. Cambiamos el icono a pausa (porque la canción empieza a sonar)
        // Usamos el nombre que confirmamos que funciona: pause_1279821.png
        try {
            btnReproducir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/pause_1279821.png")));
        } catch (Exception e) {
            System.out.println("Error al cargar icono: " + e.getMessage());
        }
        
    } else {
        // Opcional: Si ya es la primera canción, podemos reiniciar la misma canción desde el principio
        System.out.println("Ya estás en la primera canción de la lista.");
    }
        
    }//GEN-LAST:event_jButton4ActionPerformed
private void actualizarIcono(String ruta) {
    try {
        java.net.URL imgURL = getClass().getResource(ruta);
        if (imgURL != null) {
            btnReproducir.setIcon(new javax.swing.ImageIcon(imgURL));
        } else {
            System.err.println("No se pudo encontrar el archivo: " + ruta);
            // Si no encuentra la imagen, podrías poner un texto temporal en el botón
            // btnReproducir.setText("||"); 
        }
    } catch (Exception e) {
        System.err.println("Error al cargar el icono: " + e.getMessage());
    }
}
    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
int fila = List_music.getSelectedRow();
    int totalFilas = List_music.getRowCount();

    // Verificamos que no sea la última canción
    if (fila != -1 && fila < totalFilas - 1) {
        int nuevaFila = fila + 1;
        List_music.setRowSelectionInterval(nuevaFila, nuevaFila);
        
        int id = Integer.parseInt(List_music.getValueAt(nuevaFila, 0).toString());

        // Lógica de reproducción
        gestorAudio.detener();
        GestionAudio.idCancionGlobal = id;
        
        gestorAudio.prepararReproduccion(); 
        gestorAudio.iniciarEfectosVisuales(txtLyrics); // Inicia letra y visualizador
        
        iniciarBarraProgreso();

        // Icono de pausa (música iniciando)
        try {
            btnReproducir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/pause_1279821.png")));
        } catch (Exception e) {}
        
    } else {
        System.out.println("Llegaste al final de la playlist.");
    }

    }//GEN-LAST:event_jButton5ActionPerformed

    private void jsVolumenStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_jsVolumenStateChanged
float valor = (float) jsVolumen.getValue();
    
    // Solo enviamos el valor si el gestor está listo
    if (gestorAudio != null) {
        gestorAudio.ajustarVolumen(valor);
    }
    }//GEN-LAST:event_jsVolumenStateChanged

    private void jsGravesStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_jsGravesStateChanged
gestorAudio.ajustarBanda(0, (float) jsGraves.getValue());
    }//GEN-LAST:event_jsGravesStateChanged

    private void jsMediosStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_jsMediosStateChanged
gestorAudio.ajustarBanda(0, (float) jsMedios.getValue());
    }//GEN-LAST:event_jsMediosStateChanged

    private void jsAgudosStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_jsAgudosStateChanged
gestorAudio.ajustarBanda(0, (float) jsAgudos.getValue());
    }//GEN-LAST:event_jsAgudosStateChanged

    private void jsProgresoMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jsProgresoMousePressed
    reproduciendo = false;

    }//GEN-LAST:event_jsProgresoMousePressed

    private void jsProgresoMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jsProgresoMouseReleased
int posCancion = jsProgreso.getValue();
    
    // Le pasamos el valor actual del slider de volumen para que lo aplique al reanudar
    float volumenDelSlider = (float) jsVolumen.getValue(); 
    
    gestorAudio.cambiarPosicion(jsProgreso.getValue(), (float)jsVolumen.getValue());
    if (!reproduciendo) {
        iniciarBarraProgreso();
                    gestorAudio.iniciarEfectosVisuales(txtLyrics);
    }
    }//GEN-LAST:event_jsProgresoMouseReleased

    private void jLabel2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel2MouseClicked
        CrearIReproducir_Playlist n=new CrearIReproducir_Playlist();
        n.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_jLabel2MouseClicked

    private void verplaylistActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_verplaylistActionPerformed
        verplaylist.setVisible(false);
        vercanciones.setVisible(true);
        btnReproducir.setVisible(true);
        mostrarPlaylistsUsuario();
    }//GEN-LAST:event_verplaylistActionPerformed

    private void vercancionesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_vercancionesActionPerformed
        vercanciones.setVisible(false);
        btnReproducir.setVisible(false);
        verplaylist.setVisible(true);
        mostrarCanciones();
        // TODO add your handling code here:
    }//GEN-LAST:event_vercancionesActionPerformed

    private void btnReproducirplaylistActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnReproducirplaylistActionPerformed

        
        
    }//GEN-LAST:event_btnReproducirplaylistActionPerformed

    private void SetImageLabel(JLabel labelName, String root) {
    ImageIcon image = new ImageIcon(root);
    Icon icon = new ImageIcon(image.getImage().getScaledInstance(labelName.getWidth(), labelName.getHeight(), Image.SCALE_DEFAULT));
    labelName.setIcon(icon);
    this.repaint();
}
public void actualizarLetras() {
    // 1. Pedimos la letra al gestor usando el ID actual
    String textoLetra = gestorAudio.obtenerLetra(GestionAudio.idCancionGlobal);
    
    // 2. La ponemos en el JTextArea que creamos
    txtLyrics.setText(textoLetra);
    
    // 3. Opcional: Volver el scroll hacia arriba
    txtLyrics.setCaretPosition(0);
}
public void mostrarLetraActual() {
    // 1. Obtenemos la letra usando el ID global
    String letra = gestorAudio.obtenerLetra(GestionAudio.idCancionGlobal);
    
    // 2. La ponemos en el JTextArea
    txtLyrics.setText(letra);
    
    // 3. Importante: Reiniciamos el scroll al principio
    txtLyrics.setCaretPosition(0);
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
            java.util.logging.Logger.getLogger(ReproductorCoreUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ReproductorCoreUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ReproductorCoreUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ReproductorCoreUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new ReproductorCoreUI().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTable List_music;
    private javax.swing.JButton btnReproducir;
    private javax.swing.JButton btnReproducirplaylist;
    private javax.swing.JLabel fon;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JPanel jpVisualizador;
    private javax.swing.JSlider jsAgudos;
    private javax.swing.JSlider jsGraves;
    private javax.swing.JSlider jsMedios;
    private javax.swing.JSlider jsProgreso;
    private javax.swing.JSlider jsVolumen;
    private javax.swing.JTextArea txtLyrics;
    private javax.swing.JButton vercanciones;
    private javax.swing.JButton verplaylist;
    // End of variables declaration//GEN-END:variables
}
