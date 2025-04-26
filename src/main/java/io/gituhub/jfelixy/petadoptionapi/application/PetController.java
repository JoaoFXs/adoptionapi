package io.gituhub.jfelixy.petadoptionapi.application;


import io.gituhub.jfelixy.petadoptionapi.domain.Pet;
import io.gituhub.jfelixy.petadoptionapi.service.PetService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/v1/pet")
public class PetController {

    @Autowired
    private PetService service;
    private static final Logger log = LoggerFactory.getLogger(PetController.class);
    @PostMapping
    public ResponseEntity save(@RequestBody Pet pet){

        Pet petSaved = service.save(pet);
        //log.info("Pet Received: name: {}, age: {}, tags: {}",pet.getName(), pet., String.join(",", tags));

        return ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity<List<PetDTO>> search(
            @RequestParam(value="available", required = false) String available,
            @RequestParam(value="query", required = false) String query){
            return null;

    }



}
