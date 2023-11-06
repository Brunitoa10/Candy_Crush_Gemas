package EstrategiaDetonaciones;

import Entidades.Entidad;

public class EstrategiaDetonacionRocas implements EstrategiaDetonacionObstaculo {

	@Override
	public void detonarRoca(Entidad[][] entidades, int fila, int columna) {
		int filas = entidades.length;
        int columnas = entidades[0].length;

        Entidad arriba = (fila > 0) ? entidades[fila - 1][columna] : null;
        Entidad abajo = (fila < filas - 1) ? entidades[fila + 1][columna] : null;
        Entidad izquierda = (columna > 0) ? entidades[fila][columna - 1] : null;
        Entidad derecha = (columna < columnas - 1) ? entidades[fila][columna + 1] : null;

        detonarEntidad(arriba);
        detonarEntidad(abajo);
        detonarEntidad(izquierda);
        detonarEntidad(derecha);
	}
	private void detonarEntidad(Entidad entidad) {
        if (entidad != null) {
            entidad.explosionAdyacente();
        }
    }
}
