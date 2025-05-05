package io.gituhub.jfelixy.petadoptionapi.application;

import io.gituhub.jfelixy.petadoptionapi.domain.entity.Pet;
import io.gituhub.jfelixy.petadoptionapi.infra.repository.PetRepository;
import io.gituhub.jfelixy.petadoptionapi.service.PetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Service implementation class for managing Pet entities.
 * Provides methods for saving, searching, finding, and deleting pets.
 */
@Service
public class PetServiceImpl implements PetService {

    @Autowired
    private PetRepository repository; // Injects the PetRepository to access database operations

    /**
     * Saves a Pet entity to the repository.
     * @param pet the Pet object to be saved
     * @return the saved Pet entity
     */
    @Override
    public Pet save(Pet pet) {
        return repository.save(pet);
    }

    /**
     * Searches for pets based on their availability and a query string.
     * The query can match pet name or tags.
     * @param available the availability status (e.g., "true" or "false")
     * @param query the search keyword
     * @return a list of pets matching the criteria
     */
    @Override
    public List<Pet> search(String available, String query) {
        return repository.findByAvailableAndNameOrTagsLike(available, query);
    }

    /**
     * Finds a pet by its unique ID.
     * @param id the ID of the pet
     * @return an Optional containing the Pet if found, or empty if not found
     */
    @Override
    public Optional<Pet> findByID(String id) {
        return repository.findById(id);
    }

    /**
     * Deletes a pet by its unique ID.
     * @param id the ID of the pet to delete
     */
    @Override
    public void delete(String id) {
        repository.deleteById(id);
    }
}
