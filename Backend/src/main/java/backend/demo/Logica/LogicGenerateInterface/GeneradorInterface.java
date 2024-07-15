package backend.demo.Logica.LogicGenerateInterface;

import java.util.ArrayList;

import backend.demo.Logica.ProcesarTexto.ProcessTextXML;

public class GeneradorInterface {
    private static GeneradorInterface instance;
    private ProcessTextXML XML;

    public GeneradorInterface() {
        XML = ProcessTextXML.getInstance();
    }

    public void Generar(ArrayList<String> lista) {
    }

    public static GeneradorInterface getInstance() {
        if (instance == null) {
            instance = new GeneradorInterface();
        }
        return instance;
    }
}
