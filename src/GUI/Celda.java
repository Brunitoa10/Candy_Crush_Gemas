package GUI;

import java.awt.Image;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

import Logica.color;
import Logica.EntidadLogica;

public class Celda extends JLabel implements EntidadGrafica{
    //Atributos
    protected GUI miGUI;
    protected EntidadLogica entidadLogica;
    protected int size_label;

    //Constructor
    public Celda(GUI g, EntidadLogica l,int s){
        super();
		miGUI = g;
		entidadLogica = l;
		size_label = s;
		setBounds(l.getColumna()*size_label, l.getFila()*size_label, size_label, size_label);
		cambiar_imagen(l.getImagenRep());	
    }

    //Metodos
    public void notificarse_cambio_estado() {
		cambiar_imagen(entidadLogica.getImagenRep());
	}
	
	@Override
	public void notificarse_intercambio_posicion(){
		miGUI.considerar_para_intercambio_posicion(this);
	}
	
	public EntidadLogica getEntidadLogica() {
		return entidadLogica;
	}
	
	public void setEntidadLogica(EntidadLogica l) {
		entidadLogica = l;
	}
	
	protected void cambiar_imagen(String miString) {
		ImageIcon imgIcon = new ImageIcon(this.getClass().getResource(miString));
		Image imgEscalada = imgIcon.getImage().getScaledInstance(size_label, size_label, Image.SCALE_SMOOTH);
		Icon iconoEscalado = new ImageIcon(imgEscalada);
		setIcon(iconoEscalado);
	}
	
	public color getColorEntidad() {
		return entidadLogica.obtenerColor();
	}
	
	public void actualizarXY(int x, int y) {
		entidadLogica.intercambiarPosicion(x,y);
	}
}
