package Tablero;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import DeteccionDeCombos.ComboHandler;
import Entidades.Entidad;
import Entidades.Gelatina;
import GeneradorEntidades.EntidadCaramelo;
import GeneradorEntidades.EntidadFactory;
import GestorTeclado.Movimiento;
import Logica.EfectosDeTransicion;
import Logica.Juego;

public class Tablero implements TableroJuego{
	
	protected Juego juego;
	protected Entidad [][] entidades;
	protected List<Entidad> entidades_asociadas;
	
	protected int filas;
	protected int columnas;
	
	protected int fila_jugador;
	protected int columna_jugador;

	protected final ComboHandler comboHandler;

	public Tablero(Juego juego) {
		this.juego = juego;
		filas = 0;
		columnas = 0;
		this.comboHandler = new ComboHandler(this);
	}
		
	// Operaciones para Tablero Juego (Tablero <-- Juego)
	
	public int get_filas() {
		return filas;
	}
	
	public int get_columnas() {
		return columnas;
	}
	
	public void asociar_entidades_logicas_y_graficas() {
		Entidad entidad;
			
		for (int f=0; f<filas; f++) {
			for (int c=0; c<columnas; c++) {
				entidad = entidades[f][c];
				juego.asociar_entidad_logica_y_grafica(entidad);
			}
		}
		
		for(Entidad entidad_a: entidades_asociadas) {
			juego.asociar_entidad_logica_y_grafica(entidad_a);
		}
	}
	
	public void resetar_tablero(int cant_filas, int cant_columnas) {
		filas = cant_filas;
		columnas = cant_columnas;
		fila_jugador = 0;
		columna_jugador = 0;
		entidades = new Entidad[cant_filas][cant_columnas];
		entidades_asociadas = new LinkedList<Entidad>();
	}
	
	public void agregar_entidad(Entidad e) {
		entidades[e.get_fila()][e.get_columna()] = e;
	}
	
	public void agregar_entidad_y_asociada(Gelatina g) {
		entidades[g.get_fila()][g.get_columna()] = g;
		entidades_asociadas.add(g.get_caramelo_interno());
	}
	
	public void fijar_jugador(int fila_destino, int columna_destino) {
		entidades[fila_destino][columna_destino].enfocar();
		entidades[fila_jugador][columna_jugador].desenfocar();
		fila_jugador = fila_destino;
		columna_jugador = columna_destino;
	}
	
	public void mover_jugador(int direccion) {
		switch(direccion) {
			case Movimiento.ABAJO:{
				mover_jugador(fila_jugador + 1, columna_jugador);
				break;
			}
			case Movimiento.ARRIBA:{
				mover_jugador(fila_jugador - 1, columna_jugador);
				break;
			}
			case Movimiento.IZQUIERDA:{
				mover_jugador(fila_jugador, columna_jugador - 1);
				break;
			}
			case Movimiento.DERECHA:{
				mover_jugador(fila_jugador, columna_jugador + 1);
				break;
			}
		}
	}
	
	public void intercambiar_entidades(int direccion) {
		switch(direccion) {
			case Movimiento.ABAJO:{
				intercambiar_entidades_y_transicionar(fila_jugador + 1, columna_jugador);
				break;
			}
			case Movimiento.ARRIBA:{
				intercambiar_entidades_y_transicionar(fila_jugador - 1, columna_jugador);
				break;
			}
			case Movimiento.IZQUIERDA:{
				intercambiar_entidades_y_transicionar(fila_jugador, columna_jugador - 1);
				break;
			}
			case Movimiento.DERECHA:{
				intercambiar_entidades_y_transicionar(fila_jugador, columna_jugador + 1);
				break;
			}
		}
	}
	
	// Operaciones para Tablero Notificable (Tablero <-- Entidad)
	
	public void reubicar(Entidad e) {
		int nueva_fila = e.get_fila();
		int nueva_columna = e.get_columna();
		entidades[nueva_fila][nueva_columna] = e;
	}
	
	// Operaciones locales

