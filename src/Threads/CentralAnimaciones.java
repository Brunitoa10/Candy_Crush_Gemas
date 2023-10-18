package Threads;

import java.util.Iterator;
import java.util.PriorityQueue;
import GUI.Celda;
import GUI.GUI;

/**
 * Modela el manager de animaciones requerido para efectivizar las mismas en función al orden en el que fueron solicitadas desde la ventana.
 * Ante cada nueva animación solicitada por ventana y que se debe realizar por sobre una celda, se encarga de efectivizar estas animaciones 
 * en el orden que corresponda a su arriba (FIFO).
 * Permite que dos o más animaciones por sobre una misma celda se realicen efectivamente en orden secuencial, sin solapamientos.
 * Las animaciones entre entidades diferentes se resolverán, recurrentemente, considerando que algunos animadores se efectivizarán mediante Threads.
 * @author FJoaquin (federico.joaquin@cs.uns.edu.ar)
 *
 */
public class CentralAnimaciones implements ManejadorAnimaciones{
	
	protected GUI miGUI;
	protected PriorityQueue<Animador> colaConPrioridadAnimaciones;
	protected int size_label;
	
	public CentralAnimaciones(GUI g) {
		miGUI = g;
		colaConPrioridadAnimaciones = new PriorityQueue<Animador>();
	}
	
	/**
	 * Indica que la celda parametrizada debe ser animada a partir de un cambio de posición.
	 * La animación será lanzada de inmediato, siempre que no existan animaciones en progreso sobre c.
	 * La animación será encolada para efectivizarse en el futuro, a la espera de que las animaciones solicitadas previamente sobre c
	 * se realicen primero.
	 * @param c Celda que debe animarse, en relación a la posición que ubica la JLabel y la ubicación indicada por la entidad lógica
	 * referenciada por c.
	 */

	public void animar_cambio_posicion(Celda c) {
		Animador animador = new AnimadorMovimiento(this, 10, 50, c);
		miGUI.notificarse_animacion_en_progreso();
		
		if (tiene_animaciones_en_progreso() ) {
			colaConPrioridadAnimaciones.add(animador);
		}else {
			colaConPrioridadAnimaciones.add(animador);
			animador.comenzar_animacion();
		}
	}

	
	public void animar_caida(Celda c) {
		Animador animador = new AnimadorCaida(this, c);
		miGUI.notificarse_animacion_en_progreso();
		
		if (tiene_animaciones_en_progreso() ) {
			colaConPrioridadAnimaciones.add(animador);
		}else {
			colaConPrioridadAnimaciones.add(animador);
			animador.comenzar_animacion();
		}
	}
	
	public void animar_explosion(Celda c) {
		Animador animador;
		miGUI.notificarse_animacion_en_progreso();
		animador = new AnimadorExplosion(this, c, 0);

		if (tiene_animaciones_en_progreso () ) {
			colaConPrioridadAnimaciones.add(animador);
		}else {
			colaConPrioridadAnimaciones.add(animador);
			animador.comenzar_animacion();
		}	
	}
	
	/**
	 * Indica que la celda parametrizada debe ser animada a partir de un cambio de estado.
	 * La animación será lanzada de inmediato, siempre que no existan animaciones en progreso sobre c.
	 * La animación será encolada para efectivizarse en el futuro, a la espera de que las animaciones solicitadas previamente sobre c
	 * se realicen primero.
	 * @param c Celda que debe animarse, en relación a la imagen actual que la representa.
	 */


	@Override
	public void notificarse_finalizacion_animacion(Animador a) {
		Animador animador;
		
		miGUI.notificarse_animacion_finalizada();
		
		Iterator<Animador> iterador_animaciones_pendientes = colaConPrioridadAnimaciones.iterator();
		
		if (!iterador_animaciones_pendientes.hasNext()) {
			animador = iterador_animaciones_pendientes.next();
			animador.comenzar_animacion();
		}
	}
	
	/**
	 * Estima si la celda parametrizada actualmente cuenta con animaciones en progreso. 
	 * @param c Celda que se desea considerar para el chequeo de animaciones en progreso.
	 * @return True si la celda tiene animaciones actualmente en progreso; false en caso contrario.
	 */
	private boolean tiene_animaciones_en_progreso() {
		boolean retorno = false;
		if (colaConPrioridadAnimaciones.peek() != null) {
			retorno = !colaConPrioridadAnimaciones.isEmpty();
		}
		return retorno;
	}
}
