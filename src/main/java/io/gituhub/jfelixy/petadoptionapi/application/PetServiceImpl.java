package io.gituhub.jfelixy.petadoptionapi.application;

import io.gituhub.jfelixy.petadoptionapi.domain.Pet;
import io.gituhub.jfelixy.petadoptionapi.infra.repository.PetRepository;
import io.gituhub.jfelixy.petadoptionapi.service.PetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PetServiceImpl implements PetService {

    @Autowired
    private PetRepository repository;

    @Override
    public Pet save(Pet pet) {return repository.save(pet);}

    @Override
    public List<Pet> search(String available, String query) {
        return null;
        //return repository.findByAvailableAndNameOrTagsLike(available, query);
    }

    @Override
    public Optional<Pet> findByID(String id) {
        return repository.findById(id);
    }

}
