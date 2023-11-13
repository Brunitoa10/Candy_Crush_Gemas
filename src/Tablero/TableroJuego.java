package Tablero;

import Entidades.Entidad;
import Entidades.Hielo;

public interface TableroJuego extends TableroNotificable{
	
	public int getFila();
	
	public int getColumna();
	
	public void asociar_entidades_logicas_y_graficas();
	
	public void resetar_tablero(int cant_filas, int cant_columnas);
	
	public void agregar_entidad(Entidad e);
	
	public void agregar_entidad_y_asociada(Hielo g);
	
	public void fijar_jugador(int fila_destino, int columna_destino);
	
	public void mover_jugador(int direccion);

	public NotificadorDeEntidadesConTiempo obtenerObserver();
	
	//public void intercambiar_entidades(int direccion); segun fede
	public boolean intercambiar_entidades(int direccion);
}
