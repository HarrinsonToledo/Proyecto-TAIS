package backend.demo.Controllers;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.Spring;

import backend.demo.Logica.Estructura;

public class GeneradorController {

    public static void createSpringBootProjectStructure(Estructura estructura) {
        String baseDir = "Backend/src/Result/" + estructura.nameProject;
        new File(baseDir).mkdirs();

        // Creating directories
        String[] directories = {
                baseDir + "/src/main/java/" + estructura.namePacke.replace('.', '/'),
                baseDir + "/src/main/resources",
                baseDir + "/src/test/java/" + estructura.namePacke.replace('.', '/'),
                baseDir + "/src/test/resources",
                baseDir + "/target/classes/" + estructura.namePacke.replace('.', '/'),
                baseDir + "/target/test-classes/" + estructura.namePacke.replace('.', '/'),
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
        createFile(baseDir + "/HELP.md", "# Getting Started\n\n" +
                "### Reference Documentation\n" +
                "For further reference, please consider the following sections:\n\n" +
                "* [Official Apache Maven documentation](https://maven.apache.org/guides/index.html)\n" +
                "* [Spring Boot Maven Plugin Reference Guide](https://docs.spring.io/spring-boot/docs/3.3.2-SNAPSHOT/maven-plugin/reference/html/)\n"
                +
                "* [Create an OCI image](https://docs.spring.io/spring-boot/docs/3.3.2-SNAPSHOT/maven-plugin/reference/html/#build-image)\n\n"
                +
                "### Maven Parent overrides\n\n" +
                "Due to Maven's design, elements are inherited from the parent POM to the project POM.\n" +
                "While most of the inheritance is fine, it also inherits unwanted elements like `<license>` and `<developers>` from the parent.\n"
                +
                "To prevent this, the project POM contains empty overrides for these elements.\n" +
                "If you manually switch to a different parent and actually want the inheritance, you need to remove those overrides.");

        // Creating mvnw and mvnw.cmd
        createFile(baseDir + "/mvnw", generateMvnwContent());
        createFile(baseDir + "/mvnw.cmd", generateMvnwCmdContent());

        // Creating the main Java class
        String mainClassContent = generateMainClassContent(estructura);
        String mainClassPath = baseDir + "/src/main/java/" + estructura.namePacke.replace('.', '/') + "/"
                + capitalizeFirstLetter(estructura.nameProject) + "Application.java";
        createFile(mainClassPath, mainClassContent);

        // Creating the test class
        createTestClass(baseDir, estructura);

        // Simulating the target folder content
        createTargetFolderContent(baseDir, estructura);

        // Creating the maven-wrapper.properties file
        String wrapperPropertiesContent = generateMavenWrapperPropertiesContent();
        createFile(baseDir + "/.mvn/maven-wrapper.properties", wrapperPropertiesContent);

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
                "    <url/>\n" +
                "    <licenses>\n" +
                "        <license/>\n" +
                "    </licenses>\n" +
                "    <developers>\n" +
                "        <developer/>\n" +
                "    </developers>\n" +
                "    <scm>\n" +
                "        <connection/>\n" +
                "        <developerConnection/>\n" +
                "        <tag/>\n" +
                "        <url/>\n" +
                "    </scm>\n" +
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
                "         <dependency>\n" +
                "            <groupId>org.springframework.boot</groupId>\n" +
                "            <artifactId>spring-boot-starter-web</artifactId>\n" +
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
                "    <repositories>\n" +
                "       <repository>\n" +
                "           <id>spring-snapshots</id>\n" +
                "           <name>Spring Snapshots</name>\n" +
                "           <url>https://repo.spring.io/snapshot</url>\n" +
                "           <releases>\n" +
                "               <enabled>false</enabled>\n" +
                "           </releases>\n" +
                "       </repository>\n" +
                "     </repositories>\n" +
                "     <pluginRepositories>\n" +
                "       <pluginRepository>\n" +
                "           <id>spring-snapshots</id>\n" +
                "           <name>Spring Snapshots</name>\n" +
                "           <url>https://repo.spring.io/snapshot</url>\n" +
                "           <releases>\n" +
                "               <enabled>false</enabled>\n" +
                "           </releases>\n" +
                "       </pluginRepository>\n" +
                "     </pluginRepositories>\n" +
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
                ".vscode/";
    }

    private static String generateMvnwContent() {
        return "#!/bin/sh\n" +
                "set -e\n" +
                "MAVEN_HOME=\"$(cd \"$(dirname \"$0\")\" >/dev/null 2>&1 and pwd)/.mvn\"\n" +
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

    private static void createTestClass(String baseDir, Estructura estructura) {
        String testClassName = capitalizeFirstLetter(estructura.nameProject) + "ApplicationTests";
        String testClassContent = "package " + estructura.namePacke + ";\n\n" +
                "import org.junit.jupiter.api.Test;\n" +
                "import org.springframework.boot.test.context.SpringBootTest;\n\n" +
                "@SpringBootTest\n" +
                "public class " + testClassName + " {\n\n" +
                "    @Test\n" +
                "    public void contextLoads() {\n" +
                "    }\n" +
                "}\n";
        String testClassPath = baseDir + "/src/test/java/" + estructura.namePacke.replace('.', '/') + "/"
                + testClassName + ".java";
        createFile(testClassPath, testClassContent);
    }

    private static void createTargetFolderContent(String baseDir, Estructura estructura) {
        String classesDir = baseDir + "/target/classes/" + estructura.namePacke.replace('.', '/');
        String testClassesDir = baseDir + "/target/test-classes/" + estructura.namePacke.replace('.', '/');

        // Creating the files in classes directory
        createFile(classesDir + "/" + capitalizeFirstLetter(estructura.nameProject) + "Application.class", "");

        // Creating the application.properties file at the same level as classes
        // directory
        createFile(baseDir + "/target/classes/application.properties", "");

        // Creating the files in test-classes directory
        createFile(testClassesDir + "/" + capitalizeFirstLetter(estructura.nameProject) + "ApplicationTests.class", "");
    }

    private static String generateMavenWrapperPropertiesContent() {
        return "# Licensed to the Apache Software Foundation (ASF) under one\n" +
                "# or more contributor license agreements.  See the NOTICE file\n" +
                "# distributed with this work for additional information\n" +
                "# regarding copyright ownership.  The ASF licenses this file\n" +
                "# to you under the Apache License, Version 2.0 (the\n" +
                "# \"License\"); you may not use this file except in compliance\n" +
                "# with the License.  You may obtain a copy of the License at\n" +
                "#\n" +
                "#   https://www.apache.org/licenses/LICENSE-2.0\n" +
                "#\n" +
                "# Unless required by applicable law or agreed to in writing,\n" +
                "# software distributed under the License is distributed on an\n" +
                "# \"AS IS\" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY\n" +
                "# KIND, either express or implied.  See the License for the\n" +
                "# specific language governing permissions and limitations\n" +
                "# under the License.\n" +
                "wrapperVersion=3.3.2\n" +
                "distributionType=only-script\n" +
                "distributionUrl=https://repo.maven.apache.org/maven2/org/apache/maven/apache-maven/3.9.7/apache-maven-3.9.7-bin.zip\n";
    }
}