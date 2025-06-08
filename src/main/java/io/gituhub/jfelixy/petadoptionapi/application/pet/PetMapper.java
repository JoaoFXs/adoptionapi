package io.gituhub.jfelixy.petadoptionapi.application.pet;

import io.gituhub.jfelixy.petadoptionapi.domain.entity.Pet;
import io.gituhub.jfelixy.petadoptionapi.domain.enums.pet.SexEnum;
import io.gituhub.jfelixy.petadoptionapi.domain.enums.pet.SizeEnum;
import io.gituhub.jfelixy.petadoptionapi.domain.enums.pet.TemperamentEnum;
import io.gituhub.jfelixy.petadoptionapi.domain.enums.pet.TypeEnum;
import org.springframework.stereotype.Component;

import java.util.Base64;

/**
 * Mapper class responsible for converting between PetUpdateDTO, Pet, and PetDTO objects.
 */
@Component
public class PetMapper {

    /**
     * Maps a PetUpdateDTO object to a Pet entity.
     * If the photo in byte array is null but a base64 string is provided, it decodes the base64 to bytes.
     * @param pet the incoming PetUpdateDTO
     * @return a mapped Pet entity
     */
    public Pet petMapperDefault(PetUpdateDTO pet) {
        byte[] photoByte;

        // If the byte array photo is null but a base64 string is provided, decode the base64 string
        if (pet.getPhoto() == null && pet.getPhotoBase64() != null) {
            photoByte = base64toByte(pet.getPhotoBase64());
        } else {
            // Otherwise, use the provided byte array photo
            photoByte = pet.getPhoto();
        }

        // Build and return the Pet entity using the builder pattern
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
                .adoptedByUser(null)
                .adoptionDate(pet.getAdoptionDate())
                .rescueLocation(pet.getRescueLocation())
                .history(pet.getHistory())
                .microchip(pet.getMicrochip())
                .notes(pet.getNotes())
                .tags(pet.getTags())
                .build();
        return petMapped;
    }

    /**
     * Maps a Pet entity and an image URL to a PetDTO object.
     * @param pet the Pet entity
     * @param url the URL for accessing the pet's image
     * @return a mapped PetDTO
     */
    public PetDTO petMapperDTO(Pet pet, String url) {
        // Build and return the PetDTO object using the builder pattern
        PetDTO petMapped = PetDTO
                .builder()
                .id(pet.getId())
                .url(url)
                .name(pet.getName())
                .age(pet.getAge())
                .type(pet.getType().name())
                .breed(pet.getBreed())
                .sex(pet.getSex().name())
                .size(pet.getSize().getSize())
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
                .adoptedByUser(null)
                .adoptionDate(pet.getAdoptionDate())
                .rescueLocation(pet.getRescueLocation())
                .history(pet.getHistory())
                .microchip(pet.isMicrochip())
                .notes(pet.getNotes())
                .tags(pet.getTags())
                .build();
        return petMapped;
    }

    /**
     * Converts a Base64 encoded string into a byte array.
     * @param base64 the Base64 string
     * @return the decoded byte array
     */
    public static byte[] base64toByte(String base64) {
        return Base64.getDecoder().decode(base64);
    }
}
