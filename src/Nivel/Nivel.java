package Nivel;

import java.util.HashMap;
import java.util.Map;

import Logica.Logica;


public class Nivel {
	//Atributos
	protected int movimientos,totalmovimientos;
	protected Map<Integer, Objetivos> mapaDeObjetivos; 
	protected int fila_inicial_jugador;
	protected int columna_inicial_jugador;
	protected int tiempo;
	protected int vidas;
	protected Logica miLogica;

	//Constructor
	public Nivel(int posX, int posY,Logica l) {
		fila_inicial_jugador = posX;
		columna_inicial_jugador = posY;
		vidas = 3;
		this.mapaDeObjetivos = new HashMap<>(); // Inicializamos la lista de objetivos
		miLogica = l;
	}
		
	//Metodos
	public int getFilaInicialJugador() {
		return fila_inicial_jugador;
	}

	public int getColumnaInicialJugador() {
		return columna_inicial_jugador;
	}


	public void setMovimientos(int movimientos) {
		this.movimientos = movimientos;
	}

	public void setPosicionJugador(int posX, int posY) {
		fila_inicial_jugador = posX;
		columna_inicial_jugador = posY;
	}

	public int getMovimientos() {
		return movimientos;
	}
	
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

	public int getTiempo() {
		return tiempo;
	}
	
	public int getVidas() {
		return vidas;
	}
	

	public void agregarObjetivo(int id, Objetivos objetivo) {
		mapaDeObjetivos.put(id, objetivo);
    }

    public Objetivos obtenerObjetivo(int id) {
        return mapaDeObjetivos.get(id);
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
	    }
	}

	public void restarVidas() {
		vidas--;
		if(vidas == 0) {
			miLogica.notificarDerrotaPorVidas();
		}
	}


	public void setVidas(int vida) {
		this.vidas = vida;
	}

	public void imprimirObjetivos() {
        for (int id : mapaDeObjetivos.keySet()) {
            Objetivos objetivo = mapaDeObjetivos.get(id);
            if (objetivo != null) {
                System.out.println("ID: " + id + ", Objetivo: " + objetivo.getCantGemas() + " " + objetivo.getTipoGema());
            }
        }
    }
	
	public String obtenerInfoObjetivos() {
	    StringBuilder info = new StringBuilder();
	    for (Map.Entry<Integer, Objetivos> entry : mapaDeObjetivos.entrySet()) {
	        Objetivos objetivo = entry.getValue();
	        info.append("Cantidad de Gemas: ").append(objetivo.getCantGemas()).append(", Tipo de Gema: ").append(buscarTipo(objetivo.getTipoGema())).append(" ");
	    }
	    return info.toString();
	}

	private String buscarTipo(int tipoGema) {
		return miLogica.obtenerTipoDeGema(tipoGema);
	}
}
