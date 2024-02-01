package Nivel;

public class Nivel {
	
	protected int fila_inicial_jugador;
	protected int columna_inicial_jugador;
	protected int cantidadMovimientos;
	protected int cantidadTiempo;
	protected int cantidadVidas;

	public Nivel(int f, int c) {
		fila_inicial_jugador = f;
		columna_inicial_jugador = c;
		cantidadMovimientos = 0;
		cantidadTiempo = 0;
		cantidadVidas = 0;
	}
	
	public int get_fila_inicial_jugador() {
		return fila_inicial_jugador;
	}
	
	public int get_columna_inicial_jugador() {
		return columna_inicial_jugador;
	}

	public void setFila_inicial_jugador(int fila_inicial_jugador) {
		this.fila_inicial_jugador = fila_inicial_jugador;
	}

	public void setColumna_inicial_jugador(int columna_inicial_jugador) {
		this.columna_inicial_jugador = columna_inicial_jugador;
	}

	public int getCantidadMovimientos() {
		return cantidadMovimientos;
	}

	public void setCantidadMovimientos(int cantidadMovimientos) {
		this.cantidadMovimientos = cantidadMovimientos;
	}

	public int getCantidadTiempo() {
		return cantidadTiempo;
	}

	public void setCantidadTiempo(int cantidadTiempo) {
		this.cantidadTiempo = cantidadTiempo;
	}

	public int getCantidadVidas() {
		return cantidadVidas;
	}

	public void setCantidadVidas(int cantidadVidas) {
		this.cantidadVidas = cantidadVidas;
	}

	
}
