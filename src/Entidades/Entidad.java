
package Entidades;


import EstrategiaDetonaciones.EstategiaDetonacion;

import GUI.EntidadGrafica;
import Logica.*;
import Tablero.TableroNotificable;

public abstract class Entidad implements EntidadLogica, Enfocable, Intercambiable, Matchable, Detonable, Ocultable
{
	//Atributos de instancia

	protected int fila,columna;
	protected int score;
	protected boolean enfocada,detonada,visible;
	protected EntidadGrafica entidadGrafica;
	protected Color color;
	protected String rutadeLaImagen;
	protected String[] imagenes;
	protected EstategiaDetonacion estrategiadeDetonacion;
	protected TableroNotificable tablero;

	//Constructor

	public Entidad(TableroNotificable tablero ,int fila, int columna, String rutadeLaImagen, Color color, boolean visible)  {
		this.tablero = tablero;
		this.fila=fila;
		this.columna=columna;
		this.enfocada=false;
		this.detonada = false;
		this.visible = visible;
		this.color=color;
		this.rutadeLaImagen = rutadeLaImagen;
	}

	//Setters

	public void set_EstrategiaDetonacion(EstategiaDetonacion estrategiaDetonacion) {
		this.estrategiadeDetonacion = estrategiaDetonacion;
	}

	public void set_EntidadGrafica(EntidadGrafica entidadGrafica){
		this.entidadGrafica=entidadGrafica;
	}

	public void set_FilayColumna(int fila, int columna) {
		this.fila = fila;
		this.columna = columna;
	}

	
	public void set_color(int color) {
		this.color.set_color(color);
	}

	//Getters

	public EntidadGrafica get_EntidadGrafica() {
		return entidadGrafica;
	}
	
	public String get_imagen_representativa() {
		int indice = 0;
		indice += (enfocada ? 1 : 0);
		return imagenes[indice];
	}

	public int get_color() {
		return color.get_color();
	}

	public int get_fila(){
		return fila;
	}

	public int get_columna(){
		return columna;
	}

	public String get_rutadeLaImagen() {
		return rutadeLaImagen;
	}

	public boolean get_visibilidad() {
		return visible;
	}

	public int get_score()
	{
        return color.get_score();
	}

	public abstract Entidad get_gema_interna();
	//Metodos particulares

	public boolean estaDetonada() {
		return detonada;
	}

	public void cargarImagenesRepresentativas(String ri) {
		imagenes = new String [4];
		imagenes[0] = ri + color.get_color() +".png";
		System.out.println(imagenes[0]);
		imagenes[1] = ri + color.get_color() +"-cursor.png";
		imagenes[2] = ri + "detonado.gif";
		imagenes[3] = ri + "enfocado-detonado.gif";
	}

	public abstract boolean esAfectadaPorExplosionAdyacente();

	public void cambiar_posicion(int nueva_fila, int nueva_columna) {
		fila = nueva_fila;
		columna = nueva_columna;
		entidadGrafica.notificarse_intercambio();
	}

	//Metodos visuales

	public void intercambiar_Posicion(int nuevaFila, int nuevaColumna) {
		fila = nuevaFila;
		columna = nuevaColumna;
		entidadGrafica.notificarse_intercambio();
	}

	public void intercambiar_Caida(int nuevaFila, int nuevaColumna) {
		fila = nuevaFila;
		columna = nuevaColumna;
		entidadGrafica.notificarse_caida();
	}

	public void enfocar() {
		enfocada = true;
		entidadGrafica.notificarse_cambio_foco();
	}

	public void desenfocar() {
		enfocada = false;
		entidadGrafica.notificarse_cambio_foco();
	}
	
	public void mostrar() {
		visible = true;
		entidadGrafica.notificarse_cambio_visibilidad();
	}
	
	public void ocultar() {
		visible = false;
		entidadGrafica.notificarse_cambio_visibilidad();
	}
	
	
	//Metodos protegidos

	protected void intercambiar_entidad_y_entidad(Entidad origen, Entidad destino) {
		int nueva_fila_origen = destino.get_fila();
		int nueva_columna_origen = destino.get_columna();
		destino.cambiar_posicion(origen.get_fila(), origen.get_columna());
		origen.cambiar_posicion(nueva_fila_origen, nueva_columna_origen);
		tablero.reubicar(origen);
		tablero.reubicar(destino);
	}
	
	protected void intercambiar_gema_y_hielo(Entidad gema, Hielo hielo) {
		Entidad gema_interna_hielo = hielo.get_gema_interna();
		int nueva_fila_gema = hielo.get_fila();
		int nueva_columna_gema = hielo.get_columna();
		int nueva_fila_gema_interna = gema.get_fila();
		int nueva_columna_gema_interna = gema.get_columna();
		
		gema_interna_hielo.cambiar_posicion(nueva_fila_gema_interna, nueva_columna_gema_interna);
		gema.cambiar_posicion(nueva_fila_gema, nueva_columna_gema);
		hielo.set_gema_interna(gema);
		tablero.reubicar(gema_interna_hielo);
	}

	protected void intercambiar_hielo_y_hielo(Hielo hielo1, Hielo hielo2)
	{
        Entidad gema_interna_hielo1 = hielo1.get_gema_interna();
		Entidad gema_interna_hielo2 = hielo2.get_gema_interna();
		int nueva_fila_gema_interna1 = hielo2.get_fila();
		int nueva_columna_gema_interna1 = hielo2.get_columna();
		int nueva_fila_gema_interna2 = hielo1.get_fila();
		int nueva_columna_gema_interna2 = hielo1.get_columna();

		gema_interna_hielo1.cambiar_posicion(nueva_fila_gema_interna1, nueva_columna_gema_interna1);
		gema_interna_hielo2.cambiar_posicion(nueva_fila_gema_interna2, nueva_columna_gema_interna2);
		hielo1.set_gema_interna(gema_interna_hielo2);
		hielo2.set_gema_interna(gema_interna_hielo1);
	}

}