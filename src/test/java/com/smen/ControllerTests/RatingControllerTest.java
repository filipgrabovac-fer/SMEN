package com.smen.ControllerTests;

import com.smen.Controllers.RatingController;
import com.smen.Models.Rating;
import com.smen.Services.RatingService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class RatingControllerTest {

    @Mock
    private RatingService ratingService;

    @InjectMocks
    private RatingController ratingController;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(ratingController).build();
    }

    @Test
    void getRating_ShouldReturnRating_WhenExists() throws Exception {
        Long ratingId = 1L;
        Rating rating = new Rating();
        rating.setId(ratingId);
        rating.setRating(4);
        rating.setComment("Great workshop!");

        when(ratingService.getByid(ratingId)).thenReturn(Optional.of(rating));

        mockMvc.perform(get("/api/rating/{id}", ratingId))
                .andExpect(status().isOk()) // Status OK (200)
                .andExpect(jsonPath("$.id").value(ratingId))
                .andExpect(jsonPath("$.rating").value(4))
                .andExpect(jsonPath("$.comment").value("Great workshop!"));
    }

    @Test
    void getRating_ShouldReturnNotFound_WhenDoesNotExist() throws Exception {
        Long ratingId = 1L;
        when(ratingService.getByid(ratingId)).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/rating/{id}", ratingId))
                .andExpect(status().isNotFound());
    }

    @Test
    void getRatings_ShouldReturnRatings_WhenExist() throws Exception {
        Rating rating1 = new Rating();
        rating1.setId(1L);
        rating1.setRating(5);
        rating1.setComment("Amazing!");

        Rating rating2 = new Rating();
        rating2.setId(2L);
        rating2.setRating(3);
        rating2.setComment("Good, but could be better");

        when(ratingService.getAll()).thenReturn(Arrays.asList(rating1, rating2));

        mockMvc.perform(get("/api/rating/"))
                .andExpect(status().isOk()) // Status OK (200)
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("[0].rating").value(5))
                .andExpect(jsonPath("[1].rating").value(3));
    }

    @Test
    void getRatings_ShouldReturnNoContent_WhenNoRatingsExist() throws Exception {
        when(ratingService.getAll()).thenReturn(Arrays.asList());

        mockMvc.perform(get("/api/rating/"))
                .andExpect(status().isNoContent());
    }

    @Test
    @WithMockUser(username = "user", roles = "USER")
    void createRating_ShouldReturnCreatedRating_WhenValidRating() throws Exception {
        Rating newRating = new Rating();
        newRating.setId(1L);
        newRating.setRating(4);
        newRating.setComment("Good workshop!");

        when(ratingService.create(any(Rating.class))).thenReturn(newRating);

        mockMvc.perform(post("/api/rating/new")
                        .contentType("application/json")
                        .content("{\"rating\": 4, \"comment\": \"Good workshop!\"}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.rating").value(4))
                .andExpect(jsonPath("$.comment").value("Good workshop!"));
    }

    @Test
    @WithMockUser(username = "user", roles = "USER")
    void createRating_ShouldReturnBadRequest_WhenInvalidRating() throws Exception {
        Rating invalidRating = new Rating();
        invalidRating.setRating(6);
        invalidRating.setComment("Not good!");

        mockMvc.perform(post("/api/rating/new")
                        .contentType("application/json")
                        .content("{\"rating\": 6, \"comment\": \"Not good!\"}"))
                .andExpect(status().isBadRequest())
                .andExpect(header().string("Error-Message", "Rating must be between 1 and 5"));
    }

    @Test
    @WithMockUser(username = "admin", roles = "ADMIN")
    void deleteRating_ShouldReturnOk_WhenRatingDeleted() throws Exception {
        Long ratingId = 1L;
        when(ratingService.deleteById(ratingId)).thenReturn(true);

        mockMvc.perform(delete("/api/rating/{id}", ratingId))
                .andExpect(status().isOk())
                .andExpect(content().string("Rating deleted successfully"));
    }

    @Test
    @WithMockUser(username = "admin", roles = "ADMIN")
    void deleteRating_ShouldReturnNotFound_WhenRatingNotFound() throws Exception {
        Long ratingId = 1L;
        when(ratingService.deleteById(ratingId)).thenReturn(false);

        mockMvc.perform(delete("/api/rating/{id}", ratingId))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Rating not found"));
    }

    @Test
    void getUserRating_ShouldReturnAverageRating_WhenRatingsExist() throws Exception {
        Long userId = 1L;
        Double averageRating = 4.5;

        when(ratingService.getAvgUserWorkshopsRating(userId)).thenReturn(averageRating);

        mockMvc.perform(get("/api/rating/user/{id}/rating", userId))
                .andExpect(status().isOk())
                .andExpect(content().string(averageRating.toString())); // Provjera da je odgovor ispravan
    }

    @Test
    void getUserRating_ShouldReturnZero_WhenNoRatingsExist() throws Exception {
        Long userId = 1L;

        when(ratingService.getAvgUserWorkshopsRating(userId)).thenReturn(0.0);

        mockMvc.perform(get("/api/rating/user/{id}/rating", userId))
                .andExpect(status().isOk())
                .andExpect(content().string("0.0")); // Očekujemo da vrati 0.0
    }


    @Test
    @WithMockUser(username = "admin", roles = "ADMIN")
    void getWorkshopRating_ShouldReturnAverageRating_WhenRatingsExist() throws Exception {
        Long workshopId = 1L;
        Double avgRating = 4.5;
        when(ratingService.getAvgWorkshopRating(workshopId)).thenReturn(avgRating);

        mockMvc.perform(get("/api/rating/workshop/{id}/rating", workshopId))
                .andExpect(status().isOk())
                .andExpect(content().string("4.5"));
    }

    @Test
    void getWorkshopRating_ShouldReturnZero_WhenNoRatingsExist() throws Exception {
        Long workshopId = 1L;

        when(ratingService.getAvgWorkshopRating(workshopId)).thenReturn(0.0);

        mockMvc.perform(get("/api/rating/workshop/{id}/rating", workshopId))
                .andExpect(status().isOk()) // Očekujemo status 200 OK
                .andExpect(content().string("0.0")); // Očekujemo da se vrati rating 0.0
    }

}
