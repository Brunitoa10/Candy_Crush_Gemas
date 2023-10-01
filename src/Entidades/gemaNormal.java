package Entidades;

public class gemaNormal extends gema{
	
		private int color;
		
		public gemaNormal(int f, int c, int col) {
			super(f, c, col, "/assets/gema_normal/");
		}
		
		public boolean es_posible_intercambiar(entidad e) {
			return e.puede_recibir(this);
		}
		
		public boolean puede_recibir(gemaNormal g) {
			// To Do: programar la lógica para chequear match 3
			return true;
		}
		
		public boolean puede_recibir(roca r) {
			return false;
		}
		
		public boolean puede_recibir(gemaEnvuelta p) {
			// To Do: programar la lógica para chequear match 3
			return true;
		}

	}


}
