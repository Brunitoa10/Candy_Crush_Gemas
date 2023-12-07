package GUI;

import java.awt.BorderLayout;
import java.awt.Toolkit;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import java.awt.Dimension;
import javax.swing.JFrame;
import javax.swing.JLabel;

import javax.swing.JPanel;

import Logica.EntidadLogica;
import Logica.Logica;

import Threads.CentralAnimaciones;

public class GUI extends JFrame implements VentanaAnimable, VentanaNotificable, VentanaJuego {

	private Logica miLogica;
	private int filas, columnas;
	private int animacionesPendientes;
	private int sizeLabel = 70;
	private boolean bloquearIntercambios;
	private CentralAnimaciones centralAnimaciones;
	private JPanel panelPrincipal;
	private ImagenFondo fondo = new ImagenFondo();
	private ConfiguracionTeclado configuracionTeclado;

	public static final int ARRIBA = 15000;
	public static final int ABAJO = 15001;
	public static final int IZQUIERDA = 15002;
	public static final int DERECHA = 15003;

	private JLabel textoSuperior;

	public GUI(Logica juego, int filas, int columnas) {
		miLogica = juego;
		centralAnimaciones = new CentralAnimaciones(this);
		this.filas = filas;
		this.columnas = columnas;
		animacionesPendientes = 0;
		bloquearIntercambios = false;
		configuracionTeclado = new ConfiguracionTeclado(miLogica, this);

		crearComponentesGraficos();
	}

	// Métodos y operaciones de las interfaces omitidos por brevedad

	protected void crearComponentesGraficos() {
		setTitle("TdP 2023 :: Candy Crush BRUNO");
		setSize(new Dimension(Toolkit.getDefaultToolkit().getScreenSize()));
		setResizable(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLayout(new BorderLayout());

		textoSuperior = new JLabel("Esta es una versión mejorada del proyecto grupal de tdp");

		panelPrincipal = new JPanel();
		panelPrincipal.setSize(sizeLabel * filas * 2, sizeLabel * columnas * 2);
		panelPrincipal.setLayout(null);

		getContentPane().add(panelPrincipal, BorderLayout.CENTER);
		getContentPane().add(textoSuperior, BorderLayout.NORTH);

		configurarAccionesTeclado();
		panelPrincipal.setFocusable(true);

	}

	private void configurarAccionesTeclado() {
		panelPrincipal.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				Runnable accion = configuracionTeclado.obtenerAccion(e.getKeyCode());
				if (accion != null) {
					repaint();
					accion.run();
				}
			}
		});
	}

	public void cambiarFondo(int nivel) {
		fondo.cambiarFondo(nivel);
	}

	public boolean getBloquear_intercambios() {
		return bloquearIntercambios;
	}

	public EntidadGrafica agregar_entidad(EntidadLogica e) {
		Celda celda = new Celda(this, e, sizeLabel, e.get_visibilidad());
		panelPrincipal.add(celda);
		return celda;
	}

	public int getFilas() {
		return filas;
	}

	public int getColumnas() {
		return columnas;
	}

	public int getHeight() {
		return panelPrincipal.getHeight();
	}

	public int getWidth() {
		return panelPrincipal.getWidth();
	}

	/*
	 * public EntidadGrafica agregar_entidad(EntidadLogica e) {
	 * 
	 * int x = (panelPrincipal.getWidth() - sizeLabel * filas) / 2 + e.get_columna()
	 * * sizeLabel;
	 * int y = (panelPrincipal.getHeight() - sizeLabel * columnas) / 2 +
	 * e.get_fila() * sizeLabel;
	 * 
	 * Celda celda = new Celda(this, e, sizeLabel, e.get_visibilidad());
	 * celda.setBounds(x, y, sizeLabel, sizeLabel);
	 * 
	 * panelPrincipal.add(celda);
	 * return celda;
	 * }
	 */

	public void ocultar() {
		this.setVisible(false);
	}

	public void mostrar() {
		this.setVisible(true);
	}

	public void transicionar() {
		// To DO. Podría servir para mostrar información o carteles.
	}

	// Operaciones para Ventana Animable (Ventana <-- Celda)

	public void animar_intercambio(Celda celda) {
		centralAnimaciones.animar_intercambio(celda);
	}

	public void animar_cambio_foco(Celda celda) {
		centralAnimaciones.animar_cambio_foco(celda);
	}

	public void animar_detonacion(Celda celda) {
		centralAnimaciones.animar_detonacion(celda);
	}

	public void animar_caida(Celda celda) {
		centralAnimaciones.animar_caida(celda);
	}

	public void animar_cambio_visibilidad(Celda celda) {
		centralAnimaciones.animar_cambio_visibilidad(celda);
	}

	public void eliminar_celda(Celda celda) {
		panelPrincipal.remove(celda);
		panelPrincipal.repaint();
	}

	// Operaciones para Ventana Notificable (Ventana <-- CentralDeAnimaciones)

	public void notificarse_animacion_en_progreso() {
		synchronized (this) {
			animacionesPendientes++;
			bloquearIntercambios = true;
		}
	}

	public void notificarse_animacion_finalizada() {
		synchronized (this) {
			animacionesPendientes--;
			bloquearIntercambios = animacionesPendientes > 0;
		}
	}

	@Override
	public void resetear(Logica logica, int filas, int columnas) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Unimplemented method 'resetear'");
	}

}