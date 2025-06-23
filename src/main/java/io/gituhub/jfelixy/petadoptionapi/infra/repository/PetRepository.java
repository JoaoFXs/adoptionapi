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

import java.util.ArrayList;
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
    default List<Pet> findByAvailableAndNameOrTagsLike(String available, String query) {
        Specification<Pet> spec = Specification.where(conjunction());

        // Filtro por disponibilidade
        if (available != null) {
            boolean availableBoolean = Boolean.parseBoolean(available);
            spec = spec.and(availableEqual(availableBoolean));
        }

        if (StringUtils.hasText(query)) {
            String[] queryParts = query.split("\\s*,\\s*"); // divide por vírgula e remove espaços
            List<Specification<Pet>> querySpecs = new ArrayList<>();

            for (String part : queryParts) {
                querySpecs.add(Specification.anyOf(
                        fieldLike("name", part),
                        fieldLike("sex", part),
                        fieldLike("size", part),
                        fieldLike("type", part),
                        fieldLike("tags", part),
                        fieldLike("temperament", part),
                        fieldLike("address", part),
                        fieldLike("city", part),
                        fieldLike("province", part)
                ));
            }

            // Junta todos os anyOf com OR
            Specification<Pet> combinedQuerySpec = Specification.where(querySpecs.get(0));
            for (int i = 1; i < querySpecs.size(); i++) {
                combinedQuerySpec = combinedQuerySpec.or(querySpecs.get(i));
            }

            spec = spec.and(combinedQuerySpec);
        }

        return findAll(spec);
    }



}
