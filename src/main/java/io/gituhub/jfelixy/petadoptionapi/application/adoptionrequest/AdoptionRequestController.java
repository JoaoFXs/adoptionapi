package io.gituhub.jfelixy.petadoptionapi.application.adoptionrequest;


import io.gituhub.jfelixy.petadoptionapi.domain.entity.AdoptionRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

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
    @GetMapping("/pending")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<AdoptionRequestDTO>> getPendingRequests() {

        var listRequestsPending =  service.getPendingRequests();
        var requestsDTO = listRequestsPending.stream().map( request ->{
            return requestMapper.mapRequesttoDTO(request);
        }).collect(java.util.stream.Collectors.toList());

        return ResponseEntity.ok(requestsDTO);
    }

    @PatchMapping("/{id}/status")
    @PreAuthorize("hasRole('ADMIN')")
    public AdoptionRequest updateStatus(
            @PathVariable String id,
            @RequestParam AdoptionRequest.Status status
    ) {

        return service.updateStatus(id, status);
    }
}