	private void mover_jugador(int fila_destino, int columna_destino) {
		if (en_rango(fila_destino,columna_destino)) {
			entidades[fila_destino][columna_destino].enfocar();
			entidades[fila_jugador][columna_jugador].desenfocar();
			fila_jugador = fila_destino;
			columna_jugador = columna_destino;
		}
	}
	
	private void intercambiar_entidades_y_transicionar(int fila_destino, int columna_destino) {
		int fila_origen = fila_jugador;
		int columna_origen = columna_jugador;
		Entidad entidad_origen, entidad_destino;
		EfectosDeTransicion efecto_intercambio;
		
		if (en_rango(fila_destino, columna_destino)) {	
			entidad_origen = entidades[fila_origen][columna_origen];
			entidad_destino = entidades[fila_destino][columna_destino];
			
			if (entidad_origen.es_posible_intercambiar(entidad_destino)) {
				juego.getNivelActual().decrementarContadorMovientos();
				cambiar_posicion_jugador(fila_destino, columna_destino);
				entidad_origen.intercambiar(entidad_destino);
				efecto_intercambio = calcular_efectos_por_intercambio(entidad_origen, entidad_destino);
				
				if (efecto_intercambio.existen_entidades_a_detonar()) {
					transicionar_proximo_estado(efecto_intercambio);
				}else{
					entidad_origen = entidades[fila_origen][columna_origen];
					entidad_destino = entidades[fila_destino][columna_destino];
					cambiar_posicion_jugador(fila_origen, columna_origen);
					entidad_origen.intercambiar(entidad_destino);
				}
			}
		}
	}
	
	protected boolean en_rango(int fila, int columna){
		boolean en_rango_fila = (0 <= fila) && (fila < filas);
		boolean en_rango_columna = (0 <= columna) && (columna < columnas);
		return (en_rango_fila && en_rango_columna);
	}
	
	protected void cambiar_posicion_jugador(int nueva_fila, int nueva_columna) {
		fila_jugador = nueva_fila;
		columna_jugador = nueva_columna;
	}

	protected EfectosDeTransicion calcular_efectos_por_intercambio(Entidad entidad_origen, Entidad entidad_destino){
		EfectosDeTransicion efecto_transicion = new EfectosDeTransicion();
		if (entidad_origen.se_produce_match_con(entidad_destino)) {
			manejarMatch(efecto_transicion, entidad_origen);
			manejarMatch(efecto_transicion, entidad_destino);
		}else {
			comboHandler.analizarCombos(efecto_transicion,entidad_origen,entidad_destino);
		}
		return efecto_transicion;
	}

	
	protected void transicionar_proximo_estado(EfectosDeTransicion efecto_transicion) {
		detonar(efecto_transicion.entidades_a_detonar());
		agregar_entidades_nuevas(efecto_transicion.entidades_a_incorporar());
		aplicar_caida_y_reubicar(efecto_transicion.entidades_a_reemplazar());
		
	}
	
	protected void detonar(List<Entidad> entidades_a_detonar) {
		for(Entidad e: entidades_a_detonar) {
			e.detonar();
		}
	}
	
	protected void agregar_entidades_nuevas(List<Entidad> entidades_a_incorporar) {
		for(Entidad e: entidades_a_incorporar) {
			entidades[e.get_fila()][e.get_columna()] = e;
			juego.asociar_entidad_logica_y_grafica(e);
			e.mostrar();
		}
	}
	
	protected void aplicar_caida_y_reubicar(List<Entidad> entidades_a_reemplazar) {

	}

	@Override
	public Entidad obtenerEntidad(int filaOrigen, int col) {
		return entidades[filaOrigen][col];
	}
	
	private void manejarMatch(EfectosDeTransicion efecto_transicion, Entidad entidad) {
	    // Lógica para el caso de match
		EntidadFactory entidadCaramelo = new EntidadCaramelo();
	    efecto_transicion.agregar_entidad_a_detonar_y_reemplazar(entidad);
		efecto_transicion.agregar_entidad_de_reemplazo(entidadCaramelo.crearEntidad(this, entidad.get_fila(), entidad.get_columna(), new Random().nextInt(4)+1));
	}

}
