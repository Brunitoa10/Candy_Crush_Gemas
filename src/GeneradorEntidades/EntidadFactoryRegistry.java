package GeneradorEntidades;

import java.util.HashMap;
import java.util.Map;

public class EntidadFactoryRegistry {
    private static final Map<String, EntidadFactory> entidadFactories = new HashMap<>();

    static{
        entidadFactories.put("n", new EntidadCaramelo());
        entidadFactories.put("r", new EntidadGlaseado());
        //entidadFactories.put("b", new BombaFactory());
        entidadFactories.put("ph", new EntidadPotenciadorHorizontal());
        entidadFactories.put("pv", new EntidadPotenciadorVertical());
        //entidadFactories.put("c", new GemaCruzadaFactory());
        /*entidadFactories.put("hp", new HieloGemaRayadaFactory());
        entidadFactories.put("he", new HieloGemaEnvueltaFactory());
        entidadFactories.put("hc", new HieloGemaCruzadaFactory());
        entidadFactories.put("hn", new HieloGemaNormalFactory());*/
    }

    public static Map<String, EntidadFactory> getEntidadFactories() {
        return entidadFactories;
    }

    public static String[] getFactoryKeys() {
        return entidadFactories.keySet().toArray(new String[0]);
    }
}
