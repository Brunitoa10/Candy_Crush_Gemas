package GeneradorEntidades;

import java.util.HashMap;
import java.util.Map;

public class EntidadFactoryRegistry {
    private static final Map<String, EntidadFactory> entidadFactories = new HashMap<>();

    static{
        entidadFactories.put("n", new EntidadCaramelo());
        entidadFactories.put("r", new EntidadGlaseado());
        entidadFactories.put("h", new EntidadPotenciadorHorizontal());
        entidadFactories.put("v", new EntidadPotenciadorVertical());
    }

    public static Map<String, EntidadFactory> getEntidadFactories() {
        return entidadFactories;
    }
}
