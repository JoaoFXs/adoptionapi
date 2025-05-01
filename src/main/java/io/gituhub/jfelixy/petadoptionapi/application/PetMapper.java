package io.gituhub.jfelixy.petadoptionapi.application;

import io.gituhub.jfelixy.petadoptionapi.application.PetDTO;
import io.gituhub.jfelixy.petadoptionapi.domain.Pet;
import io.gituhub.jfelixy.petadoptionapi.domain.enums.SexEnum;
import io.gituhub.jfelixy.petadoptionapi.domain.enums.SizeEnum;
import io.gituhub.jfelixy.petadoptionapi.domain.enums.TemperamentEnum;
import io.gituhub.jfelixy.petadoptionapi.domain.enums.TypeEnum;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Base64;

@Component
public class PetMapper {

    public Pet petMapperDefault(PetUpdateDTO pet){
        byte[] photoByte;

        if(pet.getPhoto() == null && pet.getPhotoBase64() != null){
            photoByte = base64toByte(pet.getPhotoBase64());
        }else{
            photoByte = pet.getPhoto();
        }
        Pet petMapped = Pet
                .builder()
                .name(pet.getName())
                .age(pet.getAge())
                .type(TypeEnum.valueOf(pet.getType()))
                .breed(pet.getBreed())
                .sex(SexEnum.valueOf(pet.getSex()))
                .size(SizeEnum.valueOf(pet.getSize()))
                .weight(pet.getWeight())
                .photo(photoByte)
                .neutered(pet.getNeutered())
                .vaccinated(pet.getVaccinated())
                .dewormed(pet.getDewormed())
                .diseases(pet.getDiseases())
                .specialNeeds(pet.getSpecialNeeds())
                .temperament(TemperamentEnum.valueOf(pet.getTemperament()))
                .socialWith(pet.getSocialWith())
                .available(pet.getAvailable())
                .availabilityDate(pet.getAvailabilityDate())
                .adoptedBy(pet.getAdoptedBy())
                .adoptionDate(pet.getAdoptionDate())
                .rescueLocation(pet.getRescueLocation())
                .history(pet.getHistory())
                .microchip(pet.getMicrochip())
                .notes(pet.getNotes())
                .tags(pet.getTags())
                .build()
                ;
        return petMapped;
    }


    public PetDTO petMapperDTO(Pet pet, String url){


        PetDTO petMapped = PetDTO
                .builder()
                .url(url)
                .name(pet.getName())
                .age(pet.getAge())
                .type(pet.getType().name())
                .breed(pet.getBreed())
                .sex(pet.getSex().name())
                .size(pet.getSize().name())
                .weight(pet.getWeight())
                .photo(pet.getPhoto())
                .neutered(pet.isNeutered())
                .vaccinated(pet.isVaccinated())
                .dewormed(pet.isDewormed())
                .diseases(pet.getDiseases())
                .specialNeeds(pet.getSpecialNeeds())
                .temperament(pet.getTemperament().name())
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

    public static byte[] base64toByte(String base64){
        return Base64.getDecoder().decode(base64);
    }
}
