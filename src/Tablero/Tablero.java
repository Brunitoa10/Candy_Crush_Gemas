package Tablero;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Random;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;

import Entidades.*;
import GUI.*;
import Logica.Logica;

public class Tablero {

	protected Logica miLogica;
	protected Entidad entidades[][];
	protected int filas;
	protected int columnas;
	protected int fJugador;
	protected int cJugador;

	// Define un mapa para asociar las direcciones de intercambio con las funciones correspondientes
	private final Map<Integer, BiFunction<Integer, Integer, Boolean>> operaciones = new HashMap<>();

	// Define un mapa para asociar las direcciones de movimientos con las funciones correspondientes
	private final Map<Integer, BiConsumer<Integer, Integer>> movimientos = new HashMap<>();

	public Tablero(Logica l) {
		miLogica = l;
		//Intercambios
		operaciones.put(GUI.ABAJO, (fila, columna) -> intercambiar_auxiliar(fila + 1, columna));
		operaciones.put(GUI.ARRIBA, (fila, columna) -> intercambiar_auxiliar(fila - 1, columna));
		operaciones.put(GUI.IZQUIERDA, (fila, columna) -> intercambiar_auxiliar(fila, columna - 1));
		operaciones.put(GUI.DERECHA, (fila, columna) -> intercambiar_auxiliar(fila, columna + 1));
		//Movimientos
		movimientos.put(GUI.ABAJO, (fila, columna) -> mover_jugador_auxiliar(fila + 1, columna));
		movimientos.put(GUI.ARRIBA, (fila, columna) -> mover_jugador_auxiliar(fila - 1, columna));
		movimientos.put(GUI.IZQUIERDA, (fila, columna) -> mover_jugador_auxiliar(fila, columna - 1));
		movimientos.put(GUI.DERECHA, (fila, columna) -> mover_jugador_auxiliar(fila, columna + 1));
	}

	public Entidad get_entidad(int f, int c) {
		return entidades[f][c];
	}

	public void resetar_tablero(int f, int c) {
		filas = f;
		columnas = c;
		fJugador = 0;
		cJugador = 0;
		entidades = new Entidad[f][c];
	}

	public void agregar_entidad(Entidad e) {
		entidades[e.get_fila()][e.get_columna()] = e;
	}


	public int getFila() {
		return filas;
	}

	public int getColumna() {
		return columnas;
	}

	public void fijarJugador(int f, int c) {
		entidades[f][c].enfocar();
		entidades[fJugador][cJugador].desenfocar();
		fJugador = f;
		cJugador = c;
	}

	// Función mover_jugador usando el mapa
	public void mover_jugador(int d) {
		BiConsumer<Integer, Integer> movimiento = movimientos.get(d);
		if (movimiento != null) {
			movimiento.accept(fJugador, cJugador);
		}
	}

	public boolean intercambiar(int d) {
		BiFunction<Integer, Integer, Boolean> operacion = operaciones.get(d);
		boolean intercambioValido = false;
		if (operacion != null) {
			intercambioValido = operacion.apply(fJugador, cJugador);
		}

		return intercambioValido;
	}

	public String obtenerTipoGema(int tipoGema) {
		boolean encontre = false;
		String nombreGema = " ";
		for(int i = 0; i<filas && !encontre; i++) {
			for (int j = 0; j<columnas && !encontre; j++) {
				if (entidades[i][j].get_color() == tipoGema) {
					encontre = true; 
					if(encontre) {
						nombreGema = entidades[i][j].get_imagen_representativa();
					}
				}
			}
		}
		return nombreGema;
	}

	//METODOS PRIVADOS

	private void mover_jugador_auxiliar(int nf, int nc) {
		if (en_rango(nf,nc) ) {
			entidades[nf][nc].enfocar();
			entidades[fJugador][cJugador].desenfocar();
			fJugador = nf;
			cJugador = nc;
		}
	}

