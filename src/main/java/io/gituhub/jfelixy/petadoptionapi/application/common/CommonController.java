package io.gituhub.jfelixy.petadoptionapi.application.common;

import io.gituhub.jfelixy.petadoptionapi.application.pet.PetServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * REST controller responsible for exposing common/shared endpoints across the application.
 */
@RestController
@RequestMapping("/v1/common")
public class CommonController {

    @Autowired
    PetServiceImpl service;

    @Autowired
    CommonMapper mapper;

    /**
     * Endpoint to retrieve the list of all pet locations in a structured JSON format.
     * Each location is extracted as a string from the database, parsed into its components
     * (address, city, province, cep), and returned as a list of JSON objects.
     *
     * @return ResponseEntity containing a list of LocationsJSON objects
     */
    @GetMapping("/locations")
    public ResponseEntity getPetLocations() {
        List<String> locations = service.getAllLocations();

        var parsedLocations = locations.stream()
                .map(mapper::mapLocations)
                .collect(java.util.stream.Collectors.toList());

        return ResponseEntity.ok(parsedLocations);
    }
}
