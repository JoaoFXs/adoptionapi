package io.gituhub.jfelixy.petadoptionapi.application;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Builder
public class PetDTO {
    private String url;
    private String name;
    private int age;
    private String type;
    private boolean breed;
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
    private String adoptedBy;
    private LocalDate adoptionDate;
    private String rescueLocation;
    private String history;
    private boolean microchip;
    private String notes;
    private String tags;
}
