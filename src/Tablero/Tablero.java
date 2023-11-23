package Tablero;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;

import Entidades.*;
import EstrategiaMatch.*;
import GUI.*;
import Logica.*;
import Score.AdministradordeScore;


public class Tablero implements TableroJuego{

	protected Logica miLogica;
	protected Entidad entidades[][];
	protected int filas,columnas;
	protected int fJugador,cJugador;
	protected List<Entidad> entidades_asociadas;
	protected String skin;
	protected AdministradordeScore miAdministradordeScore;
	protected AdministradorEstrategias miAdministradordeEstrategias;
	public NotificadorDeEntidadesConTiempo notificadorDeGemasConTemporizador;

	// Define un mapa para asociar las direcciones de intercambio con las funciones correspondientes
	private final Map<Integer, BiFunction<Integer, Integer, Boolean>> operaciones = new HashMap<>();

	// Define un mapa para asociar las direcciones de movimientos con las funciones correspondientes
	private final Map<Integer, BiConsumer<Integer, Integer>> movimientos = new HashMap<>();

	public Tablero(Logica l) {
		miLogica = l;
		notificadorDeGemasConTemporizador = new NotificadorDeEntidadesConTiempo();
		notificadorDeGemasConTemporizador.empezarContador();
		//Intercambios
		operaciones.put(GUI.ABAJO, (fila, columna) -> intercambiar_entidades_y_transicionar(fila + 1, columna));
		operaciones.put(GUI.ARRIBA, (fila, columna) -> intercambiar_entidades_y_transicionar(fila - 1, columna));
		operaciones.put(GUI.IZQUIERDA, (fila, columna) -> intercambiar_entidades_y_transicionar(fila, columna - 1));
		operaciones.put(GUI.DERECHA, (fila, columna) -> intercambiar_entidades_y_transicionar(fila, columna + 1));
		//Movimientos
		movimientos.put(GUI.ABAJO, (fila, columna) -> mover_jugador_auxiliar(fila + 1, columna));
		movimientos.put(GUI.ARRIBA, (fila, columna) -> mover_jugador_auxiliar(fila - 1, columna));
		movimientos.put(GUI.IZQUIERDA, (fila, columna) -> mover_jugador_auxiliar(fila, columna - 1));
		movimientos.put(GUI.DERECHA, (fila, columna) -> mover_jugador_auxiliar(fila, columna + 1));
	}

	public Entidad get_entidad(int f, int c) {
		return entidades[f][c];
	}

	public void resetar_tablero(int cant_filas, int cant_columnas) {
		filas = cant_filas;
		columnas = cant_columnas;
		fJugador = cJugador = 0;
		entidades = new Entidad[cant_filas][cant_columnas];
		entidades_asociadas = new LinkedList<Entidad>();
	}
	
	public void agregar_entidad(Entidad e) {
		entidades[e.get_fila()][e.get_columna()] = e;
	}

	public void set_Skin(String skin)
	{
		this.skin=skin;
	}

		public int getFila() {
		return filas;
	}

	public int getColumna() {
		return columnas;
	}

	public LinkedList<Estrategias> getEstrategias()
	{
		return miAdministradordeEstrategias.get_lista_de_Estrategias();
	}

