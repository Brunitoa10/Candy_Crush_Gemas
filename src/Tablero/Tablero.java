package Tablero;

import Entidades.Entidad;
import GUI.Celda;
import GUI.GUI;
import Logica.Logica;
import Logica.Color;
import Logica.EntidadLogica;

import java.util.Iterator;
import java.util.LinkedList;

public class Tablero {
	protected Logica miLogica;
    protected GUI miGui;
	protected Celda t[][]; //[f][c]
	int filas;
	int columnas;
	int fJugador;
	int cJugador;
	
	/*[fila,columna]
	 * 
	 * [5,0][5,1][5,2][5,3]
	 * [4,0][4,1][4,2][4,3]
	 * [3,0][3,1][3,2][3,3]
	 * [2,0][2,1][2,2][2,3]
	 * [1,0][1,1][1,2][1,3]
	 * [0,0][0,1][0,2][0,3]
	 */
	
	public Tablero(Logica l,GUI g) {
		miLogica = l;
		miGui = g;
	}
	
	public void resetearTablero(int f, int c) {
	    try {
	        t = new Celda[f][c];
	        filas = f;
	        columnas = c;
	        
	        for (int i = 0; i < filas; i++) {
	            for (int j = 0; j < columnas; j++) {
	            	
	                t[i][j] = new Celda(miGui,null, 60); //ERROR :: LE ESTAS PASANDO NULL A UNA ENTIDAD
	            }
	        }
	    } catch (Exception e) {
	        System.out.println("Tablero :: Error al resetear " + e.getMessage());
	    }
	   }
	
	public Entidad getEntidad(int f, int c) {
		Entidad entidad = null;
		if(en_rango(f,c)) {
			entidad =  t[f][c].getEntidad();
		}else {
			System.out.println("Tablero :: fuera de rango en get entidad ");
		}
		return entidad;
	}
	
	public void setEntidad(Entidad e) throws Exception {
		int tmpFil = e.getFila(), tmpCol = e.getColumna();
		if(en_rango(tmpFil,tmpCol) && e != null) {
			t[tmpFil][tmpCol].setEntidad(e);}
		else {
			if(en_rango(tmpFil,tmpCol)) {
				throw new Exception("Tablero :: Fuera de rango en setEntidad ");
			}else {
				throw new Exception("Tablero :: Entidad null en setEntidad ");
			}
		}
	}
	
	public int getFila() {
		return filas;
	}
	
	public int getColumna() {
		return columnas;
	}
	
	public void agregarEntidad(Entidad e) throws Exception {
		int tmpFil = e.getFila(), tmpCol = e.getColumna();
		if(en_rango(tmpFil,tmpCol) && e != null) {
			t[tmpFil][tmpCol].setEntidad(e);
		}else {
			if(en_rango(tmpFil,tmpCol)) {
				throw new Exception("Tablero :: Fuera de rango en agregarEntidad ");
			}else {
				throw new Exception("Tablero :: Entidad null en agregarEntidad ");
			}
		}
	}
	
	public void fijarJugador(int f, int c){
		if(en_rango(f,c)) {
			fJugador = f;
		    cJugador = c;
		}else {
			System.out.println("Tablero :: fuera de rango en fijarJugador() ");
		}
	}
	
	/* mueve el cursor del jugador, en direccion 0 derecha, 1 abajo, 2 izquierda, 3 arriba*/
	public void moverJugador(int dir) {
		
		switch(dir) {
		case 0:
			if(cJugador < columnas -1) {
				t[fJugador][cJugador].notificarCeldaDesenfocar();
				cJugador++;
				t[fJugador][cJugador].notificarCeldaEnfocar();
			}
		break;
		case 1:
			if(fJugador >0) {
				t[fJugador][cJugador].notificarCeldaDesenfocar();
				fJugador--;
				t[fJugador][cJugador].notificarCeldaEnfocar();
			}	
		break;
		case 2:
			if(cJugador > 0) {
				t[fJugador][cJugador].notificarCeldaDesenfocar();
				cJugador--;
				t[fJugador][cJugador].notificarCeldaEnfocar();
			}
			
		break;
		case 3:
			if(fJugador < columnas -1) {
				t[fJugador][cJugador].notificarCeldaDesenfocar();
				fJugador++;
				t[fJugador][cJugador].notificarCeldaEnfocar();
			}
		break;
		default: System.out.println("mover jugador(): direccion incorrecta");
		}
	}
	
