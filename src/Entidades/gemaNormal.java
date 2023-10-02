package Entidades;

import GUI.entidadGrafica;

public class gemaNormal extends gema{
		
		public gemaNormal(int f, int c, int col) {
			super(f, c, col, "/assets/gema_normal/");
		}

		public boolean esPosibleIntercambiar(entidad e) {
			return e.puedeRecibir(this);
		}
}
