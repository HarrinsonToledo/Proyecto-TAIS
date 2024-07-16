package backend.demo.Logica.LogicGenerateClass;

import org.w3c.dom.*;

import backend.demo.Logica.Estructura;
import backend.demo.Logica.LogicGenerateInterface.GeneradorInterface;
import backend.demo.Logica.LogicGenerateRoute.GeneradorRoute;
import backend.demo.Logica.ProcesarTexto.ProcessTextXML;
import javax.xml.parsers.*;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class GeneradorClass {
    private static GeneradorClass instance;
    private ProcessTextXML XML;
    private String outputDirectory;
    private static final Logger LOGGER = Logger.getLogger(GeneradorRoute.class.getName());

    private GeneradorClass() {
        XML = ProcessTextXML.getInstance();
        this.outputDirectory = "Backend/src/main/java/backend/demo/Logica/LogicGenerateClass/";
        createOutputDirectory();
    }

    public static GeneradorClass getInstance() {
        if (instance == null) {
            instance = new GeneradorClass();
        }
        return instance;
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

    public String start(String classs) {
        String className = XML.extractTextSingle(classs, "<Class name='(.*?)'").get(0);
        StringBuilder txt = new StringBuilder("public class ").append(className);

        // Check for extend
        String extend = XML.extractTextSingle(classs, "<Extend name='(.*?)'").get(0);
        if (!"None".equals(extend)) {
            txt.append(" extends ").append(extend);
        }

        // Check for implements
        ArrayList<String> implementsList = XML.extractText(classs, "<Implement name='(.*?)'");

        if (implementsList.size() > 0) {
            String implement = XML.extractTextSingle(classs, "<Implement name='(.*?)'").get(0);
            if (!"None".equals(implement)) {
                txt.append(" implements ").append(implement);
            }
        }

        txt.append(" {\n");
        return txt.toString();
    }

    public String attributes(String classs) {
        StringBuilder txt = new StringBuilder();
        ArrayList<String> att = XML.extractText(classs, "<Attribute (.*?)/>");

        if (att.size() != 0) {
            for (String x : att) {
                txt.append("\t").append(XML.extractTextSingle(x, "visibility='(.*?)'").get(0)).append(" ");
                txt.append(XML.extractTextSingle(x, "type='(.*?)'").get(0)).append(" ");
                txt.append(XML.extractTextSingle(x, "name='(.*?)'").get(0)).append(";\n");
            }
        }
        return txt.toString();
    }

    public String methods(String classs) {
        StringBuilder txt = new StringBuilder();
        ArrayList<String> mth = XML.extractText(classs, "<Method (.*?)</Method>");

        if (mth.size() != 0) {
            for (String y : mth) {
                String x = XML.extractText(y, "<Method (.*?)>").get(0);

                String visibility = XML.extractTextSingle(x, "visibility='(.*?)'").get(0);
                String abstractValue = XML.extractTextSingle(x, "abstract='(.*?)'").get(0);
                String returnType = XML.extractTextSingle(x, "type='(.*?)'").get(0);
                String methodName = XML.extractTextSingle(x, "name='(.*?)'").get(0);
                String parameters = parameters(y);

                txt.append("\t");
                if (abstractValue.equals("2")) {
                    txt.append("@Override\n\t");
                }
                txt.append(visibility).append(" ");
                txt.append(returnType).append(" ");
                txt.append(methodName).append("(").append(parameters).append(") {\n");
                txt.append("\t}\n\n");
            }
        }
        return txt.toString();
    }

    public String parameters(String info) {
        StringBuilder txt = new StringBuilder();
        ArrayList<String> prm = XML.extractText(info, "<Parameter (.*?)/>");

        if (prm.size() != 0) {
            for (int i = 0; i < prm.size(); i++) {
                txt.append(XML.extractTextSingle(prm.get(i), "type='(.*?)'").get(0)).append(" ");
                txt.append(XML.extractTextSingle(prm.get(i), "name='(.*?)'").get(0));

                if (i != prm.size() - 1) {
                    txt.append(", ");
                }
            }
        }
        return txt.toString();
    }

    public void Generar(ArrayList<String> classList, Estructura estructura) {
        for (String x : classList) {
            String content = "package " + estructura.namePacke + ".LogicPackage;\n\n" + start(x) + attributes(x) + methods(x) + "}\n";
            writeJavaFile(XML.extractTextSingle(x, "<Class name='(.*?)' ").get(0), content);
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
}
