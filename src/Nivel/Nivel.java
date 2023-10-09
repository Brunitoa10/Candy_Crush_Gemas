package Nivel;

import java.util.ArrayList;

import GUI.GUI;
import Logica.Logica;
import Tablero.Tablero;


public class Nivel {
	//Atributos
	protected int movimientos,totalmovimientos;
	protected ArrayList<Objetivos> objetivo; // ArrayList para almacenar los objetivos
	protected int fila_inicial_jugador;
	protected int columna_inicial_jugador;
	protected int tiempo;
	protected int vidas;
	//Dudoso
	private Logica miLogica;
	private Tablero miTablero;

	//Constructor
	public Nivel(int posX, int posY,Logica l) {
		fila_inicial_jugador = posX;
		columna_inicial_jugador = posY;
		vidas = 3;
		this.objetivo = new ArrayList<>(); // Inicializamos la lista de objetivos
		miLogica = l;
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
	//------------------------------------------
	public int getTotalMovimientos() {
		return movimientos;
	}
	public void setTotalMovimientos(int m) {
		 totalmovimientos = m;
	}
	public int getTotalTiempo() {
		return tiempo;
	}
	public void setTotalTiempo(int t) {
		 tiempo = t;
	}
	//----------------------------------------
	public int getTiempo() {
		return tiempo;
	}
	
	public int getVidas() {
		return vidas;
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

	public void restarMovimientos() {
	    movimientos--;
	    System.out.println("Movientos :: "+movimientos);
	    if (movimientos == 0) {
	    	restarVidas();
	    	System.out.println("Nivel :: vidas "+vidas);
	    	miLogica.notificarDerrotaPorMovimientos();
	    	if(vidas>0) {
	    		miLogica.reiniciarNivel();
	    	}else {
	    		miLogica.notificarDerrotaPorVidas();
	    	}
	    	
            
	        /*if (verificarObjetivosCumplidos()) {
	            miLogica.notificarVictoriaPorObjetivos(); // Notificar a la lógica si se cumplieron los objetivos.
	        } else {
	        	restarVidas();
	            miLogica.notificarDerrotaPorMovimientos(); // Notificar a la lógica si no se cumplieron los objetivos.
	        }*/
	    }
	}

	public void restarVidas() {
		vidas--;
		if(vidas == 0) {
			miLogica.notificarDerrotaPorVidas();
		}
	}

	public boolean verificarObjetivosCumplidos() {
	    int cantGemas = 0, tipoGema = 0, cantidadGemasTipo = 0;
	     //Si anda mejorar codigo y ciclo de corte
		for (Objetivos objetivo : objetivo) {
	        cantGemas = objetivo.getCantGemas();
	        tipoGema = objetivo.getTipoGema();
	        cantidadGemasTipo = contarGemasDelTipo(tipoGema);
	        System.out.println("cantGemas "+cantGemas+" tipoGema "+tipoGema+" cantidadGemasTipo "+cantidadGemasTipo);
	        if (cantidadGemasTipo < cantGemas) {
	            return false; // El objetivo no está cumplido.
	        }
	    }
	    return true; // Todos los objetivos están cumplidos.
	}

	private int contarGemasDelTipo(int tipoGema) {
	    int contador = 0;
	    for (int i = 0; i < miTablero.getFila(); i++) {
	        for (int j = 0; j < miTablero.getColumna(); j++) {
	            if (miTablero.getEntidad(i, j).obtenerColor() == tipoGema) {
	                contador++;
	            }
	        }
	    }
	    return contador;
	}

	public void setVidas(int vida) {
		this.vidas = vida;
	}
}
