package GUI;


import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.GridBagConstraints;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.Image;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import Logica.EntidadLogica;
import Logica.Logica;
import Paneles.CentralPaneles;
import Pantallas.CentralPantallas;
import Score.Jugador;
import Threads.CentralAnimaciones;



public class GUI extends JFrame implements VentanaAnimable, VentanaNotificable,VentanaJuego{
	//Atributos
	protected Logica miLogica;
	
	protected int filas,columnas;
	protected int tiempoRestante;
	protected int animaciones_pendientes;
	protected int movimientosRestantes;
	private int size_label = 70;
	private CentralPantallas mi_central_pantallas;
	protected boolean bloquear_intercambios;
	protected CentralPaneles mi_central_paneles;
	protected CentralAnimaciones mi_animador;
	
	protected JPanel panel_tablero, panel_controles, panel_vidas,panel_principal, panel_movimientos, panel_timer;
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
		
		inicializarGUI();
	}
	 
	private void inicializarGUI() {
		this.setContentPane(fondo);
		
		ImageIcon logo = new ImageIcon(this.getClass().getResource("/assets/nivel/Icono.png"));
		setIconImage(logo.getImage());

		panel_principal = new JPanel();
		panel_principal.setBackground(new Color(0,0,0,0));

		
		mi_central_pantallas = new CentralPantallas(panel_principal, this, miLogica);
		mi_central_paneles = new CentralPaneles(panel_principal, this);

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

		mi_central_paneles.mostrarPanelScore();
		mi_central_paneles.mostrarPanelObjetivo();

		panel_movimientos = new JPanel();
		panel_movimientos.setBackground(new Color(0,0,0,200));

		movimientosLabel = new JLabel ("Movimientos restantes: "+movimientosRestantes);
		movimientosLabel.setFont(new Font("Algerian", Font.PLAIN, 20));
		movimientosLabel.setForeground(Color.WHITE);

		panel_movimientos.add(movimientosLabel);
		

		panel_tablero = new JPanel();
		panel_tablero.setSize(size_label * filas, size_label * columnas);
		panel_tablero.setLayout(new GridBagLayout());
		panel_tablero.setBackground(new Color(0,0,0,125));




		configurarAccionesTeclado();
		mostrarVidas();

		//Constraints TIMER
		GridBagConstraints c = new GridBagConstraints();
		c.insets = new Insets(0,10,0,0);
		agregarConGBCs(panel_timer, panel_principal, c, 0, 0, 3, 1);
		

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


//------------------------- BOTONES PARA TESTING, BORRAR ANTES DE ENTREGA FINAL -------------------------
		
		acciones.put(KeyEvent.VK_M, () -> {
		    mostrarMensajeFinDelJuego();
		});

		acciones.put(KeyEvent.VK_Y, () -> {
		    mostrarMensajeDerrotaPorMovimientos();
		});

		acciones.put(KeyEvent.VK_U, () -> {
		    mostrarMensajeDerrotaPorMovimientos();
		});

		acciones.put(KeyEvent.VK_O, () -> {
		    if (!bloquear_intercambios){
		    	animar_detonacion((Celda) panel_tablero.getComponent(1));
		    }
		});

		acciones.put(KeyEvent.VK_L, () -> {
		    if (!bloquear_intercambios){
		    	mostrarMensajeVictoriaPorObjetivos();
		    }
		});

		acciones.put(KeyEvent.VK_P, () -> {
		    if (!bloquear_intercambios){
		    	obtenerNombreJugador();
		    }
		});

		acciones.put(KeyEvent.VK_I, () -> {
		    if (!bloquear_intercambios){
		    	mostrarModosDeJuego();
		    }
		});

		acciones.put(KeyEvent.VK_K, () -> {
		    if (!bloquear_intercambios){
		    mostrarMensajeDerrotaPorVidas();
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

		
        JOptionPane.showConfirmDialog(null, panelNombre, "¡Top 5 alcanzado!", JOptionPane.OK_CANCEL_OPTION);

        return textFieldNombre.getText();
	}


	public EntidadGrafica agregar_entidad(EntidadLogica e) {
		Celda celda = new Celda(this, e, size_label, true);
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.gridx = e.get_columna();
		gbc.gridy = e.get_fila();
		panel_tablero.add(celda, gbc);
		return celda;
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

	private void agregarConGBCs(Component comp, Container cont, int x, int y, int width, int height) {
	    GridBagConstraints c = new GridBagConstraints();
	    c.gridx = x;
	    c.gridy = y;
	    c.gridwidth = width;
	    c.gridheight = height;
	    cont.add(comp, c);
	}
	
	
	
	
	public int getTiempoRestante() {
		return tiempoRestante;
	}

	public void actualizarTiempo(int tiempo) {	
	    timerLabel.setText("Tiempo restante: " + agregarPaddingTiempo(tiempo));
		tiempoRestante = tiempo;
		panel_timer.repaint();
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
	    mi_central_pantallas.mostrarMensajeDerrotaPorMovimientos();
	}

	public void mostrarMensajeDerrotaPorVidas() {
	    mi_central_pantallas.mostrarMensajeDerrotaPorVidas();
	}
	
	public void mostrarMensajeFinDelJuego() {
	    mi_central_pantallas.mostrarMensajeFinDelJuego();
	}

	public void mostrarMensajeVictoriaPorObjetivos() {
	    mi_central_pantallas.mostrarMensajeVictoriaPorObjetivos();
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

	public int getCantidadDeObjetivos() {
		return miLogica.getCantidadDeObjetivos();
	}

	public void mostrarPuntajes() {
		mi_central_pantallas.mostrarPuntajes();
	}

	public void actualizarProgreso(int gemasRestantes, int tipoGema) {
		mi_central_paneles.actualizarProgreso(gemasRestantes, tipoGema);
	}

	public void reiniciarProgreso() {
		mi_central_paneles.reiniciarProgresoObjetivos();
	}

	private void mostrarModosDeJuego() {
		mi_central_pantallas.mostrarModosDeJuego();
	}

	public void mostrarMensajeDerrotaPorTiempo() {
	    mi_central_pantallas.mostrarMensajeDerrotaPorTiempo();
	}

	public String[] obtenerInfoObjetivos() {
	    return miLogica.obtenerInfoObjetivos();
	}

	public JLabel crearLabel(String texto, int tamañoFuente) {
	    JLabel label = new JLabel(texto);
	    label.setFont(new Font("Algerian", Font.PLAIN, tamañoFuente));
	    label.setForeground(Color.WHITE);
	    return label;
	}

	public JLabel crearLabel(String texto, String fuente, int estilo, int tamano, Color color, int gridwidth, int gridheight) {
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
	
	public void eliminar_celda(Celda celda) {
		panel_principal.remove(celda);
		panel_principal.repaint();
	}

	public void mostrarMensajeJuegoPerdido() {
		System.out.println("GUI :: Perdiste");
		
	}

	public PriorityQueue<Jugador> obtenerListadeJugadores() {
		return miLogica.obtenerListadeJugadores();
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

