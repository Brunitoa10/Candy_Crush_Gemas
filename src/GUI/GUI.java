package GUI;


import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
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
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;


import java.awt.GridBagLayout;
import java.awt.GridLayout;
import Logica.EntidadLogica;
import Logica.Logica;
import Threads.CentralAnimaciones;



public class GUI extends JFrame implements VentanaAnimable, VentanaNotificable,VentanaJuego{
	//Atributos
	protected Logica miLogica;
	
	protected int filas,columnas;
	protected int tiempoRestante;
	protected int animaciones_pendientes;
	protected int[] objetivosColores;
	protected int movimientosRestantes;
	private int size_label = 70;
	
	protected boolean bloquear_intercambios;
	
	protected CentralAnimaciones mi_animador;
	
	protected JPanel panel_tablero, panel_score, panel_objetivos, panel_controles, panel_vidas,panel_principal, panel_movimientos, panel_timer;
	protected JLabel timerLabel,movimientosLabel, scoreLabel;
	protected JLabel[] objetivosProgreso;

	private Imagenfondo fondo = new Imagenfondo();

	
	
	// Define un mapa para asociar los códigos de tecla con las acciones
	Map<Integer, Runnable> acciones = new HashMap<>();
	
	
	//Movimientos
	public static final int ARRIBA = 15000;
	public static final int ABAJO = 15001;
	public static final int IZQUIERDA = 15002;
	public static final int DERECHA = 15003;
	
	
	public GUI(Logica l, int f, int c) {
		miLogica = l;
		filas = f;
		columnas = c;
		tiempoRestante = miLogica.getTiempo();
		movimientosRestantes = miLogica.getMovimientos();
		animaciones_pendientes = 0;
		bloquear_intercambios = false;
		mi_animador = new CentralAnimaciones(this);
		objetivosColores = new int[miLogica.getCantidadDeObjetivos()];
		objetivosProgreso = new JLabel[miLogica.getCantidadDeObjetivos()];
		
		inicializarGUI();
	}
	

	 
	private void inicializarGUI() {
		this.setContentPane(fondo);
		
		ImageIcon logo = new ImageIcon(this.getClass().getResource("/assets/nivel/Icono.png"));
		setIconImage(logo.getImage());

		panel_principal = new JPanel();
		panel_principal.setBackground(new Color(0,0,0,0));
		

		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		setTitle("Proyecto Candy Crush - Comision-06");
		panel_principal.setSize(screenSize);
		setSize(screenSize);
		setLayout(new BorderLayout());
		setExtendedState(JFrame.MAXIMIZED_BOTH); 
		setLocationRelativeTo(null);
		setUndecorated(false); 
		setVisible(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setExtendedState(JFrame.MAXIMIZED_BOTH);
		panel_principal.setLayout(new GridBagLayout());

		panel_timer = new JPanel();
		panel_timer.setBackground(new Color(0,0,0,200));

		timerLabel = new JLabel("Tiempo restante: "+ agregarPaddingTiempo(tiempoRestante));
		timerLabel.setFont(new Font("Algerian", Font.PLAIN, 20));
		timerLabel.setForeground(Color.WHITE);

		panel_timer.add(timerLabel);

		inicializarPanelControles();

		panel_score = new JPanel();
		panel_score.setBackground(new Color(0,0,0,200));

		scoreLabel = new JLabel("SCORE: 0000");
		scoreLabel.setFont(new Font("Algerian", Font.PLAIN, 40));
		scoreLabel.setForeground(Color.WHITE);
		panel_score.add(scoreLabel);

		panel_movimientos = new JPanel();
		panel_movimientos.setBackground(new Color(0,0,0,200));

		movimientosLabel = new JLabel ("Movimientos restantes: "+movimientosRestantes);
		movimientosLabel.setFont(new Font("Algerian", Font.PLAIN, 20));
		movimientosLabel.setForeground(Color.WHITE);

		panel_movimientos.add(movimientosLabel);
		

		panel_tablero = new JPanel();
		panel_tablero.setSize(size_label * filas, size_label * columnas);
		panel_tablero.setLayout(new GridLayout(filas,columnas,0,0));
		panel_tablero.setBackground(new Color(0,0,0,125));


		panel_objetivos = new JPanel();
		panel_objetivos.setSize(100,100);
		panel_objetivos.setLayout(new GridBagLayout());
		panel_objetivos.setBackground(new Color(0,0,0,200));


		configurarAccionesTeclado();
		mostrarObjetivos();
		mostrarVidas();

		//Constraints TIMER
		GridBagConstraints c = new GridBagConstraints();
		c.insets = new Insets(0,10,0,0);
		agregarConGBCs(panel_timer, panel_principal, c, 0, 0, 3, 1);
	
		//Constraints PANEL OBJETIVOS
		c.insets = new Insets(0, 10, 0, 0);   
		agregarConGBCs(panel_objetivos, panel_principal, c, 0, 1, 2, 2);   
		
		//Constraints PANEL SCORE
		agregarConGBCs(panel_score, panel_principal, 2, 0, 4, 1);

		//Constraints TABLERO
		c.insets = new Insets(0,0,0,0);
		c.weightx = 1;
		c.weighty = 1;
		c.anchor = GridBagConstraints.CENTER;
		agregarConGBCs(panel_tablero, panel_principal, c, 2, 1, 4, 4); 
		
		//Constraints VIDAS
		c.weightx = 0;
		c.weightx = 0;
		c.insets = new Insets(0,0,0,10);
		agregarConGBCs(panel_vidas, panel_principal, c, 6, 6, 2, 1); 
		 
		//Contraints CONTROLES
		agregarConGBCs(panel_controles, panel_principal, 0, 6, 5, 1);
		
		//Constraints MOVIMIENTOS
		c.weightx = 0;
		c.weightx = 0;
		c.insets = new Insets(0,0,0,10);
		agregarConGBCs(panel_movimientos, panel_principal, c, 6, 0, 2, 1); 
		
		panel_tablero.setFocusable(true);
		getContentPane().add(panel_principal);
	}

	private void configurarAccionesTeclado() {
		//Acciones del cursor
		acciones.put(KeyEvent.VK_LEFT, () ->{
			miLogica.mover_jugador(IZQUIERDA);
		});
		acciones.put(KeyEvent.VK_RIGHT, () -> {
			miLogica.mover_jugador(DERECHA);
		});
		acciones.put(KeyEvent.VK_UP, () -> {
			miLogica.mover_jugador(ARRIBA);
		});
		acciones.put(KeyEvent.VK_DOWN, () ->{
			miLogica.mover_jugador(ABAJO);
		});
		
		//Acciones de intercambio
		acciones.put(KeyEvent.VK_W, () -> {
		    if (!bloquear_intercambios) {
		    	miLogica.intercambiar(ARRIBA);
		    }
		});
		
		acciones.put(KeyEvent.VK_S, () -> {
		    if (!bloquear_intercambios){
		    	miLogica.intercambiar(ABAJO);
		    }
		});
		
		acciones.put(KeyEvent.VK_A, () -> {
		    if (!bloquear_intercambios){
		    	miLogica.intercambiar(IZQUIERDA);
		    }
		});
		
		acciones.put(KeyEvent.VK_D, () -> {
		    if (!bloquear_intercambios){
		    	miLogica.intercambiar(DERECHA);
		    }
		});

		acciones.put(KeyEvent.VK_D, () -> {
		    if (!bloquear_intercambios){
		    	miLogica.intercambiar(DERECHA);
		    }
		});

		acciones.put(KeyEvent.VK_R, () -> {
		    if (!bloquear_intercambios){
		    	mostrarPuntajes();
		    }
		});

		acciones.put(KeyEvent.VK_P, () -> {
		    if (!bloquear_intercambios){
		    	obtenerNombreJugador();
		    }
		});

		// Agrega el KeyListener
		panel_tablero.addKeyListener(new KeyAdapter() {
		    @Override
		    public void keyPressed(KeyEvent e) {
		        // Verifica si la tecla presionada tiene una acción asociada
		        Runnable accion = acciones.get(e.getKeyCode());
		        if (accion != null) {
		        	repaint();
		        	accion.run();
		        }
		    }
		});
		
	}

	public String obtenerNombreJugador() {
		String nombre = abrirVentanaParaIngresarNombre();

		while (nombre != null && nombre.length() != 3) {
            JOptionPane.showMessageDialog(null, "El nombre tener 3 letras");
            nombre = abrirVentanaParaIngresarNombre();
        }

		System.out.println(nombre);
		return nombre;
	}


    public String abrirVentanaParaIngresarNombre() {
        JPanel panelNombre = new JPanel();
        JLabel labelNombre = new JLabel("Ingresa tu nombre (3 letras):");
        JTextField textFieldNombre = new JTextField(5);

        panelNombre.add(labelNombre);
        panelNombre.add(textFieldNombre);

        int result = JOptionPane.showConfirmDialog(null, panelNombre, "¡Top 5 alcanzado!", JOptionPane.OK_CANCEL_OPTION);

        return textFieldNombre.getText();
	}


	public EntidadGrafica agregar_entidad(EntidadLogica e) {
		Celda celda = new Celda(this, e, size_label, true);
		panel_tablero.add(celda);
		return celda;
	}

	public void mostrarPuntajes() {
		this.repaint();
	    panel_principal.setVisible(false);

	    JPanel panel_puntajes = crearMensajePanel();
		panel_puntajes.setLayout(new GridBagLayout());
	    
		setearPanelPuntajes(panel_puntajes);
	}

	private void agregarAccionBotonVolver(JButton boton, JPanel panel_principal, JPanel panel_puntajes) {
	    boton.addActionListener(new ActionListener() {
	        @Override
	        public void actionPerformed(ActionEvent e) {
	            panel_puntajes.setVisible(false);
				panel_principal.setVisible(true);
				panel_tablero.requestFocus();
				panel_tablero.setFocusable(true);
				repaint();
	        }
	    });

	    getContentPane().add(panel_puntajes);
	    panel_puntajes.setVisible(true);
	    panel_puntajes.revalidate();
	}

	private JLabel label_corazon1 = new JLabel();
	private JLabel label_corazon2 = new JLabel();
	private JLabel label_corazon3 = new JLabel();

	public void mostrarVidas() {
		panel_vidas = new JPanel();
		panel_vidas.setLayout(new GridLayout(1,3,5,5));
		panel_vidas.setBackground(new Color(0,0,0,0));
		
		
		ImageIcon imgIconCorazon = new ImageIcon(this.getClass().getResource("/assets/nivel/corazon.png"));
		Image imgEscaladaCorazon = imgIconCorazon.getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH);
		Icon iconoEscaladoCorazon = new ImageIcon(imgEscaladaCorazon);
		
		label_corazon1.setIcon(iconoEscaladoCorazon);
		label_corazon3.setIcon(iconoEscaladoCorazon);
		label_corazon2.setIcon(iconoEscaladoCorazon);


		panel_vidas.add(label_corazon1);
		panel_vidas.add(label_corazon2);
		panel_vidas.add(label_corazon3);
	}

