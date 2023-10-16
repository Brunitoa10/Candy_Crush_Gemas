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
		
		int array_lengths = (miLogica.getCantidadDeObjetivos()*4);
		String[] devolver = new String[array_lengths];
	    StringBuilder[] info = new StringBuilder[array_lengths];
		
		for(int i=0;i<info.length;i++) {
			info[i] = new StringBuilder("");
		}

		int i=-1;

	    for (Map.Entry<Integer, Objetivos> entry : mapeoDeObjetivos.entrySet()) {
			i++;
	        Objetivos objetivo = entry.getValue();
	        info[i].append("Destruir ").append(objetivo.getCantGemas());
			info[i].append(" gemas de tipo: ");
			i++;
			info[i].append(buscarTipo(objetivo.getTipoGema())).append(" ");
			i++;
			info[i].append(objetivo.getCantGemas());
			i++;
			info[i].append(objetivo.getTipoGema());
	    }

		for(int j=0;j<info.length;j++) {
			devolver[j] = info[j].toString();
		}
		
	    return devolver;
	}

	private String buscarTipo(int tipoGema) {
		return miLogica.obtenerTipoDeGema(tipoGema);
	}

	public void actualizarObjetivos(LinkedList<Celda> l) {
		int i = 1,tipoGema = 0;
		for(int pos = 0; pos<l.size()-1; pos++) {
			if(l.get(pos) != null) {
				tipoGema = l.get(pos).getColorEntidad();
				for (Objetivos objetivo : mapeoDeObjetivos.values()) {
					if (objetivo.getTipoGema() == tipoGema && objetivo.getCantGemas() > 0 && !objetivo.estaCumplido()) {
						objetivo.aumentarProgreso(i);
						/*if(mapeoDeObjetivos.get(l.get(pos)).estaCumplido()) {
							
						}*/
						i++;
					
						System.out.println("\nNivel cantGemas :: "+objetivo.getCantGemas());
						System.out.println("\ncumplido :: "+objetivo.estaCumplido());
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
