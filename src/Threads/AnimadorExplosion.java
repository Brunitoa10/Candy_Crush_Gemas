package Threads;

import java.awt.Image;
import javax.swing.Icon;
import javax.swing.ImageIcon;

import GUI.Celda;

public class AnimadorExplosion extends Thread implements Animador {

    protected ManejadorAnimaciones mi_manager;
	protected Celda mi_celda_animada;

	protected int delay;

    public AnimadorExplosion(ManejadorAnimaciones m, Celda c, int d) {
		delay = d;
		mi_manager = m;
		mi_celda_animada = c;
		
		int size_label = mi_celda_animada.getSizeLabel();
	}

    public Celda get_celda_asociada() {
		return mi_celda_animada;
	}
	
	@Override
	public synchronized void comenzar_animacion() {
		this.start();
	}

    @Override
	public void run() {
        
		new Thread(()-> {
			explotar(mi_celda_animada, this, mi_manager);
		 }).start();
		
    }
    
    private static synchronized void explotar(Celda c, AnimadorExplosion a, ManejadorAnimaciones m) {
  
    	Icon icon = new ImageIcon(a.getClass().getResource("/assets/gemas/detonado.gif"));
		c.setIcon(icon);
        try {
			sleep(2000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
        ImageIcon iconoVacio = new ImageIcon(a.getClass().getResource("/assets/gemas/gema_normal/0.png"));
	    Image imgEscalada = iconoVacio.getImage().getScaledInstance(c.getSizeLabel(), c.getSizeLabel(), Image.SCALE_SMOOTH);
	    Icon iconoEscalado = new ImageIcon(imgEscalada);
		c.setIcon(iconoEscalado);

		m.notificarse_finalizacion_animacion(a);
    }
}
