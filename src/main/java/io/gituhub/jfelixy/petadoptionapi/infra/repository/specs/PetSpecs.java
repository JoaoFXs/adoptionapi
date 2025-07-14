package io.gituhub.jfelixy.petadoptionapi.infra.repository.specs;

import io.gituhub.jfelixy.petadoptionapi.domain.entity.Pet;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;

public class PetSpecs {


    private PetSpecs(){}

    public static Specification<Pet> availableEqual(boolean available){
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("available"),available);
    }

    public static Specification<Pet> fieldLike(String field, String query){
        return (root, q, criteriaBuilder) -> criteriaBuilder.like(criteriaBuilder.upper(root.get(field)), "%" + query.toUpperCase() + "%");

    }

    public static Specification <Pet> queryLike(String query){
        return (root, query1, criteriaBuilder) -> criteriaBuilder.like(criteriaBuilder.upper(root.get("name")), "%" + query.toUpperCase() + "%");
    }
}
