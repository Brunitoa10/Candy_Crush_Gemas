package Paneles;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.JLabel;
import javax.swing.JPanel;



public class PanelMovimiento extends JPanel implements Paneles {

    private CentralPaneles miCentral;
    private int movimientosRestantes;
    private JLabel movimientosLabel;
    
    public PanelMovimiento(CentralPaneles mi_central) {
        miCentral = mi_central;
        movimientosRestantes = miCentral.getCantidadDeMovimientos();
    }

    @Override
    public void crearPanel() {
		setBackground(new Color(0,0,0,200));

		movimientosLabel = new JLabel ("Movimientos restantes: "+movimientosRestantes);
		movimientosLabel.setFont(new Font("Algerian", Font.PLAIN, 20));
		movimientosLabel.setForeground(Color.WHITE);

		add(movimientosLabel);
    }

    @Override
    public void agregarAPanelPrincipal() {


        GridBagConstraints gbc = new GridBagConstraints();
        //Constraints MOVIMIENTOS
		gbc.weightx = 0;
		gbc.weightx = 0;
		gbc.insets = new Insets(0,0,0,10);
		miCentral.agregarConGBCs(this, gbc, 6, 0, 2, 1); 
    }

    public void actualizarMovimientos(int movimientosRestantes) {
		movimientosLabel.setText("Movimientos restantes: "+movimientosRestantes);
	}
}
