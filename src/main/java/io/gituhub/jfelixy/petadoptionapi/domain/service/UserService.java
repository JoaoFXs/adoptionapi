package io.gituhub.jfelixy.petadoptionapi.domain.service;

import io.gituhub.jfelixy.petadoptionapi.domain.AccessToken;
import io.gituhub.jfelixy.petadoptionapi.domain.entity.User;
import org.springframework.stereotype.Service;


public interface UserService {
    User save(User user);

    User getByEmail(String email);
    AccessToken authenticate(String email, String password);
}
