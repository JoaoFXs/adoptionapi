package io.gituhub.jfelixy.petadoptionapi.domain.entity;

import io.gituhub.jfelixy.petadoptionapi.domain.enums.user.RoleEnum;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User implements UserDetails { // Implements Spring Security's UserDetails for authentication

    @Id // Marks this field as the primary key
    @GeneratedValue(strategy = GenerationType.UUID) // Generates a UUID automatically for the ID
    private String id;

    @Column(unique = true, nullable = false) // Username must be unique and not null
    private String username;

    @Column(unique = true, nullable = false) // Username must be unique and not null
    private String email;

    @Column(nullable = false) // Password cannot be null
    private String password;

    @Enumerated(EnumType.STRING) // Stores the enum value as a string (instead of ordinal number)
    @Column(nullable = false) // Role cannot be null
    private RoleEnum role;

    // One user can adopt multiple pets
    // "adoptedByUser" is the field name in Pet class that maps back to User
    @OneToMany(mappedBy = "adoptedByUser")
    private List<Pet> adoptedPets;

    @CreatedDate
    @Column(name="created_at")
    private LocalDate createdAt;

    @Column
    @Lob
    private byte[] photo;


    // Returns the authorities/roles granted to the user
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities(){
        return List.of(() -> "ROLE_" + this.role.name()); // Returns role as a GrantedAuthority
    }

    // Indicates whether the user's account is not expired
    @Override
    public boolean isAccountNonExpired() { return true; }

    // Indicates whether the user's account is not locked
    @Override
    public boolean isAccountNonLocked() { return true; }

    // Indicates whether the user's credentials are not expired
    @Override
    public boolean isCredentialsNonExpired() { return true; }

    // Indicates whether the user is enabled
    @Override
    public boolean isEnabled() { return true; }


}