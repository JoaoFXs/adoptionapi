package io.gituhub.jfelixy.petadoptionapi.domain.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.cglib.core.Local;

import java.lang.reflect.Type;
import java.time.LocalDateTime;

@Entity
@Table(name="adoption_requests")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AdoptionRequest {

    public enum Status{
        PENDING,  //Aguardando análise
        APPROVED, //Solicitação aprovada
        REJECTED  //Rejeitada pelo admin
    }

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @ManyToOne
    @JoinColumn(name = "pet_id", nullable = false)
    private Pet pet;//Pet vinculado a request

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User applicant; // Usuário que solicitou a adoção

    @Column(nullable = false)
    private String fullName;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private boolean hasChildren;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String petExperience;

    @Column(nullable = false)
    private String residenceType;

    @Column(updatable = false)
    private LocalDateTime date = LocalDateTime.now();

    @Enumerated
    @Builder.Default
    private Status status = Status.PENDING;

    //Método auxiliar para verificar se request esta pendente
    public boolean isPending(){
        return status == Status.PENDING;
    }




}
