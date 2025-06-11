package io.gituhub.jfelixy.petadoptionapi.application.common;


import io.gituhub.jfelixy.petadoptionapi.application.pet.PetServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/v1/common")
public class CommonController {

    @Autowired
    PetServiceImpl service;

    CommonMapper mapper;

    @GetMapping("/locations")
    public ResponseEntity getPetLocations(){

        List<String> locations = service.getAllLocations();

        var teste = locations.stream().map((String location) ->{
            return mapper.mapLocations(location);
        }).collect(java.util.stream.Collectors.toList());

        return ResponseEntity.ok(teste);
    }
}
