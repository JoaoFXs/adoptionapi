package io.gituhub.jfelixy.petadoptionapi.application.adoptionrequest;

import io.gituhub.jfelixy.petadoptionapi.application.users.UserServiceImpl;
import io.gituhub.jfelixy.petadoptionapi.domain.entity.AdoptionRequest;
import io.gituhub.jfelixy.petadoptionapi.domain.service.AdoptionRequestService;
import io.gituhub.jfelixy.petadoptionapi.infra.repository.adoptionrequest.AdoptionRequestRepository;
import io.gituhub.jfelixy.petadoptionapi.infra.repository.pet.PetRepository;
import io.gituhub.jfelixy.petadoptionapi.infra.repository.user.UserRepository;

import java.util.List;

public class AdoptionRequestServiceImpl implements AdoptionRequestService {

    private AdoptionRequestRepository requestRepository;
    private UserRepository userRepository;
    private PetRepository petRepository
    @Override
    public AdoptionRequest createRequest(AdoptionRequestDTO dto, String userId, String petId) {
        return null;
    }

    @Override
    public List<AdoptionRequest> getPendingRequests() {
        return List.of();
    }

    @Override
    public AdoptionRequest updateStatus(String requestId, AdoptionRequest.Status status) {
        return null;
    }
}
