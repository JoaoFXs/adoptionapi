package io.gituhub.jfelixy.petadoptionapi.domain.service;

import io.gituhub.jfelixy.petadoptionapi.application.adoptionrequest.AdoptionRequestDTO;
import io.gituhub.jfelixy.petadoptionapi.domain.entity.AdoptionRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface AdoptionRequestService {

    AdoptionRequest createRequest(AdoptionRequestDTO dto, String userId, String petId);

    List<AdoptionRequest> getPendingRequests();

    AdoptionRequest updateStatus(String requestId, AdoptionRequest.Status status);
}
