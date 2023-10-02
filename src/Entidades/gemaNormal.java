package Entidades;

import Logica.color;
import GUI.entidadGrafica;

public class gemaNormal extends gema{
		
		public gemaNormal(int f, int c, color col) {
			super(f, c, col, "/assets/gema_normal/");
		}

		/*public boolean esPosibleIntercambiar(entidad e) {
			return e.puedeRecibir(this);
		}*/

		public boolean puedeRecibir(gemaNormal g) {
			return true;
		}

		public boolean puedeRecibir(roca r) {
			return false;
		}

		public boolean puedeRecibir(gemaEnvuelta ge) {
			return true;
		}

		public boolean puedeRecibir(gemaRayada gr) {
			return true;
		}

}