package Threads;

import java.util.LinkedList;
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
	protected LinkedList<Animador> animacionesIntercambioPendientes;
	protected LinkedList<Animador> animacionesExplosionPendientes;
	protected LinkedList<Animador> animacionesCaidaPendientes;
	protected int size_label;
	
	public CentralAnimaciones(GUI g) {
		miGUI = g;
		animacionesIntercambioPendientes = new LinkedList<Animador>();
		animacionesExplosionPendientes = new LinkedList<Animador>();
		animacionesCaidaPendientes = new LinkedList<Animador>();
	}
	
	/**
	 * Indica que la celda parametrizada debe ser animada a partir de un cambio de posición.
	 * La animación será lanzada de inmediato, siempre que no existan animaciones en progreso sobre c.
	 * La animación será encolada para efectivizarse en el futuro, a la espera de que las animaciones solicitadas previamente sobre c
	 * se realicen primero.
	 * @param c Celda que debe animarse, en relación a la posición que ubica la JLabel y la ubicación indicada por la entidad lógica
	 * referenciada por c.
	 */

	public void agregar_movimiento(Celda c) {
		AnimadorMovimiento animador = new AnimadorMovimiento(this, 10, 50, c);
		miGUI.notificarse_animacion_en_progreso();
		
		if(!tiene_animaciones_en_progreso(animacionesIntercambioPendientes))  {
			System.out.println("animacion intercambio");
			animacionesIntercambioPendientes.addLast(animador);
			animador.comenzar_animacion();
		}
		else{
			animacionesIntercambioPendientes.addLast(animador);
		}
	}

	public void animar_movimiento(Celda c) {
		while(!animacionesIntercambioPendientes.isEmpty()) {
			animacionesIntercambioPendientes.removeFirst().comenzar_animacion();
		}
	}

	public void agregar_explosion(Celda c) {
		AnimadorExplosion animador = new AnimadorExplosion(this, c, 50);
		miGUI.notificarse_animacion_en_progreso();
		
		if(tiene_animaciones_en_progreso(animacionesIntercambioPendientes))  {
			animacionesExplosionPendientes.addLast(animador);
		}
		else {
			animacionesExplosionPendientes.addLast(animador);
			animar_explosion();
		}
	}

	public void animar_explosion() {
		while(!animacionesExplosionPendientes.isEmpty()) {
			System.out.println("animacion explosion");
			animacionesExplosionPendientes.removeFirst().comenzar_animacion();
		}

		if(tiene_animaciones_en_progreso(animacionesCaidaPendientes)) {
			animar_caida();
		}
	}

	public void agregar_caida(Celda c) {
		AnimadorCaida animador = new AnimadorCaida(this, c);
		miGUI.notificarse_animacion_en_progreso();
		
		if(tiene_animaciones_en_progreso(animacionesExplosionPendientes))  {
			animacionesCaidaPendientes.addLast(animador);
		}
		else {
			animacionesCaidaPendientes.addLast(animador);
			animar_caida();
		}
	}

	public void animar_caida() {
		while(!animacionesCaidaPendientes.isEmpty()) {
			System.out.println("animacion caida");
				animacionesCaidaPendientes.removeFirst().comenzar_animacion();
		}
		if(tiene_animaciones_en_progreso(animacionesExplosionPendientes)) {
			animar_explosion();
		}

	}

	@Override
	public void notificarse_finalizacion_animacion(Animador a) {
		miGUI.notificarse_animacion_finalizada();
	}

	/**
	 * Estima si la celda parametrizada actualmente cuenta con animaciones en progreso. 
	 * @param c Celda que se desea considerar para el chequeo de animaciones en progreso.
	 * @return True si la celda tiene animaciones actualmente en progreso; false en caso contrario.
	 */
	private boolean tiene_animaciones_en_progreso(LinkedList<Animador> l) {
		return !l.isEmpty();
	}
}
