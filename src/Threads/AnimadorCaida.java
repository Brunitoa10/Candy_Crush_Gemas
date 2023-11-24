package Threads;

import GUI.Celda;

public class AnimadorCaida extends AnimadorMovimiento{
	
	public AnimadorCaida(CentralAnimaciones manager, int step, int delay, Celda celda) {
		super(manager, step, delay, celda);
		prioridad = PrioridadAnimaciones.PRIORIDAD_CAIDA; 		
	}	

	@Override
	public void run() {
		int size_label = celda_animada.get_size_label();
		int pos_y_actual = celda_animada.getY();
		
		int paso_en_y = 1; // Cambiado a 1 para indicar la dirección hacia abajo
		
		while (pos_y_actual <= pos_y_destino) {
			pos_y_actual += paso_en_y * step;
			
			celda_animada.setBounds(pos_x_destino, pos_y_actual, size_label, size_label);
			celda_animada.repaint();
			celda_animada.paintImmediately(celda_animada.getBounds());
			
			try {
				sleep(delay);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		manager.notificarse_finalizacion_animador(this);
	}
}