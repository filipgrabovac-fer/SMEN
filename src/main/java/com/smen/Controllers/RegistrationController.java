package com.smen.Controllers;

import com.smen.Dto.Registration.RegistrationDto;
import com.smen.Models.Registration;
import com.smen.Services.RegistrationService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
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

    // Create a new registration
    @PostMapping("/new")
    public ResponseEntity<RegistrationDto> createRegistration(@RequestBody Registration registration) {
        RegistrationDto newRegistration = registrationService.createRegistration(registration);
        return ResponseEntity.status(HttpStatus.CREATED).body(newRegistration);
    }

    // Get registrations by user
    @GetMapping("/user/{id}")
    public ResponseEntity<List<RegistrationDto>> getRegistrationsByUser(@PathVariable Long id) {
        List<RegistrationDto> registrations = registrationService.getRegistrationsByUser(id);

        if (registrations.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(registrations);
    }

    // Get registrations by workshop
    @GetMapping("/workshop/{id}")
    public ResponseEntity<List<RegistrationDto>> getRegistrationsByWorkshop(@PathVariable Long id) {
        List<RegistrationDto> registrations = registrationService.getRegistrationsByWorkshop(id);

        if (registrations.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(registrations);
    }
}