	private boolean intercambiar_auxiliar(int nf, int nc) {
		int af = fJugador;
		int ac = cJugador;
		boolean movimientoValido = false;
		
		if (en_rango(nf, nc)) {
			if (entidades[af][ac].es_posible_intercambiar(entidades[nf][nc])) {

				// Anima el posible intercambio de entidades
				aplicar_intercambio(af, ac, nf, nc);

				// Si el intercambio provoca un match de 2 o 3 entidades, chequea las combinaciones y detona lo necesario
				// De lo contrario, retrotae el intercambio anterior que no fue válido
				if (entidades[af][ac].machea(entidades[nf][nc])) {
					// Llamamos a buscarCombos después de un intercambio exitoso
					buscarCombos(af, ac, nf, nc);
					

				} else {
					aplicar_intercambio(nf, nc, af, ac);
				}
			}
			movimientoValido = true;
		}

		return movimientoValido;
	}
	
	private void aplicar_intercambio(int af, int ac, int nf, int nc) {
		Entidad entidad_aux = entidades[af][ac];

		entidades[af][ac].cambiar_posicion(nf, nc);
		entidades[nf][nc].cambiar_posicion(af, ac);

		entidades[af][ac] = entidades[nf][nc];
		entidades[nf][nc] = entidad_aux;

		fJugador = nf;
		cJugador = nc;
	}

	private LinkedList<Entidad> buscarCombos(int f1, int c1, int f2, int c2) {
		LinkedList<Entidad> listaCombos = new LinkedList<>();

		if (esPosicionValida(f1, c1) && esPosicionValida(f2, c2)) {
			listaCombos.addAll(buscarCombosEnFila(f1, c1));
			listaCombos.addAll(buscarCombosEnColumna(f1, c1));
			listaCombos.addAll(buscarCombosEnFila(f2, c2));
			listaCombos.addAll(buscarCombosEnColumna(f2, c2));
			miLogica.actualizarObjetivos(listaCombos);

			System.out.println("Tablero buscarCombos :: "+listaCombos.size());
			detonarGemas(listaCombos);
			generarNuevasGemasDespuesDeDetonar();
			return listaCombos;
		} else {
			throw new IllegalArgumentException("Posición inválida en buscarCombos");
		}
	}
	
	private void generarNuevasGemasDespuesDeDetonar() {
	    // Recorre todas las filas desde abajo hacia arriba
	    for (int fila = filas - 1; fila >= 0; fila--) {
	        for (int columna = 0; columna < columnas; columna++) {
	            if (entidades[fila][columna] == null || entidades[fila][columna].get_color() == 0) {
	                // Si no hay una entidad en esta posición, genera una nueva gema
	                entidades[fila][columna] =  new GemaNormal(fila, columna,new Random().nextInt(8));
	                entidades[fila][columna].setImagenesRep(entidades[fila][columna].get_imagen_representativa());
	               
	            }
	        }
	    }
	    
	}
	public Tablero obtenerTablero() {
        return this;
    }


