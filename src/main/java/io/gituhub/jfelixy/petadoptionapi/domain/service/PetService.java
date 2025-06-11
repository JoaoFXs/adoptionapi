package io.gituhub.jfelixy.petadoptionapi.domain.service;

import io.gituhub.jfelixy.petadoptionapi.domain.entity.Pet;

import java.util.List;
import java.util.Optional;

public interface PetService {

    Pet save(Pet pet);

    List<Pet> search(String available, String query);

    Optional<Pet> findByID(String id);//Optional determin that pet must be optional exists or not

    void delete(String id);

    List<String> getAllLocations();
}
