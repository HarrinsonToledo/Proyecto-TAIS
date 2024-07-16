package backend.demo.Controllers;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import backend.demo.Logica.Estructura;

public class GeneradorController {

    public static void createSpringBootProjectStructure(Estructura estructura) {
        String baseDir = "Resultados/" + estructura.nameProject;
        new File(baseDir).mkdirs();

        // Creating directories
        String[] directories = {
                baseDir + "/src/main/java/" + estructura.namePacke.replace('.', '/'),
                baseDir + "/src/main/resources",
                baseDir + "/src/test/java/" + estructura.namePacke.replace('.', '/'),
                baseDir + "/src/test/resources",
                baseDir + "/target",
                baseDir + "/.mvn"
        };

        for (String dir : directories) {
            new File(dir).mkdirs();
        }

        // Creating application.properties
        createFile(baseDir + "/src/main/resources/application.properties", "");

        // Creating pom.xml
        String pomContent = generatePomContent(estructura);
        createFile(baseDir + "/pom.xml", pomContent);

        // Creating .gitignore
        String gitignoreContent = generateGitignoreContent();
        createFile(baseDir + "/.gitignore", gitignoreContent);

        // Creating HELP.md
        createFile(baseDir + "/HELP.md", "This is a Spring Boot project.");

        // Creating mvnw and mvnw.cmd
        createFile(baseDir + "/mvnw", generateMvnwContent());
        createFile(baseDir + "/mvnw.cmd", generateMvnwCmdContent());

        // Creating the main Java class
        String mainClassContent = generateMainClassContent(estructura);
        String mainClassPath = baseDir + "/src/main/java/" + estructura.namePacke.replace('.', '/') + "/"
                + capitalizeFirstLetter(estructura.nameProject) + "Application.java";
        createFile(mainClassPath, mainClassContent);

        System.out.println("Spring Boot project structure generated successfully.");
    }

    private static void createFile(String path, String content) {
        try (FileWriter fileWriter = new FileWriter(path)) {
            fileWriter.write(content);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static String generatePomContent(Estructura estructura) {
        return "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                "<project xmlns=\"http://maven.apache.org/POM/4.0.0\"\n" +
                "         xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"\n" +
                "         xsi:schemaLocation=\"http://maven.apache.org/POM/4.0.0 https://maven.apache.org/xsd/maven-4.0.0.xsd\">\n"
                +
                "    <modelVersion>4.0.0</modelVersion>\n" +
                "    <parent>\n" +
                "        <groupId>org.springframework.boot</groupId>\n" +
                "        <artifactId>spring-boot-starter-parent</artifactId>\n" +
                "        <version>" + estructura.spring + "</version>\n" +
                "        <relativePath/> <!-- lookup parent from repository -->\n" +
                "    </parent>\n" +
                "    <groupId>" + estructura.group + "</groupId>\n" +
                "    <artifactId>" + estructura.artifact + "</artifactId>\n" +
                "    <version>0.0.1-SNAPSHOT</version>\n" +
                "    <name>" + estructura.nameProject + "</name>\n" +
                "    <description>" + estructura.description + "</description>\n" +
                "<url/>" +
                "<licenses>\n" +
                "<license/>\n" +
                "</licenses>\n" +
                "<developers>\n" +
                "<developer/>\n" +
                "</developers>\n" +
                "<scm>\n" +
                "<connection/>\n" +
                "<developerConnection/>\n" +
                "<tag/>\n" +
                "<url/>\n" +
                "</scm>\n" +
                "    <properties>\n" +
                "        <java.version>" + estructura.java + "</java.version>\n" +
                "    </properties>\n" +
                "    <dependencies>\n" +
                "        <dependency>\n" +
                "            <groupId>org.springframework.boot</groupId>\n" +
                "            <artifactId>spring-boot-starter</artifactId>\n" +
                "        </dependency>\n" +
                "        <dependency>\n" +
                "            <groupId>org.springframework.boot</groupId>\n" +
                "            <artifactId>spring-boot-starter-test</artifactId>\n" +
                "            <scope>test</scope>\n" +
                "        </dependency>\n" +
                "    </dependencies>\n" +
                "    <build>\n" +
                "        <plugins>\n" +
                "            <plugin>\n" +
                "                <groupId>org.springframework.boot</groupId>\n" +
                "                <artifactId>spring-boot-maven-plugin</artifactId>\n" +
                "            </plugin>\n" +
                "        </plugins>\n" +
                "    </build>\n" +
                "</project>";
    }

    private static String generateGitignoreContent() {
        return ".mvn/\n" +
                "*.class\n" +
                "*.jar\n" +
                "*.war\n" +
                "*.ear\n" +
                "target/\n" +
                "*.log\n" +
                ".idea/\n" +
                "*.iml\n" +
                "*.ipr\n" +
                "*.iws\n" +
                ".project\n" +
                ".settings/\n" +
                ".vscode/\n";
    }

    private static String generateMvnwContent() {
        return "#!/bin/sh\n" +
                "set -e\n" +
                "MAVEN_HOME=\"$(cd \"$(dirname \"$0\")\" >/dev/null 2>&1 && pwd)/.mvn\"\n" +
                "exec \"$MAVEN_HOME/wrapper/mvnw\" \"$@\"\n";
    }

    private static String generateMvnwCmdContent() {
        return "@echo off\n" +
                "setlocal\n" +
                "set MAVEN_HOME=%~dp0\\.mvn\n" +
                "cmd /C \"%MAVEN_HOME%\\wrapper\\mvnw.cmd\" %*\n";
    }

    private static String generateMainClassContent(Estructura estructura) {
        String className = capitalizeFirstLetter(estructura.nameProject) + "Application";
        return "package " + estructura.namePacke + ";\n\n" +
                "import org.springframework.boot.SpringApplication;\n" +
                "import org.springframework.boot.autoconfigure.SpringBootApplication;\n\n" +
                "@SpringBootApplication\n" +
                "public class " + className + " {\n\n" +
                "    public static void main(String[] args) {\n" +
                "        SpringApplication.run(" + className + ".class, args);\n" +
                "    }\n" +
                "}\n";
    }

    private static String capitalizeFirstLetter(String str) {
        if (str == null || str.isEmpty()) {
            return str;
        }
        return Character.toUpperCase(str.charAt(0)) + str.substring(1);
    }
}