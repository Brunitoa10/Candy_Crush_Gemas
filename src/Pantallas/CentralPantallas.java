package Pantallas;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import GUI.GUI;
import Logica.Logica;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;



public class CentralPantallas {
    protected JPanel panelPrincipal;
    protected GUI miGUI;
    protected Logica miLogica;

    public CentralPantallas(JPanel p_principal, GUI mi_GUI, Logica mi_logica) {
        panelPrincipal = p_principal;
        miGUI = mi_GUI;
        miLogica = mi_logica;
    }

    public void mostrarMensajeDerrotaPorMovimientos() {
	    panelPrincipal.setVisible(false);

	    PantallaDerrotaPorMovimientos mensajePanel = new PantallaDerrotaPorMovimientos(this);
	    mensajePanel.crearPantalla(miGUI.MAXIMIZED_HORIZ, miGUI.MAXIMIZED_HORIZ);
		mensajePanel.agregarComponentes();

	    miGUI.getContentPane().add(mensajePanel);

	    mensajePanel.setVisibilidad(true);
	    mensajePanel.revalidate();
	}

	public void mostrarMensajeDerrotaPorTiempo() {
	    panelPrincipal.setVisible(false);

	    PantallaDerrotaPorTiempo mensajePanel = new PantallaDerrotaPorTiempo(this);
	    mensajePanel.crearPantalla(miGUI.MAXIMIZED_HORIZ, miGUI.MAXIMIZED_HORIZ);
		mensajePanel.agregarComponentes();

	    miGUI.getContentPane().add(mensajePanel);

	    mensajePanel.setVisibilidad(true);
	    mensajePanel.revalidate();
	}

	public void mostrarMensajeVictoriaPorObjetivos() {
	    panelPrincipal.setVisible(false);

	    PantallaVictoriaPorObjetivos mensajePanel = new PantallaVictoriaPorObjetivos(this);
	    mensajePanel.crearPantalla(miGUI.MAXIMIZED_HORIZ, miGUI.MAXIMIZED_HORIZ);
		mensajePanel.agregarComponentes();

	    miGUI.getContentPane().add(mensajePanel);

	    mensajePanel.setVisibilidad(true);
	    mensajePanel.revalidate();
	}


    public void agregarConGBCs(Component componenteAAgregar, JPanel panelBase, GridBagConstraints gbc, int gridx, int gridy, int gridwidth,int gridheight) {
		gbc.gridx = gridx;
		gbc.gridy = gridy;
		gbc.gridwidth = gridwidth;
		gbc.gridheight = gridheight;

		panelBase.add(componenteAAgregar,gbc);
	}

    public JLabel crearLabel(String texto, int tamañoFuente) {
	    JLabel label = new JLabel(texto);
	    label.setFont(new Font("Algerian", Font.PLAIN, tamañoFuente));
	    label.setForeground(Color.WHITE);
	    return label;
	}

    public JButton crearBoton(String texto) {
	    JButton boton = new JButton(texto);
	    boton.setBackground(new Color(0, 0, 0, 200));
	    boton.setForeground(Color.WHITE);
	    return boton;
	}

	public void agregarFuncionalidadBotonReiniciar(Pantalla p, JButton botonReiniciar) {
		botonReiniciar.addActionListener(new ActionListener() {
	        @Override
	        public void actionPerformed(ActionEvent e) {
	            panelPrincipal.setVisible(true);
	            p.setVisibilidad(false);
	            miLogica.notificarDerrotaPorTiempo();
	        }
	    });
	}

	public void agregarFuncionalidadBotonSiguienteNivel(Pantalla p, JButton botonSiguienteNivel) {
			botonSiguienteNivel.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					panelPrincipal.setVisible(true);
					p.setVisibilidad(false);
					miLogica.cambiarNivel();
				}
			});
	}
}
