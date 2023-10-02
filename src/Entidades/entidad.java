package Entidades;

import GUI.entidadGrafica;
import Logica.EntidadLogica;

public abstract class entidad implements EntidadLogica, enfocable,intercambiable{
protected int fila;
protected int columna;
protected boolean enfocada;
protected String[] imagenes;
protected entidadGrafica entidadG;

public entidad(int f, int c, String ri) 
{
	fila=f;
	columna=c;
	enfocada=false;
	
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
	return false;
	
}

}
