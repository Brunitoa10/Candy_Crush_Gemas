package Nivel;

import java.util.ArrayList;


public class Nivel {
	//Atributos
    protected int movimientos;
    protected ArrayList<Objetivos> objetivo; // ArrayList para almacenar los objetivos
	protected int fila_inicial_jugador;
	protected int columna_inicial_jugador;
	
	//Constructor
    public Nivel(int posX, int posY) {
    	fila_inicial_jugador = posX;
    	columna_inicial_jugador = posY;
        this.movimientos = 15; 
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

 // Método para agregar un objetivo
    public void agregarObjetivo(int cantGemas, int tipoGema) {
        objetivo.add(new Objetivos(cantGemas, tipoGema));
    }

    //MEJORAR ESTA IDEA
    // Método para obtener los objetivos
    public ArrayList<Objetivos> getObjetivos() {
        return objetivo;
    }
	
   
}
