package Tablas;
import Tablas.VisualizadorGrafico;
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javazoom.jl.player.Player;
import javax.swing.JOptionPane;
import java.io.BufferedInputStream;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.io.*;
import java.sql.*;
import javazoom.jl.decoder.*;
import javazoom.jl.player.AudioDevice;
import javazoom.jl.player.JavaSoundAudioDevice;
import javax.sound.sampled.*;
import javazoom.jl.decoder.*;
import javazoom.jl.player.AudioDevice;
import javazoom.jl.player.FactoryRegistry;

public class GestionAudio {
    private int energiaAudio = 0;
    private VisualizadorGrafico visualizador;
    private long bytesGuardados = 0; // Para recordar dónde se quedó la pausa
    private SourceDataLine line;
    private long totalLength;
    private long pauseLocation;
public static int idCancionGlobal = 0;
    private javazoom.jl.player.Player player;
    private float volumenActual = 0.5f; // Valor entre 0.0 y 1.0
    private InputStream input;
    private boolean isPaused = false;
    private static final javazoom.jl.decoder.Equalizer eq = new javazoom.jl.decoder.Equalizer();

    public void controlarReproduccion() {
        if (!isPaused && player == null) {
            iniciarDesdeCero();
        } else if (isPaused) {
            reanudar();
        } else {
            pausar();
        }
    }

   // Variable nueva para el control de volumen real
private FloatControl volumeControl;

private void iniciarDesdeCero() {
    try {
        Conexion.conexion cc = new Conexion.conexion();
        Connection cn = cc.conectar();
        String sql = "SELECT CANCION FROM canciones WHERE ID_CANCION = ?";
        PreparedStatement pst = cn.prepareStatement(sql);
        pst.setInt(1, idCancionGlobal);
        ResultSet rs = pst.executeQuery();

        if (rs.next()) {
            input = rs.getBinaryStream("CANCION");
            totalLength = input.available();
            
            // Creamos el dispositivo de audio capturando la línea para el volumen
            JavaSoundAudioDevice device = new JavaSoundAudioDevice();
            player = new javazoom.jl.player.Player(new BufferedInputStream(input), device);
            
            new Thread(() -> {
                try {
                    // LLAMADA CLAVE: Buscamos el control de volumen justo cuando empieza
                    vincularControlVolumen();
                    player.play();
                } catch (Exception e) { System.out.println(e); }
            }).start();
        }
        cn.close();
    } catch (Exception e) { System.out.println(e); }
}

private void vincularControlVolumen() {
    try {
        // Buscamos la línea de salida del sistema para manipularla
        Mixer.Info[] mixers = AudioSystem.getMixerInfo();
        for (Mixer.Info mixerInfo : mixers) {
            Mixer mixer = AudioSystem.getMixer(mixerInfo);
            Line[] lines = mixer.getSourceLines();
            for (Line line : lines) {
                if (line.isControlSupported(FloatControl.Type.MASTER_GAIN)) {
                    volumeControl = (FloatControl) line.getControl(FloatControl.Type.MASTER_GAIN);
                    // Aplicamos el volumen que tenga el slider actualmente
                    ajustarVolumen(volumenActual * 100); 
                }
            }
        }
    } catch (Exception e) { }
}
   
