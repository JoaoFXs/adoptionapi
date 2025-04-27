package io.gituhub.jfelixy.petadoptionapi.service;

import io.gituhub.jfelixy.petadoptionapi.domain.Pet;

import java.util.List;

public interface PetService {

    Pet save(Pet pet);

    List<Pet> search(String available, String query);

}
