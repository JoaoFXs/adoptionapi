package io.gituhub.jfelixy.petadoptionapi.domain.entity;

import io.gituhub.jfelixy.petadoptionapi.domain.enums.pet.SexEnum;
import io.gituhub.jfelixy.petadoptionapi.domain.enums.pet.SizeEnum;
import io.gituhub.jfelixy.petadoptionapi.domain.enums.pet.TemperamentEnum;
import io.gituhub.jfelixy.petadoptionapi.domain.enums.pet.TypeEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
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
    private String breed;

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
    private byte[] photo;  // Actual photo as byte array


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

    @ManyToOne
    @JoinColumn(name = "adopted_by_user_id")
    private User adoptedByUser;

    @Column
    private LocalDate adoptionDate;

    @Column
    private String rescueLocation;

    @Column
    private String history;

    @Column
    private boolean microchip;

    @Column
    private String notes;

    @Column
    private String tags;



}
