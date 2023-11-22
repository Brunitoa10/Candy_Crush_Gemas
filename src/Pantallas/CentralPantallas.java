package Pantallas;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import GUI.GUI;
import Logica.Logica;
import Score.Jugador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.PriorityQueue;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.Insets;



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
		miLogica.pausarTiempo();
	    PantallaDerrotaPorMovimientos mensajePanel = new PantallaDerrotaPorMovimientos(this);
	    mensajePanel.crearPantalla(Frame.MAXIMIZED_HORIZ, Frame.MAXIMIZED_HORIZ);
		mensajePanel.agregarComponentes();

	    miGUI.getContentPane().add(mensajePanel);

	    mensajePanel.setVisibilidad(true);
	    mensajePanel.revalidate();
	}

	public void mostrarMensajeDerrotaPorTiempo() {
	    panelPrincipal.setVisible(false);
		miLogica.pausarTiempo();

	    PantallaDerrotaPorTiempo mensajePanel = new PantallaDerrotaPorTiempo(this);
	    mensajePanel.crearPantalla(Frame.MAXIMIZED_HORIZ, Frame.MAXIMIZED_HORIZ);
		mensajePanel.agregarComponentes();

	    miGUI.getContentPane().add(mensajePanel);

	    mensajePanel.setVisibilidad(true);
	    mensajePanel.revalidate();
	}

	public void mostrarMensajeFinDelJuego() {
	    panelPrincipal.setVisible(false);
		miLogica.pausarTiempo();

	    PantallaFinDelJuego mensajePanel = new PantallaFinDelJuego(this);
	    mensajePanel.crearPantalla(Frame.MAXIMIZED_HORIZ, Frame.MAXIMIZED_HORIZ);
		mensajePanel.agregarComponentes();

	    miGUI.getContentPane().add(mensajePanel);

	    mensajePanel.setVisibilidad(true);
	    mensajePanel.revalidate();
	}

	public void mostrarMensajeDerrotaPorVidas() {
	    panelPrincipal.setVisible(false);
		miLogica.pausarTiempo();

	    PantallaDerrotaPorVidas mensajePanel = new PantallaDerrotaPorVidas(this);
	    mensajePanel.crearPantalla(Frame.MAXIMIZED_HORIZ, Frame.MAXIMIZED_HORIZ);
		mensajePanel.agregarComponentes();

	    miGUI.getContentPane().add(mensajePanel);

	    mensajePanel.setVisibilidad(true);
	    mensajePanel.revalidate();
	}

	public void mostrarMensajeVictoriaPorObjetivos() {
	    panelPrincipal.setVisible(false);
		miLogica.pausarTiempo();
	    PantallaVictoriaPorObjetivos mensajePanel = new PantallaVictoriaPorObjetivos(this);
	    mensajePanel.crearPantalla(Frame.MAXIMIZED_HORIZ, Frame.MAXIMIZED_HORIZ);
		mensajePanel.agregarComponentes();

	    miGUI.getContentPane().add(mensajePanel);

	    mensajePanel.setVisibilidad(true);
	    mensajePanel.revalidate();
	}

	public void mostrarModosDeJuego() {
	    panelPrincipal.setVisible(false);
		miLogica.pausarTiempo();
	    PantallaModosDeJuegos mensajePanel = new PantallaModosDeJuegos(this);
	    mensajePanel.crearPantalla(Frame.MAXIMIZED_HORIZ, Frame.MAXIMIZED_HORIZ);
		mensajePanel.agregarComponentes();

	    miGUI.getContentPane().add(mensajePanel);

	    mensajePanel.setVisibilidad(true);
	    mensajePanel.revalidate();
	}

	public void mostrarPuntajes() {
		miLogica.pausarTiempo();
	    panelPrincipal.setVisible(false);

	    PantallaPuntajes mensajePanel = new PantallaPuntajes(this);
	    mensajePanel.crearPantalla(Frame.MAXIMIZED_HORIZ, Frame.MAXIMIZED_HORIZ);
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

	public JLabel crearLabelConColor(String texto, String fuente, int estilo, int tamano, Color color, int gridwidth, int gridheight) {
	    JLabel label = new JLabel(texto);
	    label.setFont(new Font(fuente, estilo, tamano));
	    label.setForeground(color);

	    GridBagConstraints c = new GridBagConstraints();
	    c.insets = new Insets(0, 20, 0, 0);
	    c.weightx = 0;
	    c.gridwidth = gridwidth;
	    c.gridheight = gridheight;

	    return label;
	}

    public JButton crearBoton(String texto) {
	    JButton boton = new JButton(texto);
	    boton.setBackground(new Color(0, 0, 0, 200));
	    boton.setForeground(Color.WHITE);
	    return boton;
	}

	public PriorityQueue<Jugador> obtenerListadeJugadores() {
		return miGUI.obtenerListadeJugadores();
	}

	public void agregarFuncionalidadSkinOriginal(Pantalla p, JButton botonOriginal) {
		botonOriginal.addActionListener(new ActionListener() {
	        @Override
	        public void actionPerformed(ActionEvent e) {
				miLogica.set_skin("original");
				panelPrincipal.setVisible(true);
				p.setVisibilidad(false);
				panelPrincipal.repaint();
				panelPrincipal.revalidate();
	            miLogica.inicializarJuego();
	        }
	    });
	}

	public void agregarFuncionalidadSkinHalloween(Pantalla p, JButton botonHalloween) {
		botonHalloween.addActionListener(new ActionListener() {
	        @Override
	        public void actionPerformed(ActionEvent e) {
				miLogica.set_skin("halloween");
				panelPrincipal.setVisible(true);
				p.setVisibilidad(false);
	            miLogica.inicializarJuego();
	        }
	    });
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
					miLogica.reanudarTiempo();
					p.setVisibilidad(false);
					miLogica.iniciarSiguienteNivel();
				}
			});
	}

	public void agregarFuncionalidadBotonVolver(Pantalla p, JButton botonVolver) {
		botonVolver.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					panelPrincipal.setVisible(true);
					p.setVisibilidad(false);
					miLogica.reanudarTiempo();
				}
			});
	}
}
