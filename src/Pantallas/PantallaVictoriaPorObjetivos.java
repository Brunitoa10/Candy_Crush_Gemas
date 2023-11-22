package Pantallas;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;


public class PantallaVictoriaPorObjetivos extends JPanel implements Pantalla {
    CentralPantallas miCentral;

    public PantallaVictoriaPorObjetivos(CentralPantallas mi_central) {
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

	    JLabel labelGanaste1 = miCentral.crearLabel("GANASTE", 50);
	    miCentral.agregarConGBCs(labelGanaste1, this, gbc, 0, 0, 1, 1);

	    JLabel labelGanaste2 = miCentral.crearLabel("Felicidades! Avanza al siguiente nivel", 30);
	    miCentral.agregarConGBCs(labelGanaste2, this, gbc, 0, 1, 1, 1);

	    JButton botonSiguienteNivel = miCentral.crearBoton("Siguiente Nivel");
	    miCentral.agregarConGBCs(botonSiguienteNivel, this, gbc, 0, 3, 1, 1);

        miCentral.agregarFuncionalidadBotonSiguienteNivel(this, botonSiguienteNivel);
	}

    public void setVisibilidad(boolean vis) {
        setVisible(vis);
    }
    
	public void refrescar() {
        this.repaint();
        this.revalidate();
    }
}
