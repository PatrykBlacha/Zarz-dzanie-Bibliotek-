package pl.agh.edu.library.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.agh.edu.library.dto.CreateUserDto;
import pl.agh.edu.library.dto.UserDto;
import pl.agh.edu.library.service.UserService;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public List<UserDto> getUsers() {
        return userService.getUsers();
    }

    @PostMapping
    public void addUser(@RequestBody CreateUserDto createUserDto) {
        userService.addUser(createUserDto);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDto> getUser(@PathVariable Long id) {
        return userService.getUser(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserDto> updateUser(@PathVariable Long id, @RequestBody UserDto userDetails) {
        return userService.updateUser(id, userDetails)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}