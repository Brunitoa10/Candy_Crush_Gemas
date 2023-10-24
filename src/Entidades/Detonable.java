package Entidades;

import Tablero.Tablero;

/**
 * Define las operaciones esperables por sobre las entidades detonables.
 * @author FJoaquin (federico.joaquin@cs.uns.edu.ar)
 *
 */
public interface Detonable {
	/**
	 * Detona la entidad que recibe el mensaje.
 	 * Notifica a la entidad gráfica del cambio de estado.
	 */
	public void detonar(Tablero t);
}
