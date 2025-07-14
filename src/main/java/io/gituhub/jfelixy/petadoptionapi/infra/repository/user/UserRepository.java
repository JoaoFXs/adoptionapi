package io.gituhub.jfelixy.petadoptionapi.infra.repository.user;

import io.gituhub.jfelixy.petadoptionapi.domain.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, String> {
    User findByEmail(String email);
}