    // Agrega esto al final de tu clase GestionAudio
public boolean isPaused() {
    return isPaused;
}
// En GestionAudio.java
public void reiniciar() {
    detener();
    iniciarDesdeCero();
}
// En GestionAudio.java
public int obtenerPosicionMilisegundos() {
    if (player != null) {
        // Retorna la posición actual en milisegundos desde que empezó a sonar
        return player.getPosition();
    }
    return 0;
}
public void prepararReproduccion() {
    // 1. Limpieza total antes de empezar una nueva pista
    if (visualizador != null) {
        visualizador.detener();
        visualizador.setEnergia(0);
    }

    try {
        Conexion.conexion cc = new Conexion.conexion();
        Connection cn = cc.conectar();
        String sql = "SELECT CANCION FROM canciones WHERE ID_CANCION = ?";
        PreparedStatement pst = cn.prepareStatement(sql);
        pst.setInt(1, idCancionGlobal);
        ResultSet rs = pst.executeQuery();

        if (rs.next()) {
            input = rs.getBinaryStream("CANCION");
            totalLength = input.available();
            BufferedInputStream bis = new BufferedInputStream(input);

            javazoom.jl.player.JavaSoundAudioDevice device = new javazoom.jl.player.JavaSoundAudioDevice();
            player = new javazoom.jl.player.Player(bis, device);
            ajustarVolumen(volumenActual * 100); 

            new Thread(() -> {
                try {
                    if (visualizador != null) visualizador.iniciar();

                    // Hilo de control de barras
                    new Thread(() -> {
                        try {
                            // Este bucle se rompe si el player cambia (siguiente canción) o es null (pausa)
                            while (player != null && !player.isComplete()) {
                                if (visualizador != null) {
                                    // Sincronía única: Posición + Bajos del Ecualizador
                                    int nivel = (player.getPosition() % 60) + (int)(eq.getBand(0) * 25);
                                    visualizador.setEnergia(nivel);
                                }
                                Thread.sleep(20);
                            }
                            if (visualizador != null) visualizador.detener();
                        } catch (Exception ex) {}
                    }).start();

                    player.play();
                } catch (Exception e) {
                    System.out.println("Error en reproducción: " + e);
                }
            }).start();
        }
        cn.close();
    } catch (Exception e) {
        System.out.println("Error en prepararReproduccion: " + e);
    }
}

public void ajustarVolumen(float valor) {
    // Guardamos el valor en escala 0.0 a 1.0
    this.volumenActual = valor / 100f;

    try {
        // En lugar de buscar en todo el sistema, intentamos 
        // obtener el control de la línea maestra de salida
        javax.sound.sampled.Port lineOut = (javax.sound.sampled.Port) 
            javax.sound.sampled.AudioSystem.getLine(javax.sound.sampled.Port.Info.SPEAKER);
        
        if (lineOut != null) {
            lineOut.open();
            if (lineOut.isControlSupported(javax.sound.sampled.FloatControl.Type.VOLUME)) {
                javax.sound.sampled.FloatControl volCtrl = (javax.sound.sampled.FloatControl) 
                    lineOut.getControl(javax.sound.sampled.FloatControl.Type.VOLUME);
                volCtrl.setValue(this.volumenActual);
            }
        }
    } catch (Exception e) {
        // Si el control de hardware falla, el volumen se gestionará 
        // a través de la variable volumenActual en el reproductor
        System.out.println("Aviso: Usando control de volumen por software.");
    }
}

    public void detener() {
        if (player != null) {
            player.close();
            player = null;
            
        }
    }
    
    public boolean isPased() { return isPaused; }
    
   // Asegúrate de que esta variable esté declarada al inicio de tu clase

public void ajustarBanda(int tipo, float ganancia) {
    // Multiplicamos el efecto para que sea exagerado (Factor de 0.0 a 3.0)
    float factor = 1.0f + (ganancia / 5.0f); 

    if (tipo == 0) { // GRAVES (Bandas 0 a 7)
        for (int i = 0; i < 8; i++) eq.setBand(i, factor);
    } 
    else if (tipo == 1) { // MEDIOS (Bandas 8 a 16)
        for (int i = 8; i < 17; i++) eq.setBand(i, factor);
    } 
    else if (tipo == 2) { // AGUDOS (Bandas 17 a 31)
        for (int i = 17; i < 32; i++) eq.setBand(i, factor);
    }
}

