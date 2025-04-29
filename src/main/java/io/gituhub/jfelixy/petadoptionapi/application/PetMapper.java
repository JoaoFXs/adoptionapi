package io.gituhub.jfelixy.petadoptionapi.application;

import io.gituhub.jfelixy.petadoptionapi.application.PetDTO;
import io.gituhub.jfelixy.petadoptionapi.domain.Pet;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Base64;

@Component
public class PetMapper {

    public Pet petMapperDefault(Pet pet){
        byte[] photoByte;

        if(pet.getPhoto() == null && pet.getPhotoBase64() != null){
            photoByte = base64toByte(pet.getPhotoBase64());
        }else{
            photoByte = pet.getPhoto();
        }
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
                .photo(photoByte)
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
    }

    public byte[] base64toByte(String base64){
        return Base64.getDecoder().decode(base64);
    }
}
