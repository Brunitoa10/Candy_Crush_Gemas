package Nivel;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import Entidades.Entidad;
import EstrategiaMatch.Estrategias;
import Logica.EntidadLogica;
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
	protected static Logica miLogica;
	protected LinkedList<Estrategias> estrategias;

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
	    if (movimientos == 0) {
	    	restarVidas();
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

	public void setEstrategias(LinkedList<Estrategias> estrategias)
	{
		miLogica.setEstrategias(estrategias);
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
	
	public void actualizarObjetivos(List<Entidad> listaCombos) {
	    int progreso = 0, tipoGema = 0; 
	    
	    for (EntidadLogica entidad : listaCombos) {
	        if (entidad != null) {
	            tipoGema = entidad.get_color(); // Obtener el tipo de gema de la entidad
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
	        }
	    }
	    
	    if(mapeo.size() == mapeoDeObjetivos.size()) {
	        mapeo.clear();
	        miLogica.notificarVictoriaPorObjetivos();
	    }
	}

	public static Logica getLogica() {
		return miLogica;
	}
}