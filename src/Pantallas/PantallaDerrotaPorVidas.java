package Pantallas;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;


public class PantallaDerrotaPorVidas extends JPanel implements Pantalla {
    CentralPantallas miCentral;

    public PantallaDerrotaPorVidas(CentralPantallas mi_central) {
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

	    JLabel labelPerdiste1 = miCentral.crearLabel("PERDISTE", 50);
	    miCentral.agregarConGBCs(labelPerdiste1, this, gbc, 0, 0, 1, 1);

	    JLabel labelPerdiste2 = miCentral.crearLabel("Te quedaste sin vidas", 30);
	    miCentral.agregarConGBCs(labelPerdiste2, this, gbc, 0, 1, 1, 1);

	    JLabel labelPerdiste3 = miCentral.crearLabel("Vuelve a empezar desde el nivel 1", 30);
	    miCentral.agregarConGBCs(labelPerdiste3, this, gbc, 0, 2, 1, 1);

        JButton botonReiniciar = miCentral.crearBoton("Reiniciar");
	    miCentral.agregarConGBCs(botonReiniciar, this, gbc, 0, 3, 1, 1);
        miCentral.agregarFuncionalidadBotonReiniciarDeCero(this, botonReiniciar);
	}

    public void setVisibilidad(boolean vis) {
        setVisible(vis);
    }
    
}
