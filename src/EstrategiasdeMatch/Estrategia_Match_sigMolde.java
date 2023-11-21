

public class Estrategia_Match_sigMolde implements Estrategia{

    @Override
    public boolean cumple_con_la_Estrategia(int vertical, int horizontal) {
        return (vertical<=3 && horizontal<=3);
    }

    @Override
    public boolean crea_Gema_Especial(int vertical, int horizontal) {
        return cumple_con_la_Estrategia(vertical, horizontal);
    }
    
}
