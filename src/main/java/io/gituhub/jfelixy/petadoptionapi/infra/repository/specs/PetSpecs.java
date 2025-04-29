package io.gituhub.jfelixy.petadoptionapi.infra.repository.specs;

import io.gituhub.jfelixy.petadoptionapi.domain.Pet;
import org.springframework.data.jpa.domain.Specification;

public class PetSpecs {
    private PetSpecs(){}

    public static Specification<Pet> availableEqual(boolean available){
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("available"),available);
    }

    public static Specification<Pet> nameLike(String query){
        return (root, q, criteriaBuilder) -> criteriaBuilder.like(criteriaBuilder.upper(root.get("name")), "%" + query.toUpperCase() + "%");

    }

    public static Specification<Pet> tagsLike(String query){
        return (root, q, criteriaBuilder) -> criteriaBuilder.like(criteriaBuilder.upper(root.get("tags")), "%" + query.toUpperCase() + "%");
    }

}
