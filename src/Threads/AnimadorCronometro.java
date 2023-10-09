package Threads;
import GUI.GUI;

public class AnimadorCronometro extends Thread {

	protected ManejadorAnimaciones mi_manager;
    protected int tiempo;
    protected GUI miGUI;

	public AnimadorCronometro(int t, GUI g) {
            miGUI = g;
			tiempo = t;
	}
	

    public void iniciarTiempo() {
        try {
            while(miGUI.getTiempoRestante() >= 0) {
			    sleep(1000);
			    miGUI.actualizarTiempo(tiempo);
                //miGUI.disminuirTiempo();
		    }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}