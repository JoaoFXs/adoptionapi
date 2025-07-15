package io.gituhub.jfelixy.petadoptionapi.application.adoptionrequest;


import io.gituhub.jfelixy.petadoptionapi.domain.entity.AdoptionRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/adoption-requests")
@RequiredArgsConstructor
public class AdoptionRequestController {
    @Autowired
    private AdoptionRequestServiceImpl service;

    @Autowired
    private RequestMapper requestMapper;

    @PostMapping
    public ResponseEntity<AdoptionRequestDTO> createRequest(
            @RequestBody AdoptionRequestDTO dto,
            @RequestParam String userId,
            @RequestParam String petId){

        return ResponseEntity.ok(requestMapper.mapRequesttoDTO(service.createRequest(dto, userId, petId)));
    }

}
