package Entidades;

import EstrategiaDetonacion.EstrategiaDetonacionGlaseado;
import Tablero.TableroJuego;
import Tablero.TableroNotificable;

public class Glaseado extends Entidad {

	public Glaseado(TableroNotificable tablero, int fila, int columna, int color) {
		super(tablero, fila, columna, "/imagenes/glaseado/", color,true);
		estrategiaDetonacion = new EstrategiaDetonacionGlaseado();
	}
	
	public Glaseado(TableroNotificable tablero, int fila, int columna, int color, boolean visible) {
		super(tablero, fila, columna, "/imagenes/glaseado/", color,visible);
		estrategiaDetonacion = new EstrategiaDetonacionGlaseado();
	}
	
	public boolean es_posible_intercambiar(Entidad entidad) {
		return true;
	}

	public boolean puede_recibir(Caramelo caramelo) {
		return false;
	}

	public boolean puede_recibir(Glaseado glaseado) {
		return false;
	}

	public boolean puede_recibir(Potenciador potenciador) {
		return false;
	}

	public boolean puede_recibir(Gelatina gelatina) {
		return false;
	}

	public void intercambiar(Entidad entidad) {}

	public void intercambiar_con(Caramelo caramelo) {}

	public void intercambiar_con(Potenciador potenciador) {}
	
	public void intercambiar_con(Glaseado glaseado) {}

	public void intercambiar_con(Gelatina gelatina) {}
	
	public boolean se_produce_match_con(Entidad e) {
		return false;
	}
	
	public boolean aplica_match_con(Caramelo c) {
		return false;
	}
	
	public boolean aplica_match_con(Potenciador p) {
		return false;
	}
	
	public boolean aplica_match_con(Glaseado g) {
		return false;
	}
	
	public boolean aplica_match_con(Gelatina g) {
		return false;
	}

	@Override
	public void detonar(TableroJuego tablero) {
		estrategiaDetonacion.detonar(this, tablero);
	}
}
