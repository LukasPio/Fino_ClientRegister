package clientregister.com.lucas.Controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/api/client")
public class ClientController {
    @GetMapping(path = "/hello")
    public ResponseEntity<String> helloWorld() {
        System.out.println("get mapping");
        return ResponseEntity.status(HttpStatus.OK).body("Augusto é gay demais mesmo");
    }

}
