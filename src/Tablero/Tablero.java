package Tablero;

import Entidades.entidad;
import GUI.Celda;
import Logica.Logica;
import Logica.entidadLogica;
import java.util.LinkedList;

public class Tablero {
	protected Logica miLogica;
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
	
	public tablero(Logica l) {
		miLogica = l;
	}
	
	public void resetearTablero(int f, int c) {
		t = new Celda[f][c];
		filas = f;
		columnas = c;
	}
	
	public int getFila() {
		return filas;
	}
	public int getColumna() {
		return columnas;
	}
	public void agregarEntidad(int f, int c, entidadLogica e) {
		if((f>= 0 && f < filas) &&(c>= 0 && c< columnas)) {
			t[f][c].setEntidadLogica(e);}
	}
	
	public void fijarJugador(int f, int c) throws Exception {
		if((f>= 0 && f < filas) &&(c>= 0 && c< columnas)) {
			fJugador = f;
		    cJugador = c;}
		else System.out.println("out of bounds en fijarJugador()");}
	
	/* mueve el cursor del jugador, en direccion 0 derecha, 1 abajo, 2 izquierda, 3 arriba*/
	public void moverJugador(int dir) {
		
		//AGREGAR RESALTADO DE CELDAS
		
		switch(dir) {
		case 0:
			if(cJugador < columnas -1) {
				cJugador++;}
		break;
		case 1:
			if(fJugador >0) {
				fJugador--;}	
		break;
		case 2:
			if(cJugador > 0) {
				cJugador--;}
			
		break;
		case 3:
			if(fJugador < columnas -1) {
				fJugador++;}
		break;
		default: System.out.println("mover jugador(): direccion incorrecta");
		}
	}
	
	/* intercambia dede la posicion del cursor a direccion 0 derecha, 1 abajo, 2 izquierda, 3 arriba
	 * en caso de no ser posible por out of bounds, no hace nada*/
	LinkedList intercambiar(int dir) {
		entidadLogica aux;
		switch(dir) {
		case 0:
			if(cJugador < columnas -1) {
				if(t[fJugador][cJugador].getEntidadLogica().esPosibleInrecambiar() && t[fJugador][cJugador+1].getEntidadLogica().esPosibleIntercambiar()) {
					aux =t[fJugador][cJugador].getEntidadLogica();
					t[fJugador][cJugador].setEntidadLogica(t[fJugador][cJugador+1].getEntidadLogica());
					t[fJugador][cJugador+1].setEntidadLogica(aux);}}
		break;
		case 1:
			if(fJugador>0) {
				if(t[fJugador][cJugador].getEntidadLogica().esPosibleInrecambiar() && t[fJugador-1][cJugador].getEntidadLogica().esPosibleIntercambiar()) {
					aux =t[fJugador][cJugador].getEntidadLogica();
					t[fJugador][cJugador].setEntidadLogica(t[fJugador-1][cJugador].getEntidadLogica());
					t[fJugador-1][cJugador].setEntidadLogica(aux);}}
		break;
		case 2:
			if(cJugador>0) {
				if(t[fJugador][cJugador].getEntidadLogica().esPosibleInrecambiar() && t[fJugador][cJugador-1].getEntidadLogica().esPosibleIntercambiar()) {
					aux =t[fJugador][cJugador].getEntidadLogica();
					t[fJugador][cJugador].setEntidadLogica(t[fJugador][cJugador-1].getEntidadLogica());
					t[fJugador][cJugador-1].setEntidadLogica(aux);}}
			
		break;
		case 3:
			if(fJugador<filas -1 ) {
				if(t[fJugador][cJugador].getEntidadLogica().esPosibleInrecambiar() && t[fJugador-1][cJugador].getEntidadLogica().esPosibleIntercambiar()) {
					aux =t[fJugador][cJugador].getEntidadLogica();
					t[fJugador][cJugador].setEntidadLogica(t[fJugador-1][cJugador].getEntidadLogica());
					t[fJugador-1][cJugador].setEntidadLogica(aux);}}
		break;
		default: System.out.println("mover jugador(): direccion incorrecta");
	}
		
	
		
	}
	
	public LinkedList checkExhaustivo() {}
	
	public boolean caida() {}
	
	private LinkedList CheckCruz(int f1, int c1, int f2, int c2) {
		//optimizar
		if((0<=f1 && f1 <filas) && (0<=c1 && c1 <columnas) && (0<=f2 && f2 <filas) && (0<=c2 && c2 <columnas)) {
			LinkedList<entidadLogica> toReturn = new LinkedList<entidadLogica>();
			int combo = 0;
			
			if(f1 == f2) {
				for(int i = 1; i< filas; i++) {
					if(t[f1][i-1].getColorEntidad() == t[f1][i].getColorEntidad()) {
						
						combo++;
						if(combo == 2) {
							toReturn.addLast(t[f1][i-2].getEntidadLogica());
							toReturn.addLast(t[f1][i-1].getEntidadLogica());
							toReturn.addLast(t[f1][i].getEntidadLogica());}
						if(combo> 2) {
							toReturn.addLast(t[f1][i].getEntidadLogica());}}		
				else {
					if(combo>=2) {
						toReturn.addLast(null);
						combo = 0;}
					else combo = 0;}}
				if(toReturn.getLast() != null) {
					toReturn.addLast(null);
				}
			
			
			
			
			}else {
				
			}
		}else {
			System.out.println("error en checkCruz() de tablero");
			return null;
		}
		
		
		
	}

}
