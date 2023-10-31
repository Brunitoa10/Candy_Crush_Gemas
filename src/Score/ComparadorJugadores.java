package Score;

@SuppressWarnings("hiding")
public class ComparadorJugadores<Integer> implements java.util.Comparator<Integer>{
	@SuppressWarnings("unchecked")
	/**
	 * Metodo que compara dos enteros y devuelve un entero respecto a su relacion de orden
	 * Se usa para ordenar de menor a mayor
	 * 
	 * @parm o1 primer número a comparar
	 * @param o2 segundo número a comparar
	 * 
	 * @return 0 si son iguales, int positivo si o1 es mayor a o2 y int negativo si o2 es mayor a o1
	 */
	public int compare(Integer player1, Integer player2) {

		return (((Comparable<Integer>) player1).compareTo(player2));
	}

}