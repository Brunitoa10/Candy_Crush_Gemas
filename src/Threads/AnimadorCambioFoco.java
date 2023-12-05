package Threads;

import java.awt.Image;

import javax.swing.Icon;
import javax.swing.ImageIcon;

import GUI.Celda;
import GUI.MultiIcon;

public class AnimadorCambioFoco implements Animador {

	/*
	 * protected CentralAnimaciones manager;
	 * protected Celda celda_animada;
	 * protected int prioridad;
	 * protected String[] path_imagenes_estados;
	 * 
	 * public AnimadorCambioFoco(CentralAnimaciones manager, Celda celda) {
	 * this.manager = manager;
	 * this.celda_animada = celda;
	 * this.prioridad = PrioridadAnimaciones.PRIORIDAD_FOCO;
	 * path_imagenes_estados =
	 * celda.get_entidad_logica().get_imagenes_representativas();
	 * }
	 * 
	 * public Celda get_celda_asociada() {
	 * return celda_animada;
	 * }
	 * 
	 * public int get_prioridad() {
	 * return prioridad;
	 * }
	 * 
	 * public void comenzar_animacion() {
	 * if(path_imagenes_estados[1] == null) {
	 * fijar_imagen_escalada_para_celda(path_imagenes_estados[0]);
	 * } else fijar_imagen_escalada_para_celda_con_hielo(path_imagenes_estados[1],
	 * path_imagenes_estados[0]);
	 * celda_animada.repaint();
	 * manager.notificarse_finalizacion_animador(this);
	 * }
	 * 
	 * protected void fijar_imagen_escalada_para_celda_con_hielo(String
	 * path_imagen1, String path_imagen2) {
	 * int size_label = celda_animada.get_size_label();
	 * ImageIcon icono_imagen1 = new
	 * ImageIcon(this.getClass().getResource(path_imagen1));
	 * Image imagen_escalada1 =
	 * icono_imagen1.getImage().getScaledInstance(size_label, size_label,
	 * Image.SCALE_SMOOTH);
	 * Icon icono_imagen_escalado1 = new ImageIcon(imagen_escalada1);
	 * 
	 * ImageIcon icono_imagen2 = new
	 * ImageIcon(this.getClass().getResource(path_imagen2));
	 * Image imagen_escalada2 =
	 * icono_imagen2.getImage().getScaledInstance(size_label, size_label,
	 * Image.SCALE_SMOOTH);
	 * Icon icono_imagen_escalado2 = new ImageIcon(imagen_escalada2);
	 * 
	 * MultiIcon iconoConHielo = new MultiIcon(icono_imagen_escalado1,
	 * icono_imagen_escalado2);
	 * iconoConHielo.setIconSize(size_label, size_label);
	 * celda_animada.setIcon(iconoConHielo);
	 * }
	 * 
	 * protected void fijar_imagen_escalada_para_celda(String path_imagen) {
	 * int size_label = celda_animada.get_size_label();
	 * ImageIcon icono_imagen = new
	 * ImageIcon(this.getClass().getResource(path_imagen));
	 * Image imagen_escalada = icono_imagen.getImage().getScaledInstance(size_label,
	 * size_label, Image.SCALE_SMOOTH);
	 * Icon icono_imagen_escalado = new ImageIcon(imagen_escalada);
	 * celda_animada.setIcon(icono_imagen_escalado);
	 * }
	 */
	protected CentralAnimaciones manager;
	protected Celda celda_animada;
	protected int prioridad;
	protected String path_imagen_estado;

	public AnimadorCambioFoco(CentralAnimaciones manager, Celda celda) {
		this.manager = manager;
		this.celda_animada = celda;
		this.prioridad = PrioridadAnimaciones.PRIORIDAD_FOCO;
		path_imagen_estado = celda.get_entidad_logica().get_imagen_representativa();
	}

	public Celda get_celda_asociada() {
		return celda_animada;
	}

	public int get_prioridad() {
		return prioridad;
	}

	public void comenzar_animacion() {
		int size_label = celda_animada.get_size_label();
		ImageIcon icono_imagen = new ImageIcon(this.getClass().getResource(path_imagen_estado));
		Image imagen_escalada = icono_imagen.getImage().getScaledInstance(size_label, size_label,
				Image.SCALE_REPLICATE);
		Icon icono_escalado = new ImageIcon(imagen_escalada);
		celda_animada.setIcon(icono_escalado);
		manager.notificarse_finalizacion_animador(this);
	}
}