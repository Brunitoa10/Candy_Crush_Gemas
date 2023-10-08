package Entidades;
public class GemaNormal extends Gema{
		
		public GemaNormal(int f, int c, int col) {
			super(f, c, col, "/assets/gemas/gema_normal/");
		}


		public void romper(Entidad e) {
			e.setImagenesRep("0");
		}


}