package Entidades;

import GUI.EntidadGrafica;
import Logica.EntidadLogica;
import Logica.color;

public abstract class Entidad implements EntidadLogica, Enfocable{
	protected int fila;
	protected int columna;
	protected boolean enfocada;
	protected String[] imagenes;
	protected EntidadGrafica entidadG;
	protected color color;

	public Entidad(int f, int c, String ri, color col)  {
		fila=f;
		columna=c;
		enfocada=false;
		color=col;
		cargarImagenesRepresentativas(ri);
	}

	public void setEntidadGrafica(EntidadGrafica eg){
		entidadG=eg;
	}

	
	public String getImagenesRep() {
		int indice = 0;
		indice += (enfocada ? 1 : 0);
		return imagenes[indice];
	}

	public color obtenerColor() {
		return color;
	}

	public void setImagenesRep(int i,String g){
		imagenes[i]=g;
	}


	public int getFila(){
		return fila;
	}

	public int getColumna(){
		return columna;
	}

	public void intercambiaPosicion(int nf, int nc) {
		fila = nf;
		columna = nc;
		entidadG.notificarse_intercambio_posicion();
	}

	public boolean enfocar() {
		enfocada = true;
		entidadG.notificarse_cambio_estado();
		return true;
	}

	@Override
	public void desenfocar() {
		enfocada = false;
		entidadG.notificarse_cambio_estado();
	}
	
	public boolean destruir(){
	return true;
	}

	public boolean esPosibleIntercambiar(Entidad e) {
		return e.puedeRecibir(this);
	}


	public color getColor(){
		return color;
	}

	protected void cargarImagenesRepresentativas(String ri) {
		imagenes = new String [2];
		imagenes[0] = ri + color +".png";
		imagenes[1] = ri + color +"-cursor.png";
	}
}