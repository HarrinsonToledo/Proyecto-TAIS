package backend.demo.Logica.LogicGenerateInterface;

import java.util.ArrayList;

import backend.demo.Logica.ProcesarTexto.ProcessTextXML;

public class GeneradorInterface {
    private static GeneradorInterface instance;
    private ProcessTextXML XML;

    public GeneradorInterface() {
        XML = ProcessTextXML.getInstance();
    }

    public String start(String inter) {
        String txt = "public Class " + XML.extractText(inter, "<Interface name=\"(.*?)\">").get(0) + "{\n";

        return txt;
    }

    public String atributtes(String inter) {
        String txt = "";

        ArrayList<String> att = XML.extractText(inter, "<Attribute (.*?)/>");

        if(att.size() != 0) {
            for(String x: att) {
                txt += "\t" + XML.extractTextSingle(x, "visibility=\"(.*?)\"").get(0) + " ";
                txt += XML.extractTextSingle(x, "type=\"(.*?)\"").get(0) + " ";
                txt += XML.extractTextSingle(x, "name=\"(.*?)\"").get(0) + ";\n";
            }
        }

        return txt;
    }

    public String methods(String inter) {
        String txt = "";

        ArrayList<String> mth = XML.extractText(inter, "<Method (.*?)</Method>");

        if(mth.size() != 0) {
            for(String x: mth) {
                txt += "\t" + XML.extractTextSingle(x, "visibility=\"(.*?)\"").get(0) + " ";
                txt += XML.extractTextSingle(x, "type=\"(.*?)\"").get(0) + " ";
                txt += XML.extractTextSingle(x, "name=\"(.*?)\"").get(0);

                txt += "(" + paramethers(x) + ")";

                if(XML.extractTextSingle(x, "abstract=\"(.*?)\"").get(0) == "0") {
                    txt += ";\n";
                } else if (XML.extractTextSingle(x, "abstract=\"(.*?)\"").get(0) == "1") {
                    txt += "{\n\n\t}";
                }
            }
        }
        return txt;
    }

    public String paramethers(String info) {
        String txt = "";

        ArrayList<String> prm = XML.extractText(info, "<Parameter (.*?)/>");

        if(prm.size() != 0) {
            for(int i = 0; i < prm.size(); i++) {
                txt += XML.extractTextSingle(prm.get(i), "type=\"(.*?)\"").get(0) + " ";
                txt += XML.extractTextSingle(prm.get(i), "name=\"(.*?)\"").get(0) + ";\n";

                if(i != prm.size()-1) {
                    txt += ",";
                }
            }
        }

        return txt;
    }

    public void Generar(ArrayList<String> lista) {
        String content = "";
        for(String x: lista) {
            content = start(x) + atributtes(x) + methods(x) + "\n}";
        }

        System.out.println(content);
    }

    public static GeneradorInterface getInstance() {
        if (instance == null) {
            instance = new GeneradorInterface();
        }
        return instance;
    }
}
