package Paneles;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.GridBagConstraints;

import javax.swing.JLabel;
import javax.swing.JPanel;


public class PanelScore extends JPanel implements Paneles {

    private CentralPaneles miCentral;

    public PanelScore(CentralPaneles mi_central) {
        miCentral = mi_central;
    }

    @Override
    public void crearPanel() {
        setBackground(new Color(0,0,0,200));

		JLabel scoreLabel = new JLabel("SCORE: 0000");
		scoreLabel.setFont(new Font("Algerian", Font.PLAIN, 40));
		scoreLabel.setForeground(Color.WHITE);
		add(scoreLabel);
    }

    @Override
    public void agregarAPanelPrincipal() {
        GridBagConstraints gbc = new GridBagConstraints();
        miCentral.agregarConGBCs((Component)this, gbc, 2, 0, 4, 1);
    }
    
    
}