   public int obtenerPorcentajeProgreso() {
    try {
        if (input != null && totalLength > 0) {
            long disponibles = input.available(); // Cuánto falta por sonar
            long reproducido = totalLength - disponibles; // Cuánto ya sonó
            
            // Cálculo del porcentaje (0 a 100)
            return (int) ((reproducido * 100) / totalLength);
        }
    } catch (Exception e) { 
        return 0; 
    }
    return 0;
}
  public void cambiarPosicion(int porcentaje, float valorSliderVolumen) {
    detener(); // Detenemos la reproducción actual
    try {
        Conexion.conexion cc = new Conexion.conexion();
        Connection cn = cc.conectar();
        
        String sql = "SELECT CANCION FROM canciones WHERE ID_CANCION = ?";
        PreparedStatement pst = cn.prepareStatement(sql);
        pst.setInt(1, idCancionGlobal);
        ResultSet rs = pst.executeQuery();

        if (rs.next()) {
            input = rs.getBinaryStream("CANCION");
            
            // Calculamos el salto de bytes
            if (totalLength > 0) {
                long bytesASaltar = (totalLength * porcentaje) / 100;
                input.skip(bytesASaltar);
            }
            
            BufferedInputStream bis = new BufferedInputStream(input);
            
            // Creamos el dispositivo de audio y el reproductor
            // IMPORTANTE: JavaSoundAudioDevice es el estándar compatible
            javazoom.jl.player.JavaSoundAudioDevice device = new javazoom.jl.player.JavaSoundAudioDevice();
            player = new javazoom.jl.player.Player(bis, device);

            // 2. CREACIÓN DEL REPRODUCTOR
            // Usamos el constructor de 2 parámetros que tu librería SÍ acepta
            // Sincronizamos el volumen ANTES de iniciar el hilo
            ajustarVolumen(valorSliderVolumen);

            new Thread(() -> {
                try {
                    // Pequeña pausa para que el hardware asimile el volumen bajo
                    Thread.sleep(20); 
                    player.play();
                } catch (Exception e) {
                    System.out.println("Error al reanudar: " + e);
                }
            }).start();
        }
        cn.close();
    } catch (Exception e) {
        System.out.println("Error en cambiarPosicion: " + e);
    }
}
   // Agrega esta variable arriba junto a las otras

// MÉTODO PARA PAUSAR
public void pausar() {
    try {
        // --- AGREGADO: Apaga el visualizador de inmediato ---
        if (visualizador != null) {
            visualizador.detener();
        }

        if (player != null && input != null) {
            // Tu lógica actual para guardar la posición
            bytesGuardados = totalLength - input.available();
            player.close();
            player = null;
            isPaused = true;
        }
    } catch (Exception e) {
        System.out.println("Error al pausar: " + e);
    }
}






public void reanudar() {
    detener(); // Tu limpieza preventiva
    try {
        Conexion.conexion cc = new Conexion.conexion();
        Connection cn = cc.conectar();
        String sql = "SELECT CANCION FROM canciones WHERE ID_CANCION = ?";
        PreparedStatement pst = cn.prepareStatement(sql);
        pst.setInt(1, idCancionGlobal);
        ResultSet rs = pst.executeQuery();

        if (rs.next()) {
            input = rs.getBinaryStream("CANCION");
            BufferedInputStream bis = new BufferedInputStream(input);

            if (bytesGuardados > 0) {
                input.skip(bytesGuardados); // Tu salto de bytes original
            }

            JavaSoundAudioDevice device = new JavaSoundAudioDevice();
            player = new javazoom.jl.player.Player(bis, device);

            // --- NUEVO: Hilo del visualizador para la reanudación ---
            new Thread(() -> {
                try {
                    if (visualizador != null) visualizador.iniciar();
                    while (player != null && !player.isComplete()) {
                        if (visualizador != null) {
                            // Cálculo de ritmo + EQ
                            int nivel = (player.getPosition() % 60) + (int)(eq.getBand(0) * 25);
                            visualizador.setEnergia(nivel);
                        }
                        Thread.sleep(20);
                    }
                } catch (Exception e) {}
            }).start();

            // Tu hilo original de reproducción
            new Thread(() -> {
                try {
                    ajustarVolumen(volumenActual * 100);
                    player.play();
                } catch (Exception e) { System.out.println(e); }
            }).start();

            isPaused = false;
        }
        cn.close();
    } catch (Exception e) { System.out.println("Error al reanudar: " + e); }
}



public void aplicarVolumenAlSistema(float valor) {
    // Convertimos 0-100 a escala 0.0-1.0
    float perimetro = valor / 100f;
    try {
        // En lugar de recorrer todos los mixers, pedimos la línea de altavoces directamente
        javax.sound.sampled.Port lineOut = (javax.sound.sampled.Port) javax.sound.sampled.AudioSystem.getLine(javax.sound.sampled.Port.Info.SPEAKER);
        lineOut.open();
        
        // Obtenemos el control de volumen
        if (lineOut.isControlSupported(javax.sound.sampled.FloatControl.Type.VOLUME)) {
            javax.sound.sampled.FloatControl volCtrl = (javax.sound.sampled.FloatControl) lineOut.getControl(javax.sound.sampled.FloatControl.Type.VOLUME);
            volCtrl.setValue(perimetro); // Aquí no usamos dB, usamos la escala 0-1
        }
    } catch (Exception e) {
        // Si falla el Port SPEAKER, intentamos con el Master Gain tradicional pero con el cast correcto
        try {
            javax.sound.sampled.Mixer.Info[] mixers = javax.sound.sampled.AudioSystem.getMixerInfo();
            for (javax.sound.sampled.Mixer.Info mixerInfo : mixers) {
                javax.sound.sampled.Mixer mixer = javax.sound.sampled.AudioSystem.getMixer(mixerInfo);
                for (javax.sound.sampled.Line line : mixer.getSourceLines()) {
                    if (line.isControlSupported(javax.sound.sampled.FloatControl.Type.MASTER_GAIN)) {
                        javax.sound.sampled.FloatControl gainControl = (javax.sound.sampled.FloatControl) line.getControl(javax.sound.sampled.FloatControl.Type.MASTER_GAIN);
                        float dB = (float) (Math.log10(perimetro > 0 ? perimetro : 0.0001) * 20.0);
                        gainControl.setValue(dB);
                    }
                }
            }
        } catch (Exception ex) {
            System.out.println("No se pudo sincronizar el volumen del hardware.");
        }
    }
}


// En las variables globales de GestionAudi
public void setVisualizador(VisualizadorGrafico v) {
    this.visualizador = v;
}


public String obtenerLetra(int id) {
    String letra = "No hay letra disponible para esta canción.";
    try {
        Conexion.conexion cc = new Conexion.conexion();
        Connection cn = cc.conectar();
        String sql = "SELECT LETRA FROM canciones WHERE ID_CANCION = ?";
        PreparedStatement pst = cn.prepareStatement(sql);
        pst.setInt(1, id);
        ResultSet rs = pst.executeQuery();

        if (rs.next()) {
            String resultado = rs.getString("LETRA");
            if (resultado != null && !resultado.isEmpty()) {
                letra = resultado;
            }
        }
        cn.close();
    } catch (Exception e) {
        System.out.println("Error al cargar letra: " + e);
    }
    return letra;
}
public void iniciarEfectosVisuales(javax.swing.JTextArea txtLyrics) {
    if (txtLyrics == null) return;

    // 1. CARGA INICIAL: Ponemos la letra antes de iniciar el hilo
    String letra = obtenerLetra(idCancionGlobal);
    txtLyrics.setText(letra);
    txtLyrics.setCaretPosition(0);

    new Thread(() -> {
        try {
            Thread.sleep(200); 

            // Intentamos obtener el scroll de forma segura
            javax.swing.JScrollPane scroll = null;
            if (txtLyrics.getParent() instanceof javax.swing.JViewport) {
                if (txtLyrics.getParent().getParent() instanceof javax.swing.JScrollPane) {
                    scroll = (javax.swing.JScrollPane) txtLyrics.getParent().getParent();
                }
            }
            
            int idAlEmpezar = idCancionGlobal;

            // Bucle principal
            while (player != null && !player.isComplete() && !isPaused && idAlEmpezar == idCancionGlobal) {
                
                // --- VISUALIZADOR ---
                if (visualizador != null) {
                    int nivel = (player.getPosition() % 60) + (int)(eq.getBand(0) * 25);
                    visualizador.setEnergia(nivel);
                }

                // --- MOVIMIENTO DEL SCROLL (Solo si se encontró el scroll) ---
                if (scroll != null && totalLength > 0 && input != null) {
                    try {
                        int bytesRestantes = input.available();
                        double porcentaje = 1.0 - ((double) bytesRestantes / (double) totalLength);
                        
                        int maxScroll = scroll.getVerticalScrollBar().getMaximum() - scroll.getVerticalScrollBar().getVisibleAmount();
                        
                        if (maxScroll > 0) {
                            int pos = (int) (maxScroll * porcentaje);
                            scroll.getVerticalScrollBar().setValue(pos);
                        }
                    } catch (Exception e) { /* Error silencioso en lectura */ }
                }

                Thread.sleep(100); 
            }
            
            if (player != null && player.isComplete() && visualizador != null) {
                visualizador.detener();
            }

        } catch (Exception e) {
            // Esto es lo que veías en tu consola. Ahora ya no debería "matar" la letra.
            System.out.println("Aviso en efectos (se mantiene la letra): " + e.getMessage());
        }
    }).start();
}
//------------------------------------------------------------------------------------------------------------------

}




// Agrega esto al final de tu clase GestionAudio

