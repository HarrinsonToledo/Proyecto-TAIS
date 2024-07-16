package backend.demo.Controllers;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.stream.Collectors;

import javax.xml.XMLConstants;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.xml.sax.SAXException;

import com.fasterxml.jackson.databind.ObjectMapper;

import backend.demo.Logica.Estructura;
import backend.demo.Logica.LogicGenerateClass.GeneradorClass;
import backend.demo.Logica.LogicGenerateInterface.GeneradorInterface;
import backend.demo.Logica.LogicGenerateRoute.GeneradorRoute;
import backend.demo.Logica.ProcesarTexto.ProcessTextXML;

@RestController
@CrossOrigin(origins = "http://localhost:5173")
@RequestMapping("/xml")
public class XMLController {

    @PostMapping("/validar")
    String validar(@RequestParam("xml") MultipartFile xml, @RequestPart("jsonData") String jsonData)
            throws IOException, SAXException {

        try {
            Source source = new StreamSource(xml.getInputStream());
            File xsdFile = new File("Backend\\src\\main\\java\\backend\\demo\\Controllers\\entidadesProyecto.xsd");
            SchemaFactory schemaFactory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);

            Schema schema = schemaFactory.newSchema(xsdFile);
            Validator validator = schema.newValidator();
            validator.validate(source);

            String xmlContent = new BufferedReader(
                    new InputStreamReader(xml.getInputStream(), StandardCharsets.UTF_8))
                    .lines().collect(Collectors.joining("\n"));

            ObjectMapper objectMapper = new ObjectMapper();
            Estructura estructura = objectMapper.readValue(jsonData, Estructura.class);

            GenerateProject(xmlContent, estructura);
            return "Proceso Exitoso!!";
        } catch (SAXException e) {
            return "Error de validación XML: " + e.getMessage();
        }

    }

    public void GenerateProject(String xmlContent, Estructura estructura) {
        ProcessTextXML PXML = ProcessTextXML.getInstance();
        PXML.processXML(xmlContent);

        GeneradorController generador = new GeneradorController();

        generador.createSpringBootProjectStructure(estructura);
        ///

        ///

        if (PXML.getTextClass().size() > 0) {
            // GeneradorClass GC = GeneradorClass.getInstance();
            // GC.Generar(PXML.getTextClass());
        }
        if (PXML.getTextInterface().size() > 0) {
            GeneradorInterface GI = GeneradorInterface.getInstance();
            GI.Generar(PXML.getTextInterface());
        }
        if (PXML.getTextRoute().size() > 0) {
            GeneradorRoute GR = GeneradorRoute.getInstance();
            GR.setOutputDirectory("Backend/src/main/java/backend/demo/Logica/LogicGenerateRoute/");
            GR.Generar(PXML.getTextRoute());
        }
    }
}
