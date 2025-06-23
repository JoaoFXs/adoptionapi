package io.gituhub.jfelixy.petadoptionapi.infra.repository;

import io.gituhub.jfelixy.petadoptionapi.domain.entity.Pet;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.util.StringUtils;
import static io.gituhub.jfelixy.petadoptionapi.infra.repository.specs.PetSpecs.*;

import java.util.List;

import static io.gituhub.jfelixy.petadoptionapi.infra.repository.specs.GenericSpecs.conjunction;

public interface PetRepository extends JpaRepository<Pet,String>, JpaSpecificationExecutor<Pet>, PetRepositoryCustom {


    /**
     * @param extension
     * @param query
     * @return
     *
     * SELECT * FROM IMAGE WHERE 1 = 1 AND AVAILABLE = boolean AND (NAME LIKE 'QUERY' OR TAGS LIKE 'QUERY')
     * */
    default List<Pet> findByAvailableAndNameOrTagsLike(String available, String query){
        Specification<Pet> spec = Specification.where(conjunction());


        // AND AVAILABLE = true

        if(available != null) {
            boolean availableBoolean = Boolean.parseBoolean(available);
            spec = spec.and(availableEqual(availableBoolean));
        }

        if(StringUtils.hasText(query)){
            spec = spec.and(Specification.anyOf(
                    fieldLike("name",query),
                    fieldLike("sex",query),
                    fieldLike("size",query),
                    fieldLike("type",query),
                    fieldLike("tags",query),
                    fieldLike("temperament", query),
                    fieldLike("address",query),
                    fieldLike("city",query),
                    fieldLike("province",query)));
        }

        return findAll(spec);
    }




}
