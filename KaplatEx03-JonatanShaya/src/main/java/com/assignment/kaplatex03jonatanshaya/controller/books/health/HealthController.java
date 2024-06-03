package com.assignment.kaplatex03jonatanshaya.controller.books.health;

import com.assignment.kaplatex03jonatanshaya.controller.Controller;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/books/health")
public class HealthController implements Controller {
    @GetMapping
    public ResponseEntity<String> health() {
        return ResponseEntity.status(HttpStatus.OK).body("OK");
    }
}
