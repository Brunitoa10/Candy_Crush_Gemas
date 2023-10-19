package Threads;

import java.awt.Image;
import javax.swing.Icon;
import javax.swing.ImageIcon;

import GUI.Celda;

public class AnimadorExplosion extends Thread implements Animador{

    protected ManejadorAnimaciones mi_manager;
	protected Celda mi_celda_animada;
	protected int prioridad;
	protected int delay;

    public AnimadorExplosion(ManejadorAnimaciones m, Celda c, int d) {
		delay = d;
		mi_manager = m;
		mi_celda_animada = c;
		prioridad = 2;
	}

    public Celda get_celda_asociada() {
		return mi_celda_animada;
	}
	
	@Override
	public void comenzar_animacion() {
		this.start();
	}

    @Override
	public void run() {
        Icon icon = new ImageIcon(this.getClass().getResource("/assets/gemas/detonado.gif"));
		new Thread(()-> {
			try {
				sleep(delay);
				mi_celda_animada.setIcon(icon);
            
				sleep(1000);
            }catch (InterruptedException e) {
                e.printStackTrace();
            }
            
            ImageIcon iconoVacio = new ImageIcon(this.getClass().getResource("/assets/gemas/gema_normal/0.png"));
		    Image imgEscalada = iconoVacio.getImage().getScaledInstance(mi_celda_animada.getSizeLabel(), mi_celda_animada.getSizeLabel(), Image.SCALE_SMOOTH);
		    Icon iconoEscalado = new ImageIcon(imgEscalada);
			mi_celda_animada.setIcon(iconoEscalado);

			mi_manager.notificarse_finalizacion_animacion(this);
		 }).start();
		
    }
}