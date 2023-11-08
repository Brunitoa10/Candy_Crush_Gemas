package Entidades;

/**
 * Define los mensaje posibles de solicitar por sobre las entidades de la aplicación, 
 * respecto de su capacidad de intercambiarse con otras entidades en el tablero.
 * @author FJoaquin (federico.joaquin@cs.uns.edu.ar)
 *
 */
public interface Intercambiable {
	/**
	 * Indica si es posible que la entidad receptora pueda ser intercambiada de posición con la entidad parametrizada.
	 * @param e Entidad con la que se analiza el intercambio.
	 * @return True si el intercambio es posible, false en caso contrario.
	 */
	public boolean es_posible_intercambiar(Entidad entidad);
	/**
	 * Indica si es posible que la entidad receptora pueda ser intercambiada de posición con el Caramelo parametrizado.
	 * @param c Caramelo con el que se analiza el intercambio.
	 * @return True si el intercambio es posible, false en caso contrario.
	 */
	public boolean puede_recibir(GemaNormal c);
	/**
	 * Indica si es posible que la entidad receptora pueda ser intercambiada de posición con el Potenciador parametrizado.
	 * @param p Potenciador con el que se analiza el intercambio.
	 * @return True si el intercambio es posible, false en caso contrario.
	 */
	public boolean puede_recibir(GemaRayada p);
	public boolean puede_recibir(GemaEnvuelta p);
	public boolean puede_recibir(GemaCruzada p);
	public boolean puede_recibir(Hielo h);
	public boolean puede_recibir(Roca r);
	public boolean puede_recibir(Bomba b);
	/**
	 * Fija la nueva posición de la entidad receptora del mensaje.
	 * Notifica a la entidad gráfica del cambio realizado.
	 * @param nf Nueva fila de la entidad. 
	 * @param nc Nuevo columna de la entidad.
	 */
	public void cambiar_posicion(int nf, int nc);
	
//	public boolean puede_recibir(Potenciador potenciador);

	
	public void intercambiar(Entidad entidad);
	
	public void intercambiar_con(GemaNormal g);
	public void intercambiar_con(GemaRayada g);
	public void intercambiar_con(GemaEnvuelta g);
	public void intercambiar_con(GemaCruzada g);
	
	public void intercambiar_con(Roca r);

	//public void intercambiar_con(Potenciador potenciador);

	public void intercambiar_con(Hielo h);
}
