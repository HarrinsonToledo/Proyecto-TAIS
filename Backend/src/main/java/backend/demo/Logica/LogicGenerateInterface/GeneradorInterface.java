package backend.demo.Logica.LogicGenerateInterface;

import java.util.ArrayList;

public class GeneradorInterface {
    private static GeneradorInterface instance;

    public void Generar(ArrayList<String> lista) {

    }

    public static GeneradorInterface getInstance() {
        if(instance == null){
            instance = new GeneradorInterface();
        }
        return instance;
    }
}
