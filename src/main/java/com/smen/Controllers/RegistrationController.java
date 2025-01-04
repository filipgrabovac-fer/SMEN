package com.smen.Controllers;

import com.smen.Models.Registration;
import com.smen.Services.RegistrationService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@AllArgsConstructor
@RequestMapping("/api/registration")
public class RegistrationController {

    private RegistrationService service;

    @GetMapping("/{id}")
    public ResponseEntity<Registration> getRegistration(@PathVariable Long id) {
        Optional<Registration> registration = service.getById(id);

        return registration.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/")
    public ResponseEntity<List<Registration>> getRegistrations() {
        List<Registration> registrations = service.getAll();
        if (registrations.isEmpty())
            return ResponseEntity.noContent().build();
        else
            return ResponseEntity.ok(registrations);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteRegistration(@PathVariable Long id) {
        boolean isDeleted = service.deleteById(id);

        if (isDeleted) {
            return ResponseEntity.status(HttpStatus.OK).body("Registration deleted successfully");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Registration not found");
        }
    }

    @PostMapping("/new")
    public ResponseEntity<Registration> createRegistration(@RequestBody Registration Registration) {
        HttpHeaders headers = new HttpHeaders(); //dodavanje poruke bez promjene oblika
        headers.add("Error-Message", "Registration must be between 1 and 5");

        Registration newRegistration = service.create(Registration);
        return ResponseEntity.status(HttpStatus.CREATED).body(newRegistration);
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<List<Registration>> getRegistrationsByUser(@PathVariable Long id) {
        List<Registration> registrations = service.getByUser(id);
        if (registrations.isEmpty())
            return ResponseEntity.noContent().build();
        else
            return ResponseEntity.ok(registrations);
    }

    @GetMapping("/workshop/{id}")
    public ResponseEntity<List<Registration>> getRegistrationsByWorkshop(@PathVariable Long id) {
        List<Registration> registrations = service.getByUser(id);
        if (registrations.isEmpty())
            return ResponseEntity.noContent().build();
        else
            return ResponseEntity.ok(registrations);
    }
}
