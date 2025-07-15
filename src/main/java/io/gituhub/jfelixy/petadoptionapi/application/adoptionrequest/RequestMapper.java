package io.gituhub.jfelixy.petadoptionapi.application.adoptionrequest;


import io.gituhub.jfelixy.petadoptionapi.domain.entity.AdoptionRequest;
import jakarta.persistence.Column;
import org.springframework.stereotype.Component;

@Component
public class RequestMapper {

    public AdoptionRequestDTO mapRequesttoDTO(AdoptionRequest request){
        return AdoptionRequestDTO
                .builder()
                .adoptionReason(request.getAdoptionReason())
                .phone(request.getPhone())
                .email(request.getEmail())
                .hasChildren(request.isHasChildren())
                .fullName(request.getFullName())
                .petExperience(request.getPetExperience())
                .residenceType(request.getResidenceType())
                .pet_id(request.getPet().getId())
                .user_id(request.getApplicant().getId())
                .build();
    }
}
