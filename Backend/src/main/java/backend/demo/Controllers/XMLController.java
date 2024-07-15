package backend.demo.Controllers;

import java.io.File;
import java.io.IOException;

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

            return "XML valido";
        } catch (SAXException e) {
            return "Error de validación XML: " + e.getMessage();
        }

        /*
         * String xmlContent = new BufferedReader(
         * new InputStreamReader(xmlFile.getInputStream(), StandardCharsets.UTF_8))
         * .lines()
         * .collect(Collectors.joining("\n"));
         */
    }
}
