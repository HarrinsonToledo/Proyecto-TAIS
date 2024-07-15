package backend.demo.Logica.ProcesarTexto;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ProcessTextXML {
    private static ProcessTextXML instance;
    private ArrayList<String> textClass;
    private ArrayList<String> textInterface;
    private ArrayList<String> textRoute;

    public ProcessTextXML() {

    }

    public ArrayList<String> extractText(String text, String separate) {
        ArrayList<String> textSeparate = new ArrayList<>();

        String regex = separate;
        Pattern pattern = Pattern.compile(regex, Pattern.DOTALL);
        Matcher matcher = pattern.matcher(text);

        while (matcher.find()) {
            textSeparate.add(matcher.group());
        }

        return textSeparate;
    }

    public void processXML(String XML) {
        textClass = extractText(XML, "<Class>(.*?)</Class>");
        textInterface = extractText(XML, "<Interface>(.*?)</Interface>");
        textRoute = extractText(XML, "<Route>(.*?)</Route>");
    }

    public ArrayList<String> getTextClass() {
        return textClass;
    }

    public ArrayList<String> getTextInterface() {
        return textInterface;
    }

    public ArrayList<String> getTextRoute() {
        return textRoute;
    }

    public static ProcessTextXML getInstance() {
        if(instance == null) {
            instance = new ProcessTextXML();
        }
        return instance;
    }
}
