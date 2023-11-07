package Entidades;

import Tablero.Tablero;
import Tablero.TableroNotificable;
import EstrategiaDetonaciones.EstrategiaDetonacionGemaNormal;
import Logica.*;

public class GemaNormal extends Gema{
		
		public GemaNormal(TableroNotificable tablero,int f, int c, Color col, boolean visible) {
			super(tablero,f, c, col, "/assets/gemas/gema_normal/", visible);
		}
		

		public void detonar(Tablero tablero) {
			estrategiaDetonacion = new EstrategiaDetonacionGemaNormal();
			estrategiaDetonacion.detonar(this,tablero);
		}
		
		public boolean es_posible_intercambiar(Entidad entidad) {
		    return entidad.puede_recibir(this);
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

		@Override
		public boolean se_produce_match_con(Entidad e) {
			return e.aplica_match_con(this);
		}

		@Override
		public void intercambiar(Entidad entidad) {
			entidad.intercambiar_con(this);
		}

		@Override
		public void intercambiar_con(GemaNormal g) {
			intercambiar_entidad_y_entidad(this, g);
		}

		@Override
		public void intercambiar_con(GemaRayada g) {
			intercambiar_entidad_y_entidad(this, g);
		}

		@Override
		public void intercambiar_con(GemaEnvuelta g) {
			intercambiar_entidad_y_entidad(this, g);
		}

		@Override
		public void intercambiar_con(GemaCruzada g) {
			intercambiar_entidad_y_entidad(this, g);
		}

		@Override
		public void intercambiar_con(Roca r) {
			intercambiar_entidad_y_entidad(this, r);
		}

		@Override
		public void intercambiar_con(Hielo h) {
			intercambiar_entidad_y_entidad(this, h);
		}


		
}