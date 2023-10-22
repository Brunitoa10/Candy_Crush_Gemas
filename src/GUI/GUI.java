package GUI;


import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.HashMap;
import java.util.Map;
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
import javax.swing.SwingUtilities;

import java.awt.GridBagLayout;
import java.awt.GridLayout;
import Logica.EntidadLogica;
import Logica.Logica;
import Threads.CentralAnimaciones;


public class GUI extends JFrame implements VentanaAnimable, VentanaNotificable{
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
	protected int[] objetivosColores;
	protected int movimientosRestantes;
	private int size_label = 70;
	private JLabel label_corazon1 = new JLabel();
	private JLabel label_corazon2 = new JLabel();
	private JLabel label_corazon3 = new JLabel();
	private Imagenfondo fondo = new Imagenfondo();
	
	// Define un mapa para asociar los códigos de tecla con las acciones
	Map<Integer, Runnable> acciones = new HashMap<>();
	
	
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
		objetivosColores = new int[milogica.getCantidadDeObjetivos()];
		objetivosProgreso = new JLabel[milogica.getCantidadDeObjetivos()];
		
		inicializarGUI();
	}
	
	
	private void inicializarGUI() {
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

		JPanel timerPanel = new JPanel();
		timerLabel = new JLabel("Tiempo restante: "+ agregarPaddingTiempo(tiempoRestante));
		timerLabel.setOpaque(true);
		timerLabel.setFont(new Font("Algerian", Font.PLAIN, 30));
		timerLabel.setForeground(Color.WHITE);
		timerLabel.setBackground(new Color(0,0,0,255));
		timerPanel.add(timerLabel);

		movimientosLabel = new JLabel ("Movimientos restantes: "+movimientosRestantes);
		movimientosLabel.setOpaque(true);
		movimientosLabel.setFont(new Font("Algerian", Font.PLAIN, 20));
		movimientosLabel.setForeground(Color.WHITE);
		movimientosLabel.setBackground(new Color(0,0,0,255));

		panel_principal = new JPanel();
		panel_principal.setSize(size_label * filas, size_label * columnas);
		panel_principal.setLayout(new GridLayout(filas,columnas,0,0));
		panel_principal.setOpaque(true);
		panel_principal.setBackground(new Color(0,0,0,0));


		panel_objetivos = new JPanel();
		panel_objetivos.setSize(100,100);
		panel_objetivos.setLayout(new GridBagLayout());
		panel_objetivos.setOpaque(true);
		panel_objetivos.setBackground(new Color(0,0,0,255));


		configurarAccionesTeclado();
		mostrarObjetivos();
		mostrarVidas();

		//Constraints TIMER
		GridBagConstraints c = new GridBagConstraints();
		c.insets = new Insets(0,10,0,0);
		agregarConGBCs(timerPanel, mainPanel, c, 0, 0, 3, 1);
	
		//Constraints PANEL OBJETIVOS
		c.insets = new Insets(0, 10, 0, 0);   
		agregarConGBCs(panel_objetivos, mainPanel, c, 0, 1, 2, 2);   
		
		//Constraints TABLERO
		c.insets = new Insets(0,0,0,0);
		c.weightx = 1;
		c.weighty = 1;
		c.anchor = GridBagConstraints.CENTER;
		agregarConGBCs(panel_principal, mainPanel, c, 2, 1, 4, 4); 
		
		//Constraints VIDAS
		c.weightx = 0;
		c.weightx = 0;
		c.insets = new Insets(0,0,0,10);
		agregarConGBCs(panelVidas, mainPanel, c, 6, 6, 2, 1); 
		 
		
		//Constraints MOVIMIENTOS
		c.weightx = 0;
		c.weightx = 0;
		c.insets = new Insets(0,0,0,10);
		agregarConGBCs(movimientosLabel, mainPanel, c, 6, 0, 2, 1); 
		
		panel_principal.setFocusable(true);
		getContentPane().add(mainPanel);
	}

	private void configurarAccionesTeclado() {
		//Acciones del cursor
		acciones.put(KeyEvent.VK_LEFT, () ->{
			milogica.mover_jugador(IZQUIERDA);
		});
		acciones.put(KeyEvent.VK_RIGHT, () -> {
			milogica.mover_jugador(DERECHA);
		});
		acciones.put(KeyEvent.VK_UP, () -> {
			milogica.mover_jugador(ARRIBA);
		});
		acciones.put(KeyEvent.VK_DOWN, () ->{
			milogica.mover_jugador(ABAJO);
		});
		
		//Acciones de intercambio
		acciones.put(KeyEvent.VK_W, () -> {
		    if (!bloquear_intercambios) {
		    	milogica.intercambiar(ARRIBA);
		    }
		});
		
		acciones.put(KeyEvent.VK_S, () -> {
		    if (!bloquear_intercambios){
		    	milogica.intercambiar(ABAJO);
		    }
		});
		
		acciones.put(KeyEvent.VK_A, () -> {
		    if (!bloquear_intercambios){
		    	milogica.intercambiar(IZQUIERDA);
		    }
		});
		
		acciones.put(KeyEvent.VK_D, () -> {
		    if (!bloquear_intercambios){
		    	milogica.intercambiar(DERECHA);
		    }
		});

		// Agrega el KeyListener
		panel_principal.addKeyListener(new KeyAdapter() {
		    @Override
		    public void keyPressed(KeyEvent e) {
		        // Verifica si la tecla presionada tiene una acción asociada
		        Runnable accion = acciones.get(e.getKeyCode());
		        if (accion != null) {
		        	accion.run();
		        }
		    }
		});
	}


	public EntidadGrafica agregar_entidad(EntidadLogica e) {
		Celda celda = new Celda(this, e, size_label);
		panel_principal.add(celda);
		return celda;
	}

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

	    int vidas = milogica.getVidas();

	    if (vidas == 2) {
	        label_corazon3.setIcon(iconoEscaladoCorazonVacio);
	    } else if (vidas == 1) {
	        System.out.println("QUEDA 1 VIDA");
	        label_corazon2.setIcon(iconoEscaladoCorazonVacio);
	        label_corazon3.setIcon(iconoEscaladoCorazonVacio);
	    }

	    GridBagConstraints gbc = new GridBagConstraints();
	    gbc.weightx = 0;
	    gbc.insets = new Insets(0, 0, 0, 10);
	    agregarConGBCs(panelVidas, mainPanel, gbc, 6, 6, 2, 1);
	}
	
	public void mostrarObjetivos() {
	    agregarTituloObjetivo();
	    
	    int coordenada_y = 1;
	    int numeroDeObjetivo = 0;

	    for (int i = 0; i < milogica.obtenerInfoObjetivos().length; i += 4) {
	        int color = Integer.parseInt(milogica.obtenerInfoObjetivos()[i + 3]);
	        objetivosColores[numeroDeObjetivo] = color;

	        JLabel objetivosTexto = crearLabelObjetivoTexto(i);
	        JLabel objetivosImagen = crearLabelObjetivoImagen(i);
	        JLabel objetivosNumero = crearLabelObjetivoNumero(i);

	        agregarObjetivoAlPanel(objetivosTexto, 0, coordenada_y, 1, 1);
	        agregarObjetivoAlPanel(objetivosImagen, 1, coordenada_y, 1, 1);
	        agregarObjetivoAlPanel(objetivosNumero, 0, coordenada_y + 1, 2, 1);

	        coordenada_y += 2;
	        numeroDeObjetivo++;
	    }
	}

	private void agregarTituloObjetivo() {
	    JLabel tituloObjetivo = new JLabel("OBJETIVOS:");
	    tituloObjetivo.setFont(new Font("Algerian", Font.PLAIN, 20));
	    tituloObjetivo.setForeground(Color.WHITE);

	    GridBagConstraints cTitulo = new GridBagConstraints();
	    cTitulo.insets = new Insets(0, 0, 0, 0);
	    cTitulo.gridx = 0;
	    cTitulo.gridy = 0;
	    cTitulo.gridwidth = 2;

	    panel_objetivos.add(tituloObjetivo, cTitulo);
	    agregarConGBCs(tituloObjetivo, panel_objetivos, cTitulo, 0, 0, 2, 1);
	}

	private JLabel crearLabelObjetivoTexto(int index) {
	    JLabel objetivosTexto = new JLabel();
	    objetivosTexto.setForeground(Color.WHITE);
	    objetivosTexto.setFont(new Font("Algerian", Font.PLAIN, 15));
	    objetivosTexto.setText(milogica.obtenerInfoObjetivos()[index]);
	    return objetivosTexto;
	}

	private JLabel crearLabelObjetivoImagen(int index) {
	    ImageIcon imgIcon = new ImageIcon(this.getClass().getResource(milogica.obtenerInfoObjetivos()[index + 1]));
	    Image imgEscalada = imgIcon.getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH);
	    Icon iconoEscalado = new ImageIcon(imgEscalada);

	    JLabel objetivosImagen = new JLabel();
	    objetivosImagen.setIcon(iconoEscalado);

	    return objetivosImagen;
	}

	private JLabel crearLabelObjetivoNumero(int index) {
	    JLabel objetivosNumero = new JLabel("0/" + milogica.obtenerInfoObjetivos()[index + 2]);
	    objetivosNumero.setForeground(Color.WHITE);
	    objetivosNumero.setFont(new Font("Arial", Font.PLAIN, 15));
	    return objetivosNumero;
	}

	private void agregarObjetivoAlPanel(JLabel objetivo, int x, int y, int width, int height) {
	    GridBagConstraints c = new GridBagConstraints();
	    c.insets = new Insets(0, 20, 0, 0);
	    c.weightx = 0;

	    agregarConGBCs(objetivo, panel_objetivos, c, x, y, width, height);
	}
	public void actualizarProgreso(int gemasRestantes, int tipoGema) {
		for(int i=0; i<objetivosColores.length;i++) {
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

	/*public void reiniciarProgreso() {
		for(int i=0; i<objetivosColores.length; i++) {
			String aux = objetivosProgreso[i].getText();
			String[] partes = aux.split("/");
			Integer num = Integer.valueOf(partes[1]);
			int gemasTotales = num.intValue();
			int progreso = 0;

			objetivosProgreso[i].setText(progreso+"/"+gemasTotales);
		}
	}*/
	
	public void reiniciarProgreso() {
	    for(int i = 0; i < objetivosColores.length; i++) {
	        String aux = objetivosProgreso[i].getText();
	        String[] partes = aux.split("/");
	        int gemasTotales = Integer.parseInt(partes[1]);

	        objetivosProgreso[i].setText("0/" + gemasTotales);
	    }
	}
	public int getTiempoRestante() {
		return tiempoRestante;
	}

	public void actualizarTiempo(int tiempo) {
		
	    SwingUtilities.invokeLater(() -> {
	        timerLabel.setText("Tiempo restante: " + agregarPaddingTiempo(tiempo));
	    });
	}
	
	private String agregarPaddingTiempo(int tiempo) {
		String padding = "";
			if(tiempo <= 9) {
			padding = "00";
			} else if(tiempo <= 99) {
				padding = "0";
			}
		return padding+tiempo;
	}

	public void notificarse_animacion_en_progreso() {
		synchronized(this){
			animaciones_pendientes ++;
			bloquear_intercambios = true;
		}
	}
	
	@Override
	public void notificarse_animacion_finalizada() {
		synchronized(this){
			animaciones_pendientes --;
			bloquear_intercambios = animaciones_pendientes > 0;
		}
	}
	
	@Override
	public void animar_movimiento(Celda c) {
		synchronized(this){
			mi_animador.animar_cambio_posicion(c);
		}
	}
	
	@Override
	public void animar_cambio_estado(Celda c) {
		synchronized(this){
			mi_animador.animar_cambio_estado(c);
		}
		
	}

	/*public void animar_explosion(Celda c) {
		synchronized(c){
		mi_animador.agregar_explosion(c);
		}
	}
	
	/*public void animar_caida(Celda c) {
		synchronized(c){
		mi_animador.agregar_caida(c);
		}
	}*/

	//Metodos agregados por bruno
	
	public void actualizarMovimientos(int movimientos) {
		movimientosLabel.setText("Movimientos restantes: "+movimientos);
	}


	public void mostrarMensajeDerrotaPorMovimientos() {
	    this.repaint();
	    mainPanel.setVisible(false);

	    JPanel mensajePanel = crearMensajePanel();
	    agregarComponentesAlMensajePanel(mensajePanel);

	    getContentPane().add(mensajePanel);

	    JButton botonReiniciar = crearBotonReintentar();
	    agregarAccionBotonReiniciar(botonReiniciar, mainPanel, mensajePanel);

	    mensajePanel.setVisible(true);
	    mensajePanel.revalidate();
	}

	private JPanel crearMensajePanel() {
	    JPanel p = new JPanel();
	    p.setLayout(new GridBagLayout());
	    p.setSize(new Dimension(MAXIMIZED_HORIZ, MAXIMIZED_HORIZ));
	    p.setBackground(new Color(0, 0, 0, 120));
	    return p;
	}

	private void agregarComponentesAlMensajePanel(JPanel p) {
	    GridBagConstraints gbc = new GridBagConstraints();

	    JLabel labelPerdiste1 = crearLabel("PERDISTE", 50);
	    agregarConGBCs(labelPerdiste1, p, gbc, 0, 0, 1, 1);

	    JLabel labelPerdiste2 = crearLabel("Te quedaste sin movimientos", 30);
	    agregarConGBCs(labelPerdiste2, p, gbc, 0, 1, 1, 1);

	    JLabel labelPerdiste3 = crearLabel("Pierdes una vida", 30);
	    agregarConGBCs(labelPerdiste3, p, gbc, 0, 2, 1, 1);
	}

	private JLabel crearLabel(String texto, int tamañoFuente) {
	    JLabel label = new JLabel(texto);
	    label.setFont(new Font("Algerian", Font.PLAIN, tamañoFuente));
	    label.setForeground(Color.WHITE);
	    return label;
	}

	private JButton crearBotonReintentar() {
	    JButton botonReiniciar = new JButton("Reintentar");
	    botonReiniciar.setBackground(new Color(0, 0, 0, 200));
	    botonReiniciar.setForeground(Color.WHITE);
	    return botonReiniciar;
	}

	private void agregarAccionBotonReiniciar(JButton boton, JPanel mainPanel, JPanel mensajePanel) {
	    boton.addActionListener(new ActionListener() {
	        @Override
	        public void actionPerformed(ActionEvent e) {
	            mainPanel.setVisible(true);
	            mensajePanel.setVisible(false);
	        }
	    });
	}
	
	public void mostrarMensajeDerrotaPorVidas() {
	    mainPanel.setVisible(false);

	    JPanel mensajePanel = crearMensajePanel();
	    agregarComponentesAlMensajePanelPorVidas(mensajePanel);

	    getContentPane().add(mensajePanel);

	    JButton botonReiniciarJuego = crearBotonReintentar();
	    agregarAccionBotonReiniciarPorVidas(botonReiniciarJuego, mainPanel, mensajePanel);

	    mensajePanel.setVisible(true);
	    mensajePanel.revalidate();
	}


	private void agregarComponentesAlMensajePanelPorVidas(JPanel p) {
	    GridBagConstraints gbc = new GridBagConstraints();

	    JLabel labelPerdiste1 = crearLabel("PERDISTE", 50);
	    agregarConGBCs(labelPerdiste1, p, gbc, 0, 0, 1, 1);

	    JLabel labelPerdiste2 = crearLabel("Ya no te quedan más vidas", 30);
	    agregarConGBCs(labelPerdiste2, p, gbc, 0, 1, 1, 1);

	    JLabel labelPerdiste3 = crearLabel("Empieza de nuevo desde el nivel 1", 30);
	    agregarConGBCs(labelPerdiste3, p, gbc, 0, 2, 1, 1);
	}


	private void agregarAccionBotonReiniciarPorVidas(JButton boton, JPanel mainPanel, JPanel mensajePanel) {
	    boton.addActionListener(new ActionListener() {
	        @Override
	        public void actionPerformed(ActionEvent e) {
	            mainPanel.setVisible(true);
	            mensajePanel.setVisible(false);
	            milogica.notificarDerrotaPorVidas();
	        }
	    });
	}
	
	public void mostrarMensajeFinDelJuego() {
	    mainPanel.setVisible(false);

	    JPanel mensajePanel = crearMensajePanel();
	    agregarComponentesAlMensajePanelFinDelJuego(mensajePanel);

	    getContentPane().add(mensajePanel);

	    mensajePanel.setVisible(true);
	    mensajePanel.revalidate();
	}

	private void agregarComponentesAlMensajePanelFinDelJuego(JPanel p) {
	    GridBagConstraints gbc = new GridBagConstraints();

	    JLabel labelGanaste1 = crearLabel("GANASTE", 50);
	    agregarConGBCs(labelGanaste1, p, gbc, 0, 0, 1, 1);

	    JLabel labelGanaste2 = crearLabel("Felicidades! Llegaste al final del juego", 30);
	    agregarConGBCs(labelGanaste2, p, gbc, 0, 1, 1, 1);

	    JLabel labelGanaste3 = crearLabel("Gracias por jugar", 30);
	    agregarConGBCs(labelGanaste3, p, gbc, 0, 2, 1, 1);
	}

	public void mostrarMensajeVictoriaPorObjetivos() {
	    mainPanel.setVisible(false);

	    JPanel mensajePanel = crearMensajePanel();
	    agregarComponentesAlMensajePanelVictoriaPorObjetivos(mensajePanel);

	    getContentPane().add(mensajePanel);

	    mensajePanel.setVisible(true);
	    mensajePanel.revalidate();
	}

	private void agregarComponentesAlMensajePanelVictoriaPorObjetivos(JPanel p) {
	    GridBagConstraints gbc = new GridBagConstraints();

	    JLabel labelGanaste1 = crearLabel("GANASTE", 50);
	    agregarConGBCs(labelGanaste1, p, gbc, 0, 0, 1, 1);

	    JLabel labelGanaste2 = crearLabel("Felicidades! Avanza al siguiente nivel", 30);
	    agregarConGBCs(labelGanaste2, p, gbc, 0, 1, 1, 1);

	    JButton botonSiguienteNivel = crearBoton("Siguiente Nivel");
	    agregarConGBCs(botonSiguienteNivel, p, gbc, 0, 3, 1, 1);

	    botonSiguienteNivel.addActionListener(new ActionListener() {
	        @Override
	        public void actionPerformed(ActionEvent e) {
	            mainPanel.setVisible(true);
	            p.setVisible(false);
	            milogica.cambiarNivel();
	        }
	    });
	}

	private JButton crearBoton(String texto) {
	    JButton boton = new JButton(texto);
	    boton.setBackground(new Color(0, 0, 0, 200));
	    boton.setForeground(Color.WHITE);
	    return boton;
	}

	public void mostrarMensajeDerrotaPorTiempo() {
	    repaint();
	    mainPanel.setVisible(false);

	    JPanel mensajePanel = crearMensajePanel();
	    agregarComponentesAlMensajePanelDerrotaPorTiempo(mensajePanel);

	    getContentPane().add(mensajePanel);

	    mensajePanel.setVisible(true);
	    mensajePanel.revalidate();
	}

	private void agregarComponentesAlMensajePanelDerrotaPorTiempo(JPanel p) {
	    GridBagConstraints gbc = new GridBagConstraints();

	    JLabel labelPerdiste1 = crearLabel("PERDISTE", 50);
	    agregarConGBCs(labelPerdiste1, p, gbc, 0, 0, 1, 1);

	    JLabel labelPerdiste2 = crearLabel("Te quedaste sin tiempo", 30);
	    agregarConGBCs(labelPerdiste2, p, gbc, 0, 1, 1, 1);

	    JLabel labelPerdiste3 = crearLabel("Pierdes una vida", 30);
	    agregarConGBCs(labelPerdiste3, p, gbc, 0, 2, 1, 1);

	    JButton botonReiniciar = crearBoton("Reintentar");
	    agregarConGBCs(botonReiniciar, p, gbc, 0, 3, 1, 1);

	    botonReiniciar.addActionListener(new ActionListener() {
	        @Override
	        public void actionPerformed(ActionEvent e) {
	            mainPanel.setVisible(true);
	            p.setVisible(false);
	            milogica.notificarDerrotaPorTiempo();
	        }
	    });
	}
	
	public void mostrarMensajeJuegoPerdido() {
		System.out.println("GUI :: Perdiste");
		
	}
	
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
	    panel_principal.removeAll();
	    panel_principal.revalidate();
	    panel_principal.repaint();
	}
	
	public boolean getBloquear_intercambios() {
		return bloquear_intercambios;
	}

	private void agregarConGBCs(Component componenteAAgregar, JPanel panelBase, GridBagConstraints gbc, int gridx, int gridy, int gridwidth,int gridheight) {
		gbc.gridx = gridx;
		gbc.gridy = gridy;
		gbc.gridwidth = gridwidth;
		gbc.gridheight = gridheight;

		panelBase.add(componenteAAgregar,gbc);
	}
	
	//Clase auxiliar para poder hacer el fondo

	private class Imagenfondo extends JPanel{
		
        /**
		 * 
		 */
		private static final long serialVersionUID = 4180857344385898634L;
		
		private Image imagen;

        public void paint(Graphics g) {
            imagen = new ImageIcon(getClass().getResource("/assets/nivel/3_Nivel.png")).getImage();
            g.drawImage(imagen, 0, 0, getWidth(), getHeight(), this);
            super.paint(g);
        }
    }


	
}

