package Nivel;

import Tablero.Tablero;

public class Nivel {
	//Atributos
	protected int fila_inicial_jugador;
	protected int columna_inicial_jugador;
	
	//Constructor
	public Nivel(int f, int c) {
		fila_inicial_jugador = f;
		columna_inicial_jugador = c;
	}
	
	//Metodos
	public int getFilaInicialJugador() {
		return fila_inicial_jugador;
	}
	
	public int getColumnaInicialJugador() {
		return columna_inicial_jugador;
	}

	
}
