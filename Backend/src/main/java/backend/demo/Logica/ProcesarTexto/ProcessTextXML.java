package backend.demo.Logica.ProcesarTexto;

public class ProcessTextXML {
    private static ProcessTextXML instance;
    private String textClass;
    private String textInterface;
    private String textRoute;

    public ProcessTextXML() {

    }

    public void processXML() {
        
    }

    public String getTextClass() {
        return textClass;
    }

    public String getTextInterface() {
        return textInterface;
    }

    public String getTextRoute() {
        return textRoute;
    }

    public static ProcessTextXML getInstance() {
        if(instance == null) {
            instance = new ProcessTextXML();
        }
        return instance;
    }
}
