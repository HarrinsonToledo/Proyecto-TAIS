package backend.demo.Logica.LogicGenerateInterface;

public class GeneradorInterface {
    private static GeneradorInterface instance;

    public void Generar() {

    }

    public static GeneradorInterface getInstance() {
        if(instance == null){
            instance = new GeneradorInterface();
        }
        return instance;
    }
}