	public void fijar_jugador(int f, int c) {
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
	
	 public boolean intercambiar_entidades(int d) {
		 BiFunction<Integer, Integer, Boolean> operacion = operaciones.get(d);
	     return operacion != null && operacion.apply(fJugador, cJugador);
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

	public void caida(int puntero,int columna ) {
		for (int i = puntero; i > 0; i--) {
			if (entidades[i][columna].get_color() != 0) {
				Entidad aux=entidades[i - 1][columna];
				entidades[i][columna] = entidades[i - 1][columna];
				entidades[i - 1][columna]=aux;
			}
		}
		entidades[0][columna] = new GemaNormal(this,0,columna,new Color(new Random().nextInt(8)),true,skin);
	}
	
	private void mover_jugador_auxiliar(int nf, int nc) {
		if (en_rango(nf,nc)) {
			entidades[nf][nc].enfocar();
			entidades[fJugador][cJugador].desenfocar();
			fJugador = nf;
			cJugador = nc;
		}
	}

	
	
	public void imprimirLista(LinkedList<Entidad> listaCombos) {
        for (Entidad elemento : listaCombos) {
            System.out.print(elemento.get_color()+" - ");
        }
    }
	
	public Tablero obtenerTablero() {
        return this;
    }
	
	public boolean hayEfectoExplosionAdyacente(int filaVecina, int columnaVecina) {
		boolean esAfectadaPorExplosionAdyacente = false;
	   
	    if (esPosicionValida(filaVecina, columnaVecina)) {
	        esAfectadaPorExplosionAdyacente = entidades[filaVecina][columnaVecina].esAfectadaPorExplosionAdyacente();
	    }
	    return esAfectadaPorExplosionAdyacente;
	}
	
	private boolean esPosicionValida(int fila, int columna) {
		return (0 <= fila && fila < filas) && (0 <= columna && columna < columnas);
	}
	

	public void imprimirTablero() {
		for (int i = 0; i < filas; i++) {
	        for (int j = 0; j < columnas; j++) {
	            System.out.print("[ "+entidades[i][j].get_color()+" ]");
	        }
	        System.out.println();
	    }
	 }
	
	//---------------LO NUEVO-----------------
	public void asociar_entidades_logicas_y_graficas() {
		Entidad entidad;
			
		for (int f=0; f<filas; f++) {
			for (int c=0; c<columnas; c++) {
				entidad = entidades[f][c];
				miLogica.asociar_entidad_logica_y_grafica(entidad);
			}
		}
		
		for(Entidad entidad_a: entidades_asociadas) {
			miLogica.asociar_entidad_logica_y_grafica(entidad_a);
		}
	}
	
	public void reubicar(Entidad e) {
		int nueva_fila = e.get_fila();
		int nueva_columna = e.get_columna();
		entidades[nueva_fila][nueva_columna] = e;
	}
	
	public void agregar_entidad_y_asociada(Hielo g) {
		entidades[g.get_fila()][g.get_columna()] = g;
		entidades_asociadas.add(g.get_gema_interna());
	}
	
/*	private boolean intercambiar_entidades_y_transicionar(int fila_destino, int columna_destino) {
		int fila_origen = fJugador;
		int columna_origen = cJugador;
		Entidad entidad_origen, entidad_destino;
		EfectosDeTransicion efecto_intercambio = null;
		boolean movimientoValido = false;
		
		if (en_rango(fila_destino, columna_destino)) {	
			entidad_origen = entidades[fila_origen][columna_origen];
			entidad_destino = entidades[fila_destino][columna_destino];
			
			if (entidad_origen.es_posible_intercambiar(entidad_destino)) {
				cambiar_posicion_jugador(fila_destino, columna_destino);
				entidad_origen.intercambiar(entidad_destino);
				efecto_intercambio = calcular_efectos_por_intercambio(entidad_origen, entidad_destino);
				
				if (efecto_intercambio.existen_entidades_a_detonar()) {
					transicionar_proximo_estado(efecto_intercambio);
				}else{
					deshacer_intercambio(entidad_origen, entidad_destino, fila_origen, columna_origen);
				}
			}
			System.out.println("TABLERO despues de DETONAR y reemplazar");
			imprimirTablero();
			movimientoValido = true;
		}
		return movimientoValido;
	}*/
	
	private boolean intercambiar_entidades_y_transicionar(int filaDestino, int columnaDestino) {
	    int filaOrigen = fJugador;
	    int columnaOrigen = cJugador;
	    boolean movientosValido = true;
	    
	    if (!en_rango(filaDestino, columnaDestino)) {
	    	movientosValido = false;
	    }

	    Entidad entidadOrigen = movientosValido ? entidades[filaOrigen][columnaOrigen] : null;
	    Entidad entidadDestino = movientosValido ? entidades[filaDestino][columnaDestino] : null;

	    if (movientosValido && !entidadOrigen.es_posible_intercambiar(entidadDestino)) {
	    	movientosValido = false;
	    }

	    if(movientosValido) {
	    	realizarIntercambioYTransicion(entidadOrigen, entidadDestino, filaOrigen, columnaOrigen);
	    }

	    imprimirTablero();
	    return movientosValido;
	}

	private void realizarIntercambioYTransicion(Entidad entidadOrigen, Entidad entidadDestino, int filaOrigen, int columnaOrigen) {
		cambiar_posicion_jugador(entidadDestino.get_fila(),entidadDestino.get_columna());
	    entidadOrigen.intercambiar(entidadDestino);

	    EfectosDeTransicion efectoIntercambio = calcularEfectosPorIntercambio(entidadOrigen, entidadDestino);

	    if (efectoIntercambio.existen_entidades_a_detonar()) {
	        transicionar_proximo_estado(efectoIntercambio);
	    } else {
	        deshacerIntercambio(entidadOrigen, entidadDestino, filaOrigen, columnaOrigen);
	    }
	}

	private void deshacerIntercambio(Entidad entidadOrigen, Entidad entidadDestino, int filaOrigen, int columnaOrigen) {
		cambiar_posicion_jugador(filaOrigen, columnaOrigen);
	    entidadOrigen.intercambiar(entidadDestino);
	}

	private EfectosDeTransicion calcularEfectosPorIntercambio(Entidad entidadOrigen, Entidad entidadDestino) {
	    EfectosDeTransicion efectoIntercambio = new EfectosDeTransicion();

	    if (entidadOrigen.se_produce_match_con(entidadDestino)) {
	        manejarMatch(efectoIntercambio, entidadOrigen);
	        manejarMatch(efectoIntercambio, entidadDestino);
	    } else {
	        manejarCombos(efectoIntercambio, entidadOrigen, entidadDestino);
	    }

	    return efectoIntercambio;
	}

	private void manejarCombos(EfectosDeTransicion efectoIntercambio, Entidad entidadOrigen, Entidad entidadDestino) {
	    LinkedList<Entidad> listaEntidadesEnCombo = buscarCombos(
	        entidadOrigen.get_fila(), entidadOrigen.get_columna(),
	        entidadDestino.get_fila(), entidadDestino.get_columna()
	    );

	    listaEntidadesEnCombo.forEach(e -> manejarMatch(efectoIntercambio, e));
	}

	public boolean en_rango(int fila, int columna){
		boolean en_rango_fila = (0 <= fila) && (fila < filas);
		boolean en_rango_columna = (0 <= columna) && (columna < columnas);
		return (en_rango_fila && en_rango_columna);
	}
	
	protected void cambiar_posicion_jugador(int nueva_fila, int nueva_columna) {
		fJugador = nueva_fila;
		cJugador = nueva_columna;
	}
	
	protected EfectosDeTransicion calcular_efectos_por_intercambio(Entidad entidad_origen, Entidad entidad_destino){
		EfectosDeTransicion efecto_transicion = new EfectosDeTransicion();
		if (entidad_origen.se_produce_match_con(entidad_destino)) {
			manejarMatch(efecto_transicion, entidad_origen);
	        manejarMatch(efecto_transicion, entidad_destino);
		}else {
			
			// To DO: incorporar logica asociada a control de match, generador de potenciadores, etc. 
			LinkedList<Entidad> listaEntidadesEnCombo = buscarCombos(entidad_origen.get_fila(),entidad_origen.get_columna(),entidad_destino.get_fila(),entidad_destino.get_columna());
			for(int pos = 0; pos < listaEntidadesEnCombo.size(); pos ++) {
				manejarMatch(efecto_transicion,listaEntidadesEnCombo.get(pos));
			}
		}
		
		return efecto_transicion;
	}
	
	private LinkedList<Entidad> buscarCombos(int f1, int c1, int f2, int c2) {
		LinkedList<Entidad> listaCombos = new LinkedList<>();
		
		if (esPosicionValida(f1, c1) && esPosicionValida(f2, c2)) {
			listaCombos.addAll(buscarCombosEnFila(f1, c1));
			listaCombos.addAll(buscarCombosEnColumna(f1, c1));
			listaCombos.addAll(buscarCombosEnFila(f2, c2));
			listaCombos.addAll(buscarCombosEnColumna(f2, c2));
			
			miLogica.actualizarObjetivos(listaCombos);
			
			return listaCombos;
		} else {
			throw new IllegalArgumentException("Posición inválida en buscarCombos");
		}
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
		} else {
			combosEnFila.clear(); // No hay combos, limpiar la lista
		}

		return combosEnFila;
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
		} else {
			combosEnColumna.clear(); // No hay combos, limpiar la lista
		}

		return combosEnColumna;
	}
	
