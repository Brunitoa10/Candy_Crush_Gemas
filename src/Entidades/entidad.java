package Entidades;

import GUI.entidadGrafica;
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
	}

public void setEntidadGrafica(entidadGrafica eg)
{
	entidadG=eg;
}

public String get_imagen_representativa() {
	int indice = 0;
	indice += (enfocada ? 1 : 0);
	return imagenes[indice];
}

public int getFila()
{
	return fila;
}

public int getColumna()
{
	return columna;
}

public boolean destruir()
{
	return true;
	entidadG.notificarse_cambio_estado;
	
}

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
