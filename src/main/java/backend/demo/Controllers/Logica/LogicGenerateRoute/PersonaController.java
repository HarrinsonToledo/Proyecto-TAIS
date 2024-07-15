@CrossOrigin(origins = "http://localhost:5173")
@RestController
public class PersonaController {

private String datos;

@GetMapping("/misdatos/persona")
public void getDatos(@RequestParam String info) {
    // TODO: Add method implementation
}

}
