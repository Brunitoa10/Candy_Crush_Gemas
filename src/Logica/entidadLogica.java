package Logica;

public interface entidadLogica {
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
	public String getImagenRep();
}
