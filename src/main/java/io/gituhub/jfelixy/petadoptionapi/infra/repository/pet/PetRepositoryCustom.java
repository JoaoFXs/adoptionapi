package io.gituhub.jfelixy.petadoptionapi.infra.repository.pet;

import java.util.List;

public interface PetRepositoryCustom {

    List<Object> findByDistinctValue(String fieldValue);
}
