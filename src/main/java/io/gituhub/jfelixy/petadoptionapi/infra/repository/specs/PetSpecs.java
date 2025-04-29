package io.gituhub.jfelixy.petadoptionapi.infra.repository.specs;

import io.gituhub.jfelixy.petadoptionapi.domain.Pet;
import org.springframework.data.jpa.domain.Specification;

public class PetSpecs {
    private PetSpecs(){}

    public static Specification<Pet> availableEqual(boolean available){
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("available"),available);
    }
}
