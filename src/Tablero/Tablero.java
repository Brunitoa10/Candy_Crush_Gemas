package Tablero;

import Entidades.*;
import GUI.*;
import Logica.Logica;
import java.util.*;

public class Tablero {
	
	//--------[ATRIBUTOS DE INSTANCIA]------------
	
	protected Logica miLogica;
    protected GUI miGui;
	protected Celda t[][]; //[f][c]
	protected int filas;
	protected int columnas;
	protected int fJugador;
	protected int cJugador;
	
	/*[fila,columna]
	 * 
	 * [5,0][5,1][5,2][5,3]
	 * [4,0][4,1][4,2][4,3]
	 * [3,0][3,1][3,2][3,3]
	 * [2,0][2,1][2,2][2,3]
	 * [1,0][1,1][1,2][1,3]
	 * [0,0][0,1][0,2][0,3]
	 */
	
	//-----------[METODOS PRIVADOS]--------------------
	
	//RETORNA UNA UN ARRAY DE LISTAS COMO LinkedList<Entidad>[combos horizontales][combos veritcales]
	private LinkedList<Celda> CheckCruz(int f1, int c1, int f2, int c2) {
		//System.out.println("columnas "+ columnas+" filas "+ columnas+" f1 c1 "+ f1+" "+c1+" f2 c2 "+ f2+" "+c2);
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
			miLogica.actualizarObjetivos(toReturn);
		return toReturn;
		}
		else {
			System.out.println("error en checkCruz() de tablero");
			return null;
		}
	}
	
	private int colorAleatorio(int cotaInferior, int cotaSuperior) {
		return (int)(Math.random()*cotaSuperior+cotaInferior);
	}

	private boolean intecambiarPriv(int f1,int c1, int f2, int c2) {
		Entidad aux;
		aux =t[f1][c1].getEntidad();
		boolean toReturn;
		
		t[f1][c1].setEntidad(t[f2][c2].getEntidad());
		t[f2][c2].setEntidad(aux);
		
		t[f1][c1].getEntidad().intercambiarPosicion(f1,c1);
		t[f2][c2].getEntidad().intercambiarPosicion(f2,c2);
		
		t[f1][c1].notificarCeldaEnfocar();
		t[f2][c2].notificarCeldaDesenfocar();


		System.out.println("INTERCAMBIADO: "+"["+f1+"]"+"["+c1+"]" +" y "+"["+f2+"]" +"["+c2+"]");
		
		boolean movValido = manejarColisiones(CheckCruz(f1,c1,f2,c2),t[f1][c1],t[f2][c2],0); 

		if(movValido) {
			do {
				caida();
			}while(checkExhaustivo());
			toReturn = true;
		}
		else {intercambiarSinCheck(f2,c2,f1,c1);
			t[f1][c1].notificarCeldaEnfocar();
			t[f2][c2].notificarCeldaDesenfocar();
			toReturn = false;
		
		}
		return toReturn;
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
		 * en la posici�n hacia donde se movi� el caramelo que gener� la
		 * formaci�n del mismo.
		 * Si el caramelo rayado es el resultado de un movimiento horizontal, las rayas ser�n
		 * horizontales, si es el resultado de un movimiento vertical, ser�n verticales y el resultado
		 * es aleatorio durante las cascadas m�s grandes
		 * 
		 * envuelto
		 * Formaci�n: 5 o 6 caramelos regulares en forma de T, L o +.
		 * Ubicaci�n de aparici�n: en la posici�n hacia donde se movi� el caramelo que gener� la
		 * formaci�n del mismo.
		 */
		
		if(!l.isEmpty()) {
			Iterator<Celda> it = l.iterator();
			Celda c = null;
			int cf = -1;
			int cc = -1; // -1 para detectar out of bounds
			Celda aux= null;
			int color1 = -1;
			int color2 = -2;
			int colorT = -3;
			int combo = 0;
			int direccRay;
			boolean posibleT = false;//marca si puede pasar que estemos en un combo t, se genera bomba(envuelta?)
			
			while(it.hasNext()) {//[1],[1],[1],[1],null,[4][4][4][4],null,[1][1][1]
				
				if(c == null) {//principio de combo
					c = it.next();
					cf = c.getEntidad().getFila();
					cc = c.getEntidad().getColumna();
					colorT = c.getEntidad().obtenerColor();
					posibleT = false;
					if(color1 == -1) {
						color1 = c.getEntidad().obtenerColor();
					}else 
						if(color1 == c.getEntidad().obtenerColor()) {
							posibleT = true;
						}else 
							if(color2 == -2) 
								color2 = c.getEntidad().obtenerColor();
							else if(color2 == c.getEntidad().obtenerColor()) {
								    posibleT = true;}
					destruirRocas(c.getEntidad());// destruiur rocas
					c.getEntidad().romper();
					combo = 1;
					if(c == c1 || c ==c2) {
						if(posibleT) {
							System.out.println("generar bomba!!!!!");
							c.setEntidad(new GemaEnvuelta(cf,cc,colorT));
							System.out.println(c.getEntidad().getClass().getName());
							//generar bomba en la celda c
							}
						else aux = c;
					}
								
				}//end if (c == null)
				else {//medio del combo
					c = it.next();
					
					if(c != null) {
						
						if(cf == c.getEntidad().getFila()) {
							//se mueve a travez de las filas
							direccRay = 0;}
						else direccRay = 1;//se mueve a travez de las columnas
						
						cf= c.getEntidad().getFila();
						cc = c.getEntidad().getColumna();
						
						destruirRocas(c.getEntidad());//destruir rocas
						c.getEntidad().romper();
						combo++;
						if(c == c1 || c ==c2) {
							if(posibleT) {
								System.out.println("generar bomba!!!!!");
								c.setEntidad(new GemaEnvuelta(cf,cc,colorT));
								//generar bomba en la celda c
							}
							else aux = c;
						}
						if(combo>=4 && !posibleT) {
							System.out.println("generar rayada!!!!!");
							aux.setEntidad(new GemaRayada(cf,cc, colorT, direccRay));
							//System.out.println(aux.getEntidad().getClass().getName());
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
	
	private void intercambiarSinCheck(int f1, int c1, int f2, int c2) {
		Entidad aux;
		aux =t[f1][c1].getEntidad();
		t[f1][c1].setEntidad(t[f2][c2].getEntidad());
		t[f2][c2].setEntidad(aux);
	}
	
	private void intercambiarCaida(int f1, int c1, int f2, int c2) {
		Entidad aux = t[f1][c1].getEntidad();
		EntidadGrafica auxG = aux.getEGrafica();
		
		t[f1][c1].setEntidad(t[f2][c2].getEntidad());
		t[f2][c2].setEntidad(aux);
		
		t[f1][c1].getEntidad().intercambiarCaida(f1,c1);
		t[f1][c1].getEntidad().setEntidadGrafica(t[f2][c2].getEntidad().getEGrafica());
		
		t[f2][c2].getEntidad().intercambiarCaida(f2,c2);
		t[f2][c2].getEntidad().setEntidadGrafica(auxG);
		

		t[f1][c1].notificarCeldaEnfocar();
		t[f2][c2].notificarCeldaDesenfocar();
		
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
	            	 //Dejar el parametro j y el parametro 0
	                t[i][j] = new Celda(miGui,new GemaNormal(i,j,0), 70); 
	            }
	        }
	    } catch (Exception e) {
	        System.out.println("Tablero :: Error al resetear " + e.getMessage());
	    }
	   }
	
	
	public void printTable() {
		System.out.println("");
		for(int i = 0; i<filas; i++) {
			for (int j = 0; j<columnas ; j++) {
				System.out.print("[" +t[i][j].getEntidad().obtenerColor() +"]");
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
System.out.print("me movi de ("+fJugador+","+cJugador+")");
				t[fJugador][cJugador].notificarCeldaDesenfocar();
				fJugador--;
				t[fJugador][cJugador].notificarCeldaEnfocar();
System.out.println("a ("+fJugador+","+cJugador+")");
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

		System.out.println("mirando: " +t[fJugador][cJugador].getEntidad().getClass().getName() );
	}
	
	/* intercambia dede la posicion del cursor a direccion 0 derecha, 1 abajo, 2 izquierda, 3 arriba
	 * en caso de no ser posible por out of bounds, no hace nada*/
	public boolean intercambiar(int dir) {
		boolean toReturn=false;
		switch(dir) {
			case GUI.DERECHA:
					if(cJugador < columnas -1) {
						if(t[fJugador][cJugador].getEntidad().esPosibleIntercambiar(t[fJugador][cJugador+1].getEntidad()) && t[fJugador][cJugador+1].getEntidad().esPosibleIntercambiar(t[fJugador][cJugador].getEntidad())) {					
							toReturn = intecambiarPriv(fJugador,cJugador,fJugador,cJugador+1);
							}}
				break;
				case GUI.ARRIBA:
					if(fJugador>0) {
						System.out.println("arriba");
						if(t[fJugador][cJugador].getEntidad().esPosibleIntercambiar(t[fJugador-1][cJugador].getEntidad()) && t[fJugador-1][cJugador].getEntidad().esPosibleIntercambiar(t[fJugador][cJugador].getEntidad())) {					
							toReturn = intecambiarPriv(fJugador,cJugador,fJugador-1,cJugador);
							}}
				break;
				case GUI.IZQUIERDA:
					if(cJugador>0) {
						System.out.println("izquierda");
						if(t[fJugador][cJugador].getEntidad().esPosibleIntercambiar(t[fJugador][cJugador-1].getEntidad()) && t[fJugador][cJugador-1].getEntidad().esPosibleIntercambiar(t[fJugador][cJugador].getEntidad())) {					
							toReturn =intecambiarPriv(fJugador,cJugador,fJugador,cJugador-1);
							}}
					
				break;
				case GUI.ABAJO:
					if(fJugador<filas -1 ) {
						System.out.println("abajo");
						if(t[fJugador][cJugador].getEntidad().esPosibleIntercambiar(t[fJugador+1][cJugador].getEntidad()) && t[fJugador+1][cJugador].getEntidad().esPosibleIntercambiar(t[fJugador][cJugador].getEntidad())) {
							toReturn = intecambiarPriv(fJugador,cJugador,fJugador+1,cJugador);
							}}
				break;
			default: System.out.println("mover jugador(): direccion incorrecta");
		}
	    return toReturn;
	}
	
	
	public boolean checkExhaustivo() {
		int color = -1;
		int combo;
		//Celda c;
		boolean combeado = false;
		
		for (int i = 0; i< filas; i++) {
			combo = 1;
			for (int j = 0;j<columnas; j++) {//a travez de las filas quiza
				if(t[i][j].getEntidad().obtenerColor() != color)
					combo = 1;
				if(combo ==3) {
					t[i][j].getEntidad().romper();
					t[i][j-1].getEntidad().romper();
				    t[i][j-2].getEntidad().romper();
				    combeado = true;}
				if(combo == 4) {
					t[i][j].getEntidad().romper();
					t[i][j].setEntidad(new GemaRayada(i,j,color,0));}
				combo ++;
				color = t[i][j].getEntidad().obtenerColor();
			}
		}
		for (int i = 0; i< columnas; i++) {
			combo = 1;
			for (int j = 0;j<filas; j++) {//a travez de las columnas quiza
				if(t[j][i].getEntidad().obtenerColor() != color)
					combo = 1;
				if(combo ==3) {
					t[j][i].getEntidad().romper();
					t[j-1][i].getEntidad().romper();
				    t[j-2][i].getEntidad().romper();
				    combeado = true;}
				if(combo == 4) {
					t[j][i].getEntidad().romper();
					t[j][i].setEntidad(new GemaRayada(j,i,color,1));}
				combo ++;
				color = t[j][i].getEntidad().obtenerColor();
			}
		}
		
		
		
		return combeado;
	}
	
	public boolean caida() {
		//verificar que la fila 0 es la de arriba
		EntidadGrafica egrafica;
		boolean caido = false;
		for (int i = 0; i<filas; i++) {
			for (int j = 0; j<columnas; j++) {
				if(t[i][j].getEntidad().obtenerColor() == 0) {
					caido = true;
					if(i == 0) {
						egrafica = t[i][j];

						t[i][j].setEntidad(new GemaNormal(i,j,colorAleatorio(1,6)));
						t[i][j].getEntidad().setEntidadGrafica(egrafica);
						t[i][j].notificarse_cambio_estado();
					}
					else
						intercambiarCaida(i-1,j,i,j);//VERIFICAR
				}
			}
		}
		miLogica.actualizarTablero();

		if(caido) {
			caida();
			return true;}
			
		else return false;
	}
		
	public void limpiarTablero() {
		for (int i = 0; i < filas; i++) {
	        for (int j = 0; j < columnas; j++) {
	            t[i][j].setEntidad(new GemaNormal(i,j,0));
	        }
	    }
	 }

	//Verifica si hay rocas para romper en la proximidad
	private void destruirRocas(Entidad e)
	{
	  int fila=e.getFila();
	  int columna=e.getColumna();
	  //si no esta en la primer fila manda mensaje
	  if(fila!=0) 
	  {
		t[fila-1][columna].getEntidad().explosionAdyacente();
	  }
	  //si no esta en la primer columna manda mensaje
	  if(columna!=0) 
	  {
		t[fila][columna-1].getEntidad().explosionAdyacente();
	  }
	  //si no esta en la ultima fila manda mensaje
	  if(fila!=filas-1)
	  {
		t[fila+1][columna].getEntidad().explosionAdyacente();
	  }
	  //si no esta en la ultima columna manda mensaje
	  if(columna!=columnas-1)
	  {
		t[fila][columna+1].getEntidad().explosionAdyacente();
	  }
	}

	//Metodo Agregado por bruno
	public String obtenerTipoGema(int tipoGema) {
		boolean encontre = false;
		String nombreGema = " ";
		for(int i = 0; i<filas && !encontre; i++) {
			for (int j = 0; j<columnas && !encontre; j++) {
				if (t[i][j].getEntidad().obtenerColor() == tipoGema) {
					encontre = true; 
					if(encontre) {
						nombreGema = t[i][j].getEntidad().getImagenRep();
					}
				}
			}
		}
		return nombreGema;
	}


}
