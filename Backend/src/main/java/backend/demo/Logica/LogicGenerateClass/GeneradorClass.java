package backend.demo.Logica.LogicGenerateClass;

import org.w3c.dom.*;
import javax.xml.parsers.*;
import java.io.*;

public class GeneradorClass {
    public static void main(String[] args) {
        try {
            File inputFile = new File("input.xml");
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(inputFile);
            doc.getDocumentElement().normalize();

            NodeList nList = doc.getElementsByTagName("Class");

            for (int temp = 0; temp < nList.getLength(); temp++) {
                Node nNode = nList.item(temp);
                if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element eElement = (Element) nNode;

                    String className = eElement.getAttribute("name");
                    boolean isAbstract = eElement.getAttribute("abstract").equals("1");
                    generateJavaClass(className, isAbstract, eElement);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void generateJavaClass(String className, boolean isAbstract, Element classElement) throws IOException {
        String fileName = className + ".java";
        BufferedWriter writer = new BufferedWriter(new FileWriter(fileName));

        if (isAbstract) {
            writer.write("public abstract class " + className + " {\n");
        } else {
            writer.write("public class " + className + " {\n");
        }

        NodeList attrList = classElement.getElementsByTagName("Attribute");
        for (int i = 0; i < attrList.getLength(); i++) {
            Node attrNode = attrList.item(i);
            if (attrNode.getNodeType() == Node.ELEMENT_NODE) {
                Element attrElement = (Element) attrNode;
                String attrVisibility = attrElement.getAttribute("visibility");
                String attrType = attrElement.getAttribute("type");
                String attrName = attrElement.getAttribute("name");
                writer.write("    " + attrVisibility + " " + attrType + " " + attrName + ";\n");
            }
        }

        NodeList methodList = classElement.getElementsByTagName("Method");
        for (int i = 0; i < methodList.getLength(); i++) {
            Node methodNode = methodList.item(i);
            if (methodNode.getNodeType() == Node.ELEMENT_NODE) {
                Element methodElement = (Element) methodNode;
                String methodVisibility = methodElement.getAttribute("visibility");
                String methodType = methodElement.getAttribute("type");
                String methodName = methodElement.getAttribute("name");
                boolean methodAbstract = methodElement.getAttribute("abstract").equals("1");

                if (methodAbstract) {
                    writer.write("    " + methodVisibility + " abstract " + methodType + " " + methodName + "(");
                } else {
                    writer.write("    " + methodVisibility + " " + methodType + " " + methodName + "(");
                }

                NodeList paramList = methodElement.getElementsByTagName("Parameter");
                for (int j = 0; j < paramList.getLength(); j++) {
                    Node paramNode = paramList.item(j);
                    if (paramNode.getNodeType() == Node.ELEMENT_NODE) {
                        Element paramElement = (Element) paramNode;
                        String paramType = paramElement.getAttribute("type");
                        String paramName = paramElement.getAttribute("name");
                        if (!paramType.equals("None")) {
                            if (j > 0) {
                                writer.write(", ");
                            }
                            writer.write(paramType + " " + paramName);
                        }
                    }
                }
                writer.write(") {\n");
                if (!methodAbstract) {
                    writer.write("        // TODO: implement " + methodName + "\n");
                    writer.write("    }\n");
                } else {
                    writer.write("    }\n");
                }
            }
        }
        writer.write("}\n");
        writer.close();
    }
}
