package Threads;

import GUI.Celda;

public class AnimadorCaida extends Thread implements Animador {

	protected ManejadorAnimaciones mi_manager;
	protected Celda mi_celda_animada;
	protected int pos_y_destino;
	
	protected int step;
	protected int delay;
	
	/**
	 * Inicializa el estado del animador, considerando
	 * @param m El manejador de animaciones al que le notificará el fin de la animación, cuando corresponda.
	 * @param step La cantidad de pixels que se desplaza la Celda en cada movimiento.
	 * @param d El delay establecido entre desplazamiento y desplazamiento.
	 * @param c La celda animada.
	 */
	public AnimadorCaida(ManejadorAnimaciones m, Celda c){
		mi_manager = m;
		mi_celda_animada = c;
		
		step = 10;
		delay = 50;
		
		int size_label = mi_celda_animada.getSizeLabel();
		System.out.println(c.getEntidad().getFila());
		System.out.println(size_label);
		pos_y_destino = c.getEntidad().getColumna() * size_label;		
	}
	
	@Override
	public Celda get_celda_asociada() {
		return mi_celda_animada;
	}
	
	@Override
	public synchronized void comenzar_animacion() {
        //try{
          //  sleep(2000);
			this.start();
        //} catch (InterruptedException e) {
         //   e.printStackTrace();
        //}
	}
	
	@Override
	public void run() {
		/*try {
			sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}*/
		int size_label = mi_celda_animada.getSizeLabel();
		int pos_x_actual = mi_celda_animada.getX();
		int pos_y_actual = mi_celda_animada.getY();
		System.out.println("Y actual: "+ pos_x_actual);
		System.out.println("Y destino: "+ pos_y_destino);
		
		while( (pos_y_actual != pos_y_destino) ) {
			pos_y_actual += step;
			mi_celda_animada.setBounds(pos_x_actual, pos_y_actual, size_label, size_label);
			step++;

			try {
				sleep(delay);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
		mi_manager.notificarse_finalizacion_animacion(this);
	}
}