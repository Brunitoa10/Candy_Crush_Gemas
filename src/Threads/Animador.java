package Threads;

import GUI.Celda;

/**
 * Define las operaciones esperables por sobre un elemento Animador.
 * Un animador podrá llevar adelante la animación de movimiento o de cambio de estado, de una entidad.
 * @author FJoaquin (federico.joaquin@cs.uns.edu.ar)
 *
 */
public interface Animador {
	/**
	 * Obtiene la celda que se animará.
	 * @return Retorna la celda obtenida.
	 */
	public Celda get_celda_asociada();
	
	/**
	 * Inicia el comportamiento asociado con la animación.
	 */
	public void comenzar_animacion();

}