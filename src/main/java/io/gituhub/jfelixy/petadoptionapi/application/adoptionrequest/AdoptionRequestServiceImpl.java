package io.gituhub.jfelixy.petadoptionapi.application.adoptionrequest;

import io.gituhub.jfelixy.petadoptionapi.application.users.UserServiceImpl;
import io.gituhub.jfelixy.petadoptionapi.domain.entity.AdoptionRequest;
import io.gituhub.jfelixy.petadoptionapi.domain.entity.User;
import io.gituhub.jfelixy.petadoptionapi.domain.exception.DuplicatedTupleException;
import io.gituhub.jfelixy.petadoptionapi.domain.exception.IdNotFound;
import io.gituhub.jfelixy.petadoptionapi.domain.service.AdoptionRequestService;
import io.gituhub.jfelixy.petadoptionapi.infra.repository.adoptionrequest.AdoptionRequestRepository;
import io.gituhub.jfelixy.petadoptionapi.infra.repository.pet.PetRepository;
import io.gituhub.jfelixy.petadoptionapi.infra.repository.user.UserRepository;
import org.hibernate.annotations.NotFound;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdoptionRequestServiceImpl implements AdoptionRequestService {

    @Autowired
    private AdoptionRequestRepository requestRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PetRepository petRepository;

    @Override
    public AdoptionRequest createRequest(AdoptionRequestDTO dto, String userId, String petId) {


        var user = userRepository.findById(userId);

        var pet = petRepository.findById(petId);

        if(user.isEmpty()) throw new IdNotFound("User not found while creating request");
        if(pet.isEmpty()) throw new IdNotFound("Pet not found whilte creating request");

        AdoptionRequest request = AdoptionRequest.builder()
                .applicant(user.get())
                .pet(pet.get())
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

        if(request.isEmpty()) throw new IdNotFound("Request not found while updating request");

        request.get().setStatus(status);
        return null;
    }
}
