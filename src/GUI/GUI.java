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
	
	protected JPanel panel_tablero,panel_principal, panel_movimientos, panel_timer;
	protected JLabel timerLabel,movimientosLabel, scoreLabel;
	protected JLabel[] objetivosProgreso;

	private Imagenfondo fondo = new Imagenfondo();

	
	
	// Define un mapa para asociar los códigos de tecla con las acciones
	Map<Integer, Runnable> acciones = new HashMap<>();
	
	
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
		setearJFrame(screenSize);
		panel_principal.setSize(screenSize);
		
		panel_principal.setLayout(new GridBagLayout());

		panel_timer = new JPanel();
		panel_timer.setBackground(new Color(0,0,0,200));

		timerLabel = new JLabel("Tiempo restante: "+ agregarPaddingTiempo(tiempoRestante));
		timerLabel.setFont(new Font("Algerian", Font.PLAIN, 20));
		timerLabel.setForeground(Color.WHITE);

		panel_timer.add(timerLabel);

		inicializarPanels();

		panel_tablero = new JPanel();
		panel_tablero.setSize(size_label * filas, size_label * columnas);
		panel_tablero.setLayout(new GridBagLayout());
		panel_tablero.setBackground(new Color(0,0,0,255));

		configurarAccionesTeclado();

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
		 	
		
		
		panel_tablero.setFocusable(true);

		getContentPane().add(panel_principal);
	}

	private void setearJFrame(Dimension screenSize) {
		setTitle("Proyecto Candy Crush - Comision-06");
		setSize(screenSize);
		setLayout(new BorderLayout());
		setExtendedState(JFrame.MAXIMIZED_BOTH); 
		setLocationRelativeTo(null);
		setUndecorated(false); 
		setVisible(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setExtendedState(JFrame.MAXIMIZED_BOTH);
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

	public void inicializarPanels() {
		mi_central_paneles.mostrarPanelScore();
		mi_central_paneles.mostrarPanelObjetivo();
		mi_central_paneles.mostrarPanelControles();
		mi_central_paneles.mostrarPanelVidas();
		mi_central_paneles.mostrarPanelMovimiento();
		mi_central_paneles.mostrarPanelReglas();
	}

	public int getVidas() {
		return miLogica.getVidas();
	}

	private static void moverComponentes(Container container, Component component, int gridx, int gridy) {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = gridx;
        gbc.gridy = gridy;

        container.remove(component);
        container.add(component, gbc);

        container.revalidate();
        container.repaint();
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
		moverComponentes(panel_tablero, celda, celda.get_entidad_logica().get_columna(), celda.get_entidad_logica().get_fila());
	}
	
	public void animar_cambio_foco(Celda celda) {
		mi_animador.animar_cambio_foco(celda);
	}
	
	public void animar_detonacion(Celda celda) {
		mi_animador.animar_detonacion(celda);
	}
	
	public void animar_caida(Celda celda) {
		mi_animador.animar_caida(celda);
		moverComponentes(panel_tablero, celda, celda.get_entidad_logica().get_columna(), celda.get_entidad_logica().get_fila());
	}
	
	public void animar_cambio_visibilidad(Celda celda) {
		mi_animador.animar_cambio_visibilidad(celda);
	}

	public void actualizarMovimientos(int movimientos) {
		mi_central_paneles.actualizarMovimientos(movimientos);
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

	public void actualizarVidas() {
		mi_central_paneles.actualizarVidas();
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

	public int getMovimientos() {
		return miLogica.getMovimientos();
	}
	
	public void limpiarGUI() {
	    tiempoRestante = miLogica.getTiempo();
	    movimientosRestantes = getMovimientos();
	    animaciones_pendientes = 0;
	    bloquear_intercambios = false;

	    limpiarMatrizGUI();
	    mi_central_paneles.actualizarVidas();

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

    public CentralPaneles obtenerCentralPaneles()
	{
		return mi_central_paneles;
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
		panel_principal = new JPanel();
	    animaciones_pendientes = 0;
	    bloquear_intercambios = false;
		miLogica = logica;
		this.filas = filas;
		this.columnas = columnas;
		
	    limpiarMatrizGUI();
	    mi_central_paneles.mostrarPanelVidas();
		mi_central_paneles.mostrarPanelScore();
		mi_central_paneles.mostrarPanelObjetivo();
		mi_central_paneles.mostrarPanelControles();

	    timerLabel.setText("Tiempo restante: " + tiempoRestante);
	    movimientosLabel.setText("Movimientos restantes: " + movimientosRestantes);
	}

	
}