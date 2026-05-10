package pl.agh.edu.library.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.agh.edu.library.dto.CreateUserDto;
import pl.agh.edu.library.dto.UserDto;
import pl.agh.edu.library.model.User;
import pl.agh.edu.library.repository.UserRepository;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    private User testUser;
    private CreateUserDto createUserDto;

    @BeforeEach
    void setUp() {
        testUser = new User();
        testUser.setFirstName("John");
        testUser.setLastName("Doe");
        testUser.setUserName("johndoe");
        testUser.setEmail("john@example.com");
        testUser.setRole("USER");
        testUser.setPassword("password123");

        createUserDto = new CreateUserDto();
        createUserDto.setFirstName("John");
        createUserDto.setLastName("Doe");
        createUserDto.setUserName("johndoe");
        createUserDto.setEmail("john@example.com");
        createUserDto.setRole("USER");
        createUserDto.setPassword("password123");
    }

    @Test
    void getUsers_ReturnsListOfUserDtos() {
        when(userRepository.findAll()).thenReturn(Arrays.asList(testUser));

        List<UserDto> result = userService.getUsers();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("John", result.get(0).getFirstName());
        verify(userRepository, times(1)).findAll();
    }

    @Test
    void addUser_SavesMappedUserEntity() {
        userService.addUser(createUserDto);

        ArgumentCaptor<User> userCaptor = ArgumentCaptor.forClass(User.class);
        verify(userRepository, times(1)).save(userCaptor.capture());
        
        User savedUser = userCaptor.getValue();
        assertEquals("John", savedUser.getFirstName());
        assertEquals("password123", savedUser.getPassword());
    }

    @Test
    void getUser_WhenExists_ReturnsUserDto() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(testUser));

        Optional<UserDto> result = userService.getUser(1L);

        assertTrue(result.isPresent());
        assertEquals("johndoe", result.get().getUserName());
        verify(userRepository, times(1)).findById(1L);
    }

    @Test
    void deleteUser_CallsRepositoryDelete() {
        userService.deleteUser(1L);

        verify(userRepository, times(1)).deleteById(1L);
    }
}