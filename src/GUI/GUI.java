package GUI;



import Tablero.Tablero;
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
	protected JPanel panel_principal, panel_objetivos, panelVidas;
	protected JLabel timerLabel;
	protected int tiempoRestante;
	protected CentralAnimaciones mi_animador;
	protected int animaciones_pendientes;
	protected Tablero miTablero;
	protected boolean bloquear_intercambios;
	protected JLabel movimientosLabel;
	protected JLabel[] objetivosProgreso;
	protected int[] objetivosColores;
	protected int movimientosRestantes;
	protected AnimadorCronometro animadorTiempo;
	private int size_label = 70;

	//Movimientos
	public static final int ARRIBA = 15000;
	public static final int ABAJO = 15001;
	public static final int IZQUIERDA = 15002;
	public static final int DERECHA = 15003;
	
	
	public GUI(Logica l,Tablero t, int f, int c) {
		milogica = l;
		miTablero = t;
		filas = f;
		mi_animador = new CentralAnimaciones(this);
		columnas = c;
		tiempoRestante = milogica.getTiempo();
		movimientosRestantes = milogica.getMovimientos();
		animaciones_pendientes = 0;
		bloquear_intercambios = false;
		objetivosColores = new int[milogica.getCantidadDeObjetivos()];
		objetivosProgreso = new JLabel[milogica.getCantidadDeObjetivos()];
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
		mostrarVidas();

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
	    c.gridy = 1;
		c.gridwidth = 2;
		c.gridheight = 2;
		getContentPane().add(panel_objetivos, c);

		//Constraints TABLERO
		c.insets = new Insets(0,0,0,0);
		c.gridx = 2;
		c.gridy = 1;
		c.gridwidth = 4;
		c.gridheight = 4;
		c.weightx = 1;
		c.weighty = 1;
		c.anchor = GridBagConstraints.CENTER;
		getContentPane().add(panel_principal,c);

		//Constraints VIDAS
		c.gridx = 6;
		c.gridy = 6;
		c.gridwidth = 2;
		c.gridheight = 1;
		c.weightx = 0;
		c.weightx = 0;
		c.insets = new Insets(0,0,0,10);
		getContentPane().add(panelVidas,c);

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

	public void mostrarVidas() {
		panelVidas = new JPanel();
		panelVidas.setLayout(new GridBagLayout());
		JLabel label_corazon1 = new JLabel();
		JLabel label_corazon2 = new JLabel();
		JLabel label_corazon3 = new JLabel();

		ImageIcon imgIconCorazon = new ImageIcon(this.getClass().getResource("/assets/nivel/corazon.png"));
		Image imgEscaladaCorazon = imgIconCorazon.getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH);
		Icon iconoEscaladoCorazon = new ImageIcon(imgEscaladaCorazon);

		ImageIcon imgIconCorazonVacio = new ImageIcon(this.getClass().getResource("/assets/nivel/corazonVacio.png"));
		Image imgEscaladaCorazonVacio = imgIconCorazonVacio.getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH);
		Icon iconoEscaladoCorazonVacio = new ImageIcon(imgEscaladaCorazonVacio);
		
		if(milogica.getVidas() == 3) {
			label_corazon1.setIcon(iconoEscaladoCorazon);
			label_corazon3.setIcon(iconoEscaladoCorazon);
			label_corazon2.setIcon(iconoEscaladoCorazon);
		} else if(milogica.getVidas() == 2) {
					label_corazon1.setIcon(iconoEscaladoCorazon);
					label_corazon2.setIcon(iconoEscaladoCorazon);
					label_corazon3.setIcon(iconoEscaladoCorazonVacio);
				}else if(milogica.getVidas() == 1) {
					label_corazon1.setIcon(iconoEscaladoCorazon);
					label_corazon2.setIcon(iconoEscaladoCorazonVacio);
					label_corazon3.setIcon(iconoEscaladoCorazonVacio);
				}

		GridBagConstraints gbc = new GridBagConstraints();

		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.insets = new Insets(0,5,0,5);
		panelVidas.add(label_corazon1,gbc);
		gbc.gridx = 1;
		panelVidas.add(label_corazon2,gbc);
		gbc.gridx = 2;
		panelVidas.add(label_corazon3,gbc);
	}
	
	public void mostrarObjetivos() {	
		JLabel tituloObjetivo = new JLabel();
		tituloObjetivo.setText("OBJETIVOS:");
		GridBagConstraints cTitulo = new GridBagConstraints();
		cTitulo.insets = new Insets(0, 0, 0, 0);      
	   	cTitulo.gridx = 0;                               
	    cTitulo.gridy = 0;
		cTitulo.gridwidth = 2;
			
		panel_objetivos.add(tituloObjetivo,cTitulo);

		//sirve para setear el gridy, aumenta con cada iteracion para poder setear el siguiente objetivo
		//debajo del anterior

		int coordenada_y = 1;
		int numeroDeObjetivo = 0;

		for(int i=0;i<milogica.obtenerInfoObjetivos().length; i = i+4) {
			int color = Integer.parseInt(milogica.obtenerInfoObjetivos()[i+3]);
			objetivosColores[numeroDeObjetivo] = color;
			JLabel objetivosTexto = new JLabel();
			JLabel objetivosImagen = new JLabel();
			JLabel objetivosNumero = objetivosProgreso[numeroDeObjetivo] = new JLabel();

			//obtenerInfoObjetivos[i] siempre devuelve el texto
			//obtenerInfoObjetivos[i+1] siempre devuelve la imagen de la gema correspondiente

			objetivosTexto.setText(milogica.obtenerInfoObjetivos()[i]);
		
			ImageIcon imgIcon = new ImageIcon(this.getClass().getResource(milogica.obtenerInfoObjetivos()[i+1]));
			Image imgEscalada = imgIcon.getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH);
			Icon iconoEscalado = new ImageIcon(imgEscalada);

			objetivosImagen.setIcon(iconoEscalado);

			objetivosNumero.setText("0/"+milogica.obtenerInfoObjetivos()[i+2]);

			//seteo los constraints y los posiciono

			GridBagConstraints c = new GridBagConstraints();
			c.insets = new Insets(0, 20, 0, 0);      
	   		c.gridx = 0;                               
	    	c.gridy = coordenada_y;
			c.gridwidth = 1;
			
			//seteo el texto en posicion [0,coordenada_y]
			panel_objetivos.add(objetivosTexto,c);
			c.gridx = 1;
			//seteo la imagen en posicion [1,coordenada_y]
			panel_objetivos.add(objetivosImagen,c);
			//seteo el progreso

			c.gridx = 0;
			c.insets = new Insets(0, 0, 0, 0);
			c.gridy = coordenada_y+1;
			c.gridwidth = 2;

			panel_objetivos.add(objetivosNumero,c);

			coordenada_y = coordenada_y + 2;

		
		}
	}

	public void actualizarProgreso(int gemasRestantes, int tipoGema) {
		for(int i=0; i<objetivosColores.length;i++) {
			
			System.out.println(objetivosColores[i]);
			if(tipoGema == objetivosColores[i]) {
				String aux = objetivosProgreso[i].getText();
				String[] partes = aux.split("/");
				Integer num = Integer.valueOf(partes[1]);
				int gemasTotales = num.intValue();
				int progreso = gemasTotales - gemasRestantes;

				objetivosProgreso[i].setText(progreso+"/"+gemasTotales);
			}
		}
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
		e.setEntidadGrafica(celda);
		c.gridx = celda.getEntidad().getFila();
		c.gridx = celda.getEntidad().getColumna();
		panel_principal.add(celda,c);
		panel_principal.revalidate();
		panel_principal.repaint();
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
			panel_principal = new JPanel();
			panel_principal.setSize(size_label * filas, size_label * columnas);
			panel_principal.setLayout(new GridBagLayout());
			limpiarMatrizGUI();
			reiniciarGUI();
			GridBagConstraints gbc = new GridBagConstraints();
			gbc.insets = new Insets(0,0,0,0);
			gbc.gridx = 2;
			gbc.gridy = 1;
			gbc.gridwidth = 4;
			gbc.gridheight = 4;
			gbc.weightx = 1;
			gbc.weighty = 1;
			gbc.anchor = GridBagConstraints.CENTER;
			getContentPane().add(panel_principal,gbc);
		}
	}
	
	public void animar_movimiento(Celda c) {
		synchronized(c){
			mi_animador.animar_cambio_posicion(c);
		}
	}

	//este proyecto es un crimen de odio a la programacion
	private void reiniciarGUI() {
		for(int i=0;i<filas;i++) {
			for(int j=0;j<columnas;j++) {
				agregar_entidad(miTablero.getEntidad(i, j));
				revalidate();
				repaint();
			}
		}
	}

	public void animar_cambio_estado(Celda c) {
		mi_animador.animar_cambio_estado(c);
	}

	public void animar_explosion(Celda c) {
		mi_animador.animar_explosion(c);
	}
	
	public void animar_caida(Celda c) {
		mi_animador.animar_caida(c);
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
}
