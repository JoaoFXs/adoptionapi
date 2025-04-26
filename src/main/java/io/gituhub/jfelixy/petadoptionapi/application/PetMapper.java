package io.gituhub.jfelixy.petadoptionapi.application;

import io.gituhub.jfelixy.petadoptionapi.domain.Pet;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Component
public class PetMapper {

    private Pet petMapperDefault(MultipartFile file, Pet pet){
        try {
           Pet petMapped = Pet
                    .builder()
                    .age(pet.getAge())
                    .breed(pet.isBreed())
                    .adoptedBy(pet.getAdoptedBy())
                    .adoptionDate(pet.getAdoptionDate())
                    .availabilityDate(pet.getAvailabilityDate())
                    .available(pet.isAvailable())
                    .name(pet.getName())
                    .photo(file.getBytes())
                    .build();
        return petMapped;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
