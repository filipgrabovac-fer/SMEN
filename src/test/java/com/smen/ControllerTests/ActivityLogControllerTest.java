package com.smen.ControllerTests;

import com.smen.Controllers.ActivityLogController;
import com.smen.Models.ActivityLog;
import com.smen.Services.ActivityLogService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.Optional;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class ActivityLogControllerTest {

    @Mock
    private ActivityLogService service;

    @InjectMocks
    private ActivityLogController activityLogController;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(activityLogController).build();
    }

    @Test
    void getActivityLog_ShouldReturnActivityLog_WhenExists() throws Exception {
        Long activityLogId = 1L;
        ActivityLog activityLog = new ActivityLog();
        activityLog.setId(activityLogId);
        activityLog.setActivity("User login");

        when(service.getByid(activityLogId)).thenReturn(Optional.of(activityLog));

        mockMvc.perform(get("/api/activitylog/{id}", activityLogId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(activityLogId))
                .andExpect(jsonPath("$.activity").value("User login"));
    }

    @Test
    void getActivityLog_ShouldReturnNotFound_WhenDoesNotExist() throws Exception {
        Long activityLogId = 1L;
        when(service.getByid(activityLogId)).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/activitylog/{id}", activityLogId))
                .andExpect(status().isNotFound());
    }

    @Test
    void getActivityLogs_ShouldReturnActivityLogs_WhenExist() throws Exception {
        ActivityLog log1 = new ActivityLog();
        log1.setId(1L);
        log1.setActivity("User login");

        ActivityLog log2 = new ActivityLog();
        log2.setId(2L);
        log2.setActivity("User logout");

        when(service.getAll()).thenReturn(Arrays.asList(log1, log2));

        mockMvc.perform(get("/api/activitylog/"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("[0].activity").value("User login"))
                .andExpect(jsonPath("[1].activity").value("User logout"));
    }

    @Test
    void getActivityLogs_ShouldReturnNoContent_WhenNoActivityLogsExist() throws Exception {
        when(service.getAll()).thenReturn(Arrays.asList());

        mockMvc.perform(get("/api/activitylog/"))
                .andExpect(status().isNoContent());
    }

    @Test
    void getActivityLogsByUser_ShouldReturnActivityLogs_WhenExist() throws Exception {
        Long userId = 1L;
        ActivityLog log1 = new ActivityLog();
        log1.setId(1L);
        log1.setActivity("User login");

        ActivityLog log2 = new ActivityLog();
        log2.setId(2L);
        log2.setActivity("User logout");

        when(service.getByUser(userId)).thenReturn(Arrays.asList(log1, log2));

        mockMvc.perform(get("/api/activitylog/user/{id}", userId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("[0].activity").value("User login"))
                .andExpect(jsonPath("[1].activity").value("User logout"));
    }

    @Test
    void getActivityLogsByUser_ShouldReturnNoContent_WhenNoActivityLogsExistForUser() throws Exception {
        Long userId = 1L;
        when(service.getByUser(userId)).thenReturn(Arrays.asList());

        mockMvc.perform(get("/api/activitylog/user/{id}", userId))
                .andExpect(status().isNoContent());
    }

    @Test
    void deleteActivityLog_ShouldReturnOk_WhenDeleted() throws Exception {
        Long activityLogId = 1L;
        when(service.deleteById(activityLogId)).thenReturn(true);

        mockMvc.perform(delete("/api/activitylog/{id}", activityLogId))
                .andExpect(status().isOk())
                .andExpect(content().string("ActivityLog deleted successfully"));
    }

    @Test
    void deleteActivityLog_ShouldReturnNotFound_WhenNotFound() throws Exception {
        Long activityLogId = 1L;
        when(service.deleteById(activityLogId)).thenReturn(false);

        mockMvc.perform(delete("/api/activitylog/{id}", activityLogId))
                .andExpect(status().isNotFound())
                .andExpect(content().string("ActivityLog not found"));
    }
}
