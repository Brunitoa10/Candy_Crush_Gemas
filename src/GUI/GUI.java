package GUI;



import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import Entidades.Entidad;
import Logica.EntidadLogica;
import Logica.Logica;
import Threads.AnimadorIntercambio;
import Threads.CentralAnimaciones;


public class GUI extends JFrame {
	protected Logica milogica;
	protected int filas,columnas;
	protected JPanel panel_principal;
	protected CentralAnimaciones mi_animador;
	protected int animaciones_pendientes;
	protected boolean bloquear_intercambios;
	
	private int size_label = 100;

	//Movimientos
	public static final int ARRIBA = 15000;
	public static final int ABAJO = 15001;
	public static final int IZQUIERDA = 15002;
	public static final int DERECHA = 15003;
	
	
	public GUI(Logica l, int f, int c) {
		milogica = l;
		filas = f;
		mi_animador = new CentralAnimaciones(this);
		columnas = c;

		animaciones_pendientes = 0;
		bloquear_intercambios = false;
		
		inicializar();
	}
	
	protected void inicializar() {
		setTitle("Proyecto Candy Crush - Comision-06");
		setExtendedState(JFrame.MAXIMIZED_BOTH); 
		setLocationRelativeTo(null);
		setUndecorated(true);
		setVisible(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLayout(new BorderLayout());
		
		
		panel_principal = new JPanel();
		panel_principal.setSize(size_label * filas, size_label * columnas);
		panel_principal.setLayout(null);
		
		panel_principal.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {	
				switch(e.getKeyCode()) {
					case KeyEvent.VK_LEFT: 	{ milogica.mover_jugador(IZQUIERDA); break; }
					case KeyEvent.VK_RIGHT: { milogica.mover_jugador(DERECHA); break; }
					case KeyEvent.VK_UP: 	{ milogica.mover_jugador(ARRIBA);break; }
					case KeyEvent.VK_DOWN: 	{ milogica.mover_jugador(ABAJO); break; }
					case KeyEvent.VK_W:		{ if (!bloquear_intercambios) milogica.intercambiar(ARRIBA); break; }
					case KeyEvent.VK_S:		{ if (!bloquear_intercambios) milogica.intercambiar(ABAJO); break; }
					case KeyEvent.VK_A:		{ if (!bloquear_intercambios) milogica.intercambiar(IZQUIERDA); break; }
					case KeyEvent.VK_D:		{ if (!bloquear_intercambios) milogica.intercambiar(DERECHA); break; } 
				}
			}
		});
		
		getContentPane().add(panel_principal, BorderLayout.CENTER);
		
		panel_principal.setFocusable(true);
	}
	
	public EntidadGrafica agregar_entidad(Entidad e) {
		Celda celda = new Celda(this, e, size_label);
		panel_principal.add(celda);
		return celda;
	}

	public void notificarse_animacion_en_progreso() {
		synchronized(this){
			animaciones_pendientes ++;
			bloquear_intercambios = true;
		}
	}
	
	public void notificarse_animacion_finalizada() {
		synchronized(this){
			animaciones_pendientes --;
			bloquear_intercambios = animaciones_pendientes > 0;
		}
	}
	
	public void animar_movimiento(Celda c) {
		mi_animador.animar_cambio_posicion(c);
	}
	
	public void animar_cambio_estado(Celda c) {
		mi_animador.animar_cambio_estado(c);
	}

}
