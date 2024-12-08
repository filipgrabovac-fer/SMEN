package com.smen.ControllerTests;

import com.smen.Controllers.LanguageController;
import com.smen.Models.Language;
import com.smen.Services.LanguageService;
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

class LanguageControllerTest {

    @Mock
    private LanguageService languageService;

    @InjectMocks
    private LanguageController languageController;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(languageController).build();
    }

    @Test
    void getLanguage_ShouldReturnLanguage_WhenExists() throws Exception {
        Long languageId = 1L;
        Language language = new Language();
        language.setId(languageId);
        language.setLanguage("English");

        when(languageService.getByid(languageId)).thenReturn(Optional.of(language));

        mockMvc.perform(get("/api/language/{id}", languageId))
                .andExpect(status().isOk()) // Status OK (200)
                .andExpect(jsonPath("$.id").value(languageId))
                .andExpect(jsonPath("$.language").value("English"));
    }

    @Test
    void getLanguage_ShouldReturnNotFound_WhenDoesNotExist() throws Exception {
        Long languageId = 1L;
        when(languageService.getByid(languageId)).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/language/{id}", languageId))
                .andExpect(status().isNotFound()); // Status Not Found (404)
    }

    @Test
    void getLanguages_ShouldReturnLanguages_WhenLanguagesExist() throws Exception {
        Language language1 = new Language();
        language1.setId(1L);
        language1.setLanguage("English");

        Language language2 = new Language();
        language2.setId(2L);
        language2.setLanguage("Spanish");

        when(languageService.getAll()).thenReturn(Arrays.asList(language1, language2));

        mockMvc.perform(get("/api/language/"))
                .andExpect(status().isOk()) // Status OK (200)
                .andExpect(jsonPath("$.length()").value(2)) // Provjera da imamo 2 jezika
                .andExpect(jsonPath("[0].language").value("English"))
                .andExpect(jsonPath("[1].language").value("Spanish"));
    }

    @Test
    void getLanguages_ShouldReturnNoContent_WhenNoLanguagesExist() throws Exception {
        when(languageService.getAll()).thenReturn(Arrays.asList());

        mockMvc.perform(get("/api/language/"))
                .andExpect(status().isNoContent()); // Status No Content (204)
    }

    @Test
    @WithMockUser(username = "admin", roles = "ADMIN")
    void deleteLanguage_ShouldReturnOk_WhenLanguageDeleted() throws Exception {
        Long languageId = 1L;
        when(languageService.deleteById(languageId)).thenReturn(true);

        mockMvc.perform(delete("/api/language/{id}", languageId))
                .andExpect(status().isOk())
                .andExpect(content().string("Language deleted successfully"));
    }

    @Test
    @WithMockUser(username = "admin", roles = "ADMIN")
    void deleteLanguage_ShouldReturnNotFound_WhenLanguageNotFound() throws Exception {
        Long languageId = 1L;
        when(languageService.deleteById(languageId)).thenReturn(false);

        mockMvc.perform(delete("/api/language/{id}", languageId))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Language not found"));
    }

    @Test
    @WithMockUser(username = "admin", roles = "ADMIN")
    void createLanguage_ShouldReturnCreatedLanguage() throws Exception {
        Language newLanguage = new Language();
        newLanguage.setId(1L);
        newLanguage.setLanguage("French");

        when(languageService.create(any(Language.class))).thenReturn(newLanguage);

        mockMvc.perform(post("/api/language/new")
                        .contentType("application/json")
                        .content("{\"language\": \"French\"}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.language").value("French"));
    }

    @Test
    void getLanguageByUserId_ShouldReturnLanguage_WhenExists() throws Exception {
        Long userId = 1L;
        Long languageId = 2L;
        Language language = new Language();
        language.setId(languageId);
        language.setLanguage("English");

        when(languageService.getLanguageByUserId(userId)).thenReturn(Optional.of(language));

        mockMvc.perform(get("/api/language/user/{userId}", userId))
                .andExpect(status().isOk()) // Status OK (200)
                .andExpect(jsonPath("$.id").value(languageId))
                .andExpect(jsonPath("$.language").value("English"));
    }

    @Test
    void getLanguageByUserId_ShouldReturnNotFound_WhenDoesNotExist() throws Exception {
        Long userId = 1L;
        when(languageService.getLanguageByUserId(userId)).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/language/user/{userId}", userId))
                .andExpect(status().isNotFound());
    }
}
