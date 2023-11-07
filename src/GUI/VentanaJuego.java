package GUI;

import Logica.EntidadLogica;
import Logica.Logica;

public interface VentanaJuego {
	
	public EntidadGrafica agregar_entidad(EntidadLogica entidad_logica);

	public void ocultar();

	public void mostrar();

	public void resetear(Logica logica, int filas, int columnas);

	public void transicionar();
}
