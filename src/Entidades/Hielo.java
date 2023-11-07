package Entidades;

import Logica.Color;
import Tablero.Tablero;
import Tablero.TableroNotificable;

public class Hielo extends Obstaculo {

	public Hielo(TableroNotificable tablero,int f, int c, Color col, boolean visible) {
		super(tablero,f, c, "/assets/obstaculo/gema_normal/", col, visible);
	}
	
	public void detonar(Tablero  t) {
		    System.out.println("destruido "+ this.color + " en: "+fila+","+columna );
		    color.set_color(Color.TRANSPARENTE);
		    cargarImagenesRepresentativas(ruta);
	        entidadG.notificarse_detonacion();
	}


	public int get_score()
	{
		return color.get_score();
	}

	@Override
	public boolean esRoca() {
		// TODO Auto-generated method stub
		return false;
	}

	public Entidad get_caramelo_interno() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean se_produce_match_con(Entidad e) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void intercambiar(Entidad entidad) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void intercambiar_con(GemaNormal g) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void intercambiar_con(GemaRayada g) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void intercambiar_con(GemaEnvuelta g) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void intercambiar_con(GemaCruzada g) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void intercambiar_con(Roca r) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void intercambiar_con(Hielo h) {
		// TODO Auto-generated method stub
		
	}
}