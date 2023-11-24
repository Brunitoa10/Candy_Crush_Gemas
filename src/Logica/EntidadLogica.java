package Logica;

import GUI.EntidadGrafica;

public interface EntidadLogica {

	public int get_fila();

	public int get_columna();
	
	public boolean get_visibilidad();

	public String[] get_imagenes_representativas();

	public int get_color();

	public boolean tieneGemaInterna();

	public void set_EntidadGrafica_entidadInterna(EntidadGrafica es);

	public EntidadLogica get_gema_interna();
}