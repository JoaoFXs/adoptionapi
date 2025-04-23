package io.gituhub.jfelixy.petadoptionapi.application;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/pet")
public class PetController {

    private static final Logger log = LoggerFactory.getLogger(PetController.class);
    @PostMapping
    public ResponseEntity save(@RequestBody Pet pet){
        //log.info("Pet Received: name: {}, age: {}, tags: {}",name, age, String.join(",", tags));
        return ResponseEntity.ok().build();
    }

}
