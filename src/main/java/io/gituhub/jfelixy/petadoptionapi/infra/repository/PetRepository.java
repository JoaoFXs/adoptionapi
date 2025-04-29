package io.gituhub.jfelixy.petadoptionapi.infra.repository;

import io.gituhub.jfelixy.petadoptionapi.domain.Pet;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.support.JpaRepositoryImplementation;

import java.util.List;

import static io.gituhub.jfelixy.petadoptionapi.infra.repository.specs.GenericSpecs.conjunction;

public interface PetRepository extends JpaRepository<Pet,String> {
    default List<Pet> findByAvailableAndNameOrTagsLike(boolean available, String query){
        Specification<Pet> spec = Specification.where(conjunction());

        if (available != null){
            // AND AVAILABLE = true
            spec = spec.and(availableEquals(available));
        }
    }
}
