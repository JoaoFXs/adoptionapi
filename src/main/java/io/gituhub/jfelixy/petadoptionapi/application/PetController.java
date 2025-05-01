package io.gituhub.jfelixy.petadoptionapi.application;

import io.gituhub.jfelixy.petadoptionapi.domain.Pet;
import io.gituhub.jfelixy.petadoptionapi.domain.enums.SexEnum;
import io.gituhub.jfelixy.petadoptionapi.domain.enums.SizeEnum;
import io.gituhub.jfelixy.petadoptionapi.domain.enums.TemperamentEnum;
import io.gituhub.jfelixy.petadoptionapi.domain.enums.TypeEnum;
import io.gituhub.jfelixy.petadoptionapi.service.PetService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URLConnection;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

/**
 * REST controller to manage Pet resources.
 */
@RestController
@RequestMapping("/v1/pet")
public class PetController {

    @Autowired
    private PetMapper mapper; // Mapper for converting between DTOs and entities

    @Autowired
    private PetServiceImpl service; // Service layer to handle business logic

    private static final Logger log = LoggerFactory.getLogger(PetController.class); // Logger for debugging

    /**
     * Handles HTTP POST to create a new Pet.
     * @param pet the incoming DTO with pet information
     * @return HTTP 201 Created with Location header to access the pet image
     */
    @PostMapping
    public ResponseEntity savePet(@RequestBody PetUpdateDTO pet) {
        // Map DTO to entity
        Pet mappedPet = mapper.petMapperDefault(pet);

        // Save the pet using the service
        Pet petSaved = service.save(mappedPet);

        // Build URI to access the pet image
        URI url = buildPetImageURL(petSaved);

        // Return 201 Created with Location header
        return ResponseEntity.created(url).build();
    }

    /**
     * Handles HTTP GET to search for pets.
     * @param available filter by availability (optional)
     * @param query search keyword (optional)
     * @return list of pets matching the search
     */
    @GetMapping
    public ResponseEntity<List<PetDTO>> searchPets(
            @RequestParam(value="available", required = false) String available,
            @RequestParam(value="query", required = false) String query){

        // Perform search using the service
        var result = service.search(available, query);

        // Map results to DTO including image URL
        var pets = result.stream().map(pet -> {
            var url = buildPetImageURL(pet);
            return mapper.petMapperDTO(pet, url.toString());
        }).collect(java.util.stream.Collectors.toList());

        // Return 200 OK with list of pets
        return ResponseEntity.ok(pets);
    }

    /**
     * Handles HTTP GET to retrieve a pet's image by ID.
     * @param id the pet's ID
     * @return the image as byte[] with correct headers
     */
    @GetMapping("/{id}")
    public ResponseEntity<byte[]> getPetImage(@PathVariable String id) {
        // Find pet by ID
        var possibleResponsePet = service.findByID(id);

        if (possibleResponsePet.isEmpty()) {
            return ResponseEntity.notFound().build(); // Return 404 if not found
        }

        Pet ResponsePet = possibleResponsePet.get();
        byte[] image = ResponsePet.getPhoto();

        if (image == null) {
            return ResponseEntity.notFound().build(); // Return 404 if no image
        }

        // Return image bytes with HTTP headers
        return new ResponseEntity<>(image, createHeader(ResponsePet), HttpStatus.OK);
    }

    /**
     * Handles HTTP PUT to update a pet.
     * @param petUpdated DTO with new pet data
     * @param id pet's ID
     * @return updated pet or 404 if not found
     */
    @PutMapping("/{id}")
    public ResponseEntity updatePet(@RequestBody PetUpdateDTO petUpdated, @PathVariable String id){
        var possiblePet = service.findByID(id);

        if (possiblePet.isEmpty()) {
            return ResponseEntity.notFound().build(); // Return 404 if not found
        }

        // Update pet and return updated entity
        return ResponseEntity.ok(updatePetFromDto(possiblePet.get(), petUpdated));
    }

    /**
     * Handles HTTP DELETE to delete a pet.
     * @param id pet's ID
     * @return HTTP 200 OK or 404 not found
     */
    @DeleteMapping("/{id}")
    public ResponseEntity deletePet(@PathVariable String id){
        var pet = service.findByID(id);


        if (!pet.isPresent()){
            return ResponseEntity.notFound().build();
        }
        service.delete(id);
        log.info("The user deleted the following pet {} with id {}", pet.get().getName(), pet.get().getId());
        return ResponseEntity.noContent().build();
    }

