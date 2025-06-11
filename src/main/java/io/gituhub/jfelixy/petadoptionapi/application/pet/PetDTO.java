package io.gituhub.jfelixy.petadoptionapi.application.pet;

import io.gituhub.jfelixy.petadoptionapi.domain.entity.Pet;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Builder
public class PetDTO {
    private String id;
    private String url;
    private String name;
    private int age;
    private String type;
    private String breed;
    private String sex;
    private String size;
    private Double weight;
    private byte[] photo;
    private boolean neutered;
    private boolean vaccinated;
    private boolean dewormed;
    private String diseases;
    private String specialNeeds;
    private String temperament;
    private String socialWith;
    private boolean available;
    private LocalDateTime availabilityDate;
    private Pet adoptedByUser;
    private LocalDate adoptionDate;
    private String rescueLocation;
    private String history;
    private boolean microchip;
    private String notes;
    private String tags;
    private String cep;
    private String address;
    private String province;
    private String city;
}
