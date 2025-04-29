package io.gituhub.jfelixy.petadoptionapi.infra.repository.specs;

import org.springframework.data.jpa.domain.Specification;

public class GenericSpecs {
    private GenericSpecs(){}

    public static <T> Specification<T> conjunction(){
        //1=1
        // SELECT * FROM PET WHERE 1 = 1/ To all find strategy
        return ((root, query, criteriaBuilder) -> criteriaBuilder.conjunction());
    }
}
