package io.gituhub.jfelixy.petadoptionapi.application.users;

import io.gituhub.jfelixy.petadoptionapi.domain.entity.User;


public class UserMapper {
    public User mapToUser(UserDTO dto){
        return User.builder()
                .username(dto.getUsername())
                .email(dto.getEmail())
                .password(dto.getPassword())
                .role(dto.getRole())
                .build();
    }
}
