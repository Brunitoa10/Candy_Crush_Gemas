package Entidades;

import EstrategiaDetonaciones.*;
import Logica.Color;
import Tablero.Tablero;
import Tablero.TableroNotificable;

public class GemaEnvuelta extends Gema{

	public GemaEnvuelta(TableroNotificable tablero,int f, int c, Color col, boolean visible, String skin) {
	 super(tablero,f, c, col, "/assets/gemas/"+skin+"/gema_envuelta/", visible);
	 cargarImagenesRepresentativas(rutadeLaImagen);
	}

	//Metodos Particulares

	public int get_score()
	{
	 return 100;
	}

	public void detonar(Tablero tablero) {
	 System.out.println("Envueltaaa");
	 EstategiaDetonacion estrategia= new EstrategiaDetonacionGemaEnvuelta();
   	 estrategia.detonar(this,tablero);
	}

	public boolean puede_recibir(Hielo hielo) {
		return hielo.get_gema_interna().puede_recibir(this);
	}
	
	public void intercambiar(Entidad entidad) {
		entidad.intercambiar_con(this);
	}
	//Metodos de la interfaz matcheable

	public boolean es_posible_intercambiar(Entidad e) {
	 return e.puede_recibir(this);
	}

	public boolean se_produce_match_con(Entidad e) {
	 return e.aplica_match_con(this);
	}

	public boolean aplica_match_con(GemaEnvuelta c) {
	 return true;
	}
  
	public boolean aplica_match_con(GemaRayada c) {
	 return true;
	}
  
	public boolean aplica_match_con(GemaCruzada c) {
	 return true;
	}
}
