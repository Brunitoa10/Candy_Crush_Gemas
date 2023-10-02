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
	imagenes= new String[2];
	imagenes[1]=ri
	}

public void setEntidadGrafica(entidadGrafica eg)
{
	entidadG=eg;
}

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
