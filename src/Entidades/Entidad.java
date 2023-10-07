package Entidades;

import GUI.EntidadGrafica;
import Logica.EntidadLogica;

public abstract class Entidad implements EntidadLogica, Enfocable{
	protected int fila;
	protected int columna;
	protected boolean enfocada;
	protected String[] imagenes;
	protected EntidadGrafica entidadG;
	protected int color;


   //crea una instancia de Entidad
	protected  Entidad(int f, int c, String ri, int col)  {
		fila=f;
		columna=c;
		enfocada=false;
		color=col;
		cargarImagenesRepresentativas(ri);
	}

//asigna una entidad grafica a la Entidad
	public void setEntidadGrafica(EntidadGrafica eg){
		entidadG=eg;
	}

	//obtener la imagen dependiendo si esta enfocada o no dicha entidad
	public String getImagenesRep() {
		int indice = 0;
		indice += (enfocada ? 1 : 0);
		return imagenes[indice];
	}

	//obtener color de la entidad
	public int obtenerColor() {
		return color;
	}
	
	public void setFilaColumna(int f, int c) {
		fila = f;
		columna = c;}

	//setear manualmente las imagenes
	public void setImagenesRep(int i,String g){
		imagenes[i]=g;
	}

    //obtener la fila
	public int getFila(){
		return fila;
	}
    
	//obtener columna
	public int getColumna(){
		return columna;
	}

	//cambia la posicion de la entidad
	public void intercambiarPosicion(int nf, int nc) {
		fila = nf;
		columna = nc;
		entidadG.notificarse_intercambio_posicion();
	}


	//enfoca la entidad
	public boolean enfocar() {
		enfocada = true;
		entidadG.notificarse_cambio_estado();
		return true;
	}

	//desenfoca la entidad
	public void desenfocar() {
		enfocada = false;
		entidadG.notificarse_cambio_estado();
	}
	
    //envia un mensaje si se destruye
	public boolean destruir(){
	   System.out.println("destruido "+ this.color + "en: "+fila+","+columna );
	   return true;
	}

    //verifica si puede recibir dicha entidad para el cambio
	public boolean puedeRecibir(Entidad entidad) {
		return true;
	}

	//carga las imagenes de la entidad con el cursor y sin el cursor
	protected void cargarImagenesRepresentativas(String ri) {
		imagenes = new String [2];
		imagenes[0] = ri + color +".png";
		imagenes[1] = ri + color +"-cursor.png";
	}

	public abstract boolean esPosibleIntercambiar();
}