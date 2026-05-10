package pl.agh.edu.library.mapper;

import pl.agh.edu.library.dto.CreateUserDto;
import pl.agh.edu.library.dto.UserDto;
import pl.agh.edu.library.model.User;

public class UserMapper {

    public static UserDto toDto(User user) {
        if (user == null) {
            return null;
        }
        return UserDto.builder()
                .id(user.getId())
                .userName(user.getUserName())
                .email(user.getEmail())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .role(user.getRole())
                .build();
    }

    public static User toEntity(CreateUserDto dto) {
        if (dto == null) {
            return null;
        }
        User user = new User();
        user.setUserName(dto.getUserName());
        user.setEmail(dto.getEmail());
        user.setFirstName(dto.getFirstName());
        user.setLastName(dto.getLastName());
        user.setPassword(dto.getPassword());
        user.setRole(dto.getRole());
        return user;
    }
}