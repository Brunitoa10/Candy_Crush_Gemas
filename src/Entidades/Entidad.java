
package Entidades;


import EstrategiaDetonaciones.EstategiaDetonacion;

import GUI.EntidadGrafica;
import Logica.*;
import Tablero.TableroNotificable;

public abstract class Entidad implements EntidadLogica, Enfocable, Intercambiable, Matchable, Detonable, Ocultable
{
	protected int fila,columna;
	protected int score;
	
	protected boolean enfocada,detonada,visible;
	
	protected EntidadGrafica entidadG;
	protected Color color;
	
	protected String ruta;
	protected String[] imagenes;
	
	protected EstategiaDetonacion estrategiaDetonacion;
	protected TableroNotificable tablero;
	
   //crea una instancia de Entidad
	public  Entidad(TableroNotificable tablero,int f, int c, String ri, Color col, boolean visible)  {
		this.tablero = tablero;
		fila=f;
		columna=c;
		enfocada=false;
		detonada = false;
		this.visible = visible;
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
	
	public void setEstrategiaDetonacion(EstategiaDetonacion estrategiaDetonacion) {
		this.estrategiaDetonacion = estrategiaDetonacion;
	}
	
	//obtener la imagen dependiendo si esta enfocada o no dicha entidad
	public String get_imagen_representativa() {
		int indice = 0;
		indice += (enfocada ? 1 : 0);
		return imagenes[indice];
	}

	//obtener color de la entidad
	public int get_color() {
		return color.get_color();
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
		entidadG.notificarse_intercambio();
	}

	public void intercambiarCaida(int nf, int nc) {
		fila = nf;
		columna = nc;
		entidadG.notificarse_caida();
	}

	//enfoca la entidad
	public void enfocar() {
		enfocada = true;
		entidadG.notificarse_cambio_foco();
	}

	//desenfoca la entidad
	public void desenfocar() {
		enfocada = false;
		entidadG.notificarse_cambio_foco();
	}

	//carga las imagenes de la entidad con el cursor y sin el cursor
	public void cargarImagenesRepresentativas(String ri) {
		imagenes = new String [4];
		imagenes[0] = ri + color.get_color() +".png";
		imagenes[1] = ri + color.get_color() +"-cursor.png";
		imagenes[2] = ri + "detonado.gif";
		imagenes[3] = ri + "enfocado-detonado.gif";
	}
	

	public void set_color(int color) {
		this.color.set_color(color);
	}
	
	public String get_ruta() {
		return ruta;
	}

	public boolean get_visibilidad() {
		return visible;
	}
	
	public void mostrar() {
		visible = true;
		entidadG.notificarse_cambio_visibilidad();
	}
	
	public void ocultar() {
		visible = false;
		entidadG.notificarse_cambio_visibilidad();
	}
	
	// TO DO: Completar con su correcta definición
	// Hardcodeada para mostrar caida simple y sin ningun tipo de control.
	public void caer() {
		fila ++;
		entidadG.notificarse_caida();
	}
	
	protected void intercambiar_entidad_y_entidad(Entidad origen, Entidad destino) {
		int nueva_fila_origen = destino.get_fila();
		int nueva_columna_origen = destino.get_columna();
		destino.cambiar_posicion(origen.get_fila(), origen.get_columna());
		origen.cambiar_posicion(nueva_fila_origen, nueva_columna_origen);
		tablero.reubicar(origen);
		tablero.reubicar(destino);
	}
	
	/*protected void intercambiar_caramelo_y_gelatina(Caramelo caramelo, Gelatina gelatina) {
		Caramelo caramelo_interno_gelatina = gelatina.get_caramelo_interno();
		int nueva_fila_caramelo = gelatina.get_fila();
		int nueva_columna_caramelo = gelatina.get_columna();
		int nueva_fila_caramelo_interno = caramelo.get_fila();
		int nueva_columna_caramelo_interno = caramelo.get_columna();
		
		caramelo_interno_gelatina.cambiar_posicion(nueva_fila_caramelo_interno, nueva_columna_caramelo_interno);
		caramelo.cambiar_posicion(nueva_fila_caramelo, nueva_columna_caramelo);
		gelatina.set_caramelo_interno(caramelo);
		tablero.reubicar(caramelo_interno_gelatina);
	}*/
	
	public abstract int get_score();
	
	public abstract boolean esRoca();

}