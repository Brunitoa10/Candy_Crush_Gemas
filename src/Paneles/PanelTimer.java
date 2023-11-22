package Paneles;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.Insets;

import javax.swing.JLabel;
import javax.swing.JPanel;


public class PanelTimer extends JPanel implements Paneles {

    private CentralPaneles miCentral;
    private JLabel timerLabel;

    public PanelTimer(CentralPaneles mi_central) {
        miCentral = mi_central;
    }

    @Override
    public void crearPanel() {
        setBackground(new Color(0,0,0,220));
        timerLabel = new JLabel("Tiempo restante: "+ agregarPaddingTiempo(miCentral.getTiempoRestante()));
		timerLabel.setFont(new Font("Algerian", Font.PLAIN, 20));
		timerLabel.setForeground(Color.WHITE);
        add(timerLabel);
    }

    @Override
    public void agregarAPanelPrincipal() {
		GridBagConstraints c = new GridBagConstraints();
		c.insets = new Insets(0,10,0,0);
		miCentral.agregarConGBCs(this, c, 0, 0, 3, 1);
    }
    
    public void actualizarTimer(int tiempo) {	
	    timerLabel.setText("Tiempo restante: " + agregarPaddingTiempo(tiempo));
		repaint();
        revalidate();
	}
	
	private String agregarPaddingTiempo(int tiempo) {
		String padding = "";
			if(tiempo <= 9) {
			padding = "00";
			} else if(tiempo <= 99) {
				padding = "0";
			}
		return padding+tiempo;
	}
}
