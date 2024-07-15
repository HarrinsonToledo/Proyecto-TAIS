package backend.demo.Logica.LogicGenerateRoute;

import org.w3c.dom.*;
import javax.xml.parsers.*;
import java.io.*;
import java.nio.file.*;
import java.util.ArrayList;

public class GeneradorRoute {
    private static GeneradorRoute instance;
    private String outputDirectory;

    public GeneradorRoute() {
        this.outputDirectory = "src/main/java/backend/demo/Controllers/Logica/LogicGenerateRoute/"; // Default directory
        createOutputDirectory();
    }

    public void setOutputDirectory(String outputDirectory) {
        this.outputDirectory = outputDirectory;
        createOutputDirectory();
    }

    private void createOutputDirectory() {
        File directory = new File(outputDirectory);
        if (!directory.exists()) {
            directory.mkdirs();
        }
    }

    public void Generar(ArrayList<String> lista) {
        for (String routeXML : lista) {
            try {
                processRoute(routeXML);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void processRoute(String routeXML) throws Exception {
        // Parse the route XML content
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

        // Generate Java class content
        String classContent = generateJavaClassContent(routeName, attributesBuilder.toString(),
                methodsBuilder.toString());

        // Write Java class to file
        writeJavaFile(routeName, classContent);
    }

    private String generateJavaClassContent(String className, String attributes, String methods) {
        return "@CrossOrigin(origins = \"http://localhost:5173\")\n" +
                "@RestController\n" +
                "public class " + className + " {\n\n" +
                attributes + "\n" +
                methods + "\n" +
                "}\n";
    }

    private void writeJavaFile(String className, String classContent) throws IOException {
        Path path = Paths.get(outputDirectory + className + ".java");
        System.out.println(path);
        Files.write(path, classContent.getBytes());
    }

    public static GeneradorRoute getInstance() {
        if (instance == null) {
            instance = new GeneradorRoute();
        }
        return instance;
    }
}
