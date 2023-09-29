package Entidades;

import GUI.entidadGrafica;
import Logica.entidadLogica;

public abstract class entidad implements entidadLogica, enfocable,intercambiable{
protected int fila;
protected int columna;
protected boolean enfocada;
protected String imagen;
protected entidadGrafica entidadG;

public entidad(int f, int c, String ri) 
{
	fila=f;
	columna=c;
	enfocada=false;
	imagen=ri;
	};

public void setEntidadGrafica(entidadGrafica eg)
{
	entidadG=eg;
};

public String getImagneRep()
{
	return imagen;
};

public int getFila()
{
	return fila;
};

public int getColumna()
{
	return columna;
}

public boolean destruir()
{
	
}
}
