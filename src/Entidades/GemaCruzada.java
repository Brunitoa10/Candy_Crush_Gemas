package Entidades;

import EstrategiaDetonaciones.EstategiaDetonacion;
import EstrategiaDetonaciones.EstrategiaDetonacionGemaCruz;
import Logica.Color;
import Tablero.Tablero;
import Tablero.TableroNotificable;


public class GemaCruzada extends Gema {

	public GemaCruzada(TableroNotificable tablero,int f, int c, Color col, boolean visible, String skin)
	{
		super(tablero,f, c, col, "/assets/gemas/"+skin+"/gema_cruz/", visible);
		cargarImagenesRepresentativas(rutadeLaImagen);
	}

	//Metodos Particulares
	public void detonar(Tablero tablero)
	{
		EstategiaDetonacion estrategia= new EstrategiaDetonacionGemaCruz();
		estrategia.detonar(this,tablero);
	}


	public int get_score() {
		return  100;
	}

	public void explosionAdyacente()
	{}

	public void intercambiar(Entidad entidad) {
		entidad.intercambiar_con(this);
	}
	
	//Metodos redefinidos de la interfaz matcheable
	public boolean es_posible_intercambiar(Entidad entidad) {
		return entidad.puede_recibir(this);
	}

	public boolean se_produce_match_con(Entidad e) {
		return e.aplica_match_con(this);
	}	
	public boolean aplica_match_con(GemaNormal c) {
		return c.get_color() == this.get_color();
	}
}
