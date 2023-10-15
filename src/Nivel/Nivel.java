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
		mapeoDeObjetivos = new HashMap<>(); // Inicializamos la lista de objetivos
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
        for (int id : mapeoDeObjetivos.keySet()) {
            Objetivos objetivo = mapeoDeObjetivos.get(id);
            if (objetivo != null) {
                System.out.println("ID: " + id + ", Objetivo: " + objetivo.getCantGemas() + " " + objetivo.getTipoGema());
            }
        }
    }
	
	
	
	/*
	Cambie obtenerInfoObjetivos para que devuelva un array de strings para poder 
	ponerlo en 3 JLabels distintos mas facil 

	Y comenté el texto porque al aparecer 3 veces hace que sea demasiado largo y rompe
	los grids, una vez solucionemos eso lo cambiamos
	- Nacho
	*/
	public String[] obtenerInfoObjetivos() {
		String[] devolver = new String[3];
	    StringBuilder[] info = new StringBuilder[3];
		info[0] = new StringBuilder("");
		info[1] = new StringBuilder("");
		info[2] = new StringBuilder("");

	    for (Map.Entry<Integer, Objetivos> entry : mapeoDeObjetivos.entrySet()) {
	        Objetivos objetivo = entry.getValue();
	        info[0].append("a:"/*"Cantidad de Gemas: "*/).append(objetivo.getCantGemas());
			info[1].append("b:"/*"Tipo de Gema: "*/);
			info[2].append(buscarTipo(objetivo.getTipoGema())).append(" ");
	    }

		devolver[0] = info[0].toString();
		devolver[1] = info[1].toString();
		devolver[2] = info[2].toString();
	    return devolver;
	}

	private String buscarTipo(int tipoGema) {
		return miLogica.obtenerTipoDeGema(tipoGema);
	}

	public void actualizarObjetivos(LinkedList<Celda> l) {
		int i = 1;
		for(int pos = 0; pos<l.size()-1; pos++) {
			if(l.get(pos) != null) {
				int tipoGema = l.get(pos).getColorEntidad();
				for (Objetivos objetivo : mapeoDeObjetivos.values()) {
					if (objetivo.getTipoGema() == tipoGema && objetivo.getCantGemas() > 0 && !objetivo.estaCumplido()) {
						objetivo.aumentarProgreso(i);
						//miLogica.getCantidadObjetivo(objetivo.getCantGemas());
						i++;
						System.out.println("Nivel cantGemas :: "+objetivo.getCantGemas());
					}else {
						i = 1;
					}
				}
				
			}else {
				System.out.println("Nivel :: Soy nulo");
			}
		}
	}

}
