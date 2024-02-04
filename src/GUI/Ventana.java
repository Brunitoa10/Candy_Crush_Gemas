package GUI;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import Entidades.EntidadLogica;
import GestorTeclado.Movimiento;
import Logica.Juego;
import ManejadorAnimaciones.CentralAnimaciones;
import GestorTeclado.*;

@SuppressWarnings("serial")
public class Ventana extends JFrame implements VentanaJuego, VentanaAnimable, VentanaNotificable{
	
	protected Juego juego;
	protected CentralAnimaciones central_animaciones;

	protected int filas;
	protected int columnas;
	protected int animaciones_pendientes;
	private final int size_label = 60;
	protected boolean bloquear_teclado;
	
	protected JLabel texto_superior;
	protected JPanel panel_principal;
	protected JPanel panel_estadisticas_derecho;
	protected Etiquetas etiquetas;

	public Ventana(Juego juego, int filas, int columnas) {
		this.juego = juego;
		this.central_animaciones = new CentralAnimaciones(this);
		this.filas = filas;
		this.columnas = columnas;
		animaciones_pendientes = 0;
		bloquear_teclado = false;
		etiquetas = new Etiquetas(juego);
		crear_componentes_graficos();
		agregar_paneles();
	}
	
	// Operaciones para Ventana Juego (Ventana <-- Juego)
	
	public EntidadGrafica agregar_entidad(EntidadLogica e) {
		Celda celda = new Celda(this, e, size_label, e.get_visibilidad());
		panel_principal.add(celda);
		return celda;
	}
	
	public void ocultar() {
		this.setVisible(false);
	}
	
	public void mostrar() {
		this.setVisible(true);
	}
	
	public void transicionar() {
		// To DO. Podría servir para mostrar información o carteles.
	}
	
	public void resetear(Juego juego, int filas, int columnas) {
		// To DO.
	}
	
	// Operaciones para Ventana Animable (Ventana <-- Celda)
	
	public void animar_intercambio(Celda celda) {
		central_animaciones.animar_intercambio(celda);
	}
	
	public void animar_cambio_foco(Celda celda) {
		central_animaciones.animar_cambio_foco(celda);
	}
	
	public void animar_detonacion(Celda celda) {
		central_animaciones.animar_detonacion(celda);
	}
	
	public void animar_caida(Celda celda) {
		central_animaciones.animar_caida(celda);
	}
	
	public void animar_cambio_visibilidad(Celda celda) {
		central_animaciones.animar_cambio_visibilidad(celda);
	}
	
	public void eliminar_celda(Celda celda) {
		panel_principal.remove(celda);
		panel_principal.repaint();
	}
	
	// Operaciones para Ventana Notificable (Ventana <-- CentralDeAnimaciones)
	
	public void notificarse_animacion_en_progreso() {
		synchronized(this){
			animaciones_pendientes ++;
			bloquear_teclado = true;
		}
	}
	
	public void notificarse_animacion_finalizada() {
		synchronized(this){
			animaciones_pendientes --;
			bloquear_teclado = animaciones_pendientes > 0;
		}
	}
	
	// Operaciones locales a ventana
	
	protected void crear_componentes_graficos() {
		setTitle("TdP 2024 :: Super mini Candy Crush");
		//setSize(new Dimension(500, 500));
		// Obtener dimensiones de la pantalla
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

		// Establecer el tamaño de la ventana al máximo de la pantalla
		setSize(screenSize.width, screenSize.height);

		setResizable(false);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLayout(new BorderLayout());
		
		texto_superior = new JLabel("Esta es una versión super simplificada del Candy-Crush");
		
		panel_principal = new JPanel();
		panel_principal.setSize(size_label * filas, size_label * columnas);
		panel_principal.setLayout(null);
		panel_principal.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {	
				switch(e.getKeyCode()) {
					case KeyEvent.VK_LEFT: 	{ if (!bloquear_teclado) juego.mover_jugador(Movimiento.IZQUIERDA); break; }
					case KeyEvent.VK_RIGHT: { if (!bloquear_teclado) juego.mover_jugador(Movimiento.DERECHA); break; }
					case KeyEvent.VK_UP: 	{ if (!bloquear_teclado) juego.mover_jugador(Movimiento.ARRIBA);break; }
					case KeyEvent.VK_DOWN: 	{ if (!bloquear_teclado) juego.mover_jugador(Movimiento.ABAJO); break; }
					case KeyEvent.VK_W:		{ if (!bloquear_teclado) juego.intercambiar_entidades(Movimiento.ARRIBA); actualizarMovimientos(); break; }
					case KeyEvent.VK_S:		{ if (!bloquear_teclado) juego.intercambiar_entidades(Movimiento.ABAJO); actualizarMovimientos(); break; }
					case KeyEvent.VK_A:		{ if (!bloquear_teclado) juego.intercambiar_entidades(Movimiento.IZQUIERDA); actualizarMovimientos(); break; }
					case KeyEvent.VK_D:		{ if (!bloquear_teclado) juego.intercambiar_entidades(Movimiento.DERECHA); actualizarMovimientos(); break; } 
				}
			}
		});
		
		getContentPane().add(panel_principal, BorderLayout.CENTER);
		getContentPane().add(texto_superior, BorderLayout.NORTH);
		
		panel_principal.setFocusable(true);
	}

	private void agregar_paneles(){
		panel_estadisticas_derecho = etiquetas.getPanelDerecho();
		panel_estadisticas_derecho.setPreferredSize(new Dimension(150, getHeight())); // Establece el ancho del panel derecho
    	add(panel_estadisticas_derecho, BorderLayout.EAST); // Agrega el panel derecho al lado derecho de la ventana
	}	

	public void actualizarMovimientos(){
		etiquetas.actualizarEtiquetaMovimientos(juego.getNivelActual().getCantidadMovimientos());
	}

	public int getWidth(){
		return super.getWidth();
	}

	public int getHeight(){
		return super.getHeight();
	}
}
