package Threads;

import GUI.Celda;
import Logica.EntidadLogica;

public class AnimadorIntercambio extends Thread{
	//Atributos
    protected int size_label;
	protected int step;
	protected int delay;
	protected Celda celda1,celda2;
	

    //Constructor
	public AnimadorIntercambio(int size, int step, int d, Celda c1, Celda c2) {
		size_label = size;
		this.step = step;
		delay = d;
		celda1 = c1;
		celda2 = c2;
	}
		
    //Metodos
	public void run() {
		EntidadLogica el1 = celda1.getEntidad();
		EntidadLogica el2 = celda2.getEntidad();
		
        int pos_x_c1,pos_y_c1,pos_x_c2,pos_y_c2;
        int pos_x_c1_futura,pos_y_c1_futura,pos_x_c2_futura,pos_y_c2_futura;
        int paso_c1_en_x,paso_c1_en_y,paso_c2_en_x,paso_c2_en_y;   
        boolean seguir_c1,seguir_c2;

		pos_x_c1 = celda1.getX();
		pos_y_c1 = celda1.getY();
		pos_x_c2 = celda2.getX();
		pos_y_c2 = celda2.getY();
		
		pos_x_c1_futura = el1.getColumna() * size_label;
		pos_y_c1_futura = el1.getFila() * size_label;
		pos_x_c2_futura = el2.getColumna() * size_label;
		pos_y_c2_futura = el2.getFila() * size_label;
		
		paso_c1_en_x = 0;
		paso_c1_en_y = 0;
		paso_c2_en_x = 0;
		paso_c2_en_y = 0;
		
		seguir_c1 = true;
		seguir_c2 = true;		
		
		if (pos_x_c1 != pos_x_c1_futura) {
			paso_c1_en_x = (pos_x_c1 < pos_x_c1_futura ? 1 : -1);
		}
		
		if (pos_y_c1 != pos_y_c1_futura) {
			paso_c1_en_y = (pos_y_c1 < pos_y_c1_futura ? 1 : -1);
		}
		
		if (pos_x_c2 != pos_x_c2_futura) {
			paso_c2_en_x = (pos_x_c2 < pos_x_c2_futura ? 1 : -1);
		}
		
		if (pos_y_c2 != pos_y_c2_futura) {
			paso_c2_en_y = (pos_y_c2 < pos_y_c2_futura ? 1 : -1);
		}
		
		while(seguir_c1 || seguir_c2) {
			seguir_c1 = seguir_c1 && (pos_x_c1 != pos_x_c1_futura || pos_y_c1 != pos_y_c1_futura);
			if (seguir_c1) {
				pos_x_c1 += paso_c1_en_x * step;
				pos_y_c1 += paso_c1_en_y * step;
				celda1.setBounds(pos_x_c1, pos_y_c1, size_label, size_label);
			}
			
			seguir_c2 = seguir_c2 && (pos_x_c2 != pos_x_c2_futura || pos_y_c2 != pos_y_c2_futura);
			if (seguir_c2) {
				pos_x_c2 += paso_c2_en_x * step;
				pos_y_c2 += paso_c2_en_y * step;
				celda2.setBounds(pos_x_c2, pos_y_c2, size_label, size_label);
			}
			
			try {
				sleep(delay);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
		}
		
		celda1 = null;
		celda2 = null;
		
	}
}
