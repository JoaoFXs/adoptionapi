package io.gituhub.jfelixy.petadoptionapi.application.adoptionrequest;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AdoptionRequestDTO {
    @NotBlank
    private String fullName;
    @Email
    private String email;
    @NotBlank
    private String phone;
    private String petExperience;
    private String residenceType;
    private boolean hasChildren;
    private String adoptionReason;
    private String pet_id;
    private String user_id;
    private String status;

}