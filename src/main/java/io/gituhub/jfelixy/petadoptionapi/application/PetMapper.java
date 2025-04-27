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
                   .id(pet.getId())
                   .name(pet.getName())
                   .age(pet.getAge())
                   .type(pet.getType())
                   .breed(pet.isBreed())
                   .sex(pet.getSex())
                   .size(pet.getSize())
                   .weight(pet.getWeight())
                   .photo(file.getBytes())
                   .neutered(pet.isNeutered())
                   .vaccinated(pet.isVaccinated())
                   .dewormed(pet.isDewormed())
                   .diseases(pet.getDiseases())
                   .specialNeeds(pet.getSpecialNeeds())
                   .temperament(pet.getTemperament())
                   .socialWith(pet.getSocialWith())
                   .available(pet.isAvailable())
                   .availabilityDate(pet.getAvailabilityDate())
                   .adoptedBy(pet.getAdoptedBy())
                   .adoptionDate(pet.getAdoptionDate())
                   .rescueLocation(pet.getRescueLocation())
                   .history(pet.getHistory())
                   .microchip(pet.isMicrochip())
                   .notes(pet.getNotes())
                   .tags(pet.getTags())
                   .build()
                   ;
        return petMapped;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
