package io.gituhub.jfelixy.petadoptionapi.application.common;

import io.gituhub.jfelixy.petadoptionapi.application.common.pojo.LocationsJSON;
import io.gituhub.jfelixy.petadoptionapi.application.pet.PetServiceImpl;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

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
        List<Object> result;
        List<LocationsJSON> parsedResult;

        List<Object> valuesAllow = List.of(
                "age",
                "type",
                "breed",
                "sex",
                "size",
                "temperament",
                "location"
        );

        if (!valuesAllow.contains(path)) return ResponseEntity.notFound().build();

        if (path.equalsIgnoreCase("location")) {
            result = service.queryValues(path);
            parsedResult = result.stream()
                    .map(r -> mapper.mapLocations((String) r))
                    .collect(Collectors.toList());

            return ResponseEntity.ok(parsedResult);
        }

        result = service.queryValues(path);
        return ResponseEntity.ok(result);

    }
}
