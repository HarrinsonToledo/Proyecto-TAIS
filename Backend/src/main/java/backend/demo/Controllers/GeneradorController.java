package backend.demo.Controllers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.catalina.connector.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import backend.demo.Logica.Estructura;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
public class GeneradorController {

    @PostMapping("/crearProyecto")
    ResponseEntity<Map<String, Object>> generarProyecto(@RequestBody Estructura est) {
        try {
            return null;
        } catch (Exception e) {
            return ResponseEntity.status(400).body(Map.of("message", e.getCause().getMessage()));
        }
    }
}