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

    private RatingService ratingService;

    @GetMapping("/{id}")
    public ResponseEntity<Rating> getRating(@PathVariable Long id) {
        Optional<Rating> rating = ratingService.getById(id);

        return rating.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<Rating>> getRatings() {
        return ResponseEntity.ok(ratingService.getAll());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRating(@PathVariable Long id) {
        boolean isDeleted = ratingService.deleteById(id);

        if (isDeleted) {
            return ResponseEntity.status(HttpStatus.OK).build();
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @PostMapping("/new")
    public ResponseEntity<Rating> createRating(@RequestBody Rating rating) {
        HttpHeaders headers = new HttpHeaders(); //dodavanje poruke bez promjene oblika
        headers.add("Error-Message", "Rating must be between 1 and 5");

        if (rating.getRating() < 1 || rating.getRating() > 5) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).headers(headers).build();
        }

        Rating newRating = ratingService.create(rating);
        return ResponseEntity.status(HttpStatus.CREATED).body(newRating);
    }

    //sav rating za usera
    @GetMapping("/user/{id}/avg")
    public Double getUserAvgRating(@PathVariable Long id) {
        return ratingService.getAvgUserWorkshopsRating(id);
    }

    //rating za radionicu
    @GetMapping("/workshop/{id}/avg")
    public Double getWorkshopAvgRating(@PathVariable Long id) {
        return ratingService.getAvgWorkshopRating(id);
    }

    @GetMapping("/workshop/{id}/ratings")
    public List<Rating> getWorkshopRatings(@PathVariable Long id) {
        return ratingService.getWorkshopRatings(id);
    }

    @GetMapping("/user/{id}/ratings")
    public List<Rating> getUserRatings(@PathVariable Long id) {
        return ratingService.getUserRatings(id);
    }
}
