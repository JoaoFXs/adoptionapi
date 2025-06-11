package io.gituhub.jfelixy.petadoptionapi.application.common.pojo;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LocationsJSON {
    private String city;
    private String state;
}
