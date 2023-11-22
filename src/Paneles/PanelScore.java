package Paneles;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.Insets;

import javax.swing.JLabel;
import javax.swing.JPanel;


public class PanelScore extends JPanel implements Paneles {

    private CentralPaneles miCentral;
    private JLabel scoreLabel;

    public PanelScore(CentralPaneles mi_central) {
        miCentral = mi_central;
    }

    @Override
    public void crearPanel() {
        setBackground(new Color(0,0,0,200));

		scoreLabel = new JLabel("SCORE: 0000");
		scoreLabel.setFont(new Font("Algerian", Font.PLAIN, 40));
		scoreLabel.setForeground(Color.WHITE);
		add(scoreLabel);
    }

    @Override
    public void agregarAPanelPrincipal() {
        GridBagConstraints gbc = new GridBagConstraints();
		gbc.insets = new Insets(10,0,0,0);
        miCentral.agregarConGBCs((Component)this, gbc, 2, 0, 4, 1);
    }
    
    public void actualizarScore(int score)
    {
        scoreLabel.setText("SCORE: "+ agregarPadding(score));
    }

    private String agregarPadding(int score) {
		String padding ="";
		if(score <= 9) {
			padding = "000";
			} else if(score <= 99) {
				padding = "00";
			}	else if(score <= 999) {
				padding = "0";
			}

		return padding + score;
	}
}
