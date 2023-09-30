package GUI;



import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;


import Celda.Celda;
import Logica.EntidadLogica;
import Logica.Logica;


public class GUI extends JFrame {

	protected Logica miLogica;
	protected int filas,columnas;
	protected Celda celda_A_pendienteAnimacion,celda_B_pendienteAnimacion;
	protected JLabel texto_superior;
	protected JPanel panel_principal;
	
	private int size_label = 60;

	//Movimientos
	private static final int ARRIBA = 15000;
	private static final int ABAJO = 15001;
	private static final int IZQUIERDA = 15002;
	private static final int DERECHA = 15003;
	
	
	public GUI(Logica l, int f, int c) {
		
	}
	
	protected void inicializar() {
		
	}
	
	public EntidadGrafica agregar_entidad(EntidadLogica e) {
		return null;
	}
	
	public void considerar_para_intercambio_posicion(Celda c) {
		
	}

}
