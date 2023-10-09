package Nivel;

public class Objetivos {
	private int cantGemas;
    private int tipoGema;
    private int progreso;
    
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
}
