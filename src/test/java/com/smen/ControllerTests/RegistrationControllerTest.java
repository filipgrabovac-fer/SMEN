package com.smen.ControllerTests;

import com.smen.Controllers.RegistrationController;
import com.smen.Models.*;
import com.smen.Services.RegistrationService;
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

class RegistrationControllerTest {

    @Mock
    private RegistrationService registrationService;

    @InjectMocks
    private RegistrationController registrationController;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(registrationController).build();
    }

    @Test
    void getRegistration_ShouldReturnRegistration_WhenExists() throws Exception {
        Long registrationId = 1L;

        User user = new User();
        user.setId(1L);
        user.setFirstName("John");

        Workshop workshop = new Workshop();
        workshop.setId(1L);
        workshop.setTitle("Spring Workshop");
        workshop.setDescription("A comprehensive Spring Boot workshop");
        workshop.setDuration(3);
        workshop.setNoOfAvailableSlots(10);
        workshop.setType(WorkshopType.HYBRID);
        workshop.setStatus(WorkshopStatus.DELAYED);

        Subject subject = new Subject();
        subject.setId(1L);
        subject.setTitle("Software Engineering");

        workshop.setSubject(subject);

        Registration registration = new Registration();
        registration.setId(registrationId);
        registration.setUser(user);
        registration.setWorkshop(workshop);

        when(registrationService.getByid(registrationId)).thenReturn(Optional.of(registration));

        mockMvc.perform(get("/api/registration/{id}", registrationId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(registrationId))
                .andExpect(jsonPath("$.user.firstName").value("John"))
                .andExpect(jsonPath("$.workshop.title").value("Spring Workshop"))
                .andExpect(jsonPath("$.workshop.subject.title").value("Software Engineering"))
                .andExpect(jsonPath("$.workshop.duration").value(3));
    }

    @Test
    void getRegistration_ShouldReturnNotFound_WhenDoesNotExist() throws Exception {
        Long registrationId = 1L;
        when(registrationService.getByid(registrationId)).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/registration/{id}", registrationId))
                .andExpect(status().isNotFound());
    }

    @Test
    void getRegistrations_ShouldReturnRegistrations_WhenExist() throws Exception {
        User user1 = new User();
        user1.setId(1L);
        user1.setFirstName("John");

        Workshop workshop1 = new Workshop();
        workshop1.setId(1L);
        workshop1.setTitle("Spring Workshop");
        workshop1.setSubject(new Subject());
        workshop1.setDescription("Spring Boot and microservices");
        workshop1.setDuration(3);
        workshop1.setNoOfAvailableSlots(5);
        workshop1.setType(WorkshopType.ONLINE);
        workshop1.setStatus(WorkshopStatus.DELAYED);

        Subject subject = new Subject();
        subject.setId(1L);
        subject.setTitle("Software Engineering");

        workshop1.setSubject(subject);

        Registration registration1 = new Registration();
        registration1.setId(1L);
        registration1.setUser(user1);
        registration1.setWorkshop(workshop1);

        when(registrationService.getAll()).thenReturn(Arrays.asList(registration1));

        mockMvc.perform(get("/api/registration/"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("[0].workshop.title").value("Spring Workshop"))
                .andExpect(jsonPath("[0].workshop.subject.title").value("Software Engineering"));
    }

    @Test
    void createRegistration_ShouldReturnCreatedRegistration() throws Exception {
        User user = new User();
        user.setId(1L);
        user.setFirstName("John");

        Subject subject = new Subject();
        subject.setId(1L);
        subject.setTitle("Software Engineering");

        Workshop workshop = new Workshop();
        workshop.setId(1L);
        workshop.setTitle("Spring Workshop");
        workshop.setSubject(subject);
        workshop.setDuration(3);
        workshop.setNoOfAvailableSlots(10);
        workshop.setType(WorkshopType.ONSITE);
        workshop.setStatus(WorkshopStatus.IN_PROGRESS);

        Registration newRegistration = new Registration();
        newRegistration.setId(1L);
        newRegistration.setUser(user);
        newRegistration.setWorkshop(workshop);

        when(registrationService.create(any(Registration.class))).thenReturn(newRegistration);

        mockMvc.perform(post("/api/registration/new")
                        .contentType("application/json")
                        .content("{\"user\": {\"id\": 1}, \"workshop\": {\"id\": 1}}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.workshop.title").value("Spring Workshop"))
                .andExpect(jsonPath("$.workshop.subject.title").value("Software Engineering"));
    }

    @Test
    @WithMockUser(username = "admin", roles = "ADMIN")
    void deleteRegistration_ShouldReturnOk_WhenRegistrationDeleted() throws Exception {
        Long registrationId = 1L;
        when(registrationService.deleteById(registrationId)).thenReturn(true);

        mockMvc.perform(delete("/api/registration/{id}", registrationId))
                .andExpect(status().isOk())
                .andExpect(content().string("Registration deleted successfully"));
    }

    @Test
    @WithMockUser(username = "admin", roles = "ADMIN")
    void deleteRegistration_ShouldReturnNotFound_WhenRegistrationNotFound() throws Exception {
        Long registrationId = 1L;
        when(registrationService.deleteById(registrationId)).thenReturn(false);

        mockMvc.perform(delete("/api/registration/{id}", registrationId))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Registration not found"));
    }
}
