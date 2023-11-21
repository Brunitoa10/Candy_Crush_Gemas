package Paneles;

import java.awt.GridBagConstraints;
import java.awt.Image;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;


public class PanelControles extends JPanel implements Paneles {

    private CentralPaneles miCentral;

    public PanelControles(CentralPaneles mi_central) {
        miCentral = mi_central;
    }

    @Override
    public void crearPanel() {
        GridBagConstraints gbc = new GridBagConstraints();
        miCentral.agregarConGBCs(this,gbc, 0, 6, 5, 1);
    }

    @Override
    public void agregarAPanelPrincipal() {
		setOpaque(false);
		
		ImageIcon icono_imagen = new ImageIcon(this.getClass().getResource("/assets/controles/arrow_keys.png"));
		Image imagen_escalada = icono_imagen.getImage().getScaledInstance(120, 30, Image.SCALE_REPLICATE);
		Icon icono_escalado = new ImageIcon(imagen_escalada);

		JLabel label_flechas = new JLabel();

		label_flechas.setIcon(icono_escalado);
		add(label_flechas);
		repaint();

		JLabel textoMoverse = miCentral.crearLabel("Mover", 25);
		add(textoMoverse);
		
		icono_imagen = new ImageIcon(this.getClass().getResource("/assets/controles/WASD.png"));
		imagen_escalada = icono_imagen.getImage().getScaledInstance(120, 30, Image.SCALE_REPLICATE);
		icono_escalado = new ImageIcon(imagen_escalada);

		JLabel label_WASD = new JLabel();

		label_WASD.setIcon(icono_escalado);
		add(label_WASD);
		repaint();

		JLabel textoIntercambiar =  miCentral.crearLabel("Intercambiar", 25);
		add(textoIntercambiar);

		icono_imagen = new ImageIcon(this.getClass().getResource("/assets/controles/R.png"));
		imagen_escalada = icono_imagen.getImage().getScaledInstance(30, 30, Image.SCALE_REPLICATE);
		icono_escalado = new ImageIcon(imagen_escalada);

		JLabel label_R = new JLabel();

		label_R.setIcon(icono_escalado);
		add(label_R);
		repaint();

		JLabel textoVerPuntajes = miCentral.crearLabel("Ver mejores puntajes", 25);
		add(textoVerPuntajes);

	}
}
