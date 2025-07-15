package io.gituhub.jfelixy.petadoptionapi.infra.repository.adoptionrequest;

import io.gituhub.jfelixy.petadoptionapi.domain.entity.AdoptionRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AdoptionRequestRepository extends JpaRepository<AdoptionRequest, String> {


    List<AdoptionRequest> findByStatus(AdoptionRequest.Status status);

}
