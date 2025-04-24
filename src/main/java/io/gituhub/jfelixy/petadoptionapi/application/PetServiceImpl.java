package io.gituhub.jfelixy.petadoptionapi.application;

import io.gituhub.jfelixy.petadoptionapi.domain.Pet;
import io.gituhub.jfelixy.petadoptionapi.infra.repository.PetRepository;
import io.gituhub.jfelixy.petadoptionapi.service.PetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PetServiceImpl implements PetService {

    @Autowired
    private PetRepository repository;

    @Override
    public Pet save(Pet pet) {
        return repository.save(pet);
    }

}