	public void actualizarVidas() {
		ImageIcon imgIconCorazonVacio = new ImageIcon(this.getClass().getResource("/assets/nivel/corazonVacio.png"));
		Image imgEscaladaCorazonVacio = imgIconCorazonVacio.getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH);
		Icon iconoEscaladoCorazonVacio = new ImageIcon(imgEscaladaCorazonVacio);
		

		if(miLogica.getVidas() == 2) {
			label_corazon3.setIcon(iconoEscaladoCorazonVacio);
		} else if(miLogica.getVidas() == 1) {
				label_corazon2.setIcon(iconoEscaladoCorazonVacio);
				label_corazon3.setIcon(iconoEscaladoCorazonVacio);
			}

		GridBagConstraints gbc = new GridBagConstraints();
		gbc.weightx = 0;
		gbc.weightx = 0;
		gbc.insets = new Insets(0,0,0,10);
		agregarConGBCs(panel_vidas, panel_principal, gbc, 6, 6, 2, 1); 
	}
	
	public void mostrarObjetivos() {   
	    JLabel tituloObjetivo = crearLabel(" OBJETIVOS:", "Algerian", Font.PLAIN, 20, Color.WHITE, 2, 1);
	    GridBagConstraints cTitulo = new GridBagConstraints();
	    cTitulo.insets = new Insets(0, 0, 0, 0);      
	    cTitulo.gridx = 0;                               
	    cTitulo.gridy = 0;
	    panel_objetivos.add(tituloObjetivo, cTitulo);

	    int coordenada_y = 1;
	    int numeroDeObjetivo = 0;

	    for(int i = 0; i < miLogica.obtenerInfoObjetivos().length; i += 4) {
	        int color = Integer.parseInt(miLogica.obtenerInfoObjetivos()[i + 3]);
	        objetivosColores[numeroDeObjetivo] = color;

	        JLabel objetivosTexto = crearLabel(miLogica.obtenerInfoObjetivos()[i], "Algerian", Font.PLAIN, 15, Color.WHITE, 1, 1);
	        JLabel objetivosImagen = crearImagen(miLogica.obtenerInfoObjetivos()[i + 1]);
	        JLabel objetivosNumero = crearLabel("0/" + miLogica.obtenerInfoObjetivos()[i + 2], "Arial", Font.PLAIN, 15, Color.WHITE, 2, 1);

	        agregarConGBCs(objetivosTexto, panel_objetivos, 0, coordenada_y, 1, 1);
	        agregarConGBCs(objetivosImagen, panel_objetivos, 1, coordenada_y, 1, 1);
	        agregarConGBCs(objetivosNumero, panel_objetivos, 0, coordenada_y + 1, 2, 1);

	        objetivosProgreso[numeroDeObjetivo] = objetivosNumero;

	        coordenada_y += 2;
	        numeroDeObjetivo++;
	    }
	}

	private JLabel crearLabel(String texto, String fuente, int estilo, int tamano, Color color, int gridwidth, int gridheight) {
	    JLabel label = new JLabel(texto);
	    label.setFont(new Font(fuente, estilo, tamano));
	    label.setForeground(color);

	    GridBagConstraints c = new GridBagConstraints();
	    c.insets = new Insets(0, 20, 0, 0);
	    c.weightx = 0;
	    c.gridwidth = gridwidth;
	    c.gridheight = gridheight;

	    return label;
	}

	private JLabel crearImagen(String ruta) {
	    ImageIcon imgIcon = new ImageIcon(this.getClass().getResource(ruta));
	    Image imgEscalada = imgIcon.getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH);
	    Icon iconoEscalado = new ImageIcon(imgEscalada);

	    JLabel label = new JLabel();
	    label.setIcon(iconoEscalado);

	    return label;
	}

	private void agregarConGBCs(Component comp, Container cont, int x, int y, int width, int height) {
	    GridBagConstraints c = new GridBagConstraints();
	    c.gridx = x;
	    c.gridy = y;
	    c.gridwidth = width;
	    c.gridheight = height;
	    cont.add(comp, c);
	}
	
	public void actualizarProgreso(int gemasRestantes, int tipoGema) {
		for(int i = 0; i < objetivosColores.length; i++) {
	        if(tipoGema == objetivosColores[i] && objetivosProgreso[i] != null) {
	            String aux = objetivosProgreso[i].getText();
	            String[] partes = aux.split("/");
	            Integer num = Integer.valueOf(partes[1]);
	            int gemasTotales = num.intValue();
	            int progreso = gemasTotales - gemasRestantes;

	            objetivosProgreso[i].setText(progreso + "/" + gemasTotales);
	            panel_objetivos.repaint();
	        }
	    }
	}
	
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
			tiempoRestante = tiempo;
			panel_timer.repaint();
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
	
	public void notificarse_animacion_finalizada() {
		synchronized(this){
			animaciones_pendientes --;
			bloquear_intercambios = animaciones_pendientes > 0;
		}
	}
	
	public void animar_intercambio(Celda celda) {
		mi_animador.animar_intercambio(celda);
	}
	
	public void animar_cambio_foco(Celda celda) {
		mi_animador.animar_cambio_foco(celda);
	}
	
	public void animar_detonacion(Celda celda) {
		mi_animador.animar_detonacion(celda);
	}
	
	public void animar_caida(Celda celda) {
		mi_animador.animar_caida(celda);
	}
	
	public void animar_cambio_visibilidad(Celda celda) {
		mi_animador.animar_cambio_visibilidad(celda);
	}

	public void actualizarMovimientos(int movimientos) {
		movimientosLabel.setText("Movimientos restantes: "+movimientos);
	}

	public void mostrarMensajeDerrotaPorMovimientos() {
	    this.repaint();
	    panel_principal.setVisible(false);

	    JPanel mensajePanel = crearMensajePanel();
	    agregarComponentesAlMensajePanel(mensajePanel);

	    getContentPane().add(mensajePanel);

	    JButton botonReiniciar = crearBotonReintentar();
	    agregarAccionBotonReiniciar(botonReiniciar, panel_principal, mensajePanel);

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

	private void agregarAccionBotonReiniciar(JButton boton, JPanel panel_principal, JPanel mensajePanel) {
	    boton.addActionListener(new ActionListener() {
	        @Override
	        public void actionPerformed(ActionEvent e) {
	            panel_principal.setVisible(true);
	            mensajePanel.setVisible(false);
	        }
	    });
	}
	
	public void mostrarMensajeDerrotaPorVidas() {
	    panel_principal.setVisible(false);

	    JPanel mensajePanel = crearMensajePanel();
	    agregarComponentesAlMensajePanelPorVidas(mensajePanel);

	    getContentPane().add(mensajePanel);

	    JButton botonReiniciarJuego = crearBotonReintentar();
	    agregarAccionBotonReiniciarPorVidas(botonReiniciarJuego, panel_principal, mensajePanel);

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


	private void agregarAccionBotonReiniciarPorVidas(JButton boton, JPanel panel_principal, JPanel mensajePanel) {
	    boton.addActionListener(new ActionListener() {
	        @Override
	        public void actionPerformed(ActionEvent e) {
	            panel_principal.setVisible(true);
	            mensajePanel.setVisible(false);
	            miLogica.notificarDerrotaPorVidas();
	        }
	    });
	}
	
	public void mostrarMensajeFinDelJuego() {
	    panel_principal.setVisible(false);

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
	    panel_principal.setVisible(false);

	    JPanel mensajePanel = crearMensajePanel();
	    agregarComponentesAlMensajePanelVictoriaPorObjetivos(mensajePanel);

	    getContentPane().add(mensajePanel);

	    mensajePanel.setVisible(true);
	    mensajePanel.revalidate();
	}

	private void inicializarPanelControles() {
		panel_controles = new JPanel();
		panel_controles.setOpaque(false);
		
		ImageIcon icono_imagen = new ImageIcon(this.getClass().getResource("/assets/controles/arrow_keys.png"));
		Image imagen_escalada = icono_imagen.getImage().getScaledInstance(120, 30, Image.SCALE_REPLICATE);
		Icon icono_escalado = new ImageIcon(imagen_escalada);

		JLabel label_flechas = new JLabel();

		label_flechas.setIcon(icono_escalado);
		panel_controles.add(label_flechas);
		panel_controles.repaint();

		JLabel textoMoverse = crearLabel("Mover", 25);
		panel_controles.add(textoMoverse);
		
		icono_imagen = new ImageIcon(this.getClass().getResource("/assets/controles/WASD.png"));
		imagen_escalada = icono_imagen.getImage().getScaledInstance(120, 30, Image.SCALE_REPLICATE);
		icono_escalado = new ImageIcon(imagen_escalada);

		JLabel label_WASD = new JLabel();

		label_WASD.setIcon(icono_escalado);
		panel_controles.add(label_WASD);
		panel_controles.repaint();

		JLabel textoIntercambiar =  crearLabel("Intercambiar", 25);
		panel_controles.add(textoIntercambiar);

		icono_imagen = new ImageIcon(this.getClass().getResource("/assets/controles/R.png"));
		imagen_escalada = icono_imagen.getImage().getScaledInstance(30, 30, Image.SCALE_REPLICATE);
		icono_escalado = new ImageIcon(imagen_escalada);

		JLabel label_R = new JLabel();

		label_R.setIcon(icono_escalado);
		panel_controles.add(label_R);
		panel_controles.repaint();

		JLabel textoVerPuntajes = crearLabel("Ver mejores puntajes", 25);
		panel_controles.add(textoVerPuntajes);

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
	            panel_principal.setVisible(true);
	            p.setVisible(false);
	            miLogica.cambiarNivel();
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
	    panel_principal.setVisible(false);

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
	            panel_principal.setVisible(true);
	            p.setVisible(false);
	            miLogica.notificarDerrotaPorTiempo();
	        }
	    });
	}
	
	public void eliminar_celda(Celda celda) {
		panel_principal.remove(celda);
		panel_principal.repaint();
	}

	public void mostrarMensajeJuegoPerdido() {
		System.out.println("GUI :: Perdiste");
		
	}
	
	public void limpiarGUI() {
	    tiempoRestante = miLogica.getTiempo();
	    movimientosRestantes = miLogica.getMovimientos();
	    animaciones_pendientes = 0;
	    bloquear_intercambios = false;

	    limpiarMatrizGUI();
	    mostrarVidas();

	    timerLabel.setText("Tiempo restante: " + tiempoRestante);
	    movimientosLabel.setText("Movimientos restantes: " + movimientosRestantes);
	}

	private void limpiarMatrizGUI() {
	    panel_tablero.removeAll();
	    panel_tablero.revalidate();
	    panel_tablero.repaint();
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

	public void cambiarScore(int score) {
		scoreLabel.setText("SCORE: "+agregarPaddingScore(score) );
	}

	private String agregarPaddingScore(int score) {
		String padding ="";
		if(score <= 9) {
			padding = "000";
			} else if(score <= 99) {
				padding = "00";
			}	else if(score <= 999) {
				padding = "0";
			}

		return padding + score;
	}

	private void setearPanelPuntajes(JPanel panel_puntajes) {
	JLabel titulo_puntajes = crearLabel("MEJORES PUNTAJES:", "Algerian", Font.PLAIN, 40, Color.WHITE, 2, 1);
		agregarConGBCs(titulo_puntajes, panel_puntajes, 0, 0, 3, 1);

		JLabel label_num1 = crearLabel("1 - ", "Algerian", Font.PLAIN, 30, Color.YELLOW, 2, 1);
		agregarConGBCs(label_num1, panel_puntajes, 0, 1, 1, 1);

		JLabel label_nombre1 = crearLabel("AAA", "Algerian", Font.PLAIN, 30, Color.YELLOW, 2, 1);
		agregarConGBCs(label_nombre1, panel_puntajes, 1, 1, 1, 1);

		JLabel label_puntaje1 = crearLabel("0000000", "Algerian", Font.PLAIN, 30, Color.WHITE, 2, 1);
		agregarConGBCs(label_puntaje1, panel_puntajes, 2, 1, 1, 1);

		JLabel label_num2 = crearLabel("2 - ", "Algerian", Font.PLAIN, 30, Color.GRAY, 2, 1);
		agregarConGBCs(label_num2, panel_puntajes, 0, 2, 1, 1);

		JLabel label_nombre2 = crearLabel("AAA", "Algerian", Font.PLAIN, 30, Color.GRAY, 2, 1);
		agregarConGBCs(label_nombre2, panel_puntajes, 1, 2, 1, 1);

		JLabel label_puntaje2 = crearLabel("0000000", "Algerian", Font.PLAIN, 30, Color.WHITE, 2, 1);
		agregarConGBCs(label_puntaje2, panel_puntajes, 2, 2, 1, 1);

		JLabel label_num3 = crearLabel("3 - ", "Algerian", Font.PLAIN, 30, Color.ORANGE, 2, 1);
		agregarConGBCs(label_num3, panel_puntajes, 0, 3, 1, 1);

		JLabel label_nombre3 = crearLabel("AAA", "Algerian", Font.PLAIN, 30, Color.ORANGE, 2, 1);
		agregarConGBCs(label_nombre3, panel_puntajes, 1, 3, 1, 1);

		JLabel label_puntaje3 = crearLabel("0000000", "Algerian", Font.PLAIN, 30, Color.WHITE, 2, 1);
		agregarConGBCs(label_puntaje3, panel_puntajes, 2, 3, 1, 1);

		JLabel label_num4 = crearLabel("4 - ", "Algerian", Font.PLAIN, 30, Color.WHITE, 2, 1);
		agregarConGBCs(label_num4, panel_puntajes, 0, 4, 1, 1);

		JLabel label_nombre4 = crearLabel("AAA", "Algerian", Font.PLAIN, 30, Color.white, 2, 1);
		agregarConGBCs(label_nombre4, panel_puntajes, 1, 4, 1, 1);

		JLabel label_puntaje4 = crearLabel("0000000", "Algerian", Font.PLAIN, 30, Color.WHITE, 2, 1);
		agregarConGBCs(label_puntaje4, panel_puntajes, 2, 4, 1, 1);

		JLabel label_num5 = crearLabel("5 - ", "Algerian", Font.PLAIN, 30, Color.WHITE, 2, 1);
		agregarConGBCs(label_num5, panel_puntajes, 0, 5, 1, 1);

		JLabel label_nombre5 = crearLabel("AAA", "Algerian", Font.PLAIN, 30, Color.white, 2, 1);
		agregarConGBCs(label_nombre5, panel_puntajes, 1, 5, 1, 1);

		JLabel label_puntaje5 = crearLabel("0000000", "Algerian", Font.PLAIN, 30, Color.WHITE, 2, 1);
		agregarConGBCs(label_puntaje5, panel_puntajes, 2, 5, 1, 1);

		JButton button_volver = new JButton("Volver");
		button_volver.setBackground(new Color(0, 0, 0, 200));
	    button_volver.setForeground(Color.WHITE);
		agregarAccionBotonVolver(button_volver, panel_principal, panel_puntajes);
		agregarConGBCs(button_volver, panel_puntajes, 0, 6, 2, 1);
	}

	public void cambiarFondo(int nivel) {
        fondo.cambiarFondo(nivel);
        repaint(); // Vuelve a pintar la GUI para reflejar el cambio de fondo
    }
	//Clase auxiliar para poder hacer el fondo

	private class Imagenfondo extends JPanel{
		
        /**
		 * 
		 */
		private static final long serialVersionUID = 4180857344385898634L;
		
		private Image imagen;
		private int nivelActual = 1;
        
		public void paint(Graphics g) {
            imagen = new ImageIcon(getClass().getResource("/assets/nivel/"+nivelActual+"_Nivel.png")).getImage();
            g.drawImage(imagen, 0, 0, getWidth(), getHeight(), this);
            super.paint(g);
        }
        
        public void cambiarFondo(int nivel) {
            nivelActual = nivel;
            repaint(); // Vuelve a pintar el fondo con el nuevo nivel
        }

    }

	@Override
	public void ocultar() {
		this.setVisible(false);
	}

	@Override
	public void mostrar() {
		this.setVisible(true);
	}
	
	@Override
	public void transicionar() {
		// To DO. Podría servir para mostrar información o carteles.
	}
	
	@Override
	public void resetear(Logica logica, int filas, int columnas) {
		// To DO.
	}
}

