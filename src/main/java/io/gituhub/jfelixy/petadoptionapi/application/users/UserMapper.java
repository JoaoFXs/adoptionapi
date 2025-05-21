package io.gituhub.jfelixy.petadoptionapi.application.users;

import io.gituhub.jfelixy.petadoptionapi.domain.entity.User;
import org.springframework.stereotype.Component;


@Component
public class UserMapper {
    public User mapToUser(UserDTO dto){
        return User.builder()
                .username(dto.getUsername())
                .email(dto.getEmail())
                .password(dto.getPassword())
                .photo(dto.getPhoto())
                .role(dto.getRole())
                .build();
    }
}