    /**
     * Creates HTTP headers for image download.
     * @param responsePet the pet whose image is being sent
     * @return HTTP headers with content type, content disposition, etc.
     */
    private HttpHeaders createHeader(Pet responsePet){
        MediaType mediaType = detectMediaType(responsePet.getPhoto());
        HttpHeaders header = new HttpHeaders();
        header.setContentType(mediaType);
        header.setContentDisposition(ContentDisposition.inline().filename(responsePet.getName()).build());
        header.setCacheControl(CacheControl.maxAge(1, TimeUnit.HOURS).mustRevalidate());
        header.setContentLength(responsePet.getPhoto().length);
        return header;
    }

    /**
     * Detects MIME type of an image byte array.
     * @param imageBytes image as byte array
     * @return detected MediaType or octet-stream as fallback
     */
    private MediaType detectMediaType(byte[] imageBytes){
        try (InputStream is = new ByteArrayInputStream(imageBytes)) {
            String mimeType = URLConnection.guessContentTypeFromStream(is);
            if (mimeType == null) {
                return MediaType.APPLICATION_OCTET_STREAM;
            }
            return MediaType.parseMediaType(mimeType);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Builds a URI for accessing a pet's image.
     * @param pet the pet
     * @return URI pointing to /v1/pet/{id}
     */
    private URI buildPetImageURL(Pet pet){
        String imagePath = "/" + pet.getId();
        return ServletUriComponentsBuilder.fromCurrentRequestUri().path(imagePath).build().toUri();
    }

    /**
     * Updates a Pet entity using a DTO.
     * Maps each field only if the DTO contains a value.
     * @param pet the existing pet
     * @param dto the DTO with updated fields
     * @return the updated pet saved via the service
     */
    public Pet updatePetFromDto(Pet pet, PetUpdateDTO dto) {
        byte[] photoByte;

        // Handle image update from Base64 or direct byte[]
        if(dto.getPhoto() == null && dto.getPhotoBase64() != null){
            photoByte = PetMapper.base64toByte(dto.getPhotoBase64());
        } else {
            photoByte = dto.getPhoto();
        }

        // Map all non-null fields from DTO to entity
        if (dto.getName() != null) pet.setName(dto.getName());
        if (dto.getAge() != null) pet.setAge(dto.getAge());
        if (dto.getType() != null) pet.setType(TypeEnum.valueOf(dto.getType()));
        if (dto.getBreed() != null) pet.setBreed(dto.getBreed());
        if (dto.getSex() != null) pet.setSex(SexEnum.valueOf(dto.getSex()));
        if (dto.getSize() != null) pet.setSize(SizeEnum.valueOf(dto.getSize()));
        if (dto.getWeight() != null) pet.setWeight(dto.getWeight());
        if (dto.getPhotoBase64() != null) pet.setPhoto(photoByte);
        if (dto.getNeutered() != null) pet.setNeutered(dto.getNeutered());
        if (dto.getVaccinated() != null) pet.setVaccinated(dto.getVaccinated());
        if (dto.getDewormed() != null) pet.setDewormed(dto.getDewormed());
        if (dto.getDiseases() != null) pet.setDiseases(dto.getDiseases());
        if (dto.getSpecialNeeds() != null) pet.setSpecialNeeds(dto.getSpecialNeeds());
        if (dto.getTemperament() != null) pet.setTemperament(TemperamentEnum.valueOf(dto.getTemperament()));
        if (dto.getSocialWith() != null) pet.setSocialWith(dto.getSocialWith());
        if (dto.getAvailable() != null) pet.setAvailable(dto.getAvailable());
        if (dto.getAvailabilityDate() != null) pet.setAvailabilityDate(dto.getAvailabilityDate());
        if (dto.getAdoptedBy() != null) pet.setAdoptedBy(dto.getAdoptedBy());
        if (dto.getAdoptionDate() != null) pet.setAdoptionDate(dto.getAdoptionDate());
        if (dto.getRescueLocation() != null) pet.setRescueLocation(dto.getRescueLocation());
        if (dto.getHistory() != null) pet.setHistory(dto.getHistory());
        if (dto.getMicrochip() != null) pet.setMicrochip(dto.getMicrochip());
        if (dto.getNotes() != null) pet.setNotes(dto.getNotes());
        if (dto.getTags() != null) pet.setTags(dto.getTags());

        // Save updated pet
        return service.save(pet);
    }
}
