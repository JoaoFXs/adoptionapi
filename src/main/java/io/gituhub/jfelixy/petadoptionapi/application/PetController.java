package io.gituhub.jfelixy.petadoptionapi.application;


import io.gituhub.jfelixy.petadoptionapi.domain.Pet;
import io.gituhub.jfelixy.petadoptionapi.service.PetService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<byte[]> getImage(@PathVariable String id) throws IOException {
        var possibleImage = service.findByID(id);

        if(possibleImage.isEmpty()){
            return ResponseEntity.notFound().build();
        }
        byte[] image = possibleImage.get().getPhoto();
        HttpHeaders header = new HttpHeaders();
        header.setContentType(detectMediaType(image));
        header.setContentDispositionFormData("inline", "image");
        return new ResponseEntity<>(image, header, HttpStatus.OK);
    }


    private MediaType detectMediaType(byte[] imageBytes) throws IOException, IOException {
        try (InputStream is = new ByteArrayInputStream(imageBytes)) {
            String mimeType = URLConnection.guessContentTypeFromStream(is);
            if (mimeType == null) {
                return MediaType.APPLICATION_OCTET_STREAM;
            }
            return MediaType.parseMediaType(mimeType);
        }
    }
    private URI buildPetImageURL(Pet pet){
        String imagePath = "/" + pet.getId();

        return ServletUriComponentsBuilder.fromCurrentRequestUri().path(imagePath).build().toUri();
    }

}
