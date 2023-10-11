package Nivel;

public class Objetivos {
	//Atributos
	protected int cantGemas;
    protected int tipoGema;
    protected int progreso;
    
    //Constructor
    public Objetivos(int cantGemas, int tipoGema) {
        this.cantGemas = cantGemas;
        this.tipoGema = tipoGema;
        progreso = 0;
    }

    public int getCantGemas() {
        return cantGemas;
    }

    public int getTipoGema() {
        return tipoGema;
    }
    
    public void aumentarProgreso(int cantidad) {
        progreso += cantidad;
    }

    public boolean estaCumplido() {
        return progreso >= cantGemas;
    }

	public int getProgreso() {
		return progreso;
	}
}