	private LinkedList<Entidad> buscarCombosEnFila(int fila, int columna) {
		LinkedList<Entidad> combosEnFila = new LinkedList<>();

		// Obtener el tipo de gema en la posición (fila, columna)
		Entidad entidad = entidades[fila][columna];

		// Contador para el número de gemas iguales consecutivas
		int cantidad = 1;

		// Buscar hacia la izquierda
		int colIzquierda = columna - 1;
		while (colIzquierda >= 0 && entidades[fila][colIzquierda].get_color() == entidad.get_color()) {
			combosEnFila.add(entidades[fila][colIzquierda]); // Agregar a la lista de combos
			cantidad++;
			colIzquierda--;
		}

		// Buscar hacia la derecha
		int colDerecha = columna + 1;
		while (colDerecha < columnas && entidades[fila][colDerecha].get_color() == entidad.get_color()) {
			combosEnFila.add(entidades[fila][colDerecha]); // Agregar a la lista de combos
			cantidad++;
			colDerecha++;
		}

		// Si hay al menos 3 gemas iguales consecutivas, agregar la posición actual
		if (cantidad >= 3) {

			combosEnFila.add(entidad); // Agregar la posición actual a la lista de combos

			if (cantidad == 4) {
				//entidad.marcarComoRayadaHorizontal(); // Suponiendo que hay un método para marcar como gema rayada horizontal
				System.out.println("Se genera una gema rayada horizontal");
			} else {
				if (cantidad == 5 || cantidad == 6) {
					// Verificar combinaciones en forma de T, L o sigmoide
					if (verificarCombinacionesEspecialesEnFila(fila, columna)) {
						//entidad.marcarComoEnvuelta();
						System.out.println("Se genera una gema Envuelta (Combinación especial en fila)");
					}
				}
			} 
		} else {
			combosEnFila.clear(); // No hay combos, limpiar la lista
		}

		return combosEnFila;
	}

	private boolean verificarCombinacionesEspecialesEnFila(int fila, int columna) {
		// Obtener el tipo de gema en la posición (fila, columna)
		int tipoGema = entidades[fila][columna].get_color();
		boolean verificaCombinacion = false;

		// Verificar combinación en forma de T
		if (columna >= 2 && columna < columnas - 1) {
			if (entidades[fila][columna - 1].get_color() == tipoGema &&
					entidades[fila][columna - 2].get_color() == tipoGema &&
					entidades[fila][columna + 1].get_color() == tipoGema) {
				verificaCombinacion = true;
				System.out.println("T fila");
			}
		}

		// Verificar combinación en forma de L
		if (columna >= 2 && fila < filas - 1) {
			if (entidades[fila][columna - 1].get_color() == tipoGema &&
					entidades[fila][columna - 2].get_color() == tipoGema &&
					entidades[fila + 1][columna - 2].get_color() == tipoGema) {
				verificaCombinacion = true;
				System.out.println("L fila");
			}
		}

		// Verificar combinación en forma de sigmoide (+)
		if (columna >= 1 && columna < columnas - 1 && fila >= 1 && fila < filas - 1) {
			if (entidades[fila][columna - 1].get_color() == tipoGema &&
					entidades[fila][columna + 1].get_color() == tipoGema &&
					entidades[fila - 1][columna].get_color() == tipoGema &&
					entidades[fila + 1][columna].get_color() == tipoGema) {
				verificaCombinacion = true;
				System.out.println("Sigmoide fila");
			}
		}

		return verificaCombinacion;
	}

	private LinkedList<Entidad> buscarCombosEnColumna(int fila, int columna) {
		LinkedList<Entidad> combosEnColumna = new LinkedList<>();

		// Obtener el tipo de gema en la posición (fila, columna)
		Entidad entidad = entidades[fila][columna];

		// Contador para el número de gemas iguales consecutivas
		int cantidad = 1;

		// Buscar hacia arriba
		int filaArriba = fila - 1;
		while (filaArriba >= 0 && entidades[filaArriba][columna].get_color() == entidad.get_color()) {
			combosEnColumna.add(entidades[filaArriba][columna]); // Agregar a la lista de combos
			cantidad++;
			filaArriba--;
		}

		// Buscar hacia abajo
		int filaAbajo = fila + 1;
		while (filaAbajo < filas && entidades[filaAbajo][columna].get_color() == entidad.get_color()) {
			combosEnColumna.add(entidades[filaAbajo][columna]); // Agregar a la lista de combos
			cantidad++;
			filaAbajo++;
		}

		// Si hay al menos 3 gemas iguales consecutivas, agregar la posición actual
		if (cantidad >= 3) {
			combosEnColumna.add(entidad); // Agregar la posición actual a la lista de combos

			if (cantidad == 4) {
				//entidad.marcarComoRayadaVertical(); // Suponiendo que hay un método para marcar como gema rayada vertical
				System.out.println("Se genera una gema rayada vertical");
			} else if (cantidad == 5 || cantidad == 6) {
				if (verificarCombinacionesEspecialesEnColumna(fila, columna)) {
					//entidad.marcarComoEnvuelta();
					System.out.println("Se genera una gema Envuelta (Combinación especial en columna)");
				}
			} 
		} else {
			combosEnColumna.clear(); // No hay combos, limpiar la lista
		}

		return combosEnColumna;
	}

