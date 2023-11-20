package Pantallas;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.util.PriorityQueue;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import Score.Jugador;


public class PantallaPuntajes extends JPanel implements Pantalla {
    CentralPantallas miCentral;

    public PantallaPuntajes(CentralPantallas mi_central) {
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

        JLabel titulo_puntajes = miCentral.crearLabelConColor("MEJORES PUNTAJES:", "Algerian", Font.PLAIN, 40, Color.WHITE, 2, 1);
		miCentral.agregarConGBCs(titulo_puntajes, this, gbc, 0, 0, 3, 1);

		for(int i=0; i < obtenerListadeJugadores().size();i++) {
			JLabel label_num = miCentral.crearLabelConColor((i+1) +" - ", "Algerian", Font.PLAIN, 30, Color.WHITE, 2, 1);
			miCentral.agregarConGBCs(label_num, this, gbc, 0, i+1, 1, 1);

			JLabel label_nombre = miCentral.crearLabelConColor("AAA", "Algerian", Font.PLAIN, 30, Color.WHITE, 2, 1);
			miCentral.agregarConGBCs(label_nombre, this, gbc, 1, i+1, 1, 1);

			JLabel label_puntaje1 = miCentral.crearLabel("0000000", 30);
			miCentral.agregarConGBCs(label_puntaje1, this, gbc, 2, i+1, 1, 1);

			if(i==0) {
				label_num.setForeground(Color.YELLOW);
				label_nombre.setForeground(Color.YELLOW);
			} else  if (i==1) {
						label_num.setForeground(Color.GRAY);
						label_nombre.setForeground(Color.GRAY);
					} else  if (i==2) {
						label_num.setForeground(Color.ORANGE);
						label_nombre.setForeground(Color.ORANGE);
					}

			
			
		}


		

		/*JLabel label_puntaje1 = miCentral.crearLabel("0000000", 30);
		miCentral.agregarConGBCs(label_puntaje1, this, gbc, 2, 1, 1, 1);

		JLabel label_num2 = miCentral.crearLabelConColor("2 - ", "Algerian", Font.PLAIN, 30, Color.GRAY, 2, 1);
		miCentral.agregarConGBCs(label_num2, this, gbc, 0, 2, 1, 1);

		JLabel label_nombre2 = miCentral.crearLabelConColor("AAA", "Algerian", Font.PLAIN, 30, Color.GRAY, 2, 1);
		miCentral.agregarConGBCs(label_nombre2, this, gbc, 1, 2, 1, 1);

		JLabel label_puntaje2 = miCentral.crearLabel("0000000", 30);
		miCentral.agregarConGBCs(label_puntaje2, this, gbc, 2, 2, 1, 1);

		JLabel label_num3 = miCentral.crearLabelConColor("3 - ", "Algerian", Font.PLAIN, 30, Color.ORANGE, 2, 1);
		miCentral.agregarConGBCs(label_num3, this, gbc, 0, 3, 1, 1);

		JLabel label_nombre3 = miCentral.crearLabelConColor("AAA", "Algerian", Font.PLAIN, 30, Color.ORANGE, 2, 1);
		miCentral.agregarConGBCs(label_nombre3, this, gbc, 1, 3, 1, 1);

		JLabel label_puntaje3 = miCentral.crearLabel("0000000", 30);
		miCentral.agregarConGBCs(label_puntaje3, this, gbc, 2, 3, 1, 1);

		JLabel label_num4 = miCentral.crearLabelConColor("4 - ", "Algerian", Font.PLAIN, 30, Color.WHITE, 2, 1);
		miCentral.agregarConGBCs(label_num4, this, gbc, 0, 4, 1, 1);

		JLabel label_nombre4 = miCentral.crearLabelConColor("AAA", "Algerian", Font.PLAIN, 30, Color.white, 2, 1);
		miCentral.agregarConGBCs(label_nombre4, this,gbc, 1, 4, 1, 1);

		JLabel label_puntaje4 = miCentral.crearLabel("0000000", 30);
		miCentral.agregarConGBCs(label_puntaje4, this, gbc, 2, 4, 1, 1);

		JLabel label_num5 = miCentral.crearLabel("5 - ", 30);
		miCentral.agregarConGBCs(label_num5, this, gbc, 0, 5, 1, 1);

		JLabel label_nombre5 = miCentral.crearLabel("AAA", 30);
		miCentral.agregarConGBCs(label_nombre5, this, gbc, 1, 5, 1, 1);

		JLabel label_puntaje5 = miCentral.crearLabel("0000000", 30);
		miCentral.agregarConGBCs(label_puntaje5, this, gbc, 2, 5, 1, 1);*/

		JButton button_volver = new JButton("Volver");
		button_volver.setBackground(new Color(0, 0, 0, 200));
	    button_volver.setForeground(Color.WHITE);
		miCentral.agregarFuncionalidadBotonVolver(this, button_volver);
		miCentral.agregarConGBCs(button_volver, this, gbc, 0, 6, 3, 1);
	}

	public PriorityQueue<Jugador> obtenerListadeJugadores() {
		return miCentral.obtenerListadeJugadores();
	}

    public void setVisibilidad(boolean vis) {
        setVisible(vis);
    }
}
