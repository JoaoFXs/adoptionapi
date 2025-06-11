package io.gituhub.jfelixy.petadoptionapi.infra.repository;

import io.gituhub.jfelixy.petadoptionapi.domain.entity.Pet;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.util.StringUtils;
import static io.gituhub.jfelixy.petadoptionapi.infra.repository.specs.PetSpecs.*;

import java.util.List;

import static io.gituhub.jfelixy.petadoptionapi.infra.repository.specs.GenericSpecs.conjunction;

public interface PetRepository extends JpaRepository<Pet,String>, JpaSpecificationExecutor<Pet> {
    /**Retrieves a list of distinct locations from all pets in the database.
        Each location is returned as a single string formatted as:
    "address/ city/ province/ cep" **/
    @Query("SELECT DISTINCT CONCAT(p.address, '/ ', p.city, '/ ', p.province, '/ ', p.cep) FROM Pet p")
    List<String> findAllLocations();


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
            spec = spec.and(Specification.anyOf(nameLike(query),tagsLike(query)));
        }

        return findAll(spec);
    }




}
