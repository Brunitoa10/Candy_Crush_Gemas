package Pantallas;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;


public class PantallaModosDeJuegos extends JPanel implements Pantalla {
    CentralPantallas miCentral;

    public PantallaModosDeJuegos(CentralPantallas mi_central) {
        miCentral = mi_central;
    }

    @Override
    public void crearPantalla(int width, int height) {
	    setLayout(new GridBagLayout());
	    setSize(new Dimension(width,height));
	    setBackground(new Color(0, 0, 0, 120));
	}

    public void agregarComponentes() {
        GridBagConstraints gbc = new GridBagConstraints();
	    JLabel label_num2 = miCentral.crearLabel("Seleccionar modo de juego",30);
		miCentral.agregarConGBCs(label_num2, this, gbc, 0, 0, 3, 1);

		JButton imageButton = crearBotonConImagen("/assets/modosDeJuego/original.png");
		miCentral.agregarConGBCs(imageButton, this, gbc, 0, 1, 1, 1);
        miCentral.agregarFuncionalidadSkinOriginal(this, imageButton);

		JButton imageButton2 = crearBotonConImagen("/assets/modosDeJuego/halloween.png");
		miCentral.agregarConGBCs(imageButton2, this, gbc, 2, 1, 1, 1);
        miCentral.agregarFuncionalidadSkinHalloween(this, imageButton2);
	}

    public void setVisibilidad(boolean vis) {
        setVisible(vis);
    }
    
    private JButton crearBotonConImagen(String imagePath) {
        // Load the image and create an ImageIcon
        ImageIcon icono_imagen = new ImageIcon(this.getClass().getResource(imagePath));
		Image imagen_escalada = icono_imagen.getImage().getScaledInstance(500, 500, Image.SCALE_SMOOTH);
		Icon icono_imagen_escalado = new ImageIcon(imagen_escalada);

        // Create a JButton with the ImageIcon
        JButton boton = new JButton(icono_imagen_escalado);

        // Set the size of the button based on the size of the image
        boton.setSize(icono_imagen_escalado.getIconWidth(), icono_imagen_escalado.getIconHeight());
		boton.setOpaque(false);
		boton.setContentAreaFilled(false);
		boton.setFocusable(false);
		boton.setBorderPainted(false);
		boton.setRolloverEnabled(false);
		boton.setBackground(new Color(0,0,0,0));

        return boton;
    }
}
