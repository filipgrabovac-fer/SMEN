package com.smen.Controllers;

import com.smen.Models.Proposition;
import com.smen.Services.PropositionService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@AllArgsConstructor
@RequestMapping("/api/proposition")
public class PropositionController {

    private PropositionService service;

    @GetMapping("/{id}")
    public ResponseEntity<Proposition> getProposition(@PathVariable Long id) {
        Optional<Proposition> proposition = service.getByid(id);

        return proposition.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/")
    public ResponseEntity<List<Proposition>> getProposition() {
        List<Proposition> propositions = service.getAll();
        if (propositions.isEmpty())
            return ResponseEntity.noContent().build();
        else
            return ResponseEntity.ok(propositions);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('MENTOR')")
    public ResponseEntity<String> deleteProposition(@PathVariable Long id) {
        boolean isDeleted = service.deleteById(id);

        if (isDeleted) {
            return ResponseEntity.status(HttpStatus.OK).body("Proposition deleted successfully");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Proposition not found");
        }
    }


    //svi mogu stvarati oglase
    @PostMapping("/new")
    public ResponseEntity<Proposition> createProposition(@RequestBody Proposition proposition) {
        Proposition newProposition = service.create(proposition);
        return ResponseEntity.status(HttpStatus.CREATED).body(newProposition);
    }
}
