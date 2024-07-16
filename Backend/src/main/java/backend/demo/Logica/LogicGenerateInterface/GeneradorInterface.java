package backend.demo.Logica.LogicGenerateInterface;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import backend.demo.Logica.LogicGenerateRoute.GeneradorRoute;
import backend.demo.Logica.ProcesarTexto.ProcessTextXML;

public class GeneradorInterface {
    private static GeneradorInterface instance;
    private ProcessTextXML XML;
    private String outputDirectory;
    private static final Logger LOGGER = Logger.getLogger(GeneradorRoute.class.getName());

    public GeneradorInterface() {
        XML = ProcessTextXML.getInstance();

        this.outputDirectory = "Backend/src/main/java/backend/demo/Logica/LogicGenerateInterface/"; // Default
                                                                                                // directory
        createOutputDirectory();
    }

    public void setOutputDirectory(String outputDirectory) {
        this.outputDirectory = outputDirectory;
        createOutputDirectory();
    }

    private void createOutputDirectory() {
        File directory = new File(outputDirectory);
        if (!directory.exists()) {
            boolean created = directory.mkdirs();
            if (created) {
                LOGGER.info("Output directory created: " + outputDirectory);
            } else {
                LOGGER.severe("Failed to create output directory: " + outputDirectory);
            }
        } else {
            LOGGER.info("Output directory already exists: " + outputDirectory);
        }
    }

    public String start(String inter) {
        String txt = "public interface " + XML.extractTextSingle(inter, "<Interface name=\"(.*?)\">").get(0) 
        
        + "{\n";

        return txt;
    }

    public String atributtes(String inter) {
        String txt = "";

        ArrayList<String> att = XML.extractText(inter, "<Attribute (.*?)/>");

        if(att.size() != 0) {
            for(String x: att) {
                txt += "\t" + XML.extractTextSingle(x, "type=\"(.*?)\"").get(0) + " ";
                txt += XML.extractTextSingle(x, "name=\"(.*?)\"").get(0) + ";\n";
            }
        }

        return txt;
    }

    public String methods(String inter) {
        String txt = "";

        ArrayList<String> mth = XML.extractText(inter, "<Method (.*?)</Method>");

        if(mth.size() != 0) {
            for(String y: mth) {
                String x = XML.extractText(y, "<Method (.*?)>").get(0);
                txt += "\t" + XML.extractTextSingle(x, "visibility=\"(.*?)\"").get(0) + " ";
                txt += XML.extractTextSingle(x, "type=\"(.*?)\"").get(0) + " ";
                txt += XML.extractTextSingle(x, "name=\"(.*?)\"").get(0);

                txt += "(" + paramethers(y) + ")";
                if(XML.extractTextSingle(x, "abstract=\"(.*?)\"").get(0).equals("1")) {
                    txt += ";\n";
                } else if (XML.extractTextSingle(x, "abstract=\"(.*?)\"").get(0).equals("0")) {
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
                txt += XML.extractTextSingle(prm.get(i), "name=\"(.*?)\"").get(0);

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
            writeJavaFile(XML.extractTextSingle(x, "<Interface name=\"(.*?)\">").get(0), content);
        }
    }

    private void writeJavaFile(String className, String classContent) {
        Path path = Paths.get(outputDirectory + className + ".java");
        try {
            LOGGER.info("Writing file: " + path.toAbsolutePath());
            Files.write(path, classContent.getBytes());
            LOGGER.info("File written successfully: " + path.toAbsolutePath());
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Failed to write file: " + path.toAbsolutePath(), e);
        }
    }

    public static GeneradorInterface getInstance() {
        if (instance == null) {
            instance = new GeneradorInterface();
        }
        return instance;
    }
}