	private boolean verificarCombinacionesEspecialesEnColumna(int fila, int columna) {
		// Obtener el tipo de gema en la posición (fila, columna)
		int tipoGema = entidades[fila][columna].get_color();
		boolean verificaCombinacion = false;
		// Verificar combinación en forma de T
		if (fila >= 2 && fila < filas - 1) {
			if (entidades[fila - 1][columna].get_color() == tipoGema &&
					entidades[fila - 2][columna].get_color() == tipoGema &&
					entidades[fila + 1][columna].get_color() == tipoGema) {
				verificaCombinacion = true;
				System.out.println("T columna");
			}
		}

		// Verificar combinación en forma de L
		if (fila >= 2 && columna < columnas - 1) {
			if (entidades[fila - 1][columna].get_color() == tipoGema &&
					entidades[fila - 2][columna].get_color() == tipoGema &&
					entidades[fila - 2][columna + 1].get_color() == tipoGema) {
				verificaCombinacion = true;
				System.out.println("L columna");
			}
		}

		// Verificar combinación en forma de sigmoide (+)
		if (fila >= 1 && fila < filas - 1 && columna >= 1 && columna < columnas - 1) {
			if (entidades[fila - 1][columna].get_color() == tipoGema &&
					entidades[fila + 1][columna].get_color() == tipoGema &&
					entidades[fila][columna - 1].get_color() == tipoGema &&
					entidades[fila][columna + 1].get_color() == tipoGema) {
				verificaCombinacion = true;
				System.out.println("Sigmoide columna");
			}
		}

		return verificaCombinacion;
	}


	private void detonarGemas(LinkedList<Entidad> gemasADetonar) {
	    for (Entidad entidad : gemasADetonar) {
	        int fila = entidad.get_fila();
	        int columna = entidad.get_columna();

	        // Aquí detonas la gema, realiza las acciones necesarias
	         entidades[fila][columna].detonar(this);
	    }
	}

     //Verifica si hay rocas para romper en la proximidad
	private void destruirRocas(Entidad e)
	{
	  int fila=e.get_fila();
	  int columna=e.get_columna();
	  //si no esta en la primer fila manda mensaje
	  if(fila!=0) 
	  {
		entidades[fila-1][columna].explosionAdyacente();
	  }
	  //si no esta en la primer columna manda mensaje
	  if(columna!=0) 
	  {
		entidades[fila][columna-1].explosionAdyacente();
	  }
	  //si no esta en la ultima fila manda mensaje
	  if(fila!=filas-1)
	  {
		entidades[fila+1][columna].explosionAdyacente();
	  }
	  //si no esta en la ultima columna manda mensaje
	  if(columna!=columnas-1)
	  {
		entidades[fila][columna+1].explosionAdyacente();
	  }
	}

	private boolean esPosicionValida(int fila, int columna) {
		return (0 <= fila && fila < filas) && (0 <= columna && columna < columnas);
	}
	
	private boolean en_rango(int nf, int nc){
		return (((nf >= 0) && (nf < filas)) && ((nc >= 0) && (nc < columnas)));
	}

	

	/*public void limpiarTablero() {
		for (int i = 0; i < filas; i++) {
	        for (int j = 0; j < columnas; j++) {
	            t[i][j].setEntidad(new GemaNormal(i,j,0));
	        }
	    }
	 }*/

}
