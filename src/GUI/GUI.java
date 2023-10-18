package GUI;


import java.awt.BorderLayout;
import java.awt.Color;
import Tablero.Tablero;
import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.Image;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.GridBagLayout;
import java.awt.GridLayout;

import Entidades.Entidad;
import Logica.Logica;
import Threads.AnimadorCronometro;
import Threads.CentralAnimaciones;


public class GUI extends JFrame {
	protected Logica milogica;
	protected int filas,columnas;
	protected JPanel panel_principal, panel_objetivos, panelVidas,mainPanel;
	protected JLabel timerLabel;
	protected int tiempoRestante;
	protected CentralAnimaciones mi_animador;
	protected int animaciones_pendientes;
	protected boolean bloquear_intercambios;
	protected JLabel movimientosLabel;
	protected JLabel[] objetivosProgreso;
	protected Celda matrizDeCeldas[][];
	protected int[] objetivosColores;
	protected int movimientosRestantes;
	protected AnimadorCronometro animadorTiempo;
	private int size_label = 70;

	//Movimientos
	public static final int ARRIBA = 15000;
	public static final int ABAJO = 15001;
	public static final int IZQUIERDA = 15002;
	public static final int DERECHA = 15003;
	
	Imagenfondo fondo = new Imagenfondo();
	
	public GUI(Logica l, int f, int c) {
		milogica = l;
		filas = f;
		mi_animador = new CentralAnimaciones(this);
		columnas = c;
		tiempoRestante = milogica.getTiempo();
		movimientosRestantes = milogica.getMovimientos();
		animaciones_pendientes = 0;
		matrizDeCeldas = new Celda[filas][columnas];
		bloquear_intercambios = false;
		objetivosColores = new int[milogica.getCantidadDeObjetivos()];
		objetivosProgreso = new JLabel[milogica.getCantidadDeObjetivos()];
		animadorTiempo = new AnimadorCronometro(tiempoRestante, this);
		
		inicializar();
	}
	
	
	protected void inicializar() {
		this.setContentPane(fondo);
		
		ImageIcon logo = new ImageIcon(this.getClass().getResource("/assets/nivel/Icono.png"));
		setIconImage(logo.getImage());

		mainPanel = new JPanel();
		mainPanel.setBackground(new Color(0,0,0,0));
		

		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		setTitle("Proyecto Candy Crush - Comision-06");
		mainPanel.setSize(screenSize);
		setSize(screenSize);
		setLayout(new BorderLayout());
		setExtendedState(JFrame.MAXIMIZED_BOTH); 
		setLocationRelativeTo(null);
		//----------------------------------------------------
		//Estaba true, le puse false para poder achicarlo y que aparezca la cruz
		setUndecorated(false); 
		//----------------------------------------------------
		setVisible(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setExtendedState(JFrame.MAXIMIZED_BOTH);
		mainPanel.setLayout(new GridBagLayout());

		timerLabel = new JLabel("Tiempo restante: "+ tiempoRestante);
		timerLabel.setOpaque(true);
		timerLabel.setFont(new Font("Algerian", Font.PLAIN, 30));
		timerLabel.setForeground(Color.WHITE);
		timerLabel.setBackground(new Color(0,0,0,255));

		movimientosLabel = new JLabel ("Movimientos restantes: "+movimientosRestantes);
		movimientosLabel.setOpaque(true);
		movimientosLabel.setFont(new Font("Algerian", Font.PLAIN, 20));
		movimientosLabel.setForeground(Color.WHITE);
		movimientosLabel.setBackground(new Color(0,0,0,255));

		panel_principal = new JPanel();
		panel_principal.setSize(size_label * filas, size_label * columnas);
		panel_principal.setLayout(new GridLayout(filas,columnas,0,0));
		panel_principal.setOpaque(true);
		panel_principal.setBackground(new Color(0,0,0,255));


		panel_objetivos = new JPanel();
		panel_objetivos.setSize(100,100);
		panel_objetivos.setLayout(new GridBagLayout());
		panel_objetivos.setOpaque(true);
		panel_objetivos.setBackground(new Color(0,0,0,255));

		
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
		
		mainPanel.add(timerLabel,c);
	
		//Constraints PANEL OBJETIVOS
		c.insets = new Insets(0, 10, 0, 0);      
	    c.gridx = 0;                               
	    c.gridy = 1;
		c.gridwidth = 2;
		c.gridheight = 2;
		mainPanel.add(panel_objetivos, c);

		//Constraints TABLERO
		c.insets = new Insets(0,0,0,0);
		c.gridx = 2;
		c.gridy = 1;
		c.gridwidth = 4;
		c.gridheight = 4;
		c.weightx = 1;
		c.weighty = 1;
		c.anchor = GridBagConstraints.CENTER;
		
		mainPanel.add(panel_principal,c);

		//Constraints VIDAS
		c.gridx = 6;
		c.gridy = 6;
		c.gridwidth = 2;
		c.gridheight = 1;
		c.weightx = 0;
		c.weightx = 0;
		c.insets = new Insets(0,0,0,10);
		mainPanel.add(panelVidas,c);

		//Constraints MOVIMIENTOS
		c.gridx = 6;
		c.gridy = 0;
		c.gridwidth = 2;
		c.gridheight = 1;
		c.weightx = 0;
		c.weightx = 0;
		c.insets = new Insets(0,0,0,10);
		mainPanel.add(movimientosLabel, c);
		
		panel_principal.setFocusable(true);
		getContentPane().add(mainPanel);
	}

	private JLabel label_corazon1 = new JLabel();
	private JLabel label_corazon2 = new JLabel();
	private JLabel label_corazon3 = new JLabel();

	public void mostrarVidas() {
		panelVidas = new JPanel();
		panelVidas.setLayout(new GridLayout(1,3,5,5));
		panelVidas.setBackground(new Color(0,0,0,0));
		
		
		ImageIcon imgIconCorazon = new ImageIcon(this.getClass().getResource("/assets/nivel/corazon.png"));
		Image imgEscaladaCorazon = imgIconCorazon.getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH);
		Icon iconoEscaladoCorazon = new ImageIcon(imgEscaladaCorazon);
		
		label_corazon1.setIcon(iconoEscaladoCorazon);
		label_corazon3.setIcon(iconoEscaladoCorazon);
		label_corazon2.setIcon(iconoEscaladoCorazon);


		panelVidas.add(label_corazon1);
		panelVidas.add(label_corazon2);
		panelVidas.add(label_corazon3);
	}

