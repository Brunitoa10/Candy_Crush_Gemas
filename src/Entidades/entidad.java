package Entidades;

import GUI.entidadGrafica;
import Logica.EntidadLogica;

public abstract class Entidad implements EntidadLogica, enfocable,intercambiable{
protected int fila;
protected int columna;
protected boolean enfocada;
protected String imagen;
protected entidadGrafica entidadG;

public Entidad(int f, int c, String ri) 
{
	fila=f;
	columna=c;
	enfocada=false;
	imagen=ri;
	}

public void setEntidadGrafica(entidadGrafica eg)
{
	entidadG=eg;
}

public String getImagneRep()
{
	return imagen;
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
	
}
}
