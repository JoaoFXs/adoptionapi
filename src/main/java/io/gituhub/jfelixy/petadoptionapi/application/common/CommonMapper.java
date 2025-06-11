package io.gituhub.jfelixy.petadoptionapi.application.common;

import io.gituhub.jfelixy.petadoptionapi.application.common.pojo.LocationsJSON;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CommonMapper {

    public LocationsJSON mapLocations(String location){
        List<String> infoAddress = List.of(location.split("/"));
        return LocationsJSON.builder().address(infoAddress.get(0)).city(infoAddress.get(1)).province(infoAddress.get(2)).cep(infoAddress.get(3)).build();
    }
}
