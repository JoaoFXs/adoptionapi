package io.gituhub.jfelixy.petadoptionapi.application.users;

import io.gituhub.jfelixy.petadoptionapi.domain.enums.user.RoleEnum;
import lombok.Data;

@Data
public class UserDTO {
    private String username;
    private String email;
    private String password;
    private RoleEnum role;

}
