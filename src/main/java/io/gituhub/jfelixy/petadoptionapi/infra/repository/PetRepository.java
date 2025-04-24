package io.gituhub.jfelixy.petadoptionapi.infra.repository;

import io.gituhub.jfelixy.petadoptionapi.domain.Pet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.support.JpaRepositoryImplementation;

public interface PetRepository extends JpaRepository<Pet,String> {
}
