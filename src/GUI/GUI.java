package GUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.GridBagConstraints;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.PriorityQueue;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.Image;
import javax.swing.ImageIcon;
import java.awt.Dimension;
import java.awt.Graphics;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import EstrategiaMatch.Estrategias;

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
	protected int animaciones_pendientes;
	protected int movimientosRestantes;
	private int size_label = 70;
	private CentralPantallas mi_central_pantallas;
	protected boolean bloquear_intercambios;
	protected CentralPaneles mi_central_paneles;
	protected CentralAnimaciones mi_animador;
	protected JPanel panel_tablero,panel_principal;
	private Imagenfondo fondo = new Imagenfondo();	
	// Define un mapa para asociar los códigos de tecla con las acciones
	private Map<Integer, Runnable> acciones = new HashMap<>();
	
	
	public static final int ARRIBA = 15000;
	public static final int ABAJO = 15001;
	public static final int IZQUIERDA = 15002;
	public static final int DERECHA = 15003;
	
	
	public GUI(Logica l, int f, int c) {
		miLogica = l;
		filas = f;
		columnas = c;
		animaciones_pendientes = 0;
		bloquear_intercambios = false;
		mi_animador = new CentralAnimaciones(this);
		inicializarGUI();
	}

	private void inicializarGUI() {
		setContentPane(fondo);
		
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
		
		
		mostrarModosDeJuego();
	}
	
	public void inicializarJuego() {
		eliminarPantallaModosDeJuego();
		setContentPane(fondo);
		repaint();
		revalidate();
		panel_principal.repaint();
		panel_principal.revalidate();
		inicializarPanels();

		panel_tablero = new JPanel();
		panel_tablero.setSize(size_label * filas, size_label * columnas);
		panel_tablero.setLayout(new GridBagLayout());
		panel_tablero.setBackground(new Color(0,0,0,125));

		configurarAccionesTeclado();
		
		GridBagConstraints c = new GridBagConstraints();
		//Constraints TABLERO
		c.insets = new Insets(0,0,0,0);
		c.weightx = 1;
		c.weighty = 1;
		c.anchor = GridBagConstraints.CENTER;
		
		agregarConGBCs(panel_tablero, panel_principal, c, 2, 1, 4, 4); 
		panel_tablero.setVisible(true);
		panel_tablero.setRequestFocusEnabled(true);
		panel_tablero.setFocusable(true);
		getContentPane().add(panel_principal);

		SwingUtilities.invokeLater(()->{
			panel_principal.requestFocus();
			panel_tablero.requestFocus();
			panel_tablero.grabFocus();
			repaint();
			revalidate();
		});
	}

	public void darFocusATablero() {
		panel_tablero.requestFocus();
		panel_tablero.grabFocus();
		repaint();
		revalidate();
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
		
		acciones.put(KeyEvent.VK_R, () -> {
		    if (!bloquear_intercambios){
		    	mostrarPuntajes();
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

	private void setearJFrame(Dimension screenSize) {
		setTitle("Proyecto Candy Crush - Comision-06");
		setSize(screenSize);
		setLayout(new BorderLayout());
		setExtendedState(JFrame.MAXIMIZED_BOTH); 
		setLocationRelativeTo(null);
		setVisible(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setExtendedState(JFrame.MAXIMIZED_BOTH);
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
		mi_central_paneles.mostrarPanelTimer();
	}

	public int getVidas() {
		return miLogica.getVidas();
	}

	public int getScore() {
		return miLogica.getScore();
	}

	private void eliminarPantallaModosDeJuego() {
		mi_central_pantallas.eliminarPantallaModosDeJuego();
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
		return miLogica.getTiempo();
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

	public void actualizarTimer(int tiempo) {
		mi_central_paneles.actualizarTimer(tiempo);
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

	public void mostrarModosDeJuego() {
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

	public void actualizarScore(int score) {
		mi_central_paneles.actualizarScore(score);
	}
	
	public void eliminar_celda(Celda celda) {
		panel_principal.remove(celda);
		panel_principal.repaint();
	}

	public PriorityQueue<Jugador> obtenerListadeJugadores() {
		return miLogica.obtenerListadeJugadores();
	}

	public String[] obtenerArrayNombresJugadores() {
		return miLogica.obtenerArrayNombresJugadores();
	}

	public int[] obtenerArrayScoreJugadores() {
		return miLogica.obtenerArrayScoreJugadores();
	}

	public int getMovimientos() {
		return miLogica.getMovimientos();
	}
	
	public void limpiarGUI() {
	    movimientosRestantes = getMovimientos();
	    animaciones_pendientes = 0;
	    bloquear_intercambios = false;

	    limpiarMatrizGUI();
	    mi_central_paneles.actualizarVidas();

		mi_central_paneles.actualizarTimer(getTiempoRestante());
	    mi_central_paneles.actualizarMovimientos(miLogica.getMovimientos());
	}

	private void limpiarMatrizGUI() {
	    panel_tablero.removeAll();
	    panel_tablero.revalidate();
	    panel_tablero.repaint();
	}
	
	public boolean getBloquear_intercambios() {
		return bloquear_intercambios;
	}

	public LinkedList<Estrategias> getEstrategias(){
		return miLogica.getEstrategias();
	}

	private void agregarConGBCs(Component componenteAAgregar, JPanel panelBase, GridBagConstraints gbc, int gridx, int gridy, int gridwidth,int gridheight) {

		gbc.gridx = gridx;
		gbc.gridy = gridy;
		gbc.gridwidth = gridwidth;
		gbc.gridheight = gridheight;

		panelBase.add(componenteAAgregar,gbc);
	}

	public void reiniciarGUI( int f, int c) {
		filas = f;
		columnas = c;
		movimientosRestantes = miLogica.getMovimientos();
		animaciones_pendientes = 0;
		bloquear_intercambios = false;
		mi_animador = new CentralAnimaciones(this);

		inicializarGUI2();
	}

	private void inicializarGUI2() {
		this.setContentPane(fondo);
		getContentPane().remove(panel_principal);
		
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
		
		inicializarPanels();

		panel_tablero = new JPanel();
		panel_tablero.setSize(size_label * filas, size_label * columnas);
		panel_tablero.setLayout(new GridBagLayout());
		panel_tablero.setBackground(new Color(0,0,0,255));

		configurarAccionesTeclado();
		
		GridBagConstraints c = new GridBagConstraints();
		//Constraints TABLERO
		c.insets = new Insets(0,0,0,0);
		c.weightx = 1;
		c.weighty = 1;
		c.anchor = GridBagConstraints.CENTER;
		agregarConGBCs(panel_tablero, panel_principal, c, 2, 1, 4, 4); 
		
		panel_tablero.setFocusable(true);
		getContentPane().add(panel_principal);
	}

    public CentralPaneles obtenerCentralPaneles()
	{
		return mi_central_paneles;
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
	    mi_central_paneles.actualizarTimer(getTiempoRestante());
	    mi_central_paneles.actualizarMovimientos(getMovimientos());
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

	
}