package io.gituhub.jfelixy.petadoptionapi.application.users;

import io.gituhub.jfelixy.petadoptionapi.domain.entity.User;
import io.gituhub.jfelixy.petadoptionapi.domain.exception.DuplicatedTupleException;
import io.gituhub.jfelixy.petadoptionapi.domain.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

/**
 * Implementation of the UserService interface.
 * Provides business logic for managing User entities,
 * including saving users with password encryption and duplicate checks.
 */
public class UserServiceImpl implements UserService {

    /**
     * Encoder used to hash user passwords before persisting.
     */
    @Autowired
    private PasswordEncoder passwordEncoder;

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
        return userRepository.save(user);
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
