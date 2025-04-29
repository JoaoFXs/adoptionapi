package io.gituhub.jfelixy.petadoptionapi.service;

import io.gituhub.jfelixy.petadoptionapi.domain.Pet;

import java.util.List;
import java.util.Optional;

public interface PetService {

    Pet save(Pet pet);

    List<Pet> search(boolean available, String query);

    Optional<Pet> findByID(String id);//Optional determin that pet must be optional exists or not
}
