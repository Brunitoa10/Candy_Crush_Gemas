package Logica;

public interface EntidadLogica {
 /**
	 * Obtiene la fila en la que se ubica la entidad lógica.
	 * @return el valor de la fila.
	 */
	public int getFila();
	/**
	 * Obtiene la columna en la que se ubica la entidad lógica.
	 * @return el valor de la columna.
	 */
	public int getColumna();
	/**
	 * Obtiene la ruta donde se encuentra la imagen representativa de la entidad, en relación a su estado.
	 * @return la ruta hacia la imagen.
	 */

	public int obtenerColor();
	//solo cambia los valores de x e y
	public void intercambiarPosicion(int x, int y);

	public String getImagenRep();
}
