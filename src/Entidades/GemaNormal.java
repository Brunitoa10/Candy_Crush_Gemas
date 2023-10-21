package Entidades;


public class GemaNormal extends Entidad{
		
		public GemaNormal(int f, int c, int col) {
			super(f, c, col, "/assets/gemas/gema_normal/");
		}

		@Override
		public boolean es_posible_intercambiar(Entidad e) {
			// TODO Auto-generated method stub
			return e.puede_recibir(this);
		}

		@Override
		public boolean puede_recibir(GemaNormal c) {
			// TODO Auto-generated method stub
			return true;
		}

		@Override
		public boolean puede_recibir(Hielo g) {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public boolean puede_recibir(GemaRayada p) {
			// TODO Auto-generated method stub
			return true;
		}

		@Override
		public boolean puede_recibir(GemaEnvuelta p) {
			// TODO Auto-generated method stub
			return true;
		}

		@Override
		public boolean machea(Entidad e) {
			// TODO Auto-generated method stub
			return e.match_con(this);
		}

		@Override
		public boolean match_con(GemaNormal c) {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public boolean match_con(GemaRayada p) {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public boolean match_con(GemaEnvuelta p) {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public boolean match_con(Hielo g) {
			// TODO Auto-generated method stub
			return false;
		}
		
}