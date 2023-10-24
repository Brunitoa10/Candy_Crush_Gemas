package Entidades;

import Logica.Color;
import Tablero.Tablero;

public class GemaNormal extends Gema{
		
		public GemaNormal(int f, int c, int col) {
			super(f, c, col, "/assets/gemas/gema_normal/");
		}


		@Override
		public void detonar(Tablero tablero) {
		    color = Color.TRANSPARENTE;
		    cargarImagenesRepresentativas(ruta);
		    entidadG.notificarse_explosion();
		}

		public boolean es_posible_intercambiar(Entidad entidad) {
		    return entidad.puede_recibir(this);
		}
		
		public boolean machea(Entidad e) 
		{
		return e.match_con(this);
		}

		public void explosionAdyacente()
		{}
}