	private void manejarMatch(EfectosDeTransicion efecto_transicion, Entidad entidad) {
	    efecto_transicion.agregar_entidad_a_detonar_y_reemplazar(entidad);
	    efecto_transicion.agregar_entidad_de_reemplazo(crearGemaNormalRandom(entidad.get_fila(), entidad.get_columna()));
	}

	private GemaNormal crearGemaNormalRandom(int fila, int columna) {
	    // Lógica para crear una GemaNormal con color aleatorio
	    return new GemaNormal(this, fila, columna,new Color(new Random().nextInt(7) + 1), false,skin);
	}

	
	
	protected void transicionar_proximo_estado(EfectosDeTransicion efecto_transicion) {
		detonar(efecto_transicion.entidades_a_detonar());
		agregar_entidades_nuevas(efecto_transicion.entidades_a_incorporar());
		//aplicar_caida_y_reubicar(efecto_transicion.entidades_a_reemplazar());
	}
	
	protected void detonar(List<Entidad> entidades_a_detonar) {
		System.out.println("TABLERO antes de DETONAR");
		imprimirTablero();
		for(Entidad e: entidades_a_detonar) {
			miLogica.agregarScore(e.get_score());
			e.detonar(this); //Segun fede e.detonar();
		}
		System.out.println("TABLERO despues de DETONAR");
		imprimirTablero();
	}
	
	protected void agregar_entidades_nuevas(List<Entidad> entidades_a_incorporar) {
		for(Entidad e: entidades_a_incorporar) {
			entidades[e.get_fila()][e.get_columna()] = e;
			miLogica.asociar_entidad_logica_y_grafica(e);
			e.mostrar();
		}
	}
	
	protected void aplicar_caida_y_reubicar(List<Entidad> entidades_a_reemplazar) {
		// To DO.
	}

	@Override
	public NotificadorDeEntidadesConTiempo obtenerObserver() {
		return notificadorDeGemasConTemporizador;
	}
}
