package backend.demo.Logica.LogicGenerateRoute;

public class GeneradorRoute {
    private static GeneradorRoute instance;

    public void Generar() {

    }

    public static GeneradorRoute getInstance() {
        if(instance == null) {
            instance = new GeneradorRoute();
        }
        return instance;
    }
}
