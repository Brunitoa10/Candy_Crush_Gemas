package EstrategiasdeMatch;

public interface Estrategia {
    
    public abstract boolean cumple_con_la_Estrategia(int vertical, int horizontal);

    public abstract boolean crea_Gema_Especial(int vertical, int horizontal);
}
