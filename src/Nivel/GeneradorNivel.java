package Nivel;

import Tablero.Tablero;

public class GeneradorNivel {
	public static Nivel cargar_nivel_y_tablero(int nivel, Tablero t) {
		t.resetearTablero(6, 6);
		
		/*for(int y=0; y<3; y++) {
			t.agregar_entidad(new Caramelo(0,y, Color.AZUL));
			t.agregar_entidad(new Glaseado(1,y, Color.NEGRO));
			t.agregar_entidad(new Caramelo(2,y, Color.VERDE));
			t.agregar_entidad(new Caramelo(3,y, Color.NARANJA));
			t.agregar_entidad(new Caramelo(4,y, Color.VIOLETA));
		}
		
		for(int y=3; y<5; y++) {
			t.agregar_entidad(new Caramelo(0,y, Color.AZUL));
			t.agregar_entidad(new Caramelo(1,y, Color.ROJO));
			t.agregar_entidad(new Caramelo(2,y, Color.VERDE));
			t.agregar_entidad(new Glaseado(3,y, Color.NEGRO));
			t.agregar_entidad(new Caramelo(4,y, Color.VIOLETA));
		}*/
		
		return new Nivel(2,2);
	}
}
