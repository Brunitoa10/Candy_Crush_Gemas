package Launcher;

import java.awt.EventQueue;

import Logica.Juego;

public class Launcher {
	public static void main(String [] args) {
		EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                	new Juego();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
	}

}
