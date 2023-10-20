package Nivel;

public class Objetivos {
	//Atributos
	protected int cantGemas;
    protected int tipoGema;
    protected int objetivosCumplidos;
        
    //Constructor
    public Objetivos(int cantGemas, int tipoGema) {
        this.cantGemas = cantGemas;
        this.tipoGema = tipoGema;
        objetivosCumplidos = 0;
    }

    public int getCantGemas() {
        return cantGemas;
    }

    public int getTipoGema() {
        return tipoGema;
    }
    
    public int getObjetivosCumplidos() {
        return objetivosCumplidos;
    }
    
    public void setTotalCantGemas(int cant) {
    	cantGemas = cant;
    }
    
    public void aumentarProgreso(int cantidad) {
        cantGemas -= cantidad;
    }

    public boolean estaCumplido() {
        return cantGemas == 0;
    }

	public void incrementarObjetivoCumplido() {
		objetivosCumplidos++;
	}

	public void reiniciarContadorDeObjetivosCumplidos() {
		objetivosCumplidos = 0;
	}
    
}