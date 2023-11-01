package Threads;

import GUI.GUI;
import GUI.Celda;
import java.awt.Point;


public class AnimadorMovimiento extends Thread{
	protected GUI miGUI;
	protected int sizeLabel;
	protected int step;
	protected int delay;

	Celda celda1, celda2;
	public AnimadorMovimiento(int sizeLabel, int step, int delay, Celda c1, Celda c2) {
		this.sizeLabel = sizeLabel;
		this.step = step;
		this.delay = delay;
		this.celda1 = c1;
		this.celda2 = c2;
	}
	
	@Override
	public void run() {
		System.out.println("inicia animacion intercambio");
		int direccionX1 = 0;
		int direccionY1 = 0;
		int direccionX2 = 0;
		int direccionY2 = 0;
		
		Point puntoActual1 = celda1.getLocation();
		Point puntoActual2 = celda2.getLocation();
		Point puntoFuturo1 = (Point) puntoActual2.clone();
		Point puntoFuturo2 = (Point) puntoActual1.clone();

		miGUI.setBloquearAccionesJugador(true);
		
		System.out.println("X: "+puntoActual1.x);
		System.out.println("X futuro: "+puntoFuturo1.x);

		System.out.println("Y: "+puntoActual1.y);
		System.out.println("Y futuro: "+puntoFuturo1.y);
		if (puntoActual1.x != puntoFuturo1.x) {
			if(puntoActual1.x < puntoFuturo1.x) {
				direccionX1 = 1;
			}
			else direccionX1 = -1;
		}

		if (puntoActual1.y != puntoFuturo1.y) {
			if(puntoActual1.y < puntoFuturo1.y) {
				direccionY1 = 1;
			}
			else direccionY1 = -1;
		}
		
		direccionX2 = direccionX1 * (-1);
		direccionY2 = direccionY2 * (-1);

		boolean seguirC1 = direccionX1 == 0 || direccionY1 == 0;
		boolean seguirC2 = direccionY1 == 0 || direccionY2 == 0;		
		
		int distanciaRecorridaEnX1 = 0, distanciaRecorridaEnY1 = 0;
		int distanciaRecorridaEnX2 = 0, distanciaRecorridaEnY2 = 0;
		
		while(seguirC1 || seguirC2) {
			puntoActual1.translate(direccionX1*step, direccionY1*step);
			puntoActual2.translate(direccionX2*step, direccionY2*step);
			
			distanciaRecorridaEnX1 += direccionX1*step; 
			distanciaRecorridaEnY1 += direccionY1*step;
			distanciaRecorridaEnX2 += direccionX2*step;
			distanciaRecorridaEnY2 += direccionY2*step;
			
			celda1.setLocation(puntoActual1);
			celda2.setLocation(puntoActual2);
			
			puntoActual1 = celda1.getLocation();
			puntoActual2 = celda2.getLocation();
			seguirC1 = puntoActual1.distance(puntoFuturo1) > 3 && Math.abs(distanciaRecorridaEnX1) < sizeLabel && Math.abs(distanciaRecorridaEnY1) < sizeLabel;;
			seguirC2 = puntoActual2.distance(puntoFuturo2) > 3 && Math.abs(distanciaRecorridaEnX2) < sizeLabel && Math.abs(distanciaRecorridaEnY2) < sizeLabel;
			
			
			try {
				Thread.sleep(delay);
				
				
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
		celda1.setLocation(puntoFuturo1);
		celda2.setLocation(puntoFuturo2);
		miGUI.repaint();
		miGUI.setBloquearAccionesJugador(false); //Se libera para que la aplicacion continue respondiendo a los eventos de teclado. El bloqueo se habia hecho en el metodo intercambiar de Juego
		}
	}
	
	
	