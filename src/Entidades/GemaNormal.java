package Entidades;

import Tablero.Tablero;

public class GemaNormal extends Gema{
		
		public GemaNormal(int f, int c, int col) {
			super(f, c, col, "/assets/gemas/gema_normal/");
		}
		
		public void detonar(Tablero tablero) {
			super.detonar(tablero);
		}
		
		public boolean es_posible_intercambiar(Entidad entidad) {
		    return entidad.puede_recibir(this);
		}
		
		public boolean puede_recibir(GemaNormal c) {
			return true;
		}
		
		public boolean machea(Entidad e) {
			System.out.println("Gema Normal matchea(Entidad) :: "+e.get_color()+","+get_color());
			return e.match_con(this);
		}
		
		@Override
		public boolean match_con(GemaNormal c) {
			return true;
		}
		
		public void explosionAdyacente()
		{}

		public int get_score()
		{
			return definirScore();
		}

		private int definirScore()
		{
			int score=0;
			if (color==1)
		{
          score=5;
		}
		if(color==2)
		{
			score=15;
		}
		if(color==3 || color==5)
		{
			score=20;
		}
		if(color==4)
		{
			score=10;
		}
		if(color==6)
		{
			score=25;
		}
		return score;
		}
}