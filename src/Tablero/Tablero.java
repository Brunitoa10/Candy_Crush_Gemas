package Tablero;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;

import Entidades.*;
import GUI.*;
import Logica.EntidadLogica;
import Logica.Logica;

public class Tablero {

	private int colorAleatorio(int cotaInferior, int cotaSuperior) {
		return (int)(Math.random()*cotaSuperior+cotaInferior);
	}

	/*private boolean manejarColisiones(LinkedList<Celda> l,Celda c1, Celda c2, int dir) {
		//RECORRER LA LISTA (los combos solo tienen 2 colores posibles, ambos diferentes)
	    //A CADA ELEMENTO DE LA LISTA APLICARLE .DESTRUIR()
		//SI HAY DOS COLORES IGUALES EN DIFERENTES SETS DE COMBOS GENERAR RAYADO EN CELDA 1
		//SI HAY OTRO MAS EN CELDA 2, NO ES POSIBLE QUE HAYA UN TERCERO
		//CUALQUIER PROBLEMA CON ESTO, CONSULTAR A VALEN

		//consigna:
		/* rayado 
	 * formacion:
	 * Exactamente 4 caramelos regulares seguidos. 
	 * en la posici�n hacia donde se movi� el caramelo que gener� la
	 * formaci�n del mismo.
	 * Si el caramelo rayado es el resultado de un movimiento horizontal, las rayas ser�n
	 * horizontales, si es el resultado de un movimiento vertical, ser�n verticales y el resultado
	 * es aleatorio durante las cascadas m�s grandes
	 * 
	 * envuelto
	 * Formaci�n: 5 o 6 caramelos regulares en forma de T, L o +.
	 * Ubicaci�n de aparici�n: en la posici�n hacia donde se movi� el caramelo que gener� la
	 * formaci�n del mismo.
	 */

	/*if(!l.isEmpty()) {
			Iterator<Celda> it = l.iterator();
			Celda c = null;
			int cf = -1;
			int cc = -1; // -1 para detectar out of bounds
			Celda aux= null;
			int color1 = -1;
			int color2 = -2;
			int colorT = -3;
			int combo = 0;
			int direccRay;
			boolean posibleT = false;//marca si puede pasar que estemos en un combo t, se genera bomba(envuelta?)

			while(it.hasNext()) {//[1],[1],[1],[1],null,[4][4][4][4],null,[1][1][1]

				if(c == null) {//principio de combo
					c = it.next();
					cf = c.getEntidad().getFila();
					cc = c.getEntidad().getColumna();
					colorT = c.getEntidad().obtenerColor();
					posibleT = false;
					if(color1 == -1) {
						color1 = c.getEntidad().obtenerColor();
					}else 
						if(color1 == c.getEntidad().obtenerColor()) {
							posibleT = true;
						}else 
							if(color2 == -2) 
								color2 = c.getEntidad().obtenerColor();
							else if(color2 == c.getEntidad().obtenerColor()) {
								    posibleT = true;}
					destruirRocas(c.getEntidad());// destruiur rocas
					c.getEntidad().romper(this);
					combo = 1;
					if(c == c1 || c ==c2) {
						if(posibleT) {
							System.out.println("generar bomba!!!!!");
							c.setEntidad(new GemaEnvuelta(cf,cc,colorT));
							//generar bomba en la celda c
							}
						else aux = c;
					}

				}//end if (c == null)
				else {//medio del combo
					c = it.next();

					if(c != null) {

						if(cf == c.getEntidad().getFila()) {
							//se mueve a travez de las filas
							direccRay = 1;}
						else direccRay = 5;//se mueve a travez de las columnas

						cf= c.getEntidad().getFila();
						cc = c.getEntidad().getColumna();

						destruirRocas(c.getEntidad());//destruir rocas
						c.getEntidad().romper(this);
						combo++;
						if(c == c1 || c ==c2) {
							if(posibleT) {
								System.out.println("generar bomba!!!!!");
								c.setEntidad(new GemaEnvuelta(cf,cc,colorT));
								//generar bomba en la celda c
							}
							else aux = c;
						}
						if(combo>=4 && !posibleT) {
							System.out.println("generar rayada!!!!!");
							aux.setEntidad(new GemaRayada(cf,cc, colorT, direccRay));
							//generar rayada en aux
							while(c!= null) {
								c = it.next();
							}
						}
					}
				}//end medio del combo	
			}//end while		
		    return true;	
		}//end if lista vacia  
		else return false;
	}*/

	private boolean en_rango(int nf, int nc){
		return (((nf >= 0) && (nf < filas)) && ((nc >= 0) && (nc < columnas)));
	}


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

