package Entidades;

import GUI.entidadGrafica;
import Logica.entidadLogica;

public abstract class entidad implements entidadLogica, enfocable,intercambiable{
	protected int fila;
	protected int columna;
	protected boolean enfocada;
	protected String[] imagenes;
	protected entidadGrafica entidadG;

	public entidad(int f, int c, String ri){
		fila=f;
		columna=c;
		enfocada=false;
	}

	public void setEntidadGrafica(entidadGrafica eg){
		entidadG=eg;
	}

	public String getImagenesRep() {
		int indice = 0;
		indice += (enfocada ? 1 : 0);
		return imagenes[indice];
	}

	public int getFila(){
		return fila;
	}

	public int getColumna(){
		return columna;
	}

	public boolean destruir(){
		return false;
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

}
