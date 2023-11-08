package Entidades;

import Logica.Color;
import Tablero.Tablero;
import Tablero.TableroNotificable;

public class Bomba extends Obstaculo {
   protected int tiempo;

   public Bomba(TableroNotificable tablero,int f, int c, String ri, Color col, boolean visible, int segundos)
   {
    super(tablero,f, c, ri, col, visible);
    tiempo=segundos;
   }

    public void detonar(Tablero t) {
        System.out.println("destruido "+ this.color + " en: "+fila+","+columna );
		    color.set_color(Color.TRANSPARENTE);
	        cargarImagenesRepresentativas(rutadeLaImagen);
	        entidadGrafica.notificarse_detonacion();
    }

    public void explosionAdyacente() {
        System.out.println("destruido "+ this.color + " en: "+fila+","+columna );
		color.set_color(Color.TRANSPARENTE);
	        cargarImagenesRepresentativas(rutadeLaImagen);
	        entidadGrafica.notificarse_detonacion();
    }

    public int get_score() {
        return color.get_score();
    }

	public boolean esAfectadaPorExplosionAdyacente()
	{
		return true;
	}

	@Override
	public boolean se_produce_match_con(Entidad e) {
	 return false;
	}

	@Override
	public boolean puede_recibir(Hielo h) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean puede_recibir(Roca r) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean puede_recibir(Bomba b) {
		// TODO Auto-generated method stub
		return false;
	}
}
