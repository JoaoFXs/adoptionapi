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

@RestController
@RequestMapping("/v1/pet")
public class PetController {
    @Autowired
    private PetMapper mapper;
    @Autowired
    private PetService service;

    private static final Logger log = LoggerFactory.getLogger(PetController.class);
    @PostMapping
    public ResponseEntity savePet(@RequestBody PetUpdateDTO pet){

        Pet petT = mapper.petMapperDefault(pet);

        Pet petSaved = service.save(petT);

        URI url = buildPetImageURL(petSaved);
        //log.info("Pet Received: name: {}, age: {}, tags: {}",pet.getName(), pet., String.join(",", tags));

        return ResponseEntity.created(url).build();
    }

    @GetMapping
    public ResponseEntity<List<PetDTO>> searchPets(
            @RequestParam(value="available", required = false) String available,
            @RequestParam(value="query", required = false) String query){
        var result = service.search(available, query);

        var  pets = result.stream().map(pet -> {
            var url = buildPetImageURL(pet);
            return mapper.petMapperDTO(pet, url.toString());
        }).collect(java.util.stream.Collectors.toList());
        return ResponseEntity.ok(pets);

    }


    @GetMapping("/{id}")
    public ResponseEntity<byte[]> getPetImage(@PathVariable String id) {
        var possibleResponsePet = service.findByID(id);
        if(possibleResponsePet.isEmpty()){
            return ResponseEntity.notFound().build();
        }
        Pet ResponsePet = possibleResponsePet.get();
        byte[] image = ResponsePet.getPhoto();
        if(image == null) {
            return ResponseEntity.notFound().build();
        }
        return new ResponseEntity<>(image, createHeader(ResponsePet), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity updatePet(@RequestBody PetUpdateDTO petUpdated, @PathVariable String id){
        var possiblePet = service.findByID(id);

        if (possiblePet.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(updatePetFromDto(possiblePet.get(), petUpdated));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deletePet(@PathVariable String id){
        service.delete(id);
        return ResponseEntity.ok().build();
    }

    private HttpHeaders createHeader(Pet responsePet){
        MediaType mediaType = detectMediaType(responsePet.getPhoto());
        HttpHeaders header = new HttpHeaders();
        header.setContentType(mediaType);
        header.setContentDisposition(ContentDisposition.inline().filename(responsePet.getName()).build());
        header.setCacheControl(CacheControl.maxAge(1,TimeUnit.HOURS).mustRevalidate());
        header.setContentLength(responsePet.getPhoto().length);
        return header;
    }


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
    private URI buildPetImageURL(Pet pet){
        String imagePath = "/" + pet.getId();
        return ServletUriComponentsBuilder.fromCurrentRequestUri().path(imagePath).build().toUri();
    }

    public Pet updatePetFromDto(Pet pet, PetUpdateDTO dto) {

        byte[] photoByte;

        if(dto.getPhoto() == null && dto.getPhotoBase64() != null){
            photoByte = PetMapper.base64toByte(dto.getPhotoBase64());
        }else{
            photoByte = dto.getPhoto();
        }
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
        return service.save(pet);
    }

}
