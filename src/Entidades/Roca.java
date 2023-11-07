package Entidades;

import EstrategiaDetonaciones.EstrategiaDetonacionGemaNormal;
import Logica.Color;
import Tablero.Tablero;
import Tablero.TableroNotificable;

public class Roca extends Obstaculo{

	public Roca(TableroNotificable tablero,int f, int c, boolean visible) {
		super(tablero,f, c, "/assets/obstaculo/", new Color(Color.ROCA), visible);
	}

	public void detonar(Tablero tablero) {
		estrategiaDetonacion = new EstrategiaDetonacionGemaNormal();
		estrategiaDetonacion.detonar(this,tablero);
	}

	public int get_score()
	{
		return color.get_score();
	}

	@Override
	public boolean esAfectadaPorExplosionAdyacente() {
		return true;
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