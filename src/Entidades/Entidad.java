
package Entidades;


import GUI.EntidadGrafica;
import Logica.*;
import Tablero.*;

public abstract class Entidad implements EntidadLogica, Enfocable, Intercambiable, Matchable, Detonable
{
	protected int fila;
	protected int columna;
	protected boolean enfocada,detonada;
	protected String[] imagenes;
	protected EntidadGrafica entidadG;
	protected int color;
	protected String ruta;

   //crea una instancia de Entidad
	public  Entidad(int f, int c, String ri, int col)  {
		fila=f;
		columna=c;
		enfocada=false;
		detonada = false;
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
	
	@Override
	public void detonar(Tablero tablero) {
		detonada = true;
		color = Color.TRANSPARENTE;
	    cargarImagenesRepresentativas(ruta);
	    entidadG.notificarse_explosion();
	    entidadG.notificarse_cambio_estado();
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
		columna = c;
	}

    //obtener la fila
	public int get_fila(){
		return fila;
	}
    
	//obtener columna
	public int get_columna(){
		return columna;
	}

	public boolean estaDetonada() {
		return detonada;
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
		entidadG.notificarse_caida();
		entidadG.notificarse_cambio_posicion();
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
	public void cargarImagenesRepresentativas(String ri) {
		imagenes = new String [2];
		imagenes[0] = ri + color +".png";
		imagenes[1] = ri + color +"-cursor.png";
	}

	public abstract void explosionAdyacente();

	public boolean puede_recibir(GemaCruzada gemaCruzada) {
		return false;
	}

}