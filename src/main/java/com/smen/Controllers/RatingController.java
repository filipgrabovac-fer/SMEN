package com.smen.Controllers;

import com.smen.DTO.Rating.RatingDto;
import com.smen.Models.Rating;
import com.smen.Services.RatingService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@AllArgsConstructor
@RequestMapping("/api/rating")
public class RatingController {

    @Autowired
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

    @PostMapping("/new")
    public ResponseEntity<RatingDto> createRating(@RequestBody RatingDto ratingDto) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Error-Message", "Rating must be between 1 and 5");

        if (ratingDto.getRating() < 1 || ratingDto.getRating() > 5) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).headers(headers).build();
        }

        RatingDto createdRating = ratingService.createRating(ratingDto);

        if (createdRating == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();  // Invalid user/workshop IDs
        }

        return ResponseEntity.status(HttpStatus.CREATED).body(createdRating);
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
    public ResponseEntity<List<RatingDto>> getWorkshopRatings(@PathVariable Long id) {
        List<RatingDto> ratings = ratingService.getWorkshopRatings(id);

        if (ratings.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(ratings);
    }

    @GetMapping("/user/{id}/ratings")
    public ResponseEntity<List<RatingDto>> getUserRatings(@PathVariable Long id) {
        List<RatingDto> ratings = ratingService.getUserRatings(id);

        if (ratings.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(ratings);
    }
}
