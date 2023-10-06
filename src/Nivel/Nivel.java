package Nivel;

import java.util.ArrayList;


public class Nivel {
	//Atributos
	protected int movimientos;
	protected ArrayList<Objetivos> objetivo; // ArrayList para almacenar los objetivos
	protected int fila_inicial_jugador;
	protected int columna_inicial_jugador;
	protected int tiempo;

	//Constructor
	public Nivel(int posX, int posY) {
		fila_inicial_jugador = posX;
		columna_inicial_jugador = posY;
		//movimientos = tiempo = 0; 
		this.objetivo = new ArrayList<>(); // Inicializamos la lista de objetivos
	}

	//Metodos
	public int getFilaInicialJugador() {
		return fila_inicial_jugador;
	}

	public int getColumnaInicialJugador() {
		return columna_inicial_jugador;
	}

	// Método para establecer los movimientos
	public void setMovimientos(int movimientos) {
		this.movimientos = movimientos;
	}

	// Método para establecer la posición del jugador
	public void setPosicionJugador(int posX, int posY) {
		fila_inicial_jugador = posX;
		columna_inicial_jugador = posY;
	}

	public int getMovimientos() {
		return movimientos;
	}

	public int getTiempo() {
		return tiempo;
	}
	
	// Método para agregar un objetivo
	public void agregarObjetivo(int cantGemas, int tipoGema) {
		objetivo.add(new Objetivos(cantGemas, tipoGema));
	}

	public int sizeObjetivos() {
		return objetivo.size();
	}
	public Objetivos getObjetivo(int indice) {
		return objetivo.get(indice);
    }
	public void setTiempo(int tiempo) {
		this.tiempo = tiempo;
	}

	




}
