package Tablero;

import Entidades.Entidad;
import Entidades.GemaNormal;
import GUI.Celda;
import GUI.GUI;
import Logica.Logica;
import Logica.Color;
import Logica.EntidadLogica;

import java.util.Iterator;
import java.util.LinkedList;

public class Tablero {
	
	//--------[ATRIBUTOS DE INSTANCIA]------------
	
	protected Logica miLogica;
    protected GUI miGui;
	protected Celda t[][]; //[f][c]
	int filas;
	int columnas;
	int fJugador;
	int cJugador;
	
	/*[fila,columna]
	 * [0,0][0,1][0,2][0,3]
	 * [1,0][1,1][1,2][1,3]
	 * [2,0][2,1][2,2][2,3]
	 * [3,0][3,1][3,2][3,3]
	 * [4,0][4,1][4,2][4,3]
	 * [5,0][5,1][5,2][5,3]
	 */
	
	//-----------[METODOS PRIVADOS]--------------------
	
	//RETORNA UNA UN ARRAY DE LISTAS COMO LinkedList<Entidad>[combos horizontales][combos veritcales]
	private LinkedList<Celda> CheckCruz(int f1, int c1, int f2, int c2) {
		//optimizar
		LinkedList<Celda> toReturn = new LinkedList<Celda>(); 
		if((0<=f1 && f1 <filas) && (0<=c1 && c1 <columnas) && (0<=f2 && f2 <filas) && (0<=c2 && c2 <columnas )){
			LinkedList<Celda> aux;
			Iterator<Celda> it;
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

	private void intecambiarPriv(int f1,int c1, int f2, int c2) {
		Entidad aux;
		aux =t[f1][c1].getEntidad();
		t[f1][c1].setEntidad(t[f2][c2].getEntidad());
		t[f2][c2].setEntidad(aux);
		
		t[f1][c1].getEntidad().setFilaColumna(f1,c1);
		t[f2][c2].getEntidad().setFilaColumna(f2,c2);
		
		t[f1][c1].notificarCeldaEnfocar();
		t[f2][c2].notificarCeldaDesenfocar();

		t[f1][c1].notificarse_intercambio_posicion();
		t[f2][c2].notificarse_intercambio_posicion();
		
		t[f1][c1].notificarse_cambio_estado();
		t[f2][c2].notificarse_cambio_estado();
		
		t[f1][c1].getEntidad().intercambiarPosicion(f1,c1);
		t[f2][c2].getEntidad().intercambiarPosicion(f2,c2);
		
		System.out.println("INTERCAMBIADO: "+"["+f1+"]"+"["+c1+"]" +" y "+"["+f2+"]" +"["+c2+"]");
		
		manejarColisiones(CheckCruz(fJugador,cJugador,fJugador-1,cJugador+1),t[f1][c1],t[f2][c2],0); 
		
	}
	
	private LinkedList<Celda> checkFila(int f) {
		LinkedList<Celda> toReturn = new LinkedList<Celda>();
		int combo = 0;
		for(int i = 1; i< filas; i++) {
			if(t[f][i-1].getColorEntidad() == t[f][i].getColorEntidad() && t[f][i].getColorEntidad() != 7&& t[f][i].getColorEntidad() != 8 ) {
				
				combo++;
				if(combo == 2) {
					toReturn.addLast(t[f][i-2]);
					toReturn.addLast(t[f][i-1]);
					toReturn.addLast(t[f][i]);}
				if(combo> 2) {
					toReturn.addLast(t[f][i]);}}		
			else {
				if(combo>=2) {
					toReturn.addLast(null);}
				combo = 0;
				}
		}//fin for
		if(!toReturn.isEmpty() &&toReturn.getLast() != null) {
			toReturn.addLast(null);}
		return toReturn;	
		//no es un error que la lista tenga nulls, se manejan apropiadamente y son importantes para su estructura
	}
	
	private LinkedList<Celda> checkColumna(int c) {
		LinkedList<Celda> toReturn = new LinkedList<Celda>();
		int combo = 0;
		for(int i = 1; i< columnas; i++) {
			if(t[i-1][c].getColorEntidad() == t[i][c].getColorEntidad() && t[i][c].getColorEntidad() != 7 && t[i][c].getColorEntidad() != 8 ) {
				
				combo++;
				if(combo == 2) {
					toReturn.addLast(t[i-2][c]);
					toReturn.addLast(t[i-1][c]);
					toReturn.addLast(t[i][c]);}
				if(combo> 2) {
					toReturn.addLast(t[i][c]);}}		
			else {
				if(combo>=2) {
					toReturn.addLast(null);}
				combo = 0;
				}
		}//fin for
		if(!toReturn.isEmpty() && toReturn.getLast() != null ) {
			toReturn.addLast(null);}
		return toReturn;
		//no es un error que la lista tenga nulls, se manejan apropiadamente y son importantes para su estructura
	}
	
	private boolean manejarColisiones(LinkedList<Celda> l,Celda c1, Celda c2, int dir) {
		//RECORRER LA LISTA (los combos solo tienen 2 colores posibles, ambos diferentes)
	    //A CADA ELEMENTO DE LA LISTA APLICARLE .DESTRUIR()
		//SI HAY DOS COLORES IGUALES EN DIFERENTES SETS DE COMBOS GENERAR RAYADO EN CELDA 1
		//SI HAY OTRO MAS EN CELDA 2, NO ES POSIBLE QUE HAYA UN TERCERO
		//CUALQUIER PROBLEMA CON ESTO, CONSULTAR A VALEN
		
		//consigna:
		/* rayado 
		 * formacion:
		 * Exactamente 4 caramelos regulares seguidos. 
		 * en la posición hacia donde se movió el caramelo que generó la
		 * formación del mismo.
		 * Si el caramelo rayado es el resultado de un movimiento horizontal, las rayas serán
		 * horizontales, si es el resultado de un movimiento vertical, serán verticales y el resultado
		 * es aleatorio durante las cascadas más grandes
		 * 
		 * envuelto
		 * Formación: 5 o 6 caramelos regulares en forma de T, L o +.
		 * Ubicación de aparición: en la posición hacia donde se movió el caramelo que generó la
		 * formación del mismo.
		 */
		
		if(!l.isEmpty()) {
			Iterator<Celda> it = l.iterator();
			Celda c = null;
			Celda aux;
			int color1 = -1;
			int color2 = -2;
			int combo = 0;
			boolean posibleT = false;//marca si puede pasar que estemos en un combo t, se genera bomba(envuelta?)
			
			while(it.hasNext()) {//[1],[1],[1],[1],null,[4][4][4][4],null,[1][1][1]
				
				if(c == null) {//principio de combo
					c = it.next();
					posibleT = false;
					if(color1 == -1) {
						color1 = c.getEntidad().obtenerColor();
					}else 
						if(color1 == c.getEntidad().obtenerColor()) {
							posibleT = true;
						}else 
							if(color2 == -2) 
								color2 = c.getEntidad().obtenerColor();
							else if(color2 == c.getEntidad().obtenerColor())
								    posibleT = true;
					c.getEntidad().destruir();
					combo = 1;
					if(c == c1 || c ==c2) {
						if(posibleT) {
							System.out.println("generar bomba!!!!!");
							//generar bomba en la celda c
							}
						else aux = c;
					}
								
				}//end if (c == null)
				else {//medio del combo
					c = it.next();
					
					if(c != null) {
						c.getEntidad().destruir();
						combo++;
						if(c == c1 || c ==c2) {
							if(posibleT) {
								System.out.println("generar bomba!!!!!");
								//generar bomba en la celda c
							}
							else aux = c;
						}
						if(combo>=4 && !posibleT) {
							System.out.println("generar rayada!!!!!");
							//generar rayada en aux
							while(c!= null) {
								c = it.next();
							}
						}
					}
				}//end medio del combo	
			}//end while		
		    return true;	
		}//end if lista vacia  
		else return false;
	}
	
	private boolean en_rango(int nf, int nc){
		return (((nf >= 0) && (nf < filas)) && ((nc >= 0) && (nc < columnas)));
	}
	
	//-----------[METODOS PUBLICOS]--------------------
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
	            	 System.out.println("ciclo en reset tablero");
	                t[i][j] = new Celda(miGui,new GemaNormal(i,c,Color.TRANSPARENTE), 60); 
	            }
	        }
	        this.printTable();
	    } catch (Exception e) {
	        System.out.println("Tablero :: Error al resetear " + e.getMessage());
	    }
	   }
	
	public void agregarCeldaVacia(int i, int j) {
		t[i][j] = new Celda(miGui,new GemaNormal(i,j,Color.TRANSPARENTE), 60); 
	}
	
	public void printTable() {
		System.out.println("");
		for(int i = 0; i<filas; i++) {
			for (int j = 0; j<columnas ; j++) {
				System.out.print("["+ t[i][j].getEntidad().obtenerColor() +"]");
			}
			System.out.println("");
		}
	}
	
	public void asignarGUI(GUI g) {
		miGui = g;
		for(int i = 0; i<filas;i++) {
			for(int j = 0; j<columnas; j++) {
				t[i][j].setGUI(g);
			}
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
			t[tmpFil][tmpCol].setEntidad(e);
		}else {
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
		    t[fJugador][cJugador].notificarCeldaEnfocar();
		}else {
			System.out.println("Tablero :: fuera de rango en fijarJugador() ");
		}
	}
	
	/* mueve el cursor del jugador, en direccion 15003 derecha, 15001 abajo, 15002 izquierda, 15000 arriba*/
	public void moverJugador(int dir) {
		
		switch(dir) {
		case GUI.DERECHA:
			if(cJugador < columnas -1) {
				t[fJugador][cJugador].notificarCeldaDesenfocar();
				cJugador++;
				t[fJugador][cJugador].notificarCeldaEnfocar();
			}
		break;
		case GUI.ARRIBA:
			if(fJugador >0) {
				t[fJugador][cJugador].notificarCeldaDesenfocar();
				fJugador--;
				t[fJugador][cJugador].notificarCeldaEnfocar();
			}	
		break;
		case GUI.IZQUIERDA:
			if(cJugador > 0) {
				t[fJugador][cJugador].notificarCeldaDesenfocar();
				cJugador--;
				t[fJugador][cJugador].notificarCeldaEnfocar();
			}
			
		break;
		case GUI.ABAJO:
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
		
		switch(dir) {
		case GUI.DERECHA:
			if(cJugador < columnas -1) {
				if(t[fJugador][cJugador].getEntidad().esPosibleInrecambiar() && t[fJugador][cJugador+1].getEntidad().esPosibleInrecambiar()) {
					intecambiarPriv(fJugador,cJugador,fJugador,cJugador+1);
					}}
		break;
		case GUI.ARRIBA:
			if(fJugador>0) {
				if(t[fJugador][cJugador].getEntidad().esPosibleInrecambiar() && t[fJugador-1][cJugador].getEntidad().esPosibleInrecambiar()) {
					intecambiarPriv(fJugador,cJugador,fJugador-1,cJugador);
					}}
		break;
		case GUI.IZQUIERDA:
			if(cJugador>0) {
				if(t[fJugador][cJugador].getEntidad().esPosibleInrecambiar() && t[fJugador][cJugador-1].getEntidad().esPosibleInrecambiar()) {
					intecambiarPriv(fJugador,cJugador,fJugador,cJugador-1);
					}}
			
		break;
		case GUI.ABAJO:
			if(fJugador<filas -1 ) {
				if(t[fJugador][cJugador].getEntidad().esPosibleInrecambiar() && t[fJugador+1][cJugador].getEntidad().esPosibleInrecambiar()) {
					intecambiarPriv(fJugador,cJugador,fJugador+1,cJugador);
					}}
		break;
		default: System.out.println("mover jugador(): direccion incorrecta");
		}
	    printTable();
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
	
	
	
		
	
		
	

		

		
		

}
