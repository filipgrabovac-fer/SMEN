package com.smen.ControllerTests;

import com.smen.Controllers.WorkshopController;
import com.smen.Models.Workshop;
import com.smen.Services.WorkshopService;
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
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class WorkshopControllerTest {

    @Mock
    private WorkshopService workshopService;

    @InjectMocks
    private WorkshopController workshopController;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(workshopController).build();
    }

    @Test
    void getWorkshop_ShouldReturnWorkshop_WhenExists() throws Exception {
        Long workshopId = 1L;
        Workshop workshop = new Workshop();
        workshop.setId(workshopId);
        workshop.setTitle("Spring Workshop");
        workshop.setDescription("Learn Spring Boot");
        workshop.setDuration(3);
        workshop.setNoOfAvailableSlots(5);

        when(workshopService.getByid(workshopId)).thenReturn(Optional.of(workshop));

        mockMvc.perform(get("/api/workshop/{id}", workshopId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(workshopId))
                .andExpect(jsonPath("$.title").value("Spring Workshop"));
    }

    @Test
    void getWorkshop_ShouldReturnNotFound_WhenDoesNotExist() throws Exception {
        Long workshopId = 1L;
        when(workshopService.getByid(workshopId)).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/workshop/{id}", workshopId))
                .andExpect(status().isNotFound());
    }

    @Test
    void getWorkshops_ShouldReturnWorkshops_WhenExist() throws Exception {
        Workshop workshop1 = new Workshop();
        workshop1.setId(1L);
        workshop1.setTitle("Spring Workshop");
        workshop1.setDescription("Learn Spring Boot");
        workshop1.setDuration(3);
        workshop1.setNoOfAvailableSlots(5);

        Workshop workshop2 = new Workshop();
        workshop2.setId(2L);
        workshop2.setTitle("Java Basics");
        workshop2.setDescription("Introduction to Java");
        workshop2.setDuration(2);
        workshop2.setNoOfAvailableSlots(3);

        when(workshopService.getAll()).thenReturn(Arrays.asList(workshop1, workshop2));

        mockMvc.perform(get("/api/workshop/"))
                .andExpect(status().isOk())  // Status OK (200)
                .andExpect(jsonPath("$.length()").value(2))  // Provjera broja rezultata
                .andExpect(jsonPath("[0].title").value("Spring Workshop"))
                .andExpect(jsonPath("[1].title").value("Java Basics"));
    }

    @Test
    void getWorkshops_ShouldReturnNoContent_WhenNoWorkshopsExist() throws Exception {
        when(workshopService.getAll()).thenReturn(Arrays.asList());

        mockMvc.perform(get("/api/workshop/"))
                .andExpect(status().isNoContent());
    }

    @Test
    @WithMockUser(username = "admin", roles = "ADMIN")
    void createWorkshop_ShouldReturnCreated_WhenWorkshopIsCreated() throws Exception {
        Workshop newWorkshop = new Workshop();
        newWorkshop.setId(1L);
        newWorkshop.setTitle("New Spring Workshop");
        newWorkshop.setDescription("Advanced Spring Boot");
        newWorkshop.setDuration(4);
        newWorkshop.setNoOfAvailableSlots(10);

        when(workshopService.create(any(Workshop.class))).thenReturn(newWorkshop);

        mockMvc.perform(post("/api/workshop/new")
                        .contentType("application/json")
                        .content("{\"title\": \"New Spring Workshop\", \"description\": \"Advanced Spring Boot\", \"duration\": 4, \"noOfAvailableSlots\": 10}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.title").value("New Spring Workshop"));
    }

    @Test
    @WithMockUser(username = "admin", roles = "ADMIN")
    void deleteWorkshop_ShouldReturnOk_WhenWorkshopDeleted() throws Exception {
        Long workshopId = 1L;
        when(workshopService.deleteById(workshopId)).thenReturn(true);

        mockMvc.perform(delete("/api/workshop/{id}", workshopId))
                .andExpect(status().isOk())
                .andExpect(content().string("Workshop deleted successfully"));
    }

    @Test
    @WithMockUser(username = "admin", roles = "ADMIN")
    void deleteWorkshop_ShouldReturnNotFound_WhenWorkshopNotFound() throws Exception {
        Long workshopId = 1L;
        when(workshopService.deleteById(workshopId)).thenReturn(false);

        mockMvc.perform(delete("/api/workshop/{id}", workshopId))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Workshop not found"));
    }

    @Test
    @WithMockUser(username = "admin", roles = "ADMIN")
    void updateWorkshop_ShouldReturnUpdatedWorkshop_WhenExists() throws Exception {
        Long workshopId = 1L;
        Workshop existingWorkshop = new Workshop();
        existingWorkshop.setId(workshopId);
        existingWorkshop.setTitle("Old Workshop");

        Workshop updatedWorkshop = new Workshop();
        updatedWorkshop.setId(workshopId);
        updatedWorkshop.setTitle("Updated Workshop");

        when(workshopService.updateWorkshop(eq(workshopId), any(Workshop.class))).thenReturn(updatedWorkshop);

        mockMvc.perform(put("/api/workshop/{id}", workshopId)
                        .contentType("application/json")
                        .content("{\"title\": \"Updated Workshop\"}"))
                .andExpect(status().isOk())  // Status OK (200)
                .andExpect(jsonPath("$.title").value("Updated Workshop"));
    }

    @Test
    @WithMockUser(username = "admin", roles = "ADMIN")
    void updateWorkshop_ShouldReturnNotFound_WhenWorkshopDoesNotExist() throws Exception {
        Long workshopId = 1L;
        Workshop workshop = new Workshop();
        workshop.setId(workshopId);

        when(workshopService.updateWorkshop(eq(workshopId), any(Workshop.class))).thenReturn(null);

        mockMvc.perform(put("/api/workshop/{id}", workshopId)
                        .contentType("application/json")
                        .content("{\"title\": \"Non-existent Workshop\"}"))
                .andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser(username = "admin", roles = "ADMIN")
    void getWorkshopsByUser_ShouldReturnWorkshops_WhenUserHasWorkshops() throws Exception {
        Long userId = 1L;
        Workshop workshop1 = new Workshop();
        workshop1.setId(1L);
        workshop1.setTitle("User Workshop");

        when(workshopService.getByUser(userId)).thenReturn(Arrays.asList(workshop1));

        mockMvc.perform(get("/api/workshop/user/{id}", userId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("[0].title").value("User Workshop"));
    }

    @Test
    @WithMockUser(username = "admin", roles = "ADMIN")
    void getWorkshopsByUser_ShouldReturnNoContent_WhenUserHasNoWorkshops() throws Exception {
        Long userId = 1L;
        when(workshopService.getByUser(userId)).thenReturn(Arrays.asList());

        mockMvc.perform(get("/api/workshop/user/{id}", userId))
                .andExpect(status().isNoContent());
    }

    @Test
    void getWorkshopsBySubject_ShouldReturnWorkshops_WhenExist() throws Exception {
        Long subjectId = 1L;

        Workshop workshop1 = new Workshop();
        workshop1.setId(1L);
        workshop1.setTitle("Spring Boot Basics");

        Workshop workshop2 = new Workshop();
        workshop2.setId(2L);
        workshop2.setTitle("Advanced Spring Boot");

        when(workshopService.getBySubject(subjectId)).thenReturn(Arrays.asList(workshop1, workshop2));

        mockMvc.perform(get("/api/workshop/subject/{id}", subjectId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("[0].title").value("Spring Boot Basics"))
                .andExpect(jsonPath("[1].title").value("Advanced Spring Boot"));
    }

    @Test
    void getWorkshopsBySubject_ShouldReturnNoContent_WhenNoWorkshopsExist() throws Exception {
        Long subjectId = 1L;

        when(workshopService.getBySubject(subjectId)).thenReturn(Arrays.asList());

        mockMvc.perform(get("/api/workshop/subject/{id}", subjectId))
                .andExpect(status().isNoContent());
    }

}

