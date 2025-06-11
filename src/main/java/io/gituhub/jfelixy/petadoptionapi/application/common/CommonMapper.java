package io.gituhub.jfelixy.petadoptionapi.application.common;

import io.gituhub.jfelixy.petadoptionapi.application.common.pojo.LocationsJSON;

public class CommonMapper {

    public LocationsJSON mapLocations(String location){

        return LocationsJSON.builder().city("Barueri").state("SP").build();
    }
}
