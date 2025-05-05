package io.gituhub.jfelixy.petadoptionapi.application.pet;


import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PetUpdateDTO {
    private String name;
    private Integer age;
    private String type;
    private String breed;
    private String sex;
    private String size;
    private Double weight;
    private byte[] photo;
    private String photoBase64;
    private Boolean neutered;
    private Boolean vaccinated;
    private Boolean dewormed;
    private String diseases;
    private String specialNeeds;
    private String temperament;
    private String socialWith;
    private Boolean available;
    private LocalDateTime availabilityDate;
    private String adoptedBy;
    private LocalDate adoptionDate;
    private String rescueLocation;
    private String history;
    private Boolean microchip;
    private String notes;
    private String tags;
}