package Nivel;


import Entidades.GemaNormal;
import Entidades.GemaEnvuelta;
import Entidades.GemaRayada;
import Entidades.Roca;
import Logica.Color;
import Tablero.Tablero;

public class GeneradorNivel {
	public static Nivel cargar_nivel_y_tablero(int nivel, Tablero t) {
		t.resetearTablero(5, 5);
		try {
			for(int y=0; y<3; y++) {
				t.agregarEntidad(new GemaNormal(0,y, Color.AZUL));
				t.agregarEntidad(new GemaRayada(1,y, Color.ROJO,0));
				t.agregarEntidad(new GemaEnvuelta(2,y, Color.VERDE));
				t.agregarEntidad(new GemaNormal(3,y, Color.NARANJA));
				t.agregarEntidad(new GemaNormal(4,y, Color.PURPURA));
			}
			
			for(int y=3; y<5; y++) {
				t.agregarEntidad(new GemaNormal(0,y, Color.AZUL));
				t.agregarEntidad(new GemaRayada(1,y, Color.ROJO,1));
				t.agregarEntidad(new GemaNormal(2,y, Color.ROJO));
				t.agregarEntidad(new Roca(3,y));
				t.agregarEntidad(new GemaNormal(4,y, Color.PURPURA));
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}

		t.printTable();
		return new Nivel(2,2);
	}
}