	/* intercambia dede la posicion del cursor a direccion 0 derecha, 1 abajo, 2 izquierda, 3 arriba
	 * en caso de no ser posible por out of bounds, no hace nada*/
	public void intercambiar(int dir) {
		Entidad aux;
		switch(dir) {
		case 0:
			if(cJugador < columnas -1) {
				if(t[fJugador][cJugador].getEntidad().esPosibleInrecambiar() && t[fJugador][cJugador+1].getEntidad().esPosibleInrecambiar()) {
					aux =t[fJugador][cJugador].getEntidad();
					t[fJugador][cJugador].setEntidad(t[fJugador][cJugador+1].getEntidad());
					t[fJugador][cJugador+1].setEntidad(aux);
					//manejarColisiones(CheckCruz(fJugador,cJugador,fJugador,cJugador+1));  ���I M P L E M E N T A R!!!
					}}
		break;
		case 1:
			if(fJugador>0) {
				if(t[fJugador][cJugador].getEntidad().esPosibleInrecambiar() && t[fJugador-1][cJugador].getEntidad().esPosibleInrecambiar()) {
					aux =t[fJugador][cJugador].getEntidad();
					t[fJugador][cJugador].setEntidad(t[fJugador-1][cJugador].getEntidad());
					t[fJugador-1][cJugador].setEntidad(aux);
					//manejarColisiones(CheckCruz(fJugador,cJugador,fJugador-1,cJugador));  ���I M P L E M E N T A R!!!
					}}
		break;
		case 2:
			if(cJugador>0) {
				if(t[fJugador][cJugador].getEntidad().esPosibleInrecambiar() && t[fJugador][cJugador-1].getEntidad().esPosibleInrecambiar()) {
					aux =t[fJugador][cJugador].getEntidad();
					t[fJugador][cJugador].setEntidad(t[fJugador][cJugador-1].getEntidad());
					t[fJugador][cJugador-1].setEntidad(aux);
					//manejarColisiones(CheckCruz(fJugador,cJugador,fJugador,cJugador-1));  ���I M P L E M E N T A R!!!
					}}
			
		break;
		case 3:
			if(fJugador<filas -1 ) {
				if(t[fJugador][cJugador].getEntidad().esPosibleInrecambiar() && t[fJugador-1][cJugador].getEntidad().esPosibleInrecambiar()) {
					aux =t[fJugador][cJugador].getEntidad();
					t[fJugador][cJugador].setEntidad(t[fJugador-1][cJugador].getEntidad());
					t[fJugador-1][cJugador].setEntidad(aux);
					//manejarColisiones(CheckCruz(fJugador,cJugador,fJugador-1	,cJugador+1));  ���I M P L E M E N T A R!!!
					}}
		break;
		default: System.out.println("mover jugador(): direccion incorrecta");
		}		
	}
	
	public void intercambiarSinCheck(int f1, int c1, int f2, int c2) {
		Entidad aux;
		if((0<=f1 && f1 <filas) && (0<=c1 && c1 <columnas) && (0<=f2 && f2 <filas) && (0<=c2 && c2 <columnas )){
			aux = t[f1][c1].getEntidad();
			t[f1][c1].setEntidad(t[f2][c2].getEntidad());
			t[f2][c2].setEntidad(aux);
		}
		else System.out.println("out of bounds en intercambiarSinCheck");
	}
	
