package Threads;

import GUI.Celda;

public class AnimadorCaida extends AnimadorMovimiento {

    public AnimadorCaida(ManejadorAnimaciones m, int step, int d, Celda c) {
        super(m, step, d, c);
    }

    @Override
    public void run() {
        int size_label = mi_celda_animada.get_size_label();
        int pos_x_actual = mi_celda_animada.getX();
        int pos_y_actual = mi_celda_animada.getY();

        int paso_en_y = 1; // La caída siempre es hacia abajo

        while (pos_y_actual < pos_y_destino) {
            pos_y_actual += paso_en_y * step;
            mi_celda_animada.setBounds(pos_x_actual, pos_y_actual, size_label, size_label);

            try {
                sleep(delay);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        mi_manager.notificarse_finalizacion_animacion(this);
    }
}