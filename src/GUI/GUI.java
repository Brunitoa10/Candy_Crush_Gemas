package GUI;




import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.Image;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import java.awt.Dimension;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.GridBagLayout;
import Entidades.Entidad;
import Logica.Logica;
import Threads.AnimadorCronometro;
import Threads.CentralAnimaciones;


public class GUI extends JFrame {
	protected Logica milogica;
	protected int filas,columnas;
	protected JPanel panel_principal, panel_objetivos;
	protected JLabel timerLabel,objetivosLabel1,objetivosLabel2,objetivosLabel3;
	protected int tiempoRestante;
	protected CentralAnimaciones mi_animador;
	protected int animaciones_pendientes;
	protected boolean bloquear_intercambios;
	protected JLabel movimientosLabel;
	protected int movimientosRestantes;
	protected AnimadorCronometro animadorTiempo;
	private int size_label = 70;

	//Movimientos
	public static final int ARRIBA = 15000;
	public static final int ABAJO = 15001;
	public static final int IZQUIERDA = 15002;
	public static final int DERECHA = 15003;
	
	
	public GUI(Logica l, int f, int c) {
		milogica = l;
		filas = f;
		mi_animador = new CentralAnimaciones(this);
		columnas = c;
		tiempoRestante = milogica.getTiempo();
		movimientosRestantes = milogica.getMovimientos();
		animaciones_pendientes = 0;
		bloquear_intercambios = false;
		animadorTiempo = new AnimadorCronometro(tiempoRestante, this);
		
		inicializar();
	}
	
	
	protected void inicializar() {
			
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		setTitle("Proyecto Candy Crush - Comision-06");
		setSize(screenSize);
		setExtendedState(JFrame.MAXIMIZED_BOTH); 
		setLocationRelativeTo(null);
		//----------------------------------------------------
		//Estaba true, le puse false para poder achicarlo y que aparezca la cruz
		setUndecorated(false); 
		//----------------------------------------------------
		setVisible(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setExtendedState(JFrame.MAXIMIZED_BOTH);
		setLayout(new GridBagLayout());
		getContentPane();

		timerLabel = new JLabel("Tiempo restante: "+ tiempoRestante);
		movimientosLabel = new JLabel ("Movimientos restantes: "+movimientosRestantes);
		
		panel_objetivos = new JPanel();
		panel_principal = new JPanel();
		panel_principal.setSize(size_label * filas, size_label * columnas);
		panel_principal.setLayout(new GridBagLayout());
		panel_objetivos.setSize(100,100);
		panel_objetivos.setLayout(new GridBagLayout());
		
		panel_principal.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {	
				switch(e.getKeyCode()) {
					case KeyEvent.VK_LEFT: 	{ milogica.mover_jugador(IZQUIERDA); break; }
					case KeyEvent.VK_RIGHT: { milogica.mover_jugador(DERECHA); break; }
					case KeyEvent.VK_UP: 	{ milogica.mover_jugador(ARRIBA);break; }
					case KeyEvent.VK_DOWN: 	{ milogica.mover_jugador(ABAJO); break; }
					case KeyEvent.VK_W:		{ if (!bloquear_intercambios) milogica.intercambiar(ARRIBA); /*iniciarTiempo();*/ break; }
					case KeyEvent.VK_S:		{ if (!bloquear_intercambios) milogica.intercambiar(ABAJO); /*iniciarTiempo();*/ break; }
					case KeyEvent.VK_A:		{ if (!bloquear_intercambios) milogica.intercambiar(IZQUIERDA); /*iniciarTiempo();*/ break; }
					case KeyEvent.VK_D:		{ if (!bloquear_intercambios) milogica.intercambiar(DERECHA); /*iniciarTiempo();*/ break; } 
				}
			}
		});

		mostrarObjetivos();

		//Constraints TIMER
		GridBagConstraints c = new GridBagConstraints();
		c.insets = new Insets(0,10,0,0);

		c.gridx = 0;                               
	    c.gridy = 0;
		c.gridwidth = 2;
		c.gridheight = 1;
		c.weightx = 0;
		c.weightx = 0;
		getContentPane().add(timerLabel,c);
	
		//Constraints PANEL OBJETIVOS
		c.insets = new Insets(0, 10, 0, 0);      
	    c.gridx = 0;                               
	    c.gridy = 5;
		c.gridwidth = 4;
		c.gridheight = 1;
		getContentPane().add(panel_objetivos, c);

		//Constraints TABLERO
		c.insets = new Insets(0,0,0,0);
		c.gridx = 2;
		c.gridy = 1;
		c.gridwidth = 4;
		c.gridheight = 4;
		c.weightx = 1;
		c.weighty = 2;
		c.anchor = GridBagConstraints.CENTER;
		getContentPane().add(panel_principal,c);

		//Constraints MOVIMIENTOS
		c.gridx = 6;
		c.gridy = 0;
		c.gridwidth = 2;
		c.gridheight = 1;
		c.weightx = 0;
		c.weightx = 0;
		c.insets = new Insets(0,0,0,10);
		getContentPane().add(movimientosLabel, c);
		
