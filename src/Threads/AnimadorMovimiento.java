package Threads;

import javax.swing.Icon;
import javax.swing.JLabel;

import GUI.Celda;
import GUI.MultiIcon;

public class AnimadorMovimiento extends Thread implements Animador {

	protected CentralAnimaciones manager;
	protected Celda celda_animada;
	protected MultiIcon miMultiIcon;
	protected int step;
	protected int delay;
	protected int prioridad;
	
	protected int pos_x_destino;
	protected int pos_y_destino;
	
	public AnimadorMovimiento(CentralAnimaciones manager, int step, int delay, Celda celda) {
		this.manager = manager;
		this.celda_animada = celda;
		this.step = step;
		this.delay = delay;
		prioridad = PrioridadAnimaciones.PRIORIDAD_INTERCAMBIO; 
		miMultiIcon = celda_animada.getMultiIcon();
		int size_label = celda_animada.get_size_label();
		pos_x_destino = celda.get_entidad_logica().get_columna() * size_label;
		pos_y_destino = celda.get_entidad_logica().get_fila() * size_label;		
	}
	
	public Celda get_celda_asociada() {
		return celda_animada;
	}
	
	public int get_prioridad() {
		return prioridad;
	}
	
	public void comenzar_animacion() {
		this.start();
	}
	
	public void run() {
		if(miMultiIcon.hasSingleIcon()) {
			moverCelda();
		} else {moverContenidoInterno();}

	}

private void moverCelda() {
	int size_label = celda_animada.get_size_label();
	int pos_x_actual = celda_animada.getX();
	int pos_y_actual = celda_animada.getY();
		
	int paso_en_x = 0;
	int paso_en_y = 0;
		
		if (pos_x_actual != pos_x_destino) {
			paso_en_x = (pos_x_actual < pos_x_destino ? 1 : -1);
		}
		
		if (pos_y_actual != pos_y_destino) {
			paso_en_y = (pos_y_actual < pos_y_destino ? 1 : -1);
		}
		
		while( (pos_x_actual != pos_x_destino) || (pos_y_actual != pos_y_destino) ) {
			pos_x_actual += paso_en_x * step;
			pos_y_actual += paso_en_y * step;
			
			celda_animada.setBounds(pos_x_actual, pos_y_actual, size_label, size_label);
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

	private void moverContenidoInterno() {
		int size_label = celda_animada.get_size_label();
		int pos_x_actual = celda_animada.getX();
		int pos_y_actual = celda_animada.getY();
		Icon gemaInterna = miMultiIcon.getIcon2();
		int paso_en_x = 0;
		int paso_en_y = 0;
		JLabel panelAMover = new JLabel();
		panelAMover.setIcon(gemaInterna);
		miMultiIcon.removeIcon2();
		panelAMover.setBounds(pos_x_actual, pos_y_actual, size_label, size_label);

		
		if (pos_x_actual != pos_x_destino) {
			paso_en_x = (pos_x_actual < pos_x_destino ? 1 : -1);
		}
		
		if (pos_y_actual != pos_y_destino) {
			paso_en_y = (pos_y_actual < pos_y_destino ? 1 : -1);
		}
		
		while( (pos_x_actual != pos_x_destino) || (pos_y_actual != pos_y_destino) ) {
			pos_x_actual += paso_en_x * step;
			pos_y_actual += paso_en_y * step;
			
			panelAMover.setBounds(pos_x_actual, pos_y_actual, size_label, size_label);
			panelAMover.repaint();
			panelAMover.paintImmediately(celda_animada.getBounds());
			
			
			try {
				sleep(delay);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		manager.notificarse_finalizacion_animador(this);
	}
}