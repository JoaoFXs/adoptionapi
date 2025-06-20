package io.gituhub.jfelixy.petadoptionapi.infra.repository;

import java.util.List;

public interface PetRepositoryCustom {

    List<Object> findByDistinctValue(String fieldValue);
}
