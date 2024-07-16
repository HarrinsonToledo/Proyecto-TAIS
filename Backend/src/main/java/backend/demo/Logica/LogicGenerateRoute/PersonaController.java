import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.catalina.connector.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
public class PersonaController {

private String datos;

@GetMapping("/misdatos/persona")
public void getDatos(@RequestParam String info) {
    // TODO: Add method implementation
}

}
