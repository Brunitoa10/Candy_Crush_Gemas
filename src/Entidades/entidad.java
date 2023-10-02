package Entidades;

import GUI.entidadGrafica;
<<<<<<< HEAD
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
=======
import Logica.EntidadLogica;
import Logica.entidadLogica;
import logica.color;

public abstract class entidad implements entidadLogica, enfocable{
protected int fila;
protected int columna;
protected boolean enfocada;
protected String[] imagenes;
protected entidadGrafica entidadG;
protected color color;

public entidad(int f, int c, String ri,color c) 
{
	fila=f;
	columna=c;
	enfocada=false;
	color=c;
	imagenes= new String[2];
	imagenes[1]=ri
>>>>>>> d71e29f09cab0098a65cff60b9bcc80adaa5b96a
	}

	public void setEntidadGrafica(entidadGrafica eg){
		entidadG=eg;
	}

<<<<<<< HEAD
	public String getImagenesRep() {
		int indice = 0;
		indice += (enfocada ? 1 : 0);
		return imagenes[indice];
	}
=======
public String getImagenesRep() {
	int indice = 0;
	indice += (enfocada ? 1 : 0);
	return imagenes[indice];
}

public void setImagenesRep(int i,String g)
{
	imagenes[i]=g;
}

public int getFila()
{
	return fila;
}
>>>>>>> d71e29f09cab0098a65cff60b9bcc80adaa5b96a

	public int getFila(){
		return fila;
	}

<<<<<<< HEAD
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
=======
public boolean destruir()
{
	return true;
	entidadG.notificarse_cambio_estado;
	
}
>>>>>>> d71e29f09cab0098a65cff60b9bcc80adaa5b96a

public color getColor()
{
	return color;
}

public void intercambiarPosicion(int f,int c)
{
	columna=col;
	fila=f;
	entidadg.notificarse_intercambio_posicion;
}
}
