package Animadores;

import Vista.Celda;

public interface Animador{
	
	public Celda get_celda_asociada();
	
	public int get_prioridad();
	
	public void comenzar_animacion();
}
