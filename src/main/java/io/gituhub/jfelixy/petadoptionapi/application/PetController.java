package io.gituhub.jfelixy.petadoptionapi.application;


import io.gituhub.jfelixy.petadoptionapi.domain.Pet;
import io.gituhub.jfelixy.petadoptionapi.service.PetService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.function.ServerRequest;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URLConnection;
import java.util.List;
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
    public ResponseEntity save(@RequestBody Pet pet){


        Pet petT = mapper.petMapperDefault(pet);

        Pet petSaved = service.save(petT);

        URI url = buildPetImageURL(petSaved);
        //log.info("Pet Received: name: {}, age: {}, tags: {}",pet.getName(), pet., String.join(",", tags));

        return ResponseEntity.created(url).build();
    }

    @GetMapping
    public ResponseEntity<List<PetDTO>> search(
            @RequestParam(value="available", required = false) String available,
            @RequestParam(value="query", required = false) String query){
            return null;

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

}
