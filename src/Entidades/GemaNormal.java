package Entidades;

import Logica.color;
import GUI.EntidadGrafica;

public class GemaNormal extends Gema{
		
		public GemaNormal(int f, int c, color col) {
			super(f, c, col, "/assets/gema_normal/");
		}

		public boolean puedeRecibir(GemaNormal g) {
			return true;
		}

		public boolean puedeRecibir(Roca r) {
			return false;
		}

		public boolean puedeRecibir(GemaEnvuelta ge) {
			return true;
		}

		public boolean puedeRecibir(GemaRayada gr) {
			return true;
		}

}