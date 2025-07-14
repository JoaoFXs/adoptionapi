package io.gituhub.jfelixy.petadoptionapi.application.adoptionrequest;

import io.gituhub.jfelixy.petadoptionapi.application.users.UserServiceImpl;
import io.gituhub.jfelixy.petadoptionapi.domain.entity.AdoptionRequest;
import io.gituhub.jfelixy.petadoptionapi.domain.entity.User;
import io.gituhub.jfelixy.petadoptionapi.domain.service.AdoptionRequestService;
import io.gituhub.jfelixy.petadoptionapi.infra.repository.adoptionrequest.AdoptionRequestRepository;
import io.gituhub.jfelixy.petadoptionapi.infra.repository.pet.PetRepository;
import io.gituhub.jfelixy.petadoptionapi.infra.repository.user.UserRepository;

import java.util.List;

public class AdoptionRequestServiceImpl implements AdoptionRequestService {

    private AdoptionRequestRepository requestRepository;
    private UserRepository userRepository;
    private PetRepository petRepository;

    @Override
    public AdoptionRequest createRequest(AdoptionRequestDTO dto, String userId, String petId) {

        var user = userRepository.findById(userId);
        var pet = petRepository.findById(petId);

        AdoptionRequest request = AdoptionRequest.builder()
                .applicant(user)
                .pet(pet)
                .fullName(dto.getFullName())
                .email(dto.getEmail())
                .phone(dto.getPhone())
                .petExperience(dto.getPetExperience())
                .residenceType(dto.getResidenceType())
                .hasChildren(dto.isHasChildren())
                .adoptionReason(dto.getAdoptionReason())
                .build();

        return requestRepository.save(request);
    }

    @Override
    public List<AdoptionRequest> getPendingRequests() {
        return requestRepository.findByStatus(AdoptionRequest.Status.PENDING);
    }

    @Override
    public AdoptionRequest updateStatus(String requestId, AdoptionRequest.Status status) {
        var request = requestRepository.findById(requestId);
        request.get().setStatus(status);
        return null;
    }
}
