package GUI;

import java.awt.Graphics;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

public class ImagenFondo extends JPanel {
    private static final long serialVersionUID = 4180857344385898634L;

    private Image imagen;
    private int nivelActual = 1;

    public void paint(Graphics g) {
        imagen = new ImageIcon(getClass().getResource("/assets/nivel/" + nivelActual + "_Nivel.png")).getImage();
        g.drawImage(imagen, 0, 0, getWidth(), getHeight(), this);
        super.paint(g);
    }

    public void cambiarFondo(int nivel) {
        nivelActual = nivel;
        repaint(); // Vuelve a pintar el fondo con el nuevo nivel
    }
}