	private void mover_jugador_auxiliar(int nf, int nc) {
		if ( en_rango(nf,nc) ) {
			entidades[nf][nc].enfocar();
			entidades[fJugador][cJugador].desenfocar();
			fJugador = nf;
			cJugador = nc;
		}
	}


	public boolean intercambiar(int d) {
		BiFunction<Integer, Integer, Boolean> operacion = operaciones.get(d);
		boolean intercambioValido = false;
		if (operacion != null) {
			intercambioValido = operacion.apply(fJugador, cJugador);
		}
		//miLogica.actualizarGUI();
		return intercambioValido;
	}

	private boolean intercambiar_auxiliar(int nf, int nc) {
		int af = fJugador;
		int ac = cJugador;
		boolean movimientoValido = false;

		if (en_rango(nf, nc)) {
			if (entidades[af][ac].es_posible_intercambiar(entidades[nf][nc])) {
				
				// Anima el posible intercambio de entidades
				aplicar_intercambio(af, ac, nf, nc);
				// Llamamos a buscarCombos después de un intercambio exitoso
				buscarCombos(af, ac, nf, nc);
				// Si el intercambio provoca un match de 2 o 3 entidades, chequea las combinaciones y detona lo necesario
				// De lo contrario, retrotae el intercambio anterior que no fue válido
				if (entidades[af][ac].machea(entidades[nf][nc])) {
					
					entidades[af][ac].detonar();
					entidades[nf][nc].detonar();
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

	private LinkedList<EntidadLogica> buscarCombos(int f1, int c1, int f2, int c2) {
		LinkedList<EntidadLogica> listaCombos = new LinkedList<>();

		if (esPosicionValida(f1, c1) && esPosicionValida(f2, c2)) {
			listaCombos.addAll(buscarCombosEnFila(f1, c1));
			listaCombos.addAll(buscarCombosEnColumna(f1, c1));
			listaCombos.addAll(buscarCombosEnFila(f2, c2));
			listaCombos.addAll(buscarCombosEnColumna(f2, c2));
			miLogica.actualizarObjetivos(listaCombos);
			System.out.println("Tablero buscarCombos :: "+listaCombos.size());
			return listaCombos;
		} else {
			throw new IllegalArgumentException("Posición inválida en buscarCombos");
		}
	}

	private LinkedList<EntidadLogica> buscarCombosEnFila(int fila, int columna) {
		LinkedList<EntidadLogica> combosEnFila = new LinkedList<>();

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

	private LinkedList<EntidadLogica> buscarCombosEnColumna(int fila, int columna) {
		LinkedList<EntidadLogica> combosEnColumna = new LinkedList<>();

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

	private boolean esPosicionValida(int fila, int columna) {
		return (0 <= fila && fila < filas) && (0 <= columna && columna < columnas);
	}

	//Metodo Agregado por bruno
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

	/*public boolean caida() {
		//verificar que la fila 0 es la de arriba
		EntidadGrafica egrafica;
		boolean caido = false;
		for (int i = 0; i<filas; i++) {
			for (int j = 0; j<columnas; j++) {
				if(t[i][j].getEntidad().obtenerColor() == 0) {
					caido = true;
					if(i == 0) {
						egrafica = t[i][j];

						t[i][j].setEntidad(new GemaNormal(i,j,colorAleatorio(1,6)));
						t[i][j].getEntidad().setEntidadGrafica(egrafica);
						t[i][j].notificarse_cambio_estado();
					}
					else
						intercambiarCaida(i-1,j,i,j);//VERIFICAR
				}
			}
		}
		miLogica.actualizarTablero();

		if(caido) {
			caida();
			return true;}

		else return false;
	}*/

	/*public void limpiarTablero() {
		for (int i = 0; i < filas; i++) {
	        for (int j = 0; j < columnas; j++) {
	            t[i][j].setEntidad(new GemaNormal(i,j,0));
	        }
	    }
	 }*/

	//Verifica si hay rocas para romper en la proximidad
	/*private void destruirRocas(Entidad e)
	{
	  int fila=e.getFila();
	  int columna=e.getColumna();
	  //si no esta en la primer fila manda mensaje
	  if(fila!=0) 
	  {
		t[fila-1][columna].getEntidad().explosionAdyacente();
	  }
	  //si no esta en la primer columna manda mensaje
	  if(columna!=0) 
	  {
		t[fila][columna-1].getEntidad().explosionAdyacente();
	  }
	  //si no esta en la ultima fila manda mensaje
	  if(fila!=filas-1)
	  {
		t[fila+1][columna].getEntidad().explosionAdyacente();
	  }
	  //si no esta en la ultima columna manda mensaje
	  if(columna!=columnas-1)
	  {
		t[fila][columna+1].getEntidad().explosionAdyacente();
	  }
	}*/

}
