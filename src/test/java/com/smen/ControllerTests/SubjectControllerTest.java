package com.smen.ControllerTests;
import com.smen.Controllers.SubjectController;
import com.smen.Models.Subject;
import com.smen.Services.SubjectService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.Collections;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class SubjectControllerTest {

    private MockMvc mockMvc;

    @Mock
    private SubjectService subjectService;

    @InjectMocks
    private SubjectController subjectController;


    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(subjectController).build();
    }

    // Test za dohvat jednog predmeta prema ID-u
    @Test
    void getSubject_ShouldReturnSubject_WhenSubjectExists() throws Exception {
        Long subjectId = 1L;
        Subject subject = new Subject();
        subject.setId(subjectId);
        subject.setTitle("Software Engineering");

        when(subjectService.getByid(subjectId)).thenReturn(Optional.of(subject));

        mockMvc.perform(get("/api/subject/{id}", subjectId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(subjectId))
                .andExpect(jsonPath("$.title").value("Software Engineering"));
    }

    @Test
    void getSubject_ShouldReturnNotFound_WhenSubjectDoesNotExist() throws Exception {
        Long subjectId = 999L;

        when(subjectService.getByid(subjectId)).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/subject/{id}", subjectId))
                .andExpect(status().isNotFound());
    }

    @Test
    void getSubjects_ShouldReturnListOfSubjects_WhenSubjectsExist() throws Exception {
        Subject subject1 = new Subject();
        subject1.setId(1L);
        subject1.setTitle("Software Engineering");
        Subject subject2 = new Subject();
        subject2.setId(2L);
        subject2.setTitle("Computer Science");

        when(subjectService.getAll()).thenReturn(Arrays.asList(subject1, subject2));

        mockMvc.perform(get("/api/subject/"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("[0].title").value("Software Engineering"))
                .andExpect(jsonPath("[1].title").value("Computer Science"));
    }

    @Test
    void getSubjects_ShouldReturnNoContent_WhenNoSubjectsExist() throws Exception {
        when(subjectService.getAll()).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/api/subject/"))
                .andExpect(status().isNoContent());
    }

    @Test
    void deleteSubject_ShouldReturnOk_WhenSubjectExists() throws Exception {
        Long subjectId = 1L;

        when(subjectService.deleteById(subjectId)).thenReturn(true);

        mockMvc.perform(delete("/api/subject/{id}", subjectId))
                .andExpect(status().isOk())
                .andExpect(content().string("Subject deleted successfully"));
    }

    @Test
    void deleteSubject_ShouldReturnNotFound_WhenSubjectDoesNotExist() throws Exception {
        Long subjectId = 999L;

        when(subjectService.deleteById(subjectId)).thenReturn(false);

        mockMvc.perform(delete("/api/subject/{id}", subjectId))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Subject not found"));
    }

    @Test
    void createSubject_ShouldReturnCreated_WhenSubjectIsValid() throws Exception {
        Subject subject = new Subject();
        subject.setTitle("Data Structures");

        when(subjectService.create(any(Subject.class))).thenReturn(subject);

        mockMvc.perform(post("/api/subject/new")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"title\":\"Data Structures\"}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.title").value("Data Structures"));
    }

}
