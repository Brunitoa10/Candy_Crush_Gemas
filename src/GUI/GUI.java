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


public class GUI extends JFrame {
	protected Logica milogica;
	protected int filas,columnas;
	protected Celda celda1_pendienteAn,celda2_pendienteAn;
	protected JPanel panel_principal;
	
	private int size_label = 100;

	//Movimientos
	public static final int ARRIBA = 15000;
	public static final int ABAJO = 15001;
	public static final int IZQUIERDA = 15002;
	public static final int DERECHA = 15003;
	
	
	public GUI(Logica l, int f, int c) {
		milogica = l;
		filas = f;
		columnas = c;
		inicializar();
	}
	
	protected void inicializar() {
		setTitle("Proyecto Candy Crush - Comision-06");
		setExtendedState(JFrame.MAXIMIZED_BOTH); 
		setLocationRelativeTo(null);
		//----------------------------------------------------
		//Estaba true, le puse false para poder achicarlo y que aparezca la cruz ()
		setUndecorated(false); 
		//----------------------------------------------------
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
					case KeyEvent.VK_W:		{ milogica.intercambiar(ARRIBA); break; }
					case KeyEvent.VK_S:		{ milogica.intercambiar(ABAJO); break; }
					case KeyEvent.VK_A:		{ milogica.intercambiar(IZQUIERDA); break; }
					case KeyEvent.VK_D:		{ milogica.intercambiar(DERECHA); break; } 
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
	
	public void considerar_para_intercambio_posicion(Celda c) {
		if (celda1_pendienteAn == null) {
			celda1_pendienteAn = c;
		}else {
			celda2_pendienteAn = c;
			AnimadorIntercambio mi_animador_intercambio = new AnimadorIntercambio(size_label, 10, 50, celda1_pendienteAn, celda2_pendienteAn);
			celda1_pendienteAn = null;
			celda2_pendienteAn = null;
			mi_animador_intercambio.start();
		}
	}

}
