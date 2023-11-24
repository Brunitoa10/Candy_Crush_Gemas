package GUI;

import java.awt.Image;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

import Logica.EntidadLogica;

public class Celda extends JLabel implements EntidadGrafica {
	
	protected GUI ventana;
	protected EntidadLogica entidad_logica;
	protected MultiIcon miMultiIcon;
	
	protected int size_label;
	
	public Celda(GUI ventana, EntidadLogica entidad_logica, int size_label, boolean visible) {
		super();
		
		this.ventana = ventana;
		this.entidad_logica = entidad_logica;
		this.size_label = size_label;
		this.setVisible(visible);
		
		int ubicacion_eje_x = entidad_logica.get_columna() * size_label;
		int ubicacion_eje_y = entidad_logica.get_fila() * size_label;
		int alto = size_label;
		int ancho = size_label;
		
		if(entidad_logica.tieneGemaInterna())
		{
           entidad_logica.set_EntidadGrafica_entidadInterna(new Celda(ventana,entidad_logica.get_gema_interna(), size_label, true));

		   System.out.println(entidad_logica.get_gema_interna().get_imagenes_representativas());
		}

		setBounds(ubicacion_eje_x, ubicacion_eje_y, ancho, alto);
		String imagen1 = entidad_logica.get_imagenes_representativas()[0];
		String imagen2 = entidad_logica.get_imagenes_representativas()[1];
		if(entidad_logica.get_imagenes_representativas()[1] == null) {
			fijar_imagen_escalada_para_celda(imagen1);
		}
		else fijar_imagen_escalada_para_celda(imagen2, imagen1);
	}
	
	public EntidadLogica get_entidad_logica() {
		return entidad_logica;
	}
	
	public int get_size_label() {
		return size_label;
	}
	
	public void eliminar_de_ventana() {
		ventana.eliminar_celda(this);
	}

	public MultiIcon getMultiIcon() {
		return miMultiIcon;
	}
	
	// Operaciones para Entidad Grafica (Celda <-- Entidad Lógica)
	
	public void notificarse_intercambio(){
		ventana.animar_intercambio(this);
	}
	
	public void notificarse_cambio_foco() {
		ventana.animar_cambio_foco(this);
	}
	
	public void notificarse_detonacion() {
		ventana.animar_detonacion(this);
	}
	
	public void notificarse_caida() {
		ventana.animar_caida(this);
	}
	
	public void notificarse_cambio_visibilidad() {
		ventana.animar_cambio_visibilidad(this);
	}
	
	// Operaciones locales a celda

	protected void fijar_imagen_escalada_para_celda(String path_imagen) {
		ImageIcon icono_imagen = new ImageIcon(this.getClass().getResource(path_imagen));
		Image imagen_escalada = icono_imagen.getImage().getScaledInstance(size_label, size_label, Image.SCALE_SMOOTH);
		Icon icono_imagen_escalado = new ImageIcon(imagen_escalada);
		miMultiIcon = new MultiIcon(icono_imagen_escalado, null);
		miMultiIcon.setIconSize(size_label, size_label);
		setIcon(miMultiIcon);
	}
	
	protected void fijar_imagen_escalada_para_celda(String path_imagen1, String path_imagen2) {
		ImageIcon icono_imagen1 = new ImageIcon(this.getClass().getResource(path_imagen1));
		Image imagen_escalada1 = icono_imagen1.getImage().getScaledInstance(size_label, size_label, Image.SCALE_SMOOTH);
		Icon icono_imagen_escalado1 = new ImageIcon(imagen_escalada1);
	
		ImageIcon icono_imagen2 = new ImageIcon(this.getClass().getResource(path_imagen2));
		Image imagen_escalada2 = icono_imagen2.getImage().getScaledInstance(size_label, size_label, Image.SCALE_SMOOTH);
		Icon icono_imagen_escalado2 = new ImageIcon(imagen_escalada2);
	
		miMultiIcon = new MultiIcon(icono_imagen_escalado1, icono_imagen_escalado2);
		miMultiIcon.setIconSize(size_label, size_label);
		setIcon(miMultiIcon);
	}

}