package backend.demo.Logica.LogicGenerateRoute;

import java.util.ArrayList;

public class GeneradorRoute {
    private static GeneradorRoute instance;

    public void Generar(ArrayList<String> lista) {

    }

    public static GeneradorRoute getInstance() {
        if(instance == null) {
            instance = new GeneradorRoute();
        }
        return instance;
    }
}