	public void actualizarVidas() {
		ImageIcon imgIconCorazonVacio = new ImageIcon(this.getClass().getResource("/assets/nivel/corazonVacio.png"));
		Image imgEscaladaCorazonVacio = imgIconCorazonVacio.getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH);
		Icon iconoEscaladoCorazonVacio = new ImageIcon(imgEscaladaCorazonVacio);
		

		if(milogica.getVidas() == 2) {

			label_corazon3.setIcon(iconoEscaladoCorazonVacio);
		} else if(milogica.getVidas() == 1) {
				System.out.println("QUEDA 1 VIDA");
				label_corazon2.setIcon(iconoEscaladoCorazonVacio);
				label_corazon3.setIcon(iconoEscaladoCorazonVacio);
			}

		GridBagConstraints gbc = new GridBagConstraints();
		gbc.gridx = 6;
		gbc.gridy = 6;
		gbc.gridwidth = 2;
		gbc.gridheight = 1;
		gbc.weightx = 0;
		gbc.weightx = 0;
		gbc.insets = new Insets(0,0,0,10);
			
		mainPanel.add(panelVidas,gbc);
	}
	
	public void mostrarObjetivos() {	
		JLabel tituloObjetivo = new JLabel();
		tituloObjetivo.setFont(new Font("Algerian", Font.PLAIN, 20));
		tituloObjetivo.setText("OBJETIVOS:");
		tituloObjetivo.setForeground(Color.WHITE);
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
			objetivosTexto.setForeground(Color.WHITE);
			objetivosTexto.setFont(new Font("Algerian", Font.PLAIN, 15));
			JLabel objetivosNumero = objetivosProgreso[numeroDeObjetivo] = new JLabel();
			objetivosNumero.setForeground(Color.WHITE);
			objetivosNumero.setFont(new Font("Arial", Font.PLAIN, 15));

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
		Celda celda = new Celda(this, e, size_label);
		e.setEntidadGrafica(celda);
		matrizDeCeldas[e.getFila()][e.getColumna()] = celda;
		panel_principal.add(matrizDeCeldas[e.getFila()][e.getColumna()]);
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
		}
	}
	
	public void animar_movimiento(Celda c) {
		synchronized(c){
			mi_animador.animar_cambio_posicion(c);
		}
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
		mainPanel.setVisible(false);
		JPanel p1 = new JPanel();
		p1.setSize(new Dimension(MAXIMIZED_HORIZ, MAXIMIZED_HORIZ));
		p1.setBackground(new Color(0,0,0,120));
		JLabel label = new JLabel("Perdiste, no tienes mas vidas");
		JButton botonReiniciar = new JButton("Reiniciar");

		p1.add(label);
		p1.add(botonReiniciar);
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.fill = GridBagConstraints.VERTICAL;
		gbc.anchor = GridBagConstraints.CENTER;

		getContentPane().add(p1,gbc);
		

		p1.setVisible(true);
		mainPanel.repaint();

		botonReiniciar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				mainPanel.setVisible(true);
				p1.setVisible(false);
				milogica.notificarDerrotaPorVidas();
			}
		});
	}

	public void mostrarMensajeVictoriaPorMovimientos() {
		mainPanel.setVisible(false);
		JPanel p1 = new JPanel();
		p1.setSize(new Dimension(MAXIMIZED_HORIZ, MAXIMIZED_HORIZ));
		p1.setBackground(new Color(0,0,0,120));
		JLabel label = new JLabel("GANASTE");
		JButton botonReiniciar = new JButton("Siguiente Nivel");

		p1.add(label);
		p1.add(botonReiniciar);
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.fill = GridBagConstraints.VERTICAL;
		gbc.anchor = GridBagConstraints.CENTER;

		getContentPane().add(p1,gbc);
		

		p1.setVisible(true);
		mainPanel.repaint();

		botonReiniciar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				mainPanel.setVisible(true);
				p1.setVisible(false);
				milogica.notificarDerrotaPorVidas();
			}
		});
	}
	
	public void mostrarMensajeVictoriaPorObjetivos() {
		mainPanel.setVisible(false);
		JPanel p1 = new JPanel();
		p1.setSize(new Dimension(MAXIMIZED_HORIZ, MAXIMIZED_HORIZ));
		p1.setBackground(new Color(0,0,0,120));
		JLabel label = new JLabel("GANASTE!");
		JButton botonReiniciar = new JButton("Siguiente Nivel");

		p1.add(label);
		p1.add(botonReiniciar);
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.fill = GridBagConstraints.VERTICAL;
		gbc.anchor = GridBagConstraints.CENTER;

		getContentPane().add(p1,gbc);
		

		p1.setVisible(true);
		mainPanel.repaint();

		botonReiniciar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				mainPanel.setVisible(true);
				p1.setVisible(false);
				milogica.notificarVictoriaPorObjetivos();
			}
		});
	}

	public void mostrarMensajeDerrotaPorTiempo() {
		mainPanel.setVisible(false);
		JPanel p1 = new JPanel();
		p1.setLayout(new GridBagLayout());
		p1.setSize(new Dimension(MAXIMIZED_HORIZ, MAXIMIZED_HORIZ));
		p1.setBackground(new Color(0,0,0,120));

		JLabel labelPerdiste1 = new JLabel("PERDISTE");
		labelPerdiste1.setFont(new Font("Algerian", Font.PLAIN, 50));
		labelPerdiste1.setForeground(Color.WHITE);
		JLabel labelPerdiste2 = new JLabel("Te quedaste sin tiempo");
		labelPerdiste2.setFont(new Font("Algerian", Font.PLAIN, 30));
		labelPerdiste2.setForeground(Color.WHITE);
		JLabel labelPerdiste3 = new JLabel("Pierdes una vida");
		labelPerdiste3.setFont(new Font("Algerian", Font.PLAIN, 30));
		labelPerdiste3.setForeground(Color.WHITE);
		JButton botonReiniciar = new JButton("Reintentar");
		botonReiniciar.setBackground(new Color(0,0,0,200));
		labelPerdiste3.setForeground(Color.WHITE);

		GridBagConstraints gbc = new GridBagConstraints();

		gbc.gridy = 0;
		p1.add(labelPerdiste1,gbc);
		gbc.gridy = 1;
		p1.add(labelPerdiste2,gbc);
		gbc.gridy = 2;
		p1.add(labelPerdiste3,gbc);
		gbc.gridy = 3;
		p1.add(botonReiniciar,gbc);

		getContentPane().add(p1);
		

		p1.setVisible(true);
		mainPanel.repaint();

		botonReiniciar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				mainPanel.setVisible(true);
				p1.setVisible(false);
				milogica.notificarDerrotaPorMovimientos();
			}
		});
	}
	
	//--------------------------------------------------------
	public void limpiarGUI() {
	   
	    tiempoRestante = milogica.getTiempo();
	    movimientosRestantes = milogica.getMovimientos();
	    animaciones_pendientes = 0;
	    bloquear_intercambios = false;
	    

	    limpiarMatrizGUI();

		mostrarVidas();
	    

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


	//Clase auxiliar para poder hacer el fondo


	public class Imagenfondo extends JPanel{
		
		private Image imagen;
		
		public void paint(Graphics g) {
			imagen = new ImageIcon(getClass().getResource("/assets/nivel/3_Nivel.png")).getImage();
			g.drawImage(imagen, 0, 0, getWidth(), getHeight(), this);
			setOpaque(false); 
			super.paint(g);
		}
	}
}

