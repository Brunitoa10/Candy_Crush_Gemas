package Nivel;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

import GUI.Celda;
import Logica.Logica;


public class Nivel {
	//Atributos
	protected int movimientos,totalmovimientos;
	protected Map<Integer, Objetivos> mapeoDeObjetivos; 
	protected Map<Objetivos, Boolean> mapeo; 
	protected int fila_inicial_jugador;
	protected int columna_inicial_jugador;
	protected int tiempo;
	protected int vidas;
	protected Logica miLogica;

	//Constructor
	public Nivel(int posX, int posY,Logica l) {
		fila_inicial_jugador = posX;
		columna_inicial_jugador = posY;
		mapeoDeObjetivos = new HashMap<>();
		mapeo = new HashMap<>();
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
	
	public int getCantidadDeObjetivos() {
		return mapeoDeObjetivos.size();
	}
	
	public void agregarObjetivo(int id, Objetivos objetivo) {
		mapeoDeObjetivos.put(id, objetivo);
    }

    public Objetivos obtenerObjetivo(int id) {
        return mapeoDeObjetivos.get(id);
    }
	
	public void setTiempo(int tiempo) {
		this.tiempo = tiempo;
	}

	public void restarMovimientos() {
	    movimientos--;
	    System.out.println("Movientos :: "+movimientos);
	    System.out.println("Cantidad de objetivos :: "+mapeoDeObjetivos.size());
	    if (movimientos == 0) {
	    	restarVidas();
	    	System.out.println("Nivel :: vidas "+vidas);
	    	if(vidas>0) {
				miLogica.notificarDerrotaPorMovimientos();
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

	public String[] obtenerInfoObjetivos() {
	    String[] info = new String[miLogica.getCantidadDeObjetivos() * 4];

	    int i = 0;

	    for (Objetivos objetivo : mapeoDeObjetivos.values()) {
	        info[i++] = "Destruir " + objetivo.getCantGemas() + " gemas de tipo: ";
	        info[i++] = buscarTipo(objetivo.getTipoGema()) + " ";
	        info[i++] = String.valueOf(objetivo.getCantGemas());
	        info[i++] = String.valueOf(objetivo.getTipoGema());
	    }

	    return info;
	}
	
	private String buscarTipo(int tipoGema) {
		return miLogica.obtenerTipoDeGema(tipoGema);
	}
	
	public void actualizarObjetivos(LinkedList<Celda> l) {
		int progreso = 0, tipoGema = 0; 
		
		for (int pos = 0; pos < l.size() - 1; pos++) {
			if (l.get(pos) != null) {
				tipoGema = l.get(pos).getColorEntidad();
				for (Objetivos objetivo : mapeoDeObjetivos.values()) {
					if (!objetivo.estaCumplido() && objetivo.getTipoGema() == tipoGema) {
						progreso++;
						objetivo.aumentarProgreso(progreso);
						if(objetivo.estaCumplido()) {
							mapeo.put(objetivo, true);
						}
						miLogica.notificar_actualizacion_objetivos(objetivo.getCantGemas(), objetivo.getTipoGema());
					}	
				}
			} else {
				System.out.println("Nivel :: Soy nulo");
			}
		}
		if(mapeo.size() == mapeoDeObjetivos.size()) {
			mapeo.clear();
			//miLogica.notificarVictoriaPorObjetivos();
			miLogica.cambiarNivel();
		}
	
	}  
}