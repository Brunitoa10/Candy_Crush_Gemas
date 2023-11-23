package GeneradorEntidades;

import java.util.HashMap;
import java.util.Map;

public class EntidadFactoryRegistry {
    private static final Map<String, EntidadFactory> entidadFactories = new HashMap<>();

    static {
        entidadFactories.put("n", new GemaNormalFactory());
        entidadFactories.put("r", new RocaFactory());
        entidadFactories.put("b", new BombaFactory());
        entidadFactories.put("p", new GemaRayadaFactory());
        entidadFactories.put("e", new GemaEnvueltaFactory());
        entidadFactories.put("c", new GemaCruzadaFactory());
    }

    public static Map<String, EntidadFactory> getEntidadFactories() {
        return entidadFactories;
    }
   
}
