package Threads;

import java.awt.Image;
import javax.swing.Icon;
import javax.swing.ImageIcon;

import GUI.Celda;

public class AnimadorExplosion extends Thread implements Animador{

    protected ManejadorAnimaciones mi_manager;
	protected Celda mi_celda_animada;

    public AnimadorExplosion(ManejadorAnimaciones m, Celda c) {
		mi_manager = m;
		mi_celda_animada = c;
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
				mi_celda_animada.setIcon(icon);
				sleep(1000);
            }catch (InterruptedException e) {
                e.printStackTrace();
            }
            
            ImageIcon iconoVacio = new ImageIcon(this.getClass().getResource("/assets/gemas/gema_normal/0.png"));
		    Image imgEscalada = iconoVacio.getImage().getScaledInstance(mi_celda_animada.get_size_label(),mi_celda_animada.get_size_label(), Image.SCALE_SMOOTH);
		    Icon iconoEscalado = new ImageIcon(imgEscalada);
			mi_celda_animada.setIcon(iconoEscalado);


			mi_manager.notificarse_finalizacion_animacion(this);
		 }).start();
	
    }
}