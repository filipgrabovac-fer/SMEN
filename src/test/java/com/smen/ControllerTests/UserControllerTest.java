package com.smen.ControllerTests;

import com.smen.Controllers.UserController;
import com.smen.Models.User;
import com.smen.Services.UserService;
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

class UserControllerTest {

    @Mock
    private UserService userService; // mockamo UserService

    @InjectMocks
    private UserController userController; // kontroler koji testiramo

    private MockMvc mockMvc;



    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
    }

    @Test
    void getUser_ShouldReturnUser_WhenExists() throws Exception {
        Long userId = 1L;
        User user = new User();
        user.setId(userId);
        user.setFirstName("Test");
        user.setLastName("User");

        when(userService.getByid(userId)).thenReturn(Optional.of(user));

        mockMvc.perform(get("/api/users/{id}", userId))
                .andExpect(status().isOk()) // Status OK (200)
                .andExpect(jsonPath("$.id").value(userId))
                .andExpect(jsonPath("$.firstName").value("Test"))
                .andExpect(jsonPath("$.lastName").value("User"));
    }

    @Test
    void getUser_ShouldReturnNotFound_WhenDoesNotExist() throws Exception {
        Long userId = 1L;
        when(userService.getByid(userId)).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/users/{id}", userId))
                .andExpect(status().isNotFound());
    }

    @Test
    void getUsers_ShouldReturnUsers_WhenUsersExist() throws Exception {
        User user1 = new User();
        user1.setId(1L);
        user1.setFirstName("User");
        user1.setLastName("One");

        User user2 = new User();
        user2.setId(2L);
        user2.setFirstName("User");
        user2.setLastName("Two");

        when(userService.getAll()).thenReturn(Arrays.asList(user1, user2));

        mockMvc.perform(get("/api/users/"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("[0].firstName").value("User"))
                .andExpect(jsonPath("[0].lastName").value("One"))
                .andExpect(jsonPath("[1].firstName").value("User"))
                .andExpect(jsonPath("[1].lastName").value("Two"));
    }

    @Test
    void getUsers_ShouldReturnNoContent_WhenNoUsersExist() throws Exception {
        when(userService.getAll()).thenReturn(Arrays.asList());

        mockMvc.perform(get("/api/users/"))
                .andExpect(status().isNoContent());
    }

    @Test
    void deleteUser_ShouldReturnOk_WhenUserDeleted() throws Exception {
        Long userId = 1L;
        when(userService.deleteById(userId)).thenReturn(true);

        mockMvc.perform(delete("/api/users/{id}", userId))
                .andExpect(status().isOk())
                .andExpect(content().string("User deleted successfully"));
    }

    @Test
    void deleteUser_ShouldReturnNotFound_WhenUserNotFound() throws Exception {
        Long userId = 1L;
        when(userService.deleteById(userId)).thenReturn(false);

        mockMvc.perform(delete("/api/users/{id}", userId))
                .andExpect(status().isNotFound())
                .andExpect(content().string("User not found"));
    }

    @Test
    void updateUser_ShouldReturnUpdatedUser_WhenUserExists() throws Exception {
        Long userId = 1L;
        User existingUser = new User();
        existingUser.setId(userId);
        existingUser.setFirstName("Old");
        existingUser.setLastName("Name");

        User updatedUser = new User();
        updatedUser.setId(userId);
        updatedUser.setFirstName("Updated");
        updatedUser.setLastName("Name");

        when(userService.updateUser(any(Long.class), any(User.class))).thenReturn(updatedUser);

        mockMvc.perform(put("/api/users/{id}", userId)
                        .contentType("application/json")
                        .content("{\"firstName\": \"Updated\", \"lastName\": \"Name\"}")
                        )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName").value("Updated"))
                .andExpect(jsonPath("$.lastName").value("Name"));
    }


    @Test
    @WithMockUser(username = "admin", roles = "ADMIN")
    void updateUser_ShouldReturnNotFound_WhenUserDoesNotExist() throws Exception {
        Long userId = 1L;
        User existingUser = new User();
        existingUser.setId(userId);
        existingUser.setFirstName("Old");
        existingUser.setLastName("Name");

        when(userService.updateUser(any(Long.class), any(User.class))).thenReturn(null);

        mockMvc.perform(put("/api/users/{id}", userId)
                        .contentType("application/json")
                        .content("{\"firstName\": \"Updated\", \"lastName\": \"Name\"}"))
                .andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser(username = "admin", roles = "ADMIN")
    void createUser_ShouldReturnCreatedUser() throws Exception {
        User newUser = new User();
        newUser.setId(1L);
        newUser.setFirstName("New");
        newUser.setLastName("User");

        when(userService.create(any(User.class))).thenReturn(newUser);
        System.out.println("new user " + newUser.getFirstName());

        mockMvc.perform(post("/api/users/new")
                        .contentType("application/json")
                        .content("{\"firstName\": \"New\", \"lastName\": \"User\"}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.firstName").value("New"))
                .andExpect(jsonPath("$.lastName").value("User"));
    }


}