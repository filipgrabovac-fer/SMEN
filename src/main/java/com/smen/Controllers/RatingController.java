package com.smen.Controllers;

import com.smen.Models.Rating;
import com.smen.Services.RatingService;
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
@RequestMapping("/api/rating")
public class RatingController {

    private RatingService service;

    @GetMapping("/{id}")
    public ResponseEntity<Rating> getRating(@PathVariable Long id) {
        Optional<Rating> rating = service.getByid(id);

        return rating.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/")
    public ResponseEntity<List<Rating>> getRatings() {
        List<Rating> ratings = service.getAll();
        if (ratings.isEmpty())
            return ResponseEntity.noContent().build();
        else
            return ResponseEntity.ok(ratings);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> deleteRating(@PathVariable Long id) {
        boolean isDeleted = service.deleteById(id);

        if (isDeleted) {
            return ResponseEntity.status(HttpStatus.OK).body("Rating deleted successfully");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Rating not found");
        }
    }

    @PostMapping("/new")
    @PreAuthorize("!hasRole('MENTOR')")
    public ResponseEntity<Rating> createRating(@RequestBody Rating rating) {
        HttpHeaders headers = new HttpHeaders(); //dodavanje poruke bez promjene oblika
        headers.add("Error-Message", "Rating must be between 1 and 5");

        if (rating.getRating() < 1 || rating.getRating() > 5) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).headers(headers).build();
        }

        Rating newRating = service.create(rating);
        return ResponseEntity.status(HttpStatus.CREATED).body(newRating);
    }

    //sav rating za usera
    @GetMapping("/user/{id}/rating")
    @PreAuthorize("hasRole('ADMIN')")
    public Double getUserRating(@PathVariable Long id) {
        return service.getAvgUserWorkshopsRating(id);
    }

    //rating za radionicu
    @GetMapping("/workshop/{id}/rating")
    @PreAuthorize("hasRole('ADMIN') or hasRole('MENTOR')")
    public Double getWorkshopRating(@PathVariable Long id) {
        return service.getAvgWorkshopRating(id);
    }
}
