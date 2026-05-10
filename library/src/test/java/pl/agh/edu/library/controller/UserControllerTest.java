package pl.agh.edu.library.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import pl.agh.edu.library.dto.CreateUserDto;
import pl.agh.edu.library.dto.UserDto;
import pl.agh.edu.library.security.JwtFilter;
import pl.agh.edu.library.security.JwtUtil;
import pl.agh.edu.library.service.UserService;

import java.util.Arrays;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(UserController.class)
@AutoConfigureMockMvc(addFilters = false)
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private UserService userService;

    @MockitoBean
    private JwtUtil jwtUtil;

    @MockitoBean
    private JwtFilter jwtFilter;

    private UserDto testUserDto;
    private CreateUserDto createTestUserDto;

    @BeforeEach
    void setUp() {
        testUserDto = UserDto.builder()
                .id(1)
                .firstName("John")
                .lastName("Doe")
                .userName("johndoe")
                .email("john@example.com")
                .role("USER")
                .build();

        createTestUserDto = new CreateUserDto();
        createTestUserDto.setFirstName("John");
        createTestUserDto.setLastName("Doe");
        createTestUserDto.setUserName("johndoe");
        createTestUserDto.setEmail("john@example.com");
        createTestUserDto.setPassword("password123");
        createTestUserDto.setRole("USER");
    }

    @Test
    void getUsers_ReturnsListOfUsers() throws Exception {
        when(userService.getUsers()).thenReturn(Arrays.asList(testUserDto));

        mockMvc.perform(get("/api/users")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].firstName").value("John"))
                .andExpect(jsonPath("$[0].userName").value("johndoe"));
    }

    @Test
    void getUser_WhenExists_ReturnsUser() throws Exception {
        when(userService.getUser(1L)).thenReturn(Optional.of(testUserDto));

        mockMvc.perform(get("/api/users/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName").value("John"));
    }

    @Test
    void getUser_WhenDoesNotExist_ReturnsNotFound() throws Exception {
        when(userService.getUser(1L)).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/users/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    void addUser_SavesUser() throws Exception {
        mockMvc.perform(post("/api/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"firstName\":\"John\",\"lastName\":\"Doe\",\"userName\":\"johndoe\",\"email\":\"john@example.com\",\"password\":\"password123\",\"role\":\"USER\"}"))
                .andExpect(status().isOk());

        verify(userService, times(1)).addUser(any(CreateUserDto.class));
    }

    @Test
    void deleteUser_CallsServiceDelete() throws Exception {
        mockMvc.perform(delete("/api/users/1"))
                .andExpect(status().isOk());

        verify(userService, times(1)).deleteUser(1L);
    }
}