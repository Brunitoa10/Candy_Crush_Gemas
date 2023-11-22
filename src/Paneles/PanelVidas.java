package Paneles;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Insets;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;



public class PanelVidas extends JPanel implements Paneles {

    private CentralPaneles miCentral;
    private JLabel label_corazon1 = new JLabel();
	private JLabel label_corazon2 = new JLabel();
	private JLabel label_corazon3 = new JLabel();

    public PanelVidas(CentralPaneles mi_central) {
        miCentral = mi_central;
    }

    @Override
    public void crearPanel() {
        setLayout(new GridLayout(1,3,5,5));
		setBackground(new Color(0,0,0,0));
    }

    @Override
    public void agregarAPanelPrincipal() {
        ImageIcon imgIconCorazon = new ImageIcon(this.getClass().getResource("/assets/nivel/corazon.png"));
        Image imgEscaladaCorazon = imgIconCorazon.getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH);
        Icon iconoEscaladoCorazon = new ImageIcon(imgEscaladaCorazon);
        
        label_corazon1.setIcon(iconoEscaladoCorazon);
        label_corazon3.setIcon(iconoEscaladoCorazon);
        label_corazon2.setIcon(iconoEscaladoCorazon);


        add(label_corazon1);
        add(label_corazon2);
        add(label_corazon3);
        GridBagConstraints gbc = new GridBagConstraints();
		gbc.insets = new Insets(0,0,10,0);
        miCentral.agregarConGBCs(this, gbc, 6, 6, 2, 1); 
    }

    public void actualizarVidas() {
		ImageIcon imgIconCorazonVacio = new ImageIcon(this.getClass().getResource("/assets/nivel/corazonVacio.png"));
		Image imgEscaladaCorazonVacio = imgIconCorazonVacio.getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH);
		Icon iconoEscaladoCorazonVacio = new ImageIcon(imgEscaladaCorazonVacio);
		

		if(miCentral.getVidas() == 2) {
			label_corazon3.setIcon(iconoEscaladoCorazonVacio);
		} else if(miCentral.getVidas() == 1) {
				label_corazon2.setIcon(iconoEscaladoCorazonVacio);
				label_corazon3.setIcon(iconoEscaladoCorazonVacio);
			}

		GridBagConstraints gbc = new GridBagConstraints();
		gbc.weightx = 0;
		gbc.weightx = 0;
		gbc.insets = new Insets(0,0,0,10);
		miCentral.agregarConGBCs(this, gbc, 6, 6, 2, 1); 
	}
}
