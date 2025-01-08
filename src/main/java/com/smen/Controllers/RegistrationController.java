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

    private RegistrationService registrationService;

    @GetMapping
    public ResponseEntity<List<Registration>> getRegistrations() {
        return ResponseEntity.ok(registrationService.getAll());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRegistration(@PathVariable Long id) {
        boolean isDeleted = registrationService.deleteById(id);

        if (isDeleted) {
            return ResponseEntity.status(HttpStatus.OK).build();
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @PostMapping("/new")
    public ResponseEntity<Registration> createRegistration(@RequestBody Registration Registration) {
        Registration newRegistration = registrationService.create(Registration);
        return ResponseEntity.status(HttpStatus.CREATED).body(newRegistration);
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<List<Registration>> getRegistrationsByUser(@PathVariable Long id) {
        return ResponseEntity.ok(registrationService.getRegistrationsByUser(id));
    }

    @GetMapping("/workshop/{id}")
    public ResponseEntity<List<Registration>> getRegistrationsByWorkshop(@PathVariable Long id) {
        return ResponseEntity.ok(registrationService.getRegistrationsByWorkshop(id));
    }
}
