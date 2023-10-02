package GUI;

import java.awt.Image;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JLabel;


import Logica.entidadLogica;

public class Celda extends JLabel implements entidadGrafica{
    //Atributos
    protected GUI miGUI;
    protected entidadLogica entidad_logica;
    protected int size_label;

    //Constructor
    public Celda(GUI g, entidadLogica l,int s){
        super();
		miGUI = g;
		entidad_logica = l;
		size_label = s;
		setBounds(l.getColumna()*size_label, l.getFila()*size_label, size_label, size_label);
		cambiar_imagen(l.get_imagen_representativa());	
    }

    //Metodos
    public void notificarse_cambio_estado() {
		cambiar_imagen(entidad_logica.get_imagen_representativa());
	}
	
	@Override
	public void notificarse_intercambio_posicion(){
		miGUI.considerar_para_intercambio_posicion(this);
	}
	
	public entidadLogica getEntidadLogica() {
		return entidad_logica;
	}
	
	public void setEntidadLogica(entidadLogica l) {
		entidad_logica = l;
	}
	
	protected void cambiar_imagen(String miString) {
		ImageIcon imgIcon = new ImageIcon(this.getClass().getResource(miString));
		Image imgEscalada = imgIcon.getImage().getScaledInstance(size_label, size_label, Image.SCALE_SMOOTH);
		Icon iconoEscalado = new ImageIcon(imgEscalada);
		setIcon(iconoEscalado);
	}
}
