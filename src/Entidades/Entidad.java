
package Entidades;


import GUI.EntidadGrafica;
import Logica.*;
import Tablero.*;

public abstract class Entidad implements EntidadLogica, Enfocable, Intercambiable, Matchable, Detonable
{
	protected int fila;
	protected int columna;
	protected boolean enfocada;
	protected String[] imagenes;
	protected EntidadGrafica entidadG;
	protected int color;
	protected String ruta;

   //crea una instancia de Entidad
	protected  Entidad(int f, int c, String ri, int col)  {
		fila=f;
		columna=c;
		enfocada=false;
		color=col;
		ruta = ri;
		cargarImagenesRepresentativas(ri);
	}

    //asigna una entidad grafica a la Entidad
	public void setEntidadGrafica(EntidadGrafica eg){
		entidadG=eg;
	}

	public EntidadGrafica getEGrafica() {
		return entidadG;
	}
	
	//obtener la imagen dependiendo si esta enfocada o no dicha entidad
	public String get_imagen_representativa() {
		int indice = 0;
		indice += (enfocada ? 1 : 0);
		return imagenes[indice];
	}

	//obtener color de la entidad
	public int get_color() {
		return color;
	}
	
	public void setFilaColumna(int f, int c) {
		fila = f;
		columna = c;}

	//setear manualmente las imagenes
	public void setImagenesRep(String g){
		imagenes[0] = g + color +".png";
		imagenes[1] = g + color +"-cursor.png";
		entidadG.notificarse_cambio_estado();
	}

    //obtener la fila
	public int get_fila(){
		return fila;
	}
    
	//obtener columna
	public int get_columna(){
		return columna;
	}

	//cambia la posicion de la entidad
	public void cambiar_posicion(int nf, int nc) {
		fila = nf;
		columna = nc;
		entidadG.notificarse_cambio_posicion();
	}

	public void intercambiarCaida(int nf, int nc) {
		fila = nf;
		columna = nc;
		entidadG.notificarse_cambio_posicion();;
	}

	//enfoca la entidad
	public void enfocar() {
		enfocada = true;
		entidadG.notificarse_cambio_estado();
	}

	//desenfoca la entidad
	public void desenfocar() {
		enfocada = false;
		entidadG.notificarse_cambio_estado();
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

	public abstract boolean es_posible_intercambiar(Entidad e);

	public abstract void explosionAdyacente();

}