		panel_principal.setFocusable(true);
	}

	public void mostrarObjetivos() {
		objetivosLabel1 = new JLabel();
		objetivosLabel2 = new JLabel();
		objetivosLabel3 = new JLabel();

		//pone el texto a los 2 primeros objetivoLabels
		objetivosLabel1.setText(milogica.obtenerInfoObjetivos()[0]);
		objetivosLabel2.setText(milogica.obtenerInfoObjetivos()[1]);

		//pone la imagen en el objetivosLabel3
		ImageIcon imgIcon = new ImageIcon(this.getClass().getResource(milogica.obtenerInfoObjetivos()[2]));
		Image imgEscalada = imgIcon.getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH);
		Icon iconoEscalado = new ImageIcon(imgEscalada);

		objetivosLabel3.setIcon(iconoEscalado);


		//asigno los contraints a cada componente
		GridBagConstraints c = new GridBagConstraints();
		c.insets = new Insets(0, 0, 0, 0);      
	   	c.gridx = 0;                               
	    c.gridy = 0;
		c.gridwidth = 1;
		//agrego los 3 labels al panel con los objetivos
		panel_objetivos.add(objetivosLabel1,c);
		c.gridy = 1;
		panel_objetivos.add(objetivosLabel2,c);
		c.gridx = 1;
		panel_objetivos.add(objetivosLabel3,c);
	}
	
	public int getTiempoRestante() {
		return tiempoRestante;
	}

	public void actualizarTiempo(int tiempo) {
		tiempoRestante = tiempo;
		timerLabel.setText("Tiempo restante: " + tiempoRestante);
	}
	public EntidadGrafica agregar_entidad(Entidad e) {
		GridBagConstraints c = new GridBagConstraints();
		Celda celda = new Celda(this, e, size_label);
		c.gridx = celda.getEntidad().getFila();
		c.gridx = celda.getEntidad().getColumna();
		panel_principal.add(celda,c);
		return celda;
	}

	public void notificarse_animacion_en_progreso() {
		synchronized(this){
			animaciones_pendientes ++;
			bloquear_intercambios = true;
		}
	}
	
	public void notificarse_animacion_finalizada() {
		synchronized(this){
			animaciones_pendientes --;
			bloquear_intercambios = animaciones_pendientes > 0;
		}
	}
	
	public void animar_movimiento(Celda c) {
		mi_animador.animar_cambio_posicion(c);
	}
	
	public void animar_cambio_estado(Celda c) {
		mi_animador.animar_cambio_estado(c);
	}

	public void animar_explosion(Celda c) {
		mi_animador.animar_explosion(c);
	}

	//Metodos agregados por bruno
	
	public void actualizarMovimientos(int movimientos) {
		movimientosRestantes = milogica.getMovimientos();
		movimientosLabel.setText("Movimientos restantes: "+movimientosRestantes);
	}

	public void iniciarTiempo() {
		animadorTiempo.iniciarTiempo();
	}

	public void mostrarMensajeDerrotaPorMovimientos() {
		System.out.println("GUI :: derrotaPorMovimientos");
		
	}

	public void mostrarMensajeDerrotaPorVidas() {

		
	}

	public void mostrarMensajeVictoriaPorMovimientos() {
		
	}
	
	public void mostrarMensajeVictoriaPorObjetivos() {
		
	}

	public void mostrarMensajeDerrotaPorTiempo() {
		System.out.println("GUI :: derrotaPorTiempo");
	}
	
	//--------------------------------------------------------
	public void limpiarGUI() {
	   
	    tiempoRestante = milogica.getTiempo();
	    movimientosRestantes = milogica.getMovimientos();
	    animaciones_pendientes = 0;
	    bloquear_intercambios = false;
	    

	    limpiarMatrizGUI();
	    

	    timerLabel.setText("Tiempo restante: " + tiempoRestante);
	    movimientosLabel.setText("Movimientos restantes: " + movimientosRestantes);
	}
	private void limpiarMatrizGUI() {
	    Component[] componentes = panel_principal.getComponents();
	    for (Component componente : componentes) {
	        panel_principal.remove(componente);
	    }
	    revalidate();
	    repaint();
	}
	
	public boolean getBloquear_intercambios() {
		return bloquear_intercambios;
	}


	/*public void mostrarImagenGema(String rutaImagen) {
	    ImageIcon icono = new ImageIcon(rutaImagen);
	    JLabel label = new JLabel(icono);
	    
	    GridBagConstraints c = new GridBagConstraints();
	    c.anchor = GridBagConstraints.PAGE_START;
	    c.insets = new Insets(10, 0, 0, 10);
	    c.gridx = 1;
	    c.gridy = 1;
	    
	    getContentPane().add(label, c);
	    revalidate(); // Asegura que la GUI se actualice correctamente
	}*/
	
}
