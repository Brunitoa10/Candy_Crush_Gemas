package Nivel;

import Tablero.Tablero;

public class NivelPiolita {
	private Tablero tablero;
	private int movimientosDisponibles;
	private int tiempoDisponible;
	private String objetivo;
	//Atributos
	protected int fila_inicial_jugador;
	protected int columna_inicial_jugador;
	
	
	
	//Metodos
	public int getFilaInicialJugador() {
		return fila_inicial_jugador;
	}
	
	public int getColumnaInicialJugador() {
		return columna_inicial_jugador;
	}
	public NivelPiolita(Tablero tablero, int movimientosDisponibles, int tiempoDisponible, String objetivo,int f, int c) {
		this.tablero = tablero;
		this.movimientosDisponibles = movimientosDisponibles;
		this.tiempoDisponible = tiempoDisponible;
		this.objetivo = objetivo;
	}

	// Getters y setters

	public Tablero getTablero() {
		return tablero;
	}

	public int getMovimientosDisponibles() {
		return movimientosDisponibles;
	}

	public int getTiempoDisponible() {
		return tiempoDisponible;
	}

	public String getObjetivo() {
		return objetivo;
	}

	// Otros métodos según necesidad

}
