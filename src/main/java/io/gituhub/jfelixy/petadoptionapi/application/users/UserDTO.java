package io.gituhub.jfelixy.petadoptionapi.application.users;

import io.gituhub.jfelixy.petadoptionapi.domain.enums.user.RoleEnum;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserDTO {
    private String username;
    private String email;
    private String password;
    private byte[] photo;
    private RoleEnum role;

}
