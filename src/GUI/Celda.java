package GUI;

import java.awt.Image;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

import Entidades.Entidad;
import Threads.AnimadorExplosion;

public class Celda extends JLabel implements EntidadGrafica{
    //Atributos
    protected GUI miGUI;
    protected Entidad ent;
    protected int size_label;

    //Constructor
    public Celda(GUI g, Entidad l,int s){
        super();
		miGUI = g;
		ent = l;
		size_label = s;
		setBounds(l.getColumna()*size_label, l.getFila()*size_label, size_label, size_label);
		System.out.println(l.getImagenRep());
		cambiar_imagen(l.getImagenRep());	
    }

    //Metodos
    public void notificarse_cambio_estado() {
		cambiar_imagen(ent.getImagenRep());
	}
	
	public void notificarse_explosion() {
		cambiar_explosion();

	}

	@Override
	public void notificarse_intercambio_posicion(){
		miGUI.animar_movimiento(this);
	}
	
	public void notificarCeldaDesenfocar(){
		ent.desenfocar();
	}

	public void notificarCeldaEnfocar(){
		ent.enfocar();
	}

	public int getSizeLabel() {
		return size_label;
	}
	
	public Entidad getEntidad() {
		return ent;
	}

	public void setEntidad(Entidad l) {
		ent = l;
	}
	
	public void setGUI(GUI g) {
		miGUI = g;
	}
	
	protected void cambiar_imagen(String miString) {
		ImageIcon imgIcon = new ImageIcon(this.getClass().getResource(miString));
		Image imgEscalada = imgIcon.getImage().getScaledInstance(size_label, size_label, Image.SCALE_SMOOTH);
		Icon iconoEscalado = new ImageIcon(imgEscalada);
		setIcon(iconoEscalado);
	}

	protected void cambiar_explosion() {
		miGUI.animar_explosion(this);
	}
	
	public int getColorEntidad() {
		return ent.obtenerColor();
	}
	
	public void actualizarXY(int x, int y) {
		ent.intercambiarPosicion(x,y);
	}
}
