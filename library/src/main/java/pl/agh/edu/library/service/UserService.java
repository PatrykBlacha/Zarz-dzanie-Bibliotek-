package pl.agh.edu.library.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.agh.edu.library.dto.CreateUserDto;
import pl.agh.edu.library.dto.UserDto;
import pl.agh.edu.library.mapper.UserMapper;
import pl.agh.edu.library.model.User;
import pl.agh.edu.library.repository.UserRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<UserDto> getUsers() {
        return userRepository.findAll().stream()
                .map(UserMapper::toDto)
                .collect(Collectors.toList());
    }

    public void addUser(CreateUserDto createUserDto) {
        User user = UserMapper.toEntity(createUserDto);
        userRepository.save(user);
    }

    public Optional<UserDto> getUser(Long id) {
        return userRepository.findById(id).map(UserMapper::toDto);
    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    public Optional<UserDto> updateUser(Long id, UserDto userDetails) {
        return userRepository.findById(id).map(user -> {
            user.setFirstName(userDetails.getFirstName());
            user.setLastName(userDetails.getLastName());
            user.setEmail(userDetails.getEmail());
            user.setRole(userDetails.getRole());
            userRepository.save(user);
            return UserMapper.toDto(user);
        });
    }
}