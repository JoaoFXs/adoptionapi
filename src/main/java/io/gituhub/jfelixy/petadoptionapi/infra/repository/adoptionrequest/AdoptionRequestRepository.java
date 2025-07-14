package io.gituhub.jfelixy.petadoptionapi.infra.repository.adoptionrequest;

import io.gituhub.jfelixy.petadoptionapi.domain.entity.AdoptionRequest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AdoptionRequestRepository extends JpaRepository<AdoptionRequest, String> {


    List<AdoptionRequest> findByStatus(AdoptionRequest.Status status);

}
