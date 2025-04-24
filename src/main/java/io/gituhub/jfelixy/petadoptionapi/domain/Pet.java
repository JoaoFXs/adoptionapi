package io.gituhub.jfelixy.petadoptionapi.domain;

import io.gituhub.jfelixy.petadoptionapi.domain.enums.SexEnum;
import io.gituhub.jfelixy.petadoptionapi.domain.enums.SizeEnum;
import io.gituhub.jfelixy.petadoptionapi.domain.enums.TemperamentEnum;
import io.gituhub.jfelixy.petadoptionapi.domain.enums.TypeEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import jakarta.persistence.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name= "pets")
@EntityListeners(AuditingEntityListener.class)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Pet {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column
    private String name;

    @Column
    private int age;

    @Column
    @Enumerated(EnumType.STRING)
    private TypeEnum type;

    @Column
    private boolean breed;

    @Column
    @Enumerated(EnumType.STRING)
    private SexEnum sex;

    @Column
    @Enumerated(EnumType.STRING)
    private SizeEnum size;

    @Column
    private Double weight;

    @Column
    @Lob
    private byte[] photo;

    @Column
    private boolean neutered;

    @Column
    private boolean vaccinated;

    @Column
    private boolean dewormed;

    @Column
    private String diseases;

    @Column
    private String specialNeeds;

    @Column
    @Enumerated(EnumType.STRING)
    private TemperamentEnum temperament;

    @Column
    private String socialWith;

    @Column
    private boolean available;

    @Column
    @CreatedDate
    private LocalDateTime availabilityDate;

    @Column
    private String adoptedBy;

    @Column
    private LocalDate adoptionDate;

    @Column
    private String rescueLocation;

    @Column
    private String history;

    @Column
    private boolean microchip;

    @Column
    @Enumerated
    private ShelterEnum shelter;

    @Column
    private String notes;

    @Column
    private String tags;



}
