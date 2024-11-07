package Animadores;

import ManejadorAnimaciones.CentralAnimaciones;
import Vista.Celda;

public class AnimadorCaida extends AnimadorMovimiento{
	
	public AnimadorCaida(CentralAnimaciones manager, int step, int delay, Celda celda) {
		super(manager, step, delay, celda);
		prioridad = PrioridadAnimaciones.PRIORIDAD_CAIDA; 		
	}	
}
