package io.gituhub.jfelixy.petadoptionapi.application.users;

import io.gituhub.jfelixy.petadoptionapi.domain.entity.User;
import io.gituhub.jfelixy.petadoptionapi.domain.enums.user.RoleEnum;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;


@Component
public class UserMapper {
    public User mapToUser(MultipartFile file, String username, String email, String password) throws IOException {
        return User.builder()
                .username(username)
                .email(email)
                .password(password)
                .photo(file.getBytes())
                .role(RoleEnum.USER)
                .build();
    }
}
