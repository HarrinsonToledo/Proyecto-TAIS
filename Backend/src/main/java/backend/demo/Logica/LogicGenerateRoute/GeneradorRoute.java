package backend.demo.Logica.LogicGenerateRoute;

import org.w3c.dom.*;

import backend.demo.Logica.Estructura;

import javax.xml.parsers.*;
import java.io.*;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class GeneradorRoute {
    private static GeneradorRoute instance;
    private String outputDirectory;
    private static final Logger LOGGER = Logger.getLogger(GeneradorRoute.class.getName());
    private String packageName;

    public GeneradorRoute() {
        this.outputDirectory = "Backend/src/main/java/backend/demo/Logica/LogicGenerateRoute/"; // Default directory
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

    public void setPackageName(Estructura estructura) {
        this.packageName = estructura.namePacke;
    }

    public void Generar(ArrayList<String> lista, Estructura estructura) {
        setPackageName(estructura);
        for (String routeXML : lista) {
            try {
                processRoute(routeXML);
            } catch (Exception e) {
                LOGGER.log(Level.SEVERE, "Error processing route XML: " + routeXML, e);
            }
        }
    }

    private void processRoute(String routeXML) throws Exception {
        LOGGER.info("Processing route XML: " + routeXML);

        // Parse the XML content of the route
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document doc = builder.parse(new ByteArrayInputStream(routeXML.getBytes()));
        doc.getDocumentElement().normalize();

        Element routeElement = doc.getDocumentElement();
        String routeName = routeElement.getAttribute("name");

        // Extract attributes
        NodeList attributeList = routeElement.getElementsByTagName("Attribute");
        StringBuilder attributesBuilder = new StringBuilder();
        for (int j = 0; j < attributeList.getLength(); j++) {
            Element attributeElement = (Element) attributeList.item(j);
            String attributeName = attributeElement.getAttribute("name");
            String attributeType = attributeElement.getAttribute("type");
            String attributeVisibility = attributeElement.getAttribute("visibility");
            attributesBuilder.append(attributeVisibility).append(" ").append(attributeType).append(" ")
                    .append(attributeName).append(";\n");
        }

        // Extract methods
        NodeList methodList = routeElement.getElementsByTagName("Method");
        StringBuilder methodsBuilder = new StringBuilder();
        for (int j = 0; j < methodList.getLength(); j++) {
            Element methodElement = (Element) methodList.item(j);
            String methodName = methodElement.getAttribute("name");
            String methodPath = methodElement.getAttribute("path");
            String methodTypeHttp = methodElement.getAttribute("typeHttp");
            String methodVisibility = methodElement.getAttribute("visibility");

            NodeList parameterList = methodElement.getElementsByTagName("Parameter");
            String parameters = "";
            if (parameterList.getLength() > 0) {
                Element parameterElement = (Element) parameterList.item(0);
                String parameterType = parameterElement.getAttribute("type");
                String parameterName = parameterElement.getAttribute("name");
                parameters = "@" + parameterElement.getAttribute("typeHttp") + " " + parameterType + " "
                        + parameterName;
            }

            methodsBuilder.append("@").append(methodTypeHttp.substring(0, 1).toUpperCase())
                    .append(methodTypeHttp.substring(1).toLowerCase()).append("Mapping(\"").append(methodPath)
                    .append("\")\n");
            methodsBuilder.append(methodVisibility).append(" void ").append(methodName).append("(").append(parameters)
                    .append(") {\n");
            methodsBuilder.append("    // TODO: Add method implementation\n");
            methodsBuilder.append("}\n");
        }

        // Generate the content of the Java class
        String classContent = generateJavaClassContent(routeName, attributesBuilder.toString(),
                methodsBuilder.toString(), packageName);

        // Write the Java class to a file
        writeJavaFile(routeName, classContent);
    }

    private String generateJavaClassContent(String className, String attributes, String methods, String packageName) {
        String imports = "import java.util.ArrayList;\n" +
                "import java.util.HashMap;\n" +
                "import java.util.List;\n" +
                "import java.util.Map;\n" +
                "import org.apache.catalina.connector.Response;\n" +
                "import org.springframework.http.ResponseEntity;\n" +
                "import org.springframework.web.bind.annotation.*;\n";

        return "package " + packageName + ".RoutesPackage;\n\n" +
                imports +
                "\n@CrossOrigin(origins = \"http://localhost:5173\")\n" +
                "@RestController\n" +
                "public class " + className + " {\n\n" +
                attributes + "\n" +
                methods + "\n" +
                "}\n";
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

    public static GeneradorRoute getInstance() {
        if (instance == null) {
            instance = new GeneradorRoute();
        }
        return instance;
    }
}
