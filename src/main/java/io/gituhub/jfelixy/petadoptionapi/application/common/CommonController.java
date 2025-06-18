package io.gituhub.jfelixy.petadoptionapi.application.common;

import io.gituhub.jfelixy.petadoptionapi.application.common.pojo.LocationsJSON;
import io.gituhub.jfelixy.petadoptionapi.application.pet.PetServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    @GetMapping("/{path}")
    public ResponseEntity getPetLocations(@PathVariable String path) {
        List<String> result;
        List<LocationsJSON> parsedResult;
        switch (path) {
            case "location":
                result = service.getAllLocations();

                parsedResult = result.stream()
                        .map(mapper::mapLocations)
                        .collect(java.util.stream.Collectors.toList());
                return ResponseEntity.ok(parsedResult);
            case "breed":
                result = service.getAllBreeds();

                return ResponseEntity.ok(result);
            case "age":
                result = service.getAllAges();
                return ResponseEntity.ok(result);
            default:
                return ResponseEntity.badRequest().build();
        }

    }
}
