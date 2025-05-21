package io.gituhub.jfelixy.petadoptionapi.application.users;

import io.gituhub.jfelixy.petadoptionapi.application.jwt.JwtService;
import io.gituhub.jfelixy.petadoptionapi.domain.AccessToken;
import io.gituhub.jfelixy.petadoptionapi.domain.entity.User;
import io.gituhub.jfelixy.petadoptionapi.domain.exception.DuplicatedTupleException;
import io.gituhub.jfelixy.petadoptionapi.domain.service.UserService;
import io.gituhub.jfelixy.petadoptionapi.infra.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Implementation of the UserService interface.
 * Provides business logic for managing User entities,
 * including saving users with password encryption and duplicate checks.
 */
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    /**
     * Encoder used to hash user passwords before persisting.
     */

    private final PasswordEncoder passwordEncoder;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private JwtService jwtService;
    /**
     * Repository used for accessing User data in the database.
     */
    @Autowired
    private UserRepository userRepo;

    /**
     * Saves a new user to the database.
     *
     * First checks if a user with the same email already exists;
     * if so, throws a {@link DuplicatedTupleException}.
     * The user's password is encoded before saving.
     *
     * @param user the User entity to save
     * @return the saved User entity
     * @throws DuplicatedTupleException if a user with the same email already exists
     */
    @Override
    @Transactional
    public User save(User user) {
        // Verify if user exists and throw an exception
        var possibleUser = getByEmail(user.getEmail());
        if (possibleUser != null){
            throw new DuplicatedTupleException("User already exists!");
        }

        encodePassword(user);
        return userRepo.save(user);
    }


    @Override
    public User getByEmail(String email) {
        return userRepo.findByEmail(email);
    }

    @Override
    public AccessToken authenticate(String email, String password) {
        var user = getByEmail(email);
        if(user == null){
            return null;
        }

        if (passwordEncoder.matches(password, user.getPassword())){
            return jwtService.generateToken(user);
        }
        return null;
    }

    public Optional<User> getById(String id) {
        return userRepo.findById(id);
    }

    /**
     * Encodes the user's raw password using the configured PasswordEncoder.
     *
     * @param user the User entity whose password will be encoded
     */
    private void encodePassword(User user){
        String rawPassword = user.getPassword();
        String encodedPassword = passwordEncoder.encode(rawPassword);
        user.setPassword(encodedPassword);
    }

}