	public LinkedList<Entidad> checkExhaustivo() {return null;}
	
	public boolean caida() {return false;}
	
	private LinkedList<Entidad> CheckCruz(int f1, int c1, int f2, int c2) {
		//optimizar
		LinkedList<Entidad> toReturn = new LinkedList<Entidad>();
		if((0<=f1 && f1 <filas) && (0<=c1 && c1 <columnas) && (0<=f2 && f2 <filas) && (0<=c2 && c2 <columnas )){
			LinkedList<Entidad> aux;
			Iterator<Entidad> it;
			if(f1 == f2) {
				//caso dos filas iguales

				aux = checkFila(f1);
				it = aux.iterator();
				while (it.hasNext()) {
					toReturn.addLast(it.next());}
				
				aux = checkColumna(c1);
				it = aux.iterator();
				while (it.hasNext()) {
					toReturn.addLast(it.next());}
				
				aux = checkColumna(c2);
				it = aux.iterator();
				while (it.hasNext()) {
					toReturn.addLast(it.next());}
			}
			else {
				aux = checkFila(f1);
				it = aux.iterator();
				while (it.hasNext()) {
					toReturn.addLast(it.next());}
				
				aux = checkFila(f2);
				it = aux.iterator();
				while (it.hasNext()) {
					toReturn.addLast(it.next());}
				
				aux = checkColumna(c2);
				it = aux.iterator();
				while (it.hasNext()) {
					toReturn.addLast(it.next());}
			}
		return toReturn;
		}
		else {
			System.out.println("error en checkCruz() de tablero");
			return null;
		}
	}
	
		
		private LinkedList<Entidad> checkFila(int f) {
			LinkedList<Entidad> toReturn = new LinkedList<Entidad>();
			int combo = 0;
			for(int i = 1; i< filas; i++) {
				if(t[f][i-1].getColorEntidad() == t[f][i].getColorEntidad() && t[f][i].getColorEntidad() != 7&& t[f][i].getColorEntidad() != 8 ) {
					
					combo++;
					if(combo == 2) {
						toReturn.addLast(t[f][i-2].getEntidad());
						toReturn.addLast(t[f][i-1].getEntidad());
						toReturn.addLast(t[f][i].getEntidad());}
					if(combo> 2) {
						toReturn.addLast(t[f][i].getEntidad());}}		
				else {
					if(combo>=2) {
						toReturn.addLast(null);}
					combo = 0;
					}
			}//fin for
			if(!toReturn.isEmpty() &&toReturn.getLast() != null) {
				toReturn.addLast(null);}
			return toReturn;
			
		}
		private LinkedList<Entidad> checkColumna(int c) {
			LinkedList<Entidad> toReturn = new LinkedList<Entidad>();
			int combo = 0;
			for(int i = 1; i< columnas; i++) {
				if(t[i-1][c].getColorEntidad() == t[i][c].getColorEntidad() && t[i][c].getColorEntidad() != 7 && t[i][c].getColorEntidad() != 8 ) {
					
					combo++;
					if(combo == 2) {
						toReturn.addLast(t[i-2][c].getEntidad());
						toReturn.addLast(t[i-1][c].getEntidad());
						toReturn.addLast(t[i][c].getEntidad());}
					if(combo> 2) {
						toReturn.addLast(t[i][c].getEntidad());}}		
				else {
					if(combo>=2) {
						toReturn.addLast(null);}
					combo = 0;
					}
			}//fin for
			if(!toReturn.isEmpty() && toReturn.getLast() != null ) {
				toReturn.addLast(null);}
			return toReturn;
			
		}
		
		private boolean manejarColisiones(LinkedList<Entidad> l) {
			return false;
			
		}
		private boolean en_rango(int nf, int nc){
			return (((nf >= 0) && (nf < filas)) && ((nc >= 0) && (nc < columnas)));
		}

}
