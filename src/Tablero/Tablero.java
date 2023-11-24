package Tablero;

import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;

import Entidades.*;
import EstrategiaMatch.*;
import GUI.*;
import GeneradorEntidades.EntidadFactory;
import GeneradorEntidades.EntidadFactoryRegistry;
import Logica.*;
import Score.AdministradordeScore;
import Timer.ObservableTimer;


public class Tablero implements TableroJuego{

	protected Logica miLogica;
	protected Entidad entidades[][];
	protected int filas,columnas;
	protected int fJugador,cJugador;
	protected List<Entidad> entidades_asociadas;
	protected String skin;
	protected AdministradordeScore miAdministradordeScore;
	protected AdministradorEstrategias miAdministradordeEstrategias;

	// Define un mapa para asociar las direcciones de intercambio con las funciones correspondientes
	private final Map<Integer, BiFunction<Integer, Integer, Boolean>> operaciones = new HashMap<>();

	// Define un mapa para asociar las direcciones de movimientos con las funciones correspondientes
	private final Map<Integer, BiConsumer<Integer, Integer>> movimientos = new HashMap<>();

	private static final Map<String, EntidadFactory> entidadFactories = EntidadFactoryRegistry.getEntidadFactories();

	public Tablero(Logica l) {
		miLogica = l;

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
						nombreGema = entidades[i][j].get_imagenes_representativas()[0];
					}
				}
			}
		}
		return nombreGema;
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
		System.out.println("Estado tablero en intercambiar_entidades_y_transicionar");
	    imprimirTablero();
	    return movientosValido;
	}

	private void realizarIntercambioYTransicion(Entidad entidadOrigen, Entidad entidadDestino, int filaOrigen, int columnaOrigen) {
		cambiar_posicion_jugador(entidadDestino.get_fila(),entidadDestino.get_columna());
	    entidadOrigen.intercambiar(entidadDestino);

	    EfectosDeTransicion efectoIntercambio = calcular_efectos_por_intercambio(entidadOrigen, entidadDestino);

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

	protected EfectosDeTransicion calcular_efectos_por_intercambio(Entidad entidad_origen, Entidad entidad_destino){
		EfectosDeTransicion efecto_transicion = new EfectosDeTransicion();
		if (entidad_origen.se_produce_match_con(entidad_destino)) {
			manejarMatch(efecto_transicion,entidad_origen);
			manejarMatch(efecto_transicion,entidad_destino);
		}else {
			// To DO: incorporar logica asociada a control de match, generador de potenciadores, etc. 
			Resultado resultado_origen = miAdministradordeEstrategias.buscar_match(entidad_origen);
			if (resultado_origen != null) {
				// Se produce un match, aplicar efectos correspondientes
				if(!resultado_origen.get_Gemas_a_romper().isEmpty())
				{for (Entidad entidad : resultado_origen.get_Gemas_a_romper()) {
					manejarMatch(efecto_transicion,entidad);
				}}
				if(resultado_origen.get_resultado()!=null)
				{
					agregar_entidad(resultado_origen.get_resultado());
				}
			}
			
			Resultado resultado_destino = miAdministradordeEstrategias.buscar_match(entidad_destino);
			if (resultado_destino != null) {
				// Se produce un match, aplicar efectos correspondientes
				if(!resultado_destino.get_Gemas_a_romper().isEmpty()) {
				for (Entidad entidad : resultado_destino.get_Gemas_a_romper()) {
					manejarMatch(efecto_transicion,entidad);
				} }
				if(resultado_destino.get_resultado()!=null)
				{
					agregar_entidad(resultado_destino.get_resultado());
				}
			}
		}
		return efecto_transicion;
	}
	
	private void manejarMatch(EfectosDeTransicion efecto_transicion, Entidad entidad) {
	    efecto_transicion.agregar_entidad_a_detonar_y_reemplazar(entidad);
		if(get_entidad(entidad.get_fila(),entidad.get_columna()).get_color()==Color.TRANSPARENTE)
		{
	    efecto_transicion.agregar_entidad_de_reemplazo(crearGemaNormalRandom(entidad.get_fila(),entidad.get_columna()));
		}
	}

	private GemaNormal crearGemaNormalRandom(int fila, int columna) {
        EntidadFactory gemaNormalFactory = entidadFactories.get("n");

        if (gemaNormalFactory != null) {
            String[] cadena = generarCadenaAleatoria(2);
            return (GemaNormal) gemaNormalFactory.crear(this, fila, columna, cadena, skin);
        } else {
            throw new IllegalStateException("Fábrica de GemaNormal no encontrada en el registro.");
        }
    }

    private String[] generarCadenaAleatoria(int tamano) {
        String[] cadena = new String[tamano];
        for (int i = 0; i < tamano; i++) {
            int numeroAleatorio = new Random().nextInt(6) + 1;
            cadena[i] = String.valueOf(numeroAleatorio);
        }
        return cadena;
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
	
	/*private LinkedList<Entidad> buscarCombos(int f1, int c1, int f2, int c2) {
		miLogica.actualizarObjetivos(listaCombos);
	}*/

	protected void transicionar_proximo_estado(EfectosDeTransicion efecto_transicion) {
		detonar(efecto_transicion.entidades_a_detonar());
		//agregar_entidades_nuevas(efecto_transicion.entidades_a_incorporar());
		System.out.println("Antes de caida");
		imprimirTablero();
		aplicar_caida_y_reubicar(efecto_transicion.entidades_a_reemplazar());
		System.out.println("Despues de caida ");
		imprimirTablero();
	}
	
	protected void detonar(List<Entidad> entidades_a_detonar) {
		System.out.println("TABLERO antes de DETONAR");
		imprimirTablero();
		for(Entidad e: entidades_a_detonar) {
			miLogica.agregarScore(e.get_score());
			//miLogica.actualizarObjetivos(entidades_a_detonar);
			e.detonar(this);
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
    	// Obtener las columnas afectadas
		Set<Integer> columnasAfectadas = new HashSet<>();
		for (Entidad entidad : entidades_a_reemplazar) {
			columnasAfectadas.add(entidad.get_columna());
		}

		// Iterar sobre cada columna afectada
		for (int columna : columnasAfectadas) {
			// Iterar desde abajo hacia arriba en la columna
			for (int fila = filas - 1; fila >= 0; fila--) {
				Entidad entidadActual = entidades[fila][columna];

				// Verificar si la entidad actual debe ser reemplazada
				if (entidades_a_reemplazar.contains(entidadActual)) {
					caerEntidad(fila,columna);
					entidades[filas - 1][columna] = crearGemaNormalRandom(filas - 1, columna);
					reubicarEntidad(entidadActual, 0, columna);
				}
			}
    	}
	}

	private void caerEntidad(int puntero, int columna) {
		// Mover las entidades hacia arriba en la columna
		for (int filaDestino = 0; filaDestino < filas - 1; filaDestino++) {
			entidades[filaDestino][columna] = entidades[filaDestino + 1][columna];
		}
	}
	
	private void reubicarEntidad(Entidad entidad, int fila, int columna) {
		System.out.println("Antes de reubicar");
		this.imprimirTablero();
		// Actualizar la posición de la entidad
	
		// Vincular la entidad con la lógica y la interfaz gráfica
		//miLogica.asociar_entidad_logica_y_grafica(entidad);
		
		entidad.intercambiar_Caida(fila, columna);

		// Vincular la entidad con la lógica y la interfaz gráfica
		miLogica.asociar_entidad_logica_y_grafica(entidad);
		
		System.out.println("Despues de reubicar");
		this.imprimirTablero();
	}
	
	@Override
	public ObservableTimer obtenerObserver() {
		return miLogica.getTimer();
	}

    public void crearAdministradorEstrategias(LinkedList<Estrategias> estrategias) {
		miAdministradordeEstrategias= new AdministradorEstrategias(estrategias,this);
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
}
