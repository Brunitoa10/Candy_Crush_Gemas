package Nivel;

public class Objetivos {
	//Atributos
	protected int cantGemas;
    protected int tipoGema;
        
    //Constructor
    public Objetivos(int cantGemas, int tipoGema) {
        this.cantGemas = cantGemas;
        this.tipoGema = tipoGema;
    }

    public int getCantGemas() {
        return cantGemas;
    }

    public int getTipoGema() {
        return tipoGema;
    }
    
    public void setTotalCantGemas(int cant) {
    	cantGemas = cant;
    }
    
    public void aumentarProgreso(int cantidad) {
        cantGemas -= cantidad; //progreso += cantidad;
    }

    public boolean estaCumplido() {
        return cantGemas == 0;//progreso >= cantGemas;
    }


}
