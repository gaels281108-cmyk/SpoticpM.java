
package Tablas;
   import javax.swing.*;
import java.awt.*;
import java.util.Random;
public class VisualizadorGrafico extends JPanel {
 


    // Estas son las "magnitudes" (alturas de las barras)
    private int[] magnitudes = new int[20]; 
    private int energiaAudio = 0;
    private Random rnd = new Random();
    private Timer timer;

    public VisualizadorGrafico() {
        setBackground(Color.BLACK); 
        // Timer de 20ms para que sea rápido y fluido
        timer = new Timer(20, e -> {
            actualizar();
            repaint();
        });
    }

    public void iniciar() { timer.start(); }
public void detener() { 
    timer.stop();
    // Ponemos todas las magnitudes en 0 para que las barras bajen al suelo
    for (int i = 0; i < magnitudes.length; i++) {
        magnitudes[i] = 0;
    }
    repaint(); 
}
    // Este método recibe la "fuerza" de la música desde GestionAudio
    public void setEnergia(int nivel) {
    // Si la energía es muy baja, le damos un empujón para que siempre haya movimiento
    this.energiaAudio = (nivel < 10) ? nivel + 15 : nivel;
    }
    
private void actualizar() {
    for (int i = 0; i < magnitudes.length; i++) {
        // Usamos el indice 'i' para que cada barra reaccione diferente
        // El Math.sin crea una curva natural, no una linea recta aburrida
        double variacionOndulada = Math.sin(System.currentTimeMillis() / 100.0 + i) * 10;
        int alturaBase = energiaAudio + (int)variacionOndulada;
        
        // Aplicamos un multiplicador aleatorio pequeño para el "ruido" visual
        int alturaFinal = (int)(alturaBase * (0.8 + rnd.nextDouble() * 0.4));

        if (alturaFinal > magnitudes[i]) {
            magnitudes[i] = Math.min(getHeight(), alturaFinal);
        } else {
            magnitudes[i] = Math.max(0, magnitudes[i] - 6); // Gravedad suave
        }
    }
}
    

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(new Color(57, 255, 20)); // Verde neón
        int anchoBarra = getWidth() / magnitudes.length;
        for (int i = 0; i < magnitudes.length; i++) {
            int x = i * anchoBarra;
            int y = getHeight() - magnitudes[i];
            g.fillRect(x + 2, y, anchoBarra - 4, magnitudes[i]);
        }
    }
}

