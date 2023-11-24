package Paneles;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import javax.swing.JLabel;
import javax.swing.JPanel;



public class PanelReglas extends JPanel implements Paneles {

    private CentralPaneles miCentral;
    
    public PanelReglas(CentralPaneles mi_central) {
        miCentral = mi_central;
    }

    @Override
    public void crearPanel() {
		setSize(100,100);
		setLayout(new GridBagLayout());
		setBackground(new Color(0,0,0,200));
    }

    @Override
    public void agregarAPanelPrincipal() {
        mostrarReglas();
        GridBagConstraints gbc = new GridBagConstraints();
		gbc.weightx = 0;
		gbc.weightx = 0;
		gbc.insets = new Insets(0,0,0,10);
		miCentral.agregarConGBCs(this, gbc, 6, 1, 2, 1); 
    }

    public void mostrarReglas() {   
	    JLabel tituloObjetivo = miCentral.crearLabelConColor(" REGLAS:", "Algerian", Font.PLAIN, 20, Color.RED, 2, 1);
	    GridBagConstraints c = new GridBagConstraints();
	    c.insets = new Insets(0, 0, 0, 0);      
	    c.gridx = 0;                               
	    c.gridy = 0;
	    add(tituloObjetivo, c);

		for(int i=0; i < miCentral.getEstrategias().size() ; i++) {
			int y = 1;
			String[] textoRegla = miCentral.getNombresEstrategiasEnUso();
			JLabel regla = miCentral.crearLabelConColor(textoRegla[i], "Algerian", Font.PLAIN, 20, Color.WHITE, 2, 1);
			c.gridy = y;
	    	add(regla, c);
			y++;
		}
	}
}
