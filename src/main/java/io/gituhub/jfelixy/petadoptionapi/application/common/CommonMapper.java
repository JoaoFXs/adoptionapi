package io.gituhub.jfelixy.petadoptionapi.application.common;

import io.gituhub.jfelixy.petadoptionapi.application.common.pojo.LocationsJSON;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Component responsible for mapping location strings into structured {@link LocationsJSON} objects.
 *
 *
 */
@Component
public class CommonMapper {

    /**
     * Converts a single location string into a {@link LocationsJSON} object.
     *
     *
     *
     * @param location the location string to be parsed
     * @return a {@link LocationsJSON} object populated with parsed data
     * @throws IndexOutOfBoundsException if the input string does not contain four parts
     */
    public LocationsJSON mapLocations(String location) {
        List<Object> infoAddress = List.of(location.split("/"));
        return LocationsJSON.builder()
                .address((String) infoAddress.get(0))
                .city((String) infoAddress.get(1))
                .province((String) infoAddress.get(2))
                .cep((String) infoAddress.get(3))
                .build();
    }
}
