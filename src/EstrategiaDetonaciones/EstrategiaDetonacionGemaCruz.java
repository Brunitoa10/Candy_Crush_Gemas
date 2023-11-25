package EstrategiaDetonaciones;

import java.util.LinkedList;

import Entidades.Entidad;
import Logica.Color;
import Tablero.Tablero;

public class EstrategiaDetonacionGemaCruz implements EstategiaDetonacion{

	@Override
	 public void detonar(Entidad entidad, Tablero tablero) {
        LinkedList<Entidad> entidades=new LinkedList<>();
        detonarEntidadesEnFila(entidad, tablero, entidades);
        detonarEntidadesEnColumna(entidad, tablero, entidades);
        tablero.detono(entidades);
        for (Entidad ent : entidades) {
           if (ent.get_fila()==entidad.get_fila() && ent.get_columna()==entidad.get_columna()) {
				entidad.set_color(Color.TRANSPARENTE);
				entidad.cargarImagenesRepresentativas(entidad.get_rutadeLaImagen());
				entidad.get_EntidadGrafica().notificarse_detonacion();
			}
			else{
				ent.detonar(tablero);
			}
        }
    }

    private void detonarEntidadesEnFila(Entidad entidad, Tablero tablero, LinkedList<Entidad> entidades) {
        int fila = entidad.get_fila();
        for (int columna = 0; columna < tablero.getColumna(); columna++) {
            entidades.addLast(tablero.get_entidad(fila, columna));
        }
    }

    private void detonarEntidadesEnColumna(Entidad entidad, Tablero tablero, LinkedList<Entidad> entidades) {
        int columna = entidad.get_columna();
        for (int fila = 0; fila < tablero.getFila(); fila++) {
            entidades.addLast(tablero.get_entidad(fila, columna));
        }
    }

}
