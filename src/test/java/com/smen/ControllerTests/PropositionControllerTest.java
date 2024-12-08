package com.smen.ControllerTests;

import com.smen.Controllers.PropositionController;
import com.smen.Models.Proposition;
import com.smen.Models.Subject;
import com.smen.Services.PropositionService;
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

class PropositionControllerTest {

    @Mock
    private PropositionService propositionService;

    @InjectMocks
    private PropositionController propositionController;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(propositionController).build();
    }

    @Test
    void getProposition_ShouldReturnProposition_WhenExists() throws Exception {
        Long propositionId = 1L;
        Proposition proposition = new Proposition();
        proposition.setId(propositionId);
        proposition.setTitle("Spring Workshop");
        proposition.setDescription("Spring Boot and Microservices");
        proposition.setTags("Spring, Boot");
        proposition.setApproved(true);

        Subject subject = new Subject();
        subject.setId(1L);
        subject.setTitle("Software Engineering");
        proposition.setSubject(subject);

        when(propositionService.getByid(propositionId)).thenReturn(Optional.of(proposition));

        mockMvc.perform(get("/api/proposition/{id}", propositionId))
                .andExpect(status().isOk())  // Status OK (200)
                .andExpect(jsonPath("$.id").value(propositionId))
                .andExpect(jsonPath("$.title").value("Spring Workshop"))
                .andExpect(jsonPath("$.subject.title").value("Software Engineering"));
    }

    @Test
    void getProposition_ShouldReturnNotFound_WhenDoesNotExist() throws Exception {
        Long propositionId = 1L;
        when(propositionService.getByid(propositionId)).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/proposition/{id}", propositionId))
                .andExpect(status().isNotFound());  // Status Not Found (404)
    }

    @Test
    void getPropositions_ShouldReturnPropositions_WhenExist() throws Exception {
        Proposition proposition1 = new Proposition();
        proposition1.setId(1L);
        proposition1.setTitle("Spring Workshop");
        proposition1.setDescription("Spring Boot and Microservices");
        proposition1.setTags("Spring, Boot");
        proposition1.setApproved(true);

        Proposition proposition2 = new Proposition();
        proposition2.setId(2L);
        proposition2.setTitle("Java Basics");
        proposition2.setDescription("Introduction to Java");
        proposition2.setTags("Java, Basics");
        proposition2.setApproved(false);

        when(propositionService.getAll()).thenReturn(Arrays.asList(proposition1, proposition2));

        mockMvc.perform(get("/api/proposition/"))
                .andExpect(status().isOk())  // Status OK (200)
                .andExpect(jsonPath("$.length()").value(2))  // Provjera broja rezultata
                .andExpect(jsonPath("[0].title").value("Spring Workshop"))
                .andExpect(jsonPath("[1].title").value("Java Basics"));
    }

    @Test
    void getPropositions_ShouldReturnNoContent_WhenNoPropositionsExist() throws Exception {
        when(propositionService.getAll()).thenReturn(Arrays.asList());

        mockMvc.perform(get("/api/proposition/"))
                .andExpect(status().isNoContent());
    }

    @Test
    @WithMockUser(username = "user", roles = "USER")
    void createProposition_ShouldReturnCreated_WhenPropositionIsCreated() throws Exception {
        Proposition newProposition = new Proposition();
        newProposition.setId(1L);
        newProposition.setTitle("New Workshop");
        newProposition.setDescription("Learn new skills");
        newProposition.setTags("New, Workshop");
        newProposition.setApproved(true);

        when(propositionService.create(any(Proposition.class))).thenReturn(newProposition);

        mockMvc.perform(post("/api/proposition/new")
                        .contentType("application/json")
                        .content("{\"title\": \"New Workshop\", \"description\": \"Learn new skills\", \"tags\": \"New, Workshop\", \"approved\": true}"))
                .andExpect(status().isCreated())  // Status Created (201)
                .andExpect(jsonPath("$.title").value("New Workshop"));
    }

    @Test
    @WithMockUser(username = "admin", roles = "ADMIN")
    void deleteProposition_ShouldReturnOk_WhenPropositionDeleted() throws Exception {
        Long propositionId = 1L;
        when(propositionService.deleteById(propositionId)).thenReturn(true);

        mockMvc.perform(delete("/api/proposition/{id}", propositionId))
                .andExpect(status().isOk())
                .andExpect(content().string("Proposition deleted successfully"));
    }

    @Test
    @WithMockUser(username = "admin", roles = "ADMIN")
    void deleteProposition_ShouldReturnNotFound_WhenPropositionNotFound() throws Exception {
        Long propositionId = 1L;
        when(propositionService.deleteById(propositionId)).thenReturn(false);

        mockMvc.perform(delete("/api/proposition/{id}", propositionId))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Proposition not found"));
    }
}
